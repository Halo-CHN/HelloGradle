#include "com_chn_halo_util_JniUtils.h"

/*
 * Class:     com_chn_halo_util_JniUtils
 * Method:    getKey
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_chn_halo_util_JniUtils_getKey
  (JNIEnv *env, jobject obj){
        return (*env)->NewStringUTF(env,"fuck!!");
  }