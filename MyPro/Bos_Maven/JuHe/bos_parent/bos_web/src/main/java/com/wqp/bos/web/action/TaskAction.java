package com.wqp.bos.web.action;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wqp.bos.domain.User;
import com.wqp.bos.domain.Workordermanage;
import com.wqp.bos.service.WorkordermanageService;
import com.wqp.bos.utils.BosContext;

@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport{
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private WorkordermanageService workordermanageService;
	
	private String taskId;
	private Integer check;
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}


	public String findGroupTask(){
		TaskQuery taskQuery = taskService.createTaskQuery();
		String id = BosContext.getUser().getId();
		taskQuery.taskCandidateUser(id);
		List<Task> list = taskQuery.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "groupTaskList";
	}
	
	public String takeTask(){
		User user = BosContext.getUser();
		taskService.claim(taskId, user.getId());
		return "takeTask";
	}
	
	public String findPersonalTask(){
		String userId = BosContext.getUser().getId();
		TaskQuery query = taskService.createTaskQuery();
		query.taskAssignee(userId);
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "personalTaskList";
	}
	
	public String checkWorkOrderManage(){
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		String workordermanageId = processInstance.getBusinessKey();
		Workordermanage workordermanage = workordermanageService.findById(workordermanageId);
		if(check == null){
			ActionContext.getContext().getValueStack().set("map", workordermanage);
			ActionContext.getContext().getValueStack().set("taskId", taskId);
			return "check";
		}else{
			workordermanageService.checkWorkordermanage(taskId,check,workordermanageId);
			return NONE;
		}
	}
}
