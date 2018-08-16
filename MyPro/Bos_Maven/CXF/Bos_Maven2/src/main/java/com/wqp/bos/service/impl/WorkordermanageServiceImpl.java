package com.wqp.bos.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wqp.bos.dao.WorkordermanageDao;
import com.wqp.bos.domain.Workordermanage;
import com.wqp.bos.service.WorkordermanageService;

@Service
@Transactional
public class WorkordermanageServiceImpl implements WorkordermanageService {

	@Autowired
	private WorkordermanageDao workordermanageDao;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;

	@Override
	public void save(Workordermanage model) {
		model.setUpdatetime(new Date());
		workordermanageDao.save(model);		
	}

	@Override
	public List<Workordermanage> findListNotStart() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Workordermanage.class);
		detachedCriteria.add(Restrictions.eq("start", "0"));
		List<Workordermanage> list = workordermanageDao.findAll(detachedCriteria);
		return list;
	}

	@Override
	public void start(String id) {
		Workordermanage workordermanage = workordermanageDao.findById(id);
		workordermanage.setStart("1");
		String processDefinitionKey = "transfer";
		String businessKey = id;
		Map<String,Object> variables = new HashMap<>();
		variables.put("业务数据", workordermanage);
		runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
	}

	@Override
	public Workordermanage findById(String businessKey) {
		Workordermanage workordermanage = workordermanageDao.findById(businessKey);
		return workordermanage;
	}

	@Override
	public void checkWorkordermanage(String taskId, Integer check, String workordermanageId) {
		Workordermanage workordermanage = workordermanageDao.findById(workordermanageId);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		Map<String,Object> variables = new HashMap<String, Object>();
		variables.put("check", check);
		String processInstanceId = task.getProcessInstanceId();
		taskService.complete(taskId, variables);
		if(check == 0){
			workordermanage.setStart("0");
			historyService.deleteHistoricProcessInstance(processInstanceId);
		}
	}
}
