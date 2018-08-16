package com.wqp.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wqp.bos.domain.Workordermanage;
import com.wqp.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage>{
	public String add() throws IOException {
		String flag = "1";
		try{
			workordermanageService.save(model);
		}catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	public String list(){
		List<Workordermanage> list = workordermanageService.findListNotStart();
		set("list", list);
		return "list";
	}
	public String start(){
		workordermanageService.start(model.getId());
		return NONE;
	}
}
