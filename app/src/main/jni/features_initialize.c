/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: features_initialize.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Oct-2020 11:12:36
 */

/* Include Files */
#include "features_initialize.h"
#include "features.h"
#include "features_data.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : void
 * Return Type  : void
 */
void features_initialize(void)
{
  rt_InitInfAndNaN();
  isInitialized_features = true;
}

/*
 * File trailer for features_initialize.c
 *
 * [EOF]
 */
