package com.example.hpslide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.objectStorage.objectStorage;




public class YPActivity extends Activity  {
	private static String KEY_SUCCESS = "success";
	Button ok,back,exit;
	TextView loginErrorMsg;
	 
	SharedPreferences prefs_data;
	SharedPreferences.Editor prefsEditor_data ;
	
	String Token = "";
    String userId="";
    String response_data="";
	 
	public String pref_token="";
	public String pref_userid="";
	public String pref_response="";
	String username;
	   ArrayList<objectStorage> objectList ;
	   String password;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

        ok = (Button)findViewById(R.id.login);
        EditText uname = (EditText)findViewById(R.id.loginEmail);
  	 username = uname.getText().toString();
 
String Token = "";
EditText pword = (EditText)findViewById(R.id.loginPassword);
 password = pword.getText().toString();
   
    }

    
  /*
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try{
            while((line = reader.readLine()) !=null ){
                sb.append(line + "\n");
            }
        }
            catch (IOException e){
                e.printStackTrace();
            } finally{
                try {
                    is.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            return sb.toString();
    }*/
  
	public void onClickLogin(View view) {
		if(view == ok){
			
			new fethcingData().execute();
	}
		
		
	}
	
	
	
	class fethcingData extends AsyncTask<Void, Void, Void> {

		MySpinner pDialog;

		@Override
		protected void onPreExecute() {
			pDialog = MySpinner.show(YPActivity.this, null, "", true,
					true, null);
			super.onPreExecute();
		}

		
		/*protected void onPreExecute() {
		pDialog_data = ProgressDialog.show(YPActivity.this, null,
					"Authenticating...".toString(), true,
			true, new DialogInterface.OnCancelListener() {
				
				public void onCancel(DialogInterface arg0) {
					fethcingData.this.cancel(true);
				}
			});
	
			super.onPreExecute();
		}*/

		@Override
		protected Void doInBackground(Void... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
	
			try {
			HttpPost httppost = new HttpPost("http://yourpadosi.com/rest/loginAndroid/");
			      EditText uname = (EditText)findViewById(R.id.loginEmail);
			   	 username = uname.getText().toString();
			         
			     
		
			 EditText pword = (EditText)findViewById(R.id.loginPassword);
			  password = pword.getText().toString();
		      
		            // Add user name and password
		        	
		            JSONObject jsonobj;
		        	  jsonobj = new JSONObject();
		        	 
						jsonobj.put("userName", username);
						jsonobj.put("passWord", password);
					
						
		        	
		        	  StringEntity se = new StringEntity(jsonobj.toString());
		          	//se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		          	se.setContentType("application/json;charset=UTF-8");
		          	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
		          	httppost.setEntity(se);
		      
		            // Execute HTTP Post Request
		            Log.w("YourPadosi", "Execute HTTP Post Request");
				response = httpclient.execute(httppost);
				if (response == null)
					return null;
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				StringBuilder builder = new StringBuilder();
				for (String line = null; (line = reader.readLine()) != null;) {
					builder.append(line).append("\n");
				}
				String tokener = new String(builder.toString());
				JSONObject objJson=new JSONObject(tokener);
			    Log.d("mydata", tokener);
                JSONObject finalResult=new JSONObject(tokener);
           
				int len = finalResult.length();
				objectStorage objNeeded = null;

					objNeeded = new objectStorage();
					prefs_data = YPActivity.this.getSharedPreferences("dataPref",Context.MODE_PRIVATE);
					 prefsEditor_data = prefs_data.edit();
				    objNeeded.setresponse(finalResult.getString("response"));
					response_data=finalResult.getString("response");
					objNeeded.settoken(finalResult.getString("token"));
					Token=finalResult.getString("token");
					objNeeded.setuserid(finalResult.getString("user_id"));
					userId=finalResult.getString("user_id");
					objNeeded.setfullname(finalResult.getString("full_name"));
					objNeeded.setusername(finalResult.getString("username"));

					
					objNeeded.setuserImage(finalResult.getString("user_image"));
				       Log.d("setuserImage", finalResult.getString("user_image")); 
				       
				       
					Log.w("YourPadosi", "Data Request");

					objectList=new ArrayList<objectStorage>();
					objectList.add(objNeeded);
//				 }
				}
		        catch (JSONException e) {
					// TODO Auto-generated catch block
		        	String test=e.getMessage().toString();
					e.printStackTrace();
				}
			 catch (Exception ex) {
				ex.printStackTrace();
			}
		        
		
			// Log.d("Contacts",arrContacts.toString());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
//			if (null == objectList || objectList.size() == 0) {
//				Toast.makeText(getApplicationContext(),"No Internet Connectivity",
//						Toast.LENGTH_SHORT).show();
//			} 
			objectList=new ArrayList<objectStorage>();
			
			String testCheck=response_data.toString();
			Log.e(testCheck, testCheck);
			String token=Token.toString();
			String userid=userId.toString();
			Log.d("Id", userid);
			Log.d("Id", token);
			prefs_data = getSharedPreferences("dataPref",Context.MODE_PRIVATE);
			prefsEditor_data = prefs_data.edit();
			prefsEditor_data.putString("response",testCheck);
			prefsEditor_data.putString("token",token);
			prefsEditor_data.putString("response",userid);
			prefsEditor_data.commit();
			if(testCheck.equalsIgnoreCase("1"))
			{
				Intent i = new Intent(YPActivity.this, YPWall.class);
				startActivity(i);
				i.putExtra("token", token);
				Log.w("YourPadosi", "Data post Succesfull");
			}
			else
			{
				Toast.makeText(YPActivity.this, "UserId or Password is incorrect", Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}
	}

}