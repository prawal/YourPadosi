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

import com.androidquery.AQuery;
import com.example.hpslide.MyHorizontalScrollView.SizeCallback;
import com.example.objectStorage.objectStorage;


public class YPWall extends Activity  {
	MyHorizontalScrollView scrollView;
	ListView listView;
	View menu;
	View app;
	ImageView btnSlide;
	boolean menuOut = false;
    private Context context;
	Handler handler = new Handler();
	int btnWidth;
	Button logout;
	Button  PadosiWall;
	Button  myPadosibutton;
	Button sharephotobuttonicon1;
	SharedPreferences prefs_data;
	SharedPreferences.Editor prefsEditor_data ;
	String response_data="";
	String TokenWall = "";
	String settings;
	Button comment;
Button 	Buttonlike;
Button ButtonUnlike;
	ArrayList<objectStorage> objectList ;
	String count = objectStorage.getcount();
	 String s=objectStorage.gettoken();
	 String s2 = objectStorage.getuserImage();
	
	private ArrayList<Product> productArrList = new ArrayList<Product>();
 private YPWall _instance;
 private YPWall mActivity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v("MainActiivty", "onCreate :: start=");
		super.onCreate(savedInstanceState);

	
		/*Intent i = getIntent();
		Bundle extras = i.getExtras();

		TokenWall = extras.getString("Token");
		Log.d("TokenWall", TokenWall);
		;

		Intent i1 = getIntent();
		Bundle extras1 = i1.getExtras();

		TokenWall = extras.getString("Token");
		Log.d("TokenWall", TokenWall);
		;

		Intent i2 = getIntent();
		Bundle extras2 = i2.getExtras();

		TokenWall = extras.getString("Token");
		Log.d("TokenWall", TokenWall);
		;*/
/*		objectList = new ArrayList<objectStorage>();
*/		_instance = this;
		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.horz_scroll_with_list_menu, null);
		setContentView(scrollView);
		new GetWall().execute();
		menu = inflater.inflate(R.layout.horz_scroll_menu, null);
		app = inflater.inflate(R.layout.horz_scroll_app, null);
		ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);
		listView = (ListView)app.findViewById(R.id.listwall);
		
		 
		// Creating a button - Load More
		Button btnLoadMore = new Button(this);
		btnLoadMore.setText("Load More");
		 
		// Adding button to listview at footer
		listView.addFooterView(btnLoadMore);
		ProductAdapter pAdpter = new ProductAdapter(_instance, productArrList,null, mActivity);
		listView.setAdapter(pAdpter);
		pAdpter.notifyDataSetChanged();
		btnLoadMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Starting a new async task
				new GetWall2().execute();
			}
		});

		// ListView listView = (ListView) app.findViewById(R.id.list);
		// ViewUtils.initListView(this, listView, "Item ", 30, android.R.layout.simple_list_item_1);

		//  listView = (ListView) menu.findViewById(R.id.list);
		//    ViewUtils.initListView(this, listView, "Menu ", 30, android.R.layout.simple_list_item_1);
/* comment = (Button)findViewById(R.id.commenttopado);
*/
		String image_url = "http://www.yourpadosi.com/"+ objectStorage.getuserImage();
		 ImageView img = (ImageView)menu.findViewById(R.id.imageTypeuser);
		
			AQuery aq = new AQuery(context);
			aq.id(img).image(image_url, true, true);
		logout= (Button)findViewById(R.id.logout);
		PadosiWall = (Button)findViewById(R.id.padosiwallbutton);
		myPadosibutton = (Button)findViewById(R.id.myPadosi);
	

		btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide);
		btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));

		final View[] children = new View[] { menu, app };

		// Scroll to app (view[1]) when layout finished.
		int scrollToViewIdx = 1;
		scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
		
		
	}


	
		 
  

	
	
	class GetWall extends AsyncTask<Void, Void, Void> {
		MySpinner pDialog;

		@Override
		protected void onPreExecute() {
			pDialog = MySpinner.show(YPWall.this, null, "", true,
					true, null);
			super.onPreExecute();
		}

		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				 String s=objectStorage.gettoken();
				 Log.d("String", s);
				/*Intent i = getIntent();
				Bundle extras = i.getExtras();

				TokenWall = extras.getString("Token");
				Log.d("TokenWall", TokenWall);
				;*/
				JSONObject jobject;
				HttpClient httpclient = new DefaultHttpClient();
				//HttpGet httpGet = new HttpGet("http://192.168.35.138/pawan-post-request.txt");
				HttpPost httppost = new HttpPost("http://www.yourpadosi.com/rest/wallAndroid/");
				 JSONObject jsonobj;
	       	  jsonobj = new JSONObject();
	       	 
				jsonobj.put("limit", "0");
					jsonobj.put("token", s);
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
						objectStorage os =new objectStorage();
						String count = jobject.getString("count");
						os.setcount(count);
						Log.v("string", is);
						Log.v("MainActiivty", "getJson :: count="+jobject.getInt("count"));
						int c = jobject.getInt("count");
						for(int j = 0 ; j < c ; j++){
							Product p = new Product();
							//Log.v("MainActiivty", "doInBackground :: i="+i);
							String index = Integer.toString(j);
							//Log.v("MainActiivty", "doInBackground :: test="+test);
							JSONObject jobject1 = jobject.getJSONObject(index);

							p.setWallId(jobject1.getString("wall_link_id"));
							p.setWallName(jobject1.getString("wall_postby_name"));
							p.setDescription(jobject1.getString("description"));
							p.setProductThumb(jobject1.getString("wall_postby_image"));
							p.setProductPubDate(jobject1.getString("created_date"));
							p.setSubject(jobject1.getString("subject"));
							p.setWallType(jobject1.getString("wall_type"));
							p.settotalComment(jobject1.getString("total_comments"));
							String record= jobject1.getString("likes_postby_users").toString();
							String[] total_likes = record.split(",");
							Log.v("aaajtak", record);
							p.setlikePostUser(total_likes.length);
							p.setlikeid(jobject1.getString("wall_like_id"));
							p.setliketype(jobject1.getString("wall_type"));
							p.setwallpostid(jobject1.getString("wall_postby_id"));
							p.setcurrentlikeuser(jobject1.getString("like_status"));
							p.setcountlike(jobject1.getString("like_status"));
							p.setProductThumb1(jobject1.getString("wall_photo_url"));
							productArrList.add(p);
							Log.d("productArrList", "doInBackground ::wall_photo_url="+jobject1.getString("wall_photo_url"));
							Log.v("productArrList", "doInBackground ::like_status="+jobject1.getString("like_status").replace("\"", "" ));
							/*Log.v("MainActiivty", "doInBackground :: wall_id="+jobject1.getString("wall_id"));
							Log.v("MainActiivty", "doInBackground :: description="+jobject1.getString("description"));
							Log.v("MainActiivty", "doInBackground :: wall_postby_name="+jobject1.getString("wall_postby_name"));
							Log.v("MainActiivty", "doInBackground :: wall_postby_image="+jobject1.getString("wall_postby_image"));
							Log.v("MainActiivty", "doInBackground :: created_date="+jobject1.getString("created_date"));
						*/
						
						}	
						
			
				Log.v("MainActiivty", "doInBackground :: productArrList="+productArrList.size());
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
			Log.v("MainActiivty", "onPostExecute :: productArrList="+productArrList.size());
			ProductAdapter pAdpter = new ProductAdapter(_instance, productArrList,null, mActivity);
			if(listView!=null){
				listView.setAdapter(pAdpter);
				pAdpter.notifyDataSetChanged();
				
			}else {
				Log.v("MainActiivty", "onPostExecute :: ad null");
			}
			

			super.onPostExecute(result);
		}
	}
	class GetWall2 extends AsyncTask<Void, Void, Void> {
		MySpinner pDialog;

		@Override
		protected void onPreExecute() {
			pDialog = MySpinner.show(YPWall.this, null, "", true,
					true, null);
			super.onPreExecute();
		}

		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				 String s=objectStorage.gettoken();
				 Log.d("String", s);
				/*Intent i = getIntent();
				Bundle extras = i.getExtras();

				TokenWall = extras.getString("Token");
				Log.d("TokenWall", TokenWall);
				;*/
				JSONObject jobject;
				HttpClient httpclient = new DefaultHttpClient();
				//HttpGet httpGet = new HttpGet("http://192.168.35.138/pawan-post-request.txt");
				HttpPost httppost = new HttpPost("http://www.yourpadosi.com/rest/wallAndroid/");
				 JSONObject jsonobj;
	       	  jsonobj = new JSONObject();
	       	 
				jsonobj.put("limit", count);
					jsonobj.put("token", s);
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
					objectStorage os = new objectStorage();
						String count = jobject.getString("count");
						 os.setcount(count);
						Log.v("string", is);
						Log.v("MainActiivty", "getJson :: count="+jobject.getInt("count"));
						int c = jobject.getInt("count");
						for(int j = 0 ; j < c ; j++){
						Product p = new Product();
							//Log.v("MainActiivty", "doInBackground :: i="+i);
							String index = Integer.toString(j);
							//Log.v("MainActiivty", "doInBackground :: test="+test);
							JSONObject jobject1 = jobject.getJSONObject(index);

							p.setWallId(jobject1.getString("wall_link_id"));
							p.setWallName(jobject1.getString("wall_postby_name"));
							p.setDescription(jobject1.getString("description"));
							p.setProductThumb(jobject1.getString("wall_postby_image"));
							p.setProductPubDate(jobject1.getString("created_date"));
							p.setSubject(jobject1.getString("subject"));
							p.setWallType(jobject1.getString("wall_type"));
							p.settotalComment(jobject1.getString("total_comments"));
							String record= jobject1.getString("likes_postby_users").toString();
							String[] total_likes = record.split(",");
							Log.v("aaajtak", record);
							p.setlikePostUser(total_likes.length);
							p.setlikeid(jobject1.getString("wall_like_id"));
							p.setliketype(jobject1.getString("wall_type"));
							p.setwallpostid(jobject1.getString("wall_postby_id"));
							p.setcurrentlikeuser(jobject1.getString("like_status"));
							p.setProductThumb1(jobject1.getString("wall_photo_url"));
							
							productArrList.add(p);
							Log.v("productArrList", "doInBackground ::wall_photo_url="+jobject1.getString("wall_photo_url"));
							
							Log.v("productArrList", "doInBackground ::like_status="+jobject1.getString("like_status").replace("\"", "" ));
							/*Log.v("MainActiivty", "doInBackground :: wall_id="+jobject1.getString("wall_id"));
							Log.v("MainActiivty", "doInBackground :: description="+jobject1.getString("description"));
							Log.v("MainActiivty", "doInBackground :: wall_postby_name="+jobject1.getString("wall_postby_name"));
							Log.v("MainActiivty", "doInBackground :: wall_postby_image="+jobject1.getString("wall_postby_image"));
							Log.v("MainActiivty", "doInBackground :: created_date="+jobject1.getString("created_date"));
						*/
						
						}	
						
			
				Log.v("MainActiivty", "doInBackground :: productArrList="+productArrList.size());
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
			Log.v("MainActiivty", "onPostExecute :: productArrList="+productArrList.size());
			final ProductAdapter pAdpter = new ProductAdapter(_instance, productArrList,null, mActivity);
			runOnUiThread(new Runnable() {
			    public void run() {
			    	pAdpter.notifyDataSetChanged();
			    }
			});
			if(listView!=null){
				listView.setAdapter(pAdpter);
				pAdpter.notifyDataSetChanged();
				
			
				
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
		Intent i = new Intent(this, MyPadosi.class);
		Bundle bundle = new Bundle();
		bundle.putString("Token", TokenWall.toString());
		i.putExtras(bundle);
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
		Bundle bundle = new Bundle();
		bundle.putString("Token", TokenWall.toString());
		i.putExtras(bundle);
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
		Bundle bundle = new Bundle();
		bundle.putString("Token", TokenWall.toString());
		i.putExtras(bundle);
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
	public void sharePhotoIcon(View view) {
		Intent i = new Intent(this, SharePhoto.class);
		startActivity(i);
		Bundle bundle = new Bundle();
		bundle.putString("Token", TokenWall.toString());
		i.putExtras(bundle);
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
	public void ShareIcon(View view) {
		Intent i = new Intent(this, Share.class);
		startActivity(i);
		Bundle bundle = new Bundle();
		bundle.putString("Token", TokenWall.toString());
		i.putExtras(bundle);
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
	public ArrayList<Product> getArrList(){
		return productArrList;
	}
}
