package com.wqp.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wqp.notes.service.NotesService;

public class T {
	@Test
	public void s() {
		String xmlPath = "classpath:spring/applicationContext-*.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		NotesService userServiceImpl = (NotesService) applicationContext.getBean("userServiceImpl");
		userServiceImpl.getNotes();
	}
}
