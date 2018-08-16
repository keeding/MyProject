package com.wqp.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wqp.bos.dao.RegionDao;
import com.wqp.bos.domain.Region;
import com.wqp.bos.page.PageBean;
import com.wqp.bos.service.RegionService;

@Service
@Transactional
public class RegionServiceImpl implements RegionService{
	@Autowired
	private RegionDao regionDao;

	@Override
	public void saveBatch(List<Region> list) {
		for(Region r : list){
			regionDao.saveOrUpdate(r);
		}
	}

	@Override
	public void pageQuery(PageBean pb) {
		regionDao.pageQuery(pb);
	}

	@Override
	public List<Region> findAll() {
		List<Region> list = regionDao.findAll();
		return list;
	}
}
