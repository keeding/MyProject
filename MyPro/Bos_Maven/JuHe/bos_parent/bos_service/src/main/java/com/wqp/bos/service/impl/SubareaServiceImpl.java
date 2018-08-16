package com.wqp.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wqp.bos.dao.SubareaDao;
import com.wqp.bos.domain.Subarea;
import com.wqp.bos.service.SubareaService;
import com.wqp.bos.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {
	@Autowired
	private SubareaDao subareaDao;

	@Override
	public void pageQuery(PageBean pb) {
		subareaDao.pageQuery(pb);
	}

	@Override
	public void save(Subarea model) {
		subareaDao.save(model);
	}

	@Override
	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

	@Override
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		List<Subarea> list = subareaDao.findAll(detachedCriteria);
		return list;
	}

	@Override
	public List<Object> findCharts() {
		List<Object> list = subareaDao.findCharts();
		return list;
	}

}
