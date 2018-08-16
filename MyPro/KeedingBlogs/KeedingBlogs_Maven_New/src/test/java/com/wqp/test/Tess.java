package com.wqp.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class Tess {
	@Test
	public void t(){
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		String d = s.format(new Date());
		System.out.println(d);
		System.out.println(new java.sql.Date(new Date().getTime()));
	}
}
