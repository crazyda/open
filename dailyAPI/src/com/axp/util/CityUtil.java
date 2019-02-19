package com.axp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.dao.ISystemConfigDao;
import com.axp.dao.ProvinceEnumDAO;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.SystemConfig;

/**
 * 城市工具类，加载数据到内存
 * @author Administrator
 *
 */
@Service("cityUtil")
public class CityUtil {
	@Autowired 
	ISystemConfigDao systemConfigDao;
	@Autowired
	ProvinceEnumDAO enumDAO;
	
	
	public void getCityJson(HttpServletRequest request){
		List<ProvinceEnum> pEnum = (List<ProvinceEnum>) request.getServletContext().getAttribute("pEnum");
		String zoneVerson = (String) request.getServletContext().getAttribute("zoneVerson");
		SystemConfig systemConfig = systemConfigDao.findByParameter(SystemConfig.SYS_PARAMETER_zone);	
		//判断版本是否为空
		if(zoneVerson==null){		
			request.getServletContext().setAttribute("zoneVerson", systemConfig.getVerson());
			if(pEnum==null){
				pEnum = enumDAO.findLevel();
				request.getServletContext().setAttribute("pEnum", pEnum);
			}
		}else{
			if(!zoneVerson.equals(Integer.toString(systemConfig.getVerson()))){
				pEnum = enumDAO.findLevel();
				request.getServletContext().setAttribute("pEnum",pEnum);
			}
		}
		
	}
	
	public static Map<String,Double> Convert_BD09_To_GCJ02(Double lat,Double lng){
		
		Map<String,Double> map = new HashMap<String,Double>();
		Double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
		double x = lng - 0.0065;
		double y = lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		Double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		map.put("lat", z * Math.sin(theta));
		map.put("lng", z * Math.cos(theta));
		return map;
		
		
		
	}
	
	public static Map<String,Double>  map_tx2bd(double lat, double lon){
		double bd_lat;
		double bd_lon;
		double x_pi=3.14159265358979324;
	    double x = lon, y = lat;
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
	    bd_lon = z * Math.cos(theta) + 0.0065;
	    bd_lat = z * Math.sin(theta) + 0.006;
	    Map<String,Double> map = new HashMap<String,Double>();
	    map.put("lat",bd_lat);
	    map.put("lng",bd_lon);
	    return map;

	}
	
	
	//小程序 获取zoneId
	public static ProvinceEnum getZoneId_xcx(double lats, double lngs){
		Map<String,Double> map = CityUtil.map_tx2bd(Double.valueOf(lats), Double.valueOf(lngs));
    	
		Double lat = map.get("lat");
		Double lng = map.get("lng");
		Map<String,Object> result = (Map<String, Object>) UrlUtil.getBaiduMapToXCX(String.valueOf(lat), String.valueOf(lng)).get("addressComponent");
		String city = result.get("city")==null?"":result.get("city").toString(); //城市
		QueryModel model = new QueryModel();
		model.clearQuery();
		model.combPreLike("name", city);
		DateBaseDAO dateBaseDAO = new DateBaseDAO();
		List<ProvinceEnum> enumList = dateBaseDAO.findLists(ProvinceEnum.class, model);
		ProvinceEnum enums = new ProvinceEnum();
		if(enumList!=null && enumList.size()>0){
			enums = enumList.get(0);
		}
	    return enums;

	}
}
