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

/**
 * @author andremello
 *
 */
public abstract class FileSystemFaceDatabaseService {

	public final static String TAG = "FileSystemFaceDatabaseService";
	
	private FileSystemFaceDatabaseService() {}
	
	public static Uri getSampleFileOrCreateItUri( Photo newPhoto ) {
		
		final String dirName = newPhoto.getPhotoDirectory();
		final String samplesName = newPhoto.getPhotoName();
		final String fileExtension = ".".concat( newPhoto.getExtension() );
		
		try {
			
			return FileSystemService.getFilePathOrCreateItURI( new File( dirName ), samplesName, fileExtension );
			
		} catch (FileNotCreatedException e) {
			
			Log.i( TAG, "O arquivo {1} não pode ser craido no diretório relativo. Esse será criado no diretóri padrão para imagens."
					.replace( "{1}", samplesName ), e );
			
			try {
				
				final File mediaDir = new File( Environment.getExternalStoragePublicDirectory(
			              Environment.DIRECTORY_PICTURES ), dirName );
				
				return FileSystemService.getFilePathOrCreateItURI( mediaDir, samplesName, fileExtension ); 
				
			} catch (FileNotCreatedException e2) {
				
				Log.e( TAG, "O arquivo não pode ser criado nem mesmo no diretório padrão para imagens. Operação abortada.", e2 );
				
				throw new SampleFileNotCreatedException( samplesName, e2 );
				
			}
			
		}
		
	}
	
}
