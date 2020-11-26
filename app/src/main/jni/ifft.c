/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: ifft.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "ifft.h"
#include "FFTImplementationCallback.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *x
 *                emxArray_creal_T *y
 * Return Type  : void
 */
void ifft(const emxArray_real_T *x, emxArray_creal_T *y)
{
  emxArray_real_T *costab;
  int nRows;
  emxArray_real_T *sintab;
  emxArray_real_T *sintabinv;
  bool useRadix2;
  int N2blue;
  if (x->size[0] == 0) {
    nRows = y->size[0];
    y->size[0] = x->size[0];
    acemxEnsureCapacity_creal_T(y, nRows);
    N2blue = x->size[0];
    for (nRows = 0; nRows < N2blue; nRows++) {
      y->data[nRows].re = 0.0;
      y->data[nRows].im = 0.0;
    }
  } else {
    acemxInit_real_T(&costab, 2);
    acemxInit_real_T(&sintab, 2);
    acemxInit_real_T(&sintabinv, 2);
    useRadix2 = ((x->size[0] & (x->size[0] - 1)) == 0);
    c_FFTImplementationCallback_get(x->size[0], useRadix2, &N2blue, &nRows);
    e_FFTImplementationCallback_gen(nRows, useRadix2, costab, sintab, sintabinv);
    if (useRadix2) {
      g_FFTImplementationCallback_r2b(x, x->size[0], costab, sintab, y);
    } else {
      d_FFTImplementationCallback_dob(x, N2blue, x->size[0], costab, sintab,
        sintabinv, y);
    }

    acemxFree_real_T(&sintabinv);
    acemxFree_real_T(&sintab);
    acemxFree_real_T(&costab);
  }
}

/*
 * File trailer for ifft.c
 *
 * [EOF]
 */
