/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: mtimes.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 23-Oct-2020 11:12:36
 */

/* Include Files */
#include "mtimes.h"
#include "features.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const emxArray_real_T *A
 *                const emxArray_real_T *B
 *                double C_data[]
 *                int C_size[2]
 * Return Type  : void
 */
void mtimes(const emxArray_real_T *A, const emxArray_real_T *B, double C_data[],
            int C_size[2])
{
  int inner;
  int nc;
  int j;
  int boffset;
  int k;
  inner = A->size[1];
  nc = B->size[1];
  C_size[0] = 1;
  C_size[1] = B->size[1];
  for (j = 0; j < nc; j++) {
    boffset = j * B->size[0];
    C_data[j] = 0.0;
    for (k = 0; k < inner; k++) {
      C_data[j] += A->data[k] * B->data[boffset + k];
    }
  }
}

/*
 * File trailer for mtimes.c
 *
 * [EOF]
 */
