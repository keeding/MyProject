package com.wqp.bos.web.action;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wqp.bos.base.BaseAction;
import com.wqp.bos.domain.User;
import com.wqp.bos.service.UserService;
import com.wqp.bos.utils.MD5Utils;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	
	private String checkcode;
	private String[] roleIds;
	
	public String login(){
		String code = (String) getSessionAttr("key");
		if(StringUtils.isNotBlank(code)){
			if(code.equals(checkcode)){
				Subject subject = SecurityUtils.getSubject();
				String password = model.getPassword();
				password = MD5Utils.md5(password);
				AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), password);
				try {
					subject.login(token);
				} catch (UnknownAccountException e) {
					this.addActionError(this.getText("usernameNotFound"));
					return "login";
				} catch (Exception e){
					this.addActionError(this.getText("loginError"));
					return "login";
				}
				User user = (User) subject.getPrincipal();
				putSession("loginUser", user);
				return "index";
			}else{
				this.addActionError(this.getText("validateCode"));
				return "login";
			}
		}else{
			return "login";
		}
	}

	public String loginBack(){
		String code = (String) getSessionAttr("key");
		if(!StringUtils.isBlank(checkcode) || checkcode.equals(code)){
			/*User userSession = userService.login(getModel().getUsername(), getModel().getPassword());
			if(userSession == null){
				this.addActionError(this.getText("loginError"));
				return "login";
			}
			putSession("loginUser", userSession);
			return "index";*/
			Subject subject = SecurityUtils.getSubject();
			String password = model.getPassword();
			password = MD5Utils.md5(password);
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), password);
			try {
				subject.login(token);
			}catch(UnknownAccountException e){
				e.printStackTrace();
				this.addActionError(this.getText("usernameNotFound"));
				return "login";
			} catch (Exception e) {
				e.printStackTrace();
				this.addActionError(this.getText("loginError"));
				return "login";
			}
			Object principal = subject.getPrincipal();
			putSession("loginUser", principal);
			return "index";
		}else{
			this.addActionError(this.getText("validateCode"));
			return "login";
		}
	}
	
	public String logout(){
		ServletActionContext.getContext().getSession().remove("loginUser");
		return "login";
	}
	
	public String editPassword() throws IOException{
		User user = (User) getSessionAttr("loginUser");
		String flag = "1";
		try {
			String password = model.getPassword();
			userService.editPassword(password, user.getId());
		} catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	public String pageQuery() throws IOException{
		userService.pageQuery(pb);
		writePageBean2Json(pb,new String[]{"roles","noticebills","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	public String add(){
		userService.add(model, roleIds);
		return "list";
	}
	
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	public String[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
}
