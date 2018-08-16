package com.wqp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wqp.notes.service.NotesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class Tes {

//	@Autowired  // 与junit整合，不需要在spring xml配置扫描
//	public UserService userServiceImpl;
	
	@Autowired
	private ApplicationContext ctx;

	@Test
	public void demo01() {
		// String xmlPath = "applicationContext.xml";
		// ApplicationContext applicationContext = new
		// ClassPathXmlApplicationContext(xmlPath);
		// AccountService accountService = (AccountService)
		// applicationContext.getBean("accountService");
		NotesService userServiceImpl = (NotesService) ctx.getBean("userServiceImpl");
		userServiceImpl.getNotes();
	}
}
