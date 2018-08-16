package com.wqp.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wqp.bos.base.impl.BaseDaoImpl;
import com.wqp.bos.dao.FunctionDao;
import com.wqp.bos.domain.Function;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao {

	@Override
	public List<Function> findByUserId(String userId) {
		String hql = "select distinct f from Function f left outer join f.roles r left outer join r.users u where u.id=?";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, userId);
		if(list != null){
			return (List<Function>) list;
		}
		return null;
	}

	@Override
	public List<Function> findAllMenu() {
		String hql = "select distinct f from Function f left outer join f.roles r left outer join r.users u where f.generatemenu='1' order by f.zindex desc";
		return (List<Function>) this.getHibernateTemplate().find(hql);
	}
	
	@Override
	public List<Function> findMenuByUserId(String userId) {
		String hql = "select distinct f from Function f left outer join f.roles r left outer join r.users u where u.id=? and f.generatemenu='1' order by f.zindex desc";
		List<?> list = this.getHibernateTemplate().find(hql, userId);
		if(list != null){
			return (List<Function>) list;
		}
		return null;
	}
	
}
