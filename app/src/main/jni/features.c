/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: features.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "features.h"
#include "ac_feat.h"
#include "ac_feat_rtwutil.h"
#include "kurtosis.h"
#include "mvksdensity.h"
#include "rt_nonfinite.h"
#include "skewness.h"
#include "vvarstd.h"
#include <math.h>

/* Function Definitions */

/*
 * Compute Skewness and Kurtosis of the input respiratory phase data
 * Arguments    : const emxArray_real_T *y_raw
 *                double *entropy_dat1
 *                double *Sk
 *                double *Ku
 * Return Type  : void
 */
void features(const emxArray_real_T *y_raw, double *entropy_dat1, double *Sk,
              double *Ku)
{
  double fout0[100];
  double xout[100];
  double u;
  int k;
  int eint;
  *Sk = skewness(y_raw);
  *Ku = kurtosis(y_raw);

  /*  Compute the standard deviation of the respiratory phase data */
  /*  Compute the bandwidth for the Gaussian Kernal estimation of the Pdf */
  /*  Compute the pdf of the respiratory phase  */
  mvksdensity(y_raw, 1.06 * vvarstd(y_raw, y_raw->size[0]) * acrt_powd_snf
              (y_raw->size[0], -0.2), fout0, xout, &u);

  /*  Compute the entropy of the respiratory data from the pdf computed */
  /*  above. */
  for (k = 0; k < 100; k++) {
    if (fout0[k] == 0.0) {
      xout[k] = rtMinusInf;
    } else if (fout0[k] < 0.0) {
      xout[k] = rtNaN;
    } else if ((!rtIsInf(fout0[k])) && (!rtIsNaN(fout0[k]))) {
      u = frexp(fout0[k], &eint);
      if (u == 0.5) {
        xout[k] = (double)eint - 1.0;
      } else if ((eint == 1) && (u < 0.75)) {
        xout[k] = log(2.0 * u) / 0.69314718055994529;
      } else {
        xout[k] = log(u) / 0.69314718055994529 + (double)eint;
      }
    } else {
      xout[k] = fout0[k];
    }
  }

  for (eint = 0; eint < 100; eint++) {
    fout0[eint] *= xout[eint];
  }

  u = fout0[0];
  for (k = 0; k < 99; k++) {
    u += fout0[k + 1];
  }

  *entropy_dat1 = -u;
}

/*
 * File trailer for features.c
 *
 * [EOF]
 */
