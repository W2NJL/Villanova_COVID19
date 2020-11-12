/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: _coder_filtering_api.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 21-Sep-2020 22:35:40
 */

#ifndef _CODER_FILTERING_API_H
#define _CODER_FILTERING_API_H

/* Include Files */
#include <stddef.h>
#include <stdlib.h>
#include "tmwtypes.h"
#include "mex.h"
#include "emlrt.h"

/* Variable Declarations */
extern emlrtCTX emlrtRootTLSGlobal;
extern emlrtContext emlrtContextGlobal;

/* Function Declarations */
extern void filtering(real_T y_raw[2411040], real_T y_fil[2411040]);
extern void filtering_api(const mxArray * const prhs[1], int32_T nlhs, const
  mxArray *plhs[1]);
extern void filtering_atexit(void);
extern void filtering_initialize(void);
extern void filtering_terminate(void);
extern void filtering_xil_shutdown(void);
extern void filtering_xil_terminate(void);

#endif

/*
 * File trailer for _coder_filtering_api.h
 *
 * [EOF]
 */
