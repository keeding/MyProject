package com.wqp.bos.dao;

import java.util.List;

import com.wqp.bos.base.BaseDao;
import com.wqp.bos.domain.Function;

public interface FunctionDao extends BaseDao<Function> {

	List<Function> findByUserId(String userId);

	List<Function> findMenuByUserId(String userId);

	List<Function> findAllMenu();
}
