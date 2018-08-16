package com.wqp.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wqp.bos.dao.UserDao;
import com.wqp.bos.dao.base.impl.BaseDaoImpl;
import com.wqp.bos.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public User find(String name, String pwd) {
		List<User> list = (List<User>) this.getHibernateTemplate().find("from User where username=? and password=?", name, pwd);
		if(list != null && list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public User findUserByUsername(String username) {
		List<User> list = (List<User>) this.getHibernateTemplate().find("from User where username=?", username);
		if(list != null && list.size()>0)
			return list.get(0);
		return null;
	}
}
