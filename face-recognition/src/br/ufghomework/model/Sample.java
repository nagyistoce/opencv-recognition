package br.ufghomework.model;

import java.util.ArrayList;
import java.util.List;

import br.ufghomework.model.exceptions.InvalidPhotoException;

public class Sample {

	private String sampleDirectory;
	private String sampleName;
	private List<Photo> samplesPhotos;
	
	/**
	 * @return the sampleDirectory
	 */
	public String getSampleDirectory() {
		return sampleDirectory;
	}

	/**
	 * @return the sampleName
	 */
	public String getSampleName() {
		return sampleName;
	}

	/**
	 * @param sampleName the sampleName to set
	 */
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	/**
	 * @return the samplesPhotos
	 */
	public List<Photo> getSamplesPhotos() {
		return samplesPhotos;
	}
	
	/**
	 * @param samplesPhotos the samplesPhotos to set
	 * @throws InvalidPhotoException 
	 */
	public void add( Photo photoToAdd ) throws InvalidPhotoException {
		
		if ( samplesPhotos == null ) {
			
			samplesPhotos = new ArrayList<Photo>();
			
		}
		
		if ( photoToAdd != null && photoToAdd.getPhotoUri() != null && !photoToAdd.getPhotoUri().equals( "" ) ) {
			
			this.samplesPhotos.add( photoToAdd );
			
		} else throw new InvalidPhotoException();
		
	}
	
}
