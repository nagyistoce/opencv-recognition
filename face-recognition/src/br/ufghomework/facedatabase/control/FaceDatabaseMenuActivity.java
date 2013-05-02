package br.ufghomework.facedatabase.control;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
	public static final String STATE_FIELD_SAMPLE_QUANTITY = "sampleQuantity";
	public static final String STATE_FIELD_LAST_SAVED_SAMPLE_URI = "lastSavedSampleUri";
	public static final String SAMPLE_FILE_URI_DESC = "faceSamples";
	public static final String LOG_INFO_PHOTO_CAPTURE_PROBLEM = "Houve algum problema ao tirar a foto. Tente novamente.";
	public static final String LOG_ERROR_INVALID_PHOTO = "A Uri da foto criada é inválido.";

	private Integer sampleQuantity;
	private Sample sample;
	private Boolean isSampleNameEnabled;
	
	private EditText samplesNameView;
	private Button startSample;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face_database_menu);
		
		initComponents( savedInstanceState );
		initState( savedInstanceState );
		
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
			
			sampleQuantity--;
			
			Toast.makeText( this, LOG_INFO_PHOTO_CAPTURE_PROBLEM, Toast.LENGTH_SHORT ).show();
			
			Log.i( TAG, LOG_INFO_PHOTO_CAPTURE_PROBLEM );
			
		}
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		outState.putBoolean( STATE_FIELD_IS_SAMPLE_NAME_ENABLED, isSampleNameEnabled );
		outState.putByte( STATE_FIELD_SAMPLE_QUANTITY, sampleQuantity.byteValue() );
		outState.putParcelable( STATE_FIELD_LAST_SAVED_SAMPLE_URI, lastSavedSampleUri );
		
		super.onSaveInstanceState(outState);
		
	}
	
	private void initComponents( Bundle savedInstanceState ) {
		
		startSample = (Button) findViewById( R.id.bt_start_sample );
		startSample.setOnTouchListener( new OnTouchStartSampleListener() );
		
		samplesNameView = (EditText) findViewById( R.id.edtx_sample_name );
		samplesNameView.setEnabled( savedInstanceState.getBoolean( STATE_FIELD_IS_SAMPLE_NAME_ENABLED ) );
			
	}
	
	private void initState( Bundle savedInstanceState ) {
		
		if ( savedInstanceState.getByte( STATE_FIELD_SAMPLE_QUANTITY ) > 0 ) {
			
			sampleQuantity = Integer.valueOf( savedInstanceState.getByte( STATE_FIELD_SAMPLE_QUANTITY ) ); 
			
		}
		
		if ( savedInstanceState.getParcelable( STATE_FIELD_LAST_SAVED_SAMPLE_URI ) != null ) {
			
			lastSavedSampleUri = savedInstanceState.getParcelable( STATE_FIELD_LAST_SAVED_SAMPLE_URI ); 
			
		}
		
		samplesName = samplesNameView.getText().toString();
		
	}

	private Photo configureNewPhotoUri( final String photoName ) {
		
		final Photo newPhoto = new Photo();
		
		sampleQuantity++;
		
		newPhoto.setPhotoUri( FileSystemFaceDatabaseService
				.getSampleFileOrCreateItUri( FileSystemService.mountFilePath( SAMPLE_FILE_URI_DESC, photoName ), 
						photoName.concat( sampleQuantity.toString() ) ) );
		
		return newPhoto; 
		
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
					
					sample = new Sample();
					sample.setSampleName( sampleName );
					
					try {
						
						sample.add( configureNewPhotoUri( sampleName ) );
						
					} catch (InvalidPhotoException e) {
						
						Toast.makeText( FaceDatabaseMenuActivity.this, LOG_ERROR_INVALID_PHOTO, Toast.LENGTH_SHORT ).show();
						Log.e( TAG, LOG_ERROR_INVALID_PHOTO, e );
						
						return false;
						
					}
					
					isSampleNameEnabled = false;
					
					Intent takePhotoSampleIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
					takePhotoSampleIntent.putExtra(MediaStore.EXTRA_OUTPUT, sample.getSamplesPhotos().get( sample.getSamplesPhotos().size() - 1 ).getPhotoUri() );
					
					startActivityForResult( takePhotoSampleIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE );
					
					return true;
					
				} else return false;
				
			} else return false;
		}
		
	}
	
}
