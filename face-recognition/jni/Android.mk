LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on
#OPENCV_LIB_TYPE:=SHARED
#include /home/andremello/Downloads/OpenCVPacks/OpenCV-2.4.5-android-sdk/sdk/native/jni/OpenCV.mk
include /Users/almde89/Downloads/OpenCV-2.4.5-android-sdk/sdk/native/jni/OpenCV.mk
LOCAL_SRC_FILES  := FaceRecognitionService_jni.cpp
LOCAL_C_INCLUDES += $(LOCAL_PATH)
LOCAL_LDLIBS     += -llog -ldl

LOCAL_MODULE     := detection_based_tracker

include $(BUILD_SHARED_LIBRARY)