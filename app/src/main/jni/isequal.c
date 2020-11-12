/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: isequal.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Oct-2020 11:12:36
 */

/* Include Files */
#include "isequal.h"
#include "features.h"
#include "features_data.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const char varargin_1[9]
 * Return Type  : bool
 */
bool isequal(const char varargin_1[9])
{
  bool p;
  bool b_p;
  int k;
  bool exitg1;
  p = false;
  b_p = true;
  k = 0;
  exitg1 = false;
  while ((!exitg1) && (k < 9)) {
    if (!(varargin_1[k] == cv[k])) {
      b_p = false;
      exitg1 = true;
    } else {
      k++;
    }
  }

  return b_p || p;
}

/*
 * File trailer for isequal.c
 *
 * [EOF]
 */
