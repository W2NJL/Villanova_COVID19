/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: xcorr.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "xcorr.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "ac_feat_rtwutil.h"
#include "fft.h"
#include "ifft.h"
#include "rt_nonfinite.h"
#include <math.h>
#include <string.h>

/* Function Declarations */
static void autocorr(const emxArray_real_T *x, double c_data[], int c_size[1]);
static double rt_hypotd_snf(double u0, double u1);

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *x
 *                double c_data[]
 *                int c_size[1]
 * Return Type  : void
 */
static void autocorr(const emxArray_real_T *x, double c_data[], int c_size[1])
{
  int mxl;
  double m2;
  int ceilLog2;
  int c0;
  emxArray_creal_T *X;
  emxArray_real_T *c1;
  int i;
  emxArray_real_T *y;
  int loop_ub;
  unsigned int y_data[1002];
  mxl = (int)fmin(1000.0, (double)x->size[0] - 1.0);
  m2 = frexp((int)fabs(2.0 * (double)x->size[0] - 1.0), &ceilLog2);
  if (m2 == 0.5) {
    ceilLog2--;
  }

  m2 = acrt_powd_snf(2.0, ceilLog2);
  c0 = (x->size[0] << 1) - 1;
  if (c0 + mxl * ((c0 - mxl) - 1) < m2 * (15.0 * (double)ceilLog2 + 6.0)) {
    mxl = (int)fmin(mxl, (double)x->size[0] - 1.0);
    ceilLog2 = mxl << 1;
    c_size[0] = ceilLog2 + 1;
    if (0 <= ceilLog2) {
      memset(&c_data[0], 0, (ceilLog2 + 1) * sizeof(double));
    }

    for (c0 = 0; c0 <= mxl; c0++) {
      m2 = 0.0;
      i = x->size[0] - c0;
      for (ceilLog2 = 0; ceilLog2 < i; ceilLog2++) {
        m2 += x->data[ceilLog2] * x->data[c0 + ceilLog2];
      }

      c_data[mxl - c0] = m2;
      c_data[mxl + c0] = m2;
    }
  } else {
    acemxInit_creal_T(&X, 1);
    acemxInit_real_T(&c1, 1);
    fft(x, m2, X);
    ceilLog2 = X->size[0];
    i = c1->size[0];
    c1->size[0] = X->size[0];
    acemxEnsureCapacity_real_T(c1, i);
    for (c0 = 0; c0 < ceilLog2; c0++) {
      c1->data[c0] = rt_hypotd_snf(X->data[c0].re, X->data[c0].im);
    }

    acemxInit_real_T(&y, 1);
    i = y->size[0];
    y->size[0] = c1->size[0];
    acemxEnsureCapacity_real_T(y, i);
    ceilLog2 = c1->size[0];
    for (c0 = 0; c0 < ceilLog2; c0++) {
      y->data[c0] = acrt_powd_snf(c1->data[c0], 2.0);
    }

    ifft(y, X);
    i = c1->size[0];
    c1->size[0] = X->size[0];
    acemxEnsureCapacity_real_T(c1, i);
    ceilLog2 = X->size[0];
    acemxFree_real_T(&y);
    for (i = 0; i < ceilLog2; i++) {
      c1->data[i] = X->data[i].re;
    }

    acemxFree_creal_T(&X);
    if (1 > mxl + 1) {
      ceilLog2 = -1;
    } else {
      ceilLog2 = mxl;
    }

    if (mxl < 1) {
      c0 = 0;
    } else {
      c0 = mxl;
      loop_ub = mxl - 1;
      for (i = 0; i <= loop_ub; i++) {
        y_data[i] = (unsigned int)(i + 1);
      }
    }

    m2 -= (double)mxl;
    c_size[0] = (c0 + ceilLog2) + 1;
    for (i = 0; i < c0; i++) {
      c_data[i] = c1->data[(int)(m2 + (double)y_data[i]) - 1];
    }

    for (i = 0; i <= ceilLog2; i++) {
      c_data[i + c0] = c1->data[i];
    }

    acemxFree_real_T(&c1);
  }
}

/*
 * Arguments    : double u0
 *                double u1
 * Return Type  : double
 */
static double rt_hypotd_snf(double u0, double u1)
{
  double y;
  double a;
  a = fabs(u0);
  y = fabs(u1);
  if (a < y) {
    a /= y;
    y *= sqrt(a * a + 1.0);
  } else if (a > y) {
    y /= a;
    y = a * sqrt(y * y + 1.0);
  } else {
    if (!rtIsNaN(y)) {
      y = a * 1.4142135623730951;
    }
  }

  return y;
}

/*
 * Arguments    : const emxArray_real_T *x
 *                double c[2001]
 *                double lags[2001]
 * Return Type  : void
 */
void xcorr(const emxArray_real_T *x, double c[2001], double lags[2001])
{
  double c1_data[2001];
  int c1_size[1];
  int i;
  int b_i;
  autocorr(x, c1_data, c1_size);
  memset(&c[0], 0, 2001U * sizeof(double));
  if (1000 > x->size[0] - 1) {
    i = c1_size[0];
    for (b_i = 0; b_i < i; b_i++) {
      c[(b_i - x->size[0]) + 1001] = c1_data[b_i];
    }
  } else {
    memcpy(&c[0], &c1_data[0], 2001U * sizeof(double));
  }

  for (i = 0; i < 2001; i++) {
    lags[i] = (double)i + -1000.0;
  }
}

/*
 * File trailer for xcorr.c
 *
 * [EOF]
 */
