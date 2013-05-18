/**
 * 
 */
package br.ufghomework.facedatabase.exceptions;

/**
 * @author almde89
 *
 */
public class CannotConvertImageFile extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6150442823979170173L;
	
	public CannotConvertImageFile( String filePath ) {
		
		super( "O arquivo {1} não pode ser convertido. Arquivo inexistente ou não há permissão.".replace( "{1}", filePath ) );
		
	}

}
