package com.wqp.bos.service;

import java.util.List;

import com.wqp.bos.domain.Region;
import com.wqp.bos.utils.PageBean;

public interface RegionService {

	void saveBatch(List<Region> list);

	void pageQuery(PageBean pb);

	List<Region> findAll();

}
