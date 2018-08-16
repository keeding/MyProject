package com.wqp.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wqp.bos.dao.StaffDao;
import com.wqp.bos.domain.Staff;
import com.wqp.bos.page.PageBean;
import com.wqp.bos.service.StaffService;

@Service
@Transactional
public class StaffServiceImpl implements StaffService{
	@Autowired
	private StaffDao staffDao;
	
	@Override
	public void save(Staff staff) {
		staffDao.save(staff);
	}


	@Override
	public void pageQuery(PageBean pb) {
		staffDao.pageQuery(pb);
	}


	@Override
	public void deleteStaffs(String ids) {
		String[] idsArgs = ids.split(",");
		for(String id : idsArgs)
			staffDao.executeUpdate("staff.delete", id);
	}


	@Override
	public void update(Staff staff) {
		Staff staffDb = staffDao.findById(staff.getId());
		staffDb.setName(staff.getName());
		staffDb.setTelephone(staff.getTelephone());
		staffDb.setStation(staff.getStation());
		staffDb.setHaspda(staff.getHaspda());
		staffDb.setStandard(staff.getStandard());
	}


	@Override
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		detachedCriteria.add(Restrictions.ne("deltag", "1"));
		return staffDao.findAll(detachedCriteria);
	}
}
