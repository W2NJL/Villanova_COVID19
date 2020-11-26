/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: xdhseqr.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 05-Nov-2020 13:57:38
 */

/* Include Files */
#include "xdhseqr.h"
#include "filtering.h"
#include "filtering_rtwutil.h"
#include "rt_nonfinite.h"
#include "xdlanv2.h"
#include "xnrm2.h"
#include <math.h>

/* Function Definitions */

/*
 * Arguments    : double h[36]
 * Return Type  : int
 */
int eml_dlahqr(double h[36])
{
  int info;
  double v[3];
  int i;
  bool exitg1;
  int L;
  bool goto150;
  int its;
  bool exitg2;
  int k;
  bool exitg3;
  int b_i;
  int hoffset;
  double s;
  int nr;
  double ba;
  int knt;
  double d;
  double tst;
  double bb;
  double ab;
  double aa;
  double h22;
  double rt1r;
  int iy;
  int m;
  int b_k;
  int j;
  info = 0;
  v[0] = 0.0;
  h[2] = 0.0;
  h[3] = 0.0;
  v[1] = 0.0;
  h[9] = 0.0;
  h[10] = 0.0;
  v[2] = 0.0;
  h[16] = 0.0;
  h[17] = 0.0;
  h[23] = 0.0;
  i = 5;
  exitg1 = false;
  while ((!exitg1) && (i + 1 >= 1)) {
    L = 1;
    goto150 = false;
    its = 0;
    exitg2 = false;
    while ((!exitg2) && (its < 301)) {
      k = i;
      exitg3 = false;
      while ((!exitg3) && (k + 1 > L)) {
        b_i = k + 6 * (k - 1);
        ba = fabs(h[b_i]);
        if (ba <= 6.0125050800269183E-292) {
          exitg3 = true;
        } else {
          knt = k + 6 * k;
          bb = fabs(h[knt]);
          hoffset = b_i - 1;
          tst = fabs(h[hoffset]) + bb;
          if (tst == 0.0) {
            if (k - 1 >= 1) {
              tst = fabs(h[(k + 6 * (k - 2)) - 1]);
            }

            if (k + 2 <= 6) {
              tst += fabs(h[knt + 1]);
            }
          }

          if (ba <= 2.2204460492503131E-16 * tst) {
            tst = fabs(h[knt - 1]);
            if (ba > tst) {
              ab = ba;
              ba = tst;
            } else {
              ab = tst;
            }

            tst = fabs(h[hoffset] - h[knt]);
            if (bb > tst) {
              aa = bb;
              bb = tst;
            } else {
              aa = tst;
            }

            s = aa + ab;
            if (ba * (ab / s) <= fmax(6.0125050800269183E-292,
                 2.2204460492503131E-16 * (bb * (aa / s)))) {
              exitg3 = true;
            } else {
              k--;
            }
          } else {
            k--;
          }
        }
      }

      L = k + 1;
      if (k + 1 > 1) {
        h[k + 6 * (k - 1)] = 0.0;
      }

      if (k + 1 >= i) {
        goto150 = true;
        exitg2 = true;
      } else {
        if (its == 10) {
          hoffset = k + 6 * k;
          s = fabs(h[hoffset + 1]) + fabs(h[(k + 6 * (k + 1)) + 2]);
          tst = 0.75 * s + h[hoffset];
          aa = -0.4375 * s;
          ab = s;
          h22 = tst;
        } else if (its == 20) {
          s = fabs(h[i + 6 * (i - 1)]) + fabs(h[(i + 6 * (i - 2)) - 1]);
          tst = 0.75 * s + h[i + 6 * i];
          aa = -0.4375 * s;
          ab = s;
          h22 = tst;
        } else {
          hoffset = i + 6 * (i - 1);
          tst = h[hoffset - 1];
          ab = h[hoffset];
          aa = h[(i + 6 * i) - 1];
          h22 = h[i + 6 * i];
        }

        s = ((fabs(tst) + fabs(aa)) + fabs(ab)) + fabs(h22);
        if (s == 0.0) {
          rt1r = 0.0;
          tst = 0.0;
          bb = 0.0;
          ab = 0.0;
        } else {
          tst /= s;
          ab /= s;
          aa /= s;
          h22 /= s;
          ba = (tst + h22) / 2.0;
          tst = (tst - ba) * (h22 - ba) - aa * ab;
          ab = sqrt(fabs(tst));
          if (tst >= 0.0) {
            rt1r = ba * s;
            bb = rt1r;
            tst = ab * s;
            ab = -tst;
          } else {
            rt1r = ba + ab;
            bb = ba - ab;
            if (fabs(rt1r - h22) <= fabs(bb - h22)) {
              rt1r *= s;
              bb = rt1r;
            } else {
              bb *= s;
              rt1r = bb;
            }

            tst = 0.0;
            ab = 0.0;
          }
        }

        m = i - 1;
        exitg3 = false;
        while ((!exitg3) && (m >= k + 1)) {
          hoffset = m + 6 * (m - 1);
          knt = hoffset - 1;
          aa = h[knt] - bb;
          s = (fabs(aa) + fabs(ab)) + fabs(h[hoffset]);
          ba = h[hoffset] / s;
          hoffset = m + 6 * m;
          v[0] = (ba * h[hoffset - 1] + (h[knt] - rt1r) * (aa / s)) - tst * (ab /
            s);
          v[1] = ba * (((h[knt] + h[hoffset]) - rt1r) - bb);
          v[2] = ba * h[hoffset + 1];
          s = (fabs(v[0]) + fabs(v[1])) + fabs(v[2]);
          v[0] /= s;
          v[1] /= s;
          v[2] /= s;
          if (m == k + 1) {
            exitg3 = true;
          } else {
            b_i = m + 6 * (m - 2);
            if (fabs(h[b_i - 1]) * (fabs(v[1]) + fabs(v[2])) <=
                2.2204460492503131E-16 * fabs(v[0]) * ((fabs(h[b_i - 2]) + fabs
                  (h[knt])) + fabs(h[hoffset]))) {
              exitg3 = true;
            } else {
              m--;
            }
          }
        }

        for (b_k = m; b_k <= i; b_k++) {
          nr = (i - b_k) + 2;
          if (3 < nr) {
            nr = 3;
          }

          if (b_k > m) {
            hoffset = (b_k + 6 * (b_k - 2)) - 1;
            for (j = 0; j < nr; j++) {
              v[j] = h[j + hoffset];
            }
          }

          ab = v[0];
          ba = 0.0;
          if (nr > 0) {
            tst = b_xnrm2(nr - 1, v);
            if (tst != 0.0) {
              aa = rt_hypotd_snf(v[0], tst);
              if (v[0] >= 0.0) {
                aa = -aa;
              }

              if (fabs(aa) < 1.0020841800044864E-292) {
                knt = -1;
                do {
                  knt++;
                  for (iy = 2; iy <= nr; iy++) {
                    v[iy - 1] *= 9.9792015476736E+291;
                  }

                  aa *= 9.9792015476736E+291;
                  ab *= 9.9792015476736E+291;
                } while (!(fabs(aa) >= 1.0020841800044864E-292));

                aa = rt_hypotd_snf(ab, b_xnrm2(nr - 1, v));
                if (ab >= 0.0) {
                  aa = -aa;
                }

                ba = (aa - ab) / aa;
                tst = 1.0 / (ab - aa);
                for (iy = 2; iy <= nr; iy++) {
                  v[iy - 1] *= tst;
                }

                for (iy = 0; iy <= knt; iy++) {
                  aa *= 1.0020841800044864E-292;
                }

                ab = aa;
              } else {
                ba = (aa - v[0]) / aa;
                tst = 1.0 / (v[0] - aa);
                for (iy = 2; iy <= nr; iy++) {
                  v[iy - 1] *= tst;
                }

                ab = aa;
              }
            }
          }

          v[0] = ab;
          if (b_k > m) {
            h[(b_k + 6 * (b_k - 2)) - 1] = ab;
            b_i = b_k + 6 * (b_k - 2);
            h[b_i] = 0.0;
            if (b_k < i) {
              h[b_i + 1] = 0.0;
            }
          } else {
            if (m > k + 1) {
              h[(b_k + 6 * (b_k - 2)) - 1] *= 1.0 - ba;
            }
          }

          s = v[1];
          tst = ba * v[1];
          if (nr == 3) {
            d = v[2];
            aa = ba * v[2];
            for (j = b_k; j < 7; j++) {
              iy = b_k + 6 * (j - 1);
              hoffset = iy - 1;
              knt = iy + 1;
              ab = (h[hoffset] + s * h[iy]) + d * h[knt];
              h[hoffset] -= ab * ba;
              h[iy] -= ab * tst;
              h[knt] -= ab * aa;
            }

            if (b_k + 3 < i + 1) {
              b_i = b_k + 2;
            } else {
              b_i = i;
            }

            for (j = 0; j <= b_i; j++) {
              iy = j + 6 * (b_k - 1);
              hoffset = j + 6 * b_k;
              knt = j + 6 * (b_k + 1);
              ab = (h[iy] + s * h[hoffset]) + d * h[knt];
              h[iy] -= ab * ba;
              h[hoffset] -= ab * tst;
              h[knt] -= ab * aa;
            }
          } else {
            if (nr == 2) {
              for (j = b_k; j < 7; j++) {
                iy = b_k + 6 * (j - 1);
                hoffset = iy - 1;
                ab = h[hoffset] + s * h[iy];
                h[hoffset] -= ab * ba;
                h[iy] -= ab * tst;
              }

              for (j = 0; j <= i; j++) {
                iy = j + 6 * (b_k - 1);
                hoffset = j + 6 * b_k;
                ab = h[iy] + s * h[hoffset];
                h[iy] -= ab * ba;
                h[hoffset] -= ab * tst;
              }
            }
          }
        }

        its++;
      }
    }

    if (!goto150) {
      info = i + 1;
      exitg1 = true;
    } else {
      if ((L != i + 1) && (L == i)) {
        b_i = i + 6 * i;
        hoffset = b_i - 1;
        s = h[hoffset];
        nr = 6 * (i - 1);
        knt = i + nr;
        d = h[knt];
        tst = h[b_i];
        xdlanv2(&h[(i + 6 * (i - 1)) - 1], &s, &d, &tst, &ab, &aa, &ba, &bb,
                &h22, &rt1r);
        h[hoffset] = s;
        h[knt] = d;
        h[b_i] = tst;
        if (6 > i + 1) {
          hoffset = 4 - i;
          iy = i + (i + 1) * 6;
          knt = iy - 1;
          for (k = 0; k <= hoffset; k++) {
            tst = h22 * h[knt] + rt1r * h[iy];
            h[iy] = h22 * h[iy] - rt1r * h[knt];
            h[knt] = tst;
            iy += 6;
            knt += 6;
          }
        }

        if (i - 1 >= 1) {
          iy = i * 6;
          for (k = 0; k <= i - 2; k++) {
            tst = h22 * h[nr] + rt1r * h[iy];
            h[iy] = h22 * h[iy] - rt1r * h[nr];
            h[nr] = tst;
            iy++;
            nr++;
          }
        }
      }

      i = L - 2;
    }
  }

  return info;
}

/*
 * File trailer for xdhseqr.c
 *
 * [EOF]
 */
