package com.example.hpslide;

import java.io.Serializable;


public class Product2 implements Serializable {

	private String full_name;
	private String user_image;
	private String padosi_id;
	private static String pasdosi_img;
	private String padosi_name;
	private static String resident_since;
	private static String about_padosi;
	
	
	public void setfullname(String full_name){
		this.full_name = full_name;
	}
	
	public void setUserImage(String user_image){
		this.user_image = user_image;
	}
	
	public void setPadosiID(String padosi_id){
		this.padosi_id = padosi_id;
	}
	
	public void setPadosiImage(String pasdosi_img){
		this.pasdosi_img = pasdosi_img;
	}
	
	
	
	public void setPadosiName(String padosi_name){
		this.padosi_name = padosi_name;
	}
	public void setResidence(String resident_since){
		this.resident_since = resident_since;
	}
	public void setAboutPadosi(String about_padosi){
		this.about_padosi = about_padosi;
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<< getter method >>>>>>>>>>>>>>>>>*/
	
	public String getfullname(){
		return full_name;
	}
	
	
	public String getUserImage(){
		return user_image;
	}
	

	
	public String getPadosiID(){
		return padosi_id;
	}
	
	public static  String getPadosiImage(){
		return pasdosi_img;
	}
	
	public String getPadosiName(){
		return padosi_name;
	}
	public static String getResidence(){
		return resident_since;
	}
	public static String getAboutPadosi(){
		return about_padosi;
	}

	
}
