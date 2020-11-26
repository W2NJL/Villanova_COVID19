/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: power.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "power.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "ac_feat_rtwutil.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *a
 *                emxArray_real_T *y
 * Return Type  : void
 */
void b_power(const emxArray_real_T *a, emxArray_real_T *y)
{
  int nx;
  int k;
  nx = y->size[0] * y->size[1];
  y->size[0] = a->size[0];
  y->size[1] = a->size[1];
  acemxEnsureCapacity_real_T(y, nx);
  nx = a->size[0] * a->size[1];
  for (k = 0; k < nx; k++) {
    y->data[k] = acrt_powd_snf(a->data[k], 2.0);
  }
}

/*
 * Arguments    : const emxArray_real_T *a
 *                emxArray_real_T *y
 * Return Type  : void
 */
void power(const emxArray_real_T *a, emxArray_real_T *y)
{
  int nx;
  int k;
  nx = y->size[0];
  y->size[0] = a->size[0];
  acemxEnsureCapacity_real_T(y, nx);
  nx = a->size[0];
  for (k = 0; k < nx; k++) {
    y->data[k] = acrt_powd_snf(a->data[k], 2.0);
  }
}

/*
 * File trailer for power.c
 *
 * [EOF]
 */
