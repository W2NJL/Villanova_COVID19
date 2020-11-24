/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: skewness.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "skewness.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "ac_feat_rtwutil.h"
#include "bsxfun.h"
#include "nan_sum_or_mean.h"
#include "nanmean.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *x
 * Return Type  : double
 */
double skewness(const emxArray_real_T *x)
{
  double s;
  emxArray_real_T *x0;
  emxArray_real_T *xp;
  double m1;
  int n;
  int i;
  if (x->size[0] == 0) {
    s = rtNaN;
  } else {
    acemxInit_real_T(&x0, 1);
    acemxInit_real_T(&xp, 1);
    nan_sum_or_mean(x, &m1, &n);
    bsxfun(x, m1, x0);
    i = xp->size[0];
    xp->size[0] = x0->size[0];
    acemxEnsureCapacity_real_T(xp, i);
    n = x0->size[0];
    for (i = 0; i < n; i++) {
      xp->data[i] = x0->data[i] * x0->data[i];
    }

    m1 = nanmean(xp);
    n = xp->size[0];
    for (i = 0; i < n; i++) {
      xp->data[i] *= x0->data[i];
    }

    acemxFree_real_T(&x0);
    s = nanmean(xp) / acrt_powd_snf(m1, 1.5);
    acemxFree_real_T(&xp);
  }

  return s;
}

/*
 * File trailer for skewness.c
 *
 * [EOF]
 */
