/*
 * Academic License - for use in teaching, academic research, and meeting
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
#include "filtering_terminate.h"
#include <stdio.h>
#include <stdlib.h>

/* Function Declarations */
static void argInit_1x2411040_real_T(double result[2411040]);
static double argInit_real_T(void);
static double* main_filtering();

/* Function Definitions */

/*
 * Arguments    : double result[2411040]
 * Return Type  : void
 */
static void argInit_1x2411040_real_T(double result[2411040])
{
  int idx1;

  /* Loop over the array to initialize each element. */
  for (idx1 = 0; idx1 < 2411040; idx1++) {
    /* Set the value of the array element.
       Change this value to the value that the application requires. */
    result[idx1] = argInit_real_T();
  }
}

/*
 * Arguments    : void
 * Return Type  : double
 */
static double argInit_real_T(void)
{
  return 0.0;
}

/*
 * Arguments    : void
 * Return Type  : void
 */
static double*  main_filtering()
{
  static double dv[2411040];
  static double y_fil[2411040];

   for(int i=0; i<2411040; i++){
                dv[i] = 0.1112;
		dv[i+1] = 0.2010; 
		dv[i+2] = -0.1010; 

		i = i+2; 
        }

  
  
  /* Initialize function 'filtering' input arguments. */
  /* Initialize function input argument 'y_raw'. */
  /* Call the entry-point 'filtering'. */
  
  filtering(dv, y_fil);

   printf("Location 0: %f\n", dv[0]);
   printf("Location 1: %f\n", dv[1]);
   printf("Location 2: %f\n", dv[2]);
   printf("Location 3: %f\n", dv[3]);
   printf("Location 4: %f\n", dv[4]);
   printf("Location 5: %f\n", dv[5]);
   printf("Location 6: %f\n", dv[6]);
   printf("Location 7: %f\n", dv[7]);
   printf("Location 8: %f\n", dv[8]);
   printf("Location 9: %f\n", dv[9]);
   printf("Location 10: %f\n", dv[10]);

  return y_fil; 
}

/*
 * Arguments    : int argc
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
  double* y = main_filtering();

      int length = sizeof(y)/sizeof(y[0]); 

      double min = y[0]; 
      double max = y[0];

       for (int i=0; i <sizeof(y); i++){
            if(y [i] > max)
                   max = y[i];
     }


     for (int i=0; i <2411040; i++){
 	     if(y [i] < min)
		   min = y[i];
     }

      printf("Largest element of filtered arrays: %f\n", max);

    printf("Smallest element of filtered arrays: %f\n", min); 


  printf("Location 0: %f\n", y[0]);
   printf("Location 1: %f\n", y[1]);
   printf("Location 2: %f\n", y[2]);
   printf("Location 3: %f\n", y[3]);
   printf("Location 4: %f\n", y[4]);
   printf("Location 5: %f\n", y[5]);
   printf("Location 6: %f\n", y[6]);
   printf("Location 7: %f\n", y[7]);
   printf("Location 8: %f\n", y[8]);
   printf("Location 9: %f\n", y[9]);
   printf("Location 10: %f\n", y[10]);


  /* Terminate the application.
     You do not need to do this more than one time. */
  filtering_terminate();



  


  return 0;
}



/*
 * File trailer for main.c
 *
 * [EOF]
 */
