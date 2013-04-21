package com.example.hpslide;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;

public class ProductAdapter2 extends BaseAdapter {
	private Context context;
	public ArrayList<Product2> _productArrList2;
	public ProductAdapter2(Context context,ArrayList<Product2> _mproductArrList2){
		this.context= context;
		_productArrList2=_mproductArrList2;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _productArrList2.size();
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
		private ImageView iv_padosiImage2;
		private TextView tv_padosiname2;
		private ProgressBar progress2;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View)layoutInflater.inflate(R.layout.mypadosidetail_list_holder, null);
		Product2 productInstance = _productArrList2.get(position);
		
		if(convertView == null){
			convertView = view;
			Holder holder = new Holder();
			holder.iv_padosiImage2 = (ImageView)convertView.findViewById(R.id.iv_padosiImage);
		
			holder.tv_padosiname2 = (TextView)convertView.findViewById(R.id.tv_padosiname);
			
			convertView.setTag(holder);
		}
		Holder holder = (Holder)convertView.getTag();
	
		holder.tv_padosiname2.setText(productInstance.getAboutPadosi());
	
		String image_url = "http://www.yourpadosi.com/"+ productInstance.getPadosiImage();
		AQuery aq = new AQuery(context);
		aq.id(holder.iv_padosiImage2).progress(holder.progress2).image(image_url, true, true);

		return convertView;
	}
	
	
}
	 
