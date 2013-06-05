/*
 * FaceRecognitionService.cpp
 *
 *  Created on: 08/05/2013
 *      Author: almde89
 */
#define LOG_TAG "FaceRecognition/FaceRecognitionService"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))
#include <FaceRecognitionService_jni.h>

#include <android/log.h>
/*
 * Copyright (c) 2011. Philipp Wagner <bytefish[at]gmx[dot]de>.
 * Released to public domain under terms of the BSD Simplified license.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the organization nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 *   See <http://www.opensource.org/licenses/bsd-license>
 */

#include "opencv2/core/core.hpp"
#include "opencv2/contrib/contrib.hpp"
#include "opencv2/highgui/highgui.hpp"

#include <iostream>
#include <fstream>
#include <sstream>

using namespace cv;
using namespace std;

static void read_csv(JNIEnv * jenv, const jstring& filename, vector<Mat>& images, vector<jstring>& labels, vector<int>& apiLabels, char separator = ';') {

	const string filneNameString = jenv->GetStringUTFChars( filename, NULL );

    std::ifstream file(filneNameString.c_str(), ifstream::in);
    if (!file) {

    	LOGD( "O arquivo não pôde ser lido." );

		//FIXME colocar depois a classe de excecao

		jclass je = jenv->FindClass("java/lang/Exception");

		if(!je) {

			je = jenv->FindClass("java/lang/Exception");

		}

		jenv->ThrowNew( je, NULL );

    }

    string line, path, classlabel;
    int labelCount = 0;

    while (getline(file, line)) {
        stringstream liness(line);
        getline(liness, path, separator);
        getline(liness, classlabel);

        if(!path.empty() && !classlabel.empty()) {

        	Mat faceToPush = imread( path, IMREAD_GRAYSCALE );

        	LOGD( format( "-------------------------- path %s", path.c_str() ).c_str() );
        	LOGD( format( "-------------------------- channels %d", faceToPush.channels() ).c_str() );

			LOGD( format( "---------------> %d step", faceToPush.step ).c_str() );
			LOGD( format( "---------------> %d cols", faceToPush.cols ).c_str() );
			LOGD( format( "---------------> %d rows", faceToPush.rows ).c_str() );
			LOGD( format( "---------------> %d elemSize", faceToPush.elemSize() ).c_str() );

        	if ( faceToPush.isContinuous() ) {

        		LOGD( format( "Leu uma linha. %s", classlabel.c_str() ).c_str() );
				images.push_back( faceToPush );
				labels.push_back( jenv->NewStringUTF( classlabel.c_str() ) );
				apiLabels.push_back( labelCount++ );

        	} else {

        		LOGD( "A matriz de entrada não é contínua." );

				//FIXME colocar depois a classe de excecao

				jclass je = jenv->FindClass("java/lang/Exception");

				if(!je) {

					je = jenv->FindClass("java/lang/Exception");

				}

				jenv->ThrowNew( je, "A matriz de entrada não é contínua." );

        	}

        }
    }
}

JNIEXPORT jstring JNICALL Java_br_ufghomework_facerecognition_service_FaceRecognitionService_nativeRecognize
(JNIEnv * jenv, jclass, jlong faceToRecog, jstring csvFilePathDsc)
{
	LOGD("Java_br_ufghomework_facerecognition_service_FaceRecognitionService_nativeRecognize enter");

	jstring sample = NULL;

	Mat faceToRecognize = *((Mat*)faceToRecog);

	/*if ( !faceToRecognize.isContinuous() ) {

		LOGD( "A matriz de entrada não é contínua ou não existe." );

		//FIXME colocar depois a classe de excecao

		jclass je = jenv->FindClass("java/lang/Exception");

		if(!je) {

			je = jenv->FindClass("java/lang/Exception");

		}

		jenv->ThrowNew( je, "NÃO ACREDITO QUE ERA ISSO!" );

	}*/

	vector<Mat> images;
	vector<jstring> labels;
	vector<int> apiLabels;

	try {

		read_csv( jenv, csvFilePathDsc, images, labels, apiLabels );

	} catch (cv::Exception& e) {

		LOGD("Erro ao ler o arquivo CSV.", e.what());

		//FIXME colocar depois a classe de excecao

		jclass je = jenv->FindClass("java/lang/Exception");

		if(!je) {

			je = jenv->FindClass("java/lang/Exception");

		}

		jenv->ThrowNew(je, e.what());

	}

	//Avalia se ha um numero suficiente de imagens.
	if(images.size() <= 1) {

		LOGD( "Não há imagens o suficiente para avaliacao." );

		//FIXME colocar depois a classe de excecao

		jclass je = jenv->FindClass("java/lang/Exception");

		if(!je) {

			je = jenv->FindClass("java/lang/Exception");

		}

		jenv->ThrowNew(je, NULL);

	}

	Mat matTest = images[images.size() -1 ];
	apiLabels.pop_back();
	images.pop_back();

	// The following lines create an Eigenfaces model for
	// face recognition and train it with the images and
	// labels read from the given CSV file.
	// This here is a full PCA, if you just want to keep
	// 10 principal components (read Eigenfaces), then call
	// the factory method like this:
	//
	//      cv::createEigenFaceRecognizer(10);
	//
	// If you want to create a FaceRecognizer with a
	// confidence threshold (e.g. 123.0), call it with:
	//
	//      cv::createEigenFaceRecognizer(10, 123.0);
	//
	// If you want to use _all_ Eigenfaces and have a threshold,
	// then call the method like this:
	//
	//      cv::createEigenFaceRecognizer(0, 123.0);
	//
	Ptr<FaceRecognizer> model = createEigenFaceRecognizer();
	model->train(images, apiLabels);
	// The following line predicts the label of a given
	// test image:
	//int predictedLabel = model->predict( faceToRecognize );
	int predictedLabel = model->predict( matTest );

	if ( predictedLabel > -1 ) {

		LOGD( format( "Índice do label identificado é %d.", predictedLabel ).c_str() );
		LOGD( format( "Tamanho dos labels %d.", labels.size() ).c_str() );

		sample = labels[predictedLabel];

		LOGD( format( "Sample identificado %s.", jenv->GetStringUTFChars( sample, NULL ) ).c_str() );
	}

	return sample;

}
