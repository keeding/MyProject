package com.wqp.bos.service;

import java.util.List;

import com.wqp.bos.domain.Subarea;
import com.wqp.bos.utils.PageBean;

public interface SubareaService {

	void pageQuery(PageBean pb);

	void save(Subarea model);

	List<Subarea> findAll();

	List<Subarea> findListNotAssociation();

	List<Object> findCharts();

}
