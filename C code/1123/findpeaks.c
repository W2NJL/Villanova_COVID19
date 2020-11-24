/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: findpeaks.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 16-Nov-2020 11:39:11
 */

/* Include Files */
#include "findpeaks.h"
#include "eml_setop.h"
#include "phase_sep.h"
#include "phase_sep_emxutil.h"
#include "rt_nonfinite.h"
#include "sort.h"
#include "sortIdx.h"
#include <math.h>

/* Function Declarations */
static void c_findPeaksSeparatedByMoreThanM(const emxArray_real_T *y, const
  emxArray_real_T *x, const emxArray_int32_T *iPk, emxArray_int32_T *idx);

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *y
 *                const emxArray_real_T *x
 *                const emxArray_int32_T *iPk
 *                emxArray_int32_T *idx
 * Return Type  : void
 */
static void c_findPeaksSeparatedByMoreThanM(const emxArray_real_T *y, const
  emxArray_real_T *x, const emxArray_int32_T *iPk, emxArray_int32_T *idx)
{
  emxArray_real_T *locs_temp;
  int i;
  int loop_ub;
  emxArray_int32_T *c_sortIdx;
  emxArray_boolean_T *idelete;
  emxArray_boolean_T *r;
  int b_i;
  int i1;
  int end;
  emxArray_int32_T *r1;
  if (iPk->size[0] == 0) {
    idx->size[0] = 0;
  } else {
    phaseemxInit_real_T(&locs_temp, 1);
    i = locs_temp->size[0];
    locs_temp->size[0] = iPk->size[0];
    phaseemxEnsureCapacity_real_T(locs_temp, i);
    loop_ub = iPk->size[0];
    for (i = 0; i < loop_ub; i++) {
      locs_temp->data[i] = y->data[iPk->data[i] - 1];
    }

    emxInit_int32_T(&c_sortIdx, 1);
    sortIdx(locs_temp, c_sortIdx);
    i = locs_temp->size[0];
    locs_temp->size[0] = c_sortIdx->size[0];
    phaseemxEnsureCapacity_real_T(locs_temp, i);
    loop_ub = c_sortIdx->size[0];
    for (i = 0; i < loop_ub; i++) {
      locs_temp->data[i] = x->data[iPk->data[c_sortIdx->data[i] - 1] - 1];
    }

    emxInit_boolean_T(&idelete, 1);
    i = idelete->size[0];
    idelete->size[0] = c_sortIdx->size[0];
    emxEnsureCapacity_boolean_T(idelete, i);
    loop_ub = c_sortIdx->size[0];
    for (i = 0; i < loop_ub; i++) {
      idelete->data[i] = false;
    }

    i = c_sortIdx->size[0];
    emxInit_boolean_T(&r, 1);
    for (b_i = 0; b_i < i; b_i++) {
      if (!idelete->data[b_i]) {
        i1 = r->size[0];
        r->size[0] = locs_temp->size[0];
        emxEnsureCapacity_boolean_T(r, i1);
        loop_ub = locs_temp->size[0];
        for (i1 = 0; i1 < loop_ub; i1++) {
          end = iPk->data[c_sortIdx->data[b_i] - 1] - 1;
          r->data[i1] = ((locs_temp->data[i1] >= x->data[end] - 40000.0) &&
                         (locs_temp->data[i1] <= x->data[end] + 40000.0));
        }

        loop_ub = idelete->size[0];
        for (i1 = 0; i1 < loop_ub; i1++) {
          idelete->data[i1] = (idelete->data[i1] || r->data[i1]);
        }

        idelete->data[b_i] = false;
      }
    }

    emxFree_boolean_T(&r);
    phaseemxFree_real_T(&locs_temp);
    end = idelete->size[0] - 1;
    loop_ub = 0;
    for (b_i = 0; b_i <= end; b_i++) {
      if (!idelete->data[b_i]) {
        loop_ub++;
      }
    }

    emxInit_int32_T(&r1, 1);
    i = r1->size[0];
    r1->size[0] = loop_ub;
    emxEnsureCapacity_int32_T(r1, i);
    loop_ub = 0;
    for (b_i = 0; b_i <= end; b_i++) {
      if (!idelete->data[b_i]) {
        r1->data[loop_ub] = b_i + 1;
        loop_ub++;
      }
    }

    emxFree_boolean_T(&idelete);
    i = idx->size[0];
    idx->size[0] = r1->size[0];
    emxEnsureCapacity_int32_T(idx, i);
    loop_ub = r1->size[0];
    for (i = 0; i < loop_ub; i++) {
      idx->data[i] = c_sortIdx->data[r1->data[i] - 1];
    }

    emxFree_int32_T(&r1);
    emxFree_int32_T(&c_sortIdx);
    sort(idx);
  }
}

/*
 * Arguments    : const emxArray_real_T *Yin
 *                emxArray_real_T *Ypk
 *                emxArray_real_T *Xpk
 * Return Type  : void
 */
void findpeaks(const emxArray_real_T *Yin, emxArray_real_T *Ypk, emxArray_real_T
               *Xpk)
{
  emxArray_int32_T *iFinite;
  emxArray_int32_T *iInfinite;
  emxArray_int32_T *iInflect;
  int i;
  int ny;
  int nPk;
  int nInf;
  char dir;
  int kfirst;
  double ykfirst;
  bool isinfykfirst;
  int k;
  emxArray_int32_T *iPk;
  double yk;
  bool isinfyk;
  char previousdir;
  emxArray_int32_T *c;
  emxArray_real_T *r;
  emxArray_int32_T *idx;
  emxArray_real_T b_Yin;
  int c_Yin[1];
  emxArray_int32_T *b_idx;
  emxInit_int32_T(&iFinite, 1);
  emxInit_int32_T(&iInfinite, 1);
  emxInit_int32_T(&iInflect, 1);
  i = iFinite->size[0];
  iFinite->size[0] = Yin->size[1];
  emxEnsureCapacity_int32_T(iFinite, i);
  i = iInfinite->size[0];
  iInfinite->size[0] = Yin->size[1];
  emxEnsureCapacity_int32_T(iInfinite, i);
  ny = Yin->size[1];
  nPk = 0;
  nInf = 0;
  dir = 'n';
  kfirst = 0;
  ykfirst = rtInf;
  isinfykfirst = true;
  for (k = 1; k <= ny; k++) {
    yk = Yin->data[k - 1];
    if (rtIsNaN(yk)) {
      yk = rtInf;
      isinfyk = true;
    } else if (rtIsInf(yk) && (yk > 0.0)) {
      isinfyk = true;
      nInf++;
      iInfinite->data[nInf - 1] = k;
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
          iFinite->data[nPk - 1] = kfirst;
        }
      } else {
        dir = 'i';
      }

      ykfirst = yk;
      kfirst = k;
      isinfykfirst = isinfyk;
    }
  }

  emxInit_int32_T(&iPk, 1);
  if (1 > nPk) {
    i = 0;
  } else {
    i = nPk;
  }

  ny = iFinite->size[0];
  iFinite->size[0] = i;
  emxEnsureCapacity_int32_T(iFinite, ny);
  ny = iInfinite->size[0];
  if (1 > nInf) {
    iInfinite->size[0] = 0;
  } else {
    iInfinite->size[0] = nInf;
  }

  emxEnsureCapacity_int32_T(iInfinite, ny);
  ny = iPk->size[0];
  iPk->size[0] = i;
  emxEnsureCapacity_int32_T(iPk, ny);
  nPk = 0;
  for (k = 0; k < i; k++) {
    ykfirst = Yin->data[iFinite->data[k] - 1];
    if ((ykfirst > rtMinusInf) && (ykfirst - fmax(Yin->data[iFinite->data[k] - 2],
          Yin->data[iFinite->data[k]]) >= 0.0)) {
      nPk++;
      iPk->data[nPk - 1] = iFinite->data[k];
    }
  }

  emxInit_int32_T(&c, 1);
  phaseemxInit_real_T(&r, 1);
  i = iPk->size[0];
  if (1 > nPk) {
    iPk->size[0] = 0;
  } else {
    iPk->size[0] = nPk;
  }

  emxEnsureCapacity_int32_T(iPk, i);
  do_vectors(iPk, iInfinite, c, iInflect, iFinite);
  ny = Yin->size[1];
  i = r->size[0];
  r->size[0] = (int)((double)Yin->size[1] - 1.0) + 1;
  phaseemxEnsureCapacity_real_T(r, i);
  kfirst = (int)((double)Yin->size[1] - 1.0);
  emxFree_int32_T(&iPk);
  emxFree_int32_T(&iInflect);
  emxFree_int32_T(&iInfinite);
  emxFree_int32_T(&iFinite);
  for (i = 0; i <= kfirst; i++) {
    r->data[i] = (double)i + 1.0;
  }

  emxInit_int32_T(&idx, 1);
  b_Yin = *Yin;
  c_Yin[0] = ny;
  b_Yin.size = &c_Yin[0];
  b_Yin.numDimensions = 1;
  c_findPeaksSeparatedByMoreThanM(&b_Yin, r, c, idx);
  phaseemxFree_real_T(&r);
  emxInit_int32_T(&b_idx, 1);
  if (idx->size[0] > Yin->size[1]) {
    kfirst = Yin->size[1];
    i = b_idx->size[0];
    b_idx->size[0] = Yin->size[1];
    emxEnsureCapacity_int32_T(b_idx, i);
    for (i = 0; i < kfirst; i++) {
      b_idx->data[i] = idx->data[i];
    }

    i = idx->size[0];
    idx->size[0] = b_idx->size[0];
    emxEnsureCapacity_int32_T(idx, i);
    kfirst = b_idx->size[0];
    for (i = 0; i < kfirst; i++) {
      idx->data[i] = b_idx->data[i];
    }
  }

  i = b_idx->size[0];
  b_idx->size[0] = idx->size[0];
  emxEnsureCapacity_int32_T(b_idx, i);
  kfirst = idx->size[0];
  for (i = 0; i < kfirst; i++) {
    b_idx->data[i] = c->data[idx->data[i] - 1];
  }

  emxFree_int32_T(&c);
  emxFree_int32_T(&idx);
  i = Ypk->size[0] * Ypk->size[1];
  Ypk->size[0] = 1;
  Ypk->size[1] = b_idx->size[0];
  phaseemxEnsureCapacity_real_T(Ypk, i);
  kfirst = b_idx->size[0];
  for (i = 0; i < kfirst; i++) {
    Ypk->data[i] = Yin->data[b_idx->data[i] - 1];
  }

  i = Xpk->size[0] * Xpk->size[1];
  Xpk->size[0] = 1;
  Xpk->size[1] = b_idx->size[0];
  phaseemxEnsureCapacity_real_T(Xpk, i);
  kfirst = b_idx->size[0];
  for (i = 0; i < kfirst; i++) {
    Xpk->data[i] = b_idx->data[i];
  }

  emxFree_int32_T(&b_idx);
}

/*
 * File trailer for findpeaks.c
 *
 * [EOF]
 */
