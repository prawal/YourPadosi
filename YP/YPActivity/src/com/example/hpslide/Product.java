package com.example.hpslide;

import java.io.Serializable;


public class Product implements Serializable {

	private String wall_id;
	private String _productTitle;
	private String _productDesc;
	private String _productThumb;
	private String _productPubDate;
	private String _subject;
	private String wall_type;
	private String total_comment;
	private int total_likes;
	private String likeid;
	private String wallpostid;
	private String liketype;
	private String currentlikeuser;
	private String countLike;
	private String _productThumb1;

	
	
	public void setWallId(String wall_id){
		this.wall_id = wall_id;
	}
	
	public void setWallName(String _productTitle){
		this._productTitle = _productTitle;
	}
	
	public void setDescription(String _productDesc){
		this._productDesc = _productDesc;
	}
	
	public void setProductThumb(String _productThumb){
		this._productThumb = _productThumb;
	}
	
	
	
	public void setProductPubDate(String _productPubDate){
		this._productPubDate = _productPubDate;
	}
	public void setSubject(String _subject){
		this._subject = _subject;
	}
	public void setWallType(String wall_type){
		this.wall_type = wall_type;
	}
	public void settotalComment(String total_comment){
		this.total_comment = total_comment;
	}
	public void setlikePostUser(int length){
		this.total_likes = length;
	}
	public void setlikeid(String likeid) {
		this.likeid=likeid;
		
	}

	public void setliketype(String liketype) {
		this.liketype = liketype;
		
	}

	public void setwallpostid(String wallpostid) {
		this.wallpostid=wallpostid;
		
	}
	public void setcurrentlikeuser(String currentlikeuser) {
		this.currentlikeuser = currentlikeuser;
		
	}
	public void setcountlike(String countLike) {
		this.countLike = countLike;
		
	}
	public void setProductThumb1(String _productThumb1) {
		this._productThumb1 = _productThumb1;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<< getter method >>>>>>>>>>>>>>>>>*/
	
	public String getWallId(){
		return wall_id;
	}
	
	
	public String getsetWallName(){
		return _productTitle;
	}
	

	
	public String getProductDesc(){
		return _productDesc;
	}
	
	public  String getProductThumb(){
		return _productThumb;
	}
	
	public String getProductPubDate(){
		return _productPubDate;
	}
	public String getSubject(){
		return _subject;
	}
	public String getWallType(){
		return wall_type;
	}
	public String gettotalComment(){
		return total_comment;
	}
	public int getlikePostUser(){
		return total_likes;
	}
	public String getlikeid() {
		return likeid;
		
	}

	public String getliketype() {
		return liketype;
		
	}

	public String getwallpostid() {
	return wallpostid;
		
	}
	public String getcurrentlikeuser() {
		return currentlikeuser;
			
		}

	public String getcountlike() {
		return countLike ;
		
	}

	public String getProductThumb1(){
		return _productThumb1;
	}
	
	
}
