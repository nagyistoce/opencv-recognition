package br.ufghomework.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.ufghomework.model.exceptions.InvalidPhotoException;

public class Sample implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2055627285708262720L;
	private String sampleName;
	private List<Photo> samplesPhotos;
	
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
		
		if ( photoToAdd != null && photoToAdd.getPhotoName() != null && !photoToAdd.getPhotoName().equals( "" ) ) {
			
			this.samplesPhotos.add( photoToAdd );
			
		} else throw new InvalidPhotoException();
		
	}
	
	public Photo retrieveLastPhoto() {
		
		if ( samplesPhotos != null && samplesPhotos.size() > 0 ) {
			
			return samplesPhotos.get( samplesPhotos.size() - 1 );
			
		} else return null;
		
	}
	
}
