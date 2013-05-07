/**
 * 
 */
package br.ufghomework.filesystem.exceptions;

/**
 * @author almde89
 *
 */
public class FileWriteProblemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7791287616361529728L;
	
	public FileWriteProblemException( String filePath, Throwable cause ) {

		super( "O arquivo {1}".replace( "{1}", filePath ), cause );
		
	}
	
	public FileWriteProblemException() {

		this( "", null );
	
	}

}
