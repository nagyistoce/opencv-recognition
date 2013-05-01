package br.ufghomework.facedatabase.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import br.ufghomework.R;

public class FaceDatabaseMenuActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face_database_menu);
		
		initComponents();
		
	}
	
	private void initComponents() {
		
		View startSample = findViewById( R.id.bt_start_sample );
		startSample.setOnTouchListener( new OnTouchStartSampleListener() );
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.face_database_menu, menu);
		return true;
	}

	private class OnTouchStartSampleListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
				
				Intent takePhotoSampleIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
				
				startActivityForResult( takePhotoSampleIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE );
				
				return true;
				
			} else return false;
		}
		
	}
	
}
