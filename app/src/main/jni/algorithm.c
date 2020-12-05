//
// Created by nick on 9/25/2020.
//
#include <stdio.h>
#include <stdlib.h>
#include "algorithm.h"
#include "../../../../../../AppData/Local/Android/Sdk/ndk/21.0.6113669/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/jni.h"
#include "filtering.h"
#include "filtering_terminate.h"
#include "rt_nonfinite.h"
#include "ac_feat.h"
#include "ac_feat_emxAPI.h"
#include "ac_feat_terminate.h"
#include "phase_sep.h"
#include "phase_sep_terminate.h"
#include "phase_sep_emxAPI.h"
#include "filtering_emxAPI.h"


//double *dv;



//
//
//
//static double*  main_filtering()
//{
//
//    static double dv3[2890080];
//    static double y_fil[2890080];
//
//
//
////    SNDFILE *sf;
////    SF_INFO info;
////    int num_channels;
////    int num, num_items;
////    double *buf;
////    int f,sr,c;
////    int i,j;
////    FILE *out;
////
////    /* Open the WAV file. */
////    info.format = 0;
////    sf = sf_open("32mm.wav",SFM_READ,&info);
////    if (sf == NULL)
////    {
////        printf("Failed to open the file.\n");
////        exit(-1);
////    }
/////* Print some of the info, and figure out how much data to read. */
////    f = info.frames;
////    sr = info.samplerate;
////    c = info.channels;
////    printf("frames=%d\n",f);
////    printf("samplerate=%d\n",sr);
////    printf("channels=%d\n",c);
////    num_items = f*c;
////    printf("num_items=%d\n",num_items);
////    /* Allocate space for the data to be read, then read it. */
////    buf = (double *) malloc(num_items*sizeof(double));
////    num = sf_readf_double(sf,buf,num_items);
////    sf_close(sf);
////    printf("Read %d items\n",num);
////
////
////
//    for(int i=0; i<2890080; i++){
//        dv3[i] = 0.1112;
//     dv3[i+1] = 0.2010;
//       dv3[i+2] = -0.1010;
//
//        i = i+2;
//  }
////
////
////    /* Initialize function 'filtering' input arguments. */
////    /* Initialize function input argument 'y_raw'. */
////    /* Call the entry-point 'filtering'. */
////
//    filtering(dv, y_fil);
////
////    printf("Location 0: %f\n", dv[0]);
////    printf("Location 1: %f\n", dv[1]);
////    printf("Location 2: %f\n", dv[2]);
////    printf("Location 3: %f\n", dv[3]);
////    printf("Location 4: %f\n", dv[4]);
////    printf("Location 5: %f\n", dv[5]);
////    printf("Location 6: %f\n", dv[6]);
////    printf("Location 7: %f\n", dv[7]);
////    printf("Location 8: %f\n", dv[8]);
////    printf("Location 9: %f\n", dv[9]);
////    printf("Location 10: %f\n", dv[10]);
//
//    return y_fil;
//}
//
//
//
//
//JNIEXPORT jstring JNICALL Java_com_example_covid19_MainActivity_stringFromJNI(JNIEnv *env, jobject this) {
//
//
//    return (*env)->NewStringUTF(env, "Hello World from C!\n");
//
//
//}
//
//JNIEXPORT jdoubleArray JNICALL
//Java_com_example_covid19_MainActivity_doublefromJNI(JNIEnv *env, jclass clazz) {
//
//    double* y = main_filtering();
//
//    int length = sizeof(y)/sizeof(y[0]);
//
//    double min = y[0];
//    double max = y[0];
//
//    for (int i=0; i <2890080; i++){
//        if(y [i] > max)
//            max = y[i];
//    }
//
//
//    for (int i=0; i <2890080; i++){
//        if(y [i] < min)
//            min = y[i];
//    }
//
//    jdoubleArray fiboarray  = (*env)->NewDoubleArray(env,2890080);
//
//
//    (*env)->SetDoubleArrayRegion(env,fiboarray,0,2890080,y);
//
//
//
//    return fiboarray;
//}
//
//
//
//
//JNIEXPORT jdoubleArray JNICALL
//Java_com_example_covid19_MainActivity_getStats(JNIEnv *env, jclass clazz) {
//
//    double datax[3];
//
//    double y_mn;
//    double y_skew;
//    double y_kur;
//
//    mean_skew_kur(dv, &y_mn, &y_skew, &y_kur);
//
//    datax[0] = y_mn;
//    datax[1] = y_skew;
//    datax[2] = y_kur;
//
//    jdoubleArray fiboarray = (*env)->NewDoubleArray(env, 3);
//
//
//    (*env)->SetDoubleArrayRegion(env, fiboarray, 0, 3, datax);
//
//
//    return fiboarray;
//}
JNIEXPORT jdouble JNICALL
Java_com_w2njl_VillanovaCovid19_CovidRisk_addArray(JNIEnv *env, jclass clazz,
                                                           jdoubleArray jarr, jdoubleArray jarr2, jdoubleArray jarr3, jdoubleArray jarr4) {

   //Construct double and EMX arrays for filtered and unfiltered data

//    static double subarray[37973];
    emxArray_real_T *rawInputs;
    emxArray_real_T *filteredOutputs;

    //Construct data types to store array data
    double siz_in_data[100];
    double siz_ex_data[100];
    double feat_ac[9];
    int siz_in_size[2];
    int siz_ex_size[2];
    int insize;
    int exsize;
    int inhale;
    int exhale;
    int inelements;
    int outelements;

    //Store the Java arrays as JNI data types
    jdouble *arr = (*env)->GetDoubleArrayElements(env, jarr, NULL);
    jdouble *arr2 = (*env)->GetDoubleArrayElements(env, jarr2, NULL);
    jdouble *arr3 = (*env)->GetDoubleArrayElements(env, jarr3, NULL);
    jdouble *arr4 = (*env)->GetDoubleArrayElements(env, jarr4, NULL);
    double res=0;

    //Get elements of WAV file array
    int size = (*env)->GetArrayLength(env, jarr);

    static double dv2[2890080];
    static double y_fil[2890080];

    //Convert from JNI double array to C array
    for(int i=0;i<size;i++)
        dv2[i] = arr[i];

    //Wrap double arrays in EMX array
    rawInputs = emxCreateWrapper_real_T(dv2, 960000, 1);
    filteredOutputs = emxCreateWrapper_real_T(y_fil, 960000, 1);

    //Call the filtering function
    filtering(rawInputs, 16000, filteredOutputs);

    for(int i=0;i<size;i++)
        arr[i] = y_fil[i];

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
    phase_sep(rawInputs, filteredOutputs, 16000, y_ph_in, y_ph_fil_in, y_ph_ex, y_ph_fil_ex, siz_in_data, siz_in_size, siz_ex_data, siz_ex_size);

    insize = siz_in_data[0];
    exsize = siz_ex_data[0];


    double inPhase[insize];
    double exPhase[exsize];

    for(int i=0; i<insize; i++)
        inPhase[i] = y_ph_in[0].f1->data[i];

    for(int i=0; i<exsize; i++)
        exPhase[i] = y_ph_ex[0].f1->data[i];

    for(int i=0;i<insize;i++)
        arr2[i] = inPhase[i];

    for(int i=0;i<exsize;i++)
        arr3[i] = exPhase[i];


    /*Destroy the EMX arrays */
    emxDestroyArray_real_T(filteredOutputs);
    emxDestroyArray_real_T(rawInputs);

    /*Get number of phases */
    inelements = siz_in_size[1];
    outelements = siz_ex_size[1];
    double featArr[inelements+outelements][9];

    /* Call the ACF function */
    for(int i=0; i<inelements; i++){

        ac_feat(y_ph_in[i].f1, y_ph_fil_in[i].f1, featArr[i]);

    }

    int featArrCount = outelements + inelements - inelements +1;
    int z=0;

    for(int i=featArrCount; i<outelements+inelements; i++)
    {
        ac_feat(y_ph_ex[z].f1, y_ph_fil_ex[z].f1, featArr[i]);


        z++;
    }

    /*Destroy the cell wrap data */
    emxDestroyMatrix_cell_wrap_0(y_ph_fil_ex);
    emxDestroyMatrix_cell_wrap_0(y_ph_ex);
    emxDestroyMatrix_cell_wrap_0(y_ph_fil_in);
    emxDestroyMatrix_cell_wrap_0(y_ph_in);


    double datax[4];

//    double entropy_dat1;
//    double Sk;
//    double Ku;
//
//    int j = 540296;
//    for(int i=0; i<37974; i++){
//        subarray [i] = dv2[j];
//        j++;
//    }

//    emxArray_real_T *inputs;
//
//    inputs = emxCreateWrapper_real_T(subarray, 37973, 1);

////    features(inputs, &entropy_dat1, &Sk, &Ku);
//
   datax[0] = inelements;
    datax[1] = outelements;
    datax[2] = insize;
    datax[3] = exsize;
//    datax[2] = Ku;
//
//    for(int i=0;i<inelements+outelements;i++){
//
//        datax[i] = featArr[i][0];
//        }
//
    for(int i=0;i<4;i++)
        arr4[i] = datax[i];

    /*Return the arrays back to Java */

    (*env)->ReleaseDoubleArrayElements(env, jarr, arr, 0);
    (*env)->ReleaseDoubleArrayElements(env, jarr2, arr2, 0);
    (*env)->ReleaseDoubleArrayElements(env, jarr3, arr3, 0);
    (*env)->ReleaseDoubleArrayElements(env, jarr4, arr4, 0);

    return res;
}