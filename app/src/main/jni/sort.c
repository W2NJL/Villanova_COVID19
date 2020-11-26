/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: sort.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 16-Nov-2020 11:39:11
 */

/* Include Files */
#include "sort.h"
#include "phase_sep.h"
#include "phase_sep_emxutil.h"
#include "rt_nonfinite.h"
#include "sortIdx.h"

/* Function Definitions */

/*
 * Arguments    : emxArray_real_T *x
 * Return Type  : void
 */
void b_sort(emxArray_real_T *x)
{
  emxArray_int32_T *idx;
  int i1;
  int ib;
  emxArray_int32_T *iwork;
  int n;
  int b_n;
  double x4[4];
  int idx4[4];
  emxArray_real_T *xwork;
  int nNaNs;
  int k;
  int i4;
  int idx_tmp;
  signed char perm[4];
  int quartetOffset;
  int i2;
  double d;
  double d1;
  emxInit_int32_T(&idx, 2);
  i1 = idx->size[0] * idx->size[1];
  idx->size[0] = 1;
  idx->size[1] = x->size[1];
  emxEnsureCapacity_int32_T(idx, i1);
  ib = x->size[1];
  for (i1 = 0; i1 < ib; i1++) {
    idx->data[i1] = 0;
  }

  if (x->size[1] != 0) {
    emxInit_int32_T(&iwork, 1);
    n = x->size[1];
    b_n = x->size[1];
    x4[0] = 0.0;
    idx4[0] = 0;
    x4[1] = 0.0;
    idx4[1] = 0;
    x4[2] = 0.0;
    idx4[2] = 0;
    x4[3] = 0.0;
    idx4[3] = 0;
    ib = x->size[1];
    i1 = iwork->size[0];
    iwork->size[0] = ib;
    emxEnsureCapacity_int32_T(iwork, i1);
    for (i1 = 0; i1 < ib; i1++) {
      iwork->data[i1] = 0;
    }

    phaseemxInit_real_T(&xwork, 1);
    ib = x->size[1];
    i1 = xwork->size[0];
    xwork->size[0] = ib;
    phaseemxEnsureCapacity_real_T(xwork, i1);
    for (i1 = 0; i1 < ib; i1++) {
      xwork->data[i1] = 0.0;
    }

    nNaNs = 0;
    ib = -1;
    for (k = 0; k < b_n; k++) {
      if (rtIsNaN(x->data[k])) {
        idx_tmp = (b_n - nNaNs) - 1;
        idx->data[idx_tmp] = k + 1;
        xwork->data[idx_tmp] = x->data[k];
        nNaNs++;
      } else {
        ib++;
        idx4[ib] = k + 1;
        x4[ib] = x->data[k];
        if (ib + 1 == 4) {
          quartetOffset = k - nNaNs;
          if (x4[0] <= x4[1]) {
            i1 = 1;
            i2 = 2;
          } else {
            i1 = 2;
            i2 = 1;
          }

          if (x4[2] <= x4[3]) {
            ib = 3;
            i4 = 4;
          } else {
            ib = 4;
            i4 = 3;
          }

          d = x4[i1 - 1];
          d1 = x4[ib - 1];
          if (d <= d1) {
            d = x4[i2 - 1];
            if (d <= d1) {
              perm[0] = (signed char)i1;
              perm[1] = (signed char)i2;
              perm[2] = (signed char)ib;
              perm[3] = (signed char)i4;
            } else if (d <= x4[i4 - 1]) {
              perm[0] = (signed char)i1;
              perm[1] = (signed char)ib;
              perm[2] = (signed char)i2;
              perm[3] = (signed char)i4;
            } else {
              perm[0] = (signed char)i1;
              perm[1] = (signed char)ib;
              perm[2] = (signed char)i4;
              perm[3] = (signed char)i2;
            }
          } else {
            d1 = x4[i4 - 1];
            if (d <= d1) {
              if (x4[i2 - 1] <= d1) {
                perm[0] = (signed char)ib;
                perm[1] = (signed char)i1;
                perm[2] = (signed char)i2;
                perm[3] = (signed char)i4;
              } else {
                perm[0] = (signed char)ib;
                perm[1] = (signed char)i1;
                perm[2] = (signed char)i4;
                perm[3] = (signed char)i2;
              }
            } else {
              perm[0] = (signed char)ib;
              perm[1] = (signed char)i4;
              perm[2] = (signed char)i1;
              perm[3] = (signed char)i2;
            }
          }

          idx_tmp = perm[0] - 1;
          idx->data[quartetOffset - 3] = idx4[idx_tmp];
          i2 = perm[1] - 1;
          idx->data[quartetOffset - 2] = idx4[i2];
          ib = perm[2] - 1;
          idx->data[quartetOffset - 1] = idx4[ib];
          i1 = perm[3] - 1;
          idx->data[quartetOffset] = idx4[i1];
          x->data[quartetOffset - 3] = x4[idx_tmp];
          x->data[quartetOffset - 2] = x4[i2];
          x->data[quartetOffset - 1] = x4[ib];
          x->data[quartetOffset] = x4[i1];
          ib = -1;
        }
      }
    }

    i4 = (b_n - nNaNs) - 1;
    if (ib + 1 > 0) {
      perm[1] = 0;
      perm[2] = 0;
      perm[3] = 0;
      if (ib + 1 == 1) {
        perm[0] = 1;
      } else if (ib + 1 == 2) {
        if (x4[0] <= x4[1]) {
          perm[0] = 1;
          perm[1] = 2;
        } else {
          perm[0] = 2;
          perm[1] = 1;
        }
      } else if (x4[0] <= x4[1]) {
        if (x4[1] <= x4[2]) {
          perm[0] = 1;
          perm[1] = 2;
          perm[2] = 3;
        } else if (x4[0] <= x4[2]) {
          perm[0] = 1;
          perm[1] = 3;
          perm[2] = 2;
        } else {
          perm[0] = 3;
          perm[1] = 1;
          perm[2] = 2;
        }
      } else if (x4[0] <= x4[2]) {
        perm[0] = 2;
        perm[1] = 1;
        perm[2] = 3;
      } else if (x4[1] <= x4[2]) {
        perm[0] = 2;
        perm[1] = 3;
        perm[2] = 1;
      } else {
        perm[0] = 3;
        perm[1] = 2;
        perm[2] = 1;
      }

      for (k = 0; k <= ib; k++) {
        idx_tmp = perm[k] - 1;
        i2 = (i4 - ib) + k;
        idx->data[i2] = idx4[idx_tmp];
        x->data[i2] = x4[idx_tmp];
      }
    }

    ib = (nNaNs >> 1) + 1;
    for (k = 0; k <= ib - 2; k++) {
      i1 = (i4 + k) + 1;
      i2 = idx->data[i1];
      idx_tmp = (b_n - k) - 1;
      idx->data[i1] = idx->data[idx_tmp];
      idx->data[idx_tmp] = i2;
      x->data[i1] = xwork->data[idx_tmp];
      x->data[idx_tmp] = xwork->data[i1];
    }

    if ((nNaNs & 1) != 0) {
      i1 = i4 + ib;
      x->data[i1] = xwork->data[i1];
    }

    i2 = n - nNaNs;
    ib = 2;
    if (i2 > 1) {
      if (n >= 256) {
        i1 = i2 >> 8;
        if (i1 > 0) {
          for (ib = 0; ib < i1; ib++) {
            b_merge_pow2_block(idx, x, ib << 8);
          }

          ib = i1 << 8;
          i1 = i2 - ib;
          if (i1 > 0) {
            b_merge_block(idx, x, ib, i1, 2, iwork, xwork);
          }

          ib = 8;
        }
      }

      b_merge_block(idx, x, 0, i2, ib, iwork, xwork);
    }

    phaseemxFree_real_T(&xwork);
    emxFree_int32_T(&iwork);
  }

  emxFree_int32_T(&idx);
}

/*
 * Arguments    : emxArray_int32_T *x
 * Return Type  : void
 */
void sort(emxArray_int32_T *x)
{
  int dim;
  emxArray_int32_T *vwork;
  int vstride;
  int vlen;
  int j;
  int k;
  emxArray_int32_T *b_vwork;
  dim = 0;
  if (x->size[0] != 1) {
    dim = -1;
  }

  emxInit_int32_T(&vwork, 1);
  if (dim + 2 <= 1) {
    vstride = x->size[0];
  } else {
    vstride = 1;
  }

  vlen = vstride - 1;
  j = vwork->size[0];
  vwork->size[0] = vstride;
  emxEnsureCapacity_int32_T(vwork, j);
  vstride = 1;
  for (k = 0; k <= dim; k++) {
    vstride *= x->size[0];
  }

  emxInit_int32_T(&b_vwork, 1);
  for (j = 0; j < vstride; j++) {
    for (k = 0; k <= vlen; k++) {
      vwork->data[k] = x->data[j + k * vstride];
    }

    b_sortIdx(vwork, b_vwork);
    for (k = 0; k <= vlen; k++) {
      x->data[j + k * vstride] = vwork->data[k];
    }
  }

  emxFree_int32_T(&b_vwork);
  emxFree_int32_T(&vwork);
}

/*
 * File trailer for sort.c
 *
 * [EOF]
 */
