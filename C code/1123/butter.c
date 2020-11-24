/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: butter.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 05-Nov-2020 13:57:38
 */

/* Include Files */
#include "butter.h"
#include "filtering.h"
#include "filtering_rtwutil.h"
#include "lp2hp.h"
#include "rt_nonfinite.h"
#include "xdhseqr.h"
#include "xdlanv2.h"
#include "xgehrd.h"
#include "xzgeev.h"
#include "xzgetrf.h"
#include <math.h>
#include <string.h>

/* Function Definitions */

/*
 * Arguments    : double Wn
 *                double varargout_1[7]
 *                double varargout_2[7]
 * Return Type  : void
 */
void butter(double Wn, double varargout_1[7], double varargout_2[7])
{
  double a[36];
  int i;
  int d;
  double c[6];
  double b[6];
  double ad[36];
  double bd[6];
  double cd[6];
  double wn;
  creal_T b_c[3];
  static const creal_T dcv[6] = { { -0.96592582628906831,/* re */
      -0.25881904510252057             /* im */
    }, { -0.96592582628906831,         /* re */
      0.25881904510252057              /* im */
    }, { -0.70710678118654746,         /* re */
      -0.70710678118654757             /* im */
    }, { -0.70710678118654746,         /* re */
      0.70710678118654757              /* im */
    }, { -0.25881904510252063,         /* re */
      -0.96592582628906831             /* im */
    }, { -0.25881904510252063,         /* re */
      0.96592582628906831              /* im */
    } };

  double re;
  int k;
  int b_i;
  double b_d;
  int ipiv[6];
  int jBcol;
  double T[36];
  double b1_idx_1;
  int j;
  double s;
  double b_a[4];
  bool p;
  int kAcol;
  int a_tmp;
  bool exitg2;
  creal_T alpha1[6];
  double a1[4];
  creal_T c_c[7];
  creal_T beta1[6];
  int exitg1;
  static const creal_T c_a[7] = { { 1.0,/* re */
      0.0                              /* im */
    }, { -1.0,                         /* re */
      -1.2246467991473532E-16          /* im */
    }, { 1.0,                          /* re */
      2.4492935982947064E-16           /* im */
    }, { -1.0,                         /* re */
      -3.6739403974420594E-16          /* im */
    }, { 1.0,                          /* re */
      4.8985871965894128E-16           /* im */
    }, { -1.0,                         /* re */
      -6.1232339957367663E-16          /* im */
    }, { 1.0,                          /* re */
      7.3478807948841188E-16           /* im */
    } };

  static const signed char d_a[7] = { 1, -6, 15, -20, 15, -6, 1 };

  memset(&a[0], 0, 36U * sizeof(double));
  for (i = 0; i < 6; i++) {
    c[i] = 0.0;
  }

  d = 1;
  for (i = 0; i <= 5; i += 2) {
    b_c[1].re = -dcv[i].re - -dcv[i].im * 0.0;
    re = dcv[i + 1].re;
    wn = dcv[i + 1].im;
    b_c[2].re = -re * b_c[1].re - -wn * -dcv[i].im;
    b_d = b_c[1].re;
    for (k = 2; k >= 2; k--) {
      b_d -= re - wn * 0.0;
    }

    wn = sqrt(rt_hypotd_snf(dcv[i].re, dcv[i].im) * rt_hypotd_snf(re, wn));
    b1_idx_1 = 1.0 / wn;
    wn = 1.0 / wn;
    s = (1.0 - -b_d * 0.0) / wn;
    b_a[1] = s;
    b_a[0] = -b_d - s * 0.0;
    s = (0.0 - -b_c[2].re * 0.0) / wn;
    b_a[3] = s;
    b_a[2] = -b_c[2].re - s * 0.0;
    for (b_i = 0; b_i < 2; b_i++) {
      b_d = b_a[b_i + 2];
      a1[b_i] = b_a[b_i] + b_d * 0.0;
      a1[b_i + 2] = b_a[b_i] * 0.0 + b_d * b1_idx_1;
    }

    if (i - 1 == -1) {
      a[0] = a1[0];
      a[1] = a1[1];
      c[0] = 0.0;
      a[6] = a1[2];
      a[7] = a1[3];
      c[1] = wn;
    } else {
      jBcol = i + 6 * i;
      kAcol = i + 6 * (i + 1);
      for (b_i = 0; b_i < i; b_i++) {
        a_tmp = i + 6 * b_i;
        a[a_tmp] = c[b_i];
        a[a_tmp + 1] = 0.0 * c[b_i];
        c[b_i] *= 0.0;
      }

      a[jBcol] = a1[0];
      a[jBcol + 1] = a1[1];
      a[kAcol] = a1[2];
      a[kAcol + 1] = a1[3];
      c[i] = 0.0;
      c[i + 1] = wn;
    }

    b[i] = d;
    b[i + 1] = 0.0;
    d = 0;
  }

  lp2hp(a, b, c, d, 4.0 * tan(3.1415926535897931 * Wn / 2.0), ad, bd, cd, &wn);
  memset(&a[0], 0, 36U * sizeof(double));
  for (k = 0; k < 6; k++) {
    a[k + 6 * k] = 1.0;
  }

  for (b_i = 0; b_i < 36; b_i++) {
    b_d = ad[b_i] * 0.5 / 2.0;
    ad[b_i] = b_d;
    T[b_i] = a[b_i] + b_d;
    a[b_i] -= b_d;
  }

  xzgetrf(a, ipiv, &jBcol);
  for (i = 0; i < 5; i++) {
    if (ipiv[i] != i + 1) {
      for (j = 0; j < 6; j++) {
        jBcol = i + 6 * j;
        wn = T[jBcol];
        d = (ipiv[i] + 6 * j) - 1;
        T[jBcol] = T[d];
        T[d] = wn;
      }
    }
  }

  for (j = 0; j < 6; j++) {
    jBcol = 6 * j;
    for (k = 0; k < 6; k++) {
      kAcol = 6 * k;
      b_i = k + jBcol;
      if (T[b_i] != 0.0) {
        a_tmp = k + 2;
        for (i = a_tmp; i < 7; i++) {
          d = (i + jBcol) - 1;
          T[d] -= T[b_i] * a[(i + kAcol) - 1];
        }
      }
    }
  }

  for (j = 0; j < 6; j++) {
    jBcol = 6 * j;
    for (k = 5; k >= 0; k--) {
      kAcol = 6 * k;
      b_i = k + jBcol;
      if (T[b_i] != 0.0) {
        T[b_i] /= a[k + kAcol];
        for (i = 0; i < k; i++) {
          d = i + jBcol;
          T[d] -= T[b_i] * a[i + kAcol];
        }
      }
    }
  }

  p = true;
  for (k = 0; k < 36; k++) {
    if ((!p) || (rtIsInf(T[k]) || rtIsNaN(T[k]))) {
      p = false;
    }
  }

  if (!p) {
    for (i = 0; i < 6; i++) {
      alpha1[i].re = rtNaN;
      alpha1[i].im = 0.0;
    }
  } else {
    p = true;
    j = 0;
    exitg2 = false;
    while ((!exitg2) && (j < 6)) {
      i = 0;
      do {
        exitg1 = 0;
        if (i <= j) {
          if (!(T[i + 6 * j] == T[j + 6 * i])) {
            p = false;
            exitg1 = 1;
          } else {
            i++;
          }
        } else {
          j++;
          exitg1 = 2;
        }
      } while (exitg1 == 0);

      if (exitg1 == 1) {
        exitg2 = true;
      }
    }

    if (p) {
      p = true;
      for (k = 0; k < 36; k++) {
        if ((!p) || (rtIsInf(T[k]) || rtIsNaN(T[k]))) {
          p = false;
        }
      }

      if (!p) {
        for (b_i = 0; b_i < 36; b_i++) {
          T[b_i] = rtNaN;
        }

        jBcol = 2;
        for (j = 0; j < 5; j++) {
          if (jBcol <= 6) {
            memset(&T[(j * 6 + jBcol) + -1], 0, (7 - jBcol) * sizeof(double));
          }

          jBcol++;
        }
      } else {
        xgehrd(T);
        eml_dlahqr(T);
        jBcol = 4;
        for (j = 0; j < 3; j++) {
          if (jBcol <= 6) {
            memset(&T[(j * 6 + jBcol) + -1], 0, (7 - jBcol) * sizeof(double));
          }

          jBcol++;
        }
      }

      for (k = 0; k < 6; k++) {
        alpha1[k].re = T[k + 6 * k];
        alpha1[k].im = 0.0;
      }
    } else {
      xzgeev(T, &jBcol, alpha1, beta1);
      for (b_i = 0; b_i < 6; b_i++) {
        if (beta1[b_i].im == 0.0) {
          if (alpha1[b_i].im == 0.0) {
            re = alpha1[b_i].re / beta1[b_i].re;
            wn = 0.0;
          } else if (alpha1[b_i].re == 0.0) {
            re = 0.0;
            wn = alpha1[b_i].im / beta1[b_i].re;
          } else {
            re = alpha1[b_i].re / beta1[b_i].re;
            wn = alpha1[b_i].im / beta1[b_i].re;
          }
        } else if (beta1[b_i].re == 0.0) {
          if (alpha1[b_i].re == 0.0) {
            re = alpha1[b_i].im / beta1[b_i].im;
            wn = 0.0;
          } else if (alpha1[b_i].im == 0.0) {
            re = 0.0;
            wn = -(alpha1[b_i].re / beta1[b_i].im);
          } else {
            re = alpha1[b_i].im / beta1[b_i].im;
            wn = -(alpha1[b_i].re / beta1[b_i].im);
          }
        } else {
          b1_idx_1 = fabs(beta1[b_i].re);
          wn = fabs(beta1[b_i].im);
          if (b1_idx_1 > wn) {
            s = beta1[b_i].im / beta1[b_i].re;
            wn = beta1[b_i].re + s * beta1[b_i].im;
            re = (alpha1[b_i].re + s * alpha1[b_i].im) / wn;
            wn = (alpha1[b_i].im - s * alpha1[b_i].re) / wn;
          } else if (wn == b1_idx_1) {
            if (beta1[b_i].re > 0.0) {
              wn = 0.5;
            } else {
              wn = -0.5;
            }

            if (beta1[b_i].im > 0.0) {
              s = 0.5;
            } else {
              s = -0.5;
            }

            re = (alpha1[b_i].re * wn + alpha1[b_i].im * s) / b1_idx_1;
            wn = (alpha1[b_i].im * wn - alpha1[b_i].re * s) / b1_idx_1;
          } else {
            s = beta1[b_i].re / beta1[b_i].im;
            wn = beta1[b_i].im + s * beta1[b_i].re;
            re = (s * alpha1[b_i].re + alpha1[b_i].im) / wn;
            wn = (s * alpha1[b_i].im - alpha1[b_i].re) / wn;
          }
        }

        alpha1[b_i].re = re;
        alpha1[b_i].im = wn;
      }
    }
  }

  c_c[0].re = 1.0;
  c_c[0].im = 0.0;
  for (j = 0; j < 6; j++) {
    b_d = c_c[j].re;
    c_c[j + 1].re = -alpha1[j].re * c_c[j].re - -alpha1[j].im * c_c[j].im;
    c_c[j + 1].im = -alpha1[j].re * c_c[j].im + -alpha1[j].im * b_d;
    for (k = j + 1; k >= 2; k--) {
      wn = alpha1[j].re * c_c[k - 2].im + alpha1[j].im * c_c[k - 2].re;
      c_c[k - 1].re -= alpha1[j].re * c_c[k - 2].re - alpha1[j].im * c_c[k - 2].
        im;
      c_c[k - 1].im -= wn;
    }
  }

  wn = 0.0;
  s = 0.0;
  for (b_i = 0; b_i < 7; b_i++) {
    varargout_2[b_i] = c_c[b_i].re;
    wn += (double)(int)c_a[b_i].re * c_c[b_i].re - c_a[b_i].im * 0.0;
    s += (double)(int)c_a[b_i].re * 0.0 + c_a[b_i].im * c_c[b_i].re;
  }

  for (b_i = 0; b_i < 7; b_i++) {
    varargout_1[b_i] = ((double)d_a[b_i] * wn + 3.6739403974420594E-16 *
                        ((double)d_a[b_i] * s)) / 64.0;
  }
}

/*
 * File trailer for butter.c
 *
 * [EOF]
 */
