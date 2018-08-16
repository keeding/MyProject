package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

@Controller
public class PictureController {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping(value="/pic/upload", produces=MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String fileUpload(MultipartFile uploadFile){
		Map<String, Object> result = new HashMap<>();
		try {
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			FastDFSClient client = new FastDFSClient("classpath:conf/client.conf");
			String url = client.uploadFile(uploadFile.getBytes(), extName);
			result.put("error", 0);
			result.put("url", IMAGE_SERVER_URL + url);
			String json = JsonUtils.objectToJson(result);
			System.out.println();
			return json;
		} catch (Exception e) {
			result.put("error", 1);
			result.put("url", "上传失败！");
			return JsonUtils.objectToJson(result);
		}
	}
}
