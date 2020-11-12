/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: bsxfun.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Oct-2020 11:12:36
 */

/* Include Files */
#include "bsxfun.h"
#include "features.h"
#include "features_emxutil.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *a
 *                const double b_data[]
 *                const int b_size[2]
 *                emxArray_real_T *c
 * Return Type  : void
 */
void b_bsxfun(const emxArray_real_T *a, const double b_data[], const int b_size
              [2], emxArray_real_T *c)
{
  int u0;
  int u1;
  int csz_idx_1;
  int i;
  int acoef;
  int bcoef;
  int k;
  int i1;
  int b_k;
  u0 = b_size[1];
  u1 = a->size[1];
  if (u0 < u1) {
    u1 = u0;
  }

  if (b_size[1] == 1) {
    csz_idx_1 = a->size[1];
  } else if (a->size[1] == 1) {
    csz_idx_1 = b_size[1];
  } else if (a->size[1] == b_size[1]) {
    csz_idx_1 = a->size[1];
  } else {
    csz_idx_1 = u1;
  }

  i = c->size[0] * c->size[1];
  c->size[0] = a->size[0];
  u0 = b_size[1];
  u1 = a->size[1];
  if (u0 < u1) {
    u1 = u0;
  }

  if (b_size[1] == 1) {
    c->size[1] = a->size[1];
  } else if (a->size[1] == 1) {
    c->size[1] = b_size[1];
  } else if (a->size[1] == b_size[1]) {
    c->size[1] = a->size[1];
  } else {
    c->size[1] = u1;
  }

  emxEnsureCapacity_real_T(c, i);
  if ((a->size[0] != 0) && (csz_idx_1 != 0)) {
    acoef = (a->size[1] != 1);
    bcoef = (b_size[1] != 1);
    i = csz_idx_1 - 1;
    for (k = 0; k <= i; k++) {
      u0 = acoef * k;
      u1 = bcoef * k;
      csz_idx_1 = (a->size[0] != 1);
      i1 = c->size[0] - 1;
      for (b_k = 0; b_k <= i1; b_k++) {
        c->data[b_k + c->size[0] * k] = a->data[csz_idx_1 * b_k + a->size[0] *
          u0] * b_data[u1];
      }
    }
  }
}

/*
 * Arguments    : const emxArray_real_T *a
 *                double b
 *                emxArray_real_T *c
 * Return Type  : void
 */
void bsxfun(const emxArray_real_T *a, double b, emxArray_real_T *c)
{
  int i;
  int acoef;
  int k;
  i = c->size[0];
  c->size[0] = a->size[0];
  emxEnsureCapacity_real_T(c, i);
  if (a->size[0] != 0) {
    acoef = (a->size[0] != 1);
    i = a->size[0] - 1;
    for (k = 0; k <= i; k++) {
      c->data[k] = a->data[acoef * k] - b;
    }
  }
}

/*
 * Arguments    : const double a_data[]
 *                const int a_size[2]
 *                const double b_data[]
 *                const int b_size[2]
 *                double c_data[]
 *                int c_size[2]
 * Return Type  : void
 */
void c_bsxfun(const double a_data[], const int a_size[2], const double b_data[],
              const int b_size[2], double c_data[], int c_size[2])
{
  signed char csz_idx_1;
  int acoef;
  int bcoef;
  int i;
  int k;
  if (b_size[1] == 1) {
    csz_idx_1 = (signed char)a_size[1];
  } else if (a_size[1] == 1) {
    csz_idx_1 = (signed char)b_size[1];
  } else if (a_size[1] == b_size[1]) {
    csz_idx_1 = (signed char)a_size[1];
  } else if (b_size[1] < a_size[1]) {
    csz_idx_1 = (signed char)b_size[1];
  } else {
    csz_idx_1 = (signed char)a_size[1];
  }

  c_size[0] = 1;
  if (b_size[1] == 1) {
    c_size[1] = (signed char)a_size[1];
  } else if (a_size[1] == 1) {
    c_size[1] = (signed char)b_size[1];
  } else if (a_size[1] == b_size[1]) {
    c_size[1] = (signed char)a_size[1];
  } else if (b_size[1] < a_size[1]) {
    c_size[1] = (signed char)b_size[1];
  } else {
    c_size[1] = (signed char)a_size[1];
  }

  if (csz_idx_1 != 0) {
    acoef = (a_size[1] != 1);
    bcoef = (b_size[1] != 1);
    i = csz_idx_1 - 1;
    for (k = 0; k <= i; k++) {
      c_data[k] = a_data[acoef * k] * b_data[bcoef * k];
    }
  }
}

/*
 * File trailer for bsxfun.c
 *
 * [EOF]
 */
