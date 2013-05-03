/**
 * 
 */
package br.ufghomework.facedatabase.service;

import java.io.File;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import br.ufghomework.facedatabase.exceptions.SampleFileNotCreatedException;
import br.ufghomework.filesystem.exceptions.FileNotCreatedException;
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
	public static String ABSOLUTE_DIRECTORY = SAMPLES_DIRECTORY;
	
	private FileSystemFaceDatabaseService() {}
	
	public static Uri getSampleFileOrCreateItUri( final Photo newPhoto, final String sampleName ) {
		
		final String photoName = newPhoto.getPhotoName();
		final String fileExtension = ".jpg";
		
		try {
			
			return FileSystemService.getFilePathOrCreateItURI( new File( ABSOLUTE_DIRECTORY.concat( File.separator ).concat( sampleName ) ), 
					photoName, 
					fileExtension );
			
		} catch (FileNotCreatedException e) {
			
			Log.i( TAG, "O arquivo {1} não pode ser craido no diretório relativo. Esse será criado no diretóri padrão para imagens."
					.replace( "{1}", photoName ), e );
			
			try {
				
				ABSOLUTE_DIRECTORY = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES )
						.getAbsolutePath()
						.concat( File.separator )
						.concat( SAMPLES_DIRECTORY );

				final File mediaDir = new File( ABSOLUTE_DIRECTORY, sampleName );
				
				return FileSystemService.getFilePathOrCreateItURI( mediaDir, photoName, fileExtension ); 
				
			} catch (FileNotCreatedException e2) {
				
				Log.e( TAG, "O arquivo não pode ser criado nem mesmo no diretório padrão para imagens. Operação abortada.", e2 );
				
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
	
}
