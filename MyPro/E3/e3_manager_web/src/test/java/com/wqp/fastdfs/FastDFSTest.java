package com.wqp.fastdfs;

import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class FastDFSTest {
	@Test
	public void testUpload() throws Exception{
		FastDFSClient client = new FastDFSClient("D:/Data/Eclipse/Eclipse_tianlong/e3_manager_web/src/test/resources/conf/fdfs_client.conf");
		String uploadFile = client.uploadFile("C:/Users/Administrator/Pictures/photo-1416163119970-b823693d67e7.jpg");
		System.out.println(uploadFile);
	}
}
