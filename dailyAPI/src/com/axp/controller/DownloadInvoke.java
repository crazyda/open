package com.axp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("invoke")
public class DownloadInvoke extends BaseController {

	@RequestMapping("download")
	public String mallGetHome(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("dataJson", request.getParameter("data"));
		return "shareWeb/download/download";
	}
	
}
