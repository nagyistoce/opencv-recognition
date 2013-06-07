package br.ufghomework.menu.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import br.ufghomework.R;
import br.ufghomework.facedatabase.control.FaceDatabaseMenuActivity;
import br.ufghomework.facerecognition.controller.FaceRecogByPhotoActivity;

public class PrincipalMenuActivity extends Activity {
	
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

			if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
				
				Intent faceDatabaseIntent = new Intent( PrincipalMenuActivity.this, FaceDatabaseMenuActivity.class );
				
				startActivity( faceDatabaseIntent );
				
				return true;
				
			} else return false;
			
		}
		
	}
	
	private class OnTouchFaceRecognitionListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
			
				Intent faceRecognitionIntent = new Intent( PrincipalMenuActivity.this, FaceRecogByPhotoActivity.class );
				
				startActivity( faceRecognitionIntent );
				
				return true;
			
			} else return false;
			
		}
		
	}

}
