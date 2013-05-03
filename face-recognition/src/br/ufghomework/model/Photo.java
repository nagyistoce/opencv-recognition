package br.ufghomework.model;

import java.io.Serializable;


public class Photo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3174039257897020360L;
	private String photoName;
	
	/**
	 * @return the photoUri
	 */
	public String getPhotoName() {
		return photoName;
	}

	/**
	 * @param photoUri the photoUri to set
	 */
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

}