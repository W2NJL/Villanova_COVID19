/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: ac_feat_emxAPI.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

#ifndef AC_FEAT_EMXAPI_H
#define AC_FEAT_EMXAPI_H

/* Include Files */
#include <stddef.h>
#include <stdlib.h>
#include "rtwtypes.h"
#include "ac_feat_types.h"

/* Function Declarations */
extern emxArray_real_T *acemxCreateND_real_T(int numDimensions, const int *size);
extern emxArray_real_T *acemxCreateWrapperND_real_T(double *data, int
  numDimensions, const int *size);
extern emxArray_real_T *acemxCreateWrapper_real_T(double *data, int rows, int cols);
extern emxArray_real_T *acemxCreate_real_T(int rows, int cols);
extern void acemxDestroyArray_real_T(emxArray_real_T *emxArray);
extern void acemxInitArray_real_T(emxArray_real_T **pEmxArray, int numDimensions);

#endif

/*
 * File trailer for ac_feat_emxAPI.h
 *
 * [EOF]
 */
