/**
 * 
 */
package br.ufghomework.model.exceptions;

/**
 * @author almde89
 *
 */
public class InvalidPhotoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5060010375757652700L;

	public InvalidPhotoException() {
	
		super( "A photo adicionado é inválida. Provavelmente tem conteúdo vazio é Null." );
		
	}
	
}
