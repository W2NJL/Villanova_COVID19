/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: kurtosis.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "kurtosis.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "bsxfun.h"
#include "nan_sum_or_mean.h"
#include "nanmean.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *x
 * Return Type  : double
 */
double kurtosis(const emxArray_real_T *x)
{
  double k;
  emxArray_real_T *x0;
  double s2;
  int n;
  int i;
  acemxInit_real_T(&x0, 1);
  nan_sum_or_mean(x, &s2, &n);
  bsxfun(x, s2, x0);
  n = x0->size[0];
  for (i = 0; i < n; i++) {
    x0->data[i] *= x0->data[i];
  }

  s2 = nanmean(x0);
  n = x0->size[0];
  for (i = 0; i < n; i++) {
    x0->data[i] *= x0->data[i];
  }

  k = nanmean(x0) / (s2 * s2);
  acemxFree_real_T(&x0);
  return k;
}

/*
 * File trailer for kurtosis.c
 *
 * [EOF]
 */
