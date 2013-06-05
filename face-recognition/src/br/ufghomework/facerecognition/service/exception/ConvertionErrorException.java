package br.ufghomework.facerecognition.service.exception;

public class ConvertionErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6302608983599558352L;
	
	public final static String ERROR_FAIL_TO_CONVERT = "Falha ao converter {1}."; 

	public ConvertionErrorException( String fileUri, Throwable cause ) {
		super( ERROR_FAIL_TO_CONVERT.replace( "{1}", fileUri ), cause );
	}
	
}