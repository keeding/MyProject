package com.wqp.bos.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wqp.bos.crm.CustomerService;
import com.wqp.bos.page.PageBean;
import com.wqp.bos.service.DecidedzoneService;
import com.wqp.bos.service.FunctionService;
import com.wqp.bos.service.NoticebillService;
import com.wqp.bos.service.RegionService;
import com.wqp.bos.service.RoleService;
import com.wqp.bos.service.StaffService;
import com.wqp.bos.service.SubareaService;
import com.wqp.bos.service.UserService;
import com.wqp.bos.service.WorkordermanageService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	@Autowired
	protected WorkordermanageService workordermanageService;
	@Autowired
	protected FunctionService functionService;
	@Autowired
	protected RoleService roleService;
	@Autowired
	protected DecidedzoneService decidedzoneService;
	@Autowired
	protected CustomerService customerService;
	@Autowired
	protected RegionService regionService;
	@Autowired
	protected StaffService staffService;
	@Autowired
	protected SubareaService subareaService;
	@Autowired
	protected UserService userService;
	@Autowired
	protected NoticebillService noticebillService;
	
	protected T model;
	
	protected PageBean pb = new PageBean();
	
	protected DetachedCriteria dc = null;
	
	public void setPage(Integer page) {
		pb.setCurrentPage(page); 
	}
	public void setRows(Integer rows) {
		pb.setPageSize(rows);
	}
	
	public void setModel(T model) {
		this.model = model;
	}
	
	@Override
	public T getModel() {
		return model;
	}
	
	public BaseAction() {
		try {
			ParameterizedType type = null;
			if(this.getClass().getGenericSuperclass() instanceof ParameterizedType){
				type = (ParameterizedType) this.getClass().getGenericSuperclass();
			}else{
				type = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
			}
			Class<T> clazz  = (Class<T>) type.getActualTypeArguments()[0];
			dc = DetachedCriteria.forClass(clazz);
			pb.setDetachedCriteria(dc);
			model = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void put(String key, Object value){
		ServletActionContext.getContext().put(key, value);
	}
	
	public void set(String key, Object o){
		ServletActionContext.getContext().getValueStack().set(key, o);
	}
	
	public void push(Object o){
		ServletActionContext.getContext().getValueStack().push(o);
	}
	
	public void putSession(String key, Object object){
		ServletActionContext.getRequest().getSession().setAttribute(key, object);;
	}
	
	public Object getSessionAttr(String key){
		Object object = ServletActionContext.getRequest().getSession().getAttribute(key);
		return object;
	}
	
	public void putApplication(String key, Object value){
		ServletActionContext.getContext().getApplication().put(key, value);
	}
	
	public void writeObjectJson(Object object, String[] excludes) throws IOException{
		JsonConfig jc = new JsonConfig();
		jc.setExcludes(excludes);
		JSONObject jo = JSONObject.fromObject(object);
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(jo.toString());
		System.out.println(jo.toString());
	}
	
	public void writeListJson(List list, String[] excludes) throws IOException{
		JsonConfig jc = new JsonConfig();
		jc.setExcludes(excludes);
//		JSONObject jo = JSONObject.fromObject(pb, jc);
		JSONArray ja = JSONArray.fromObject(list, jc);
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(ja.toString());
		System.out.println(ja.toString());
	}
	
	public void writePageBean2Json(PageBean pb,String[] excludes) throws IOException{
		JsonConfig jc = new JsonConfig();
		jc.setExcludes(excludes);
		JSONObject jo = JSONObject.fromObject(pb, jc);
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(jo.toString());
		System.out.println(jo.toString());
	}
	
}
