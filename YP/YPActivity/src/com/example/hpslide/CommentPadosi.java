package com.example.hpslide;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.hpslide.MyHorizontalScrollView.SizeCallback;
import com.example.hpslide.MypadosiDetails.SizeCallbackForMenu;
import com.example.objectStorage.objectStorage;

public class CommentPadosi extends Activity{
	 MyHorizontalScrollView scrollView;
	  View menu;
	    View app;
	    ImageView btnSlide;
	    int btnWidth;
	    Button logout;
	    Button  PadosiWall;
	    Button myPadosibutton;
	    String WallType ;
	    String WallId;
	    String commentpadosinanme= "";
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
	        app = inflater.inflate(R.layout.commentpadosi, null);
	        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar5);
	        menu = inflater.inflate(R.layout.horz_scroll_menu, null);

String wallname = getIntent().getExtras().getString("wallname").toString();
commentpadosinanme = wallname.toString();
Log.v("string", commentpadosinanme);
String string1 = getIntent().getExtras().getString("productthum");

TextView text2 = (TextView)app.findViewById(R.id.Commentpadosiname);
text2.setText(wallname);
String image_url = "http://www.yourpadosi.com/"+ string1;
ImageView img = (ImageView)app. findViewById(R.id.Commentpadosimage);

	AQuery aq = new AQuery(context);
	aq.id(img).image(image_url, true, true);

	      Button  post = (Button)findViewById(R.id.postComment);
	      
	


	
   logout= (Button)findViewById(R.id.logout);
   PadosiWall = (Button)findViewById(R.id.padosiwallbutton);
   myPadosibutton = (Button)findViewById(R.id.myPadosi);
    btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide5);
	   btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));

	        final View[] children = new View[] { menu, app };

	        // Scroll to app (view[1]) when layout finished.
	        int scrollToViewIdx = 1;
	        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
	   
	    }
	 public void onClickPost2(View view) {
		 
		 		new CommentPostData().execute();
		 		Intent i = new Intent(this, YPWall.class);
				startActivity(i);
				Display display = ((WindowManager) 
						getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
				if ((display.getRotation() == Surface.ROTATION_0) || 
						(display.getRotation() == Surface.ROTATION_90)) {
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				} else if ((display.getRotation() == Surface.ROTATION_180) ||
						(display.getRotation() == Surface.ROTATION_270)) {
					overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
				}
			
			
		}
	 
	 
class CommentPostData extends AsyncTask<Void, Void, Void> {

			ProgressDialog pDialog_data;

			@Override
			protected void onPreExecute() {
				pDialog_data = ProgressDialog.show(CommentPadosi.this, null,
						"Authenticating...".toString(), true,
				true, new DialogInterface.OnCancelListener() {
					
					public void onCancel(DialogInterface arg0) {
						CommentPostData.this.cancel(true);
					}
				});
			
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;
		
				try {
				HttpPost httppost = new HttpPost("http://yourpadosi.com/rest/sendCommentAndroid/");
		/*		Intent i = getIntent();
				Bundle extras = i.getExtras();*/
			      Bundle extras=getIntent().getExtras();
				     String WallId=extras.getString("productwall");
				     String WallType=extras.getString("productwalltype");
				     String wallname = extras.getString("wallname");
				     String wallThumb = extras.getString("productthum");
		     		 Log.v("Walltype", WallType);
	        		 Log.v("WallId", WallId);
			/*	Product myPresent = (Product)i.getSerializableExtra("product");
				Product myPresent1 = (Product)i.getSerializableExtra("productwall");
				Product myPresent2 = (Product)i.getSerializableExtra("productwalltype");*/
			/*	WallType = myPresent.getWallType().toString();
				 WallId=  myPresent.getWallId().toString();*/
			    EditText subj = (EditText)findViewById(R.id.subject1);
			  	 Subject = subj.getText().toString();
			 
			
		/*	EditText bodi = (EditText)findViewById(R.id.body);
			body = bodi.getText().toString();*/
			      
			        
			        	
			            JSONObject jsonobj;
			        	  jsonobj = new JSONObject();
			        	 
							jsonobj.put("wallId", WallId);
							jsonobj.put("description", Subject);
							jsonobj.put("userToken", s);
							jsonobj.put("wallType", WallType);
			        	
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
