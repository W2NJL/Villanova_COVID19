/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: filtering.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 21-Sep-2020 22:35:40
 */

/* Include Files */
#include "filtering.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* Function Definitions */

/*
 * function [y_fil]= filtering(y_raw)
 * Arguments    : const double y_raw[2411040]
 *                double y_fil[2411040]
 * Return Type  : void
 */
void filtering(const double y_raw[2411040], double y_fil[2411040])
{
  int k;
  int naxpy;
  int j;
  int y_fil_tmp;
  static const double dv[7] = { 0.85918007665512053, -5.1550804599307227,
    12.887701149826809, -17.183601533102411, 12.887701149826809,
    -5.1550804599307227, 0.85918007665512053 };

  double as;
  static const double dv1[7] = { 1.0, -5.6965607253643142, 13.528498994105979,
    -17.144063240960978, 12.227073155879228, -4.6531383854961241,
    0.73819040412109915 };

  /* 'filtering:3' [num,den] = butter(6,[200]/8000,'high') ; */
  /* 'filtering:4' y_fil=filter(num,den,y_raw); */
  memset(&y_fil[0], 0, 2411040U * sizeof(double));
  for (k = 0; k < 2411040; k++) {
    if (2411040 - k < 7) {
      naxpy = 2411039 - k;
    } else {
      naxpy = 6;
    }

    for (j = 0; j <= naxpy; j++) {
      y_fil_tmp = k + j;
      y_fil[y_fil_tmp] += y_raw[k] * dv[j];
    }

    if (2411039 - k < 6) {
      naxpy = 2411038 - k;
    } else {
      naxpy = 5;
    }

    as = -y_fil[k];
    for (j = 0; j <= naxpy; j++) {
      y_fil_tmp = (k + j) + 1;
      y_fil[y_fil_tmp] += as * dv1[j + 1];
    }
  }
}

/*
 * File trailer for filtering.c
 *
 * [EOF]
 */
