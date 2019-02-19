package com.axp.service.impl;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.ItalkGroup;
import com.axp.domain.TkldPid;
import com.axp.domain.Users;
import com.axp.service.IUsersService;
import com.axp.service.ItalkGroupMemberService;
import com.axp.service.RongCloudService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.rongcloud.RongCloud;
import com.rongcloud.models.CodeSuccessResult;
import com.rongcloud.models.GroupInfo;
import com.rongcloud.models.TokenResult;
@Service
public class RongCloudServiceImpl implements RongCloudService{
	
	@Autowired
	private IUsersService usersService;
	@Autowired
	private ItalkGroupMemberService italkGroupMemberService;
	@Resource
	private DateBaseDAO dateBaseDAO;
	
	//开发环境用
	//private final String appKey = "6tnym1br64w07";//替换成您的appkey
	//private final String appSecret = "DwfpctqPb24";//替换成匹配上面key的secret   da
	//生产环境
	private final String appKey = "0vnjpoad062fz";//替换成您的appkey
	private final String appSecret = "QyQI79nMxYcMHq";//替换成匹配上面key的secret   da
	
	
	Reader reader = null ;
	RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
	
	@Override
	public TokenResult getToken(String userId, String name, String portraitUri) {
		 TokenResult userGetTokenResult=null;
		try {
    	  // 获取 Token 方法 
    	   userGetTokenResult = rongCloud.user.getToken(userId, name, portraitUri);
    	  return userGetTokenResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userGetTokenResult;
	}

	@Override
	public CodeSuccessResult getGroupToken(String[] userId, String groupId,
			String groupName) {
		CodeSuccessResult getGroupToken = null;
		try {
			getGroupToken = rongCloud.group.create(userId, groupId, groupName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getGroupToken;
	}

	@Override
	public CodeSuccessResult getSyncGroup(String userId, GroupInfo[] groupInfo) {
		CodeSuccessResult getSyncGroup = null;
		try {
			getSyncGroup = rongCloud.group.sync(userId, groupInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getSyncGroup;
	}

	@Override
	public Map<String, Object> getJoinGroup(HttpServletRequest request) {
		 Parameter parameter = ParameterUtil.getParameter(request);
		 Map<String,Object> statusMap = new HashMap<String,Object>();
		 Map<String,Object> dataMap = new HashMap<String,Object>();
	    
		 if(parameter == null){
	    		return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
	    	}
	    	String[] userIds = null;
	    	String groupId = null;
	    	String groupName = null;
	    	try {
		    	userIds = parameter.getData().getString("userIds").split(",");
		    	
		    	
			} catch (Exception e) {
				 e.printStackTrace();
	             return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
	    	
	    	String userId = userIds[0];
	    	Users user = usersService.get(Integer.parseInt(userId));
	    	
	    	QueryModel queryModel = new QueryModel();
	    	queryModel.combPreEquals("users.id", userId,"usersId");
	    	queryModel.combPreEquals("isValid", true);
	    	List<TkldPid> tkldPid = dateBaseDAO.findLists(TkldPid.class, queryModel);
	    	
	    	queryModel.clearQuery();
	    	queryModel.combPreEquals("italkGroup.groupId",groupId,"groupId" );
	    	queryModel.combPreEquals("isValid", true);
	    	ItalkGroup italkGroup = (ItalkGroup) dateBaseDAO.findOne(ItalkGroup.class,queryModel );
	    	if(italkGroup == null){//  没有群
	    		dataMap.put("noGroup", "没有找到该群");
	    	}else{
	    	Map<String,Object> getJoinCode = new HashMap<String,Object>();
	    	CodeSuccessResult joinCode = null;
	    	try {
				joinCode = rongCloud.group.join(userIds, groupId, groupName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(joinCode.getCode()==200){
	    		//保存群成员信息 可以返回保存信息
	    		String joinString = null;
	    		
	    		joinString = italkGroupMemberService.addGroup(Integer.parseInt(groupId), user);
	    		
	    		dataMap.put("joinString", joinString);
	    		
	    		getJoinCode.put("code", joinCode.getCode());
	    	}else{
	    		getJoinCode.put("code", joinCode.getCode());
	    		getJoinCode.put("message", joinCode.getErrorMessage());
	    	}
	    	
	    	dataMap.put("getJoinCode", getJoinCode);
	    	}
	    	
	    	statusMap.put("dataMap", dataMap);
	    	
		return statusMap;
	}

	

	
	
}
