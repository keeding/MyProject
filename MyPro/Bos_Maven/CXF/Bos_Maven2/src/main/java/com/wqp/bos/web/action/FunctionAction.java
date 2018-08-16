package com.wqp.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wqp.bos.base.BaseAction;
import com.wqp.bos.domain.Function;
import com.wqp.bos.domain.User;

@Controller("functionAction")
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
	public String pageQuery() throws IOException{
		String page = model.getPage();
		pb.setCurrentPage(Integer.valueOf(page));
		functionService.pageQuery(pb);
		writePageBean2Json(pb,new String[]{"function","functions","roles","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	public String listRootFunction() throws IOException{
		List<Function> list = functionService.findAll();
		writeListJson(list, new String[]{"function","functions","roles","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	public String add(){
		functionService.add(model);
		return "list";
	}
	
	public String findMenu() throws IOException{
		List<Function> list = functionService.findMenu();
		writeListJson(list, new String[]{"functions","function","roles"});
		return NONE;
	}
}
