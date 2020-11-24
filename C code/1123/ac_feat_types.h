/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: ac_feat_types.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

#ifndef AC_FEAT_TYPES_H
#define AC_FEAT_TYPES_H

/* Include Files */
#include "rtwtypes.h"

/* Type Definitions */
#ifndef struct_emxArray_boolean_T
#define struct_emxArray_boolean_T

struct acemxArray_boolean_T
{
  bool *data;
  int *size;
  int allocatedSize;
  int numDimensions;
  bool canFreeData;
};

#endif                                 /*struct_emxArray_boolean_T*/

#ifndef typedef_emxArray_boolean_T
#define typedef_emxArray_boolean_T

typedef struct acemxArray_boolean_T emxArray_boolean_T;

#endif                                 /*typedef_emxArray_boolean_T*/

#ifndef typedef_emxArray_creal_T
#define typedef_emxArray_creal_T

typedef struct {
  creal_T *data;
  int *size;
  int allocatedSize;
  int numDimensions;
  bool canFreeData;
} emxArray_creal_T;

#endif                                 /*typedef_emxArray_creal_T*/

#ifndef typedef_emxArray_cuint8_T
#define typedef_emxArray_cuint8_T

typedef struct {
  cuint8_T *data;
  int *size;
  int allocatedSize;
  int numDimensions;
  bool canFreeData;
} emxArray_cuint8_T;

#endif                                 /*typedef_emxArray_cuint8_T*/

#ifndef struct_emxArray_int32_T
#define struct_emxArray_int32_T

struct acemxArray_int32_T
{
  int *data;
  int *size;
  int allocatedSize;
  int numDimensions;
  bool canFreeData;
};

#endif                                 /*struct_emxArray_int32_T*/

#ifndef typedef_emxArray_int32_T
#define typedef_emxArray_int32_T

typedef struct acemxArray_int32_T emxArray_int32_T;

#endif                                 /*typedef_emxArray_int32_T*/

#ifndef struct_emxArray_real_T
#define struct_emxArray_real_T

struct acemxArray_real_T
{
  double *data;
  int *size;
  int allocatedSize;
  int numDimensions;
  bool canFreeData;
};

#endif                                 /*struct_emxArray_real_T*/

#ifndef typedef_emxArray_real_T
#define typedef_emxArray_real_T

typedef struct acemxArray_real_T emxArray_real_T;

#endif                                 /*typedef_emxArray_real_T*/
#endif

/*
 * File trailer for ac_feat_types.h
 *
 * [EOF]
 */
