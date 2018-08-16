package com.wqp.bos.dao;

import java.util.List;

import com.wqp.bos.base.BaseDao;
import com.wqp.bos.domain.Subarea;

public interface SubareaDao extends BaseDao<Subarea>{

	List<Object> findCharts();

}
