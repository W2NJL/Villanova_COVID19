/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: filtering_initialize.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 05-Nov-2020 13:57:38
 */

/* Include Files */
#include "filtering_initialize.h"
#include "filtering.h"
#include "filtering_data.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : void
 * Return Type  : void
 */
void filtering_initialize(void)
{
  rt_InitInfAndNaN();
  isInitialized_filtering = true;
}

/*
 * File trailer for filtering_initialize.c
 *
 * [EOF]
 */
