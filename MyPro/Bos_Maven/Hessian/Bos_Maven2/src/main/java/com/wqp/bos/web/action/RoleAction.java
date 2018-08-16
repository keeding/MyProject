package com.wqp.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wqp.bos.base.BaseAction;
import com.wqp.bos.domain.Role;

@Controller("roleAction")
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	private String ids;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String pageQuery() throws IOException{
		roleService.pageQuery(pb);
		writePageBean2Json(pb,new String[]{"users","functions"});
		return NONE;
	}
	
	public String findAll() throws IOException{
		List<Role> list = roleService.findAll();
		writeListJson(list, new String[]{"users","functions","code","description"});
		return NONE;
	}
	
	public String add(){
		roleService.add(model, ids);
		return "list";
	}
	
}
