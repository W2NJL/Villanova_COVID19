/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: lp2hp.h
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 05-Nov-2020 13:57:38
 */

#ifndef LP2HP_H
#define LP2HP_H

/* Include Files */
#include <stddef.h>
#include <stdlib.h>
#include "rtwtypes.h"
#include "filtering_types.h"

/* Function Declarations */
extern void lp2hp(const double a[36], const double b[6], const double c[6],
                  double d, double wo, double at[36], double bt[6], double ct[6],
                  double *dt);

#endif

/*
 * File trailer for lp2hp.h
 *
 * [EOF]
 */
