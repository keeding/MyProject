package com.wqp.bos.web.action;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wqp.bos.base.BaseAction;
import com.wqp.bos.domain.Noticebill;
import com.wqp.bos.domain.User;
import com.wqp.bos.utils.BosContext;

import cn.wqp.crm.domain.Customer;

@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	
	public String findCustomerByTelephone() throws IOException{
		Customer customer = customerService.findCustomerByTelephone(model.getTelephone());
		writeObjectJson(customer, new String[]{});
		return NONE;
	}
	
	public String add(){
		User user = BosContext.getUser();
		model.setUser(user);
		noticebillService.add(model);
		return "addUI";
	}
}
