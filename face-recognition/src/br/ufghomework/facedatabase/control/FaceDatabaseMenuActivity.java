package br.ufghomework.facedatabase.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.ufghomework.R;
import br.ufghomework.facedatabase.service.FileSystemFaceDatabaseService;
import br.ufghomework.model.Photo;
import br.ufghomework.model.Sample;
import br.ufghomework.model.exceptions.InvalidPhotoException;

public class FaceDatabaseMenuActivity extends Activity {

	public static final String TAG = "FaceDatabaseMenuActivity";
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final String START_SAMPLE_DESC = "Restam {1} fotos para o sample.";
	public static final String STATE_FIELD_IS_SAMPLE_NAME_ENABLED = "isSampleNameEnabled";
	public static final String STATE_FIELD_PHOTO_QUANTITY = "photoQuantity";
	public static final String STATE_FIELD_SAVED_SAMPLE = "lastSavedSampleUri";
	public static final String LOG_INFO_PHOTO_CAPTURE_PROBLEM = "Houve algum problema ao tirar a foto. Tente novamente.";
	public static final String LOG_INFO_SAMPLE_NAME_PROBLEM = "Nome inválido para sample. Tente novamente.";
	public static final String LOG_ERROR_INVALID_PHOTO = "A Uri da foto criada é inválido.";

	private Integer photoQuantity;
	private Sample sample;
	private Boolean isSampleNameEnabled;
	
	private EditText samplesNameView;
	private Button startSample;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face_database_menu);

		startSample = (Button) findViewById( R.id.bt_start_sample );
		startSample.setOnTouchListener( new OnTouchStartSampleListener() );
		
		samplesNameView = (EditText) findViewById( R.id.edtx_sample_name );
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.face_database_menu, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		
		if ( isSampleNameEnabled != null ) {
			
			samplesNameView.setEnabled( isSampleNameEnabled );
			
		}
		
		if ( photoQuantity != null ) {

			if ( photoQuantity > 0 ) {
				
				final Integer lasting = 10 - photoQuantity;
				
				startSample.setText( START_SAMPLE_DESC.replace( "{1}", lasting.toString() ) );
				
			} else if ( photoQuantity >= 10 ) {
				
				finish();
				
			}
			
		} else {
			
			photoQuantity = 0;
			
		}
		
		super.onStart();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		
		if ( savedInstanceState != null ) {
			
			sample = (Sample) savedInstanceState.getSerializable( STATE_FIELD_SAVED_SAMPLE ); 
				
			photoQuantity = Integer.valueOf( savedInstanceState.getByte( STATE_FIELD_PHOTO_QUANTITY ) );
			
			isSampleNameEnabled = savedInstanceState.getBoolean( STATE_FIELD_IS_SAMPLE_NAME_ENABLED );
			
		} else {
			
			photoQuantity = 0;
			isSampleNameEnabled = true;
			
		}
		
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if ( resultCode == Activity.RESULT_OK && requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ) {

			
			
		} else {
			
			photoQuantity--;
			
			Toast.makeText( this, LOG_INFO_PHOTO_CAPTURE_PROBLEM, Toast.LENGTH_SHORT ).show();
			
			Log.i( TAG, LOG_INFO_PHOTO_CAPTURE_PROBLEM );
			
		}
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		outState.putBoolean( STATE_FIELD_IS_SAMPLE_NAME_ENABLED, isSampleNameEnabled );
		outState.putByte( STATE_FIELD_PHOTO_QUANTITY, photoQuantity.byteValue() );
		
		if ( sample != null ) {
			
			outState.putSerializable( STATE_FIELD_SAVED_SAMPLE, sample );
			
		}
			
		super.onSaveInstanceState(outState);
		
	}
	
	@Override
	public void onBackPressed() {
		
		FileSystemFaceDatabaseService.deleteSample( sample );
		
		super.onBackPressed();
	}

	private void startPhotoActivity() {
		
		final Intent takePhotoSampleIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		takePhotoSampleIntent.putExtra( MediaStore.EXTRA_OUTPUT, 
				FileSystemFaceDatabaseService.getSampleFileOrCreateItUri( sample.retrieveLastPhoto(), sample.getSampleName() ) );
		
		startActivityForResult( takePhotoSampleIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE );
		
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
				
				final String sampleName = samplesNameView.getText().toString();
				
				if ( sampleName != null && !sampleName.equals( "" ) ) {
					
					if ( sample == null ) {
						
						sample = new Sample();
						
					}
					
					sample.setSampleName( sampleName );
					
					try {
						
						photoQuantity++;

						final Photo newPhoto = new Photo();
						
						newPhoto.setPhotoName( sample.getSampleName().concat( photoQuantity.toString() ) );
						
						sample.add( newPhoto );
						
					} catch (InvalidPhotoException e) {
						
						Toast.makeText( FaceDatabaseMenuActivity.this, LOG_ERROR_INVALID_PHOTO, Toast.LENGTH_LONG ).show();
						Log.e( TAG, LOG_ERROR_INVALID_PHOTO, e );
						
						return false;
						
					}
					
					isSampleNameEnabled = false;
					
					startPhotoActivity();
					
					return true;
					
				} else {
					
					Toast.makeText( FaceDatabaseMenuActivity.this, LOG_INFO_SAMPLE_NAME_PROBLEM, Toast.LENGTH_LONG ).show();
					return false;
				}
				
			} else return false;
		}
		
	}
	
}
