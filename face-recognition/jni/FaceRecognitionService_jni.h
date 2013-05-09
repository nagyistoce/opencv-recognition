/*
 * FaceRecgnitionService.h
 *
 *  Created on: 08/05/2013
 *      Author: almde89
 */
#include <jni.h>

#ifndef _Included_br_ufghomework_facerecognition_service_FaceRecognitionService
#define _Included_br_ufghomework_facerecognition_service_FaceRecognitionService
#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     br_ufghomework_facerecognition_service_FaceRecognitionService
 * Method:    nativeRecognize
 */
JNIEXPORT jstring JNICALL Java_br_ufghomework_facerecognition_service_FaceRecognitionService_nativeRecognize
  (JNIEnv *, jclass, jlong, jstring);

#ifdef __cplusplus
}
#endif
#endif
