package com.axp.util;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

public class ProRequestUtil{
	
	public static final String CHARSET = "UTF-8";
	
	public static String getJSONString(HttpServletRequest request) {
		String json = "";
		try {
			ServletInputStream in = request.getInputStream();
			String content = IOUtils.toString(in, CHARSET);
			json = URLDecoder.decode(content, CHARSET);
			json = json.substring(json.indexOf("=") + 1);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

}