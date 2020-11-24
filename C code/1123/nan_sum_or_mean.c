/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: nan_sum_or_mean.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "nan_sum_or_mean.h"
#include "ac_feat.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *x
 *                double *y
 *                int *c
 * Return Type  : void
 */
void nan_sum_or_mean(const emxArray_real_T *x, double *y, int *c)
{
  int vlen;
  int k;
  *c = 0;
  if (x->size[0] == 0) {
    *y = rtNaN;
  } else {
    vlen = x->size[0];
    *y = 0.0;
    for (k = 0; k < vlen; k++) {
      if (!rtIsNaN(x->data[k])) {
        *y += x->data[k];
        (*c)++;
      }
    }

    if (*c == 0) {
      *y = rtNaN;
    } else {
      *y /= (double)*c;
    }
  }
}

/*
 * File trailer for nan_sum_or_mean.c
 *
 * [EOF]
 */
