/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: skewness.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Oct-2020 11:12:36
 */

/* Include Files */
#include "skewness.h"
#include "bsxfun.h"
#include "features.h"
#include "features_emxutil.h"
#include "features_rtwutil.h"
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
    emxInit_real_T(&x0, 1);
    emxInit_real_T(&xp, 1);
    nan_sum_or_mean(x, &m1, &n);
    bsxfun(x, m1, x0);
    i = xp->size[0];
    xp->size[0] = x0->size[0];
    emxEnsureCapacity_real_T(xp, i);
    n = x0->size[0];
    for (i = 0; i < n; i++) {
      xp->data[i] = x0->data[i] * x0->data[i];
    }

    m1 = nanmean(xp);
    n = xp->size[0];
    for (i = 0; i < n; i++) {
      xp->data[i] *= x0->data[i];
    }

    emxFree_real_T(&x0);
    s = nanmean(xp) / rt_powd_snf(m1, 1.5);
    emxFree_real_T(&xp);
  }

  return s;
}

/*
 * File trailer for skewness.c
 *
 * [EOF]
 */
