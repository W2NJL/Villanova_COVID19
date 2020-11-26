/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: phase_sep.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 16-Nov-2020 11:39:11
 */

#ifndef PHASE_SEP_H
#define PHASE_SEP_H

/* Include Files */
#include <stddef.h>
#include <stdlib.h>
#include "rtwtypes.h"
#include "phase_sep_types.h"

/* Function Declarations */
extern void phase_sep(const emxArray_real_T *y_raw, const emxArray_real_T
                      *y_fil_hb, cell_wrap_0 y_ph_in[100], cell_wrap_0
                      y_ph_fil_in[100], cell_wrap_0 y_ph_ex[100], cell_wrap_0
                      y_ph_fil_ex[100], double siz_in_data[], int siz_in_size[2],
                      double siz_ex_data[], int siz_ex_size[2]);

#endif

/*
 * File trailer for phase_sep.h
 *
 * [EOF]
 */
