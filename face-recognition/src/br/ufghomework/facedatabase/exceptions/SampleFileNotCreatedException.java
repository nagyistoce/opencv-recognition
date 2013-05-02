package br.ufghomework.facedatabase.exceptions;

public class SampleFileNotCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3274981768728365201L;

	public SampleFileNotCreatedException() {
		
		super( "O arquivo para sample não pode ser criado." );
		
	}
	
	public SampleFileNotCreatedException( String samplesName ) {
		
		this( samplesName, null );
		
	}
	
	public SampleFileNotCreatedException( String samplesName, Throwable cause ) {
		
		super( "O arquivo {1} para sample não pode ser criado.".replace( "{1}", samplesName ), cause );
		
	}
	
}
