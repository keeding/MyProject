package com.wqp.bos.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class ProcessInstanceAction extends ActionSupport {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	
	private String id;
	private String deploymentId;
	private String imageName;
	public void setId(String id) {
		this.id = id;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String list(){
		ExecutionQuery executionQuery = runtimeService.createExecutionQuery();
		executionQuery.orderByProcessDefinitionId().desc();
		List<Execution> list = executionQuery.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	public String findData() throws IOException{
		Map<String, Object> map = runtimeService.getVariables(id);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(map.toString());
		return NONE;
	}
	
	public String showPng(){
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
		String processDefinitionId = processInstance.getProcessDefinitionId();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		
		deploymentId = processDefinition.getDeploymentId();
		imageName = processDefinition.getDiagramResourceName();
		
		String activityId = processInstance.getActivityId();
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		ActivityImpl activiti = pd.findActivity(activityId);
		int x = activiti.getX();
		int y = activiti.getY();
		int width = activiti.getWidth();
		int height = activiti.getHeight();
		ActionContext.getContext().getValueStack().set("x", x);
		ActionContext.getContext().getValueStack().set("y", y);
		ActionContext.getContext().getValueStack().set("width", width);
		ActionContext.getContext().getValueStack().set("height", height);
		return "showPng";
	}
	
	public String viewImage(){
		InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("imgStream", inputStream);
		return "viewImage";
	}
}
