/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: FFTImplementationCallback.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

#ifndef FFTIMPLEMENTATIONCALLBACK_H
#define FFTIMPLEMENTATIONCALLBACK_H

/* Include Files */
#include <stddef.h>
#include <stdlib.h>
#include "rtwtypes.h"
#include "ac_feat_types.h"

/* Function Declarations */
extern void c_FFTImplementationCallback_dob(const emxArray_real_T *x, int n2blue,
  int nfft, const emxArray_real_T *costab, const emxArray_real_T *sintab, const
  emxArray_real_T *sintabinv, emxArray_creal_T *y);
extern void c_FFTImplementationCallback_gen(int nRows, bool useRadix2,
  emxArray_real_T *costab, emxArray_real_T *sintab, emxArray_real_T *sintabinv);
extern void c_FFTImplementationCallback_get(int nfft, bool useRadix2, int
  *n2blue, int *nRows);
extern void c_FFTImplementationCallback_r2b(const emxArray_real_T *x, int
  n1_unsigned, const emxArray_real_T *costab, const emxArray_real_T *sintab,
  emxArray_creal_T *y);
extern void d_FFTImplementationCallback_dob(const emxArray_real_T *x, int n2blue,
  int nfft, const emxArray_real_T *costab, const emxArray_real_T *sintab, const
  emxArray_real_T *sintabinv, emxArray_creal_T *y);
extern void e_FFTImplementationCallback_gen(int nRows, bool useRadix2,
  emxArray_real_T *costab, emxArray_real_T *sintab, emxArray_real_T *sintabinv);
extern void g_FFTImplementationCallback_r2b(const emxArray_real_T *x, int
  n1_unsigned, const emxArray_real_T *costab, const emxArray_real_T *sintab,
  emxArray_creal_T *y);

#endif

/*
 * File trailer for FFTImplementationCallback.h
 *
 * [EOF]
 */
