/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: minOrMax.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Oct-2020 11:12:36
 */

#ifndef MINORMAX_H
#define MINORMAX_H

/* Include Files */
#include <stddef.h>
#include <stdlib.h>
#include "rtwtypes.h"
#include "features_types.h"

/* Function Declarations */
extern void b_maximum2(double x, const double y_data[], const int y_size[1],
  double ex_data[], int ex_size[1]);
extern double maximum(const emxArray_real_T *x);
extern void maximum2(const double y_data[], const int y_size[1], double ex_data[],
                     int ex_size[1]);
extern double minimum(const emxArray_real_T *x);
extern void minimum2(const double x_data[], const int x_size[1], double ex_data[],
                     int ex_size[1]);

#endif

/*
 * File trailer for minOrMax.h
 *
 * [EOF]
 */
