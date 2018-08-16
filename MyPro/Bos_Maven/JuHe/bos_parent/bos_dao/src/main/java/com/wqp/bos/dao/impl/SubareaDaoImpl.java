package com.wqp.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wqp.bos.dao.SubareaDao;
import com.wqp.bos.dao.base.impl.BaseDaoImpl;
import com.wqp.bos.domain.Subarea;

@Repository
public class SubareaDaoImpl extends BaseDaoImpl<Subarea> implements SubareaDao{

	@Override
	public List<Object> findCharts() {
		String hql = "select r.province,count(*) from Subarea s left outer join s.region r group by r.id";
		List<Object> list = (List<Object>) this.getHibernateTemplate().find(hql);
		return list;
	}

}
