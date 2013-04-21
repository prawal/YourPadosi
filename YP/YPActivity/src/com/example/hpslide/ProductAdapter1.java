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

public class ProductAdapter1 extends BaseAdapter {
	private Context context;
	public ArrayList<Product1> _productArrList1;
	public ProductAdapter1(Context context,ArrayList<Product1> _mproductArrList1){
		this.context= context;
		_productArrList1=_mproductArrList1;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _productArrList1.size();
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
		private ImageView iv_padosiImage;
		private TextView tv_padosiname;
		private ProgressBar progress;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View)layoutInflater.inflate(R.layout.mypadosi_list_holder, null);
		Product1 productInstance = _productArrList1.get(position);
		
		if(convertView == null){
			convertView = view;
			Holder holder = new Holder();
			holder.iv_padosiImage = (ImageView)convertView.findViewById(R.id.iv_padosiImage);
		
			holder.tv_padosiname = (TextView)convertView.findViewById(R.id.tv_padosiname);
			
			convertView.setTag(holder);
		}
		Holder holder = (Holder)convertView.getTag();
	
		holder.tv_padosiname.setText(productInstance.getpadosiname());
	
		String image_url = "http://www.yourpadosi.com/"+ productInstance.getpadosiimage();
		AQuery aq = new AQuery(context);
		aq.id(holder.iv_padosiImage).progress(holder.progress).image(image_url, true, true);

		return convertView;
	}
	
	
}
	 

