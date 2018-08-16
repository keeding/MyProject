package com.wqp.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wqp.bos.dao.FunctionDao;
import com.wqp.bos.domain.Function;
import com.wqp.bos.domain.User;
import com.wqp.bos.page.PageBean;
import com.wqp.bos.service.FunctionService;
import com.wqp.bos.utils.BosContext;

@Service
@Transactional
public class FunctionServiceImple implements FunctionService {
	@Autowired
	private FunctionDao functionDao;

	@Override
	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	@Override
	public List<Function> findAll() {
		return functionDao.findAll();
	}

	@Override
	public void add(Function model) {
		Function function = model.getFunction();
		if(function != null && function.getId().equals("")){
			model.setFunction(null);
		}
		functionDao.save(model);
	}

	@Override
	public List<Function> findMenu() {
		User user = BosContext.getUser();
		if("admin".equals(user.getUsername())){
			return functionDao.findAllMenu();
		}else{
			return functionDao.findMenuByUserId(user.getId());
		}
	}
}
