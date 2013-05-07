/**
 * 
 */
package br.ufghomework.filesystem.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Scanner;

import android.net.Uri;
import android.util.Log;
import br.ufghomework.filesystem.exceptions.FileNotCreatedException;
import br.ufghomework.filesystem.exceptions.FileWriteProblemException;

/**
 * @author andremello
 *
 */
public final class FileSystemService {

	public final static String TAG = "FileSystemService";
	public final static String MEDIA_STOREGEDIR_EXISTS = "O caminho {1} já existe.";
	public final static String FILE_ENCODING = "UTF-8";
	public final static String newLine = System.getProperty("line.separator");
	
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
		
		return path.substring( 0, path.length() - 1 );
		
	}
	
	public static Uri convertStringToUri( String path ) {
		
		return Uri.parse( path );
		
	}
	
	public static StringBuilder readTextFile( final Uri fileUri ) throws FileNotFoundException {
		
		final File csvFile = new File( fileUri.getPath() );
		final StringBuilder content = new StringBuilder();
		
		final Scanner scanner = new Scanner( new FileInputStream( csvFile ), FILE_ENCODING );
		
		while ( scanner.hasNextLine() ) {
			
			content.append( scanner.nextLine() ).append( newLine );
			
		}
		
		scanner.close();
		
		return content;
		
	}
	
	public static void writeToTextFile( final Uri fileUri, final StringBuilder content ) throws UnsupportedEncodingException, FileNotFoundException, FileWriteProblemException {
		
    	final Writer out = new OutputStreamWriter( new FileOutputStream( fileUri.getPath() ), FILE_ENCODING );
    	
		try {
			
			out.write( content.toString() );
			
		} catch (IOException e) {
			
			Log.e( TAG, "O arquivo {1} não pode ser escrito.".replace( "{1}", fileUri.getPath() ), e );
			
			throw new FileWriteProblemException( fileUri.getPath(), e );
			
		} finally {
			
			try {

				out.close();
				
			} catch (IOException e) {
				
				Log.e( TAG, "O arquivo {1} não pode ser fechado.".replace( "{1}", fileUri.getPath() ), e );
				
			}
			
		}
    	
	} 
	
	public static Uri convertStringToUri( final String... archivesNames ) {
		
		if ( archivesNames != null && archivesNames.length > 0 ) {
			
			return convertStringToUri( mountFilePath( archivesNames )  );
			
		} else return null;
		
	}
	
	public static void recursiveArchiveDelete( File archiveToDelete ) {
		
		if ( archiveToDelete.isDirectory() ) {

			for ( File child : archiveToDelete.listFiles() ) {
				
				recursiveArchiveDelete( child );
				
			}
			
			archiveToDelete.delete();
			
		} else {
			
			archiveToDelete.delete();
			
		}
			
	}
	
}
