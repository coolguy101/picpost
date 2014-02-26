package ca.ualberta.cs.picposter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;
import ca.ualberta.cs.picposter.model.PicPostModel;

public class ElasticSearchOperations {
    
	public static void pushPicPostModel(final PicPostModel model)
	{
          new AsyncTask<Void, Void, Void>()
          {
            
			

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				HttpClient httpClient = new DefaultHttpClient();
				HttpPut request = new HttpPut("http://cmput301.softwareprocess.es:8080/testing/yazhou/1");
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

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
			
        	  
          }.execute();
          
          
	}
	
}
