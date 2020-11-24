/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: phase_sep.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 16-Nov-2020 11:39:11
 */

/* Include Files */
#include "phase_sep.h"
#include "find.h"
#include "findpeaks.h"
#include "mycell.h"
#include "phase_sep_data.h"
#include "phase_sep_emxutil.h"
#include "phase_sep_initialize.h"
#include "rt_nonfinite.h"
#include "sort.h"
#include <math.h>
#include <string.h>

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *y_raw
 *                const emxArray_real_T *y_fil_hb
 *                cell_wrap_0 y_ph_in[100]
 *                cell_wrap_0 y_ph_fil_in[100]
 *                cell_wrap_0 y_ph_ex[100]
 *                cell_wrap_0 y_ph_fil_ex[100]
 *                double siz_in_data[]
 *                int siz_in_size[2]
 *                double siz_ex_data[]
 *                int siz_ex_size[2]
 * Return Type  : void
 */
void phase_sep(const emxArray_real_T *y_raw, const emxArray_real_T *y_fil_hb,
               cell_wrap_0 y_ph_in[100], cell_wrap_0 y_ph_fil_in[100],
               cell_wrap_0 y_ph_ex[100], cell_wrap_0 y_ph_fil_ex[100], double
               siz_in_data[], int siz_in_size[2], double siz_ex_data[], int
               siz_ex_size[2])
{
  int i;
  emxArray_real_T *y;
  int nx;
  int k;
  emxArray_real_T *b_y;
  int nx_m_nb;
  int j;
  int naxpy;
  int y_tmp;
  double en_y1;
  emxArray_real_T *c_y;
  double tu;
  emxArray_real_T *val_pkmx;
  emxArray_real_T *loc;
  emxArray_boolean_T *b_val_pkmx;
  emxArray_real_T *ind_n;
  emxArray_int32_T *r;
  int b_i;
  emxArray_real_T *ind_mx;
  emxArray_real_T *ind1;
  emxArray_real_T *ind_fin_up;
  emxArray_int32_T *r1;
  emxArray_real_T *vec;
  unsigned int siz_in_1[100];
  unsigned int siz_ex_1[100];
  emxArray_int32_T *r2;
  int i1;
  if (!isInitialized_phase_sep) {
    phase_sep_initialize();
  }

  mycell(y_ph_in);
  for (i = 0; i < 100; i++) {
    emxCopyStruct_cell_wrap_0(&y_ph_fil_in[i], &y_ph_in[i]);
    emxCopyStruct_cell_wrap_0(&y_ph_ex[i], &y_ph_in[i]);
    emxCopyStruct_cell_wrap_0(&y_ph_fil_ex[i], &y_ph_in[i]);
  }

  phaseemxInit_real_T(&y, 1);

  /*  plot(y_win1) */
  nx = y_fil_hb->size[0];
  i = y->size[0];
  y->size[0] = y_fil_hb->size[0];
  phaseemxEnsureCapacity_real_T(y, i);
  for (k = 0; k < nx; k++) {
    y->data[k] = fabs(y_fil_hb->data[k]);
  }

  phaseemxInit_real_T(&b_y, 1);
  nx = y->size[0];
  nx_m_nb = y->size[0];
  i = b_y->size[0];
  b_y->size[0] = y->size[0];
  phaseemxEnsureCapacity_real_T(b_y, i);
  for (i = 0; i < nx_m_nb; i++) {
    b_y->data[i] = 0.0;
  }

  if (y->size[0] >= 30000) {
    for (k = 0; k < 15000; k++) {
      nx_m_nb = k + 1;
      for (j = nx_m_nb; j <= nx; j++) {
        b_y->data[j - 1] += 6.666666666666667E-5 * y->data[(j - k) - 1];
      }
    }
  } else {
    if (y->size[0] > 15000) {
      nx_m_nb = y->size[0] - 15001;
    } else {
      nx_m_nb = -1;
    }

    for (k = 0; k <= nx_m_nb; k++) {
      for (j = 0; j < 15000; j++) {
        y_tmp = k + j;
        b_y->data[y_tmp] += y->data[k] * 6.666666666666667E-5;
      }
    }

    naxpy = y->size[0] - nx_m_nb;
    i = nx_m_nb + 2;
    for (k = i; k <= nx; k++) {
      for (j = 0; j <= naxpy - 2; j++) {
        y_tmp = (k + j) - 1;
        b_y->data[y_tmp] += y->data[k - 1] * 6.666666666666667E-5;
      }

      naxpy--;
    }
  }

  i = y->size[0];
  y->size[0] = b_y->size[0];
  phaseemxEnsureCapacity_real_T(y, i);
  nx = b_y->size[0];
  for (k = 0; k < nx; k++) {
    y->data[k] = b_y->data[k] * b_y->data[k];
  }

  i = b_y->size[0];
  b_y->size[0] = y_fil_hb->size[0];
  phaseemxEnsureCapacity_real_T(b_y, i);
  nx = y_fil_hb->size[0];
  for (k = 0; k < nx; k++) {
    b_y->data[k] = y_fil_hb->data[k] * y_fil_hb->data[k];
  }

  nx_m_nb = b_y->size[0];
  if (b_y->size[0] == 0) {
    en_y1 = 0.0;
  } else {
    en_y1 = b_y->data[0];
    for (k = 2; k <= nx_m_nb; k++) {
      en_y1 += b_y->data[k - 1];
    }
  }

  phaseemxFree_real_T(&b_y);
  phaseemxInit_real_T(&c_y, 2);
  en_y1 /= (double)y_fil_hb->size[0];
  tu = en_y1 / 6.0;
  en_y1 *= 0.45;
  i = c_y->size[0] * c_y->size[1];
  c_y->size[0] = 1;
  c_y->size[1] = y->size[0];
  phaseemxEnsureCapacity_real_T(c_y, i);
  nx_m_nb = y->size[0];
  for (i = 0; i < nx_m_nb; i++) {
    c_y->data[i] = -y->data[i];
  }

  phaseemxInit_real_T(&val_pkmx, 2);
  phaseemxInit_real_T(&loc, 2);
  emxInit_boolean_T(&b_val_pkmx, 2);
  findpeaks(c_y, val_pkmx, loc);
  i = b_val_pkmx->size[0] * b_val_pkmx->size[1];
  b_val_pkmx->size[0] = 1;
  b_val_pkmx->size[1] = val_pkmx->size[1];
  emxEnsureCapacity_boolean_T(b_val_pkmx, i);
  nx_m_nb = val_pkmx->size[0] * val_pkmx->size[1];
  for (i = 0; i < nx_m_nb; i++) {
    b_val_pkmx->data[i] = (-val_pkmx->data[i] < en_y1);
  }

  phaseemxInit_real_T(&ind_n, 2);
  emxInit_int32_T(&r, 2);
  eml_find(b_val_pkmx, r);
  i = ind_n->size[0] * ind_n->size[1];
  ind_n->size[0] = 1;
  ind_n->size[1] = r->size[1] + 1;
  phaseemxEnsureCapacity_real_T(ind_n, i);
  ind_n->data[0] = 1.0;
  nx_m_nb = r->size[1];
  for (i = 0; i < nx_m_nb; i++) {
    ind_n->data[i + 1] = loc->data[r->data[i] - 1];
  }

  i = c_y->size[0] * c_y->size[1];
  c_y->size[0] = 1;
  c_y->size[1] = y->size[0];
  phaseemxEnsureCapacity_real_T(c_y, i);
  nx_m_nb = y->size[0];
  for (i = 0; i < nx_m_nb; i++) {
    c_y->data[i] = y->data[i];
  }

  findpeaks(c_y, val_pkmx, loc);
  i = b_val_pkmx->size[0] * b_val_pkmx->size[1];
  b_val_pkmx->size[0] = 1;
  b_val_pkmx->size[1] = val_pkmx->size[1];
  emxEnsureCapacity_boolean_T(b_val_pkmx, i);
  nx_m_nb = val_pkmx->size[0] * val_pkmx->size[1];
  phaseemxFree_real_T(&c_y);
  for (i = 0; i < nx_m_nb; i++) {
    b_val_pkmx->data[i] = (val_pkmx->data[i] > tu);
  }

  eml_find(b_val_pkmx, r);
  i = val_pkmx->size[0] * val_pkmx->size[1];
  val_pkmx->size[0] = 1;
  val_pkmx->size[1] = r->size[1];
  phaseemxEnsureCapacity_real_T(val_pkmx, i);
  nx_m_nb = r->size[0] * r->size[1];
  emxFree_boolean_T(&b_val_pkmx);
  for (i = 0; i < nx_m_nb; i++) {
    val_pkmx->data[i] = r->data[i];
  }

  emxFree_int32_T(&r);
  y_tmp = val_pkmx->size[1] - 1;
  nx = 0;
  for (b_i = 0; b_i <= y_tmp; b_i++) {
    if ((int)val_pkmx->data[b_i] != 0) {
      nx++;
    }
  }

  phaseemxInit_real_T(&ind_mx, 2);
  i = ind_mx->size[0] * ind_mx->size[1];
  ind_mx->size[0] = 1;
  ind_mx->size[1] = nx;
  phaseemxEnsureCapacity_real_T(ind_mx, i);
  naxpy = 0;
  for (b_i = 0; b_i <= y_tmp; b_i++) {
    if ((int)val_pkmx->data[b_i] != 0) {
      ind_mx->data[naxpy] = loc->data[b_i];
      naxpy++;
    }
  }

  phaseemxFree_real_T(&loc);
  i = val_pkmx->size[0] * val_pkmx->size[1];
  val_pkmx->size[0] = 1;
  val_pkmx->size[1] = ind_mx->size[1];
  phaseemxEnsureCapacity_real_T(val_pkmx, i);
  nx_m_nb = ind_mx->size[0] * ind_mx->size[1];
  for (i = 0; i < nx_m_nb; i++) {
    val_pkmx->data[i] = ind_mx->data[i];
  }

  i = ind_mx->size[1];
  for (b_i = 0; b_i <= i - 2; b_i++) {
    en_y1 = 0.0;
    y_tmp = ind_n->size[1];
    nx = -1;
    for (nx_m_nb = 0; nx_m_nb < y_tmp; nx_m_nb++) {
      if (ind_n->data[nx_m_nb] != 0.0) {
        nx++;
      }
    }

    for (j = 0; j <= nx; j++) {
      tu = ind_n->data[j];
      if ((tu <= ind_mx->data[b_i + 1]) && (tu >= ind_mx->data[b_i])) {
        en_y1++;
      }
    }

    if (en_y1 == 0.0) {
      y_tmp = (int)ind_mx->data[b_i] - 1;
      nx = (int)ind_mx->data[b_i + 1] - 1;
      if ((y->data[y_tmp] < y->data[nx]) || (rtIsNaN(y->data[y_tmp]) &&
           (!rtIsNaN(y->data[nx])))) {
        val_pkmx->data[b_i] = 0.0;
      } else {
        val_pkmx->data[b_i + 1] = 0.0;
      }
    }
  }

  phaseemxInit_real_T(&ind1, 2);
  i = ind1->size[0] * ind1->size[1];
  ind1->size[0] = 1;
  ind1->size[1] = ind_n->size[1];
  phaseemxEnsureCapacity_real_T(ind1, i);
  nx_m_nb = ind_n->size[0] * ind_n->size[1];
  for (i = 0; i < nx_m_nb; i++) {
    ind1->data[i] = ind_n->data[i];
  }

  y_tmp = ind_n->size[1];
  nx = -2;
  for (b_i = 0; b_i < y_tmp; b_i++) {
    if (ind_n->data[b_i] != 0.0) {
      nx++;
    }
  }

  for (b_i = 0; b_i <= nx; b_i++) {
    en_y1 = 0.0;
    i = ind_mx->size[1];
    for (j = 0; j < i; j++) {
      tu = ind_mx->data[j];
      if ((tu <= ind_n->data[b_i + 1]) && (tu >= ind_n->data[b_i])) {
        en_y1++;
      }
    }

    if (en_y1 == 0.0) {
      en_y1 = y->data[(int)ind_n->data[b_i] - 1];
      tu = y->data[(int)ind_n->data[b_i + 1] - 1];
      if ((en_y1 > tu) || (rtIsNaN(en_y1) && (!rtIsNaN(tu)))) {
        ind1->data[b_i] = 0.0;
      } else {
        ind1->data[b_i + 1] = 0.0;
      }
    }

    /*     if((ind(i)<=ind_mx(j)+32000) && (ind(i)>=ind_mx(j)-32000)) */
    /*         ind1(i)=0; */
  }

  phaseemxFree_real_T(&y);
  phaseemxFree_real_T(&ind_mx);
  phaseemxFree_real_T(&ind_n);
  y_tmp = val_pkmx->size[1] - 1;
  nx = 0;
  for (b_i = 0; b_i <= y_tmp; b_i++) {
    if (val_pkmx->data[b_i] > 0.0) {
      nx++;
    }
  }

  phaseemxInit_real_T(&ind_fin_up, 2);
  i = ind_fin_up->size[0] * ind_fin_up->size[1];
  ind_fin_up->size[0] = 1;
  ind_fin_up->size[1] = nx;
  phaseemxEnsureCapacity_real_T(ind_fin_up, i);
  naxpy = 0;
  for (b_i = 0; b_i <= y_tmp; b_i++) {
    tu = val_pkmx->data[b_i];
    if (tu > 0.0) {
      ind_fin_up->data[naxpy] = tu;
      naxpy++;
    }
  }

  phaseemxFree_real_T(&val_pkmx);
  y_tmp = ind1->size[1] - 1;
  nx = 0;
  for (b_i = 0; b_i <= y_tmp; b_i++) {
    if (ind1->data[b_i] > 0.0) {
      nx++;
    }
  }

  emxInit_int32_T(&r1, 2);
  i = r1->size[0] * r1->size[1];
  r1->size[0] = 1;
  r1->size[1] = nx;
  emxEnsureCapacity_int32_T(r1, i);
  naxpy = 0;
  for (b_i = 0; b_i <= y_tmp; b_i++) {
    if (ind1->data[b_i] > 0.0) {
      r1->data[naxpy] = b_i + 1;
      naxpy++;
    }
  }

  phaseemxInit_real_T(&vec, 2);
  i = vec->size[0] * vec->size[1];
  vec->size[0] = 1;
  vec->size[1] = r1->size[1] + ind_fin_up->size[1];
  phaseemxEnsureCapacity_real_T(vec, i);
  nx_m_nb = r1->size[1];
  for (i = 0; i < nx_m_nb; i++) {
    vec->data[i] = ind1->data[r1->data[i] - 1];
  }

  phaseemxFree_real_T(&ind1);
  nx_m_nb = ind_fin_up->size[1];
  for (i = 0; i < nx_m_nb; i++) {
    vec->data[i + r1->size[1]] = ind_fin_up->data[i];
  }

  emxFree_int32_T(&r1);
  b_sort(vec);
  memset(&siz_in_1[0], 0, 100U * sizeof(unsigned int));
  memset(&siz_ex_1[0], 0, 100U * sizeof(unsigned int));
  k = -1;
  naxpy = -1;
  i = vec->size[1];
  emxInit_int32_T(&r2, 1);
  for (b_i = 0; b_i <= i - 3; b_i++) {
    y_tmp = (int)(((double)ind_fin_up->size[1] + 1.0) / 2.0);
    for (j = 0; j < y_tmp; j++) {
      if (vec->data[b_i + 1] == ind_fin_up->data[j << 1]) {
        k++;
        tu = vec->data[b_i];
        en_y1 = vec->data[b_i + 2];
        if (tu > en_y1) {
          nx = -1;
          i1 = -1;
        } else {
          nx = (int)tu - 2;
          i1 = (int)en_y1 - 1;
        }

        nx_m_nb = i1 - nx;
        i1 = y_ph_in[k].f1->size[0] * y_ph_in[k].f1->size[1];
        y_ph_in[k].f1->size[0] = nx_m_nb;
        y_ph_in[k].f1->size[1] = 1;
        phaseemxEnsureCapacity_real_T(y_ph_in[k].f1, i1);
        i1 = r2->size[0];
        r2->size[0] = nx_m_nb;
        emxEnsureCapacity_int32_T(r2, i1);
        for (i1 = 0; i1 < nx_m_nb; i1++) {
          r2->data[i1] = (nx + i1) + 1;
        }

        for (nx = 0; nx < nx_m_nb; nx++) {
          y_ph_in[k].f1->data[nx] = y_raw->data[r2->data[nx]];
        }

        tu = vec->data[b_i];
        if (tu > en_y1) {
          nx = -1;
          i1 = -1;
        } else {
          nx = (int)tu - 2;
          i1 = (int)en_y1 - 1;
        }

        nx_m_nb = i1 - nx;
        i1 = y_ph_fil_in[k].f1->size[0] * y_ph_fil_in[k].f1->size[1];
        y_ph_fil_in[k].f1->size[0] = nx_m_nb;
        y_ph_fil_in[k].f1->size[1] = 1;
        phaseemxEnsureCapacity_real_T(y_ph_fil_in[k].f1, i1);
        i1 = r2->size[0];
        r2->size[0] = nx_m_nb;
        emxEnsureCapacity_int32_T(r2, i1);
        for (i1 = 0; i1 < nx_m_nb; i1++) {
          r2->data[i1] = (nx + i1) + 1;
        }

        for (nx = 0; nx < nx_m_nb; nx++) {
          y_ph_fil_in[k].f1->data[nx] = y_fil_hb->data[r2->data[nx]];
        }

        if (y_ph_in[k].f1->size[0] == 0) {
          nx_m_nb = 0;
        } else if (y_ph_in[k].f1->size[0] > y_ph_in[k].f1->size[1]) {
          nx_m_nb = y_ph_in[k].f1->size[0];
        } else {
          nx_m_nb = y_ph_in[k].f1->size[1];
        }

        siz_in_1[k] = (unsigned int)nx_m_nb;
      }
    }

    y_tmp = (int)((double)ind_fin_up->size[1] / 2.0);
    for (j = 0; j < y_tmp; j++) {
      if (vec->data[b_i + 1] == ind_fin_up->data[(j << 1) + 1]) {
        naxpy++;
        tu = vec->data[b_i];
        en_y1 = vec->data[b_i + 2];
        if (tu > en_y1) {
          nx = -1;
          i1 = -1;
        } else {
          nx = (int)tu - 2;
          i1 = (int)en_y1 - 1;
        }

        nx_m_nb = i1 - nx;
        i1 = y_ph_ex[naxpy].f1->size[0] * y_ph_ex[naxpy].f1->size[1];
        y_ph_ex[naxpy].f1->size[0] = nx_m_nb;
        y_ph_ex[naxpy].f1->size[1] = 1;
        phaseemxEnsureCapacity_real_T(y_ph_ex[naxpy].f1, i1);
        i1 = r2->size[0];
        r2->size[0] = nx_m_nb;
        emxEnsureCapacity_int32_T(r2, i1);
        for (i1 = 0; i1 < nx_m_nb; i1++) {
          r2->data[i1] = (nx + i1) + 1;
        }

        for (nx = 0; nx < nx_m_nb; nx++) {
          y_ph_ex[naxpy].f1->data[nx] = y_raw->data[r2->data[nx]];
        }

        tu = vec->data[b_i];
        en_y1 = vec->data[b_i + 2];
        if (tu > en_y1) {
          nx = -1;
          i1 = -1;
        } else {
          nx = (int)tu - 2;
          i1 = (int)en_y1 - 1;
        }

        nx_m_nb = i1 - nx;
        i1 = y_ph_fil_ex[naxpy].f1->size[0] * y_ph_fil_ex[naxpy].f1->size[1];
        y_ph_fil_ex[naxpy].f1->size[0] = nx_m_nb;
        y_ph_fil_ex[naxpy].f1->size[1] = 1;
        phaseemxEnsureCapacity_real_T(y_ph_fil_ex[naxpy].f1, i1);
        i1 = r2->size[0];
        r2->size[0] = nx_m_nb;
        emxEnsureCapacity_int32_T(r2, i1);
        for (i1 = 0; i1 < nx_m_nb; i1++) {
          r2->data[i1] = (nx + i1) + 1;
        }

        for (nx = 0; nx < nx_m_nb; nx++) {
          y_ph_fil_ex[naxpy].f1->data[nx] = y_fil_hb->data[r2->data[nx]];
        }

        if (y_ph_ex[naxpy].f1->size[0] == 0) {
          nx_m_nb = 0;
        } else if (y_ph_ex[naxpy].f1->size[0] > y_ph_ex[naxpy].f1->size[1]) {
          nx_m_nb = y_ph_ex[naxpy].f1->size[0];
        } else {
          nx_m_nb = y_ph_ex[naxpy].f1->size[1];
        }

        siz_ex_1[naxpy] = (unsigned int)nx_m_nb;
      }
    }
  }

  emxFree_int32_T(&r2);
  phaseemxFree_real_T(&vec);
  phaseemxFree_real_T(&ind_fin_up);
  nx = 0;
  siz_in_size[0] = 1;
  naxpy = 0;
  nx_m_nb = 0;
  siz_ex_size[0] = 1;
  y_tmp = 0;
  for (b_i = 0; b_i < 100; b_i++) {
    if ((int)siz_in_1[b_i] != 0) {
      nx++;
      siz_in_data[naxpy] = siz_in_1[b_i];
      naxpy++;
    }

    if ((int)siz_ex_1[b_i] != 0) {
      nx_m_nb++;
      siz_ex_data[y_tmp] = siz_ex_1[b_i];
      y_tmp++;
    }
  }

  siz_in_size[1] = nx;
  siz_ex_size[1] = nx_m_nb;
}

/*
 * File trailer for phase_sep.c
 *
 * [EOF]
 */
