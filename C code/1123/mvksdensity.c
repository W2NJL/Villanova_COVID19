/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: mvksdensity.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "mvksdensity.h"
#include "ac_feat.h"
#include "ac_feat_data.h"
#include "ac_feat_emxutil.h"
#include "all.h"
#include "any.h"
#include "bsxfun.h"
#include "diff.h"
#include "ecdf.h"
#include "exp.h"
#include "isequal.h"
#include "linspace.h"
#include "log.h"
#include "minOrMax.h"
#include "mtimes.h"
#include "power.h"
#include "rt_nonfinite.h"
#include "featsort.h"
#include "sum.h"
#include <math.h>
#include <string.h>

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *yData
 *                double varargin_6
 *                double fout[100]
 *                double xout[100]
 *                double *u
 * Return Type  : void
 */
void mvksdensity(const emxArray_real_T *yData, double varargin_6, double fout
                 [100], double xout[100], double *u)
{
  emxArray_boolean_T *cens;
  double ximin;
  int i;
  int loop_ub;
  emxArray_boolean_T *r;
  int end;
  int trueCount;
  int jend;
  emxArray_boolean_T *b_cens;
  int jstart;
  emxArray_int32_T *r1;
  emxArray_real_T *weight;
  emxArray_int32_T *r2;
  emxArray_real_T *b_yData;
  double L;
  emxArray_real_T *F;
  emxArray_real_T *ty;
  emxArray_real_T *c_yData;
  double ximax;
  int k;
  double x_data[1];
  double b_x_data[1];
  bool c[100];
  bool bv[100];
  bool inbounds_data[101];
  bool b_c[100];
  double xi_data[101];
  double y_data[100];
  double unnamed_idx_0;
  double txi_data[100];
  bool b;
  signed char tmp_data[100];
  int xi_size[1];
  double b_xi_data[100];
  double b_tmp_data[100];
  int tmp_size[1];
  int b_tmp_size[1];
  emxArray_real_T *b_weight;
  emxArray_real_T *z;
  emxArray_int32_T *iidx;
  double f_data[101];
  int f_size[2];
  emxArray_real_T *f;
  int c_tmp_size[2];
  emxArray_real_T *nearby;
  emxArray_real_T *r3;
  emxArray_real_T *b_F;
  int b_f_size[2];
  acemxInit_boolean_T(&cens, 1);
  ximin = 1.0 / (double)yData->size[0];
  i = cens->size[0];
  cens->size[0] = yData->size[0];
  acemxEnsureCapacity_boolean_T(cens, i);
  loop_ub = yData->size[0];
  for (i = 0; i < loop_ub; i++) {
    cens->data[i] = rtIsNaN(yData->data[i]);
  }

  acemxInit_boolean_T(&r, 1);
  any(cens, r);
  loop_ub = r->size[0];
  acemxFree_boolean_T(&cens);
  for (i = 0; i < loop_ub; i++) {
    r->data[i] = !r->data[i];
  }

  end = r->size[0] - 1;
  trueCount = 0;
  for (jend = 0; jend <= end; jend++) {
    if (r->data[jend]) {
      trueCount++;
    }
  }

  acemxInit_boolean_T(&b_cens, 1);
  i = b_cens->size[0];
  b_cens->size[0] = trueCount;
  acemxEnsureCapacity_boolean_T(b_cens, i);
  jstart = 0;
  for (jend = 0; jend <= end; jend++) {
    if (r->data[jend]) {
      b_cens->data[jstart] = false;
      jstart++;
    }
  }

  end = r->size[0] - 1;
  trueCount = 0;
  for (jend = 0; jend <= end; jend++) {
    if (r->data[jend]) {
      trueCount++;
    }
  }

  acemxInit_int32_T(&r1, 1);
  i = r1->size[0];
  r1->size[0] = trueCount;
  acemxEnsureCapacity_int32_T(r1, i);
  jstart = 0;
  for (jend = 0; jend <= end; jend++) {
    if (r->data[jend]) {
      r1->data[jstart] = jend + 1;
      jstart++;
    }
  }

  acemxInit_real_T(&weight, 2);
  i = weight->size[0] * weight->size[1];
  weight->size[0] = 1;
  weight->size[1] = r1->size[0];
  acemxEnsureCapacity_real_T(weight, i);
  loop_ub = r1->size[0];
  for (i = 0; i < loop_ub; i++) {
    weight->data[i] = ximin;
  }

  end = r->size[0] - 1;
  trueCount = 0;
  for (jend = 0; jend <= end; jend++) {
    if (r->data[jend]) {
      trueCount++;
    }
  }

  acemxInit_int32_T(&r2, 1);
  i = r2->size[0];
  r2->size[0] = trueCount;
  acemxEnsureCapacity_int32_T(r2, i);
  jstart = 0;
  for (jend = 0; jend <= end; jend++) {
    if (r->data[jend]) {
      r2->data[jstart] = jend + 1;
      jstart++;
    }
  }

  acemxFree_boolean_T(&r);
  acemxInit_real_T(&b_yData, 1);
  i = b_yData->size[0];
  b_yData->size[0] = r2->size[0];
  acemxEnsureCapacity_real_T(b_yData, i);
  loop_ub = r2->size[0];
  for (i = 0; i < loop_ub; i++) {
    b_yData->data[i] = yData->data[r2->data[i] - 1];
  }

  ximin = sum(weight);
  loop_ub = weight->size[0] * weight->size[1] - 1;
  i = weight->size[0] * weight->size[1];
  weight->size[0] = 1;
  acemxEnsureCapacity_real_T(weight, i);
  for (i = 0; i <= loop_ub; i++) {
    weight->data[i] /= ximin;
  }

  if (isequal(cv)) {
    L = rtMinusInf;
  } else {
    L = 0.0;
  }

  acemxInit_real_T(&F, 1);
  acemxInit_real_T(&ty, 1);
  if (b_any(b_cens)) {
    acemxInit_real_T(&c_yData, 1);
    loop_ub = r2->size[0];
    i = c_yData->size[0];
    c_yData->size[0] = r2->size[0];
    acemxEnsureCapacity_real_T(c_yData, i);
    for (i = 0; i < loop_ub; i++) {
      c_yData->data[i] = b_yData->data[i];
    }

    i = b_yData->size[0];
    b_yData->size[0] = c_yData->size[0];
    acemxEnsureCapacity_real_T(b_yData, i);
    loop_ub = c_yData->size[0];
    for (i = 0; i < loop_ub; i++) {
      b_yData->data[i] = c_yData->data[i];
    }

    acemxFree_real_T(&c_yData);
    ecdf(b_yData, b_cens, weight, F, ty);
    diff(F, b_yData);
    i = weight->size[0] * weight->size[1];
    weight->size[0] = 1;
    weight->size[1] = b_yData->size[0];
    acemxEnsureCapacity_real_T(weight, i);
    loop_ub = b_yData->size[0];
    for (i = 0; i < loop_ub; i++) {
      weight->data[i] = b_yData->data[i];
    }

    if (2 > ty->size[0]) {
      i = 0;
      end = 0;
    } else {
      i = 1;
      end = ty->size[0];
    }

    loop_ub = end - i;
    end = F->size[0];
    F->size[0] = loop_ub;
    acemxEnsureCapacity_real_T(F, end);
    for (end = 0; end < loop_ub; end++) {
      F->data[end] = ty->data[i + end];
    }

    i = ty->size[0];
    ty->size[0] = F->size[0];
    acemxEnsureCapacity_real_T(ty, i);
    loop_ub = F->size[0];
    for (i = 0; i < loop_ub; i++) {
      ty->data[i] = F->data[i];
    }
  } else {
    i = ty->size[0];
    ty->size[0] = b_yData->size[0];
    acemxEnsureCapacity_real_T(ty, i);
    loop_ub = b_yData->size[0];
    for (i = 0; i < loop_ub; i++) {
      ty->data[i] = b_yData->data[i];
    }
  }

  acemxFree_int32_T(&r2);
  acemxFree_boolean_T(&b_cens);
  ximin = minimum(ty) - 3.0 * varargin_6;
  ximax = b_maximum(ty) + 3.0 * varargin_6;
  if (!(L == rtMinusInf)) {
    if (L == 0.0) {
      ximin = exp(ximin);
      ximax = exp(ximax);
    } else {
      trueCount = 0;
      if (ximin < 0.0) {
        trueCount = 1;
      }

      if (0 <= trueCount - 1) {
        x_data[0] = exp(ximin);
        b_x_data[0] = exp(ximin);
      }

      loop_ub = trueCount - 1;
      for (i = 0; i <= loop_ub; i++) {
        x_data[0] = (rtInf * x_data[0] + L) / (b_x_data[0] + 1.0);
      }

      unnamed_idx_0 = ximin;
      trueCount = 0;
      if (ximin < 0.0) {
        unnamed_idx_0 = x_data[0];
      } else {
        trueCount = 1;
      }

      if (0 <= trueCount - 1) {
        x_data[0] = -ximin;
      }

      for (k = 0; k < trueCount; k++) {
        x_data[0] = exp(x_data[0]);
      }

      if (0 <= trueCount - 1) {
        b_x_data[0] = -ximin;
      }

      for (k = 0; k < trueCount; k++) {
        b_x_data[0] = exp(b_x_data[0]);
      }

      loop_ub = trueCount - 1;
      for (i = 0; i <= loop_ub; i++) {
        x_data[0] = (L * x_data[0] + rtInf) / (b_x_data[0] + 1.0);
      }

      if (!(ximin < 0.0)) {
        unnamed_idx_0 = x_data[0];
      }

      ximin = unnamed_idx_0;
      trueCount = 0;
      if (ximax < 0.0) {
        trueCount = 1;
      }

      if (0 <= trueCount - 1) {
        x_data[0] = exp(ximax);
        b_x_data[0] = exp(ximax);
      }

      loop_ub = trueCount - 1;
      for (i = 0; i <= loop_ub; i++) {
        x_data[0] = (rtInf * x_data[0] + L) / (b_x_data[0] + 1.0);
      }

      unnamed_idx_0 = ximax;
      trueCount = 0;
      if (ximax < 0.0) {
        unnamed_idx_0 = x_data[0];
      } else {
        trueCount = 1;
      }

      if (0 <= trueCount - 1) {
        x_data[0] = -ximax;
      }

      for (k = 0; k < trueCount; k++) {
        x_data[0] = exp(x_data[0]);
      }

      if (0 <= trueCount - 1) {
        b_x_data[0] = -ximax;
      }

      for (k = 0; k < trueCount; k++) {
        b_x_data[0] = exp(b_x_data[0]);
      }

      loop_ub = trueCount - 1;
      for (i = 0; i <= loop_ub; i++) {
        x_data[0] = (L * x_data[0] + rtInf) / (b_x_data[0] + 1.0);
      }

      if (!(ximax < 0.0)) {
        unnamed_idx_0 = x_data[0];
      }

      ximax = unnamed_idx_0;
    }
  }

  linspace(ximin, ximax, xout);
  memset(&fout[0], 0, 100U * sizeof(double));
  if (L == rtMinusInf) {
    trueCount = 100;
    for (i = 0; i < 100; i++) {
      inbounds_data[i] = true;
      xi_data[i] = xout[i];
    }
  } else if (L == 0.0) {
    for (i = 0; i < 100; i++) {
      c[i] = (xout[i] > 0.0);
    }

    all(c, bv);
    trueCount = 0;
    jstart = 0;
    for (jend = 0; jend < 100; jend++) {
      inbounds_data[jend] = bv[jend];
      if (bv[jend]) {
        trueCount++;
        tmp_data[jstart] = (signed char)(jend + 1);
        jstart++;
      }
    }

    for (i = 0; i < trueCount; i++) {
      xi_data[i] = xout[tmp_data[i] - 1];
    }
  } else {
    for (k = 0; k < 100; k++) {
      c[k] = (xout[k] > L);
      b_c[k] = (xout[k] < rtInf);
    }

    all(c, bv);
    all(b_c, c);
    trueCount = 0;
    jstart = 0;
    for (jend = 0; jend < 100; jend++) {
      b = (bv[jend] && c[jend]);
      inbounds_data[jend] = b;
      if (b) {
        trueCount++;
        tmp_data[jstart] = (signed char)(jend + 1);
        jstart++;
      }
    }

    for (i = 0; i < trueCount; i++) {
      xi_data[i] = xout[tmp_data[i] - 1];
    }
  }

  if (0 <= trueCount - 1) {
    memcpy(&y_data[0], &xi_data[0], trueCount * sizeof(double));
  }

  if (0 <= trueCount - 1) {
    memcpy(&txi_data[0], &xi_data[0], trueCount * sizeof(double));
  }

  if (!(L == rtMinusInf)) {
    if (L == 0.0) {
      xi_size[0] = trueCount;
      if (0 <= trueCount - 1) {
        memcpy(&b_xi_data[0], &xi_data[0], trueCount * sizeof(double));
      }

      maximum2(b_xi_data, xi_size, b_tmp_data, b_tmp_size);
      b_log(b_tmp_data, b_tmp_size);
      loop_ub = b_tmp_size[0];
      if (0 <= loop_ub - 1) {
        memcpy(&txi_data[0], &b_tmp_data[0], loop_ub * sizeof(double));
      }
    } else {
      xi_size[0] = trueCount;
      if (0 <= trueCount - 1) {
        memcpy(&b_xi_data[0], &xi_data[0], trueCount * sizeof(double));
      }

      minimum2(b_xi_data, xi_size, b_tmp_data, tmp_size);
      b_maximum2(L, b_tmp_data, tmp_size, b_xi_data, xi_size);
      loop_ub = xi_size[0];
      if (0 <= loop_ub - 1) {
        memcpy(&y_data[0], &b_xi_data[0], loop_ub * sizeof(double));
      }

      b_tmp_size[0] = trueCount;
      for (i = 0; i < trueCount; i++) {
        b_tmp_data[i] = y_data[i] - L;
      }

      b_log(b_tmp_data, b_tmp_size);
      xi_size[0] = trueCount;
      for (i = 0; i < trueCount; i++) {
        b_xi_data[i] = rtInf - y_data[i];
      }

      b_log(b_xi_data, xi_size);
      loop_ub = b_tmp_size[0];
      for (i = 0; i < loop_ub; i++) {
        txi_data[i] = b_tmp_data[i] - b_xi_data[i];
      }

      for (end = 0; end < trueCount; end++) {
        if (y_data[end] == rtInf) {
          txi_data[end] = rtInf;
        }
      }
    }
  }

  if (trueCount == 0) {
    end = 1;
  } else {
    end = ty->size[0];
    b = ((!rtIsInf(L)) || (!(L < 0.0)));
    if (ty->size[0] * trueCount <= 30000) {
      acemxInit_real_T(&z, 2);
      i = z->size[0] * z->size[1];
      z->size[0] = ty->size[0];
      z->size[1] = trueCount;
      acemxEnsureCapacity_real_T(z, i);
      loop_ub = ty->size[0] * trueCount;
      for (i = 0; i < loop_ub; i++) {
        z->data[i] = 0.0;
      }

      i = ty->size[0];
      for (end = 0; end < i; end++) {
        for (jstart = 0; jstart < trueCount; jstart++) {
          z->data[end + z->size[0] * jstart] = (txi_data[jstart] - ty->data[end])
            / varargin_6;
        }
      }

      acemxInit_real_T(&f, 2);
      b_power(z, f);
      loop_ub = f->size[0] * f->size[1];
      for (i = 0; i < loop_ub; i++) {
        f->data[i] *= -0.5;
      }

      b_exp(f);
      loop_ub = f->size[0] * f->size[1];
      for (i = 0; i < loop_ub; i++) {
        f->data[i] /= 2.5066282746310002;
      }

      if (b && (L == 0.0)) {
        c_tmp_size[0] = 1;
        c_tmp_size[1] = trueCount;
        for (i = 0; i < trueCount; i++) {
          b_tmp_data[i] = 1.0 / xi_data[i];
        }

        i = z->size[0] * z->size[1];
        z->size[0] = f->size[0];
        z->size[1] = f->size[1];
        acemxEnsureCapacity_real_T(z, i);
        loop_ub = f->size[0] * f->size[1] - 1;
        for (i = 0; i <= loop_ub; i++) {
          z->data[i] = f->data[i];
        }

        b_bsxfun(z, b_tmp_data, c_tmp_size, f);
      }

      i = z->size[0] * z->size[1];
      z->size[0] = ty->size[0];
      z->size[1] = trueCount;
      acemxEnsureCapacity_real_T(z, i);
      loop_ub = f->size[0] * f->size[1];
      for (i = 0; i < loop_ub; i++) {
        z->data[i] = f->data[i];
      }

      acemxFree_real_T(&f);
      mtimes(weight, z, b_xi_data, f_size);
      acemxFree_real_T(&z);
    } else {
      acemxInit_real_T(&b_weight, 2);
      acemxInit_int32_T(&iidx, 1);
      acb_sort(ty, iidx);
      i = b_weight->size[0] * b_weight->size[1];
      b_weight->size[0] = 1;
      b_weight->size[1] = iidx->size[0];
      acemxEnsureCapacity_real_T(b_weight, i);
      loop_ub = iidx->size[0];
      for (i = 0; i < loop_ub; i++) {
        b_weight->data[i] = weight->data[iidx->data[i] - 1];
      }

      f_size[0] = 1;
      f_size[1] = trueCount;
      if (0 <= trueCount - 1) {
        memset(&b_xi_data[0], 0, trueCount * sizeof(double));
      }

      i = F->size[0];
      F->size[0] = trueCount;
      acemxEnsureCapacity_real_T(F, i);
      for (i = 0; i < trueCount; i++) {
        F->data[i] = txi_data[i];
      }

      acb_sort(F, iidx);
      jstart = 1;
      jend = 1;
      ximax = 4.0 * varargin_6;
      acemxInit_real_T(&nearby, 2);
      acemxInit_real_T(&r3, 1);
      acemxInit_real_T(&b_F, 1);
      for (k = 0; k < trueCount; k++) {
        ximin = F->data[k] - ximax;
        while ((ty->data[jstart - 1] < ximin) && (jstart < end)) {
          jstart++;
        }

        ximin = F->data[k] + ximax;
        jend = (int)fmax(jend, jstart);
        while ((ty->data[jend - 1] <= ximin) && (jend < end)) {
          jend++;
        }

        if (jend < jstart) {
          nearby->size[0] = 1;
          nearby->size[1] = 0;
        } else {
          i = nearby->size[0] * nearby->size[1];
          nearby->size[0] = 1;
          loop_ub = jend - jstart;
          nearby->size[1] = loop_ub + 1;
          acemxEnsureCapacity_real_T(nearby, i);
          for (i = 0; i <= loop_ub; i++) {
            nearby->data[i] = jstart + i;
          }
        }

        i = b_F->size[0];
        b_F->size[0] = nearby->size[1];
        acemxEnsureCapacity_real_T(b_F, i);
        loop_ub = nearby->size[1];
        for (i = 0; i < loop_ub; i++) {
          b_F->data[i] = (F->data[k] - ty->data[(int)nearby->data[i] - 1]) /
            varargin_6;
        }

        power(b_F, r3);
        i = b_F->size[0];
        b_F->size[0] = r3->size[0];
        acemxEnsureCapacity_real_T(b_F, i);
        loop_ub = r3->size[0];
        for (i = 0; i < loop_ub; i++) {
          b_F->data[i] = -0.5 * r3->data[i];
        }

        c_exp(b_F);
        ximin = 0.0;
        loop_ub = nearby->size[1];
        for (i = 0; i < loop_ub; i++) {
          ximin += b_weight->data[(int)nearby->data[i] - 1] * (b_F->data[i] /
            2.5066282746310002);
        }

        b_xi_data[k] = ximin;
      }

      acemxFree_real_T(&b_F);
      acemxFree_real_T(&r3);
      acemxFree_real_T(&nearby);
      acemxFree_real_T(&b_weight);
      i = r1->size[0];
      r1->size[0] = iidx->size[0];
      acemxEnsureCapacity_int32_T(r1, i);
      loop_ub = iidx->size[0];
      for (i = 0; i < loop_ub; i++) {
        r1->data[i] = iidx->data[i];
      }

      acemxFree_int32_T(&iidx);
      loop_ub = r1->size[0];
      i = b_yData->size[0];
      b_yData->size[0] = r1->size[0];
      acemxEnsureCapacity_real_T(b_yData, i);
      for (i = 0; i < loop_ub; i++) {
        b_yData->data[i] = b_xi_data[i];
      }

      loop_ub = b_yData->size[0];
      for (i = 0; i < loop_ub; i++) {
        b_xi_data[r1->data[i] - 1] = b_yData->data[i];
      }

      if (b && (L == 0.0)) {
        c_tmp_size[0] = 1;
        c_tmp_size[1] = trueCount;
        for (i = 0; i < trueCount; i++) {
          b_tmp_data[i] = 1.0 / xi_data[i];
        }

        b_f_size[0] = 1;
        b_f_size[1] = trueCount;
        loop_ub = trueCount - 1;
        if (0 <= loop_ub) {
          memcpy(&y_data[0], &b_xi_data[0], (loop_ub + 1) * sizeof(double));
        }

        c_bsxfun(y_data, b_f_size, b_tmp_data, c_tmp_size, b_xi_data, f_size);
      }
    }

    end = f_size[1];
    loop_ub = f_size[0] * f_size[1];
    if (0 <= loop_ub - 1) {
      memcpy(&f_data[0], &b_xi_data[0], loop_ub * sizeof(double));
    }
  }

  acemxFree_real_T(&ty);
  acemxFree_real_T(&F);
  acemxFree_int32_T(&r1);
  acemxFree_real_T(&weight);
  acemxFree_real_T(&b_yData);
  loop_ub = end - 1;
  for (i = 0; i <= loop_ub; i++) {
    f_data[i] /= varargin_6;
  }

  jstart = 0;
  end = 0;
  for (jend = 0; jend < 100; jend++) {
    if (inbounds_data[jend]) {
      fout[jend] = f_data[jstart];
      jstart++;
      xout[jend] = xi_data[end];
      end++;
    }
  }

  *u = varargin_6;
}

/*
 * File trailer for mvksdensity.c
 *
 * [EOF]
 */
