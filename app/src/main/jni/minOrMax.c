/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: minOrMax.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Oct-2020 11:12:36
 */

/* Include Files */
#include "minOrMax.h"
#include "features.h"
#include "rt_nonfinite.h"
#include <math.h>

/* Function Definitions */

/*
 * Arguments    : double x
 *                const double y_data[]
 *                const int y_size[1]
 *                double ex_data[]
 *                int ex_size[1]
 * Return Type  : void
 */
void b_maximum2(double x, const double y_data[], const int y_size[1], double
                ex_data[], int ex_size[1])
{
  int nx;
  int k;
  ex_size[0] = y_size[0];
  nx = y_size[0];
  for (k = 0; k < nx; k++) {
    ex_data[k] = fmax(x, y_data[k]);
  }
}

/*
 * Arguments    : const emxArray_real_T *x
 * Return Type  : double
 */
double maximum(const emxArray_real_T *x)
{
  double ex;
  int n;
  int idx;
  int k;
  bool exitg1;
  double d;
  n = x->size[0];
  if (x->size[0] <= 2) {
    if (x->size[0] == 1) {
      ex = x->data[0];
    } else if ((x->data[0] < x->data[1]) || (rtIsNaN(x->data[0]) && (!rtIsNaN
                 (x->data[1])))) {
      ex = x->data[1];
    } else {
      ex = x->data[0];
    }
  } else {
    if (!rtIsNaN(x->data[0])) {
      idx = 1;
    } else {
      idx = 0;
      k = 2;
      exitg1 = false;
      while ((!exitg1) && (k <= x->size[0])) {
        if (!rtIsNaN(x->data[k - 1])) {
          idx = k;
          exitg1 = true;
        } else {
          k++;
        }
      }
    }

    if (idx == 0) {
      ex = x->data[0];
    } else {
      ex = x->data[idx - 1];
      idx++;
      for (k = idx; k <= n; k++) {
        d = x->data[k - 1];
        if (ex < d) {
          ex = d;
        }
      }
    }
  }

  return ex;
}

/*
 * Arguments    : const double y_data[]
 *                const int y_size[1]
 *                double ex_data[]
 *                int ex_size[1]
 * Return Type  : void
 */
void maximum2(const double y_data[], const int y_size[1], double ex_data[], int
              ex_size[1])
{
  int nx;
  int k;
  ex_size[0] = y_size[0];
  nx = y_size[0];
  for (k = 0; k < nx; k++) {
    ex_data[k] = fmax(0.0, y_data[k]);
  }
}

/*
 * Arguments    : const emxArray_real_T *x
 * Return Type  : double
 */
double minimum(const emxArray_real_T *x)
{
  double ex;
  int n;
  int idx;
  int k;
  bool exitg1;
  double d;
  n = x->size[0];
  if (x->size[0] <= 2) {
    if (x->size[0] == 1) {
      ex = x->data[0];
    } else if ((x->data[0] > x->data[1]) || (rtIsNaN(x->data[0]) && (!rtIsNaN
                 (x->data[1])))) {
      ex = x->data[1];
    } else {
      ex = x->data[0];
    }
  } else {
    if (!rtIsNaN(x->data[0])) {
      idx = 1;
    } else {
      idx = 0;
      k = 2;
      exitg1 = false;
      while ((!exitg1) && (k <= x->size[0])) {
        if (!rtIsNaN(x->data[k - 1])) {
          idx = k;
          exitg1 = true;
        } else {
          k++;
        }
      }
    }

    if (idx == 0) {
      ex = x->data[0];
    } else {
      ex = x->data[idx - 1];
      idx++;
      for (k = idx; k <= n; k++) {
        d = x->data[k - 1];
        if (ex > d) {
          ex = d;
        }
      }
    }
  }

  return ex;
}

/*
 * Arguments    : const double x_data[]
 *                const int x_size[1]
 *                double ex_data[]
 *                int ex_size[1]
 * Return Type  : void
 */
void minimum2(const double x_data[], const int x_size[1], double ex_data[], int
              ex_size[1])
{
  int nx;
  int k;
  ex_size[0] = x_size[0];
  nx = x_size[0];
  for (k = 0; k < nx; k++) {
    ex_data[k] = fmin(x_data[k], rtInf);
  }
}

/*
 * File trailer for minOrMax.c
 *
 * [EOF]
 */
