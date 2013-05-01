/**
 * 
 */
package br.ufghomework.filesystem.exceptions;

/**
 * @author andremello
 *
 */
public class FileNotCreatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3344487769213249915L;

	public FileNotCreatedException() {
		
		super( "O arquivo não pode ser criado." );
		
	}
	
	public FileNotCreatedException( String archiveName ) {
		
		this( archiveName, null );
		
	}
	
	public FileNotCreatedException( String archiveName, Throwable cause ) {
		
		super( "O arquivo {1} não pode ser criado.".replace( "{1}", archiveName ) );
		
	}
	
}
