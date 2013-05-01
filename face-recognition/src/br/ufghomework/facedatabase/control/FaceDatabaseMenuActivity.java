package br.ufghomework.facedatabase.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import br.ufghomework.R;
import br.ufghomework.facedatabase.service.FileSystemFaceDatabaseService;

public class FaceDatabaseMenuActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face_database_menu);
		
		initComponents();
		
	}
	
	private void initComponents() {
		
		final View startSample = findViewById( R.id.bt_start_sample );
		startSample.setOnTouchListener( new OnTouchStartSampleListener() );
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.face_database_menu, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if ( resultCode == Activity.RESULT_OK && requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ) {
			
			data.getData();
			
		}
		
	}

	/**
	 * Padrão observador para comunicação das alterações dos estados da visão. Toda vez que um evento de toque é realizado, o método onTouch dessa classe é invocado.
	 * 
	 * @author andremello
	 *
	 */
	private class OnTouchStartSampleListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
				
				final EditText samplesNameView = (EditText) findViewById( R.id.edtx_sample_name );
				final String samplesName = samplesNameView.getText().toString(); 
				
				if ( samplesName != null ) {
					
					Intent takePhotoSampleIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
					takePhotoSampleIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileSystemFaceDatabaseService
							.getSampleFileOrCreateItUri( "faceSamples", samplesName ) );
					
					startActivityForResult( takePhotoSampleIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE );
					
				}
				
				return true;
				
			} else return false;
		}
		
	}
	
}
