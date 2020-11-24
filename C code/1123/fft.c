/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: fft.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "fft.h"
#include "FFTImplementationCallback.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *x
 *                double varargin_1
 *                emxArray_creal_T *y
 * Return Type  : void
 */
void fft(const emxArray_real_T *x, double varargin_1, emxArray_creal_T *y)
{
  emxArray_real_T *costab;
  emxArray_real_T *sintab;
  emxArray_real_T *sintabinv;
  bool guard1 = false;
  int i;
  bool useRadix2;
  int N2blue;
  int nRows;
  acemxInit_real_T(&costab, 2);
  acemxInit_real_T(&sintab, 2);
  acemxInit_real_T(&sintabinv, 2);
  guard1 = false;
  if (x->size[0] == 0) {
    guard1 = true;
  } else {
    i = (int)varargin_1;
    if (0 == i) {
      guard1 = true;
    } else {
      useRadix2 = ((i > 0) && ((i & (i - 1)) == 0));
      c_FFTImplementationCallback_get((int)varargin_1, useRadix2, &N2blue,
        &nRows);
      c_FFTImplementationCallback_gen(nRows, useRadix2, costab, sintab,
        sintabinv);
      if (useRadix2) {
        c_FFTImplementationCallback_r2b(x, i, costab, sintab, y);
      } else {
        c_FFTImplementationCallback_dob(x, N2blue, (int)varargin_1, costab,
          sintab, sintabinv, y);
      }
    }
  }

  if (guard1) {
    N2blue = (int)varargin_1;
    i = y->size[0];
    y->size[0] = N2blue;
    acemxEnsureCapacity_creal_T(y, i);
    for (i = 0; i < N2blue; i++) {
      y->data[i].re = 0.0;
      y->data[i].im = 0.0;
    }
  }

  acemxFree_real_T(&sintabinv);
  acemxFree_real_T(&sintab);
  acemxFree_real_T(&costab);
}

/*
 * File trailer for fft.c
 *
 * [EOF]
 */
