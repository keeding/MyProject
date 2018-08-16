package com.wqp.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport{
	
	@Autowired
	private RepositoryService repositoryService;
	
	private File fileZip;
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public void setFileZip(File fileZip) {
		this.fileZip = fileZip;
	}

	public String list(){
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.latestVersion();
		query.orderByProcessDefinitionName().desc();
		List<ProcessDefinition> list = query.list();
		ServletActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	@InputConfig(resultName="error")
	public String deploy() throws FileNotFoundException{
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.addZipInputStream(new ZipInputStream(new FileInputStream(fileZip)));
		deploymentBuilder.deploy();
		return "toList";
	}
	
	public String viewpng(){
		InputStream inputStream = repositoryService.getProcessDiagram(id);
		ServletActionContext.getContext().getValueStack().set("pngStream", inputStream);
		return "viewpng";
	}
	
	public String delete(){
		String deltag = "0";
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.processDefinitionId(id);
		ProcessDefinition processDefinition = query.singleResult();
		String deploymentId = processDefinition.getDeploymentId();
		try {
			repositoryService.deleteDeployment(deploymentId);
		} catch (Exception e) {
			deltag = "1";
			ServletActionContext.getContext().getValueStack().set("deltag", deltag);
			ProcessDefinitionQuery query2 = repositoryService.createProcessDefinitionQuery();
			query2.latestVersion();
			query2.orderByProcessDefinitionName().desc();
			List<ProcessDefinition> list = query2.list();
			ServletActionContext.getContext().getValueStack().set("list", list);
			return "list";
		}
		return "toList";
	}
}
