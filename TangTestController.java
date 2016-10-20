package com.assist.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assist.entity.ReturnObject;
import com.assist.service.TangTestService;
import com.assist.util.PageView;

@Controller
@RequestMapping("/tangTest")
public class TangTestController {
	private final static Logger logger = Logger.getLogger(TangTestController.class);
	@Autowired
	private TangTestService tangSerive;
	
	@ResponseBody
	@RequestMapping(value="/getPageInfo",method=RequestMethod.GET)
	public Map<String,Object> getPageInfo(HttpServletResponse resp){
		/*
		 * 跨区域访问时 应该在返回头加上下面的头
		 * */
		resp.setHeader("Access-Control-Allow-Origin", "*");
		
		logger.debug("<--"+"this is my test info"+"-->");
		Map<String,Object> ro = new HashMap<String, Object>();
		tangSerive.insertTestData();
		
		return ro;
	}
	
	@ResponseBody
	@RequestMapping(value="/getPageView",method=RequestMethod.GET)
	public ReturnObject getPageView(Integer pageIndex,HttpServletResponse resp){
		logger.debug("<---"+"开始getPageView了"+"---->");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> pageInfo = new HashMap<String, Object>();
		pageInfo.put("pageIndex", pageIndex);
		PageView pageview = tangSerive.getPageViewInfo(pageInfo);
		ReturnObject ro = new ReturnObject();
		ro.setPageView(pageview);
		return ro;
	}
	
}
