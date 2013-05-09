/*
 * FaceRecognitionService.cpp
 *
 *  Created on: 08/05/2013
 *      Author: almde89
 */

#include<FaceRecognitionService_jni.h>
#include <opencv2/core/core.hpp>

#include <android/log.h>

#define LOG_TAG "FaceDetection/DetectionBasedTracker"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))

JNIEXPORT jstring JNICALL Java_br_ufghomework_facerecognition_service_FaceRecognitionService_nativeRecognize(JNIEnv * jenv, jclass, jlong faceToRecog) {

	char string[] = {'a', 'n', 'd', 'r', 'e'};
	jstring sample = jenv->NewStringUTF( string );

	return sample;

}
