package com.mycom.myapp.user.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
	@JsonProperty(required = false)
	private Integer userSeq;
	private String userName;
	private String userPassword;
	private String userEmail;

	private Date userRegisterDate;
	private String salt;
	private String newPassword;

	public String getNewPassword() {
	    return newPassword;
	}

	public void setNewPassword(String newPassword) {
	    this.newPassword = newPassword;
	}

	public String getSalt() {
		return salt;
	}


	
	
	
	public Integer getUserSeq() {
		return userSeq;
	}


	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getUserRegisterDate() {
		return userRegisterDate;
	}
	public void setUserRegisterDate(Date userRegisterDate) {
		this.userRegisterDate = userRegisterDate;
	}
	



	public void setSalt(String salt) {
		this.salt = salt;
	}


	


	
}

