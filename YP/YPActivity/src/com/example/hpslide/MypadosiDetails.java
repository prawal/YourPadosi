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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MypadosiDetails extends Activity{
	 MyHorizontalScrollView scrollView;
	  View menu;
	    View app;
	    ImageView btnSlide;
	    private Context context;
	    boolean menuOut = false;
	    Handler handler = new Handler();
	    int btnWidth;
	    Button logout;
	    Button  PadosiWall;
	    Button myPadosibutton;
	    private ArrayList<Product2> productArrList2 = new ArrayList<Product2>();
	    SharedPreferences prefs_data;
		SharedPreferences.Editor prefsEditor_data ;
	    String s=objectStorage.gettoken();
	    String Padosi_id ;
	    ListView listView3;
	    private YPWall _instance2;
		String aboutPadosi= "";
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        LayoutInflater inflater = LayoutInflater.from(this);
	        scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.horz_scroll_with_list_menu, null);
	        setContentView(scrollView);
	   
			//
	        menu = inflater.inflate(R.layout.horz_scroll_menu, null);
	        app = inflater.inflate(R.layout.mypadosidetail, null);
	        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar4);
	        new GetMyPadosiDetails().execute();
	        
	        		
	   
	        logout= (Button)findViewById(R.id.logout);
		       PadosiWall = (Button)findViewById(R.id.padosiwallbutton);
		       myPadosibutton = (Button)findViewById(R.id.myPadosi);
		        btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide4);
		 	   btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));

		 	        final View[] children = new View[] { menu, app };

		 	        // Scroll to app (view[1]) when layout finished.
		 	        int scrollToViewIdx = 1;
		 	        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
	    
	    }
	   
	    class GetMyPadosiDetails extends AsyncTask<Void, Void, Void> {
			ProgressDialog pDialog;
		
			@Override
			protected void onPreExecute() {
				pDialog = ProgressDialog.show(MypadosiDetails.this, null,
						"Authenticating...".toString(), true,
						true, new DialogInterface.OnCancelListener() {

					public void onCancel(DialogInterface arg0) {
						GetMyPadosiDetails.this.cancel(true);
					}
				});

				super.onPreExecute();
			}
			@Override
			protected Void doInBackground(Void... params){
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;
				try {

					Intent intent = getIntent();
					Product1 myPresent = (Product1)intent.getSerializableExtra("present");
					
					Padosi_id = myPresent.getpadosiid().toString();
					Log.v("Tag", "My Present is: " + myPresent.getpadosiid());
					JSONObject jobject;
				
					//HttpGet httpGet = new HttpGet("http://192.168.35.138/pawan-post-request.txt");
					HttpPost httppost = new HttpPost("http://www.yourpadosi.com/rest/padosiDetailAndroid/");
					 JSONObject jsonobj;
		       	  jsonobj = new JSONObject();
		       	 
						jsonobj.put("padosiId", Padosi_id);

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
						
						JSONObject json = new JSONObject(builder.toString());
						JSONArray jArray = json.getJSONArray("detail");

						System.out.println("*****JARRAY*****"+jArray.length());
						for(int i=0;i<jArray.length();i++){


						 JSONObject json_data = jArray.getJSONObject(i);
						 Log.i("log_tag","_id"+json_data.getString("full_name")+
						  ", mall_name"+json_data.getString("user_image")+
						  ", location"+json_data.getString("padosi_id")+
						  ", telephone"+json_data.getString("padosi_image")
						
						 );

					
						
					int len = json_data.length();
					Product2 prod2 = null;

					prod2 = new Product2();

									 prod2.setfullname(json_data.getString("full_name"));
								
									 prod2.setUserImage(json_data.getString("user_image"));
									 prod2.setPadosiID(json_data.getString("padosi_id"));
									 prod2.setPadosiImage(json_data.getString("padosi_image"));
									 prod2.setPadosiName(json_data.getString("padosi_name"));
									 prod2.setResidence(json_data.getString("resident_since"));
									 prod2.setAboutPadosi(json_data.getString("about_padosi"));
										String aboutPadosi = json_data.getString("about_padosi");
										aboutPadosi.toString();
									     Log.d("aboutPadosi", json_data.getString("padosi_image"));
					       Log.d("Myname", json_data.getString("full_name"));
					       
						Log.w("YourPadosi", "Data Request");

						
						productArrList2.add(prod2);
			
						
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
				if (null != pDialog && pDialog.isShowing()) {
					pDialog.dismiss();
				}
			Log.v("MainActiivty", "onPostExecute :: productArrList="+productArrList2.size());
			productArrList2 = new ArrayList<Product2>();
			
			String padosi=aboutPadosi.toString();
			Log.d("padosi", padosi);
		
			prefs_data = getSharedPreferences("dataPref",Context.MODE_PRIVATE);
			prefsEditor_data = prefs_data.edit();
		/*	prefsEditor_data.putString("response",aboutPadosi);*/
		
			prefsEditor_data.commit();
			
			TextView text=(TextView)findViewById(R.id.aboutPadosi);
			text.setText(String.valueOf(Product2.getAboutPadosi()));

		TextView text2 = (TextView)findViewById(R.id.resindence);
		text2.setText(String.valueOf(Product2.getResidence()));
		
		String image_url = "http://www.yourpadosi.com/"+ Product2.getPadosiImage();
		 ImageView img = (ImageView) findViewById(R.id.PadosiImage);
		
			AQuery aq = new AQuery(context);
			aq.id(img).image(image_url, true, true);
				/*final ProductAdapter2 pAdpter = new ProductAdapter2(_instance2, productArrList2);
				if(listView3!=null){
					listView3.setAdapter(pAdpter);
				}else {
					Log.v("MainActiivty", "onPostExecute :: ad null");
				}
				*/

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
			  i.putExtra("Id", Padosi_id );

	
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
		public void backboy(View view) {
			Intent i = new Intent(this, MyPadosi.class);
			  i.putExtra("Id", Padosi_id );

	
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
		public ArrayList<Product2> getArrList(){
			return productArrList2;
		}
	}
