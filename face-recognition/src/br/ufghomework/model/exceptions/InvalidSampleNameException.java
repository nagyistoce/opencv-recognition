/**
 * 
 */
package br.ufghomework.model.exceptions;

/**
 * @author almde89
 *
 */
public class InvalidSampleNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1192727241307242203L;
	
	public InvalidSampleNameException( String sampleName ) {
		
		super( "O sample {1} contém nome inválido.".replace( "{1}", sampleName ) );
		
	}

}
