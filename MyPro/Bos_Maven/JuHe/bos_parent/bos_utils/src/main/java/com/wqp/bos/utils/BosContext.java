package com.wqp.bos.utils;

import org.apache.struts2.ServletActionContext;

import com.wqp.bos.domain.User;

public class BosContext {
	public static User getUser(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		return user;
	}
}
