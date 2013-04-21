/*package com.example.hpslide;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.objectStorage.objectStorage;

public class ProductDataLoader {
	
	
	private ArrayList<Product> productArrList = new ArrayList<Product>();
	public ProductDataLoader(){
		getJson();

		
	}
	private void getJson(){
		try {
			objectStorage os = new objectStorage();
			os.gettoken();
			JSONObject jobject;
			HttpClient httpclient = new DefaultHttpClient();
			//HttpGet httpGet = new HttpGet("http://192.168.35.138/pawan-post-request.txt");
			HttpPost httppost = new HttpPost("http://www.yourpadosi.com/rest/wallAndroid/");
			 JSONObject jsonobj;
       	  jsonobj = new JSONObject();
       	 
				jsonobj.put("limit", "0");
				jsonobj.put("token", os.gettoken());
				  StringEntity se = new StringEntity(jsonobj.toString());
		          	//se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		          	se.setContentType("application/json;charset=UTF-8");
		          	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
		          	httppost.setEntity(se);
		          	Log.d("JSonToken", jsonobj.toString());
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("limit", "0"));
			nameValuePairs.add(new BasicNameValuePair("token", "token"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String is = EntityUtils.toString(entity);
			jobject =  new JSONObject(is);
			Log.v("MainActiivty", "getJson :: count="+jobject.getInt("count"));
			int c = jobject.getInt("count");
			for(int i = 0 ; i < c ; i++){
				Product p = new Product();
				//Log.v("MainActiivty", "doInBackground :: i="+i);
				String index = Integer.toString(i);
				//Log.v("MainActiivty", "doInBackground :: test="+test);
				JSONObject jobject1 = jobject.getJSONObject(index);

				p.setWallId(jobject1.getString("wall_id"));
				p.setWallName(jobject1.getString("wall_postby_name"));
				p.setDescription(jobject1.getString("description"));
				p.setProductThumb(jobject1.getString("wall_photo_url").replace("\"", "" ));
				
				p.setProductPubDate(jobject1.getString("created_date"));
				p.setSubject(jobject1.getString("subject"));
			
			p.setProductThumb2(jobject1.getString("wall_postby_image"));
				productArrList.add(p);
				
				Log.v("productArrList", "doInBackground ::wall_postby_image="+jobject1.getString("wall_postby_image").replace("\"", "" ));
				Log.v("MainActiivty", "doInBackground :: wall_id="+jobject1.getString("wall_id"));
				Log.v("MainActiivty", "doInBackground :: description="+jobject1.getString("description"));
				Log.v("MainActiivty", "doInBackground :: wall_postby_name="+jobject1.getString("wall_postby_name"));
				Log.v("MainActiivty", "doInBackground :: wall_postby_image="+jobject1.getString("wall_postby_image"));
				Log.v("MainActiivty", "doInBackground :: created_date="+jobject1.getString("created_date"));
			
			
			}
			Log.v("MainActiivty", "getJson :: productArrList="+productArrList.size());
		} catch (Exception e) {
			Log.v("MainActiivty", "getJson :: exception="+e.toString());
		}
	}
	public ArrayList<Product> getArrList(){
		return productArrList;
	}
}
*/