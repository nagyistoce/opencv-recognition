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

static void read_csv(JNIEnv * jenv, const jstring& filename, vector<Mat>& images, vector<jstring>& labels, char separator = ';') {

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

    while (getline(file, line)) {
        stringstream liness(line);
        getline(liness, path, separator);
        getline(liness, classlabel);
        if(!path.empty() && !classlabel.empty()) {
            images.push_back(imread(path, 0));
            labels.push_back( jenv->NewStringUTF( classlabel.c_str() ) );
        }
    }
}

JNIEXPORT jstring JNICALL Java_br_ufghomework_facerecognition_service_FaceRecognitionService_nativeRecognize
(JNIEnv * jenv, jclass, jlong faceToRecog, jstring csvFilePathDsc)
{

	jstring sample = jenv->NewStringUTF( "andre" );

	vector<Mat> images;
	vector<jstring> labels;

	try {

		read_csv( jenv, csvFilePathDsc, images, labels );

	} catch (cv::Exception& e) {

		LOGD("Erro ao ler o arquivo CSV.", e.what());

		//FIXME colocar depois a classe de excecao

		jclass je = jenv->FindClass("java/lang/Exception");

		if(!je) {

			je = jenv->FindClass("java/lang/Exception");

		}

		jenv->ThrowNew(je, e.what());

	}
/*
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
	model->train(images, labels);
	// The following line predicts the label of a given
	// test image:
	//FIXME passar a matriz capturada para avaliacao
	int predictedLabel = model->predict(NULL);

	// Here is how to get the eigenvalues of this Eigenfaces model:
	Mat eigenvalues = model->getMat("eigenvalues");
	// And we can do the same to display the Eigenvectors (read Eigenfaces):
	Mat W = model->getMat("eigenvectors");
	// Get the sample mean from the training data
	Mat mean = model->getMat("mean");

*/
	return sample;

}
