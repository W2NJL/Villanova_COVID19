/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: phase_sep_initialize.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 16-Nov-2020 11:39:11
 */

/* Include Files */
#include "phase_sep_initialize.h"
#include "phase_sep.h"
#include "phase_sep_data.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : void
 * Return Type  : void
 */
void phase_sep_initialize(void)
{
  rt_InitInfAndNaN();
  isInitialized_phase_sep = true;
}

/*
 * File trailer for phase_sep_initialize.c
 *
 * [EOF]
 */
