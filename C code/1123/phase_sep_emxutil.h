/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: phase_sep_emxutil.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 16-Nov-2020 11:39:11
 */

#ifndef PHASE_SEP_EMXUTIL_H
#define PHASE_SEP_EMXUTIL_H

/* Include Files */
#include <stddef.h>
#include <stdlib.h>
#include "rtwtypes.h"
#include "phase_sep_types.h"

/* Function Declarations */
extern void emxCopyStruct_cell_wrap_0(cell_wrap_0 *dst, const cell_wrap_0 *src);
extern void emxEnsureCapacity_boolean_T(emxArray_boolean_T *emxArray, int
  oldNumel);
extern void emxEnsureCapacity_int32_T(emxArray_int32_T *emxArray, int oldNumel);
extern void phaseemxEnsureCapacity_real_T(emxArray_real_T *emxArray, int oldNumel);
extern void emxFreeMatrix_cell_wrap_0(cell_wrap_0 pMatrix[100]);
extern void emxFree_boolean_T(emxArray_boolean_T **pEmxArray);
extern void emxFree_int32_T(emxArray_int32_T **pEmxArray);
extern void phaseemxFree_real_T(emxArray_real_T **pEmxArray);
extern void emxInitMatrix_cell_wrap_0(cell_wrap_0 pMatrix[100]);
extern void emxInit_boolean_T(emxArray_boolean_T **pEmxArray, int numDimensions);
extern void emxInit_int32_T(emxArray_int32_T **pEmxArray, int numDimensions);
extern void phaseemxInit_real_T(emxArray_real_T **pEmxArray, int numDimensions);

#endif

/*
 * File trailer for phase_sep_emxutil.h
 *
 * [EOF]
 */
