/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: ac_feat_initialize.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "ac_feat_initialize.h"
#include "ac_feat.h"
#include "ac_feat_data.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : void
 * Return Type  : void
 */
void ac_feat_initialize(void)
{
  rt_InitInfAndNaN();
  isInitialized_ac_feat = true;
}

/*
 * File trailer for ac_feat_initialize.c
 *
 * [EOF]
 */
