package com.axp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.axp.domain.RequestEntity;
import com.axp.util.ParameterUtil;

@Controller
public class DefinedTestInterfaceInvoke extends BaseController {

    /**
     * 请求新增自定义接口，并跳转到相应界面；
     *
     * @return
     */
    @RequestMapping("/addTestAPI")
    public String requestAddTestAPI() {
        return "definedTestAPI/addTestAPI";
    }

    /**
     * 保存新建或者修改的数据；
     *
     * @param id
     * @param name
     * @param status
     * @param message
     * @param data
     * @param response
     * @return
     */
    @RequestMapping("/test/save")
    public String save(Integer id, String name, String status, String message, String data, HttpServletResponse response) {

        //参数检查；
        Boolean b = ParameterUtil.EmptyCheck(name, status, message, data);
        if (!b) {
            try {
                response.getWriter().print("some of parameter is empty,sb");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //检查数据库中是否有重名的请求；
        RequestEntity re = null;
        Boolean isError = false;
        try {
            re = requestEntityService.getRequestEntityByRequestName(name);
        } catch (Exception e) {
            isError = true;
        }
        if (isError || (re != null && id == null)) {//出现多条相同请求的错误数据；或者保存的时候数据库中已经有了一条相同请求名的数据；
            try {
                response.getWriter().print("this name has exist in database already,sb");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //判断是保存还是修改；
        RequestEntity entity;
        if (id != null) {
            entity = requestEntityService.get(id);
        } else {
            entity = new RequestEntity();

        }

        //保存数据；
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);
        map.put("data", data);

        entity.setInvokeName(name);
        entity.setContain(JSONObject.toJSONString(map));
        requestEntityService.save(entity);

        return "forward:/testAPIList";
    }

    /**
     * 请求跳转到自定义接口的列表界面；
     *
     * @return
     */
    @RequestMapping("/testAPIList")
    public String list(HttpServletRequest request) {
        List<RequestEntity> all = requestEntityService.queryAll();
        request.getSession().setAttribute("all", all);
        return "definedTestAPI/list";
    }

    /**
     * 编辑商品，回显数据；
     *
     * @param id
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("testAPIEdit")
    public String edit(Integer id, HttpServletResponse response, HttpServletRequest request) {

        //检查参数；
        Boolean b = ParameterUtil.EmptyCheck(id);
        if (!b) {
            try {
                response.getWriter().print("no this id");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //获取数据并放到session中；
        RequestEntity entity = requestEntityService.get(id);
        if (entity != null) {
            String name = entity.getInvokeName();
            JSONObject parseObject = JSONObject.parseObject(entity.getContain());
            String status = parseObject.getString("status");
            String message = parseObject.getString("message");
            String data = parseObject.getString("data");

            request.getSession().setAttribute("id", id);
            request.getSession().setAttribute("name", name);
            request.getSession().setAttribute("status", status);
            request.getSession().setAttribute("message", message);
            request.getSession().setAttribute("data", data);
        }
        return "definedTestAPI/addTestAPI";
    }

    @RequestMapping("/testAPIDelete")
    public String delete(Integer id, HttpServletResponse response) {

        //参数判断；
        if (id == null) {
            try {
                response.getWriter().print("no this id,i do not know why");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //删除数据，并返回到列表；
        RequestEntity re = new RequestEntity();
        re.setId(id);
        requestEntityService.delete(re);
        return "forward:/testAPIList";
    }

    /**
     * 处理所有的自定义请求处理；
     * 要求：
     * 1，请求只有一层，也就是请求中不能带斜杠；
     * 2，请求需要一test结尾；
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("*.test")
    @ResponseBody
    public void getTestAPIContain(HttpServletRequest request, HttpServletResponse response, String status, String message) throws Exception {

        //获取请求；
        String requestURI = request.getRequestURI();
        int startIndex = requestURI.lastIndexOf("/");
        int endIndex = requestURI.lastIndexOf(".");
        String ret = requestURI.substring(startIndex + 1, endIndex);

        //从数据库中获取数据后返回回去
        RequestEntity entity = requestEntityService.getRequestEntityByRequestName(ret);

        //如果status和message有值，则可以实现更改,否则就直接返回contain内容；
        String returnString = entity.getContain();
        if (status != null || message != null) {
            JSONObject parseObject = JSONObject.parseObject(entity.getContain());
            String status2 = parseObject.getString("status");
            String message2 = parseObject.getString("message");
            String data2 = parseObject.getString("data");

            if (status != null) {
                status2 = status;
            }
            if (message != null) {
                message2 = message;
            }

            Map<String, Object> map = new HashMap<>();
            map.put("status", status2);
            map.put("message", message2);
            map.put("data", data2);
            returnString = JSONObject.toJSONString(map);
        }

        //返回数据；
        try {
            response.getWriter().print(returnString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
