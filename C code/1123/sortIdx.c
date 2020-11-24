/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: sortIdx.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 16-Nov-2020 11:39:11
 */

/* Include Files */
#include "sortIdx.h"
#include "phase_sep.h"
#include "phase_sep_emxutil.h"
#include "rt_nonfinite.h"

/* Function Declarations */
static void b_merge(emxArray_int32_T *idx, emxArray_real_T *x, int offset, int
                    np, int nq, emxArray_int32_T *iwork, emxArray_real_T *xwork);
static void merge(emxArray_int32_T *idx, emxArray_int32_T *x, int offset, int np,
                  int nq, emxArray_int32_T *iwork, emxArray_int32_T *xwork);
static void merge_block(emxArray_int32_T *idx, emxArray_int32_T *x, int offset,
  int n, int preSortLevel, emxArray_int32_T *iwork, emxArray_int32_T *xwork);
static void merge_pow2_block(emxArray_int32_T *idx, emxArray_int32_T *x, int
  offset);

/* Function Definitions */

/*
 * Arguments    : emxArray_int32_T *idx
 *                emxArray_real_T *x
 *                int offset
 *                int np
 *                int nq
 *                emxArray_int32_T *iwork
 *                emxArray_real_T *xwork
 * Return Type  : void
 */
static void b_merge(emxArray_int32_T *idx, emxArray_real_T *x, int offset, int
                    np, int nq, emxArray_int32_T *iwork, emxArray_real_T *xwork)
{
  int n_tmp;
  int j;
  int p;
  int iout;
  int q;
  int exitg1;
  if (nq != 0) {
    n_tmp = np + nq;
    for (j = 0; j < n_tmp; j++) {
      iout = offset + j;
      iwork->data[j] = idx->data[iout];
      xwork->data[j] = x->data[iout];
    }

    p = 0;
    q = np;
    iout = offset - 1;
    do {
      exitg1 = 0;
      iout++;
      if (xwork->data[p] <= xwork->data[q]) {
        idx->data[iout] = iwork->data[p];
        x->data[iout] = xwork->data[p];
        if (p + 1 < np) {
          p++;
        } else {
          exitg1 = 1;
        }
      } else {
        idx->data[iout] = iwork->data[q];
        x->data[iout] = xwork->data[q];
        if (q + 1 < n_tmp) {
          q++;
        } else {
          q = iout - p;
          for (j = p + 1; j <= np; j++) {
            iout = q + j;
            idx->data[iout] = iwork->data[j - 1];
            x->data[iout] = xwork->data[j - 1];
          }

          exitg1 = 1;
        }
      }
    } while (exitg1 == 0);
  }
}

/*
 * Arguments    : emxArray_int32_T *idx
 *                emxArray_int32_T *x
 *                int offset
 *                int np
 *                int nq
 *                emxArray_int32_T *iwork
 *                emxArray_int32_T *xwork
 * Return Type  : void
 */
static void merge(emxArray_int32_T *idx, emxArray_int32_T *x, int offset, int np,
                  int nq, emxArray_int32_T *iwork, emxArray_int32_T *xwork)
{
  int n_tmp;
  int j;
  int p;
  int iout;
  int q;
  int exitg1;
  if (nq != 0) {
    n_tmp = np + nq;
    for (j = 0; j < n_tmp; j++) {
      iout = offset + j;
      iwork->data[j] = idx->data[iout];
      xwork->data[j] = x->data[iout];
    }

    p = 0;
    q = np;
    iout = offset - 1;
    do {
      exitg1 = 0;
      iout++;
      if (xwork->data[p] <= xwork->data[q]) {
        idx->data[iout] = iwork->data[p];
        x->data[iout] = xwork->data[p];
        if (p + 1 < np) {
          p++;
        } else {
          exitg1 = 1;
        }
      } else {
        idx->data[iout] = iwork->data[q];
        x->data[iout] = xwork->data[q];
        if (q + 1 < n_tmp) {
          q++;
        } else {
          q = iout - p;
          for (j = p + 1; j <= np; j++) {
            iout = q + j;
            idx->data[iout] = iwork->data[j - 1];
            x->data[iout] = xwork->data[j - 1];
          }

          exitg1 = 1;
        }
      }
    } while (exitg1 == 0);
  }
}

/*
 * Arguments    : emxArray_int32_T *idx
 *                emxArray_int32_T *x
 *                int offset
 *                int n
 *                int preSortLevel
 *                emxArray_int32_T *iwork
 *                emxArray_int32_T *xwork
 * Return Type  : void
 */
static void merge_block(emxArray_int32_T *idx, emxArray_int32_T *x, int offset,
  int n, int preSortLevel, emxArray_int32_T *iwork, emxArray_int32_T *xwork)
{
  int nPairs;
  int bLen;
  int tailOffset;
  int nTail;
  nPairs = n >> preSortLevel;
  bLen = 1 << preSortLevel;
  while (nPairs > 1) {
    if ((nPairs & 1) != 0) {
      nPairs--;
      tailOffset = bLen * nPairs;
      nTail = n - tailOffset;
      if (nTail > bLen) {
        merge(idx, x, offset + tailOffset, bLen, nTail - bLen, iwork, xwork);
      }
    }

    tailOffset = bLen << 1;
    nPairs >>= 1;
    for (nTail = 0; nTail < nPairs; nTail++) {
      merge(idx, x, offset + nTail * tailOffset, bLen, bLen, iwork, xwork);
    }

    bLen = tailOffset;
  }

  if (n > bLen) {
    merge(idx, x, offset, bLen, n - bLen, iwork, xwork);
  }
}

/*
 * Arguments    : emxArray_int32_T *idx
 *                emxArray_int32_T *x
 *                int offset
 * Return Type  : void
 */
static void merge_pow2_block(emxArray_int32_T *idx, emxArray_int32_T *x, int
  offset)
{
  int b;
  int bLen;
  int bLen2;
  int nPairs;
  int k;
  int blockOffset;
  int j;
  int p;
  int iout;
  int q;
  int iwork[256];
  int xwork[256];
  int exitg1;
  for (b = 0; b < 6; b++) {
    bLen = 1 << (b + 2);
    bLen2 = bLen << 1;
    nPairs = 256 >> (b + 3);
    for (k = 0; k < nPairs; k++) {
      blockOffset = offset + k * bLen2;
      for (j = 0; j < bLen2; j++) {
        iout = blockOffset + j;
        iwork[j] = idx->data[iout];
        xwork[j] = x->data[iout];
      }

      p = 0;
      q = bLen;
      iout = blockOffset - 1;
      do {
        exitg1 = 0;
        iout++;
        if (xwork[p] <= xwork[q]) {
          idx->data[iout] = iwork[p];
          x->data[iout] = xwork[p];
          if (p + 1 < bLen) {
            p++;
          } else {
            exitg1 = 1;
          }
        } else {
          idx->data[iout] = iwork[q];
          x->data[iout] = xwork[q];
          if (q + 1 < bLen2) {
            q++;
          } else {
            iout -= p;
            for (j = p + 1; j <= bLen; j++) {
              q = iout + j;
              idx->data[q] = iwork[j - 1];
              x->data[q] = xwork[j - 1];
            }

            exitg1 = 1;
          }
        }
      } while (exitg1 == 0);
    }
  }
}

/*
 * Arguments    : emxArray_int32_T *idx
 *                emxArray_real_T *x
 *                int offset
 *                int n
 *                int preSortLevel
 *                emxArray_int32_T *iwork
 *                emxArray_real_T *xwork
 * Return Type  : void
 */
void b_merge_block(emxArray_int32_T *idx, emxArray_real_T *x, int offset, int n,
                   int preSortLevel, emxArray_int32_T *iwork, emxArray_real_T
                   *xwork)
{
  int nPairs;
  int bLen;
  int tailOffset;
  int nTail;
  nPairs = n >> preSortLevel;
  bLen = 1 << preSortLevel;
  while (nPairs > 1) {
    if ((nPairs & 1) != 0) {
      nPairs--;
      tailOffset = bLen * nPairs;
      nTail = n - tailOffset;
      if (nTail > bLen) {
        b_merge(idx, x, offset + tailOffset, bLen, nTail - bLen, iwork, xwork);
      }
    }

    tailOffset = bLen << 1;
    nPairs >>= 1;
    for (nTail = 0; nTail < nPairs; nTail++) {
      b_merge(idx, x, offset + nTail * tailOffset, bLen, bLen, iwork, xwork);
    }

    bLen = tailOffset;
  }

  if (n > bLen) {
    b_merge(idx, x, offset, bLen, n - bLen, iwork, xwork);
  }
}

/*
 * Arguments    : emxArray_int32_T *idx
 *                emxArray_real_T *x
 *                int offset
 * Return Type  : void
 */
void b_merge_pow2_block(emxArray_int32_T *idx, emxArray_real_T *x, int offset)
{
  int b;
  int bLen;
  int bLen2;
  int nPairs;
  int k;
  int blockOffset;
  int j;
  int p;
  int iout;
  int q;
  int iwork[256];
  double xwork[256];
  int exitg1;
  for (b = 0; b < 6; b++) {
    bLen = 1 << (b + 2);
    bLen2 = bLen << 1;
    nPairs = 256 >> (b + 3);
    for (k = 0; k < nPairs; k++) {
      blockOffset = offset + k * bLen2;
      for (j = 0; j < bLen2; j++) {
        iout = blockOffset + j;
        iwork[j] = idx->data[iout];
        xwork[j] = x->data[iout];
      }

      p = 0;
      q = bLen;
      iout = blockOffset - 1;
      do {
        exitg1 = 0;
        iout++;
        if (xwork[p] <= xwork[q]) {
          idx->data[iout] = iwork[p];
          x->data[iout] = xwork[p];
          if (p + 1 < bLen) {
            p++;
          } else {
            exitg1 = 1;
          }
        } else {
          idx->data[iout] = iwork[q];
          x->data[iout] = xwork[q];
          if (q + 1 < bLen2) {
            q++;
          } else {
            iout -= p;
            for (j = p + 1; j <= bLen; j++) {
              q = iout + j;
              idx->data[q] = iwork[j - 1];
              x->data[q] = xwork[j - 1];
            }

            exitg1 = 1;
          }
        }
      } while (exitg1 == 0);
    }
  }
}

/*
 * Arguments    : emxArray_int32_T *x
 *                emxArray_int32_T *idx
 * Return Type  : void
 */
void b_sortIdx(emxArray_int32_T *x, emxArray_int32_T *idx)
{
  int i3;
  int idx_tmp;
  emxArray_int32_T *iwork;
  int n;
  int b_n;
  int x4[4];
  int idx4[4];
  emxArray_int32_T *xwork;
  int nQuartets;
  int j;
  int nDone;
  int i;
  int i4;
  int i1;
  signed char perm[4];
  int i2;
  i3 = x->size[0];
  idx_tmp = idx->size[0];
  idx->size[0] = i3;
  emxEnsureCapacity_int32_T(idx, idx_tmp);
  for (idx_tmp = 0; idx_tmp < i3; idx_tmp++) {
    idx->data[idx_tmp] = 0;
  }

  if (x->size[0] != 0) {
    emxInit_int32_T(&iwork, 1);
    n = x->size[0];
    b_n = x->size[0] - 1;
    x4[0] = 0;
    idx4[0] = 0;
    x4[1] = 0;
    idx4[1] = 0;
    x4[2] = 0;
    idx4[2] = 0;
    x4[3] = 0;
    idx4[3] = 0;
    idx_tmp = iwork->size[0];
    iwork->size[0] = i3;
    emxEnsureCapacity_int32_T(iwork, idx_tmp);
    for (idx_tmp = 0; idx_tmp < i3; idx_tmp++) {
      iwork->data[idx_tmp] = 0;
    }

    emxInit_int32_T(&xwork, 1);
    i3 = x->size[0];
    idx_tmp = xwork->size[0];
    xwork->size[0] = i3;
    emxEnsureCapacity_int32_T(xwork, idx_tmp);
    for (idx_tmp = 0; idx_tmp < i3; idx_tmp++) {
      xwork->data[idx_tmp] = 0;
    }

    nQuartets = x->size[0] >> 2;
    for (j = 0; j < nQuartets; j++) {
      i = j << 2;
      idx4[0] = i + 1;
      idx4[1] = i + 2;
      idx4[2] = i + 3;
      idx4[3] = i + 4;
      x4[0] = x->data[i];
      i3 = x->data[i + 1];
      x4[1] = i3;
      i4 = x->data[i + 2];
      x4[2] = i4;
      nDone = x->data[i + 3];
      x4[3] = nDone;
      if (x->data[i] <= i3) {
        i1 = 1;
        i2 = 2;
      } else {
        i1 = 2;
        i2 = 1;
      }

      if (i4 <= nDone) {
        i3 = 3;
        i4 = 4;
      } else {
        i3 = 4;
        i4 = 3;
      }

      idx_tmp = x4[i1 - 1];
      nDone = x4[i3 - 1];
      if (idx_tmp <= nDone) {
        idx_tmp = x4[i2 - 1];
        if (idx_tmp <= nDone) {
          perm[0] = (signed char)i1;
          perm[1] = (signed char)i2;
          perm[2] = (signed char)i3;
          perm[3] = (signed char)i4;
        } else if (idx_tmp <= x4[i4 - 1]) {
          perm[0] = (signed char)i1;
          perm[1] = (signed char)i3;
          perm[2] = (signed char)i2;
          perm[3] = (signed char)i4;
        } else {
          perm[0] = (signed char)i1;
          perm[1] = (signed char)i3;
          perm[2] = (signed char)i4;
          perm[3] = (signed char)i2;
        }
      } else {
        nDone = x4[i4 - 1];
        if (idx_tmp <= nDone) {
          if (x4[i2 - 1] <= nDone) {
            perm[0] = (signed char)i3;
            perm[1] = (signed char)i1;
            perm[2] = (signed char)i2;
            perm[3] = (signed char)i4;
          } else {
            perm[0] = (signed char)i3;
            perm[1] = (signed char)i1;
            perm[2] = (signed char)i4;
            perm[3] = (signed char)i2;
          }
        } else {
          perm[0] = (signed char)i3;
          perm[1] = (signed char)i4;
          perm[2] = (signed char)i1;
          perm[3] = (signed char)i2;
        }
      }

      i2 = perm[0] - 1;
      idx->data[i] = idx4[i2];
      idx_tmp = perm[1] - 1;
      idx->data[i + 1] = idx4[idx_tmp];
      i3 = perm[2] - 1;
      idx->data[i + 2] = idx4[i3];
      i4 = perm[3] - 1;
      idx->data[i + 3] = idx4[i4];
      x->data[i] = x4[i2];
      x->data[i + 1] = x4[idx_tmp];
      x->data[i + 2] = x4[i3];
      x->data[i + 3] = x4[i4];
    }

    nDone = nQuartets << 2;
    i4 = b_n - nDone;
    if (i4 + 1 > 0) {
      for (i1 = 0; i1 <= i4; i1++) {
        i3 = nDone + i1;
        idx4[i1] = i3 + 1;
        x4[i1] = x->data[i3];
      }

      perm[1] = 0;
      perm[2] = 0;
      perm[3] = 0;
      if (i4 + 1 == 1) {
        perm[0] = 1;
      } else if (i4 + 1 == 2) {
        if (x4[0] <= x4[1]) {
          perm[0] = 1;
          perm[1] = 2;
        } else {
          perm[0] = 2;
          perm[1] = 1;
        }
      } else if (x4[0] <= x4[1]) {
        if (x4[1] <= x4[2]) {
          perm[0] = 1;
          perm[1] = 2;
          perm[2] = 3;
        } else if (x4[0] <= x4[2]) {
          perm[0] = 1;
          perm[1] = 3;
          perm[2] = 2;
        } else {
          perm[0] = 3;
          perm[1] = 1;
          perm[2] = 2;
        }
      } else if (x4[0] <= x4[2]) {
        perm[0] = 2;
        perm[1] = 1;
        perm[2] = 3;
      } else if (x4[1] <= x4[2]) {
        perm[0] = 2;
        perm[1] = 3;
        perm[2] = 1;
      } else {
        perm[0] = 3;
        perm[1] = 2;
        perm[2] = 1;
      }

      for (i1 = 0; i1 <= i4; i1++) {
        i2 = perm[i1] - 1;
        idx_tmp = nDone + i1;
        idx->data[idx_tmp] = idx4[i2];
        x->data[idx_tmp] = x4[i2];
      }
    }

    i3 = 2;
    if (n > 1) {
      if (n >= 256) {
        i3 = n >> 8;
        for (i4 = 0; i4 < i3; i4++) {
          merge_pow2_block(idx, x, i4 << 8);
        }

        i3 <<= 8;
        i4 = n - i3;
        if (i4 > 0) {
          merge_block(idx, x, i3, i4, 2, iwork, xwork);
        }

        i3 = 8;
      }

      merge_block(idx, x, 0, n, i3, iwork, xwork);
    }

    emxFree_int32_T(&xwork);
    emxFree_int32_T(&iwork);
  }
}

/*
 * Arguments    : const emxArray_real_T *x
 *                emxArray_int32_T *idx
 * Return Type  : void
 */
void sortIdx(const emxArray_real_T *x, emxArray_int32_T *idx)
{
  int i;
  int loop_ub;
  emxArray_int32_T *iwork;
  int k;
  double d;
  int b_i;
  int i2;
  int j;
  int pEnd;
  int p;
  int q;
  int qEnd;
  int kEnd;
  i = idx->size[0];
  idx->size[0] = x->size[0];
  emxEnsureCapacity_int32_T(idx, i);
  loop_ub = x->size[0];
  for (i = 0; i < loop_ub; i++) {
    idx->data[i] = 0;
  }

  emxInit_int32_T(&iwork, 1);
  loop_ub = x->size[0] + 1;
  i = iwork->size[0];
  iwork->size[0] = x->size[0];
  emxEnsureCapacity_int32_T(iwork, i);
  i = x->size[0] - 1;
  for (k = 1; k <= i; k += 2) {
    d = x->data[k - 1];
    if ((d >= x->data[k]) || rtIsNaN(d)) {
      idx->data[k - 1] = k;
      idx->data[k] = k + 1;
    } else {
      idx->data[k - 1] = k + 1;
      idx->data[k] = k;
    }
  }

  if ((x->size[0] & 1) != 0) {
    idx->data[x->size[0] - 1] = x->size[0];
  }

  b_i = 2;
  while (b_i < loop_ub - 1) {
    i2 = b_i << 1;
    j = 1;
    for (pEnd = b_i + 1; pEnd < loop_ub; pEnd = qEnd + b_i) {
      p = j - 1;
      q = pEnd;
      qEnd = j + i2;
      if (qEnd > loop_ub) {
        qEnd = loop_ub;
      }

      k = 0;
      kEnd = qEnd - j;
      while (k + 1 <= kEnd) {
        d = x->data[idx->data[p] - 1];
        i = idx->data[q - 1];
        if ((d >= x->data[i - 1]) || rtIsNaN(d)) {
          iwork->data[k] = idx->data[p];
          p++;
          if (p + 1 == pEnd) {
            while (q < qEnd) {
              k++;
              iwork->data[k] = idx->data[q - 1];
              q++;
            }
          }
        } else {
          iwork->data[k] = i;
          q++;
          if (q == qEnd) {
            while (p + 1 < pEnd) {
              k++;
              iwork->data[k] = idx->data[p];
              p++;
            }
          }
        }

        k++;
      }

      for (k = 0; k < kEnd; k++) {
        idx->data[(j + k) - 1] = iwork->data[k];
      }

      j = qEnd;
    }

    b_i = i2;
  }

  emxFree_int32_T(&iwork);
}

/*
 * File trailer for sortIdx.c
 *
 * [EOF]
 */
