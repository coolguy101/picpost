package ca.ualberta.cs.picposter.model;

import java.util.Date;

import android.graphics.Bitmap;

/**
 * Represents a single Pic Post, which includes a picture, some text, and a timestamp.
 * @author zjullion
 */
public class PicPostModel {
	
	
	//private Bitmap picture;
	private String text;
	private Date timestamp;
	Bitmap picture;
	String code;
	public PicPostModel(String code,String text, Date timestamp) {
		this.code = code;
		this.text = text;
		this.timestamp = timestamp;
	}
	
	
	public Bitmap getPicture() {
		return this.picture;
	}
	
	
	public String getText() {
		return this.text;
	}
	
	
	public Date getTimestamp() {
		return this.timestamp;
	}
}