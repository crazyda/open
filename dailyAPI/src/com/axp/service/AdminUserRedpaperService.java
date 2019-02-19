package com.axp.service;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.AdminUser;
import com.axp.domain.AdminuserRedpaper;

public interface AdminUserRedpaperService extends IBaseService<AdminuserRedpaper>{
	Map<String, Object> sendRedpaper(HttpServletRequest request,HttpServletResponse response);
}
