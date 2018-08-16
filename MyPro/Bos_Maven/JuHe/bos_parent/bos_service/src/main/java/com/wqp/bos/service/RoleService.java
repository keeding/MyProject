package com.wqp.bos.service;

import java.util.List;

import com.wqp.bos.domain.Role;
import com.wqp.bos.utils.PageBean;

public interface RoleService {
	void pageQuery(PageBean pageBean);

	void add(Role role, String ids);

	List<Role> findAll();
}
