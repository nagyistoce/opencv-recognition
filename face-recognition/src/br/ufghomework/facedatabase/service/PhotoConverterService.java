/**
 * 
 */
package br.ufghomework.facedatabase.service;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.samples.facedetect.DetectionBasedTracker;

import br.ufghomework.facedatabase.exceptions.CannotConvertImageFile;

/**
 * @author almde89
 *
 */
public class PhotoConverterService {
	
	public static void convertToCroppedPhotoFromFilePath( File photoFile, File cascadeFile ) throws CannotConvertImageFile {

		final Mat grayMap = Highgui.imread( photoFile.getAbsolutePath(), Highgui.CV_LOAD_IMAGE_GRAYSCALE );
		
		if ( grayMap == null ) {
			
			final MatOfRect facesDelimitation = new MatOfRect();
			final DetectionBasedTracker  mNativeDetector = new DetectionBasedTracker( cascadeFile.getAbsolutePath(), 0 );
			mNativeDetector.detect( grayMap, facesDelimitation );
			
			final Rect[] faces = facesDelimitation.toArray();
			
			if ( faces.length > 0 ) {
				
				final Mat croppedPhoto = convertToCroppedPhotoFromFilePath( faces[0], grayMap );
				Highgui.imwrite( photoFile.getAbsolutePath(), croppedPhoto );
				
			}
			
		} else {
			
			throw new CannotConvertImageFile( photoFile.getAbsolutePath() );
			
		}
		
	}
	
	private static Mat convertToCroppedPhotoFromFilePath( Rect face, Mat matToCrop ) {
		
		Mat croppedMat = new Mat( matToCrop, face ).clone();
		
		if ( croppedMat.isContinuous() ) {
			
			return croppedMat;
			
		} else  {
			
			return null;
			
		}
		
	}
	
}
