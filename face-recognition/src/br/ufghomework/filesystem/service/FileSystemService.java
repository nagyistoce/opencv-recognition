/**
 * 
 */
package br.ufghomework.filesystem.service;

import java.io.File;

import android.net.Uri;
import android.util.Log;
import br.ufghomework.filesystem.exceptions.FileNotCreatedException;

/**
 * @author andremello
 *
 */
public final class FileSystemService {

	public final static String TAG = "FileSystemService";
	public final static String MEDIA_STOREGEDIR_EXISTS = "O caminho {1} já existe.";
	
	private FileSystemService() {}
	
	public static File getFilePathOrCreateIt( final File mediaStorageDir, final String filesName, final String extension ) throws FileNotCreatedException {
		
		if ( !mediaStorageDir.exists() ) {
			
			Log.i( TAG, MEDIA_STOREGEDIR_EXISTS.replace( "{1}", mediaStorageDir.getPath() ) );
			
			if ( !mediaStorageDir.mkdirs() ) {
				
				throw new FileNotCreatedException( mediaStorageDir.getPath() );
				
			}
			
		}
		
		if ( filesName != null ) {
			
			File mediaFile = null;
			
			if ( extension != null ) {
				
				mediaFile = new File( mediaStorageDir, filesName + extension );
				
			} else {
				
				mediaFile = new File( mediaStorageDir, filesName );
				
			}
			
			return mediaFile;
			
		} else return mediaStorageDir;
		
	}
	
	public static Uri getFilePathOrCreateItURI( final File relativePath, final String filesName, final String extension ) throws FileNotCreatedException {
		
		final File mediaFile = getFilePathOrCreateIt( relativePath, filesName, extension );
		
		return Uri.fromFile( mediaFile );
		
	}
	
	public static String mountFilePath( String... archivesNames ) {
		
		final StringBuilder path = new StringBuilder();
		
		for( String archiveName : archivesNames ) {
			
			path.append( archiveName ).append( File.separator ); 
			
		}
		
		return path.toString();
		
	}
	
}
