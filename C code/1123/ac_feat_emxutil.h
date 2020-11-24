/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: ac_feat_emxutil.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

#ifndef AC_FEAT_EMXUTIL_H
#define AC_FEAT_EMXUTIL_H

/* Include Files */
#include <stddef.h>
#include <stdlib.h>
#include "rtwtypes.h"
#include "ac_feat_types.h"

/* Function Declarations */
extern void acemxEnsureCapacity_boolean_T(emxArray_boolean_T *emxArray, int
  oldNumel);
extern void acemxEnsureCapacity_creal_T(emxArray_creal_T *emxArray, int oldNumel);
extern void acemxEnsureCapacity_cuint8_T(emxArray_cuint8_T *emxArray, int oldNumel);
extern void acemxEnsureCapacity_int32_T(emxArray_int32_T *emxArray, int oldNumel);
extern void acemxEnsureCapacity_real_T(emxArray_real_T *emxArray, int oldNumel);
extern void acemxFree_boolean_T(emxArray_boolean_T **pEmxArray);
extern void acemxFree_creal_T(emxArray_creal_T **pEmxArray);
extern void acemxFree_cuint8_T(emxArray_cuint8_T **pEmxArray);
extern void acemxFree_int32_T(emxArray_int32_T **pEmxArray);
extern void acemxFree_real_T(emxArray_real_T **pEmxArray);
extern void acemxInit_boolean_T(emxArray_boolean_T **pEmxArray, int numDimensions);
extern void acemxInit_creal_T(emxArray_creal_T **pEmxArray, int numDimensions);
extern void acemxInit_cuint8_T(emxArray_cuint8_T **pEmxArray, int numDimensions);
extern void acemxInit_int32_T(emxArray_int32_T **pEmxArray, int numDimensions);
extern void acemxInit_real_T(emxArray_real_T **pEmxArray, int numDimensions);

#endif

/*
 * File trailer for ac_feat_emxutil.h
 *
 * [EOF]
 */
