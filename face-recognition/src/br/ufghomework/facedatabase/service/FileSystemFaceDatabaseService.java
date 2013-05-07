/**
 * 
 */
package br.ufghomework.facedatabase.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import br.ufghomework.facedatabase.exceptions.SampleFileNotCreatedException;
import br.ufghomework.filesystem.exceptions.FileNotCreatedException;
import br.ufghomework.filesystem.exceptions.FileWriteProblemException;
import br.ufghomework.filesystem.service.FileSystemService;
import br.ufghomework.model.Photo;
import br.ufghomework.model.Sample;

/**
 * @author andremello
 *
 */
public abstract class FileSystemFaceDatabaseService {

	public final static String TAG = "FileSystemFaceDatabaseService";
	public final static String SAMPLES_DIRECTORY = "faceSamples/";
	public final static String FACES_MAP_FILE_NAME = "facesMap";
	public final static String LOG_INFO_RELATIVE_CREATION_PROBLEM = "O arquivo {1} não pode ser criado no diretório relativo. Esse será criado no diretório padrão para imagens.";
	public final static String LOG_ERROR_DEFAULT_CREATION_PROBLEM = "O arquivo não pode ser criado nem mesmo no diretório padrão para imagens. Operação abortada.";
	public final static String LOG_ERROR_FILE_NOT_FOUND = "Arquivo sem permissão de escrita ou provável problema de concorrência.";
	
	public final static String photoFileExtension = ".jpg";
	
	private FileSystemFaceDatabaseService() {}
	
	public static Uri getSampleFileOrCreateItUri( final Photo newPhoto, final String sampleName ) {
		
		final String photoName = newPhoto.getPhotoName();
		String absoluteDirectory = SAMPLES_DIRECTORY;
		
		try {
			
			return FileSystemService.getFilePathOrCreateItURI( new File( absoluteDirectory.concat( File.separator ).concat( sampleName ) ), 
					photoName, 
					photoFileExtension );
			
		} catch (FileNotCreatedException e) {
			
			Log.i( TAG, LOG_INFO_RELATIVE_CREATION_PROBLEM.replace( "{1}", photoName ), e );
			
			try {
				
				absoluteDirectory = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES )
						.getAbsolutePath()
						.concat( File.separator )
						.concat( SAMPLES_DIRECTORY );

				final File mediaDir = new File( absoluteDirectory, sampleName );
				
				return FileSystemService.getFilePathOrCreateItURI( mediaDir, photoName, photoFileExtension ); 
				
			} catch (FileNotCreatedException e2) {
				
				Log.e( TAG, LOG_ERROR_DEFAULT_CREATION_PROBLEM, e2 );
				
				throw new SampleFileNotCreatedException( photoName, e2 );
				
			}
			
		}
		
	}
	
	public static void deleteSample( Sample sampleToDelete ) {
		
		if ( sampleToDelete != null ) {
		
			final String absolutPath = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES )
					.getAbsolutePath()
					.concat( File.separator )
					.concat( SAMPLES_DIRECTORY );
			final File fileToDelete = new File( absolutPath, sampleToDelete.getSampleName() ); 
			
			FileSystemService.recursiveArchiveDelete( fileToDelete );
		
		}
		
	}
	
	public static Uri getCSVMapFileOrCreateItUri() {
		
		final String csvFileExtension = ".txt";
		String absoluteDirectory = SAMPLES_DIRECTORY;
		
		try {
			
			return FileSystemService.getFilePathOrCreateItURI( new File( absoluteDirectory.concat( File.separator ) ), 
					FACES_MAP_FILE_NAME, 
					csvFileExtension );
			
		} catch (FileNotCreatedException e) {
			
			Log.i( TAG, LOG_INFO_RELATIVE_CREATION_PROBLEM.replace( "{1}", FACES_MAP_FILE_NAME ), e );
			
			try {
				
				absoluteDirectory = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES )
						.getAbsolutePath()
						.concat( File.separator )
						.concat( SAMPLES_DIRECTORY );

				final File mediaDir = new File( absoluteDirectory );
				
				return FileSystemService.getFilePathOrCreateItURI( mediaDir, FACES_MAP_FILE_NAME, csvFileExtension ); 
				
			} catch (FileNotCreatedException e2) {
				
				Log.e( TAG, LOG_ERROR_DEFAULT_CREATION_PROBLEM, e2 );
				
				throw new SampleFileNotCreatedException( FACES_MAP_FILE_NAME, e2 );
				
			}
			
		}
		
	}
	
	public static Boolean writeNewSampleLine( final Uri csvFileUri, final Sample sample ) {
		
		final File csvFile = new File( csvFileUri.getPath() );
		final Photo lastSavedPhoto = sample.retrieveLastPhoto();
		final String photoPath = FileSystemService.mountFilePath( csvFile.getParentFile().getAbsolutePath(), sample.getSampleName(), lastSavedPhoto.getPhotoName().concat( photoFileExtension ) );
		final String photoLable = sample.getSampleName();
	
		StringBuilder content = null;
		
		try {
			
			content = FileSystemService.readTextFile( csvFileUri );
			
		} catch (FileNotFoundException e) {
			
			Log.e( TAG, LOG_ERROR_FILE_NOT_FOUND, e );
			
			return false;
			
		}
		
		if ( content != null ) {
			
			content.append( photoPath );
			content.append( ";" );
			content.append( photoLable ).append( FileSystemService.newLine );
			
			try {
				
				FileSystemService.writeToTextFile( csvFileUri, content );
				
			} catch (UnsupportedEncodingException e) {
				
				Log.e( TAG, "O encoding não é suportado pelo sistema.", e );
				
				return false;
				
			} catch (FileNotFoundException e) {
				
				Log.e( TAG, "Arquivo de mapeamento foi deletado provavelmente ou o método de criação não foi bem sucedido.", e );
				
				return false;
				
			} catch (FileWriteProblemException e) {
				
				Log.e( TAG, LOG_ERROR_FILE_NOT_FOUND, e );
				
				return false;
				
			}
			
			return true;
			
		} else return false;
		
	}
	
}
