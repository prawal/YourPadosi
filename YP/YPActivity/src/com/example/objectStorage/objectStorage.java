package com.example.objectStorage;

import android.R.string;

public class objectStorage {

	String response;
	static String token;
	String userid;
	static String fullname;
	String username;
	String email;
	String password;
	String wallPost;
	String subject;
	String description;
	String comment;
	String date;
	static String userImage;
	private static String count;

	public objectStorage(){
		 
    }
    // constructor
    public objectStorage(String id, String name, String token, String userImage){
        this.userid = id;
        this.username = name;
        this.token = token;
        this.userImage = userImage;
    }
 
    // constructor
    public objectStorage(String name, String token, String userImage){
    	  this.username = name;
    	  this.token = token;
          this.userImage = userImage;
    }
	public String getresponse()
	{
		return response;
	}
	
	public void setresponse(String responseget)
	{
		this.response=responseget;
	}
	
	public static String gettoken()
	{
		return token;
	}
	
	public void settoken(String tokenget)
	{
		this.token=tokenget;
	}
	
	public String getuserid()
	{
		return userid;
	}
	
	public void setuserid(String useridget)
	{
		this.userid=useridget;
	}
	
	public static String getfullname()
	{
		return fullname;
	}
	
	public void setfullname(String fullnameget)
	{
		this.fullname=fullnameget;
	}
	
	public String getusername()
	{
		return username;
	}
	
	public void setusername(String usernameget)
	{
		this.username=usernameget;
	}
	
	public String getemail()
	{
		return email;
	}
	
	public void setemail(String emailget)
	{
		this.email=emailget;
	}
	
	public String getpassword()
	{
		return password;
	}
	
	public void setpassword(String password)
	{
		this.password=password;
	}

	public void setwallpost(String wallPostget) {
		this.wallPost= wallPostget;
		
	}
	public String getwallpost(){
		return wallPost;
	}

	public void setsubject(String subjectget) {
		this.subject = subjectget;
		
	}
	public String getsubject(){
		return subject;
	}

	public void setdescription(String descriptionget) {
		this.description = descriptionget;
		
	}
	public String getdescription(){
		return description;
	}

	public void setcomment(String commentget) {

		this.comment = commentget;
	}
	public String getcomment(){
		return comment;
	}

	public void setcreatedDate(String createdDateget) {
	this.date=createdDateget;
		
	}
	public String getcreatedDate(){
		return date;
	}
	public void setuserImage(String userImage) {
		this.userImage=userImage;
			
		}
		public static String getuserImage(){
			return userImage;
		}
		public void setcount(String count) {
			this.count = count;
			
		}
		public static String getcount() {
			return count;
				
			}
}
