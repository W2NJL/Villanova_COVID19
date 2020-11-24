/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: mycell.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 16-Nov-2020 11:39:11
 */

/* Include Files */
#include "mycell.h"
#include "phase_sep.h"
#include "phase_sep_emxutil.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : cell_wrap_0 x[100]
 * Return Type  : void
 */
void mycell(cell_wrap_0 x[100])
{
  int i;
  i = x[0].f1->size[0] * x[0].f1->size[1];
  x[0].f1->size[0] = 1;
  x[0].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[0].f1, i);
  i = x[1].f1->size[0] * x[1].f1->size[1];
  x[1].f1->size[0] = 1;
  x[1].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[1].f1, i);
  i = x[2].f1->size[0] * x[2].f1->size[1];
  x[2].f1->size[0] = 1;
  x[2].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[2].f1, i);
  i = x[3].f1->size[0] * x[3].f1->size[1];
  x[3].f1->size[0] = 1;
  x[3].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[3].f1, i);
  i = x[4].f1->size[0] * x[4].f1->size[1];
  x[4].f1->size[0] = 1;
  x[4].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[4].f1, i);
  i = x[5].f1->size[0] * x[5].f1->size[1];
  x[5].f1->size[0] = 1;
  x[5].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[5].f1, i);
  i = x[6].f1->size[0] * x[6].f1->size[1];
  x[6].f1->size[0] = 1;
  x[6].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[6].f1, i);
  i = x[7].f1->size[0] * x[7].f1->size[1];
  x[7].f1->size[0] = 1;
  x[7].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[7].f1, i);
  i = x[8].f1->size[0] * x[8].f1->size[1];
  x[8].f1->size[0] = 1;
  x[8].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[8].f1, i);
  i = x[9].f1->size[0] * x[9].f1->size[1];
  x[9].f1->size[0] = 1;
  x[9].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[9].f1, i);
  i = x[10].f1->size[0] * x[10].f1->size[1];
  x[10].f1->size[0] = 1;
  x[10].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[10].f1, i);
  i = x[11].f1->size[0] * x[11].f1->size[1];
  x[11].f1->size[0] = 1;
  x[11].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[11].f1, i);
  i = x[12].f1->size[0] * x[12].f1->size[1];
  x[12].f1->size[0] = 1;
  x[12].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[12].f1, i);
  i = x[13].f1->size[0] * x[13].f1->size[1];
  x[13].f1->size[0] = 1;
  x[13].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[13].f1, i);
  i = x[14].f1->size[0] * x[14].f1->size[1];
  x[14].f1->size[0] = 1;
  x[14].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[14].f1, i);
  i = x[15].f1->size[0] * x[15].f1->size[1];
  x[15].f1->size[0] = 1;
  x[15].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[15].f1, i);
  i = x[16].f1->size[0] * x[16].f1->size[1];
  x[16].f1->size[0] = 1;
  x[16].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[16].f1, i);
  i = x[17].f1->size[0] * x[17].f1->size[1];
  x[17].f1->size[0] = 1;
  x[17].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[17].f1, i);
  i = x[18].f1->size[0] * x[18].f1->size[1];
  x[18].f1->size[0] = 1;
  x[18].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[18].f1, i);
  i = x[19].f1->size[0] * x[19].f1->size[1];
  x[19].f1->size[0] = 1;
  x[19].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[19].f1, i);
  i = x[20].f1->size[0] * x[20].f1->size[1];
  x[20].f1->size[0] = 1;
  x[20].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[20].f1, i);
  i = x[21].f1->size[0] * x[21].f1->size[1];
  x[21].f1->size[0] = 1;
  x[21].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[21].f1, i);
  i = x[22].f1->size[0] * x[22].f1->size[1];
  x[22].f1->size[0] = 1;
  x[22].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[22].f1, i);
  i = x[23].f1->size[0] * x[23].f1->size[1];
  x[23].f1->size[0] = 1;
  x[23].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[23].f1, i);
  i = x[24].f1->size[0] * x[24].f1->size[1];
  x[24].f1->size[0] = 1;
  x[24].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[24].f1, i);
  i = x[25].f1->size[0] * x[25].f1->size[1];
  x[25].f1->size[0] = 1;
  x[25].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[25].f1, i);
  i = x[26].f1->size[0] * x[26].f1->size[1];
  x[26].f1->size[0] = 1;
  x[26].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[26].f1, i);
  i = x[27].f1->size[0] * x[27].f1->size[1];
  x[27].f1->size[0] = 1;
  x[27].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[27].f1, i);
  i = x[28].f1->size[0] * x[28].f1->size[1];
  x[28].f1->size[0] = 1;
  x[28].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[28].f1, i);
  i = x[29].f1->size[0] * x[29].f1->size[1];
  x[29].f1->size[0] = 1;
  x[29].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[29].f1, i);
  i = x[30].f1->size[0] * x[30].f1->size[1];
  x[30].f1->size[0] = 1;
  x[30].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[30].f1, i);
  i = x[31].f1->size[0] * x[31].f1->size[1];
  x[31].f1->size[0] = 1;
  x[31].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[31].f1, i);
  i = x[32].f1->size[0] * x[32].f1->size[1];
  x[32].f1->size[0] = 1;
  x[32].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[32].f1, i);
  i = x[33].f1->size[0] * x[33].f1->size[1];
  x[33].f1->size[0] = 1;
  x[33].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[33].f1, i);
  i = x[34].f1->size[0] * x[34].f1->size[1];
  x[34].f1->size[0] = 1;
  x[34].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[34].f1, i);
  i = x[35].f1->size[0] * x[35].f1->size[1];
  x[35].f1->size[0] = 1;
  x[35].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[35].f1, i);
  i = x[36].f1->size[0] * x[36].f1->size[1];
  x[36].f1->size[0] = 1;
  x[36].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[36].f1, i);
  i = x[37].f1->size[0] * x[37].f1->size[1];
  x[37].f1->size[0] = 1;
  x[37].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[37].f1, i);
  i = x[38].f1->size[0] * x[38].f1->size[1];
  x[38].f1->size[0] = 1;
  x[38].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[38].f1, i);
  i = x[39].f1->size[0] * x[39].f1->size[1];
  x[39].f1->size[0] = 1;
  x[39].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[39].f1, i);
  i = x[40].f1->size[0] * x[40].f1->size[1];
  x[40].f1->size[0] = 1;
  x[40].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[40].f1, i);
  i = x[41].f1->size[0] * x[41].f1->size[1];
  x[41].f1->size[0] = 1;
  x[41].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[41].f1, i);
  i = x[42].f1->size[0] * x[42].f1->size[1];
  x[42].f1->size[0] = 1;
  x[42].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[42].f1, i);
  i = x[43].f1->size[0] * x[43].f1->size[1];
  x[43].f1->size[0] = 1;
  x[43].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[43].f1, i);
  i = x[44].f1->size[0] * x[44].f1->size[1];
  x[44].f1->size[0] = 1;
  x[44].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[44].f1, i);
  i = x[45].f1->size[0] * x[45].f1->size[1];
  x[45].f1->size[0] = 1;
  x[45].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[45].f1, i);
  i = x[46].f1->size[0] * x[46].f1->size[1];
  x[46].f1->size[0] = 1;
  x[46].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[46].f1, i);
  i = x[47].f1->size[0] * x[47].f1->size[1];
  x[47].f1->size[0] = 1;
  x[47].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[47].f1, i);
  i = x[48].f1->size[0] * x[48].f1->size[1];
  x[48].f1->size[0] = 1;
  x[48].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[48].f1, i);
  i = x[49].f1->size[0] * x[49].f1->size[1];
  x[49].f1->size[0] = 1;
  x[49].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[49].f1, i);
  i = x[50].f1->size[0] * x[50].f1->size[1];
  x[50].f1->size[0] = 1;
  x[50].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[50].f1, i);
  i = x[51].f1->size[0] * x[51].f1->size[1];
  x[51].f1->size[0] = 1;
  x[51].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[51].f1, i);
  i = x[52].f1->size[0] * x[52].f1->size[1];
  x[52].f1->size[0] = 1;
  x[52].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[52].f1, i);
  i = x[53].f1->size[0] * x[53].f1->size[1];
  x[53].f1->size[0] = 1;
  x[53].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[53].f1, i);
  i = x[54].f1->size[0] * x[54].f1->size[1];
  x[54].f1->size[0] = 1;
  x[54].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[54].f1, i);
  i = x[55].f1->size[0] * x[55].f1->size[1];
  x[55].f1->size[0] = 1;
  x[55].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[55].f1, i);
  i = x[56].f1->size[0] * x[56].f1->size[1];
  x[56].f1->size[0] = 1;
  x[56].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[56].f1, i);
  i = x[57].f1->size[0] * x[57].f1->size[1];
  x[57].f1->size[0] = 1;
  x[57].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[57].f1, i);
  i = x[58].f1->size[0] * x[58].f1->size[1];
  x[58].f1->size[0] = 1;
  x[58].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[58].f1, i);
  i = x[59].f1->size[0] * x[59].f1->size[1];
  x[59].f1->size[0] = 1;
  x[59].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[59].f1, i);
  i = x[60].f1->size[0] * x[60].f1->size[1];
  x[60].f1->size[0] = 1;
  x[60].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[60].f1, i);
  i = x[61].f1->size[0] * x[61].f1->size[1];
  x[61].f1->size[0] = 1;
  x[61].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[61].f1, i);
  i = x[62].f1->size[0] * x[62].f1->size[1];
  x[62].f1->size[0] = 1;
  x[62].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[62].f1, i);
  i = x[63].f1->size[0] * x[63].f1->size[1];
  x[63].f1->size[0] = 1;
  x[63].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[63].f1, i);
  i = x[64].f1->size[0] * x[64].f1->size[1];
  x[64].f1->size[0] = 1;
  x[64].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[64].f1, i);
  i = x[65].f1->size[0] * x[65].f1->size[1];
  x[65].f1->size[0] = 1;
  x[65].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[65].f1, i);
  i = x[66].f1->size[0] * x[66].f1->size[1];
  x[66].f1->size[0] = 1;
  x[66].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[66].f1, i);
  i = x[67].f1->size[0] * x[67].f1->size[1];
  x[67].f1->size[0] = 1;
  x[67].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[67].f1, i);
  i = x[68].f1->size[0] * x[68].f1->size[1];
  x[68].f1->size[0] = 1;
  x[68].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[68].f1, i);
  i = x[69].f1->size[0] * x[69].f1->size[1];
  x[69].f1->size[0] = 1;
  x[69].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[69].f1, i);
  i = x[70].f1->size[0] * x[70].f1->size[1];
  x[70].f1->size[0] = 1;
  x[70].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[70].f1, i);
  i = x[71].f1->size[0] * x[71].f1->size[1];
  x[71].f1->size[0] = 1;
  x[71].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[71].f1, i);
  i = x[72].f1->size[0] * x[72].f1->size[1];
  x[72].f1->size[0] = 1;
  x[72].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[72].f1, i);
  i = x[73].f1->size[0] * x[73].f1->size[1];
  x[73].f1->size[0] = 1;
  x[73].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[73].f1, i);
  i = x[74].f1->size[0] * x[74].f1->size[1];
  x[74].f1->size[0] = 1;
  x[74].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[74].f1, i);
  i = x[75].f1->size[0] * x[75].f1->size[1];
  x[75].f1->size[0] = 1;
  x[75].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[75].f1, i);
  i = x[76].f1->size[0] * x[76].f1->size[1];
  x[76].f1->size[0] = 1;
  x[76].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[76].f1, i);
  i = x[77].f1->size[0] * x[77].f1->size[1];
  x[77].f1->size[0] = 1;
  x[77].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[77].f1, i);
  i = x[78].f1->size[0] * x[78].f1->size[1];
  x[78].f1->size[0] = 1;
  x[78].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[78].f1, i);
  i = x[79].f1->size[0] * x[79].f1->size[1];
  x[79].f1->size[0] = 1;
  x[79].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[79].f1, i);
  i = x[80].f1->size[0] * x[80].f1->size[1];
  x[80].f1->size[0] = 1;
  x[80].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[80].f1, i);
  i = x[81].f1->size[0] * x[81].f1->size[1];
  x[81].f1->size[0] = 1;
  x[81].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[81].f1, i);
  i = x[82].f1->size[0] * x[82].f1->size[1];
  x[82].f1->size[0] = 1;
  x[82].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[82].f1, i);
  i = x[83].f1->size[0] * x[83].f1->size[1];
  x[83].f1->size[0] = 1;
  x[83].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[83].f1, i);
  i = x[84].f1->size[0] * x[84].f1->size[1];
  x[84].f1->size[0] = 1;
  x[84].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[84].f1, i);
  i = x[85].f1->size[0] * x[85].f1->size[1];
  x[85].f1->size[0] = 1;
  x[85].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[85].f1, i);
  i = x[86].f1->size[0] * x[86].f1->size[1];
  x[86].f1->size[0] = 1;
  x[86].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[86].f1, i);
  i = x[87].f1->size[0] * x[87].f1->size[1];
  x[87].f1->size[0] = 1;
  x[87].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[87].f1, i);
  i = x[88].f1->size[0] * x[88].f1->size[1];
  x[88].f1->size[0] = 1;
  x[88].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[88].f1, i);
  i = x[89].f1->size[0] * x[89].f1->size[1];
  x[89].f1->size[0] = 1;
  x[89].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[89].f1, i);
  i = x[90].f1->size[0] * x[90].f1->size[1];
  x[90].f1->size[0] = 1;
  x[90].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[90].f1, i);
  i = x[91].f1->size[0] * x[91].f1->size[1];
  x[91].f1->size[0] = 1;
  x[91].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[91].f1, i);
  i = x[92].f1->size[0] * x[92].f1->size[1];
  x[92].f1->size[0] = 1;
  x[92].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[92].f1, i);
  i = x[93].f1->size[0] * x[93].f1->size[1];
  x[93].f1->size[0] = 1;
  x[93].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[93].f1, i);
  i = x[94].f1->size[0] * x[94].f1->size[1];
  x[94].f1->size[0] = 1;
  x[94].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[94].f1, i);
  i = x[95].f1->size[0] * x[95].f1->size[1];
  x[95].f1->size[0] = 1;
  x[95].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[95].f1, i);
  i = x[96].f1->size[0] * x[96].f1->size[1];
  x[96].f1->size[0] = 1;
  x[96].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[96].f1, i);
  i = x[97].f1->size[0] * x[97].f1->size[1];
  x[97].f1->size[0] = 1;
  x[97].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[97].f1, i);
  i = x[98].f1->size[0] * x[98].f1->size[1];
  x[98].f1->size[0] = 1;
  x[98].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[98].f1, i);
  i = x[99].f1->size[0] * x[99].f1->size[1];
  x[99].f1->size[0] = 1;
  x[99].f1->size[1] = 100;
  phaseemxEnsureCapacity_real_T(x[99].f1, i);
  for (i = 0; i < 100; i++) {
    x[0].f1->data[i] = 0.0;
    x[1].f1->data[i] = 0.0;
    x[2].f1->data[i] = 0.0;
    x[3].f1->data[i] = 0.0;
    x[4].f1->data[i] = 0.0;
    x[5].f1->data[i] = 0.0;
    x[6].f1->data[i] = 0.0;
    x[7].f1->data[i] = 0.0;
    x[8].f1->data[i] = 0.0;
    x[9].f1->data[i] = 0.0;
    x[10].f1->data[i] = 0.0;
    x[11].f1->data[i] = 0.0;
    x[12].f1->data[i] = 0.0;
    x[13].f1->data[i] = 0.0;
    x[14].f1->data[i] = 0.0;
    x[15].f1->data[i] = 0.0;
    x[16].f1->data[i] = 0.0;
    x[17].f1->data[i] = 0.0;
    x[18].f1->data[i] = 0.0;
    x[19].f1->data[i] = 0.0;
    x[20].f1->data[i] = 0.0;
    x[21].f1->data[i] = 0.0;
    x[22].f1->data[i] = 0.0;
    x[23].f1->data[i] = 0.0;
    x[24].f1->data[i] = 0.0;
    x[25].f1->data[i] = 0.0;
    x[26].f1->data[i] = 0.0;
    x[27].f1->data[i] = 0.0;
    x[28].f1->data[i] = 0.0;
    x[29].f1->data[i] = 0.0;
    x[30].f1->data[i] = 0.0;
    x[31].f1->data[i] = 0.0;
    x[32].f1->data[i] = 0.0;
    x[33].f1->data[i] = 0.0;
    x[34].f1->data[i] = 0.0;
    x[35].f1->data[i] = 0.0;
    x[36].f1->data[i] = 0.0;
    x[37].f1->data[i] = 0.0;
    x[38].f1->data[i] = 0.0;
    x[39].f1->data[i] = 0.0;
    x[40].f1->data[i] = 0.0;
    x[41].f1->data[i] = 0.0;
    x[42].f1->data[i] = 0.0;
    x[43].f1->data[i] = 0.0;
    x[44].f1->data[i] = 0.0;
    x[45].f1->data[i] = 0.0;
    x[46].f1->data[i] = 0.0;
    x[47].f1->data[i] = 0.0;
    x[48].f1->data[i] = 0.0;
    x[49].f1->data[i] = 0.0;
    x[50].f1->data[i] = 0.0;
    x[51].f1->data[i] = 0.0;
    x[52].f1->data[i] = 0.0;
    x[53].f1->data[i] = 0.0;
    x[54].f1->data[i] = 0.0;
    x[55].f1->data[i] = 0.0;
    x[56].f1->data[i] = 0.0;
    x[57].f1->data[i] = 0.0;
    x[58].f1->data[i] = 0.0;
    x[59].f1->data[i] = 0.0;
    x[60].f1->data[i] = 0.0;
    x[61].f1->data[i] = 0.0;
    x[62].f1->data[i] = 0.0;
    x[63].f1->data[i] = 0.0;
    x[64].f1->data[i] = 0.0;
    x[65].f1->data[i] = 0.0;
    x[66].f1->data[i] = 0.0;
    x[67].f1->data[i] = 0.0;
    x[68].f1->data[i] = 0.0;
    x[69].f1->data[i] = 0.0;
    x[70].f1->data[i] = 0.0;
    x[71].f1->data[i] = 0.0;
    x[72].f1->data[i] = 0.0;
    x[73].f1->data[i] = 0.0;
    x[74].f1->data[i] = 0.0;
    x[75].f1->data[i] = 0.0;
    x[76].f1->data[i] = 0.0;
    x[77].f1->data[i] = 0.0;
    x[78].f1->data[i] = 0.0;
    x[79].f1->data[i] = 0.0;
    x[80].f1->data[i] = 0.0;
    x[81].f1->data[i] = 0.0;
    x[82].f1->data[i] = 0.0;
    x[83].f1->data[i] = 0.0;
    x[84].f1->data[i] = 0.0;
    x[85].f1->data[i] = 0.0;
    x[86].f1->data[i] = 0.0;
    x[87].f1->data[i] = 0.0;
    x[88].f1->data[i] = 0.0;
    x[89].f1->data[i] = 0.0;
    x[90].f1->data[i] = 0.0;
    x[91].f1->data[i] = 0.0;
    x[92].f1->data[i] = 0.0;
    x[93].f1->data[i] = 0.0;
    x[94].f1->data[i] = 0.0;
    x[95].f1->data[i] = 0.0;
    x[96].f1->data[i] = 0.0;
    x[97].f1->data[i] = 0.0;
    x[98].f1->data[i] = 0.0;
    x[99].f1->data[i] = 0.0;
  }
}

/*
 * File trailer for mycell.c
 *
 * [EOF]
 */
