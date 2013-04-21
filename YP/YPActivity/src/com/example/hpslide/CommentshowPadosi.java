package com.example.hpslide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.hpslide.MyHorizontalScrollView.SizeCallback;
import com.example.objectStorage.objectStorage;

public class CommentshowPadosi extends Activity {
	 MyHorizontalScrollView scrollView;
	  View menu;
	    View app;
	    ImageView btnSlide;
	    private Context context;
	    boolean menuOut = false;
	    Handler handler = new Handler();
	    int btnWidth;
	    Button logout;
		ListView listView5;
	    Button  PadosiWall;
	    Button myPadosibutton;

		private ArrayList<Product3> productArrList5 = new ArrayList<Product3>();
	
		 private CommentshowPadosi _instance1;
	
		
	    String   commentshowpadosinanme= "";
	    String s=objectStorage.gettoken();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = LayoutInflater.from(this);
        scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.horz_scroll_with_list_menu, null);
        setContentView(scrollView);
   
		//
        menu = inflater.inflate(R.layout.horz_scroll_menu, null);
        app = inflater.inflate(R.layout.commentshowpadosi, null);
        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar10);
        new getcommentshowpadosi().execute();
        listView5 = (ListView)app.findViewById(R.id.listcomment);      
        _instance1 = this;
        String wallid = getIntent().getExtras().getString("productwall").toString();
        String wallname = getIntent().getExtras().getString("wallname").toString();
     
        String productthum = getIntent().getExtras().getString("productthum");
        TextView text2 = (TextView)app.findViewById(R.id.Commentshowpadosiname);
        text2.setText(wallname);
        String image_url = "http://www.yourpadosi.com/"+ productthum;
        ImageView img = (ImageView)app. findViewById(R.id.Commentshowpadosimage);

        	AQuery aq = new AQuery(context);
        	aq.id(img).image(image_url, true, true);	
   
        logout= (Button)findViewById(R.id.logout);
	       PadosiWall = (Button)findViewById(R.id.padosiwallbutton);
	       myPadosibutton = (Button)findViewById(R.id.myPadosi);
	        btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide10);
	 	   btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));
	 	
	 	
	 	        final View[] children = new View[] { menu, app };

	 	        // Scroll to app (view[1]) when layout finished.
	 	        int scrollToViewIdx = 1;
	 	        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
	}
	  class getcommentshowpadosi extends AsyncTask<Void, Void, Void> {
			ProgressDialog pDialog;
		
			@Override
			protected void onPreExecute() {
				pDialog = ProgressDialog.show(CommentshowPadosi.this, null,
						"Authenticating...".toString(), true,
						true, new DialogInterface.OnCancelListener() {

					public void onCancel(DialogInterface arg0) {
						getcommentshowpadosi.this.cancel(true);
					}
				});

				super.onPreExecute();
			}
			@Override
			protected Void doInBackground(Void... params){
				try {

					/*Intent i = getIntent();
					Bundle extras = i.getExtras();

					TokenWall = extras.getString("Token");
					Log.d("TokenWall", TokenWall);
					;*/
					JSONObject jobject;
					HttpClient httpclient = new DefaultHttpClient();
			
					//HttpGet httpGet = new HttpGet("http://192.168.35.138/pawan-post-request.txt");
					HttpPost httppost = new HttpPost("http://www.yourpadosi.com/rest/CommentAndroid/");
					 JSONObject jsonobj;
		       	  jsonobj = new JSONObject();
		          String wallid = getIntent().getExtras().getString("productwall").toString();
		       	  Log.d("wallname123", wallid);
						jsonobj.put("wallLinkId", wallid);
						jsonobj.put("Token", s);
						 StringEntity se = new StringEntity(jsonobj.toString());
				          	//se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				          	se.setContentType("application/json;charset=UTF-8");
				          	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
				          	httppost.setEntity(se);
				          	Log.d("JSonToken", jsonobj.toString());
				          	HttpResponse response = httpclient.execute(httppost);
							HttpEntity entity = response.getEntity();
							String is = EntityUtils.toString(entity);
							jobject =  new JSONObject(is);
							Log.v("string", is);
							Log.v("MainActiivty", "getJson :: count="+jobject.getInt("count"));
							int c = jobject.getInt("count");
							for(int j = 0 ; j < c ; j++){
								
								//Log.v("MainActiivty", "doInBackground :: i="+i);
								String index = Integer.toString(j);
								//Log.v("MainActiivty", "doInBackground :: test="+test);
								JSONObject jobject1 = jobject.getJSONObject(index);
						
								Product3 p = new Product3();
								//Log.v("MainActiivty", "doInBackground :: i="+i);
							

								p.setcommentshowname(jobject1.getString("comment_postby_name"));
								p.setcommentimage(jobject1.getString("comment_postby_image"));
								p.setcommentpostid(jobject1.getString("comment_postby_id"));
								p.setcommentshowdata(jobject1.getString("comment_postby_data"));
								p.setcommentdate(jobject1.getString("comment_postby_date"));
								
								productArrList5.add(p);
							
						
						     
								
								Log.v("productArrList", "doInBackground ::comment_postby_name="+jobject1.getString("comment_postby_name"));
								Log.v("productArrList", "doInBackground ::commentimage="+jobject1.getString("comment_postby_image"));

								Log.v("productArrList", "doInBackground ::setcommentshowdata="+jobject1.getString("comment_postby_data"));
								Log.v("productArrList", "doInBackground ::setcommentdate="+jobject1.getString("comment_postby_date"));
								/*Log.v("MainActiivty", "doInBackground :: wall_id="+jobject1.getString("wall_id"));
								Log.v("MainActiivty", "doInBackground :: description="+jobject1.getString("description"));
								Log.v("MainActiivty", "doInBackground :: wall_postby_name="+jobject1.getString("wall_postby_name"));
								Log.v("MainActiivty", "doInBackground :: wall_postby_image="+jobject1.getString("wall_postby_image"));
								Log.v("MainActiivty", "doInBackground :: created_date="+jobject1.getString("created_date"));
							*/
							
							}	
					
							
				
				/*	Log.v("MainActiivty", "doInBackground :: productArrList="+productArrList.size());*/
				} catch (Exception e) {
					Log.v("MainActiivty", "doInBackground :: exception="+e.toString());
				}

				return null;


			}
			@Override
			protected void onPostExecute(Void result) {
				if (null != pDialog && pDialog.isShowing()) {
					pDialog.dismiss();
				}
				Log.v("MainActiivty", "onPostExecute :: productArrList="+productArrList5.size());

		
				final ProductAdapter3 pAdpter = new ProductAdapter3(_instance1, productArrList5);
				if(listView5!=null){
					listView5.setAdapter(pAdpter);
				}else {
					Log.v("MainActiivty", "onPostExecute :: ad null");
				}
				 
				
				super.onPostExecute(result);
			}
			
		}
	    public void  logout(View view) {
			Intent i = new Intent(this, YPActivity.class);
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

		public void sendmessagebutton(View view) {
			Intent i = new Intent(this, Message.class);
	
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
		public void sendmessage(View view) {
			Intent i = new Intent(this, MessagePost.class);
	

	
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
		public void padosiwallbutton(View view) {

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
		public void myPadosi(View view) {
			Intent i = new Intent(this, MyPadosi.class);
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
		/**
		 * Helper for examples with a HSV that should be scrolled by a menu View's width.
		 */
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
