package com.example.hpslide;

import java.io.Serializable;

public class Product3 implements Serializable {
	private String comment_name;
	private String comment_image;
	private String comment_data;
	private String comment_date;
	private String comment_id;
	
	public void setcommentshowname(String comment_name){
		this.comment_name = comment_name;
	}
	
	public void setcommentimage(String comment_image){
		this.comment_image = comment_image;
	}
	
	public void setcommentshowdata(String comment_data){
		this.comment_data = comment_data;
	}
	
	public void setcommentdate(String comment_date){
		this.comment_date = comment_date;
	}
	public void setcommentpostid(String comment_id) {
		this.comment_id = comment_id;
		
	}
	////////////////////////////getter/////////////
	
	public String getcommentshowname(){
		return comment_name;
	}

	
	
	public String getcommentimage(){
		return comment_image;
	}
	

	
	public String getcommentshowdata(){
		return comment_data;
	}
	
	public  String getcommentdate(){
		return comment_date;
	}

	
}
