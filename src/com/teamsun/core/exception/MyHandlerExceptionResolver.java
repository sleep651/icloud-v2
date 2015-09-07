package com.teamsun.core.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private String defaultErrorView;
	public String getDefaultErrorView() {
		return defaultErrorView;
	}
	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}
	@SuppressWarnings("unchecked")
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
/*		logger.error("class = "+handler.getClass());
		logger.error("exception = "+ex.getClass());
		logger.error("exception = "+ex.getCause());
		logger.error("Handle exception: " + ex.getMessage());*/

		StackTraceElement   stackTraceElement=   ex.getStackTrace()[0];//   得到异常棧的首个元素 
		logger.error( "File= "+stackTraceElement.getFileName());//   打印文件名 
		logger.error( "Line= "+stackTraceElement.getLineNumber());//   打印出错行号 
		logger.error( "Method= "+stackTraceElement.getMethodName());//   打印出错方法 
		ex.printStackTrace();//   打印出错方法 
		
		Map model = new HashMap();
		model.put("ex", ex.getClass().getSimpleName());
		model.put("error", ex.getMessage());
		return new ModelAndView(defaultErrorView, model);
	}
}