package com.wqp.bos.service.impl;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wqp.bos.dao.RoleDao;
import com.wqp.bos.dao.UserDao;
import com.wqp.bos.domain.Role;
import com.wqp.bos.domain.User;
import com.wqp.bos.page.PageBean;
import com.wqp.bos.service.UserService;
import com.wqp.bos.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private	UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private IdentityService identityService;

	@Transactional
	@Override
	public User login(String name, String pwd) {
		pwd = MD5Utils.md5(pwd);
		System.out.println(name+"========="+pwd);
		User userDb = userDao.find(name, pwd);
		return userDb;
	}
	

	@Override
	public void editPassword(String password, String id) {
		userDao.executeUpdate("editPassword", MD5Utils.md5(password), id);
	}


	@Override
	public void add(User user, String[] roleIds) {
		user.setPassword(MD5Utils.md5(user.getPassword()));
		userDao.save(user);
		org.activiti.engine.identity.User actUser = new UserEntity(user.getId());
		identityService.saveUser(actUser);
		//Activiti添加
		for(String roleId : roleIds){
			Role role = roleDao.findById(roleId);
			user.getRoles().add(role);
			//Activiti添加
			identityService.createMembership(actUser.getId(), role.getName());
		}
	}


	@Override
	public void pageQuery(PageBean pb) {
		userDao.pageQuery(pb);
	}
	
}
