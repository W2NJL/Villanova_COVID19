//
// Created by nick on 9/25/2020.
//
#include <stdio.h>
#include <stdlib.h>
#include "algorithm.h"
#include "../../../../../../AppData/Local/Android/Sdk/ndk/21.0.6113669/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/jni.h"
#include "main.h"
#include "filtering.h"
#include "filtering_terminate.h"
#include "rt_nonfinite.h"
#include "features.h"
#include "features_emxAPI.h"
#include "features_terminate.h"



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
                                                           jdoubleArray jarr, jdoubleArray jarr2) {
    static double dv2[2890080];
    static double y_fil[2890080];
    static double subarray[37973];


    jdouble *arr = (*env)->GetDoubleArrayElements(env, jarr, NULL);
    jdouble *arr2 = (*env)->GetDoubleArrayElements(env, jarr2, NULL);
    double res=0;
    int size = (*env)->GetArrayLength(env, jarr);
    for(int i=0;i<size;i++)
        dv2[i] = arr[i];


    filtering(dv2, y_fil);

    for(int i=0;i<size;i++)
        arr[i] = y_fil[i];


    double datax[3];

    double entropy_dat1;
    double Sk;
    double Ku;

    int j = 540296;
    for(int i=0; i<37974; i++){
        subarray [i] = dv2[j];
        j++;
    }

    emxArray_real_T *inputs;

    inputs = emxCreateWrapper_real_T(subarray, 37973, 1);

    features(inputs, &entropy_dat1, &Sk, &Ku);

    datax[0] = entropy_dat1;
    datax[1] = Sk;
    datax[2] = Ku;

    for(int i=0;i<3;i++)
        arr2[i] = datax[i];


    (*env)->ReleaseDoubleArrayElements(env, jarr, arr, 0);
    (*env)->ReleaseDoubleArrayElements(env, jarr2, arr2, 0);

    return res;
}