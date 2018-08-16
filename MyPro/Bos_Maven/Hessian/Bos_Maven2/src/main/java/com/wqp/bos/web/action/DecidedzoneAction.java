package com.wqp.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wqp.bos.base.BaseAction;
import com.wqp.bos.domain.Decidedzone;

import cn.wqp.crm.domain.Customer;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	
	private String[] subareaId;
	private Integer[] customerIds;

	public String pageQuery() throws IOException{
		decidedzoneService.pageQuery(pb);
		writePageBean2Json(pb, new String[]{"subareas","decidedzones","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	public String add(){
		decidedzoneService.add(model, subareaId);
		return "list";
	}
	
	// 未关联定区客户
	public String findnoassociationCustomers() throws IOException{
		List<Customer> list = customerService.findnoassociationCustomers();
		writeListJson(list, new String[]{});
		return NONE;
	}
	
	// 查询已经关联指定定区的客户
	public String findhasassociationCustomers() throws IOException{
		List<Customer> list = customerService.findhasassociationCustomers(model.getId());
		writeListJson(list, new String[]{});
		return NONE;
	}
	
	// 将未关联定区客户关联到定区上
	public String assignCustomersToDecidedZone(){
		customerService.assignCustomersToDecidedZone(customerIds, model.getId());
		return "list";
	}
	

	public Integer[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}

	public void setSubareaId(String[] subareaId) {
		this.subareaId = subareaId;
	}
}
