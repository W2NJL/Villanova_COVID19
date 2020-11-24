/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: lp2hp.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 05-Nov-2020 13:57:38
 */

/* Include Files */
#include "lp2hp.h"
#include "filtering.h"
#include "rt_nonfinite.h"
#include "xzgetrf.h"
#include <string.h>

/* Function Definitions */

/*
 * Arguments    : const double a[36]
 *                const double b[6]
 *                const double c[6]
 *                double d
 *                double wo
 *                double at[36]
 *                double bt[6]
 *                double ct[6]
 *                double *dt
 * Return Type  : void
 */
void lp2hp(const double a[36], const double b[6], const double c[6], double d,
           double wo, double at[36], double bt[6], double ct[6], double *dt)
{
  int i;
  double x[36];
  int ipiv[6];
  int pipk;
  int k;
  signed char p[6];
  int j;
  int at_tmp;
  int b_i;
  int kAcol;
  double temp;
  double X[6];
  for (i = 0; i < 36; i++) {
    at[i] = 0.0;
    x[i] = a[i];
  }

  xzgetrf(x, ipiv, &pipk);
  for (i = 0; i < 6; i++) {
    p[i] = (signed char)(i + 1);
  }

  for (k = 0; k < 5; k++) {
    if (ipiv[k] > k + 1) {
      pipk = p[ipiv[k] - 1];
      p[ipiv[k] - 1] = p[k];
      p[k] = (signed char)pipk;
    }
  }

  for (k = 0; k < 6; k++) {
    at_tmp = 6 * (p[k] - 1);
    at[k + at_tmp] = 1.0;
    for (j = k + 1; j < 7; j++) {
      i = (j + at_tmp) - 1;
      if (at[i] != 0.0) {
        kAcol = j + 1;
        for (b_i = kAcol; b_i < 7; b_i++) {
          pipk = (b_i + at_tmp) - 1;
          at[pipk] -= at[i] * x[(b_i + 6 * (j - 1)) - 1];
        }
      }
    }
  }

  for (j = 0; j < 6; j++) {
    pipk = 6 * j;
    for (k = 5; k >= 0; k--) {
      kAcol = 6 * k;
      i = k + pipk;
      if (at[i] != 0.0) {
        at[i] /= x[k + kAcol];
        for (b_i = 0; b_i < k; b_i++) {
          at_tmp = b_i + pipk;
          at[at_tmp] -= at[i] * x[b_i + kAcol];
        }
      }
    }
  }

  for (i = 0; i < 36; i++) {
    at[i] *= wo;
  }

  for (b_i = 0; b_i < 6; b_i++) {
    bt[b_i] = b[b_i];
  }

  memcpy(&x[0], &a[0], 36U * sizeof(double));
  xzgetrf(x, ipiv, &pipk);
  for (b_i = 0; b_i < 5; b_i++) {
    if (ipiv[b_i] != b_i + 1) {
      temp = bt[b_i];
      bt[b_i] = bt[ipiv[b_i] - 1];
      bt[ipiv[b_i] - 1] = temp;
    }
  }

  for (k = 0; k < 6; k++) {
    kAcol = 6 * k;
    if (bt[k] != 0.0) {
      i = k + 2;
      for (b_i = i; b_i < 7; b_i++) {
        bt[b_i - 1] -= bt[k] * x[(b_i + kAcol) - 1];
      }
    }
  }

  for (k = 5; k >= 0; k--) {
    kAcol = 6 * k;
    if (bt[k] != 0.0) {
      bt[k] /= x[k + kAcol];
      for (b_i = 0; b_i < k; b_i++) {
        bt[b_i] -= bt[k] * x[b_i + kAcol];
      }
    }
  }

  for (i = 0; i < 6; i++) {
    bt[i] *= -wo;
    ct[i] = c[i];
  }

  memcpy(&x[0], &a[0], 36U * sizeof(double));
  xzgetrf(x, ipiv, &pipk);
  for (j = 0; j < 6; j++) {
    pipk = 6 * j;
    for (k = 0; k < j; k++) {
      i = k + pipk;
      if (x[i] != 0.0) {
        ct[j] -= x[i] * ct[k];
      }
    }

    ct[j] *= 1.0 / x[j + pipk];
  }

  for (j = 5; j >= 0; j--) {
    pipk = 6 * j - 1;
    i = j + 2;
    for (k = i; k < 7; k++) {
      kAcol = k + pipk;
      if (x[kAcol] != 0.0) {
        ct[j] -= x[kAcol] * ct[k - 1];
      }
    }
  }

  for (j = 4; j >= 0; j--) {
    if (ipiv[j] != j + 1) {
      temp = ct[j];
      ct[j] = ct[ipiv[j] - 1];
      ct[ipiv[j] - 1] = temp;
    }
  }

  for (i = 0; i < 6; i++) {
    X[i] = c[i];
  }

  memcpy(&x[0], &a[0], 36U * sizeof(double));
  xzgetrf(x, ipiv, &pipk);
  for (j = 0; j < 6; j++) {
    pipk = 6 * j;
    for (k = 0; k < j; k++) {
      i = k + pipk;
      if (x[i] != 0.0) {
        X[j] -= x[i] * X[k];
      }
    }

    X[j] *= 1.0 / x[j + pipk];
  }

  for (j = 5; j >= 0; j--) {
    pipk = 6 * j - 1;
    i = j + 2;
    for (k = i; k < 7; k++) {
      kAcol = k + pipk;
      if (x[kAcol] != 0.0) {
        X[j] -= x[kAcol] * X[k - 1];
      }
    }
  }

  for (j = 4; j >= 0; j--) {
    if (ipiv[j] != j + 1) {
      temp = X[j];
      X[j] = X[ipiv[j] - 1];
      X[ipiv[j] - 1] = temp;
    }
  }

  temp = 0.0;
  for (i = 0; i < 6; i++) {
    temp += X[i] * b[i];
  }

  *dt = d - temp;
}

/*
 * File trailer for lp2hp.c
 *
 * [EOF]
 */
