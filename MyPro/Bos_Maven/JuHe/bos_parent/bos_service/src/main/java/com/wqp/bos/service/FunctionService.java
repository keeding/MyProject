package com.wqp.bos.service;

import java.util.List;

import com.wqp.bos.domain.Function;
import com.wqp.bos.utils.PageBean;

public interface FunctionService {
	
	void pageQuery(PageBean pageBean);

	List<Function> findAll();

	void add(Function model);

	List<Function> findMenu();

}
