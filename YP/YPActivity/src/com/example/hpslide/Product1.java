package com.example.hpslide;

import java.io.Serializable;

public class Product1 implements Serializable{
	private  String padosi_id;
	private String _padosiimage;
	private String _padosiname;
	 
	public void setpadosiid(String padosi_id){
		this.padosi_id = padosi_id;
	}
	
	public void setpadosiimage(String _padosiimage){
		this._padosiimage = _padosiimage;
	}
	
	public void setpadosiname(String _padosiname){
		this._padosiname = _padosiname;
	}
//////////////getter method
	public  String getpadosiid(){
		return padosi_id;
	}
	

	
	public String getpadosiimage(){
		return _padosiimage;
	}
	
	public  String getpadosiname(){
		return _padosiname;
	}
	
}
