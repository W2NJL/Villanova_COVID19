/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: ac_feat.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "ac_feat.h"
#include "ac_feat_data.h"
#include "ac_feat_emxutil.h"
#include "ac_feat_initialize.h"
#include "ac_feat_rtwutil.h"
#include "diff.h"
#include "features.h"
#include "acfindpeaks.h"
#include "minOrMax.h"
#include "rt_nonfinite.h"
#include "featsort.h"
#include "xcorr.h"
#include <math.h>
#include <string.h>

/* Function Definitions */

/*
 * Load the separated phases file
 * Arguments    : const emxArray_real_T *y_ph_re1
 *                const emxArray_real_T *y_ph_re2
 *                double feat_ac[9]
 * Return Type  : void
 */
void ac_feat(const emxArray_real_T *y_ph_re1, const emxArray_real_T *y_ph_re2,
             double feat_ac[9])
{
  int vlen;
  double xbar;
  int k;
  emxArray_real_T *b_y_ph_re1;
  double ac_tes[2001];
  double ac_i_data[2001];
  double maxval_tmp;
  static const int iv[1] = { 2001 };

  double b_ac_i_data[1002];
  double pu_data[2004];
  int pu_size[1];
  double iu_data[2004];
  int iu_size[1];
  double ac_d_data[1002];
  double pl_data[2004];
  int pl_size[1];
  double il_data[2004];
  int il_size[1];
  double p_val;
  double p_val2;
  int in_size[1];
  static double in_data[4008];
  double en;
  double sk;
  double ku;
  double var1;
  double t;
  int ac_d_size[1];
  emxArray_real_T *x;
  double b_ac_d_data[4008];
  emxArray_real_T c_ac_d_data;
  emxArray_real_T *y;
  emxArray_real_T *b_y;
  if (!isInitialized_ac_feat) {
    ac_feat_initialize();
  }

  /*  Initialize all the feature arrays for each iteration. */
  /*  ac ---- Auto-Correlation Function (ACF) */
  /*  me_ac --- Mean of the ACF curve */
  /*  var1 --- Variance of the ACF curve */
  /*  std_i --- Standard Deviation of the ACF curve */
  /*  en --- Entropy of the respiratory phase (unfiltered data) */
  /*  Ku --- Kurtosis of the respiratory phase (unfiltered data) */
  /*  sk --- Skewness of the respiratory phase (unfiltered data) */
  /*  Run the loop until empty cell of a cell array is encountered.  */
  /*  Clear all the intermediate variables. */
  /*  Load the inhale/exhale data into the ph_tes variable */
  /*  Subtract the mean component of the data */
  /*  Compute the ACF of the test phase     */
  vlen = y_ph_re1->size[0];
  if (y_ph_re1->size[0] == 0) {
    xbar = 0.0;
  } else {
    xbar = y_ph_re1->data[0];
    for (k = 2; k <= vlen; k++) {
      xbar += y_ph_re1->data[k - 1];
    }
  }

  acemxInit_real_T(&b_y_ph_re1, 1);
  xbar /= (double)y_ph_re1->size[0];
  k = b_y_ph_re1->size[0];
  b_y_ph_re1->size[0] = y_ph_re1->size[0];
  acemxEnsureCapacity_real_T(b_y_ph_re1, k);
  vlen = y_ph_re1->size[0];
  for (k = 0; k < vlen; k++) {
    b_y_ph_re1->data[k] = y_ph_re1->data[k] - xbar;
  }

  xcorr(b_y_ph_re1, ac_tes, ac_i_data);

  /*  Normalize the ACF */
  maxval_tmp = maximum(ac_tes, iv);
  for (k = 0; k < 2001; k++) {
    ac_i_data[k] = ac_tes[k] / maxval_tmp;
  }

  /*  ACF value at zero lag is the energy and this is one of the features */
  /*  given as input to the classifier */
  /*  Find the upper and lower peaks of the ACF curve    */
  for (k = 0; k < 1002; k++) {
    ac_d_data[k] = ac_tes[k + 999] / maxval_tmp;
    b_ac_i_data[k] = ac_i_data[k + 999];
  }

  acfindpeaks(b_ac_i_data, pu_data, pu_size, iu_data, iu_size);
  for (k = 0; k < 1002; k++) {
    b_ac_i_data[k] = -ac_i_data[k + 999];
  }

  acfindpeaks(b_ac_i_data, pl_data, pl_size, il_data, il_size);
  if (pl_size[0] == 0) {
    p_val = ac_i_data[1038];
  } else if (il_data[0] <= 48.0) {
    p_val = -pl_data[0];
  } else {
    p_val = 0.7;
  }

  if (pu_size[0] == 1) {
    p_val2 = 0.9;
  } else if ((pu_size[0] != 0) && (iu_data[1] <= 48.0)) {
    p_val2 = pu_data[1];
  } else {
    p_val2 = 0.9;
  }

  in_size[0] = iu_size[0] + il_size[0];
  vlen = iu_size[0];
  if (0 <= vlen - 1) {
    memcpy(&in_data[0], &iu_data[0], vlen * sizeof(double));
  }

  vlen = il_size[0];
  for (k = 0; k < vlen; k++) {
    in_data[k + iu_size[0]] = il_data[k];
  }

  acsort( in_data, in_size);

  /*  dx =((lags_tes(floor(length(ac_d1)/2)+70)):(lags_tes(floor(length(ac_d1)/2)+149)));                                 % Find Mean Differece In ‘x’ Values */
  /*  Compute the entropy, skewness and kurtosis by calling the features function  */
  vlen = y_ph_re2->size[0];
  if (y_ph_re2->size[0] == 0) {
    xbar = 0.0;
  } else {
    xbar = y_ph_re2->data[0];
    for (k = 2; k <= vlen; k++) {
      xbar += y_ph_re2->data[k - 1];
    }
  }

  xbar /= (double)y_ph_re2->size[0];
  k = b_y_ph_re1->size[0];
  b_y_ph_re1->size[0] = y_ph_re2->size[0];
  acemxEnsureCapacity_real_T(b_y_ph_re1, k);
  vlen = y_ph_re2->size[0];
  for (k = 0; k < vlen; k++) {
    b_y_ph_re1->data[k] = y_ph_re2->data[k] - xbar;
  }

  features(b_y_ph_re1, &en, &sk, &ku);

  /*  Polynomial fitting for the ACF curve after second maxima */
  /*  v_ac=polyfit(val_ac,dx,1); */
  /* pol_i=v_ac; */
  xbar = ac_i_data[1070];
  for (k = 0; k < 930; k++) {
    xbar += ac_i_data[k + 1071];
  }

  xbar /= 931.0;
  var1 = 0.0;
  for (k = 0; k < 931; k++) {
    t = ac_d_data[k + 71] - xbar;
    var1 += t * t;
  }

  var1 /= 930.0;
  ac_d_size[0] = in_size[0];
  vlen = in_size[0];
  for (k = 0; k < vlen; k++) {
    b_ac_d_data[k] = ac_d_data[(int)in_data[k] - 1];
  }

  acemxInit_real_T(&x, 1);
  c_ac_d_data.data = &b_ac_d_data[0];
  c_ac_d_data.size = &ac_d_size[0];
  c_ac_d_data.allocatedSize = 4008;
  c_ac_d_data.numDimensions = 1;
  c_ac_d_data.canFreeData = false;
  diff(&c_ac_d_data, b_y_ph_re1);
  k = x->size[0];
  x->size[0] = b_y_ph_re1->size[0];
  acemxEnsureCapacity_real_T(x, k);
  vlen = b_y_ph_re1->size[0];
  for (k = 0; k < vlen; k++) {
    x->data[k] = b_y_ph_re1->data[k];
  }

  acemxFree_real_T(&b_y_ph_re1);
  acemxInit_real_T(&y, 1);
  vlen = x->size[0];
  k = y->size[0];
  y->size[0] = x->size[0];
  acemxEnsureCapacity_real_T(y, k);
  for (k = 0; k < vlen; k++) {
    y->data[k] = fabs(x->data[k]);
  }

  acemxFree_real_T(&x);
  acemxInit_real_T(&b_y, 1);
  k = b_y->size[0];
  b_y->size[0] = y->size[0];
  acemxEnsureCapacity_real_T(b_y, k);
  vlen = y->size[0];
  for (k = 0; k < vlen; k++) {
    b_y->data[k] = acrt_powd_snf(y->data[k], 2.0);
  }

  acemxFree_real_T(&y);
  vlen = b_y->size[0];
  if (b_y->size[0] == 0) {
    t = 0.0;
  } else {
    t = b_y->data[0];
    for (k = 2; k <= vlen; k++) {
      t += b_y->data[k - 1];
    }
  }

  acemxFree_real_T(&b_y);

  /*  List of features that will be required to feed into the classifier. */
  /*  Difference between the second maxima and first minima of the normalized ACF curve */
  /*  Sum of the squares of the difference between the maximum and minimum values of the ACF curve */
  /*  Second maxima of the ACF curve */
  /*  R_x(0) or energy of the respiratory phase    */
  /*  Mean of the ACF curve after second maxima */
  /* pol_fin=pol_i;          % Polynomial fit of the ACF curve after second maxima */
  /*  sd_fil(j)={p_v}; */
  /*  Variance of the ACF curve after second maxima */
  /*  Standard deviation of the ACF curve after second maxima */
  /*  figure; */
  /*  for i=1:length(find(~cellfun(@isempty, y_ph_re1(5,:)))) */
  /*   acp=cell2mat(ac_i(5,i)); */
  /*   plot(lags_tes, acp); */
  /*   hold on; */
  /*  end */
  xbar = ac_d_data[71];
  for (k = 0; k < 930; k++) {
    xbar += ac_d_data[k + 72];
  }

  feat_ac[0] = ku;
  feat_ac[1] = sk;
  feat_ac[2] = en;
  feat_ac[3] = p_val2 - p_val;
  feat_ac[4] = p_val;
  feat_ac[5] = maxval_tmp;
  feat_ac[6] = xbar / 931.0;
  feat_ac[7] = var1;
  feat_ac[8] = t;
}

/*
 * File trailer for ac_feat.c
 *
 * [EOF]
 */
