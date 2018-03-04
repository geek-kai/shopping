package cn.e3shop.manager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.MediaTray;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.geek.e3shop.common.utils.FastDFSClient;
import cn.geek.e3shop.common.utils.JsonUtils;

@Controller
public class PictureHandler {

	@Value("${ipAddress}")
	private String imageServerIpAddress;
	@RequestMapping(value="/pic/upload",method=RequestMethod.POST,produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uplode(MultipartFile uploadFile) {
		Map<String,Object>result=new HashMap<String,Object>();
		String jsonResults=null;
		try {
			FastDFSClient  fastDFSClient=new FastDFSClient("classpath:conf/FastDfs.conf");
			String uploadFilePath = fastDFSClient.uploadFile(uploadFile.getBytes(),uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".")+1));
			result.put("error", 0);
			result.put("url", imageServerIpAddress+"/"+uploadFilePath);
			jsonResults = JsonUtils.objectToJson(result).toString();
		} catch (Exception e) {
			result.put("error", 1);
			result.put("message", "上传失败");
			jsonResults = JsonUtils.objectToJson(result).toString();
		}
		return jsonResults;
	}
}
