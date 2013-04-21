package com.example.hpslide;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.hpslide.MyHorizontalScrollView.SizeCallback;
import com.example.hpslide.MyPadosi.ClickListenerForScrolling;
import com.example.hpslide.MyPadosi.GetMyPadosi;
import com.example.hpslide.MyPadosi.SizeCallbackForMenu;
import com.example.objectStorage.objectStorage;

public class Message extends Activity {
	 MyHorizontalScrollView scrollView;
	  View menu;
	    View app;
	    ImageView btnSlide;
	    boolean menuOut = false;
	    Handler handler = new Handler();
	    int btnWidth;
	    Button logout;
	    Button  PadosiWall;
	    Button myPadosibutton;
	    ListView listView1;
	    String padosi_id;
	    
		SharedPreferences prefs_data;
		SharedPreferences.Editor prefsEditor_data ;
		
		 String s=objectStorage.gettoken();
		 private ArrayList<Product1> productArrList1 = new ArrayList<Product1>();
		 private Message _instance1;
		
		

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        LayoutInflater inflater = LayoutInflater.from(this);
	        scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.horz_scroll_with_list_menu, null);
	        setContentView(scrollView);
	        
	        _instance1 = this;
	        menu = inflater.inflate(R.layout.horz_scroll_menu, null);
	        app = inflater.inflate(R.layout.mypadosi, null);
	        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBarm);
	        listView1 = (ListView)app.findViewById(R.id.mypadosilist);
	        listView1.setClickable(true);
	        
	   	
	       
	       // ListView listView = (ListView) app.findViewById(R.id.list);
	       // ViewUtils.initListView(this, listView, "Item ", 30, android.R.layout.simple_list_item_1);

	      //  listView = (ListView) menu.findViewById(R.id.list);
	    //    ViewUtils.initListView(this, listView, "Menu ", 30, android.R.layout.simple_list_item_1);

	        logout= (Button)findViewById(R.id.logout);
	       PadosiWall = (Button)findViewById(R.id.padosiwallbutton);
	       myPadosibutton = (Button)findViewById(R.id.myPadosi);
	     new GetMyPadosi().execute();
	        
	        btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlidem);
	   btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));

	        final View[] children = new View[] { menu, app };

	        // Scroll to app (view[1]) when layout finished.
	        int scrollToViewIdx = 1;
	        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
	     
	    }
	    class GetMyPadosi extends AsyncTask<Void, Void, Void> {
			ProgressDialog pDialog;
			protected ProductAdapter1 parent;
		
			@Override
			protected void onPreExecute() {
				pDialog = ProgressDialog.show(Message.this, null,
						"Authenticating...".toString(), true,
						true, new DialogInterface.OnCancelListener() {

					public void onCancel(DialogInterface arg0) {
						GetMyPadosi.this.cancel(true);
					}
				});

				super.onPreExecute();
			}
			@Override
			protected Void doInBackground(Void... params) {
				try {
				
					/*Intent i = getIntent();
					Bundle extras = i.getExtras();

					TokenWall = extras.getString("Token");
					Log.d("TokenWall", TokenWall);
					;*/
					JSONObject jobject;
					HttpClient httpclient = new DefaultHttpClient();
					//HttpGet httpGet = new HttpGet("http://192.168.35.138/pawan-post-request.txt");
					HttpPost httppost = new HttpPost("http://yourpadosi.com/rest/mypadosiAndroid/");
					 JSONObject jsonobj;
		       	  jsonobj = new JSONObject();
		       	 
						jsonobj.put("limit", "0");
						jsonobj.put("token", s);
						  StringEntity se = new StringEntity(jsonobj.toString());
				          	//se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				          	se.setContentType("application/json;charset=UTF-8");
				          	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
				          	httppost.setEntity(se);
				     
				          	HttpResponse response = httpclient.execute(httppost);
							HttpEntity entity = response.getEntity();
							String is = EntityUtils.toString(entity);
							jobject =  new JSONObject(is);
							Log.v("MainActiivty", "getJson :: count="+jobject.getInt("total_padosi"));
							int c = jobject.getInt("total_padosi");
							for(int j = 0 ; j < c ; j++){
								Product1 p1 = new Product1();
								//Log.v("MainActiivty", "doInBackground :: i="+i);
								String index = Integer.toString(j);
								//Log.v("MainActiivty", "doInBackground :: test="+test);
								JSONObject jobject1 = jobject.getJSONObject(index);

								p1.setpadosiid(jobject1.getString("padosi_id"));
								p1.setpadosiname(jobject1.getString("padosi_name"));
								p1.setpadosiimage(jobject1.getString("padosi_image"));
								
								productArrList1.add(p1);
								padosi_id = jobject1.getString("padosi_id");
								
								Log.v("productArrList", "doInBackground ::padosi_id="+jobject1.getString("padosi_id").replace("\"", "" ));
								/*Log.v("MainActiivty", "doInBackground :: wall_id="+jobject1.getString("wall_id"));
								Log.v("MainActiivty", "doInBackground :: description="+jobject1.getString("description"));
								Log.v("MainActiivty", "doInBackground :: wall_postby_name="+jobject1.getString("wall_postby_name"));
								Log.v("MainActiivty", "doInBackground :: wall_postby_image="+jobject1.getString("wall_postby_image"));
								Log.v("MainActiivty", "doInBackground :: created_date="+jobject1.getString("created_date"));
							*/
							
							}	
							Product1 prod= new Product1();
							prod.setpadosiid(padosi_id);
				
					Log.v("MainActiivty", "doInBackground :: productArrList="+productArrList1.size());
				} catch (Exception e) {
					Log.v("MainActiivty", "doInBackground :: exception="+e.toString());
				}return null;


			}

			@Override
			protected void onPostExecute(Void result) {
				if (null != pDialog && pDialog.isShowing()) {
					pDialog.dismiss();
				}
				Log.v("MainActiivty", "onPostExecute :: productArrList="+productArrList1.size());
				final ProductAdapter1 pAdpter = new ProductAdapter1(_instance1, productArrList1);
				if(listView1!=null){
					listView1.setAdapter(pAdpter);
				}else {
					Log.v("MainActiivty", "onPostExecute :: ad null");
				}
				 listView1.setOnItemClickListener(new OnItemClickListener() {
			         @Override
			         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			        	 Intent intent = new Intent(Message.this, MypadosiDetails.class);
			        	 final Product1[] addresses = productArrList1.toArray(new Product1[productArrList1.size()]);
			  	 Product1 prod = productArrList1.get(position);
			        	/* prod.getpadosiid().toString();*///new Product1();
			        	 intent.putExtra("present", prod); 
			        	 /* intent.putExtra("PadosiId", userid);*/



			        	 startActivity(intent);



			        	 }


				
			        });
				
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
	                scrollView.smoothScrollTo(left, 0);
	            } else {
	                // Scroll to menuWidth so menu isn't on screen.
	                int left = menuWidth;
	                scrollView.smoothScrollTo(left, 0);
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
