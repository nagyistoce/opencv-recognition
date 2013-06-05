package br.ufghomework.facerecognition.service;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.highgui.Highgui;
import org.opencv.samples.facedetect.DetectionBasedTracker;

import br.ufghomework.facerecognition.service.exception.ConvertionErrorException;

public class FaceRecognitionService {

	public static String recognize( Mat faceToRecognize, String csvFilePath ) {
		return nativeRecognize( faceToRecognize.nativeObj, csvFilePath );
		
	}
	
	public static void convertFullPhotoToROI( String fileUri, DetectionBasedTracker detector ) throws ConvertionErrorException {
		
		try {

			final MatOfRect roi = new MatOfRect();
			detector.detect( Highgui.imread( fileUri ), roi );
			
		} catch (Exception e) {
			
			throw new ConvertionErrorException( fileUri, e );
			
		}		
	}
	
	private static native String nativeRecognize( long faceToRecog, String csvFilePath );
	
}