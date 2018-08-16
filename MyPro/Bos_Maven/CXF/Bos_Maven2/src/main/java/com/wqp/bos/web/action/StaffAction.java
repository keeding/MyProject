package com.wqp.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wqp.bos.base.BaseAction;
import com.wqp.bos.domain.Staff;
import com.wqp.bos.service.StaffService;

@Controller("staffAction")
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	
	private String ids;
	
	public String save(){
//		System.out.println(t);
		staffService.save(model);
		return "staffHome";
	}
	
	public String pageQuery() throws IOException{
		staffService.pageQuery(pb);
		writePageBean2Json(pb,new String[]{"decidedzones","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	public String editStaff(){
		staffService.update(model);
		return "staffHome";
	}
	
	@RequiresPermissions(value="staff")
	@RequiresRoles(value="sta")
	public String deleteStaffs(){
		staffService.deleteStaffs(ids);
		return "staffHome";
	}
	
	public String listAjax() throws IOException{
		List<Staff> list = staffService.findListNotDelete();
		writeListJson(list, new String[]{"decidedzones"});
		return NONE;
	}

	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
