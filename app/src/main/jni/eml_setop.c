/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: eml_setop.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 16-Nov-2020 11:39:11
 */

/* Include Files */
#include "eml_setop.h"
#include "phase_sep.h"
#include "phase_sep_emxutil.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_int32_T *a
 *                const emxArray_int32_T *b
 *                emxArray_int32_T *c
 *                emxArray_int32_T *ia
 *                emxArray_int32_T *ib
 * Return Type  : void
 */
void do_vectors(const emxArray_int32_T *a, const emxArray_int32_T *b,
                emxArray_int32_T *c, emxArray_int32_T *ia, emxArray_int32_T *ib)
{
  int na;
  int nb;
  int ncmax;
  int iafirst;
  int nc;
  int nia;
  int nib;
  int ialast;
  int ibfirst;
  int iblast;
  int b_ialast;
  int ak;
  int b_iblast;
  int bk;
  na = a->size[0];
  nb = b->size[0];
  ncmax = a->size[0] + b->size[0];
  iafirst = c->size[0];
  c->size[0] = ncmax;
  emxEnsureCapacity_int32_T(c, iafirst);
  iafirst = ia->size[0];
  ia->size[0] = a->size[0];
  emxEnsureCapacity_int32_T(ia, iafirst);
  iafirst = ib->size[0];
  ib->size[0] = b->size[0];
  emxEnsureCapacity_int32_T(ib, iafirst);
  nc = -1;
  nia = -1;
  nib = 0;
  iafirst = 1;
  ialast = 0;
  ibfirst = 0;
  iblast = 0;
  while ((ialast + 1 <= na) && (iblast + 1 <= nb)) {
    b_ialast = ialast + 1;
    ak = a->data[ialast];
    while ((b_ialast < a->size[0]) && (a->data[b_ialast] == ak)) {
      b_ialast++;
    }

    ialast = b_ialast - 1;
    b_iblast = iblast + 1;
    bk = b->data[iblast];
    while ((b_iblast < b->size[0]) && (b->data[b_iblast] == bk)) {
      b_iblast++;
    }

    iblast = b_iblast - 1;
    if (ak == bk) {
      nc++;
      c->data[nc] = ak;
      nia++;
      ia->data[nia] = iafirst;
      ialast = b_ialast;
      iafirst = b_ialast + 1;
      iblast = b_iblast;
      ibfirst = b_iblast;
    } else if (ak < bk) {
      nc++;
      nia++;
      c->data[nc] = ak;
      ia->data[nia] = iafirst;
      ialast = b_ialast;
      iafirst = b_ialast + 1;
    } else {
      nc++;
      nib++;
      c->data[nc] = bk;
      ib->data[nib - 1] = ibfirst + 1;
      iblast = b_iblast;
      ibfirst = b_iblast;
    }
  }

  while (ialast + 1 <= na) {
    b_ialast = ialast + 1;
    while ((b_ialast < a->size[0]) && (a->data[b_ialast] == a->data[ialast])) {
      b_ialast++;
    }

    nc++;
    nia++;
    c->data[nc] = a->data[ialast];
    ia->data[nia] = iafirst;
    ialast = b_ialast;
    iafirst = b_ialast + 1;
  }

  while (iblast + 1 <= nb) {
    b_iblast = iblast + 1;
    while ((b_iblast < b->size[0]) && (b->data[b_iblast] == b->data[iblast])) {
      b_iblast++;
    }

    nc++;
    nib++;
    c->data[nc] = b->data[iblast];
    ib->data[nib - 1] = ibfirst + 1;
    iblast = b_iblast;
    ibfirst = b_iblast;
  }

  if (a->size[0] > 0) {
    iafirst = ia->size[0];
    if (1 > nia + 1) {
      ia->size[0] = 0;
    } else {
      ia->size[0] = nia + 1;
    }

    emxEnsureCapacity_int32_T(ia, iafirst);
  }

  if (b->size[0] > 0) {
    iafirst = ib->size[0];
    if (1 > nib) {
      ib->size[0] = 0;
    } else {
      ib->size[0] = nib;
    }

    emxEnsureCapacity_int32_T(ib, iafirst);
  }

  if (ncmax > 0) {
    iafirst = c->size[0];
    if (1 > nc + 1) {
      c->size[0] = 0;
    } else {
      c->size[0] = nc + 1;
    }

    emxEnsureCapacity_int32_T(c, iafirst);
  }
}

/*
 * File trailer for eml_setop.c
 *
 * [EOF]
 */
