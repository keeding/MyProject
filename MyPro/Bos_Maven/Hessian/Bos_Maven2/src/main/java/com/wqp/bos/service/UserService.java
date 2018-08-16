package com.wqp.bos.service;

import com.wqp.bos.domain.User;
import com.wqp.bos.page.PageBean;

public interface UserService {
	
	User login(String name, String pwd);

	void editPassword(String password, String id);

	void add(User user, String[] roleIds);

	void pageQuery(PageBean pb);
}
