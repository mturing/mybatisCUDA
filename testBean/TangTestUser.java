package com.assist.database.testBean;

import com.assist.database.ourinterface.Column;
import com.assist.database.ourinterface.Table;

@Table
public class TangTestUser {
	@Column private Integer id;
	@Column private String userName;
	@Column private String password;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "TangTestUser [id=" + id + ", userName=" + userName
				+ ", password=" + password + "]";
	}
	
	
}
