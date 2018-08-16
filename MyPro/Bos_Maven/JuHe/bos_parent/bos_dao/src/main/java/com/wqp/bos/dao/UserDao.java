package com.wqp.bos.dao;

import com.wqp.bos.dao.base.BaseDao;
import com.wqp.bos.domain.User;

public interface UserDao extends BaseDao<User>{

	User find(String name, String pwd);

	User findUserByUsername(String username);

}
