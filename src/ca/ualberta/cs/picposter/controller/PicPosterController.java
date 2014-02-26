package ca.ualberta.cs.picposter.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import ca.ualberta.cs.picposter.PicPosterActivity;
import ca.ualberta.cs.picposter.R;
import ca.ualberta.cs.picposter.model.PicPostModel;
import ca.ualberta.cs.picposter.model.PicPosterModelList;

import com.google.gson.Gson;

/**
 * Controller for PicPost that scales Bitmaps, checks Text, etc.
 * @author zjullion
 */
public class PicPosterController {


	public static final int MAX_BITMAP_DIMENSIONS = 50;
	public static final int MAX_TEXT_LENGTH = 100;
	
	
	private PicPosterModelList model;
	private PicPosterActivity activity;
	
	
	public PicPosterController(PicPosterModelList model, PicPosterActivity activity) {
		this.model = model;
		this.activity = activity;
	}
	
	
	/**
	 * Adds the new pic post to the model, adding a default pic and text if none is assigned, and 
	 * scaling the pic and shortening text as necessary.
	 * @param pic the Bitmap to be displayed in the post
	 * @param text the String to be displayed in the post
	 */
	public void addPicPost(Bitmap pic, String text) {
		
		//Assign a default pic if there is none:
		if (pic == null)
			pic = BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.no_img);
		
		//Scale the pic if it is too large:
		if (pic.getWidth() > MAX_BITMAP_DIMENSIONS || pic.getHeight() > MAX_BITMAP_DIMENSIONS) {
			double scalingFactor = pic.getWidth() * 1.0 / MAX_BITMAP_DIMENSIONS;
			if (pic.getHeight() > pic.getWidth())
				scalingFactor = pic.getHeight() * 1.0 / MAX_BITMAP_DIMENSIONS;
			
			int newWidth = (int)Math.round(pic.getWidth() / scalingFactor);
			int newHeight = (int)Math.round(pic.getHeight() / scalingFactor);
			
			pic = Bitmap.createScaledBitmap(pic, newWidth, newHeight, false);
		
		}
		
		//Assign default text if there is none:
		if (text == null || text.length() == 0)
			text = this.activity.getResources().getString(R.string.no_pic_text);
		
		//Shorten the text if it is too long:
		if (text.length() > MAX_TEXT_LENGTH) {
			text = text.substring(0, MAX_TEXT_LENGTH);
		}
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
		pic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream .toByteArray();

		

		String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
		
		new AsyncTask<Void, Void, Void>()
		{
            PicPostModel model = new PicPostModel(code,"asfal", new Date());
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost request = new HttpPost("http://cmput301.softwareprocess.es:8080/testing/yazhou/");
                Gson gson = new Gson();
				String asfa = gson.toJson(model);
			    StringEntity entity;
				
			    
			    try {
			    	entity = new StringEntity(asfa);
			    	request.setEntity(entity);
					HttpResponse response = httpClient.execute(request);
					Log.w("hehe", response.getStatusLine().toString());
					BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String output = reader.readLine();
					while (output != null)
					{
						output=reader.readLine();
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
				
			
		}.execute();
		
		
		
		
		
	}
}