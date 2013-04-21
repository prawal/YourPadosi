package com.example.hpslide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.objectStorage.objectStorage;

public class ProductAdapter extends BaseAdapter {
	 String token=objectStorage.gettoken();
	  private List<ProductAdapter> items;
	private Context context;
	ArrayList<String> ids;
	int counter = 0;
	private String _stringVal;

	
	public ArrayList<Product> _productArrList;
	 int selected;
	  ScrollView mScrollView;
	  private TextView tv_category;
	
	public ProductAdapter(Context context,ArrayList<Product> _mproductArrList, ArrayList<String> ids, YPWall a){
		this.context= context;
		_productArrList=_mproductArrList;
		 YPWall mActivity = a;
		 ids =ids;
		 selected=_productArrList.size()+1;  
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _productArrList.size();
	}

	@Override
	public Object getItem(int position) {
		 notifyDataSetChanged();
		return position;
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public class Holder {
		private ImageView iv_productImage;
		private ImageView iv_productImage1;
		private TextView tv_productTitle;
		private TextView tv_category;
		private TextView tv_category1;
		private TextView tv_productPrice;
		private TextView tv_productlanguage;
		private TextView tv_productPubDate;
		private TextView tv_productLike;
		private TextView tv_productComment;
		private ProgressBar progress;
		final int counter = 0 ;
		private String _stringVal;
		private TextView button;
		private TextView button1;
		private TextView like;
		private TextView unlike;
		private EditText edit;
		  ScrollView mScrollView;
	
	
	
	}
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View)layoutInflater.inflate(R.layout.main_list_holder, null);
		Product productInstance = _productArrList.get(position);

		if(convertView == null){
			convertView = view;
	
			Product productInstance1 = _productArrList.get(position);
			Holder holder = new Holder();
			holder.iv_productImage = (ImageView)convertView.findViewById(R.id.iv_productImage);
			holder.iv_productImage1 = (ImageView)convertView.findViewById(R.id.iv_productImage2);
			holder.tv_productTitle = (TextView)convertView.findViewById(R.id.tv_productTitle);
			holder.tv_category = (TextView)convertView.findViewById(R.id.tv_category);
			holder.tv_productPrice = (TextView)convertView.findViewById(R.id.tv_productPrice);
		/*	holder.tv_productlanguage = (TextView)convertView.findViewById(R.id.tv_productlanguage);*/
			holder.tv_productPubDate = (TextView)convertView.findViewById(R.id.tv_productPubDate);
			holder.tv_productLike = (TextView)convertView.findViewById(R.id.tv_productLike);
			holder.tv_productComment = (TextView)convertView.findViewById(R.id.tv_productComment);
			holder.progress = (ProgressBar)convertView.findViewById(R.id.progress);
		    holder.button = (TextView) convertView.findViewById(R.id.commenttopado);
		    holder.button1 = (TextView)convertView.findViewById(R.id.commentlikebuttonid);
		 holder.like = (TextView) convertView.findViewById(R.id.likeid);
		   holder.unlike = (TextView) convertView.findViewById(R.id.unlikeid);

	
		   holder.unlike.setVisibility( TextView.GONE );
		    
		   holder.like.setTag(position);  
		   TextView  like  = (TextView)convertView.findViewById(R.id.likeid);
		   like.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	              
	             }
	     });
		
  	convertView.setTag(holder);
		}
		
  final Holder holder = (Holder)convertView.getTag();
		String productDesc = productInstance.getProductDesc();
		holder.tv_productTitle.setText(productInstance.getsetWallName());
		holder.tv_productPrice.setText(productInstance.getSubject());
		holder.tv_category.setText(productInstance.getProductDesc());
		holder.tv_productPubDate.setText(productInstance.getProductPubDate());
		holder.tv_productComment.setText(productInstance.gettotalComment());
		holder.tv_productLike.setText(String.valueOf(productInstance.getlikePostUser()));
	
			String image_url = "http://www.yourpadosi.com/"+ productInstance.getProductThumb();
			AQuery aq = new AQuery(context);
			aq.id(holder.iv_productImage).progress(holder.progress).image(image_url, true, true);
			String image_url1 = "http://www.yourpadosi.com/"+ productInstance.getProductThumb1();
			AQuery aq1 = new AQuery(context);
			aq.id(holder.iv_productImage1).image(image_url1, true, true);
			holder.like = (TextView) convertView.findViewById(R.id.likeid);
			holder.like.setTag( position);
	  
			 
				
			
holder.like.setOnClickListener(new View.OnClickListener() {        
		             public void onClick(View v) { 
		            

								holder.like.findViewById(R.id.likeid). setVisibility(TextView.INVISIBLE );
				            	
								holder.unlike.findViewById(R.id.unlikeid).setVisibility( TextView.VISIBLE); 
		            
		     
		            	  Product prod = _productArrList.get(position);
		           
		            		
		            		  String qty=	holder.tv_productLike.getText().toString(); 	
		            		  int mn=Integer.parseInt(qty);
		            		  int  qty1=mn+1;
		            		  
		            		
		            		  String s = new Integer(qty1).toString();
		            		  holder.tv_productLike.setText(""+qty1);
		            		
		           
		                 v.getTag(position);
		             
        
		            	Log.i("ConfirmAdapter ","Button postion  "+ position + "canceled item : " + _productArrList.get(position) );
		            	  new Thread(new Runnable() {
		            	        @Override
		            	public void run() {
		    			try {	HttpClient httpclient = new DefaultHttpClient();
		    			HttpResponse response;
		    			HttpPost httppost = new HttpPost("http://www.yourpadosi.com/rest/sendLikeAndroid/");
		    			
		    			Product prod = _productArrList.get(position);
		    			 
		    		
		    		
		                 String s = prod.getWallId().toString();
		                 String s1 = prod.getwallpostid().toString();
		           		 String s2= prod.getWallType().toString();
		         
		        
		               Log.v("wallid", s);
		           		 Log.v("wallpostid", s1);
		           		 Log.v("WallType", s2);
		    			 
		    	
		    		        	
		    		            JSONObject jsonobj;
		    		        	  jsonobj = new JSONObject();

		    		      		
		    		  			jsonobj.put("userToken", token);
		    		  			jsonobj.put("likeId", s);
		    		  			jsonobj.put("likeType", s2);
		    		  			jsonobj.put("wallPostByID", s1);
		    		        	
		    		        	  StringEntity se = new StringEntity(jsonobj.toString());
		    		          	//se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		    		          	se.setContentType("application/json;charset=UTF-8");
		    		          	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
		    		          	httppost.setEntity(se);
		    		          
		    		            // Execute HTTP Post Request
		    		            Log.w("YourPadosi", "Execute HTTP Post Request");
		    		       	 Log.i("ConfirmAdapter ","Button postion  "+ position + "canceled item : " + _productArrList.get(position) );
		    				response = httpclient.execute(httppost);
		    				if (response == null)
		    					return;
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
		    			   
		            	        } }).start();
		            	
		             }  });    
			         



holder. unlike.setOnClickListener(new View.OnClickListener() {        
		             public void onClick(View v) { 
		        	 v.getTag(position);
		        	 Product prod = _productArrList.get(position);

		        		
           		  String qty=	holder.tv_productLike.getText().toString(); 	
           		  int mn=Integer.parseInt(qty);
           	     int qty1=mn-1;
           	  holder.tv_productLike.setText(""+qty1);
                 String s = new Integer(qty1).toString();
                 holder.tv_productLike.setText(s);
           		
		      		holder.like.findViewById(R.id.likeid). setVisibility(TextView.VISIBLE );
	            	
					holder.unlike.findViewById(R.id.unlikeid).setVisibility( TextView.INVISIBLE);
	                
		            	Log.i("ConfirmAdapter ","Button postion  "+ position + "canceled item : " + _productArrList.get(position) );
		            	  new Thread(new Runnable() {
		            	        @Override
		            	public void run() {
		    			try {	HttpClient httpclient = new DefaultHttpClient();
		    			HttpResponse response;
		    			HttpPost httppost = new HttpPost("http://www.yourpadosi.com/rest/sendUnlikeAndroid/");
		    			
		    			Product prod = _productArrList.get(position);
		    			 
		    		
		    
		                 String s = prod.getWallId().toString();
		                 String s1 = prod.getwallpostid().toString();
		           		 String s2= prod.getWallType().toString();
		         
		        
		               Log.v("wallid", s);
		           		 Log.v("wallpostid", s1);
		           		 Log.v("WallType", s2);
		    			 
		    	
		    		        	
		    		            JSONObject jsonobj;
		    		        	  jsonobj = new JSONObject();

		    		      		
		    		  			jsonobj.put("userToken", token);
		    		  			jsonobj.put("likeId", s);
		    		  			jsonobj.put("likeType", s2);
		    		  			
		    		        	
		    		        	  StringEntity se = new StringEntity(jsonobj.toString());
		    		          	//se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		    		          	se.setContentType("application/json;charset=UTF-8");
		    		          	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
		    		          	httppost.setEntity(se);
		    		          
		    		            // Execute HTTP Post Request
		    		            Log.w("YourPadosi", "Execute HTTP Post Request");
		    				response = httpclient.execute(httppost);
		    				if (response == null)
		    					return;
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
		    			   
		            	        } }).start();
		            	
		            	
		        	  
		        	
		
		    	             }
		            	
		    	             }); 
				 
			 
		 holder.button.setOnClickListener(new View.OnClickListener() {        
             public void onClick(View v) {
            	 
            	 
        		  String qty=	holder.tv_productComment.getText().toString(); 	
        		  int mn=Integer.parseInt(qty);
        		  int  qty1=mn+1;
        		  
        		
        		  String s1 = new Integer(qty1).toString();
        		  holder.tv_productComment.setText(""+qty1);
        		  
        		  
            	  Intent intent = new Intent();
    	          intent.setClass(context, CommentPadosi.class);
    	          Product prod = _productArrList.get(position);
    	          String s = prod.getWallId().toString();
	        		 String s2= prod.getWallType().toString();
	        		 String s3 = prod.getsetWallName().toString();
	        		 String s4 = prod.getProductThumb().toString();
    	          intent.putExtra("product", prod); 
    	          intent.putExtra("productwall", s); 
    	          intent.putExtra("productwalltype", s2); 
    	          intent.putExtra("wallname", s3);
    	          intent.putExtra("productthum", s4);
    	    
	        		 Log.v("Walltype", s);
	        		 Log.v("Walltype", s2);
	        		 Log.v("Walltype", s3);
	        		 Log.v("Walltype", s4);
    	          context.startActivity(intent);
          
                 }       
         }); 

	  
	
		 holder.button1.setOnClickListener(new View.OnClickListener() {        
             public void onClick(View v) {   
            	  Intent intent = new Intent();
    	          intent.setClass(context, CommentshowPadosi.class);
    	          Product prod = _productArrList.get(position);
    	          String s = prod.getWallId().toString();
	        		 String s2= prod.getWallType().toString();
	        		 String s3 = prod.getsetWallName().toString();
	        		 String s4 = prod.getProductThumb().toString();
    	      
    	          intent.putExtra("productwall", s); 
    	          intent.putExtra("productwalltype", s2); 
    	          intent.putExtra("wallname", s3);
    	          intent.putExtra("productthum", s4);
    	    
	        		 Log.v("Walltype", s);
	        	
    	          context.startActivity(intent);
          
                 }       
         }); 
         

	return convertView ;

	
}
	
	}






