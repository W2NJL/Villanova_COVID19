/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: ac_feat_emxAPI.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "ac_feat_emxAPI.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "rt_nonfinite.h"
#include <stdlib.h>

/* Function Definitions */

/*
 * Arguments    : int numDimensions
 *                const int *size
 * Return Type  : emxArray_real_T *
 */
emxArray_real_T *acemxCreateND_real_T(int numDimensions, const int *size)
{
  emxArray_real_T *emx;
  int numEl;
  int i;
  acemxInit_real_T(&emx, numDimensions);
  numEl = 1;
  for (i = 0; i < numDimensions; i++) {
    numEl *= size[i];
    emx->size[i] = size[i];
  }

  emx->data = (double *)calloc((unsigned int)numEl, sizeof(double));
  emx->numDimensions = numDimensions;
  emx->allocatedSize = numEl;
  return emx;
}

/*
 * Arguments    : double *data
 *                int numDimensions
 *                const int *size
 * Return Type  : emxArray_real_T *
 */
emxArray_real_T *acemxCreateWrapperND_real_T(double *data, int numDimensions,
  const int *size)
{
  emxArray_real_T *emx;
  int numEl;
  int i;
  acemxInit_real_T(&emx, numDimensions);
  numEl = 1;
  for (i = 0; i < numDimensions; i++) {
    numEl *= size[i];
    emx->size[i] = size[i];
  }

  emx->data = data;
  emx->numDimensions = numDimensions;
  emx->allocatedSize = numEl;
  emx->canFreeData = false;
  return emx;
}

/*
 * Arguments    : double *data
 *                int rows
 *                int cols
 * Return Type  : emxArray_real_T *
 */
emxArray_real_T *acemxCreateWrapper_real_T(double *data, int rows, int cols)
{
  emxArray_real_T *emx;
  acemxInit_real_T(&emx, 2);
  emx->size[0] = rows;
  emx->size[1] = cols;
  emx->data = data;
  emx->numDimensions = 2;
  emx->allocatedSize = rows * cols;
  emx->canFreeData = false;
  return emx;
}

/*
 * Arguments    : int rows
 *                int cols
 * Return Type  : emxArray_real_T *
 */
emxArray_real_T *acemxCreate_real_T(int rows, int cols)
{
  emxArray_real_T *emx;
  int numEl;
  acemxInit_real_T(&emx, 2);
  emx->size[0] = rows;
  numEl = rows * cols;
  emx->size[1] = cols;
  emx->data = (double *)calloc((unsigned int)numEl, sizeof(double));
  emx->numDimensions = 2;
  emx->allocatedSize = numEl;
  return emx;
}

/*
 * Arguments    : emxArray_real_T *emxArray
 * Return Type  : void
 */
void acemxDestroyArray_real_T(emxArray_real_T *emxArray)
{
  acemxFree_real_T(&emxArray);
}

/*
 * Arguments    : emxArray_real_T **pEmxArray
 *                int numDimensions
 * Return Type  : void
 */
void acemxInitArray_real_T(emxArray_real_T **pEmxArray, int numDimensions)
{
  acemxInit_real_T(pEmxArray, numDimensions);
}

/*
 * File trailer for ac_feat_emxAPI.c
 *
 * [EOF]
 */
