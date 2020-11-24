/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: all.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "all.h"
#include "ac_feat.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const bool x[100]
 *                bool y[100]
 * Return Type  : void
 */
void all(const bool x[100], bool y[100])
{
  int i;
  int i1;
  int i2;
  int j;
  int ix;
  bool exitg1;
  for (i = 0; i < 100; i++) {
    y[i] = true;
  }

  i = -1;
  i1 = 0;
  i2 = 0;
  for (j = 0; j < 100; j++) {
    i1++;
    i2++;
    i++;
    ix = i1;
    exitg1 = false;
    while ((!exitg1) && (ix <= i2)) {
      if (!x[ix - 1]) {
        y[i] = false;
        exitg1 = true;
      } else {
        ix += 100;
      }
    }
  }
}

/*
 * File trailer for all.c
 *
 * [EOF]
 */
