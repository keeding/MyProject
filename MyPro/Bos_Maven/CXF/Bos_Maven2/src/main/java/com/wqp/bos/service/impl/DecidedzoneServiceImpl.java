package com.wqp.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wqp.bos.dao.DecidedzoneDao;
import com.wqp.bos.dao.SubareaDao;
import com.wqp.bos.domain.Decidedzone;
import com.wqp.bos.domain.Subarea;
import com.wqp.bos.page.PageBean;
import com.wqp.bos.service.DecidedzoneService;

@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService{
	@Autowired
	DecidedzoneDao decidedzoneDao;
	@Autowired
	private SubareaDao subareaDao;

	@Override
	public void add(Decidedzone decidedzone, String[] subareaId) {
		decidedzoneDao.save(decidedzone);
		for(String id : subareaId){
			Subarea subarea = subareaDao.findById(id);
			subarea.setDecidedzone(decidedzone);
		}
	}

	@Override
	public void pageQuery(PageBean pb) {
		decidedzoneDao.pageQuery(pb);
	}
}
