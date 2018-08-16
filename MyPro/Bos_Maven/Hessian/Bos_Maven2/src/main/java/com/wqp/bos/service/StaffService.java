package com.wqp.bos.service;

import java.util.List;

import com.wqp.bos.domain.Staff;
import com.wqp.bos.page.PageBean;

public interface StaffService {

	void save(Staff staff);

	void pageQuery(PageBean pb);

	void deleteStaffs(String ids);

	void update(Staff staff);

	List<Staff> findListNotDelete();

}
