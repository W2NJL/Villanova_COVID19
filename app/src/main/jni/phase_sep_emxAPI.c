/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: phase_sep_emxAPI.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 05-Nov-2020 11:44:56
 */

/* Include Files */
#include "phase_sep_emxAPI.h"
#include "phase_sep.h"
#include "phase_sep_emxutil.h"
#include "rt_nonfinite.h"
#include <stdlib.h>

/* Function Definitions */

/*
 * Arguments    : int numDimensions
 *                const int *size
 * Return Type  : emxArray_real_T *
 */
emxArray_real_T *phaseemxCreateND_real_T(int numDimensions, const int *size)
{
  emxArray_real_T *emx;
  int numEl;
  int i;
  phaseemxInit_real_T(&emx, numDimensions);
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
emxArray_real_T *phaseemxCreateWrapperND_real_T(double *data, int numDimensions,
  const int *size)
{
  emxArray_real_T *emx;
  int numEl;
  int i;
  phaseemxInit_real_T(&emx, numDimensions);
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
emxArray_real_T *phaseemxCreateWrapper_real_T(double *data, int rows, int cols)
{
  emxArray_real_T *emx;
  phaseemxInit_real_T(&emx, 2);
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
emxArray_real_T *phaseemxCreate_real_T(int rows, int cols)
{
  emxArray_real_T *emx;
  int numEl;
  phaseemxInit_real_T(&emx, 2);
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
void phaseemxDestroyArray_real_T(emxArray_real_T *emxArray)
{
  phaseemxFree_real_T(&emxArray);
}

/*
 * Arguments    : cell_wrap_0 emxArray[100]
 * Return Type  : void
 */
void emxDestroyMatrix_cell_wrap_0(cell_wrap_0 emxArray[100])
{
  emxFreeMatrix_cell_wrap_0(emxArray);
}

/*
 * Arguments    : emxArray_real_T **pEmxArray
 *                int numDimensions
 * Return Type  : void
 */
void phaseemxInitArray_real_T(emxArray_real_T **pEmxArray, int numDimensions)
{
  phaseemxInit_real_T(pEmxArray, numDimensions);
}

/*
 * Arguments    : cell_wrap_0 pMatrix[100]
 * Return Type  : void
 */
void emxInitMatrix_cell_wrap_01(cell_wrap_0 pMatrix[100])
{
  emxInitMatrix_cell_wrap_0(pMatrix);
}

/*
 * File trailer for phase_sep_emxAPI.c
 *
 * [EOF]
 */
