package com.wqp.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.wqp.bos.domain.User;

public class LoginInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User userSession = (User) ServletActionContext.getContext().getSession().get("loginUser");
		if(userSession == null){
			Object action = invocation.getAction();
			if(action instanceof ActionSupport)
				((ActionSupport) action).addFieldError("", "尚未登陆!");
			return "login";
		}
		return invocation.invoke();
	}

}
