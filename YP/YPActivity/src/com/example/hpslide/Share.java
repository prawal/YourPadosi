package com.example.hpslide;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class Share extends Activity {
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
		private Bitmap image;
		private ImageView imageView1;
		private Button buttonNewPic;
		private Button buttonImage;
		private static final int IMAGE_PICK 	= 1;
		private static final int IMAGE_CAPTURE 	= 2;
	String Subject;
	String body;
	 String s=objectStorage.gettoken();
	 Button post;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 LayoutInflater inflater = LayoutInflater.from(this);
		  scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.horz_scroll_with_list_menu, null);
	        setContentView(scrollView);
	   
	        app = inflater.inflate(R.layout.share, null);
	        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar5);
	        menu = inflater.inflate(R.layout.horz_scroll_menu, null);

	      Button  post = (Button)findViewById(R.id.wallPost);

  logout= (Button)findViewById(R.id.logout);
  PadosiWall = (Button)findViewById(R.id.padosiwallbutton);
  myPadosibutton = (Button)findViewById(R.id.myPadosi);
   btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide5);
   this.buttonNewPic 	= (Button) this.findViewById(R.id.cameraButton);
	 TextView txt = (TextView)app.findViewById(R.id.UserName);
	 txt.setText(objectStorage.getfullname());
   
	String image_url = "http://www.yourpadosi.com/"+ objectStorage.getuserImage();
	 ImageView img = (ImageView)app.findViewById(R.id.UserImage);
	
		AQuery aq = new AQuery(context);
		aq.id(img).image(image_url, true, true);
/*   this.buttonImage 	= (Button) this.findViewById(R.id.Uploadimagegallery);*/

   this.imageView1 		= (ImageView) this.findViewById(R.id.captureimage);
	   btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));

	        final View[] children = new View[] { menu, app };

	        // Scroll to app (view[1]) when layout finished.
	        int scrollToViewIdx = 1;
	        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
	   
	    }
	 public void onClickwallpost(View view) {
		new SharePostData().execute();
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
	 
	 class SharePostData extends AsyncTask<Void, Void, Void> {

			ProgressDialog pDialog_data;

			@Override
			protected void onPreExecute() {
				pDialog_data = ProgressDialog.show(Share.this, null,
						"Authenticating...".toString(), true,
				true, new DialogInterface.OnCancelListener() {
					
					public void onCancel(DialogInterface arg0) {
						SharePostData.this.cancel(true);
					}
				});
			
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;
		
				try {
				HttpPost httppost = new HttpPost("http://yourpadosi.com/rest/sendWallAndroid/");
			

	
			    EditText subj = (EditText)findViewById(R.id.bodySubject);
			  	 Subject = subj.getText().toString();
			 
				 String s=objectStorage.gettoken();
			EditText bodi = (EditText)findViewById(R.id.bodyshare);
			body = bodi.getText().toString();
			      
			        Log.d("Mybody", body);
			        Log.d("Mybody", Subject);
			        Log.d("Mybody", s);
			            JSONObject jsonobj;
			        	  jsonobj = new JSONObject();
			        	 
							jsonobj.put("subject", Subject);
							jsonobj.put("body", body);
							jsonobj.put("userToken", s);
						jsonobj.put("file_data", "");
			        	
			        	  StringEntity se = new StringEntity(jsonobj.toString());
			          	//se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			          	se.setContentType("application/json;charset=UTF-8");
			          	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
			          	httppost.setEntity(se);
			          
			            // Execute HTTP Post Request
			            Log.w("YourPadosi", "Execute HTTP Post Request");
					response = httpclient.execute(httppost);
			    
					HttpEntity entity = response.getEntity();
					String is = EntityUtils.toString(entity);

Log.d("ismy", is);
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

	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	
	    	if (resultCode == Activity.RESULT_OK) { 
		    	switch (requestCode) {
				case IMAGE_PICK:	
					this.imageFromGallery(resultCode, data);
					break;
				case IMAGE_CAPTURE:
					this.imageFromCamera(resultCode, data);
					break;
				default:
					break;
				}
	    	}
	    }
	    
	    /**
	     * Update the imageView with new bitmap
	     * @param newImage
	     */
	    private void updateImageView(Bitmap newImage) {
	    	BitmapProcessor bitmapProcessor = new BitmapProcessor(newImage, 1000, 1000, 90);
	    	
	    	this.image = bitmapProcessor.getBitmap();
	    	this.imageView1.setImageBitmap(this.image);
	    }
	    
	    /**
	     * Image result from camera
	     * @param resultCode
	     * @param data
	     */
	    private void imageFromCamera(int resultCode, Intent data) {
	    	Bitmap bitmap =((Bitmap) data.getExtras().get("data"));

	    	imageView1 		= (ImageView) this.findViewById(R.id.captureimage);
	    	imageView1.setImageBitmap(bitmap);
	    }
	    
	    /**
	     * Image result from gallery
	     * @param resultCode
	     * @param data
	     */
	    private void imageFromGallery(int resultCode, Intent data) {
	    	Uri selectedImage = data.getData();
	    	String [] filePathColumn = {MediaStore.Images.Media.DATA};
	    	
	    	Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	    	cursor.moveToFirst();
	    	
	    	int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	    	String filePath = cursor.getString(columnIndex);
	    	cursor.close();
	    	
	    	this.updateImageView(BitmapFactory.decodeFile(filePath));
	    }
	    
	    /**
	     * Click Listener for selecting images from phone gallery
	     * @author tscolari
	     *
	     */
	 
			/*public void camerag(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "Escolha uma Foto"), IMAGE_PICK);
				
			}
	  
	    */
	    /**
	     * Click listener for taking new picture
	     * @author tscolari
	     *
	     */
	 
			public void camerab(View v) {
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, IMAGE_CAPTURE);
				
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
			
			
			public void oncancel(View view) {
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
