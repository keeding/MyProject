package com.wqp.bos.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.wqp.bos.dao.FunctionDao;
import com.wqp.bos.dao.UserDao;
import com.wqp.bos.domain.Function;
import com.wqp.bos.domain.User;

public class BOSRealm extends AuthorizingRealm {
	private static final String User = null;
	@Autowired
	private UserDao userDao;
	@Autowired
	private FunctionDao functionDao;
	
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) auth;
		User user = userDao.findUserByUsername(token.getUsername());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getSimpleName());
		return info;
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		User user = (User)principalCollection.getPrimaryPrincipal();
		String username = user.getUsername();
		String userId = user.getId();
		List<Function> list = null;
		if("admin".equals(username)){
			list = functionDao.findAll();
		}else{
			list = functionDao.findByUserId(userId);
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if(list != null && list.size() > 0){
			for(Function function : list){
				String code = function.getCode();
				if(code != null && !(code).equals("")){
					info.addStringPermission(code);
				}
			}
		}
//		info.addStringPermission("staff");
//		info.addRole("staff");
		return info;
	}
}
