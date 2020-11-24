/*
 * Academic License - for use in teachinig, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: main.c
 *
 * MATLAB Coder version            : 5.0
 * C/C++ source code generated on  : 21-Sep-2020 22:35:40
 */

/*************************************************************************/
/* This automatically generated example C main file shows how to call    */
/* entry-point functions that MATLAB Coder generated. You must customize */
/* this file for your application. Do not modify this file directly.     */
/* Instead, make a copy of this file, modify it, and integrate it into   */
/* your development environment.                                         */
/*                                                                       */
/* This file initializes entry-point function arguments to a default     */
/* size and value before calling the entry-point functions. It does      */
/* not store or use any values returned from the entry-point functions.  */
/* If necessary, it does pre-allocate memory for returned values.        */
/* You can use this file as a starting point for a main function that    */
/* you can deploy in your application.                                   */
/*                                                                       */
/* After you copy the file, and before you deploy it, you must make the  */
/* following changes:                                                    */
/* * For variable-size function arguments, change the example sizes to   */
/* the sizes that your application requires.                             */
/* * Change the example values of function arguments to the values that  */
/* your application requires.                                            */
/* * If the entry-point functions return values, store these values or   */
/* otherwise use them as required by your application.                   */
/*                                                                       */
/*************************************************************************/

/* Include Files */
#include "main.h"
#include "filtering.h"
#include "phase_sep_emxAPI.h"
#include "filtering_emxAPI.h"
#include "filtering_terminate.h"
#include <stdio.h>
#include <stdlib.h>
#include <sndfile.h>
#include "rt_nonfinite.h"
#include "phase_sep.h"
#include "phase_sep_terminate.h"
#include "ac_feat.h"
#include "ac_feat_emxAPI.h"
#include "ac_feat_terminate.h"

/* Function Declarations */
static double* main_filtering();

static double*  main_filtering()
{
 
	/* Initialize filtered wav file array, wav file variables */
  static double y_fil[2902260];
   SNDFILE *sf;
    SF_INFO info;
    int num_channels;
    int num, num_items;
    double *buf;
    int f,sr,c;
    int i,j;

    /* Open the WAV file. */
    info.format = 0;
    sf = sf_open("24.wav",SFM_READ,&info);
    if (sf == NULL)
        {
        printf("Failed to open the file.\n");
        exit(-1);
        }
/* Print some of the info, and figure out how much data to read. */
    f = info.frames;
    sr = info.samplerate;
    c = info.channels;
    printf("frames=%d\n",f);
    printf("samplerate=%d\n",sr);
    printf("channels=%d\n",c);
    num_items = f*c;
    printf("num_items=%d\n",num_items);
    /* Allocate space for the data to be read, then read it. */
    buf = (double *) malloc(num_items*sizeof(double));
    num = sf_readf_double(sf,buf,num_items);
    sf_close(sf);
    printf("Read %d items\n",num);


   

/* Initialize the emx Arrays for the filtered and unfiltered WAV data,intialize the double arrays for the selected phase data */
  
emxArray_real_T *rawInputs;
emxArray_real_T *filteredOutputs; 


double siz_in_data[100];
double siz_ex_data[100];
double feat_ac[9];
int siz_in_size[2];
int siz_ex_size[2];
int insize;
int exsize;
int inelements;
int outelements;



/* Calculate the mean of the unfiltered data, subtract the mean from the filtered data */

double mean; 
for (int i=0; i<2902260; i++)
	mean = mean + buf[i];

mean = mean/2902260;

for(int z=0; z<2902260; z++)
	buf[z] = buf[z] - mean;


/* Fill the emx arrays */

rawInputs = emxCreateWrapper_real_T(buf, 2902260, 1);
filteredOutputs = emxCreateWrapper_real_T(y_fil, 2902260, 1);




   /* Initialize function 'filtering' input arguments. */
  /* Initialize function input argument 'y_raw'. */
  /* Call the entry-point 'filtering'. */
  
  filtering(rawInputs, 48000, filteredOutputs);





/*Initialize the cell_wrap arrays */
cell_wrap_0 y_ph_in[100];
  cell_wrap_0 y_ph_fil_in[100];
  cell_wrap_0 y_ph_ex[100];
  cell_wrap_0 y_ph_fil_ex[100];
  emxInitMatrix_cell_wrap_01(y_ph_in);
  emxInitMatrix_cell_wrap_01(y_ph_fil_in);
  emxInitMatrix_cell_wrap_01(y_ph_ex);
  emxInitMatrix_cell_wrap_01(y_ph_fil_ex);
  
/*Run the phase sep function */

phase_sep(rawInputs, filteredOutputs, y_ph_in, y_ph_fil_in, y_ph_ex, y_ph_fil_ex, siz_in_data, siz_in_size, siz_ex_data, siz_ex_size);




  emxDestroyArray_real_T(filteredOutputs);
  emxDestroyArray_real_T(rawInputs);



inelements = siz_in_size[1];
outelements = siz_ex_size[1];
double featArr[inelements+outelements][9];   


for(int i=0; i<inelements; i++){

ac_feat(y_ph_in[i].f1, y_ph_fil_in[i].f1, featArr[i]);
/*for(int j=0; j<9; j++){
	featArr[i][j] = feat_ac[j];
}*/
}

int featArrCount = outelements + inelements - inelements +1;
int z=0;
for(int i=featArrCount; i<outelements+inelements; i++)
{
ac_feat(y_ph_ex[z].f1, y_ph_fil_ex[z].f1, featArr[i]);
/*for(int j=0; j<9; j++){
	featArr[featArrCount][j] = feat_ac[j];
}*/

z++;
}

 emxDestroyMatrix_cell_wrap_0(y_ph_fil_ex);
  emxDestroyMatrix_cell_wrap_0(y_ph_ex);
  emxDestroyMatrix_cell_wrap_0(y_ph_fil_in);
  emxDestroyMatrix_cell_wrap_0(y_ph_in);





printf("feat_ac place 0,8: %f\n", featArr[0][8]);
printf("feat_ac place 17,0: %f\n", featArr[17][0]);
printf("feat_ac place 1,0: %f\n", featArr[1][0]);
printf("feat_ac place 3,8: %f\n", featArr[3][7]);
printf("feat_ac place 18,0: %f\n", featArr[18][0]);
printf("feat_ac place 33,6: %f\n", featArr[34][6]);


/*printf("inhale place 0: %f\n", siz_in_data[0]);
printf("inhale array size %d\n", siz_in_size[1]);
printf("exhale place 0: %f\n", siz_ex_data[0]);
printf("exhale array size: %d\n", siz_ex_size[1]);*/








return y_fil; 
}

/*
 * Arguments    : int argc00
 *                const char * const argv[]
 * Return Type  : int
 */
int main(int argc, const char * const argv[])
{
  (void)argc;
  (void)argv;

  /* The initialize function is being called automatically from your entry-point function. So, a call to initialize is not included here. */
  /* Invoke the entry-point functions.
     You can call entry-point functions multiple times. */
 
  double* y;
 
  y = main_filtering();


 




       
        
     





  /* Terminate the application.
     You do not need to do this more than one time. */
  filtering_terminate();
  phase_sep_terminate();



  


  return 0;
}

/*
 * File trailer for main.c
 *
 * [EOF]
 */
