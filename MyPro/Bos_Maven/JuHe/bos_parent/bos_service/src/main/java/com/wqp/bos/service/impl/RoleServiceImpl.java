package com.wqp.bos.service.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wqp.bos.dao.RoleDao;
import com.wqp.bos.domain.Function;
import com.wqp.bos.domain.Role;
import com.wqp.bos.service.RoleService;
import com.wqp.bos.utils.PageBean;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private IdentityService identityService;
	
	@Override
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	@Override
	public void add(Role role, String ids) {
		roleDao.save(role);
//		Set functions = role.getFunctions();
		Group group = new GroupEntity(role.getName());
		identityService.saveGroup(group);
		
		String[] split = ids.split(",");
		for(String s : split){
			Function function = new Function(s);
			role.getFunctions().add(function);
		}
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

}
