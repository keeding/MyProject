package com;

import java.io.File;

import org.junit.Test;

public class Tes {
	@Test
	public void sa() {
		try {
//			Writer out = new FileWriter("D:/temp1/E3/freemarker/item/111.html");
			File file =  new File("D:/temp/E3/freemarker/item/");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
//			out.write("abc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
