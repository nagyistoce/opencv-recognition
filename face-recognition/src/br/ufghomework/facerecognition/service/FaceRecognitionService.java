package br.ufghomework.facerecognition.service;

import org.opencv.core.Mat;

public class FaceRecognitionService {

	public static String recognize( Mat faceToRecognize, String csvFilePath ) {
		
		return nativeRecognize( faceToRecognize.nativeObj, csvFilePath );
		
	}
	
	private static native String nativeRecognize( long faceToRecog, String csvFilePath );
	
}