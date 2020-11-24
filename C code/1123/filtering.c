/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: filtering.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 05-Nov-2020 13:57:38
 */

/* Include Files */
#include "filtering.h"
#include "butter.h"
#include "filtering_data.h"
#include "filtering_emxutil.h"
#include "filtering_initialize.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *y_raw
 *                double Fs
 *                emxArray_real_T *y_fil
 * Return Type  : void
 */
void filtering(const emxArray_real_T *y_raw, double Fs, emxArray_real_T *y_fil)
{
  double num[7];
  double den[7];
  int nx;
  int loop_ub;
  int naxpy;
  int k;
  int j;
  int y_fil_tmp;
  double as;
  if (!isInitialized_filtering) {
    filtering_initialize();
  }

  butter(200.0 / (Fs / 2.0), num, den);
  nx = y_raw->size[0] - 1;
  loop_ub = y_raw->size[0];
  naxpy = y_fil->size[0];
  y_fil->size[0] = y_raw->size[0];
  emxEnsureCapacity_real_T(y_fil, naxpy);
  for (naxpy = 0; naxpy < loop_ub; naxpy++) {
    y_fil->data[naxpy] = 0.0;
  }

  for (k = 0; k <= nx; k++) {
    loop_ub = nx - k;
    naxpy = loop_ub + 1;
    if (naxpy >= 7) {
      naxpy = 7;
    }

    for (j = 0; j < naxpy; j++) {
      y_fil_tmp = k + j;
      y_fil->data[y_fil_tmp] += y_raw->data[k] * num[j];
    }

    if (loop_ub < 6) {
      naxpy = loop_ub;
    } else {
      naxpy = 6;
    }

    as = -y_fil->data[k];
    for (j = 0; j < naxpy; j++) {
      y_fil_tmp = (k + j) + 1;
      y_fil->data[y_fil_tmp] += as * den[j + 1];
    }
  }
}

/*
 * File trailer for filtering.c
 *
 * [EOF]
 */
