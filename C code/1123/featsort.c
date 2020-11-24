/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: sort.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "sort.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "rt_nonfinite.h"
#include "sortIdx.h"

/* Function Definitions */

/*
 * Arguments    : emxArray_real_T *x
 *                emxArray_int32_T *idx
 * Return Type  : void
 */
void acb_sort(emxArray_real_T *x, emxArray_int32_T *idx)
{
  int dim;
  emxArray_real_T *vwork;
  int i;
  int vlen;
  int vstride;
  int k;
  emxArray_int32_T *iidx;
  dim = 0;
  if (x->size[0] != 1) {
    dim = -1;
  }

  acemxInit_real_T(&vwork, 1);
  if (dim + 2 <= 1) {
    i = x->size[0];
  } else {
    i = 1;
  }

  vlen = i - 1;
  vstride = vwork->size[0];
  vwork->size[0] = i;
  acemxEnsureCapacity_real_T(vwork, vstride);
  i = idx->size[0];
  idx->size[0] = x->size[0];
  acemxEnsureCapacity_int32_T(idx, i);
  vstride = 1;
  for (k = 0; k <= dim; k++) {
    vstride *= x->size[0];
  }

  acemxInit_int32_T(&iidx, 1);
  for (dim = 0; dim < vstride; dim++) {
    for (k = 0; k <= vlen; k++) {
      vwork->data[k] = x->data[dim + k * vstride];
    }

    sortIdx(vwork, iidx);
    for (k = 0; k <= vlen; k++) {
      i = dim + k * vstride;
      x->data[i] = vwork->data[k];
      idx->data[i] = iidx->data[k];
    }
  }

  acemxFree_int32_T(&iidx);
  acemxFree_real_T(&vwork);
}

/*
 * Arguments    : double x_data[]
 *                const int x_size[1]
 * Return Type  : void
 */
void acsort(double x_data[], const int x_size[1])
{
  int dim;
  int vwork_size_idx_0;
  int vlen;
  int vstride;
  int k;
  emxArray_real_T *vwork;
  emxArray_int32_T *b_vwork;
  int j;
  double vwork_data[4008];
  dim = 0;
  if (x_size[0] != 1) {
    dim = -1;
  }

  if (dim + 2 <= 1) {
    vwork_size_idx_0 = x_size[0];
  } else {
    vwork_size_idx_0 = 1;
  }

  vlen = vwork_size_idx_0 - 1;
  vstride = 1;
  for (k = 0; k <= dim; k++) {
    vstride *= x_size[0];
  }

  acemxInit_real_T(&vwork, 1);
  acemxInit_int32_T(&b_vwork, 1);
  for (j = 0; j < vstride; j++) {
    for (k = 0; k <= vlen; k++) {
      vwork_data[k] = x_data[j + k * vstride];
    }

    k = vwork->size[0];
    vwork->size[0] = vwork_size_idx_0;
    acemxEnsureCapacity_real_T(vwork, k);
    for (k = 0; k < vwork_size_idx_0; k++) {
      vwork->data[k] = vwork_data[k];
    }

    sortIdx(vwork, b_vwork);
    vwork_size_idx_0 = vwork->size[0];
    dim = vwork->size[0];
    for (k = 0; k < dim; k++) {
      vwork_data[k] = vwork->data[k];
    }

    for (k = 0; k <= vlen; k++) {
      x_data[j + k * vstride] = vwork->data[k];
    }
  }

  acemxFree_int32_T(&b_vwork);
  acemxFree_real_T(&vwork);
}

/*
 * File trailer for sort.c
 *
 * [EOF]
 */
