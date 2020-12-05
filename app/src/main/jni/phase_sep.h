/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: phase_sep.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 04-Dec-2020 03:11:40
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
                      *y_fil_hb, double Fs, cell_wrap_0 y_ph_in[30], cell_wrap_0
                      y_ph_fil_in[30], cell_wrap_0 y_ph_ex[30], cell_wrap_0
                      y_ph_fil_ex[30], double siz_in_data[], int siz_in_size[2],
                      double siz_ex_data[], int siz_ex_size[2]);

#endif

/*
 * File trailer for phase_sep.h
 *
 * [EOF]
 */
