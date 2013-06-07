package br.ufghomework.facerecognition.controller;

import java.io.File;

import org.opencv.android.OpenCVLoader;
import org.opencv.highgui.Highgui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;
import br.ufghomework.R;
import br.ufghomework.facedatabase.control.FaceDatabaseMenuActivity;
import br.ufghomework.facedatabase.service.FileSystemFaceDatabaseService;
import br.ufghomework.facerecognition.service.FaceRecognitionService;
import br.ufghomework.filesystem.exceptions.FileNotCreatedException;
import br.ufghomework.filesystem.service.FileSystemService;

public class FaceRecogByPhotoActivity extends Activity {

	public final static String TAG = "UFGHomeWork/FaceRecogByPhotoActivity";
	
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final String RECOG_IMAGE_NAME = "temp_face_to_recog";
	public static final String RECOG_IMAGE_DIRECTORY_NAME = "temp_recog";
	public static final String RECOG_IMAGE_DIRECTORY = FileSystemService.mountFilePath( Environment
			.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ).getAbsolutePath() );
	
	private static final String SAMPLE_FOUND = "O sample {1} foi encontrado";
	
	private Button startFaceRecog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face_recog_by_photo);
		
		startFaceRecog = (Button) findViewById( R.id.bt_start_face_recog );

		OpenCVLoader.initDebug();
		System.loadLibrary( "detection_based_tracker" );
		
		startFaceRecog.setOnTouchListener( new OnTouchStartFaceRecogListener() );
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if ( resultCode == Activity.RESULT_OK && requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ) {
			
			File fileToDelete = null;
			
			try {
				
				final File absolutPath = new File( RECOG_IMAGE_DIRECTORY, RECOG_IMAGE_DIRECTORY_NAME );
				
				fileToDelete = FileSystemService.getFilePathOrCreateIt( absolutPath, 
						RECOG_IMAGE_NAME, FileSystemFaceDatabaseService.PHOTO_FILE_EXTENSION );
				
				final String recognizedSample = FaceRecognitionService.recognize( Highgui.imread(fileToDelete.getPath(), Highgui.CV_LOAD_IMAGE_GRAYSCALE), 
						"/storage/sdcard0/Pictures/faceSamples/facesMap.txt" );
			
				if ( !"".equals( recognizedSample )  ) {
					
					Toast.makeText( this, SAMPLE_FOUND.replace( "{1}", recognizedSample ), Toast.LENGTH_LONG ).show();
					
				}
			
			} catch (Exception e) {

				//talve não seja a melhor implementação, mas nos poupará código repetido
				Toast.makeText( this, FaceDatabaseMenuActivity.LOG_ERROR_CALL_FOR_SUPPORT, Toast.LENGTH_LONG ).show();
				Log.e( TAG, FaceDatabaseMenuActivity.LOG_ERROR_CALL_FOR_SUPPORT, e );
				
				finish();
				
			} 
			
			FileSystemService.recursiveArchiveDelete( fileToDelete );
			
		} else if ( resultCode == Activity.RESULT_CANCELED ) {

			//talve não seja a melhor implementação, mas nos poupará código repetido
			Toast.makeText( this, FaceDatabaseMenuActivity.LOG_INFO_PHOTO_CAPTURE_CANCEL, Toast.LENGTH_LONG ).show();
			Log.i( TAG, FaceDatabaseMenuActivity.LOG_INFO_PHOTO_CAPTURE_CANCEL );
			
		} else {

			//talve não seja a melhor implementação, mas nos poupará código repetido
			Toast.makeText( this, FaceDatabaseMenuActivity.LOG_INFO_PHOTO_CAPTURE_PROBLEM, Toast.LENGTH_LONG ).show();
			Log.i( TAG, FaceDatabaseMenuActivity.LOG_INFO_PHOTO_CAPTURE_PROBLEM );
			
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void startPhotoActivity() throws FileNotCreatedException {
		
		final Intent takePhotoSampleIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		final File photoToRecog = new File( RECOG_IMAGE_DIRECTORY, RECOG_IMAGE_DIRECTORY_NAME );
		
		takePhotoSampleIntent.putExtra( MediaStore.EXTRA_OUTPUT, FileSystemService.getFilePathOrCreateItURI( photoToRecog, RECOG_IMAGE_NAME, 
				FileSystemFaceDatabaseService.PHOTO_FILE_EXTENSION ) );
		
		startActivityForResult( takePhotoSampleIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE );
		
	}
	
	private final class OnTouchStartFaceRecogListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
					
				try {
					startPhotoActivity();
				} catch (FileNotCreatedException e) {
					
					e.printStackTrace();
					
				}
				
				return true;
					
			} else return false;
			
		}

		
	}
	
}