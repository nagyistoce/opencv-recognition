package br.ufghomework.facedatabase.exceptions;

public class InvalidCSVSampleContentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7115216738392718296L;

	public InvalidCSVSampleContentException() {

		super( "O conteúdo de mapeamento não dever ser nulo nem vazio." );
		
	}
	
}
