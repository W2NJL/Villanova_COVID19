/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: FFTImplementationCallback.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "FFTImplementationCallback.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "rt_nonfinite.h"
#include <math.h>

/* Function Declarations */
static void c_FFTImplementationCallback_doH(const emxArray_real_T *x,
  emxArray_creal_T *y, int unsigned_nRows, const emxArray_real_T *costab, const
  emxArray_real_T *sintab);
static void d_FFTImplementationCallback_doH(const emxArray_real_T *x,
  emxArray_creal_T *y, int nrowsx, int nRows, int nfft, const emxArray_creal_T
  *wwc, const emxArray_real_T *costab, const emxArray_real_T *sintab, const
  emxArray_real_T *costabinv, const emxArray_real_T *sintabinv);
static void d_FFTImplementationCallback_gen(int nRows, emxArray_real_T *costab,
  emxArray_real_T *sintab, emxArray_real_T *sintabinv);
static void d_FFTImplementationCallback_get(const emxArray_real_T *costab, const
  emxArray_real_T *sintab, emxArray_real_T *hcostab, emxArray_real_T *hsintab);
static void d_FFTImplementationCallback_r2b(const emxArray_creal_T *x, int
  unsigned_nRows, const emxArray_real_T *costab, const emxArray_real_T *sintab,
  emxArray_creal_T *y);
static void e_FFTImplementationCallback_doH(const emxArray_real_T *x,
  emxArray_creal_T *y, int nrowsx, int nRows, int nfft, const emxArray_creal_T
  *wwc, const emxArray_real_T *costab, const emxArray_real_T *sintab, const
  emxArray_real_T *costabinv, const emxArray_real_T *sintabinv);
static void e_FFTImplementationCallback_get(int nRowsM1, int nfftLen,
  emxArray_int32_T *bitrevIndex);
static void e_FFTImplementationCallback_r2b(const emxArray_creal_T *x, int
  n1_unsigned, const emxArray_real_T *costab, const emxArray_real_T *sintab,
  emxArray_creal_T *y);
static void f_FFTImplementationCallback_get(const emxArray_real_T *costab, const
  emxArray_real_T *sintab, const emxArray_real_T *costabinv, const
  emxArray_real_T *sintabinv, emxArray_real_T *hcostab, emxArray_real_T *hsintab,
  emxArray_real_T *hcostabinv, emxArray_real_T *hsintabinv);
static void f_FFTImplementationCallback_r2b(const emxArray_creal_T *x, int
  n1_unsigned, const emxArray_real_T *costab, const emxArray_real_T *sintab,
  emxArray_creal_T *y);
static void g_FFTImplementationCallback_get(emxArray_creal_T *y, const
  emxArray_creal_T *reconVar1, const emxArray_creal_T *reconVar2, const
  emxArray_int32_T *wrapIndex, int hnRows);

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *x
 *                emxArray_creal_T *y
 *                int unsigned_nRows
 *                const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 * Return Type  : void
 */
static void c_FFTImplementationCallback_doH(const emxArray_real_T *x,
  emxArray_creal_T *y, int unsigned_nRows, const emxArray_real_T *costab, const
  emxArray_real_T *sintab)
{
  emxArray_int32_T *wrapIndex;
  emxArray_real_T *hcostab;
  emxArray_real_T *hsintab;
  emxArray_creal_T *reconVar1;
  emxArray_creal_T *reconVar2;
  int nRows;
  int istart;
  int nRowsD2;
  int k;
  int j;
  int i;
  emxArray_int32_T *bitrevIndex;
  double twid_re;
  double z;
  double twid_im;
  int iDelta;
  bool nxeven;
  int ix;
  emxArray_creal_T *b_y;
  double temp_re;
  double temp_im;
  int temp_re_tmp;
  int ihi;
  acemxInit_int32_T(&wrapIndex, 2);
  acemxInit_real_T(&hcostab, 2);
  acemxInit_real_T(&hsintab, 2);
  acemxInit_creal_T(&reconVar1, 1);
  acemxInit_creal_T(&reconVar2, 1);
  nRows = unsigned_nRows / 2;
  istart = nRows - 2;
  nRowsD2 = nRows / 2;
  k = nRowsD2 / 2;
  d_FFTImplementationCallback_get(costab, sintab, hcostab, hsintab);
  j = reconVar1->size[0];
  reconVar1->size[0] = nRows;
  acemxEnsureCapacity_creal_T(reconVar1, j);
  j = reconVar2->size[0];
  reconVar2->size[0] = nRows;
  acemxEnsureCapacity_creal_T(reconVar2, j);
  j = wrapIndex->size[0] * wrapIndex->size[1];
  wrapIndex->size[0] = 1;
  wrapIndex->size[1] = nRows;
  acemxEnsureCapacity_int32_T(wrapIndex, j);
  for (i = 0; i < nRows; i++) {
    twid_re = sintab->data[i];
    twid_im = costab->data[i];
    reconVar1->data[i].re = twid_re + 1.0;
    reconVar1->data[i].im = -twid_im;
    reconVar2->data[i].re = 1.0 - twid_re;
    reconVar2->data[i].im = twid_im;
    if (i + 1 != 1) {
      wrapIndex->data[i] = (nRows - i) + 1;
    } else {
      wrapIndex->data[0] = 1;
    }
  }

  acemxInit_int32_T(&bitrevIndex, 1);
  z = (double)unsigned_nRows / 2.0;
  iDelta = y->size[0];
  if (iDelta >= nRows) {
    iDelta = nRows;
  }

  e_FFTImplementationCallback_get(iDelta - 1, (int)z, bitrevIndex);
  if ((x->size[0] & 1) == 0) {
    nxeven = true;
    iDelta = x->size[0];
  } else if (x->size[0] >= unsigned_nRows) {
    nxeven = true;
    iDelta = unsigned_nRows;
  } else {
    nxeven = false;
    iDelta = x->size[0] - 1;
  }

  ix = 0;
  if (iDelta >= unsigned_nRows) {
    iDelta = unsigned_nRows;
  }

  j = (int)((double)iDelta / 2.0);
  for (i = 0; i < j; i++) {
    y->data[bitrevIndex->data[i] - 1].re = x->data[ix];
    y->data[bitrevIndex->data[i] - 1].im = x->data[ix + 1];
    ix += 2;
  }

  if (!nxeven) {
    y->data[bitrevIndex->data[j] - 1].re = x->data[ix];
    y->data[bitrevIndex->data[j] - 1].im = 0.0;
  }

  acemxFree_int32_T(&bitrevIndex);
  acemxInit_creal_T(&b_y, 1);
  j = b_y->size[0];
  b_y->size[0] = y->size[0];
  acemxEnsureCapacity_creal_T(b_y, j);
  iDelta = y->size[0];
  for (j = 0; j < iDelta; j++) {
    b_y->data[j] = y->data[j];
  }

  if (nRows > 1) {
    for (i = 0; i <= istart; i += 2) {
      temp_re = b_y->data[i + 1].re;
      temp_im = b_y->data[i + 1].im;
      twid_re = b_y->data[i].re;
      twid_im = b_y->data[i].im;
      b_y->data[i + 1].re = b_y->data[i].re - b_y->data[i + 1].re;
      b_y->data[i + 1].im = b_y->data[i].im - b_y->data[i + 1].im;
      twid_re += temp_re;
      twid_im += temp_im;
      b_y->data[i].re = twid_re;
      b_y->data[i].im = twid_im;
    }
  }

  iDelta = 2;
  ix = 4;
  nRows = ((k - 1) << 2) + 1;
  while (k > 0) {
    for (i = 0; i < nRows; i += ix) {
      temp_re_tmp = i + iDelta;
      temp_re = b_y->data[temp_re_tmp].re;
      temp_im = b_y->data[temp_re_tmp].im;
      b_y->data[temp_re_tmp].re = b_y->data[i].re - b_y->data[temp_re_tmp].re;
      b_y->data[temp_re_tmp].im = b_y->data[i].im - b_y->data[temp_re_tmp].im;
      b_y->data[i].re += temp_re;
      b_y->data[i].im += temp_im;
    }

    istart = 1;
    for (j = k; j < nRowsD2; j += k) {
      twid_re = hcostab->data[j];
      twid_im = hsintab->data[j];
      i = istart;
      ihi = istart + nRows;
      while (i < ihi) {
        temp_re_tmp = i + iDelta;
        temp_re = twid_re * b_y->data[temp_re_tmp].re - twid_im * b_y->
          data[temp_re_tmp].im;
        temp_im = twid_re * b_y->data[temp_re_tmp].im + twid_im * b_y->
          data[temp_re_tmp].re;
        b_y->data[temp_re_tmp].re = b_y->data[i].re - temp_re;
        b_y->data[temp_re_tmp].im = b_y->data[i].im - temp_im;
        b_y->data[i].re += temp_re;
        b_y->data[i].im += temp_im;
        i += ix;
      }

      istart++;
    }

    k /= 2;
    iDelta = ix;
    ix += ix;
    nRows -= iDelta;
  }

  acemxFree_real_T(&hsintab);
  acemxFree_real_T(&hcostab);
  j = y->size[0];
  y->size[0] = b_y->size[0];
  acemxEnsureCapacity_creal_T(y, j);
  iDelta = b_y->size[0];
  for (j = 0; j < iDelta; j++) {
    y->data[j] = b_y->data[j];
  }

  acemxFree_creal_T(&b_y);
  g_FFTImplementationCallback_get(y, reconVar1, reconVar2, wrapIndex, (int)z);
  acemxFree_creal_T(&reconVar2);
  acemxFree_creal_T(&reconVar1);
  acemxFree_int32_T(&wrapIndex);
}

/*
 * Arguments    : const emxArray_real_T *x
 *                emxArray_creal_T *y
 *                int nrowsx
 *                int nRows
 *                int nfft
 *                const emxArray_creal_T *wwc
 *                const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                const emxArray_real_T *costabinv
 *                const emxArray_real_T *sintabinv
 * Return Type  : void
 */
static void d_FFTImplementationCallback_doH(const emxArray_real_T *x,
  emxArray_creal_T *y, int nrowsx, int nRows, int nfft, const emxArray_creal_T
  *wwc, const emxArray_real_T *costab, const emxArray_real_T *sintab, const
  emxArray_real_T *costabinv, const emxArray_real_T *sintabinv)
{
  emxArray_cuint8_T *b_y;
  int hnRows;
  int i;
  emxArray_creal_T *ytmp;
  int idx;
  bool nxeven;
  int u0;
  emxArray_real_T *unusedU0;
  emxArray_int32_T *wrapIndex;
  emxArray_real_T *costable;
  emxArray_real_T *sintable;
  emxArray_real_T *hsintab;
  emxArray_real_T *hcostabinv;
  emxArray_real_T *hsintabinv;
  emxArray_creal_T *reconVar1;
  emxArray_creal_T *reconVar2;
  int xidx;
  int k1;
  double b_im;
  emxArray_creal_T *fv;
  emxArray_creal_T *r;
  double ytmp_im;
  acemxInit_cuint8_T(&b_y, 1);
  hnRows = nRows / 2;
  i = b_y->size[0];
  b_y->size[0] = hnRows;
  acemxEnsureCapacity_cuint8_T(b_y, i);
  if (hnRows > nrowsx) {
    i = b_y->size[0];
    b_y->size[0] = hnRows;
    acemxEnsureCapacity_cuint8_T(b_y, i);
    for (i = 0; i < hnRows; i++) {
      b_y->data[i].re = 0U;
      b_y->data[i].im = 0U;
    }
  }

  acemxInit_creal_T(&ytmp, 1);
  i = ytmp->size[0];
  ytmp->size[0] = b_y->size[0];
  acemxEnsureCapacity_creal_T(ytmp, i);
  idx = b_y->size[0];
  for (i = 0; i < idx; i++) {
    ytmp->data[i].re = b_y->data[i].re;
    ytmp->data[i].im = b_y->data[i].im;
  }

  acemxFree_cuint8_T(&b_y);
  if ((x->size[0] & 1) == 0) {
    nxeven = true;
    u0 = x->size[0];
  } else if (x->size[0] >= nRows) {
    nxeven = true;
    u0 = nRows;
  } else {
    nxeven = false;
    u0 = x->size[0] - 1;
  }

  acemxInit_real_T(&unusedU0, 2);
  acemxInit_int32_T(&wrapIndex, 2);
  acemxInit_real_T(&costable, 2);
  acemxInit_real_T(&sintable, 2);
  acemxInit_real_T(&hsintab, 2);
  acemxInit_real_T(&hcostabinv, 2);
  acemxInit_real_T(&hsintabinv, 2);
  acemxInit_creal_T(&reconVar1, 1);
  acemxInit_creal_T(&reconVar2, 1);
  if (u0 >= nRows) {
    u0 = nRows;
  }

  d_FFTImplementationCallback_gen(nRows << 1, costable, sintable, unusedU0);
  f_FFTImplementationCallback_get(costab, sintab, costabinv, sintabinv, unusedU0,
    hsintab, hcostabinv, hsintabinv);
  i = reconVar1->size[0];
  reconVar1->size[0] = hnRows;
  acemxEnsureCapacity_creal_T(reconVar1, i);
  i = reconVar2->size[0];
  reconVar2->size[0] = hnRows;
  acemxEnsureCapacity_creal_T(reconVar2, i);
  idx = 0;
  i = wrapIndex->size[0] * wrapIndex->size[1];
  wrapIndex->size[0] = 1;
  wrapIndex->size[1] = hnRows;
  acemxEnsureCapacity_int32_T(wrapIndex, i);
  for (xidx = 0; xidx < hnRows; xidx++) {
    reconVar1->data[xidx].re = sintable->data[idx] + 1.0;
    reconVar1->data[xidx].im = -costable->data[idx];
    reconVar2->data[xidx].re = 1.0 - sintable->data[idx];
    reconVar2->data[xidx].im = costable->data[idx];
    idx += 2;
    if (xidx + 1 != 1) {
      wrapIndex->data[xidx] = (hnRows - xidx) + 1;
    } else {
      wrapIndex->data[0] = 1;
    }
  }

  acemxFree_real_T(&sintable);
  acemxFree_real_T(&costable);
  xidx = 0;
  i = (int)((double)u0 / 2.0);
  for (k1 = 0; k1 < i; k1++) {
    idx = (hnRows + k1) - 1;
    b_im = x->data[xidx + 1];
    ytmp->data[k1].re = wwc->data[idx].re * x->data[xidx] + wwc->data[idx].im *
      b_im;
    ytmp->data[k1].im = wwc->data[idx].re * b_im - wwc->data[idx].im * x->
      data[xidx];
    xidx += 2;
  }

  if (!nxeven) {
    idx = (hnRows + i) - 1;
    ytmp->data[i].re = wwc->data[idx].re * x->data[xidx] + wwc->data[idx].im *
      0.0;
    ytmp->data[i].im = wwc->data[idx].re * 0.0 - wwc->data[idx].im * x->
      data[xidx];
    if (i + 2 <= hnRows) {
      i = (int)((double)u0 / 2.0) + 2;
      for (xidx = i; xidx <= hnRows; xidx++) {
        ytmp->data[xidx - 1].re = 0.0;
        ytmp->data[xidx - 1].im = 0.0;
      }
    }
  } else {
    if (i + 1 <= hnRows) {
      i = (int)((double)u0 / 2.0) + 1;
      for (xidx = i; xidx <= hnRows; xidx++) {
        ytmp->data[xidx - 1].re = 0.0;
        ytmp->data[xidx - 1].im = 0.0;
      }
    }
  }

  acemxInit_creal_T(&fv, 1);
  acemxInit_creal_T(&r, 1);
  xidx = (int)((double)nfft / 2.0);
  d_FFTImplementationCallback_r2b(ytmp, xidx, unusedU0, hsintab, fv);
  e_FFTImplementationCallback_r2b(wwc, xidx, unusedU0, hsintab, r);
  idx = fv->size[0];
  acemxFree_real_T(&hsintab);
  acemxFree_real_T(&unusedU0);
  for (i = 0; i < idx; i++) {
    b_im = fv->data[i].re * r->data[i].im + fv->data[i].im * r->data[i].re;
    fv->data[i].re = fv->data[i].re * r->data[i].re - fv->data[i].im * r->data[i]
      .im;
    fv->data[i].im = b_im;
  }

  f_FFTImplementationCallback_r2b(fv, xidx, hcostabinv, hsintabinv, r);
  i = fv->size[0];
  fv->size[0] = r->size[0];
  acemxEnsureCapacity_creal_T(fv, i);
  idx = r->size[0];
  acemxFree_real_T(&hsintabinv);
  acemxFree_real_T(&hcostabinv);
  for (i = 0; i < idx; i++) {
    fv->data[i] = r->data[i];
  }

  acemxFree_creal_T(&r);
  idx = 0;
  i = wwc->size[0];
  for (xidx = hnRows; xidx <= i; xidx++) {
    ytmp->data[idx].re = wwc->data[xidx - 1].re * fv->data[xidx - 1].re +
      wwc->data[xidx - 1].im * fv->data[xidx - 1].im;
    ytmp->data[idx].im = wwc->data[xidx - 1].re * fv->data[xidx - 1].im -
      wwc->data[xidx - 1].im * fv->data[xidx - 1].re;
    idx++;
  }

  acemxFree_creal_T(&fv);
  for (xidx = 0; xidx < hnRows; xidx++) {
    i = wrapIndex->data[xidx];
    b_im = ytmp->data[i - 1].re;
    ytmp_im = -ytmp->data[i - 1].im;
    y->data[xidx].re = 0.5 * ((ytmp->data[xidx].re * reconVar1->data[xidx].re -
      ytmp->data[xidx].im * reconVar1->data[xidx].im) + (b_im * reconVar2->
      data[xidx].re - ytmp_im * reconVar2->data[xidx].im));
    y->data[xidx].im = 0.5 * ((ytmp->data[xidx].re * reconVar1->data[xidx].im +
      ytmp->data[xidx].im * reconVar1->data[xidx].re) + (b_im * reconVar2->
      data[xidx].im + ytmp_im * reconVar2->data[xidx].re));
    b_im = ytmp->data[i - 1].re;
    ytmp_im = -ytmp->data[i - 1].im;
    i = hnRows + xidx;
    y->data[i].re = 0.5 * ((ytmp->data[xidx].re * reconVar2->data[xidx].re -
      ytmp->data[xidx].im * reconVar2->data[xidx].im) + (b_im * reconVar1->
      data[xidx].re - ytmp_im * reconVar1->data[xidx].im));
    y->data[i].im = 0.5 * ((ytmp->data[xidx].re * reconVar2->data[xidx].im +
      ytmp->data[xidx].im * reconVar2->data[xidx].re) + (b_im * reconVar1->
      data[xidx].im + ytmp_im * reconVar1->data[xidx].re));
  }

  acemxFree_creal_T(&reconVar2);
  acemxFree_creal_T(&reconVar1);
  acemxFree_int32_T(&wrapIndex);
  acemxFree_creal_T(&ytmp);
}

/*
 * Arguments    : int nRows
 *                emxArray_real_T *costab
 *                emxArray_real_T *sintab
 *                emxArray_real_T *sintabinv
 * Return Type  : void
 */
static void d_FFTImplementationCallback_gen(int nRows, emxArray_real_T *costab,
  emxArray_real_T *sintab, emxArray_real_T *sintabinv)
{
  emxArray_real_T *costab1q;
  double e;
  int n;
  int i;
  int nd2;
  int k;
  emxArray_real_T *b_costab;
  emxArray_real_T *b_sintab;
  emxArray_real_T *b_sintabinv;
  acemxInit_real_T(&costab1q, 2);
  e = 6.2831853071795862 / (double)nRows;
  n = nRows / 2 / 2;
  i = costab1q->size[0] * costab1q->size[1];
  costab1q->size[0] = 1;
  costab1q->size[1] = n + 1;
  acemxEnsureCapacity_real_T(costab1q, i);
  costab1q->data[0] = 1.0;
  nd2 = n / 2 - 1;
  for (k = 0; k <= nd2; k++) {
    costab1q->data[k + 1] = cos(e * ((double)k + 1.0));
  }

  i = nd2 + 2;
  nd2 = n - 1;
  for (k = i; k <= nd2; k++) {
    costab1q->data[k] = sin(e * (double)(n - k));
  }

  acemxInit_real_T(&b_costab, 2);
  acemxInit_real_T(&b_sintab, 2);
  acemxInit_real_T(&b_sintabinv, 2);
  costab1q->data[n] = 0.0;
  n = costab1q->size[1] - 1;
  nd2 = (costab1q->size[1] - 1) << 1;
  i = b_costab->size[0] * b_costab->size[1];
  b_costab->size[0] = 1;
  b_costab->size[1] = nd2 + 1;
  acemxEnsureCapacity_real_T(b_costab, i);
  i = b_sintab->size[0] * b_sintab->size[1];
  b_sintab->size[0] = 1;
  b_sintab->size[1] = nd2 + 1;
  acemxEnsureCapacity_real_T(b_sintab, i);
  b_costab->data[0] = 1.0;
  b_sintab->data[0] = 0.0;
  i = b_sintabinv->size[0] * b_sintabinv->size[1];
  b_sintabinv->size[0] = 1;
  b_sintabinv->size[1] = nd2 + 1;
  acemxEnsureCapacity_real_T(b_sintabinv, i);
  for (k = 0; k < n; k++) {
    b_sintabinv->data[k + 1] = costab1q->data[(n - k) - 1];
  }

  i = costab1q->size[1];
  for (k = i; k <= nd2; k++) {
    b_sintabinv->data[k] = costab1q->data[k - n];
  }

  for (k = 0; k < n; k++) {
    b_costab->data[k + 1] = costab1q->data[k + 1];
    b_sintab->data[k + 1] = -costab1q->data[(n - k) - 1];
  }

  i = costab1q->size[1];
  for (k = i; k <= nd2; k++) {
    b_costab->data[k] = -costab1q->data[nd2 - k];
    b_sintab->data[k] = -costab1q->data[k - n];
  }

  acemxFree_real_T(&costab1q);
  i = costab->size[0] * costab->size[1];
  costab->size[0] = 1;
  costab->size[1] = b_costab->size[1];
  acemxEnsureCapacity_real_T(costab, i);
  nd2 = b_costab->size[0] * b_costab->size[1];
  for (i = 0; i < nd2; i++) {
    costab->data[i] = b_costab->data[i];
  }

  acemxFree_real_T(&b_costab);
  i = sintab->size[0] * sintab->size[1];
  sintab->size[0] = 1;
  sintab->size[1] = b_sintab->size[1];
  acemxEnsureCapacity_real_T(sintab, i);
  nd2 = b_sintab->size[0] * b_sintab->size[1];
  for (i = 0; i < nd2; i++) {
    sintab->data[i] = b_sintab->data[i];
  }

  acemxFree_real_T(&b_sintab);
  i = sintabinv->size[0] * sintabinv->size[1];
  sintabinv->size[0] = 1;
  sintabinv->size[1] = b_sintabinv->size[1];
  acemxEnsureCapacity_real_T(sintabinv, i);
  nd2 = b_sintabinv->size[0] * b_sintabinv->size[1];
  for (i = 0; i < nd2; i++) {
    sintabinv->data[i] = b_sintabinv->data[i];
  }

  acemxFree_real_T(&b_sintabinv);
}

/*
 * Arguments    : const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                emxArray_real_T *hcostab
 *                emxArray_real_T *hsintab
 * Return Type  : void
 */
static void d_FFTImplementationCallback_get(const emxArray_real_T *costab, const
  emxArray_real_T *sintab, emxArray_real_T *hcostab, emxArray_real_T *hsintab)
{
  int hszCostab;
  int hcostab_tmp;
  int i;
  hszCostab = costab->size[1] / 2;
  hcostab_tmp = hcostab->size[0] * hcostab->size[1];
  hcostab->size[0] = 1;
  hcostab->size[1] = hszCostab;
  acemxEnsureCapacity_real_T(hcostab, hcostab_tmp);
  hcostab_tmp = hsintab->size[0] * hsintab->size[1];
  hsintab->size[0] = 1;
  hsintab->size[1] = hszCostab;
  acemxEnsureCapacity_real_T(hsintab, hcostab_tmp);
  for (i = 0; i < hszCostab; i++) {
    hcostab_tmp = ((i + 1) << 1) - 2;
    hcostab->data[i] = costab->data[hcostab_tmp];
    hsintab->data[i] = sintab->data[hcostab_tmp];
  }
}

/*
 * Arguments    : const emxArray_creal_T *x
 *                int unsigned_nRows
 *                const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                emxArray_creal_T *y
 * Return Type  : void
 */
static void d_FFTImplementationCallback_r2b(const emxArray_creal_T *x, int
  unsigned_nRows, const emxArray_real_T *costab, const emxArray_real_T *sintab,
  emxArray_creal_T *y)
{
  emxArray_creal_T *b_y;
  int iDelta2;
  int iy;
  int j;
  int nRowsD2;
  int k;
  int ix;
  int ju;
  int i;
  bool tst;
  double temp_re;
  double temp_im;
  double twid_re;
  double twid_im;
  int temp_re_tmp;
  int ihi;
  acemxInit_creal_T(&b_y, 1);
  iDelta2 = b_y->size[0];
  b_y->size[0] = unsigned_nRows;
  acemxEnsureCapacity_creal_T(b_y, iDelta2);
  if (unsigned_nRows > x->size[0]) {
    iDelta2 = b_y->size[0];
    b_y->size[0] = unsigned_nRows;
    acemxEnsureCapacity_creal_T(b_y, iDelta2);
    for (iDelta2 = 0; iDelta2 < unsigned_nRows; iDelta2++) {
      b_y->data[iDelta2].re = 0.0;
      b_y->data[iDelta2].im = 0.0;
    }
  }

  iDelta2 = y->size[0];
  y->size[0] = b_y->size[0];
  acemxEnsureCapacity_creal_T(y, iDelta2);
  iy = b_y->size[0];
  for (iDelta2 = 0; iDelta2 < iy; iDelta2++) {
    y->data[iDelta2] = b_y->data[iDelta2];
  }

  iDelta2 = x->size[0];
  if (iDelta2 >= unsigned_nRows) {
    iDelta2 = unsigned_nRows;
  }

  j = unsigned_nRows - 2;
  nRowsD2 = unsigned_nRows / 2;
  k = nRowsD2 / 2;
  ix = 0;
  iy = 0;
  ju = 0;
  for (i = 0; i <= iDelta2 - 2; i++) {
    y->data[iy] = x->data[ix];
    iy = unsigned_nRows;
    tst = true;
    while (tst) {
      iy >>= 1;
      ju ^= iy;
      tst = ((ju & iy) == 0);
    }

    iy = ju;
    ix++;
  }

  y->data[iy] = x->data[ix];
  iDelta2 = b_y->size[0];
  b_y->size[0] = y->size[0];
  acemxEnsureCapacity_creal_T(b_y, iDelta2);
  iy = y->size[0];
  for (iDelta2 = 0; iDelta2 < iy; iDelta2++) {
    b_y->data[iDelta2] = y->data[iDelta2];
  }

  if (unsigned_nRows > 1) {
    for (i = 0; i <= j; i += 2) {
      temp_re = b_y->data[i + 1].re;
      temp_im = b_y->data[i + 1].im;
      twid_re = b_y->data[i].re;
      twid_im = b_y->data[i].im;
      b_y->data[i + 1].re = b_y->data[i].re - b_y->data[i + 1].re;
      b_y->data[i + 1].im = b_y->data[i].im - b_y->data[i + 1].im;
      twid_re += temp_re;
      twid_im += temp_im;
      b_y->data[i].re = twid_re;
      b_y->data[i].im = twid_im;
    }
  }

  iy = 2;
  iDelta2 = 4;
  ix = ((k - 1) << 2) + 1;
  while (k > 0) {
    for (i = 0; i < ix; i += iDelta2) {
      temp_re_tmp = i + iy;
      temp_re = b_y->data[temp_re_tmp].re;
      temp_im = b_y->data[temp_re_tmp].im;
      b_y->data[temp_re_tmp].re = b_y->data[i].re - b_y->data[temp_re_tmp].re;
      b_y->data[temp_re_tmp].im = b_y->data[i].im - b_y->data[temp_re_tmp].im;
      b_y->data[i].re += temp_re;
      b_y->data[i].im += temp_im;
    }

    ju = 1;
    for (j = k; j < nRowsD2; j += k) {
      twid_re = costab->data[j];
      twid_im = sintab->data[j];
      i = ju;
      ihi = ju + ix;
      while (i < ihi) {
        temp_re_tmp = i + iy;
        temp_re = twid_re * b_y->data[temp_re_tmp].re - twid_im * b_y->
          data[temp_re_tmp].im;
        temp_im = twid_re * b_y->data[temp_re_tmp].im + twid_im * b_y->
          data[temp_re_tmp].re;
        b_y->data[temp_re_tmp].re = b_y->data[i].re - temp_re;
        b_y->data[temp_re_tmp].im = b_y->data[i].im - temp_im;
        b_y->data[i].re += temp_re;
        b_y->data[i].im += temp_im;
        i += iDelta2;
      }

      ju++;
    }

    k /= 2;
    iy = iDelta2;
    iDelta2 += iDelta2;
    ix -= iy;
  }

  iDelta2 = y->size[0];
  y->size[0] = b_y->size[0];
  acemxEnsureCapacity_creal_T(y, iDelta2);
  iy = b_y->size[0];
  for (iDelta2 = 0; iDelta2 < iy; iDelta2++) {
    y->data[iDelta2] = b_y->data[iDelta2];
  }

  acemxFree_creal_T(&b_y);
}

/*
 * Arguments    : const emxArray_real_T *x
 *                emxArray_creal_T *y
 *                int nrowsx
 *                int nRows
 *                int nfft
 *                const emxArray_creal_T *wwc
 *                const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                const emxArray_real_T *costabinv
 *                const emxArray_real_T *sintabinv
 * Return Type  : void
 */
static void e_FFTImplementationCallback_doH(const emxArray_real_T *x,
  emxArray_creal_T *y, int nrowsx, int nRows, int nfft, const emxArray_creal_T
  *wwc, const emxArray_real_T *costab, const emxArray_real_T *sintab, const
  emxArray_real_T *costabinv, const emxArray_real_T *sintabinv)
{
  emxArray_cuint8_T *b_y;
  int hnRows;
  int i;
  emxArray_creal_T *ytmp;
  int idx;
  bool nxeven;
  int u0;
  emxArray_real_T *unusedU0;
  emxArray_int32_T *wrapIndex;
  emxArray_real_T *costable;
  emxArray_real_T *sintable;
  emxArray_real_T *hsintab;
  emxArray_real_T *hcostabinv;
  emxArray_real_T *hsintabinv;
  emxArray_creal_T *reconVar1;
  emxArray_creal_T *reconVar2;
  int xidx;
  int k1;
  double b_im;
  emxArray_creal_T *fv;
  emxArray_creal_T *r;
  double re;
  double ai;
  acemxInit_cuint8_T(&b_y, 1);
  hnRows = nRows / 2;
  i = b_y->size[0];
  b_y->size[0] = hnRows;
  acemxEnsureCapacity_cuint8_T(b_y, i);
  if (hnRows > nrowsx) {
    i = b_y->size[0];
    b_y->size[0] = hnRows;
    acemxEnsureCapacity_cuint8_T(b_y, i);
    for (i = 0; i < hnRows; i++) {
      b_y->data[i].re = 0U;
      b_y->data[i].im = 0U;
    }
  }

  acemxInit_creal_T(&ytmp, 1);
  i = ytmp->size[0];
  ytmp->size[0] = b_y->size[0];
  acemxEnsureCapacity_creal_T(ytmp, i);
  idx = b_y->size[0];
  for (i = 0; i < idx; i++) {
    ytmp->data[i].re = b_y->data[i].re;
    ytmp->data[i].im = b_y->data[i].im;
  }

  acemxFree_cuint8_T(&b_y);
  if ((x->size[0] & 1) == 0) {
    nxeven = true;
    u0 = x->size[0];
  } else if (x->size[0] >= nRows) {
    nxeven = true;
    u0 = nRows;
  } else {
    nxeven = false;
    u0 = x->size[0] - 1;
  }

  acemxInit_real_T(&unusedU0, 2);
  acemxInit_int32_T(&wrapIndex, 2);
  acemxInit_real_T(&costable, 2);
  acemxInit_real_T(&sintable, 2);
  acemxInit_real_T(&hsintab, 2);
  acemxInit_real_T(&hcostabinv, 2);
  acemxInit_real_T(&hsintabinv, 2);
  acemxInit_creal_T(&reconVar1, 1);
  acemxInit_creal_T(&reconVar2, 1);
  if (u0 >= nRows) {
    u0 = nRows;
  }

  d_FFTImplementationCallback_gen(nRows << 1, costable, sintable, unusedU0);
  f_FFTImplementationCallback_get(costab, sintab, costabinv, sintabinv, unusedU0,
    hsintab, hcostabinv, hsintabinv);
  i = reconVar1->size[0];
  reconVar1->size[0] = hnRows;
  acemxEnsureCapacity_creal_T(reconVar1, i);
  i = reconVar2->size[0];
  reconVar2->size[0] = hnRows;
  acemxEnsureCapacity_creal_T(reconVar2, i);
  idx = 0;
  i = wrapIndex->size[0] * wrapIndex->size[1];
  wrapIndex->size[0] = 1;
  wrapIndex->size[1] = hnRows;
  acemxEnsureCapacity_int32_T(wrapIndex, i);
  for (xidx = 0; xidx < hnRows; xidx++) {
    reconVar1->data[xidx].re = 1.0 - sintable->data[idx];
    reconVar1->data[xidx].im = -costable->data[idx];
    reconVar2->data[xidx].re = sintable->data[idx] + 1.0;
    reconVar2->data[xidx].im = costable->data[idx];
    idx += 2;
    if (xidx + 1 != 1) {
      wrapIndex->data[xidx] = (hnRows - xidx) + 1;
    } else {
      wrapIndex->data[0] = 1;
    }
  }

  acemxFree_real_T(&sintable);
  acemxFree_real_T(&costable);
  xidx = 0;
  i = (int)((double)u0 / 2.0);
  for (k1 = 0; k1 < i; k1++) {
    idx = (hnRows + k1) - 1;
    b_im = x->data[xidx + 1];
    ytmp->data[k1].re = wwc->data[idx].re * x->data[xidx] + wwc->data[idx].im *
      b_im;
    ytmp->data[k1].im = wwc->data[idx].re * b_im - wwc->data[idx].im * x->
      data[xidx];
    xidx += 2;
  }

  if (!nxeven) {
    idx = (hnRows + i) - 1;
    ytmp->data[i].re = wwc->data[idx].re * x->data[xidx] + wwc->data[idx].im *
      0.0;
    ytmp->data[i].im = wwc->data[idx].re * 0.0 - wwc->data[idx].im * x->
      data[xidx];
    if (i + 2 <= hnRows) {
      i = (int)((double)u0 / 2.0) + 2;
      for (xidx = i; xidx <= hnRows; xidx++) {
        ytmp->data[xidx - 1].re = 0.0;
        ytmp->data[xidx - 1].im = 0.0;
      }
    }
  } else {
    if (i + 1 <= hnRows) {
      i = (int)((double)u0 / 2.0) + 1;
      for (xidx = i; xidx <= hnRows; xidx++) {
        ytmp->data[xidx - 1].re = 0.0;
        ytmp->data[xidx - 1].im = 0.0;
      }
    }
  }

  acemxInit_creal_T(&fv, 1);
  acemxInit_creal_T(&r, 1);
  xidx = (int)((double)nfft / 2.0);
  d_FFTImplementationCallback_r2b(ytmp, xidx, unusedU0, hsintab, fv);
  e_FFTImplementationCallback_r2b(wwc, xidx, unusedU0, hsintab, r);
  idx = fv->size[0];
  acemxFree_real_T(&hsintab);
  acemxFree_real_T(&unusedU0);
  for (i = 0; i < idx; i++) {
    b_im = fv->data[i].re * r->data[i].im + fv->data[i].im * r->data[i].re;
    fv->data[i].re = fv->data[i].re * r->data[i].re - fv->data[i].im * r->data[i]
      .im;
    fv->data[i].im = b_im;
  }

  f_FFTImplementationCallback_r2b(fv, xidx, hcostabinv, hsintabinv, r);
  i = fv->size[0];
  fv->size[0] = r->size[0];
  acemxEnsureCapacity_creal_T(fv, i);
  idx = r->size[0];
  acemxFree_real_T(&hsintabinv);
  acemxFree_real_T(&hcostabinv);
  for (i = 0; i < idx; i++) {
    fv->data[i] = r->data[i];
  }

  acemxFree_creal_T(&r);
  idx = 0;
  i = wwc->size[0];
  for (xidx = hnRows; xidx <= i; xidx++) {
    ytmp->data[idx].re = wwc->data[xidx - 1].re * fv->data[xidx - 1].re +
      wwc->data[xidx - 1].im * fv->data[xidx - 1].im;
    ytmp->data[idx].im = wwc->data[xidx - 1].re * fv->data[xidx - 1].im -
      wwc->data[xidx - 1].im * fv->data[xidx - 1].re;
    if (ytmp->data[idx].im == 0.0) {
      re = ytmp->data[idx].re / (double)hnRows;
      b_im = 0.0;
    } else if (ytmp->data[idx].re == 0.0) {
      re = 0.0;
      b_im = ytmp->data[idx].im / (double)hnRows;
    } else {
      re = ytmp->data[idx].re / (double)hnRows;
      b_im = ytmp->data[idx].im / (double)hnRows;
    }

    ytmp->data[idx].re = re;
    ytmp->data[idx].im = b_im;
    idx++;
  }

  acemxFree_creal_T(&fv);
  for (xidx = 0; xidx < hnRows; xidx++) {
    i = wrapIndex->data[xidx];
    b_im = ytmp->data[i - 1].re;
    re = -ytmp->data[i - 1].im;
    y->data[xidx].re = 0.5 * ((ytmp->data[xidx].re * reconVar1->data[xidx].re -
      ytmp->data[xidx].im * reconVar1->data[xidx].im) + (b_im * reconVar2->
      data[xidx].re - re * reconVar2->data[xidx].im));
    y->data[xidx].im = 0.5 * ((ytmp->data[xidx].re * reconVar1->data[xidx].im +
      ytmp->data[xidx].im * reconVar1->data[xidx].re) + (b_im * reconVar2->
      data[xidx].im + re * reconVar2->data[xidx].re));
    b_im = ytmp->data[i - 1].re;
    re = -ytmp->data[i - 1].im;
    i = hnRows + xidx;
    y->data[i].re = 0.5 * ((ytmp->data[xidx].re * reconVar2->data[xidx].re -
      ytmp->data[xidx].im * reconVar2->data[xidx].im) + (b_im * reconVar1->
      data[xidx].re - re * reconVar1->data[xidx].im));
    y->data[i].im = 0.5 * ((ytmp->data[xidx].re * reconVar2->data[xidx].im +
      ytmp->data[xidx].im * reconVar2->data[xidx].re) + (b_im * reconVar1->
      data[xidx].im + re * reconVar1->data[xidx].re));
  }

  acemxFree_creal_T(&reconVar2);
  acemxFree_creal_T(&reconVar1);
  acemxFree_int32_T(&wrapIndex);
  acemxFree_creal_T(&ytmp);
  idx = y->size[0];
  for (i = 0; i < idx; i++) {
    b_im = y->data[i].re;
    ai = y->data[i].im;
    if (ai == 0.0) {
      re = b_im / 2.0;
      b_im = 0.0;
    } else if (b_im == 0.0) {
      re = 0.0;
      b_im = ai / 2.0;
    } else {
      re = b_im / 2.0;
      b_im = ai / 2.0;
    }

    y->data[i].re = re;
    y->data[i].im = b_im;
  }
}

/*
 * Arguments    : int nRowsM1
 *                int nfftLen
 *                emxArray_int32_T *bitrevIndex
 * Return Type  : void
 */
static void e_FFTImplementationCallback_get(int nRowsM1, int nfftLen,
  emxArray_int32_T *bitrevIndex)
{
  int ju;
  int iy;
  int b_j1;
  bool tst;
  ju = 0;
  iy = 1;
  b_j1 = bitrevIndex->size[0];
  bitrevIndex->size[0] = nfftLen;
  acemxEnsureCapacity_int32_T(bitrevIndex, b_j1);
  for (b_j1 = 0; b_j1 < nfftLen; b_j1++) {
    bitrevIndex->data[b_j1] = 0;
  }

  for (b_j1 = 0; b_j1 < nRowsM1; b_j1++) {
    bitrevIndex->data[b_j1] = iy;
    iy = nfftLen;
    tst = true;
    while (tst) {
      iy >>= 1;
      ju ^= iy;
      tst = ((ju & iy) == 0);
    }

    iy = ju + 1;
  }

  bitrevIndex->data[nRowsM1] = iy;
}

/*
 * Arguments    : const emxArray_creal_T *x
 *                int n1_unsigned
 *                const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                emxArray_creal_T *y
 * Return Type  : void
 */
static void e_FFTImplementationCallback_r2b(const emxArray_creal_T *x, int
  n1_unsigned, const emxArray_real_T *costab, const emxArray_real_T *sintab,
  emxArray_creal_T *y)
{
  int iy;
  int iDelta2;
  int iheight;
  int nRowsD2;
  int k;
  int ix;
  int ju;
  int i;
  bool tst;
  double temp_re;
  double temp_im;
  double twid_re;
  double twid_im;
  int temp_re_tmp;
  int ihi;
  iy = y->size[0];
  y->size[0] = n1_unsigned;
  acemxEnsureCapacity_creal_T(y, iy);
  if (n1_unsigned > x->size[0]) {
    iy = y->size[0];
    y->size[0] = n1_unsigned;
    acemxEnsureCapacity_creal_T(y, iy);
    for (iy = 0; iy < n1_unsigned; iy++) {
      y->data[iy].re = 0.0;
      y->data[iy].im = 0.0;
    }
  }

  iDelta2 = x->size[0];
  if (iDelta2 >= n1_unsigned) {
    iDelta2 = n1_unsigned;
  }

  iheight = n1_unsigned - 2;
  nRowsD2 = n1_unsigned / 2;
  k = nRowsD2 / 2;
  ix = 0;
  iy = 0;
  ju = 0;
  for (i = 0; i <= iDelta2 - 2; i++) {
    y->data[iy] = x->data[ix];
    iy = n1_unsigned;
    tst = true;
    while (tst) {
      iy >>= 1;
      ju ^= iy;
      tst = ((ju & iy) == 0);
    }

    iy = ju;
    ix++;
  }

  y->data[iy] = x->data[ix];
  if (n1_unsigned > 1) {
    for (i = 0; i <= iheight; i += 2) {
      temp_re = y->data[i + 1].re;
      temp_im = y->data[i + 1].im;
      twid_re = y->data[i].re;
      twid_im = y->data[i].im;
      y->data[i + 1].re = y->data[i].re - y->data[i + 1].re;
      y->data[i + 1].im = y->data[i].im - y->data[i + 1].im;
      twid_re += temp_re;
      twid_im += temp_im;
      y->data[i].re = twid_re;
      y->data[i].im = twid_im;
    }
  }

  iy = 2;
  iDelta2 = 4;
  iheight = ((k - 1) << 2) + 1;
  while (k > 0) {
    for (i = 0; i < iheight; i += iDelta2) {
      temp_re_tmp = i + iy;
      temp_re = y->data[temp_re_tmp].re;
      temp_im = y->data[temp_re_tmp].im;
      y->data[temp_re_tmp].re = y->data[i].re - y->data[temp_re_tmp].re;
      y->data[temp_re_tmp].im = y->data[i].im - y->data[temp_re_tmp].im;
      y->data[i].re += temp_re;
      y->data[i].im += temp_im;
    }

    ix = 1;
    for (ju = k; ju < nRowsD2; ju += k) {
      twid_re = costab->data[ju];
      twid_im = sintab->data[ju];
      i = ix;
      ihi = ix + iheight;
      while (i < ihi) {
        temp_re_tmp = i + iy;
        temp_re = twid_re * y->data[temp_re_tmp].re - twid_im * y->
          data[temp_re_tmp].im;
        temp_im = twid_re * y->data[temp_re_tmp].im + twid_im * y->
          data[temp_re_tmp].re;
        y->data[temp_re_tmp].re = y->data[i].re - temp_re;
        y->data[temp_re_tmp].im = y->data[i].im - temp_im;
        y->data[i].re += temp_re;
        y->data[i].im += temp_im;
        i += iDelta2;
      }

      ix++;
    }

    k /= 2;
    iy = iDelta2;
    iDelta2 += iDelta2;
    iheight -= iy;
  }
}

/*
 * Arguments    : const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                const emxArray_real_T *costabinv
 *                const emxArray_real_T *sintabinv
 *                emxArray_real_T *hcostab
 *                emxArray_real_T *hsintab
 *                emxArray_real_T *hcostabinv
 *                emxArray_real_T *hsintabinv
 * Return Type  : void
 */
static void f_FFTImplementationCallback_get(const emxArray_real_T *costab, const
  emxArray_real_T *sintab, const emxArray_real_T *costabinv, const
  emxArray_real_T *sintabinv, emxArray_real_T *hcostab, emxArray_real_T *hsintab,
  emxArray_real_T *hcostabinv, emxArray_real_T *hsintabinv)
{
  int hszCostab;
  int hcostab_tmp;
  int i;
  hszCostab = costab->size[1] / 2;
  hcostab_tmp = hcostab->size[0] * hcostab->size[1];
  hcostab->size[0] = 1;
  hcostab->size[1] = hszCostab;
  acemxEnsureCapacity_real_T(hcostab, hcostab_tmp);
  hcostab_tmp = hsintab->size[0] * hsintab->size[1];
  hsintab->size[0] = 1;
  hsintab->size[1] = hszCostab;
  acemxEnsureCapacity_real_T(hsintab, hcostab_tmp);
  hcostab_tmp = hcostabinv->size[0] * hcostabinv->size[1];
  hcostabinv->size[0] = 1;
  hcostabinv->size[1] = hszCostab;
  acemxEnsureCapacity_real_T(hcostabinv, hcostab_tmp);
  hcostab_tmp = hsintabinv->size[0] * hsintabinv->size[1];
  hsintabinv->size[0] = 1;
  hsintabinv->size[1] = hszCostab;
  acemxEnsureCapacity_real_T(hsintabinv, hcostab_tmp);
  for (i = 0; i < hszCostab; i++) {
    hcostab_tmp = ((i + 1) << 1) - 2;
    hcostab->data[i] = costab->data[hcostab_tmp];
    hsintab->data[i] = sintab->data[hcostab_tmp];
    hcostabinv->data[i] = costabinv->data[hcostab_tmp];
    hsintabinv->data[i] = sintabinv->data[hcostab_tmp];
  }
}

/*
 * Arguments    : const emxArray_creal_T *x
 *                int n1_unsigned
 *                const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                emxArray_creal_T *y
 * Return Type  : void
 */
static void f_FFTImplementationCallback_r2b(const emxArray_creal_T *x, int
  n1_unsigned, const emxArray_real_T *costab, const emxArray_real_T *sintab,
  emxArray_creal_T *y)
{
  int iDelta2;
  int iheight;
  int istart;
  int nRowsD2;
  int k;
  int ix;
  int iy;
  int ju;
  int i;
  bool tst;
  double temp_re;
  double temp_im;
  double twid_re;
  double twid_im;
  int temp_re_tmp;
  iDelta2 = y->size[0];
  y->size[0] = n1_unsigned;
  acemxEnsureCapacity_creal_T(y, iDelta2);
  if (n1_unsigned > x->size[0]) {
    iDelta2 = y->size[0];
    y->size[0] = n1_unsigned;
    acemxEnsureCapacity_creal_T(y, iDelta2);
    for (iDelta2 = 0; iDelta2 < n1_unsigned; iDelta2++) {
      y->data[iDelta2].re = 0.0;
      y->data[iDelta2].im = 0.0;
    }
  }

  iheight = x->size[0];
  if (iheight >= n1_unsigned) {
    iheight = n1_unsigned;
  }

  istart = n1_unsigned - 2;
  nRowsD2 = n1_unsigned / 2;
  k = nRowsD2 / 2;
  ix = 0;
  iy = 0;
  ju = 0;
  for (i = 0; i <= iheight - 2; i++) {
    y->data[iy] = x->data[ix];
    iDelta2 = n1_unsigned;
    tst = true;
    while (tst) {
      iDelta2 >>= 1;
      ju ^= iDelta2;
      tst = ((ju & iDelta2) == 0);
    }

    iy = ju;
    ix++;
  }

  y->data[iy] = x->data[ix];
  if (n1_unsigned > 1) {
    for (i = 0; i <= istart; i += 2) {
      temp_re = y->data[i + 1].re;
      temp_im = y->data[i + 1].im;
      twid_re = y->data[i].re;
      twid_im = y->data[i].im;
      y->data[i + 1].re = y->data[i].re - y->data[i + 1].re;
      y->data[i + 1].im = y->data[i].im - y->data[i + 1].im;
      twid_re += temp_re;
      twid_im += temp_im;
      y->data[i].re = twid_re;
      y->data[i].im = twid_im;
    }
  }

  iy = 2;
  iDelta2 = 4;
  iheight = ((k - 1) << 2) + 1;
  while (k > 0) {
    for (i = 0; i < iheight; i += iDelta2) {
      temp_re_tmp = i + iy;
      temp_re = y->data[temp_re_tmp].re;
      temp_im = y->data[temp_re_tmp].im;
      y->data[temp_re_tmp].re = y->data[i].re - y->data[temp_re_tmp].re;
      y->data[temp_re_tmp].im = y->data[i].im - y->data[temp_re_tmp].im;
      y->data[i].re += temp_re;
      y->data[i].im += temp_im;
    }

    istart = 1;
    for (ix = k; ix < nRowsD2; ix += k) {
      twid_re = costab->data[ix];
      twid_im = sintab->data[ix];
      i = istart;
      ju = istart + iheight;
      while (i < ju) {
        temp_re_tmp = i + iy;
        temp_re = twid_re * y->data[temp_re_tmp].re - twid_im * y->
          data[temp_re_tmp].im;
        temp_im = twid_re * y->data[temp_re_tmp].im + twid_im * y->
          data[temp_re_tmp].re;
        y->data[temp_re_tmp].re = y->data[i].re - temp_re;
        y->data[temp_re_tmp].im = y->data[i].im - temp_im;
        y->data[i].re += temp_re;
        y->data[i].im += temp_im;
        i += iDelta2;
      }

      istart++;
    }

    k /= 2;
    iy = iDelta2;
    iDelta2 += iDelta2;
    iheight -= iy;
  }

  if (y->size[0] > 1) {
    twid_re = 1.0 / (double)y->size[0];
    iy = y->size[0];
    for (iDelta2 = 0; iDelta2 < iy; iDelta2++) {
      y->data[iDelta2].re *= twid_re;
      y->data[iDelta2].im *= twid_re;
    }
  }
}

/*
 * Arguments    : emxArray_creal_T *y
 *                const emxArray_creal_T *reconVar1
 *                const emxArray_creal_T *reconVar2
 *                const emxArray_int32_T *wrapIndex
 *                int hnRows
 * Return Type  : void
 */
static void g_FFTImplementationCallback_get(emxArray_creal_T *y, const
  emxArray_creal_T *reconVar1, const emxArray_creal_T *reconVar2, const
  emxArray_int32_T *wrapIndex, int hnRows)
{
  int iterVar;
  double temp1_re;
  double temp1_im;
  double y_im;
  double y_re;
  double b_y_im;
  int i;
  int b_i;
  double temp2_re;
  double temp2_im;
  int i1;
  iterVar = hnRows / 2;
  temp1_re = y->data[0].re;
  temp1_im = y->data[0].im;
  y_im = y->data[0].re * reconVar1->data[0].im + y->data[0].im * reconVar1->
    data[0].re;
  y_re = y->data[0].re;
  b_y_im = -y->data[0].im;
  y->data[0].re = 0.5 * ((y->data[0].re * reconVar1->data[0].re - y->data[0].im *
    reconVar1->data[0].im) + (y_re * reconVar2->data[0].re - b_y_im *
    reconVar2->data[0].im));
  y->data[0].im = 0.5 * (y_im + (y_re * reconVar2->data[0].im + b_y_im *
    reconVar2->data[0].re));
  y->data[hnRows].re = 0.5 * ((temp1_re * reconVar2->data[0].re - temp1_im *
    reconVar2->data[0].im) + (temp1_re * reconVar1->data[0].re - -temp1_im *
    reconVar1->data[0].im));
  y->data[hnRows].im = 0.5 * ((temp1_re * reconVar2->data[0].im + temp1_im *
    reconVar2->data[0].re) + (temp1_re * reconVar1->data[0].im + -temp1_im *
    reconVar1->data[0].re));
  for (i = 2; i <= iterVar; i++) {
    temp1_re = y->data[i - 1].re;
    temp1_im = y->data[i - 1].im;
    b_i = wrapIndex->data[i - 1];
    temp2_re = y->data[b_i - 1].re;
    temp2_im = y->data[b_i - 1].im;
    y_im = y->data[i - 1].re * reconVar1->data[i - 1].im + y->data[i - 1].im *
      reconVar1->data[i - 1].re;
    y_re = y->data[b_i - 1].re;
    b_y_im = -y->data[b_i - 1].im;
    y->data[i - 1].re = 0.5 * ((y->data[i - 1].re * reconVar1->data[i - 1].re -
      y->data[i - 1].im * reconVar1->data[i - 1].im) + (y_re * reconVar2->data[i
      - 1].re - b_y_im * reconVar2->data[i - 1].im));
    y->data[i - 1].im = 0.5 * (y_im + (y_re * reconVar2->data[i - 1].im + b_y_im
      * reconVar2->data[i - 1].re));
    i1 = (hnRows + i) - 1;
    y->data[i1].re = 0.5 * ((temp1_re * reconVar2->data[i - 1].re - temp1_im *
      reconVar2->data[i - 1].im) + (temp2_re * reconVar1->data[i - 1].re -
      -temp2_im * reconVar1->data[i - 1].im));
    y->data[i1].im = 0.5 * ((temp1_re * reconVar2->data[i - 1].im + temp1_im *
      reconVar2->data[i - 1].re) + (temp2_re * reconVar1->data[i - 1].im +
      -temp2_im * reconVar1->data[i - 1].re));
    y->data[b_i - 1].re = 0.5 * ((temp2_re * reconVar1->data[b_i - 1].re -
      temp2_im * reconVar1->data[b_i - 1].im) + (temp1_re * reconVar2->data[b_i
      - 1].re - -temp1_im * reconVar2->data[b_i - 1].im));
    y->data[b_i - 1].im = 0.5 * ((temp2_re * reconVar1->data[b_i - 1].im +
      temp2_im * reconVar1->data[b_i - 1].re) + (temp1_re * reconVar2->data[b_i
      - 1].im + -temp1_im * reconVar2->data[b_i - 1].re));
    i1 = (b_i + hnRows) - 1;
    y->data[i1].re = 0.5 * ((temp2_re * reconVar2->data[b_i - 1].re - temp2_im *
      reconVar2->data[b_i - 1].im) + (temp1_re * reconVar1->data[b_i - 1].re -
      -temp1_im * reconVar1->data[b_i - 1].im));
    y->data[i1].im = 0.5 * ((temp2_re * reconVar2->data[b_i - 1].im + temp2_im *
      reconVar2->data[b_i - 1].re) + (temp1_re * reconVar1->data[b_i - 1].im +
      -temp1_im * reconVar1->data[b_i - 1].re));
  }

  if (iterVar != 0) {
    temp1_re = y->data[iterVar].re;
    temp1_im = y->data[iterVar].im;
    y_im = y->data[iterVar].re * reconVar1->data[iterVar].im + y->data[iterVar].
      im * reconVar1->data[iterVar].re;
    y_re = y->data[iterVar].re;
    b_y_im = -y->data[iterVar].im;
    y->data[iterVar].re = 0.5 * ((y->data[iterVar].re * reconVar1->data[iterVar]
      .re - y->data[iterVar].im * reconVar1->data[iterVar].im) + (y_re *
      reconVar2->data[iterVar].re - b_y_im * reconVar2->data[iterVar].im));
    y->data[iterVar].im = 0.5 * (y_im + (y_re * reconVar2->data[iterVar].im +
      b_y_im * reconVar2->data[iterVar].re));
    b_i = hnRows + iterVar;
    y->data[b_i].re = 0.5 * ((temp1_re * reconVar2->data[iterVar].re - temp1_im *
      reconVar2->data[iterVar].im) + (temp1_re * reconVar1->data[iterVar].re -
      -temp1_im * reconVar1->data[iterVar].im));
    y->data[b_i].im = 0.5 * ((temp1_re * reconVar2->data[iterVar].im + temp1_im *
      reconVar2->data[iterVar].re) + (temp1_re * reconVar1->data[iterVar].im +
      -temp1_im * reconVar1->data[iterVar].re));
  }
}

/*
 * Arguments    : const emxArray_real_T *x
 *                int n2blue
 *                int nfft
 *                const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                const emxArray_real_T *sintabinv
 *                emxArray_creal_T *y
 * Return Type  : void
 */
void c_FFTImplementationCallback_dob(const emxArray_real_T *x, int n2blue, int
  nfft, const emxArray_real_T *costab, const emxArray_real_T *sintab, const
  emxArray_real_T *sintabinv, emxArray_creal_T *y)
{
  bool ishalflength;
  emxArray_creal_T *wwc;
  int nInt2m1;
  int nRows;
  int nInt2;
  int idx;
  int rt;
  int k;
  int b_y;
  double nt_im;
  double nt_re;
  emxArray_creal_T *fv;
  emxArray_creal_T *b_fv;
  if ((nfft != 1) && ((nfft & 1) == 0)) {
    ishalflength = true;
  } else {
    ishalflength = false;
  }

  acemxInit_creal_T(&wwc, 1);
  if (ishalflength) {
    nRows = nfft / 2;
    nInt2m1 = (nRows + nRows) - 1;
    nInt2 = wwc->size[0];
    wwc->size[0] = nInt2m1;
    acemxEnsureCapacity_creal_T(wwc, nInt2);
    idx = nRows;
    rt = 0;
    wwc->data[nRows - 1].re = 1.0;
    wwc->data[nRows - 1].im = 0.0;
    nInt2 = nRows << 1;
    for (k = 0; k <= nRows - 2; k++) {
      b_y = ((k + 1) << 1) - 1;
      if (nInt2 - rt <= b_y) {
        rt += b_y - nInt2;
      } else {
        rt += b_y;
      }

      nt_im = -3.1415926535897931 * (double)rt / (double)nRows;
      if (nt_im == 0.0) {
        nt_re = 1.0;
        nt_im = 0.0;
      } else {
        nt_re = cos(nt_im);
        nt_im = sin(nt_im);
      }

      wwc->data[idx - 2].re = nt_re;
      wwc->data[idx - 2].im = -nt_im;
      idx--;
    }

    idx = 0;
    nInt2 = nInt2m1 - 1;
    for (k = nInt2; k >= nRows; k--) {
      wwc->data[k] = wwc->data[idx];
      idx++;
    }
  } else {
    nInt2m1 = (nfft + nfft) - 1;
    nInt2 = wwc->size[0];
    wwc->size[0] = nInt2m1;
    acemxEnsureCapacity_creal_T(wwc, nInt2);
    idx = nfft;
    rt = 0;
    wwc->data[nfft - 1].re = 1.0;
    wwc->data[nfft - 1].im = 0.0;
    nInt2 = nfft << 1;
    for (k = 0; k <= nfft - 2; k++) {
      b_y = ((k + 1) << 1) - 1;
      if (nInt2 - rt <= b_y) {
        rt += b_y - nInt2;
      } else {
        rt += b_y;
      }

      nt_im = -3.1415926535897931 * (double)rt / (double)nfft;
      if (nt_im == 0.0) {
        nt_re = 1.0;
        nt_im = 0.0;
      } else {
        nt_re = cos(nt_im);
        nt_im = sin(nt_im);
      }

      wwc->data[idx - 2].re = nt_re;
      wwc->data[idx - 2].im = -nt_im;
      idx--;
    }

    idx = 0;
    nInt2 = nInt2m1 - 1;
    for (k = nInt2; k >= nfft; k--) {
      wwc->data[k] = wwc->data[idx];
      idx++;
    }
  }

  nInt2 = y->size[0];
  y->size[0] = nfft;
  acemxEnsureCapacity_creal_T(y, nInt2);
  if (nfft > x->size[0]) {
    nInt2 = y->size[0];
    y->size[0] = nfft;
    acemxEnsureCapacity_creal_T(y, nInt2);
    for (nInt2 = 0; nInt2 < nfft; nInt2++) {
      y->data[nInt2].re = 0.0;
      y->data[nInt2].im = 0.0;
    }
  }

  if ((n2blue != 1) && ((nfft & 1) == 0)) {
    d_FFTImplementationCallback_doH(x, y, x->size[0], nfft, n2blue, wwc, costab,
      sintab, costab, sintabinv);
  } else {
    rt = x->size[0];
    if (nfft < rt) {
      rt = nfft;
    }

    nInt2 = 0;
    for (k = 0; k < rt; k++) {
      b_y = (nfft + k) - 1;
      y->data[k].re = wwc->data[b_y].re * x->data[nInt2];
      y->data[k].im = wwc->data[b_y].im * -x->data[nInt2];
      nInt2++;
    }

    nInt2 = rt + 1;
    for (k = nInt2; k <= nfft; k++) {
      y->data[k - 1].re = 0.0;
      y->data[k - 1].im = 0.0;
    }

    acemxInit_creal_T(&fv, 1);
    acemxInit_creal_T(&b_fv, 1);
    e_FFTImplementationCallback_r2b(y, n2blue, costab, sintab, fv);
    e_FFTImplementationCallback_r2b(wwc, n2blue, costab, sintab, b_fv);
    nInt2 = b_fv->size[0];
    b_fv->size[0] = fv->size[0];
    acemxEnsureCapacity_creal_T(b_fv, nInt2);
    rt = fv->size[0];
    for (nInt2 = 0; nInt2 < rt; nInt2++) {
      nt_re = fv->data[nInt2].re * b_fv->data[nInt2].im + fv->data[nInt2].im *
        b_fv->data[nInt2].re;
      b_fv->data[nInt2].re = fv->data[nInt2].re * b_fv->data[nInt2].re -
        fv->data[nInt2].im * b_fv->data[nInt2].im;
      b_fv->data[nInt2].im = nt_re;
    }

    f_FFTImplementationCallback_r2b(b_fv, n2blue, costab, sintabinv, fv);
    idx = 0;
    nInt2 = wwc->size[0];
    acemxFree_creal_T(&b_fv);
    for (k = nfft; k <= nInt2; k++) {
      y->data[idx].re = wwc->data[k - 1].re * fv->data[k - 1].re + wwc->data[k -
        1].im * fv->data[k - 1].im;
      y->data[idx].im = wwc->data[k - 1].re * fv->data[k - 1].im - wwc->data[k -
        1].im * fv->data[k - 1].re;
      idx++;
    }

    acemxFree_creal_T(&fv);
  }

  acemxFree_creal_T(&wwc);
}

/*
 * Arguments    : int nRows
 *                bool useRadix2
 *                emxArray_real_T *costab
 *                emxArray_real_T *sintab
 *                emxArray_real_T *sintabinv
 * Return Type  : void
 */
void c_FFTImplementationCallback_gen(int nRows, bool useRadix2, emxArray_real_T *
  costab, emxArray_real_T *sintab, emxArray_real_T *sintabinv)
{
  emxArray_real_T *costab1q;
  double e;
  int n;
  int i;
  int nd2;
  int k;
  acemxInit_real_T(&costab1q, 2);
  e = 6.2831853071795862 / (double)nRows;
  n = nRows / 2 / 2;
  i = costab1q->size[0] * costab1q->size[1];
  costab1q->size[0] = 1;
  costab1q->size[1] = n + 1;
  acemxEnsureCapacity_real_T(costab1q, i);
  costab1q->data[0] = 1.0;
  nd2 = n / 2 - 1;
  for (k = 0; k <= nd2; k++) {
    costab1q->data[k + 1] = cos(e * ((double)k + 1.0));
  }

  i = nd2 + 2;
  nd2 = n - 1;
  for (k = i; k <= nd2; k++) {
    costab1q->data[k] = sin(e * (double)(n - k));
  }

  costab1q->data[n] = 0.0;
  if (!useRadix2) {
    n = costab1q->size[1] - 1;
    nd2 = (costab1q->size[1] - 1) << 1;
    i = costab->size[0] * costab->size[1];
    costab->size[0] = 1;
    costab->size[1] = nd2 + 1;
    acemxEnsureCapacity_real_T(costab, i);
    i = sintab->size[0] * sintab->size[1];
    sintab->size[0] = 1;
    sintab->size[1] = nd2 + 1;
    acemxEnsureCapacity_real_T(sintab, i);
    costab->data[0] = 1.0;
    sintab->data[0] = 0.0;
    i = sintabinv->size[0] * sintabinv->size[1];
    sintabinv->size[0] = 1;
    sintabinv->size[1] = nd2 + 1;
    acemxEnsureCapacity_real_T(sintabinv, i);
    for (k = 0; k < n; k++) {
      sintabinv->data[k + 1] = costab1q->data[(n - k) - 1];
    }

    i = costab1q->size[1];
    for (k = i; k <= nd2; k++) {
      sintabinv->data[k] = costab1q->data[k - n];
    }

    for (k = 0; k < n; k++) {
      costab->data[k + 1] = costab1q->data[k + 1];
      sintab->data[k + 1] = -costab1q->data[(n - k) - 1];
    }

    i = costab1q->size[1];
    for (k = i; k <= nd2; k++) {
      costab->data[k] = -costab1q->data[nd2 - k];
      sintab->data[k] = -costab1q->data[k - n];
    }
  } else {
    n = costab1q->size[1] - 1;
    nd2 = (costab1q->size[1] - 1) << 1;
    i = costab->size[0] * costab->size[1];
    costab->size[0] = 1;
    costab->size[1] = nd2 + 1;
    acemxEnsureCapacity_real_T(costab, i);
    i = sintab->size[0] * sintab->size[1];
    sintab->size[0] = 1;
    sintab->size[1] = nd2 + 1;
    acemxEnsureCapacity_real_T(sintab, i);
    costab->data[0] = 1.0;
    sintab->data[0] = 0.0;
    for (k = 0; k < n; k++) {
      costab->data[k + 1] = costab1q->data[k + 1];
      sintab->data[k + 1] = -costab1q->data[(n - k) - 1];
    }

    i = costab1q->size[1];
    for (k = i; k <= nd2; k++) {
      costab->data[k] = -costab1q->data[nd2 - k];
      sintab->data[k] = -costab1q->data[k - n];
    }

    sintabinv->size[0] = 1;
    sintabinv->size[1] = 0;
  }

  acemxFree_real_T(&costab1q);
}

/*
 * Arguments    : int nfft
 *                bool useRadix2
 *                int *n2blue
 *                int *nRows
 * Return Type  : void
 */
void c_FFTImplementationCallback_get(int nfft, bool useRadix2, int *n2blue, int *
  nRows)
{
  int n;
  int pmax;
  int pmin;
  bool exitg1;
  int k;
  int pow2p;
  *n2blue = 1;
  if (useRadix2) {
    *nRows = nfft;
  } else {
    if (nfft > 0) {
      n = (nfft + nfft) - 1;
      pmax = 31;
      if (n <= 1) {
        pmax = 0;
      } else {
        pmin = 0;
        exitg1 = false;
        while ((!exitg1) && (pmax - pmin > 1)) {
          k = (pmin + pmax) >> 1;
          pow2p = 1 << k;
          if (pow2p == n) {
            pmax = k;
            exitg1 = true;
          } else if (pow2p > n) {
            pmax = k;
          } else {
            pmin = k;
          }
        }
      }

      *n2blue = 1 << pmax;
    }

    *nRows = *n2blue;
  }
}

/*
 * Arguments    : const emxArray_real_T *x
 *                int n1_unsigned
 *                const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                emxArray_creal_T *y
 * Return Type  : void
 */
void c_FFTImplementationCallback_r2b(const emxArray_real_T *x, int n1_unsigned,
  const emxArray_real_T *costab, const emxArray_real_T *sintab, emxArray_creal_T
  *y)
{
  int i;
  int u0;
  int loop_ub;
  int exitg1;
  emxArray_creal_T *b_y;
  i = y->size[0];
  y->size[0] = n1_unsigned;
  acemxEnsureCapacity_creal_T(y, i);
  if (n1_unsigned > x->size[0]) {
    i = y->size[0];
    y->size[0] = n1_unsigned;
    acemxEnsureCapacity_creal_T(y, i);
    for (i = 0; i < n1_unsigned; i++) {
      y->data[i].re = 0.0;
      y->data[i].im = 0.0;
    }
  }

  if (n1_unsigned != 1) {
    c_FFTImplementationCallback_doH(x, y, n1_unsigned, costab, sintab);
  } else {
    u0 = x->size[0];
    if (u0 >= 1) {
      u0 = 1;
    }

    loop_ub = u0 - 2;
    i = 0;
    do {
      if (i <= loop_ub) {
        y->data[0].re = x->data[0];
        y->data[0].im = 0.0;
        exitg1 = 1;
      } else {
        acemxInit_creal_T(&b_y, 1);
        y->data[0].re = x->data[0];
        y->data[0].im = 0.0;
        i = b_y->size[0];
        b_y->size[0] = y->size[0];
        acemxEnsureCapacity_creal_T(b_y, i);
        u0 = y->size[0];
        for (i = 0; i < u0; i++) {
          b_y->data[i] = y->data[i];
        }

        i = y->size[0];
        y->size[0] = b_y->size[0];
        acemxEnsureCapacity_creal_T(y, i);
        u0 = b_y->size[0];
        for (i = 0; i < u0; i++) {
          y->data[i] = b_y->data[i];
        }

        acemxFree_creal_T(&b_y);
        exitg1 = 1;
      }
    } while (exitg1 == 0);
  }
}

/*
 * Arguments    : const emxArray_real_T *x
 *                int n2blue
 *                int nfft
 *                const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                const emxArray_real_T *sintabinv
 *                emxArray_creal_T *y
 * Return Type  : void
 */
void d_FFTImplementationCallback_dob(const emxArray_real_T *x, int n2blue, int
  nfft, const emxArray_real_T *costab, const emxArray_real_T *sintab, const
  emxArray_real_T *sintabinv, emxArray_creal_T *y)
{
  bool ishalflength;
  emxArray_creal_T *wwc;
  int nInt2m1;
  int nRows;
  int nInt2;
  int idx;
  int rt;
  int k;
  int b_y;
  double nt_im;
  double nt_re;
  emxArray_creal_T *fv;
  emxArray_creal_T *b_fv;
  if ((nfft != 1) && ((nfft & 1) == 0)) {
    ishalflength = true;
  } else {
    ishalflength = false;
  }

  acemxInit_creal_T(&wwc, 1);
  if (ishalflength) {
    nRows = nfft / 2;
    nInt2m1 = (nRows + nRows) - 1;
    nInt2 = wwc->size[0];
    wwc->size[0] = nInt2m1;
    acemxEnsureCapacity_creal_T(wwc, nInt2);
    idx = nRows;
    rt = 0;
    wwc->data[nRows - 1].re = 1.0;
    wwc->data[nRows - 1].im = 0.0;
    nInt2 = nRows << 1;
    for (k = 0; k <= nRows - 2; k++) {
      b_y = ((k + 1) << 1) - 1;
      if (nInt2 - rt <= b_y) {
        rt += b_y - nInt2;
      } else {
        rt += b_y;
      }

      nt_im = 3.1415926535897931 * (double)rt / (double)nRows;
      if (nt_im == 0.0) {
        nt_re = 1.0;
        nt_im = 0.0;
      } else {
        nt_re = cos(nt_im);
        nt_im = sin(nt_im);
      }

      wwc->data[idx - 2].re = nt_re;
      wwc->data[idx - 2].im = -nt_im;
      idx--;
    }

    idx = 0;
    nInt2 = nInt2m1 - 1;
    for (k = nInt2; k >= nRows; k--) {
      wwc->data[k] = wwc->data[idx];
      idx++;
    }
  } else {
    nInt2m1 = (nfft + nfft) - 1;
    nInt2 = wwc->size[0];
    wwc->size[0] = nInt2m1;
    acemxEnsureCapacity_creal_T(wwc, nInt2);
    idx = nfft;
    rt = 0;
    wwc->data[nfft - 1].re = 1.0;
    wwc->data[nfft - 1].im = 0.0;
    nInt2 = nfft << 1;
    for (k = 0; k <= nfft - 2; k++) {
      b_y = ((k + 1) << 1) - 1;
      if (nInt2 - rt <= b_y) {
        rt += b_y - nInt2;
      } else {
        rt += b_y;
      }

      nt_im = 3.1415926535897931 * (double)rt / (double)nfft;
      if (nt_im == 0.0) {
        nt_re = 1.0;
        nt_im = 0.0;
      } else {
        nt_re = cos(nt_im);
        nt_im = sin(nt_im);
      }

      wwc->data[idx - 2].re = nt_re;
      wwc->data[idx - 2].im = -nt_im;
      idx--;
    }

    idx = 0;
    nInt2 = nInt2m1 - 1;
    for (k = nInt2; k >= nfft; k--) {
      wwc->data[k] = wwc->data[idx];
      idx++;
    }
  }

  nInt2 = y->size[0];
  y->size[0] = nfft;
  acemxEnsureCapacity_creal_T(y, nInt2);
  if (nfft > x->size[0]) {
    nInt2 = y->size[0];
    y->size[0] = nfft;
    acemxEnsureCapacity_creal_T(y, nInt2);
    for (nInt2 = 0; nInt2 < nfft; nInt2++) {
      y->data[nInt2].re = 0.0;
      y->data[nInt2].im = 0.0;
    }
  }

  if ((n2blue != 1) && ((nfft & 1) == 0)) {
    e_FFTImplementationCallback_doH(x, y, x->size[0], nfft, n2blue, wwc, costab,
      sintab, costab, sintabinv);
  } else {
    rt = x->size[0];
    if (nfft < rt) {
      rt = nfft;
    }

    nInt2 = 0;
    for (k = 0; k < rt; k++) {
      b_y = (nfft + k) - 1;
      y->data[k].re = wwc->data[b_y].re * x->data[nInt2];
      y->data[k].im = wwc->data[b_y].im * -x->data[nInt2];
      nInt2++;
    }

    nInt2 = rt + 1;
    for (k = nInt2; k <= nfft; k++) {
      y->data[k - 1].re = 0.0;
      y->data[k - 1].im = 0.0;
    }

    acemxInit_creal_T(&fv, 1);
    acemxInit_creal_T(&b_fv, 1);
    e_FFTImplementationCallback_r2b(y, n2blue, costab, sintab, fv);
    e_FFTImplementationCallback_r2b(wwc, n2blue, costab, sintab, b_fv);
    nInt2 = b_fv->size[0];
    b_fv->size[0] = fv->size[0];
    acemxEnsureCapacity_creal_T(b_fv, nInt2);
    rt = fv->size[0];
    for (nInt2 = 0; nInt2 < rt; nInt2++) {
      nt_re = fv->data[nInt2].re * b_fv->data[nInt2].im + fv->data[nInt2].im *
        b_fv->data[nInt2].re;
      b_fv->data[nInt2].re = fv->data[nInt2].re * b_fv->data[nInt2].re -
        fv->data[nInt2].im * b_fv->data[nInt2].im;
      b_fv->data[nInt2].im = nt_re;
    }

    f_FFTImplementationCallback_r2b(b_fv, n2blue, costab, sintabinv, fv);
    idx = 0;
    nInt2 = wwc->size[0];
    acemxFree_creal_T(&b_fv);
    for (k = nfft; k <= nInt2; k++) {
      y->data[idx].re = wwc->data[k - 1].re * fv->data[k - 1].re + wwc->data[k -
        1].im * fv->data[k - 1].im;
      y->data[idx].im = wwc->data[k - 1].re * fv->data[k - 1].im - wwc->data[k -
        1].im * fv->data[k - 1].re;
      if (y->data[idx].im == 0.0) {
        nt_im = y->data[idx].re / (double)nfft;
        nt_re = 0.0;
      } else if (y->data[idx].re == 0.0) {
        nt_im = 0.0;
        nt_re = y->data[idx].im / (double)nfft;
      } else {
        nt_im = y->data[idx].re / (double)nfft;
        nt_re = y->data[idx].im / (double)nfft;
      }

      y->data[idx].re = nt_im;
      y->data[idx].im = nt_re;
      idx++;
    }

    acemxFree_creal_T(&fv);
  }

  acemxFree_creal_T(&wwc);
}

/*
 * Arguments    : int nRows
 *                bool useRadix2
 *                emxArray_real_T *costab
 *                emxArray_real_T *sintab
 *                emxArray_real_T *sintabinv
 * Return Type  : void
 */
void e_FFTImplementationCallback_gen(int nRows, bool useRadix2, emxArray_real_T *
  costab, emxArray_real_T *sintab, emxArray_real_T *sintabinv)
{
  emxArray_real_T *costab1q;
  double e;
  int n;
  int i;
  int nd2;
  int k;
  acemxInit_real_T(&costab1q, 2);
  e = 6.2831853071795862 / (double)nRows;
  n = nRows / 2 / 2;
  i = costab1q->size[0] * costab1q->size[1];
  costab1q->size[0] = 1;
  costab1q->size[1] = n + 1;
  acemxEnsureCapacity_real_T(costab1q, i);
  costab1q->data[0] = 1.0;
  nd2 = n / 2 - 1;
  for (k = 0; k <= nd2; k++) {
    costab1q->data[k + 1] = cos(e * ((double)k + 1.0));
  }

  i = nd2 + 2;
  nd2 = n - 1;
  for (k = i; k <= nd2; k++) {
    costab1q->data[k] = sin(e * (double)(n - k));
  }

  costab1q->data[n] = 0.0;
  if (!useRadix2) {
    n = costab1q->size[1] - 1;
    nd2 = (costab1q->size[1] - 1) << 1;
    i = costab->size[0] * costab->size[1];
    costab->size[0] = 1;
    costab->size[1] = nd2 + 1;
    acemxEnsureCapacity_real_T(costab, i);
    i = sintab->size[0] * sintab->size[1];
    sintab->size[0] = 1;
    sintab->size[1] = nd2 + 1;
    acemxEnsureCapacity_real_T(sintab, i);
    costab->data[0] = 1.0;
    sintab->data[0] = 0.0;
    i = sintabinv->size[0] * sintabinv->size[1];
    sintabinv->size[0] = 1;
    sintabinv->size[1] = nd2 + 1;
    acemxEnsureCapacity_real_T(sintabinv, i);
    for (k = 0; k < n; k++) {
      sintabinv->data[k + 1] = costab1q->data[(n - k) - 1];
    }

    i = costab1q->size[1];
    for (k = i; k <= nd2; k++) {
      sintabinv->data[k] = costab1q->data[k - n];
    }

    for (k = 0; k < n; k++) {
      costab->data[k + 1] = costab1q->data[k + 1];
      sintab->data[k + 1] = -costab1q->data[(n - k) - 1];
    }

    i = costab1q->size[1];
    for (k = i; k <= nd2; k++) {
      costab->data[k] = -costab1q->data[nd2 - k];
      sintab->data[k] = -costab1q->data[k - n];
    }
  } else {
    n = costab1q->size[1] - 1;
    nd2 = (costab1q->size[1] - 1) << 1;
    i = costab->size[0] * costab->size[1];
    costab->size[0] = 1;
    costab->size[1] = nd2 + 1;
    acemxEnsureCapacity_real_T(costab, i);
    i = sintab->size[0] * sintab->size[1];
    sintab->size[0] = 1;
    sintab->size[1] = nd2 + 1;
    acemxEnsureCapacity_real_T(sintab, i);
    costab->data[0] = 1.0;
    sintab->data[0] = 0.0;
    for (k = 0; k < n; k++) {
      costab->data[k + 1] = costab1q->data[k + 1];
      sintab->data[k + 1] = costab1q->data[(n - k) - 1];
    }

    i = costab1q->size[1];
    for (k = i; k <= nd2; k++) {
      costab->data[k] = -costab1q->data[nd2 - k];
      sintab->data[k] = costab1q->data[k - n];
    }

    sintabinv->size[0] = 1;
    sintabinv->size[1] = 0;
  }

  acemxFree_real_T(&costab1q);
}

/*
 * Arguments    : const emxArray_real_T *x
 *                int n1_unsigned
 *                const emxArray_real_T *costab
 *                const emxArray_real_T *sintab
 *                emxArray_creal_T *y
 * Return Type  : void
 */
void g_FFTImplementationCallback_r2b(const emxArray_real_T *x, int n1_unsigned,
  const emxArray_real_T *costab, const emxArray_real_T *sintab, emxArray_creal_T
  *y)
{
  int i;
  bool guard1 = false;
  int u0;
  int loop_ub;
  double b;
  int exitg1;
  emxArray_creal_T *b_y;
  i = y->size[0];
  y->size[0] = n1_unsigned;
  acemxEnsureCapacity_creal_T(y, i);
  if (n1_unsigned > x->size[0]) {
    i = y->size[0];
    y->size[0] = n1_unsigned;
    acemxEnsureCapacity_creal_T(y, i);
    for (i = 0; i < n1_unsigned; i++) {
      y->data[i].re = 0.0;
      y->data[i].im = 0.0;
    }
  }

  guard1 = false;
  if (n1_unsigned != 1) {
    c_FFTImplementationCallback_doH(x, y, n1_unsigned, costab, sintab);
    guard1 = true;
  } else {
    u0 = x->size[0];
    if (u0 >= 1) {
      u0 = 1;
    }

    loop_ub = u0 - 2;
    i = 0;
    do {
      if (i <= loop_ub) {
        y->data[0].re = x->data[0];
        y->data[0].im = 0.0;
        exitg1 = 1;
      } else {
        acemxInit_creal_T(&b_y, 1);
        y->data[0].re = x->data[0];
        y->data[0].im = 0.0;
        i = b_y->size[0];
        b_y->size[0] = y->size[0];
        acemxEnsureCapacity_creal_T(b_y, i);
        u0 = y->size[0];
        for (i = 0; i < u0; i++) {
          b_y->data[i] = y->data[i];
        }

        i = y->size[0];
        y->size[0] = b_y->size[0];
        acemxEnsureCapacity_creal_T(y, i);
        u0 = b_y->size[0];
        for (i = 0; i < u0; i++) {
          y->data[i] = b_y->data[i];
        }

        acemxFree_creal_T(&b_y);
        guard1 = true;
        exitg1 = 1;
      }
    } while (exitg1 == 0);
  }

  if (guard1 && (y->size[0] > 1)) {
    b = 1.0 / (double)y->size[0];
    u0 = y->size[0];
    for (i = 0; i < u0; i++) {
      y->data[i].re *= b;
      y->data[i].im *= b;
    }
  }
}

/*
 * File trailer for FFTImplementationCallback.c
 *
 * [EOF]
 */
