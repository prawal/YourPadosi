package com.example.hpslide;

import java.util.ArrayList;

import com.androidquery.AQuery;
import com.example.hpslide.ProductAdapter1.Holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProductAdapter3 extends BaseAdapter {
	private Context context;
	public ArrayList<Product3> _productArrList3;
	public ProductAdapter3(Context context,ArrayList<Product3> _mproductArrList3){
		this.context= context;
		_productArrList3=_mproductArrList3;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _productArrList3.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public class Holder {
		private ImageView iv_padosiCommentImage ;
		private TextView tv_padosiCommentname;
		private TextView tv_padosiCommentshow;
		private TextView tv_padosiCommentdate;
		private ProgressBar progress;
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View)layoutInflater.inflate(R.layout.commentlistshowadapter, null);
		Product3 productInstance = _productArrList3.get(position);
		
		if(convertView == null){
			convertView = view;
			Holder holder = new Holder();
			holder.iv_padosiCommentImage = (ImageView)convertView.findViewById(R.id.iv_padosiCommentshowImage);
			holder.tv_padosiCommentname = (TextView)convertView.findViewById(R.id.tv_padosicommentshowname);
			holder.tv_padosiCommentshow = (TextView)convertView.findViewById(R.id.tv_padosicommentshow);
			holder.tv_padosiCommentdate = (TextView)convertView.findViewById(R.id.tv_padosicommentdateshow);
			
			convertView.setTag(holder);
		}
		Holder holder = (Holder)convertView.getTag();


		holder.tv_padosiCommentname.setText(productInstance.getcommentshowname());
		holder.tv_padosiCommentshow.setText(productInstance.getcommentshowdata());
	holder.tv_padosiCommentdate.setText(productInstance.getcommentdate());
		String image_url = "http://www.yourpadosi.com/"+ productInstance.getcommentimage();
	AQuery aq = new AQuery(context);
		aq.id(holder.iv_padosiCommentImage).progress(holder.progress).image(image_url, true, true);

		return convertView;
	}
	
	
}
	 
	
	

