/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: vvarstd.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "vvarstd.h"
#include "ac_feat.h"
#include "rt_nonfinite.h"
#include <math.h>

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *v
 *                int n
 * Return Type  : double
 */
double vvarstd(const emxArray_real_T *v, int n)
{
  double s;
  double xbar;
  int k;
  double scale;
  double d;
  double t;
  if (n == 0) {
    s = rtNaN;
  } else if (n == 1) {
    if ((!rtIsInf(v->data[0])) && (!rtIsNaN(v->data[0]))) {
      s = 0.0;
    } else {
      s = rtNaN;
    }
  } else {
    if (v->size[0] == 0) {
      xbar = 0.0;
    } else {
      xbar = v->data[0];
      for (k = 2; k <= n; k++) {
        xbar += v->data[k - 1];
      }
    }

    xbar /= (double)n;
    s = 0.0;
    scale = 3.3121686421112381E-170;
    for (k = 0; k < n; k++) {
      d = fabs(v->data[k] - xbar);
      if (d > scale) {
        t = scale / d;
        s = s * t * t + 1.0;
        scale = d;
      } else {
        t = d / scale;
        s += t * t;
      }
    }

    s = scale * sqrt(s);
    s /= sqrt(n - 1);
  }

  return s;
}

/*
 * File trailer for vvarstd.c
 *
 * [EOF]
 */
