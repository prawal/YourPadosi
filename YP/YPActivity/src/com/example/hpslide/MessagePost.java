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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.example.hpslide.MyHorizontalScrollView.SizeCallback;
import com.example.hpslide.MypadosiDetails.ClickListenerForScrolling;
import com.example.hpslide.MypadosiDetails.SizeCallbackForMenu;
import com.example.objectStorage.objectStorage;

public class MessagePost extends Activity{
	 MyHorizontalScrollView scrollView;
	  View menu;
	    View app;
	    ImageView btnSlide;
	    int btnWidth;
	    Button logout;
	    Button  PadosiWall;
	    Button myPadosibutton;
	    private Context context;
	    boolean menuOut = false;
	String Subject;
	String body;
	 String s=objectStorage.gettoken();
	 Button post;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
	        LayoutInflater inflater = LayoutInflater.from(this);
	       
	        scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.horz_scroll_with_list_menu, null);
	        setContentView(scrollView);
	   
	        app = inflater.inflate(R.layout.sendmessage, null);
	        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar6);
	        menu = inflater.inflate(R.layout.horz_scroll_menu, null);

	      Button  post = (Button)findViewById(R.id.postMessage);
	/*        EditText subj = (EditText)findViewById(R.id.subject);
	  	 Subject = subj.getText().toString();
	 
	
	EditText bodi = (EditText)findViewById(R.id.body);
	body = bodi.getText().toString();*/
	
    logout= (Button)findViewById(R.id.logout);
    PadosiWall = (Button)findViewById(R.id.padosiwallbutton);
    myPadosibutton = (Button)findViewById(R.id.myPadosi);
     btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide6);
	   btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));

	        final View[] children = new View[] { menu, app };

	        // Scroll to app (view[1]) when layout finished.
	        int scrollToViewIdx = 1;
	        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
	   
	    }
	 public void onClickPost2(View view) {
			
				
				new MessagePostData().execute();
	
			
			
		}
class MessagePostData extends AsyncTask<Void, Void, Void> {

			ProgressDialog pDialog_data;

			@Override
			protected void onPreExecute() {
				pDialog_data = ProgressDialog.show(MessagePost.this, null,
						"Authenticating...".toString(), true,
				true, new DialogInterface.OnCancelListener() {
					
					public void onCancel(DialogInterface arg0) {
						MessagePostData.this.cancel(true);
					}
				});
			
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;
		
				try {
				HttpPost httppost = new HttpPost("http://yourpadosi.com/rest/sendMessageAndroid/");
				Intent i = getIntent();
				Bundle extras = i.getExtras();

				String id = extras.getString("Id");
				Log.d("MYPastId", id);
				 
			    EditText subj = (EditText)findViewById(R.id.subject);
			  	 Subject = subj.getText().toString();
			 
			
			EditText bodi = (EditText)findViewById(R.id.body);
			body = bodi.getText().toString();
			      
			        
			        	
			            JSONObject jsonobj;
			        	  jsonobj = new JSONObject();
			        	 
							jsonobj.put("subject", Subject);
							jsonobj.put("body", body);
							jsonobj.put("userToken", s);
							jsonobj.put("messageTo", id);
			        	
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
				if (null != pDialog_data && pDialog_data.isShowing()) {
					pDialog_data.dismiss();
				}
//				if (null == objectList || objectList.size() == 0) {
//					Toast.makeText(getApplicationContext(),"No Internet Connectivity",
//							Toast.LENGTH_SHORT).show();
//				} 
			
				super.onPostExecute(result);
			}
		}


static class ClickListenerForScrolling implements OnClickListener {
		HorizontalScrollView scrollView;
		View menu;
		/**
		 * Menu must NOT be out/shown to start with.
		 */
		boolean menuOut = false;

		public ClickListenerForScrolling(HorizontalScrollView scrollView, View menu) {
			super();
			this.scrollView = scrollView;
			this.menu = menu;
		}

		@Override
		public void onClick(View v) {
			Context context = menu.getContext();
			/*   String msg = "Slide " + new Date();
            Toast.makeText(context, msg, 1000).show();
            System.out.println(msg);*/
			int menuWidth = menu.getMeasuredWidth();

			// Ensure menu is visible
			menu.setVisibility(View.VISIBLE);

			if (!menuOut) {
				// Scroll to 0 to reveal menu
				int left = 0;
				scrollView.smoothScrollTo(left, 2);
			} else {
				// Scroll to menuWidth so menu isn't on screen.
				int left = menuWidth;
				scrollView.smoothScrollTo(left, 2);
			}
			menuOut = !menuOut;
		}


	/**
	 * Helper that remembers the width of the 'slide' button, so that the 'slide' button remains in view, even when the menu is
	 * showing.
	 */
	static class SizeCallbackForMenu implements SizeCallback {
		int btnWidth;
		View btnSlide;

		public SizeCallbackForMenu(View btnSlide) {
			super();
			this.btnSlide = btnSlide;
		}

		@Override
		public void onGlobalLayout() {
			btnWidth = btnSlide.getMeasuredWidth();
			System.out.println("btnWidth=" + btnWidth);
		}

		@Override
		public void getViewSize(int idx, int w, int h, int[] dims) {
			dims[0] = w;
			dims[1] = h;
			final int menuIdx = 0;
			if (idx == menuIdx) {
				dims[0] = w - btnWidth;
			}
		}
	}

}
}
