package br.ufghomework.facedatabase.control;

import org.opencv.android.OpenCVLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.ufghomework.R;
import br.ufghomework.exception.HomeWorkException;
import br.ufghomework.facedatabase.exceptions.InvalidCSVSampleContentException;
import br.ufghomework.facedatabase.service.FileSystemFaceDatabaseService;
import br.ufghomework.filesystem.exceptions.FileWriteProblemException;
import br.ufghomework.model.Photo;
import br.ufghomework.model.Sample;
import br.ufghomework.model.exceptions.InvalidPhotoException;
import br.ufghomework.model.exceptions.InvalidSampleNameException;

public class FaceDatabaseMenuActivity extends Activity {

	public static final String TAG = "br.u.f.c.FaceDatabaseMenuActivity";
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final String START_SAMPLE_DESC = "Restam {1} fotos para o sample.";
	public static final String STATE_FIELD_IS_SAMPLE_NAME_ENABLED = "isSampleNameEnabled";
	public static final String STATE_FIELD_PHOTO_QUANTITY = "photoQuantity";
	public static final String STATE_FIELD_SAVED_SAMPLE = "lastSavedSampleUri";
	public static final String LOG_INFO_PHOTO_CAPTURE_PROBLEM = "Houve algum problema ao tirar a foto. Tente novamente.";
	public static final String LOG_INFO_PHOTO_CAPTURE_CANCEL = "Captura cancelada. Tente novamente.";
	public static final String LOG_INFO_SAMPLE_COMPLETE = "Sample {1} completo";
	public static final String LOG_INFO_SAMPLE_NAME_PROBLEM = "Nome inválido para sample. Tente novamente.";
	public static final String LOG_ERROR_INVALID_PHOTO = "A Uri da foto criada é inválido.";
	public static final String LOG_ERROR_UNAVAILABLE_MAP_FILE = "O arquivo de mapeamento não está disponível.";
	public static final String LOG_ERROR_INVALID_SAMPLE = "O sample {1} é inválido.";
	public static final String LOG_ERROR_CALL_FOR_SUPPORT = "Erro interno, alguma correção no programa é necessária.";
	
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
		
		if ( isSampleNameEnabled == null ) {

			isSampleNameEnabled = true;
			
		}
		
		if ( photoQuantity == null ) {
			
			photoQuantity = 1;
			
		}
		OpenCVLoader.initDebug();
		System.loadLibrary( "detection_based_tracker" );
		
	}
	
	@Override
	protected void onResume() {
		
		samplesNameView.setEnabled( isSampleNameEnabled );
		
		if ( photoQuantity > 1 ) {
			
			final Integer lasting = 10 - sample.getSamplesPhotos().size();
			
			startSample.setText( START_SAMPLE_DESC.replace( "{1}", lasting.toString() ) );
			
		} else if ( photoQuantity >= 10 ) {
			
			finish();
			
		}
		
		super.onStart();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		
		if ( savedInstanceState != null ) {
			
			sample = (Sample) savedInstanceState.getSerializable( STATE_FIELD_SAVED_SAMPLE ); 
				
			photoQuantity = Integer.valueOf( savedInstanceState.getByte( STATE_FIELD_PHOTO_QUANTITY ) );
			
			isSampleNameEnabled = savedInstanceState.getBoolean( STATE_FIELD_IS_SAMPLE_NAME_ENABLED );
			
		}
		
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if ( resultCode == Activity.RESULT_OK && requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ) {

			try {
				
				isSampleNameEnabled = false;
				
				if ( photoQuantity >= 10 ) {
	
					Toast.makeText( this, LOG_INFO_SAMPLE_COMPLETE.replace( "{1}", sample.getSampleName() ), Toast.LENGTH_LONG ).show();
						
					FileSystemFaceDatabaseService.addNewSampleContent( sample );
						
					finish();
					
				}
				
				photoQuantity++;
			
			} catch (FileWriteProblemException e) {

				FileSystemFaceDatabaseService.deleteSample( sample );
				
				Toast.makeText( this, LOG_ERROR_UNAVAILABLE_MAP_FILE, Toast.LENGTH_LONG ).show();
				Log.e( TAG, LOG_ERROR_UNAVAILABLE_MAP_FILE, e );
				
				finish();
				
			} catch (InvalidCSVSampleContentException e) {

				FileSystemFaceDatabaseService.deleteSample( sample );
				
				Toast.makeText( this, LOG_ERROR_INVALID_SAMPLE.replace( "{1}", sample.getSampleName() ), Toast.LENGTH_LONG ).show();
				Log.e( TAG, LOG_ERROR_INVALID_SAMPLE.replace( "{1}", sample.getSampleName() ), e );
				
				finish();
				
			} catch (HomeWorkException e) {

				FileSystemFaceDatabaseService.deleteSample( sample );
				
				Toast.makeText( this, LOG_ERROR_CALL_FOR_SUPPORT, Toast.LENGTH_LONG ).show();
				Log.e( TAG, LOG_ERROR_CALL_FOR_SUPPORT, e );
				
				finish();
				
			} 
			
		} else if ( resultCode == Activity.RESULT_CANCELED ) {
			
			sample.removeLastPhoto();
			
			Toast.makeText( this, LOG_INFO_PHOTO_CAPTURE_CANCEL, Toast.LENGTH_LONG ).show();
			
			Log.i( TAG, LOG_INFO_PHOTO_CAPTURE_CANCEL );
			
		} else {
			
			sample.removeLastPhoto();
			
			Toast.makeText( this, LOG_INFO_PHOTO_CAPTURE_PROBLEM, Toast.LENGTH_LONG ).show();
			
			Log.i( TAG, LOG_INFO_PHOTO_CAPTURE_PROBLEM );
			
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
		
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
			
			final String sampleName = samplesNameView.getText().toString();
			
			try {
			
				if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
					
					
					if ( sample == null ) {
							
						sample = new Sample( sampleName );
						
					}
					
					sample.setSampleName( sampleName );
						
					final Photo newPhoto = new Photo();
					
					newPhoto.setPhotoName( sample.getSampleName().concat( photoQuantity.toString() ) );
					
					sample.add( newPhoto );
						
					startPhotoActivity();
					
					return true;
						
				} else return false;
			
			} catch (InvalidPhotoException e) {
				
				Toast.makeText( FaceDatabaseMenuActivity.this, e.getMessage(), Toast.LENGTH_LONG ).show();
				Log.w( TAG, LOG_ERROR_INVALID_PHOTO, e );
				
				return false;
				
			} catch (InvalidSampleNameException e) {
				
				Toast.makeText( FaceDatabaseMenuActivity.this, e.getMessage(), Toast.LENGTH_LONG ).show();
				Log.w( TAG, LOG_ERROR_INVALID_SAMPLE.replace( "{1}", sampleName ), e );
				
				return false;
				
			}
			
		}
		
	}
	
}