package com.example.hpslide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class MyGallery extends Activity {
	
	private static int RESULT_LOAD_IMAGE = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		
		Intent i = new Intent(
				Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		
		startActivityForResult(i, RESULT_LOAD_IMAGE);
		View viewButton = findViewById(R.id.backMain);
		
		viewButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MyGallery.this,
						YPWall.class);
				MyGallery.this.startActivity(intent);
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
		});
		 View viewButton8 = findViewById(R.id.Back2);
			
			viewButton8.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent intent = new Intent(MyGallery.this,
						Message.class);
					MyGallery.this.startActivity(intent);
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
			});
	/*	GridView gridView = (GridView) findViewById(R.id.grid_view);
		
		// Instance of ImageAdapter Class
		gridView.setAdapter(new ImageAdapter(this));

		*//**
		 * On Click event for Single Gridview Item
		 * *//*
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				// Sending image id to FullScreenActivity
				Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
				// passing array index
				i.putExtra("id", position);
				startActivity(i);
			}
		});*/
	}
	  @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	super.onActivityResult(requestCode, resultCode, data);
	    	
			if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				
				ImageView imageView = (ImageView) findViewById(R.id.imgView);
				imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			
			}
	    
	    
	    }
}
		
		
	
