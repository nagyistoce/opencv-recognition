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
import br.ufghomework.filesystem.service.FileSystemService;
import br.ufghomework.model.Photo;
import br.ufghomework.model.Sample;
import br.ufghomework.model.exceptions.InvalidPhotoException;

public class FaceDatabaseMenuActivity extends Activity {

	public static final String TAG = "FaceDatabaseMenuActivity";
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final String STATE_FIELD_IS_SAMPLE_NAME_ENABLED = "isSampleNameEnabled";
	public static final String STATE_FIELD_PHOTO_QUANTITY = "photoQuantity";
	public static final String STATE_FIELD_SAVED_SAMPLE = "lastSavedSampleUri";
	public static final String SAMPLE_FILE_URI_DESC = "faceSamples";
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

		initState( savedInstanceState );
		initComponents( savedInstanceState );
		
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
	
	private void initComponents( Bundle savedInstanceState ) {
		
		startSample = (Button) findViewById( R.id.bt_start_sample );
		startSample.setOnTouchListener( new OnTouchStartSampleListener() );
		
		samplesNameView = (EditText) findViewById( R.id.edtx_sample_name );
		
		if ( savedInstanceState != null ) {
			
			samplesNameView.setEnabled( isSampleNameEnabled );
			
		}
			
	}
	
	private void initState( Bundle savedInstanceState ) {
		
		if ( savedInstanceState != null ) {
			
			sample = (Sample) savedInstanceState.getSerializable( STATE_FIELD_SAVED_SAMPLE ); 
				
			photoQuantity = Integer.valueOf( savedInstanceState.getByte( STATE_FIELD_PHOTO_QUANTITY ) );
			
			isSampleNameEnabled = savedInstanceState.getBoolean( STATE_FIELD_IS_SAMPLE_NAME_ENABLED );
			
		} else {
			
			photoQuantity = 0;
			isSampleNameEnabled = true;
			
		}
		
	}

	private void addNewPhoto( final String photoName, final Sample sample ) throws InvalidPhotoException {
		
		final Photo newPhoto = new Photo();
		
		newPhoto.setPhotoName( photoName );
		newPhoto.setPhotoDirectory( sample.getSampleDirectory() );
		newPhoto.setExtension( "jpg" );
		
		sample.add( newPhoto );
		
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
					sample.setSampleDirectory( FileSystemService.mountFilePath( SAMPLE_FILE_URI_DESC, sample.getSampleName() ) );
					
					try {
						
						photoQuantity++;

						addNewPhoto( sampleName.concat( photoQuantity.toString() ), sample );
						
					} catch (InvalidPhotoException e) {
						
						Toast.makeText( FaceDatabaseMenuActivity.this, LOG_ERROR_INVALID_PHOTO, Toast.LENGTH_SHORT ).show();
						Log.e( TAG, LOG_ERROR_INVALID_PHOTO, e );
						
						return false;
						
					}
					
					final Photo newPhoto = sample.getSamplesPhotos().get( sample.getSamplesPhotos().size() - 1 );
					
					isSampleNameEnabled = false;
					
					Intent takePhotoSampleIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
					takePhotoSampleIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileSystemFaceDatabaseService.getSampleFileOrCreateItUri( newPhoto ) );
					
					startActivityForResult( takePhotoSampleIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE );
					
					return true;
					
				} else {
					
					Toast.makeText( FaceDatabaseMenuActivity.this, LOG_INFO_SAMPLE_NAME_PROBLEM, Toast.LENGTH_SHORT ).show();
					return false;
				}
				
			} else return false;
		}
		
	}
	
}
