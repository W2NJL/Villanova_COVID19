/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: findpeaks.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "findpeaks.h"
#include "ac_feat.h"
#include "aceml_setop.h"
#include "rt_nonfinite.h"
#include <math.h>
#include <string.h>

/* Function Definitions */

/*
 * Arguments    : const double Yin_data[]
 *                double Ypk_data[]
 *                int Ypk_size[1]
 *                double Xpk_data[]
 *                int Xpk_size[1]
 * Return Type  : void
 */
void acfindpeaks(const double Yin_data[], double Ypk_data[], int Ypk_size[1],
               double Xpk_data[], int Xpk_size[1])
{
  int nPk;
  int nInf;
  char dir;
  int kfirst;
  double ykfirst;
  bool isinfykfirst;
  int k;
  double yk;
  int n;
  bool isinfyk;
  short iFinite_data[1002];
  int b_iFinite_data[1002];
  int iInfinite_data[1002];
  char previousdir;
  int iInfinite_size[1];
  int iPk_size[1];
  int iPk_data[1002];
  int c_data[2004];
  int c_size[1];
  int iInflect_data[1002];
  int iInflect_size[1];
  int iFinite_size[1];
  int b_iPk_data[2004];
  int idx_data[2004];
  nPk = -1;
  nInf = -1;
  dir = 'n';
  kfirst = -1;
  ykfirst = rtInf;
  isinfykfirst = true;
  for (k = 0; k < 1002; k++) {
    yk = Yin_data[k];
    if (rtIsNaN(Yin_data[k])) {
      yk = rtInf;
      isinfyk = true;
    } else if (rtIsInf(Yin_data[k]) && (Yin_data[k] > 0.0)) {
      isinfyk = true;
      nInf++;
      iInfinite_data[nInf] = k + 1;
    } else {
      isinfyk = false;
    }

    if (yk != ykfirst) {
      previousdir = dir;
      if (isinfyk || isinfykfirst) {
        dir = 'n';
      } else if (yk < ykfirst) {
        dir = 'd';
        if (('d' != previousdir) && (previousdir == 'i')) {
          nPk++;
          b_iFinite_data[nPk] = kfirst + 1;
        }
      } else {
        dir = 'i';
      }

      ykfirst = yk;
      kfirst = k;
      isinfykfirst = isinfyk;
    }
  }

  if (1 > nPk + 1) {
    nPk = -1;
  }

  n = nPk + 1;
  for (k = 0; k <= nPk; k++) {
    iFinite_data[k] = (short)b_iFinite_data[k];
  }

  for (k = 0; k < n; k++) {
    b_iFinite_data[k] = iFinite_data[k];
  }

  if (1 > nInf + 1) {
    nPk = -1;
  } else {
    nPk = nInf;
  }

  kfirst = nPk + 1;
  for (k = 0; k <= nPk; k++) {
    iFinite_data[k] = (short)iInfinite_data[k];
  }

  iInfinite_size[0] = kfirst;
  for (k = 0; k < kfirst; k++) {
    iInfinite_data[k] = iFinite_data[k];
  }

  nPk = 0;
  for (k = 0; k < n; k++) {
    ykfirst = Yin_data[b_iFinite_data[k] - 1];
    if ((ykfirst > rtMinusInf) && (ykfirst - fmax(Yin_data[b_iFinite_data[k] - 2],
          Yin_data[b_iFinite_data[k]]) >= 0.0)) {
      nPk++;
      iPk_data[nPk - 1] = b_iFinite_data[k];
    }
  }

  if (1 > nPk) {
    iPk_size[0] = 0;
  } else {
    iPk_size[0] = nPk;
  }

  acdo_vectors(iPk_data, iPk_size, iInfinite_data, iInfinite_size, c_data, c_size,
             iInflect_data, iInflect_size, b_iFinite_data, iFinite_size);
  if (c_size[0] < 1) {
    n = 0;
  } else {
    n = c_size[0];
  }

  if (n > 0) {
    b_iPk_data[0] = 1;
    kfirst = 1;
    for (k = 2; k <= n; k++) {
      kfirst++;
      b_iPk_data[k - 1] = kfirst;
    }
  }

  if (0 <= n - 1) {
    memcpy(&idx_data[0], &b_iPk_data[0], n * sizeof(int));
  }

  if (n > 1002) {
    memcpy(&b_iPk_data[0], &idx_data[0], 1002U * sizeof(int));
    n = 1002;
    memcpy(&idx_data[0], &b_iPk_data[0], 1002U * sizeof(int));
  }

  for (k = 0; k < n; k++) {
    b_iPk_data[k] = c_data[idx_data[k] - 1];
  }

  Ypk_size[0] = n;
  for (k = 0; k < n; k++) {
    Ypk_data[k] = Yin_data[b_iPk_data[k] - 1];
  }

  Xpk_size[0] = n;
  for (k = 0; k < n; k++) {
    Xpk_data[k] = (short)((short)(b_iPk_data[k] - 1) + 1);
  }
}

/*
 * File trailer for findpeaks.c
 *
 * [EOF]
 */
