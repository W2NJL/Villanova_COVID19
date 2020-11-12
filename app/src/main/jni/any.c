/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: any.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Oct-2020 11:12:36
 */

/* Include Files */
#include "any.h"
#include "features.h"
#include "features_emxutil.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_boolean_T *x
 *                emxArray_boolean_T *y
 * Return Type  : void
 */
void any(const emxArray_boolean_T *x, emxArray_boolean_T *y)
{
  int iy;
  int vstride;
  int i1;
  int i2;
  int j;
  int ix;
  bool exitg1;
  iy = y->size[0];
  y->size[0] = x->size[0];
  emxEnsureCapacity_boolean_T(y, iy);
  vstride = x->size[0];
  for (iy = 0; iy < vstride; iy++) {
    y->data[iy] = false;
  }

  vstride = x->size[0];
  iy = -1;
  i1 = 0;
  i2 = 0;
  for (j = 0; j < vstride; j++) {
    i1++;
    i2++;
    iy++;
    ix = i1;
    exitg1 = false;
    while ((!exitg1) && ((vstride > 0) && (ix <= i2))) {
      if (!x->data[ix - 1]) {
        ix += vstride;
      } else {
        y->data[iy] = true;
        exitg1 = true;
      }
    }
  }
}

/*
 * Arguments    : const emxArray_boolean_T *x
 * Return Type  : bool
 */
bool b_any(const emxArray_boolean_T *x)
{
  bool y;
  int ix;
  bool exitg1;
  y = false;
  ix = 1;
  exitg1 = false;
  while ((!exitg1) && (ix <= x->size[0])) {
    if (!x->data[ix - 1]) {
      ix++;
    } else {
      y = true;
      exitg1 = true;
    }
  }

  return y;
}

/*
 * File trailer for any.c
 *
 * [EOF]
 */
