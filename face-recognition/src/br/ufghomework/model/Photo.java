package br.ufghomework.model;

import java.io.Serializable;


public class Photo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3174039257897020360L;
	private String photoName;
	private String photoDirectory;
	private String extension;
	
	/**
	 * @return the photoUri
	 */
	public String getPhotoName() {
		return new String ( photoName );
	}

	/**
	 * @param photoUri the photoUri to set
	 */
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	/**
	 * @return the photoDirectory
	 */
	public String getPhotoDirectory() {
		return new String( photoDirectory );
	}

	/**
	 * @param photoDirectory the photoDirectory to set
	 */
	public void setPhotoDirectory(String photoDirectory) {
		this.photoDirectory = photoDirectory;
		
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return new String( extension );
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getNameWithExtension() {
		
		return photoName.concat( "." ).concat( extension );
		
	}
	
}
