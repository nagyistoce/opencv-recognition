package br.ufghomework.model.exceptions;

public class InvalidSampleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4362762337267530982L;
	
	public InvalidSampleException( String sampleName ) {
		
		this( sampleName, null );
		
	}
	
	public InvalidSampleException( String sampleName, Throwable cause ) {
		
		super( "O sample {1} é inválido.".replace( "{1}", sampleName ), cause );
		
	}

}
