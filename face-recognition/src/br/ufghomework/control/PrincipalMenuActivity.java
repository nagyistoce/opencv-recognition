package br.ufghomework.control;

import org.opencv.samples.facedetect.FdActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import br.ufghomework.R;

public class PrincipalMenuActivity extends Activity {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal_menu);
		
		initComponents();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal_menu, menu);
		return true;
	}
	
	private void initComponents() {
		
		View faceDatabaseIcon = findViewById( R.id.imgbt_face_database );
		faceDatabaseIcon.setOnTouchListener( new OnTouchFaceDatabaseListener() );

		View faceReconitionIcon = findViewById( R.id.imgbt_face_recog );
		faceReconitionIcon.setOnTouchListener( new OnTouchFaceRecognitionListener() );
		
	}
	
	private class OnTouchFaceDatabaseListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			Intent faceDatabaseIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
			
			startActivityForResult(faceDatabaseIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			
			return true;
		}
		
	}
	
	private class OnTouchFaceRecognitionListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			Intent faceDatabaseIntent = new Intent( PrincipalMenuActivity.this, FdActivity.class );
			
			startActivity( faceDatabaseIntent );
			
			return true;
		}
		
	}

}
