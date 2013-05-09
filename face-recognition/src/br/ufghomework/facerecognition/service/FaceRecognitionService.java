package br.ufghomework.facerecognition.service;

import org.opencv.core.Mat;

public class FaceRecognitionService {

	public static String recognize( Mat faceToRecognize ) {
		
		return nativeRecognize( faceToRecognize );
		
	}
	
	private static native String nativeRecognize( Mat faceToRecog );
	
}
