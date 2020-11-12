/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: bsxfun.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Oct-2020 11:12:36
 */

#ifndef BSXFUN_H
#define BSXFUN_H

/* Include Files */
#include <stddef.h>
#include <stdlib.h>
#include "rtwtypes.h"
#include "features_types.h"

/* Function Declarations */
extern void b_bsxfun(const emxArray_real_T *a, const double b_data[], const int
                     b_size[2], emxArray_real_T *c);
extern void bsxfun(const emxArray_real_T *a, double b, emxArray_real_T *c);
extern void c_bsxfun(const double a_data[], const int a_size[2], const double
                     b_data[], const int b_size[2], double c_data[], int c_size
                     [2]);

#endif

/*
 * File trailer for bsxfun.h
 *
 * [EOF]
 */
