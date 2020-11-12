/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: log.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Oct-2020 11:12:36
 */

/* Include Files */
#include "log.h"
#include "features.h"
#include "rt_nonfinite.h"
#include <math.h>

/* Function Definitions */

/*
 * Arguments    : double x_data[]
 *                const int x_size[1]
 * Return Type  : void
 */
void b_log(double x_data[], const int x_size[1])
{
  int nx;
  int k;
  nx = x_size[0];
  for (k = 0; k < nx; k++) {
    x_data[k] = log(x_data[k]);
  }
}

/*
 * File trailer for log.c
 *
 * [EOF]
 */
