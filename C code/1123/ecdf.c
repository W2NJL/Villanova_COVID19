/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: ecdf.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "ecdf.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "diff.h"
#include "nullAssignment.h"
#include "rt_nonfinite.h"
#include "featsort.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *y
 *                const emxArray_boolean_T *varargin_2
 *                const emxArray_real_T *varargin_4
 *                emxArray_real_T *Fout
 *                emxArray_real_T *x
 * Return Type  : void
 */
void ecdf(const emxArray_real_T *y, const emxArray_boolean_T *varargin_2, const
          emxArray_real_T *varargin_4, emxArray_real_T *Fout, emxArray_real_T *x)
{
  emxArray_boolean_T *dInd;
  int i;
  int idx;
  emxArray_boolean_T *r;
  int n;
  int trueCount;
  int b_i;
  emxArray_int32_T *r1;
  emxArray_boolean_T *cens;
  emxArray_real_T *freq;
  emxArray_int32_T *iidx;
  emxArray_real_T *totcumfreq;
  emxArray_boolean_T *isDiff;
  emxArray_real_T *r2;
  bool b_y;
  bool exitg1;
  emxArray_real_T *D;
  emxArray_int32_T *r3;
  emxArray_real_T *b_x;
  double ex;
  double d;
  acemxInit_boolean_T(&dInd, 1);
  i = dInd->size[0];
  dInd->size[0] = y->size[0];
  acemxEnsureCapacity_boolean_T(dInd, i);
  idx = y->size[0];
  for (i = 0; i < idx; i++) {
    dInd->data[i] = rtIsNaN(y->data[i]);
  }

  acemxInit_boolean_T(&r, 1);
  i = r->size[0];
  r->size[0] = varargin_4->size[1];
  acemxEnsureCapacity_boolean_T(r, i);
  idx = varargin_4->size[1];
  for (i = 0; i < idx; i++) {
    r->data[i] = rtIsNaN(varargin_4->data[i]);
  }

  idx = dInd->size[0];
  for (i = 0; i < idx; i++) {
    dInd->data[i] = ((!dInd->data[i]) && (!r->data[i]));
  }

  i = r->size[0];
  r->size[0] = varargin_4->size[1];
  acemxEnsureCapacity_boolean_T(r, i);
  idx = varargin_4->size[1];
  for (i = 0; i < idx; i++) {
    r->data[i] = (varargin_4->data[i] > 0.0);
  }

  n = dInd->size[0] - 1;
  trueCount = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (dInd->data[b_i] && r->data[b_i]) {
      trueCount++;
    }
  }

  acemxInit_int32_T(&r1, 1);
  i = r1->size[0];
  r1->size[0] = trueCount;
  acemxEnsureCapacity_int32_T(r1, i);
  idx = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (dInd->data[b_i] && r->data[b_i]) {
      r1->data[idx] = b_i + 1;
      idx++;
    }
  }

  n = dInd->size[0] - 1;
  trueCount = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (dInd->data[b_i] && r->data[b_i]) {
      trueCount++;
    }
  }

  acemxInit_boolean_T(&cens, 1);
  i = cens->size[0];
  cens->size[0] = trueCount;
  acemxEnsureCapacity_boolean_T(cens, i);
  idx = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (dInd->data[b_i] && r->data[b_i]) {
      cens->data[idx] = varargin_2->data[b_i];
      idx++;
    }
  }

  n = dInd->size[0] - 1;
  trueCount = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (dInd->data[b_i] && r->data[b_i]) {
      trueCount++;
    }
  }

  acemxInit_real_T(&freq, 1);
  i = freq->size[0];
  freq->size[0] = trueCount;
  acemxEnsureCapacity_real_T(freq, i);
  idx = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (dInd->data[b_i] && r->data[b_i]) {
      freq->data[idx] = varargin_4->data[b_i];
      idx++;
    }
  }

  acemxFree_boolean_T(&r);
  i = x->size[0];
  x->size[0] = r1->size[0];
  acemxEnsureCapacity_real_T(x, i);
  idx = r1->size[0];
  for (i = 0; i < idx; i++) {
    x->data[i] = y->data[r1->data[i] - 1];
  }

  acemxFree_int32_T(&r1);
  acemxInit_int32_T(&iidx, 1);
  acb_sort(x, iidx);
  i = dInd->size[0];
  dInd->size[0] = iidx->size[0];
  acemxEnsureCapacity_boolean_T(dInd, i);
  idx = iidx->size[0];
  for (i = 0; i < idx; i++) {
    dInd->data[i] = cens->data[iidx->data[i] - 1];
  }

  i = cens->size[0];
  cens->size[0] = dInd->size[0];
  acemxEnsureCapacity_boolean_T(cens, i);
  idx = dInd->size[0];
  for (i = 0; i < idx; i++) {
    cens->data[i] = dInd->data[i];
  }

  acemxInit_real_T(&totcumfreq, 1);
  i = totcumfreq->size[0];
  totcumfreq->size[0] = iidx->size[0];
  acemxEnsureCapacity_real_T(totcumfreq, i);
  idx = iidx->size[0];
  for (i = 0; i < idx; i++) {
    totcumfreq->data[i] = freq->data[iidx->data[i] - 1];
  }

  acemxFree_int32_T(&iidx);
  i = freq->size[0];
  freq->size[0] = totcumfreq->size[0];
  acemxEnsureCapacity_real_T(freq, i);
  idx = totcumfreq->size[0];
  for (i = 0; i < idx; i++) {
    freq->data[i] = totcumfreq->data[i];
  }

  if ((freq->size[0] != 1) && (freq->size[0] != 0) && (freq->size[0] != 1)) {
    i = freq->size[0];
    for (b_i = 0; b_i <= i - 2; b_i++) {
      totcumfreq->data[b_i + 1] += totcumfreq->data[b_i];
    }
  }

  idx = freq->size[0];
  for (i = 0; i < idx; i++) {
    freq->data[i] *= (double)!cens->data[i];
  }

  acemxFree_boolean_T(&cens);
  if ((freq->size[0] != 1) && (freq->size[0] != 0) && (freq->size[0] != 1)) {
    i = freq->size[0];
    for (b_i = 0; b_i <= i - 2; b_i++) {
      freq->data[b_i + 1] += freq->data[b_i];
    }
  }

  acemxInit_boolean_T(&isDiff, 1);
  acemxInit_real_T(&r2, 1);
  diff(x, r2);
  i = isDiff->size[0];
  isDiff->size[0] = r2->size[0];
  acemxEnsureCapacity_boolean_T(isDiff, i);
  idx = r2->size[0];
  for (i = 0; i < idx; i++) {
    isDiff->data[i] = (r2->data[i] == 0.0);
  }

  b_y = false;
  idx = 1;
  exitg1 = false;
  while ((!exitg1) && (idx <= isDiff->size[0])) {
    if (!isDiff->data[idx - 1]) {
      idx++;
    } else {
      b_y = true;
      exitg1 = true;
    }
  }

  if (b_y) {
    i = r2->size[0];
    r2->size[0] = x->size[0];
    acemxEnsureCapacity_real_T(r2, i);
    idx = x->size[0];
    for (i = 0; i < idx; i++) {
      r2->data[i] = x->data[i];
    }

    nullAssignment(r2, isDiff);
    i = x->size[0];
    x->size[0] = r2->size[0];
    acemxEnsureCapacity_real_T(x, i);
    idx = r2->size[0];
    for (i = 0; i < idx; i++) {
      x->data[i] = r2->data[i];
    }

    nullAssignment(totcumfreq, isDiff);
    nullAssignment(freq, isDiff);
  }

  acemxFree_boolean_T(&isDiff);
  acemxInit_real_T(&D, 1);
  diff(freq, r2);
  i = D->size[0];
  D->size[0] = r2->size[0] + 1;
  acemxEnsureCapacity_real_T(D, i);
  D->data[0] = freq->data[0];
  idx = r2->size[0];
  for (i = 0; i < idx; i++) {
    D->data[i + 1] = r2->data[i];
  }

  acemxFree_real_T(&r2);
  if (1 > totcumfreq->size[0] - 1) {
    idx = 1;
  } else {
    idx = totcumfreq->size[0];
  }

  i = freq->size[0];
  freq->size[0] = idx;
  acemxEnsureCapacity_real_T(freq, i);
  freq->data[0] = totcumfreq->data[totcumfreq->size[0] - 1];
  for (i = 0; i <= idx - 2; i++) {
    freq->data[i + 1] = totcumfreq->data[totcumfreq->size[0] - 1] -
      totcumfreq->data[i];
  }

  acemxFree_real_T(&totcumfreq);
  i = dInd->size[0];
  dInd->size[0] = D->size[0];
  acemxEnsureCapacity_boolean_T(dInd, i);
  idx = D->size[0];
  for (i = 0; i < idx; i++) {
    dInd->data[i] = (D->data[i] > 0.0);
  }

  n = D->size[0] - 1;
  trueCount = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (D->data[b_i] > 0.0) {
      trueCount++;
    }
  }

  acemxInit_int32_T(&r3, 1);
  i = r3->size[0];
  r3->size[0] = trueCount;
  acemxEnsureCapacity_int32_T(r3, i);
  idx = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (D->data[b_i] > 0.0) {
      r3->data[idx] = b_i + 1;
      idx++;
    }
  }

  acemxInit_real_T(&b_x, 1);
  i = b_x->size[0];
  b_x->size[0] = r3->size[0];
  acemxEnsureCapacity_real_T(b_x, i);
  idx = r3->size[0];
  for (i = 0; i < idx; i++) {
    b_x->data[i] = x->data[r3->data[i] - 1];
  }

  acemxFree_int32_T(&r3);
  i = x->size[0];
  x->size[0] = b_x->size[0];
  acemxEnsureCapacity_real_T(x, i);
  idx = b_x->size[0];
  for (i = 0; i < idx; i++) {
    x->data[i] = b_x->data[i];
  }

  n = D->size[0] - 1;
  trueCount = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (D->data[b_i] > 0.0) {
      trueCount++;
    }
  }

  idx = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (D->data[b_i] > 0.0) {
      D->data[idx] = D->data[b_i];
      idx++;
    }
  }

  i = D->size[0];
  D->size[0] = trueCount;
  acemxEnsureCapacity_real_T(D, i);
  n = dInd->size[0] - 1;
  idx = 0;
  for (b_i = 0; b_i <= n; b_i++) {
    if (dInd->data[b_i]) {
      freq->data[idx] = freq->data[b_i];
      idx++;
    }
  }

  acemxFree_boolean_T(&dInd);
  i = freq->size[0];
  freq->size[0] = trueCount;
  acemxEnsureCapacity_real_T(freq, i);
  for (i = 0; i < trueCount; i++) {
    freq->data[i] = 1.0 - D->data[i] / freq->data[i];
  }

  acemxFree_real_T(&D);
  if ((freq->size[0] != 1) && (freq->size[0] != 0) && (freq->size[0] != 1)) {
    i = freq->size[0];
    for (b_i = 0; b_i <= i - 2; b_i++) {
      freq->data[b_i + 1] *= freq->data[b_i];
    }
  }

  idx = freq->size[0];
  for (i = 0; i < idx; i++) {
    freq->data[i] = 1.0 - freq->data[i];
  }

  if (trueCount != 0) {
    n = y->size[0];
    if (y->size[0] <= 2) {
      if (y->size[0] == 1) {
        ex = y->data[0];
      } else if ((y->data[0] > y->data[1]) || (rtIsNaN(y->data[0]) && (!rtIsNaN
                   (y->data[1])))) {
        ex = y->data[1];
      } else {
        ex = y->data[0];
      }
    } else {
      if (!rtIsNaN(y->data[0])) {
        idx = 1;
      } else {
        idx = 0;
        b_i = 2;
        exitg1 = false;
        while ((!exitg1) && (b_i <= y->size[0])) {
          if (!rtIsNaN(y->data[b_i - 1])) {
            idx = b_i;
            exitg1 = true;
          } else {
            b_i++;
          }
        }
      }

      if (idx == 0) {
        ex = y->data[0];
      } else {
        ex = y->data[idx - 1];
        i = idx + 1;
        for (b_i = i; b_i <= n; b_i++) {
          d = y->data[b_i - 1];
          if (ex > d) {
            ex = d;
          }
        }
      }
    }

    i = b_x->size[0];
    b_x->size[0] = x->size[0] + 1;
    acemxEnsureCapacity_real_T(b_x, i);
    b_x->data[0] = ex;
    idx = x->size[0];
    for (i = 0; i < idx; i++) {
      b_x->data[i + 1] = x->data[i];
    }

    i = x->size[0];
    x->size[0] = b_x->size[0];
    acemxEnsureCapacity_real_T(x, i);
    idx = b_x->size[0];
    for (i = 0; i < idx; i++) {
      x->data[i] = b_x->data[i];
    }

    i = Fout->size[0];
    Fout->size[0] = freq->size[0] + 1;
    acemxEnsureCapacity_real_T(Fout, i);
    Fout->data[0] = 0.0;
    idx = freq->size[0];
    for (i = 0; i < idx; i++) {
      Fout->data[i + 1] = freq->data[i];
    }
  } else {
    i = Fout->size[0];
    Fout->size[0] = freq->size[0];
    acemxEnsureCapacity_real_T(Fout, i);
    idx = freq->size[0];
    for (i = 0; i < idx; i++) {
      Fout->data[i] = freq->data[i];
    }
  }

  acemxFree_real_T(&b_x);
  acemxFree_real_T(&freq);
}

/*
 * File trailer for ecdf.c
 *
 * [EOF]
 */
