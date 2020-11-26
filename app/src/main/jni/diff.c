/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: diff.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Nov-2020 00:25:45
 */

/* Include Files */
#include "diff.h"
#include "ac_feat.h"
#include "ac_feat_emxutil.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *x
 *                emxArray_real_T *y
 * Return Type  : void
 */
void diff(const emxArray_real_T *x, emxArray_real_T *y)
{
  int dimSize;
  int ixLead;
  int iyLead;
  double work_data_idx_0;
  int m;
  double tmp1;
  dimSize = x->size[0];
  if (x->size[0] == 0) {
    y->size[0] = 0;
  } else {
    ixLead = x->size[0] - 1;
    if (ixLead >= 1) {
      ixLead = 1;
    }

    if (ixLead < 1) {
      y->size[0] = 0;
    } else {
      ixLead = y->size[0];
      y->size[0] = x->size[0] - 1;
      acemxEnsureCapacity_real_T(y, ixLead);
      if (x->size[0] - 1 != 0) {
        ixLead = 1;
        iyLead = 0;
        work_data_idx_0 = x->data[0];
        for (m = 2; m <= dimSize; m++) {
          tmp1 = x->data[ixLead] - work_data_idx_0;
          work_data_idx_0 = x->data[ixLead];
          ixLead++;
          y->data[iyLead] = tmp1;
          iyLead++;
        }
      }
    }
  }
}

/*
 * File trailer for diff.c
 *
 * [EOF]
 */
