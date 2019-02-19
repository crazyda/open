package com.axp.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.dao.DateBaseDAO;
import com.axp.dao.IUsersDao;
import com.axp.domain.CashshopType;
import com.axp.util.CacheUtil;
import com.axp.util.CalcUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;

@Controller
@RequestMapping("invoke/taoke")
public class TaoKeInvoke extends BaseController {
	String url = "http://hhh.aixiaoping.cn";
	String sellerUrl = "http://seller.aixiaoping.com";// 优惠券

	@Autowired
	IUsersDao usersDao;

	@RequestMapping("/getXplg")
	@ResponseBody
	public Map<String, Object> getAdverImgs(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("url", "");
		// data.put("url",
		// "http://www.518wtk.com/index.php?s=/Index/indexYmb.html&pid=mm_111685502_17728608_72904717");
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", data);

		return statusMap;
	}

	@RequestMapping("/getTjg")
	@ResponseBody
	public Map<String, Object> getTjg(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		// data.put("url", "http://www.fcpinhuiju.com:8080/xplg/");
		data.put("url", "http://taojingou.777wk.com?from=xplg");
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", data);

		return statusMap;
	}

	@RequestMapping("/getHgxp")
	@ResponseBody
	public Map<String, Object> getHgxp(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		// data.put("url", "http://www.fcpinhuiju.com:8080/xplg/");
		data.put("url",
				"http://www.518wtk.com?from=xplg&pid=mm_121508524_22148843_73750411");
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", data);

		return statusMap;
	}

	@RequestMapping("/getSqg")
	@ResponseBody
	public Map<String, Object> getSqg(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		// data.put("url", "http://www.fcpinhuiju.com:8080/xplg/");
		data.put("url",
				"http://www.518wtk.com?from=xplg&pid=mm_121757583_22042765_75434538");
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", data);

		return statusMap;
	}

	@RequestMapping("/getPartnerInfo")
	@ResponseBody
	public Map<String, Object> getPartnerInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();

		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}

			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());

				// statusMap=
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/Cityagent/index?user_id="+userId);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/Cityagent/index?user_id="+userId);

			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "获取数据失败！");

		}

		return statusMap;
	}

	@RequestMapping("/getPartnerOrder")
	@ResponseBody
	public Map<String, Object> getPartnerOrder(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();

		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}

			String time_num = parameter.getData().getString("timeNum") == null ? "5"
					: parameter.getData().getString("timeNum");
			String seach = parameter.getData().getString("seach") == null ? ""
					: parameter.getData().getString("seach");
			String pageIndex = parameter.getData().getString("pageIndex") == null ? "0"
					: parameter.getData().getString("pageIndex");

			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/Cityagent/orderindex?user_id="+userId+"&time_num="+time_num+"&center="+seach+"&p="+pageIndex);
				// statusMap=
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/Cityagent/orderindex?user_id="+userId+"&time_num="+time_num+"&center="+seach+"&p="+pageIndex);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return statusMap;
	}

	@RequestMapping("/getPartnerList")
	@ResponseBody
	public Map<String, Object> getPartnerList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();

		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}

			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				String seach = parameter.getData().getString("seach") == null ? ""
						: parameter.getData().getString("seach");
				String pageIndex = parameter.getData().getString("pageIndex") == null ? "1"
						: parameter.getData().getString("pageIndex");

				// statusMap=
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/Cityagent/MyPartner?user_id="+userId+"&center="+seach+"&p="+pageIndex);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/Cityagent/MyPartner?user_id="+userId+"&center="+seach+"&p="+pageIndex);

			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return statusMap;
	}

	@RequestMapping("/settlement")
	@ResponseBody
	public Map<String, Object> settlement(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();

		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}

			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				// statusMap=
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/cityagent/settlementApi?user_id="+userId);
				statusMap = UrlUtil.getTaoKeToMap(url
						+ "/home/cityagent/settlementApi?user_id="+userId);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return statusMap;
	}

	// 合伙人入口加载数据
	@RequestMapping("/index")
	@ResponseBody
	public Map<String, Object> index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				// statusMap =
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/index?&user_id="+userId);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/taoke/index?&user_id="+userId);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}

	// 淘客代理/合伙人信息
	@RequestMapping("/getPartnerInfos")
	@ResponseBody
	public Map<String, Object> getPartnerInfos(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				Integer areaType = parameter.getData().getInteger("areaType") == null ? 0
						: parameter.getData().getInteger("areaType"); // 区域选项
																		// 0周边产品
																		// 1全国特产
				Integer sortType = parameter.getData().getInteger("sortType") == null ? 0
						: parameter.getData().getInteger("sortType");// 排序选项 默认0
																		// 升序1
																		// 降序-1
				String zoneId = parameter.getZoneId() == null ? "1" : parameter
						.getZoneId();
				//改成java处理
				 //statusMap = tkldPidService.getPartenerInfo(userId, areaType, sortType, zoneId);
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/getPartnerInfo?&user_id="+userId);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/taoke/getPartnerInfo?&user_id="+userId+"&areaType="+areaType+"&sortType="+sortType
						+ "&zone_id=" + zoneId);
				GetData gd = new GetData(userId);
				gd.start();

			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "获取数据失败！");
		}
		return statusMap;
	}


	
	
	
	// 我的合伙人信息
	@RequestMapping("/getPartnerLists")
	@ResponseBody
	public Map<String, Object> getPartnerLists(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				String seach = parameter.getData().getString("seach") == null ? ""
						: parameter.getData().getString("seach");
				Integer pageIndex = parameter.getData().getInteger("pageIndex") == null ? 1
						: parameter.getData().getInteger("pageIndex");
				
				
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/getPartnerList?&user_id="+userId+"&pageIndex="+pageIndex+"&seach="+seach);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/taoke/getPartnerList?&user_id="+userId+"&pageIndex="+pageIndex+"&seach="+seach);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}

	// 删除合伙人
	@RequestMapping("/partnerDelete")
	@ResponseBody
	public Map<String, Object> partnerDelete(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				Integer partnerId = parameter.getData().getInteger("partnerId") == null ? 0
						: parameter.getData().getInteger("partnerId");
				// statusMap =
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/partnerDelete?&user_id="+userId+"&partnerId="+partnerId);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/taoke/partnerDelete?&user_id="+userId
						+ "&partnerId=" + partnerId);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}

	// 备注合伙人
	@RequestMapping("/partnerRemark")
	@ResponseBody
	public Map<String, Object> partnerRemark(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			/*
			 * request.setCharacterEncoding("utf8");
			 * response.setCharacterEncoding("utf8");
			 */
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				Integer partnerId = parameter.getData().getInteger("partnerId");
				String remark = parameter.getData().getString("remark") == null ? ""
						: parameter.getData().getString("remark");
				// statusMap =
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/partnerRemark?partnerId="+partnerId+"&remark="+remark+"&user_id="+userId);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/taoke/partnerRemark?partnerId="+partnerId+"&remark="+remark+"&user_id="+userId);

			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "数据请求错误");
		}
		return statusMap;
	}

	// 我的合伙人列表
	@RequestMapping("/partnerList")
	@ResponseBody
	public Map<String, Object> partnerList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				// statusMap =
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/partnerList?&user_id="+userId);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/taoke/partnerList?&user_id="+userId);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "数据请求错误");
		}
		return statusMap;
	}

	// 合伙人添加
	@RequestMapping("/partnerAdd")
	@ResponseBody
	public Map<String, Object> partnerAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				String remark = parameter.getData().getString("remark") == null ? ""
						: parameter.getData().getString("remark");
				String partnerPhone = parameter.getData().getString(
						"partnerPhone") == null ? "" : parameter.getData()
						.getString("partnerPhone");
				// statusMap =
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/partnerAdd?&user_id="+userId+"&partnerPhone="+partnerPhone+"&remark="+remark);
				statusMap = UrlUtil
						.getTaoKeToMap(url+"/home/taoke/partnerAdd?&user_id="+userId+"&partnerPhone="+partnerPhone
								+ "&remark=" + remark);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "数据请求错误");
		}
		return statusMap;
	}

	// 优惠券列表
	@RequestMapping("/ticketList")
	@ResponseBody
	public Map<String, Object> ticketList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				Integer ticketType = parameter.getData().getInteger(
						"ticketType") == null ? 2 : parameter.getData()
						.getInteger("ticketType");
				Integer pageIndex = parameter.getData().getInteger("pageIndex") == null ? 1
						: parameter.getData().getInteger("pageIndex");
				// statusMap =
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/ticketList?&user_id="+userId+"&ticketType="+ticketType+"&pageIndex="+pageIndex);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/taoke/ticketList?&user_id="+userId+"&ticketType="+ticketType+"&pageIndex="+pageIndex);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "数据请求错误");
		}
		return statusMap;
	}

	// 淘客代理/合伙人信息
	@RequestMapping("/homeTickets")
	@ResponseBody
	public Map<String, Object> homeTickets(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				String page = parameter.getData().getString("page") == null ? "0"
						: parameter.getData().getString("page");
				Integer areaType = parameter.getData().getInteger("areaType");// 区域选项
																				// 0周边产品
																				// 1全国特产
				Integer sortType = parameter.getData().getInteger("sortType");// 排序选项
																				// 默认0
																				// 升序1
																				// 降序-1
				Integer userId = Integer.parseInt(parameter.getUserId());
				Integer typeId = parameter.getData().getInteger("typeId") == null ? 0
						: parameter.getData().getInteger("typeId");
				String zoneId = parameter.getZoneId() == null ? "1" : parameter
						.getZoneId();
				// statusMap =
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/homeTickets?&user_id="+userId+"&typeId="+typeId+"&page="+page);
				String url1 = url+"/home/taoke/homeTickets?&user_id="+userId+"&typeId="+typeId+"&page="+page+"&zone_id="+zoneId;

				if (areaType != null) {
					url1 += "&areaType=" + areaType;
				}
				if (sortType != null) {
					url1 += "&sortType=" + sortType;
				}
				statusMap = UrlUtil.getTaoKeToMap(url1);

			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "数据请求错误");
		}

		return statusMap;
	}

	// 得到所有分类
	@RequestMapping("/cate")
	@ResponseBody
	public Map<String, Object> cate(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		String source = "0";
		Parameter parameter = ParameterUtil.getParameter(request);

		if (parameter.getData() != null) {
			source = parameter.getData().getString("source");
		}

		if (StringUtils.isBlank(source)) {
			source = "0";
		}

		try {
			if ("0".equals(source)) {// 淘宝
				statusMap = UrlUtil
						.getTaoKeToMap("http://www.518wtk.com/admin.php?s=/Interface/cate");

			}
			if ("3".equals(source)) {// 拼多多
				List<Map<String, Object>> PddList = new ArrayList<Map<String, Object>>();
				PddList = (List<Map<String, Object>>) UrlUtil
						.getTaoKeToMap2(
								"http://open.aixiaoping.cn:8080/open/api/getClassify")
						.get(0).get("openClassify");

				for (int i = 0; i < PddList.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("cid", PddList.get(i).get("categoryId"));
					map.put("title", PddList.get(i).get("categoryName"));
					dataList.add(map);
				}
				dataMap.put("isShare", false);
				dataMap.put("cate", dataList);
				statusMap.put("data", dataMap);
				statusMap.put("message", "请求成功");
				statusMap.put("status", 1);

			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "数据请求错误");
		}
		return statusMap;
	}

	// 查询分类商品根据分类id
	@RequestMapping("/getGoods")
	@ResponseBody
	public Map<String, Object> getGoods(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			request.setCharacterEncoding("utf8");
			response.setCharacterEncoding("utf8");

			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			// 如果pid为空 那么就给一个默认值 StringUtil.TAOKEPID
			String title = parameter.getData().getString("title") == null ? ""
					: parameter.getData().getString("title");
			String search = parameter.getData().getString("keyword") == null ? ""
					: parameter.getData().getString("keyword");
			String pid = parameter.getData().getString("pid") == null?StringUtil.TAOKEPID: parameter.getData().getString("pid");
			Integer cid = parameter.getData().getInteger("cid") == null ? -1
					: parameter.getData().getInteger("cid");// 类别id
			String userId = parameter.getUserId() == null ? "-1" : parameter
					.getUserId();
			String zoneId = parameter.getZoneId() == null ? "-1" : parameter
					.getZoneId();
			// pageIndex 指的是条数
			Integer pageIndex = (parameter.getData().getInteger("pageIndex") ==null?1:parameter.getData().getInteger("pageIndex"));

			String source = parameter.getData().getString("source");

			if (StringUtils.isBlank(source)) {
				source = "0";
			}
			if("".equals(pid)){
				pid = StringUtil.TAOKEPID;
			}
			if (source.equals("0")) {// 0:淘宝天猫、、 pid 可以传null 不能传""
				String a = "http://www.518wtk.com/admin.php?s="+"/Interface/getGoods&pid="+pid+"&cid="+cid
						+"&page_num="+pageIndex+"&title="+title
						+"&keyword="+URLEncoder.encode(search, "UTF-8")
						+"&user_id="+userId+"&zone_id="+zoneId;
				statusMap = UrlUtil.getTaoKeToMap(a);
				
				GetGoods getGoods = new GetGoods(pid, cid, pageIndex, title,
						search, userId, zoneId);
				getGoods.start();
				getGoods.join();

			}
			if (source.equals("1")) {// 1:京东、后续添加

			}
			if (source.equals("2")) {// 2:商品活动

			}
			if (source.equals("3")) {// 3:拼多多

				Map<String, Object> data = new HashMap<String, Object>();
				List<Map<String, Object>> goods = new ArrayList<Map<String, Object>>();
				Map<String, String> signMap = new HashMap<String, String>();
				signMap.put("client_id", StringUtil.pdd_client_id);
				signMap.put("limit", "20");
				signMap.put("page", String.valueOf(pageIndex));
				String sign = MD5Util.getSign(signMap,
						StringUtil.pdd_client_screct);
				String url = "";
				if (cid == -1) {
					url = "http://open.aixiaoping.cn:8080/open/api/product?data={\"client_id\":\""
							+ StringUtil.pdd_client_id
							+ "\",\"sign\":\""
							+ sign
							+ "\",\"data\":{\"limit\":\"20\",\"page\":\""
							+ pageIndex
							+ "\",\"search\":\""
							+ URLEncoder.encode(search, "UTF-8") + "\"}}";
				} else {
					url = "http://open.aixiaoping.cn:8080/open/api/product?data={\"client_id\":\""
							+ StringUtil.pdd_client_id
							+ "\",\"sign\":\""
							+ sign
							+ "\",\"data\":{\"limit\":\"20\",\"page\":\""
							+ pageIndex
							+ "\",\"search\":\""
							+ URLEncoder.encode(search, "UTF-8")
							+ "\",\"categoryId\":" + cid + "}}";
				}

				// 把所有的字段全部改成和淘宝字段一样的
				Map<String, Object> goodMap = UrlUtil.getTaoKeToMap(url);
				Map<String, Object> result = (Map<String, Object>) goodMap
						.get("result");
				List<Map<String, Object>> good = (List<Map<String, Object>>) result
						.get("products");
				for (int i = 0; i < good.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (good.get(i).get("coupon_total_quantity") == null
							|| good.get(i).get("coupon_remain_quantity") == null
							|| good.get(i).get("min_group_price") == null
							|| good.get(i).get("promotion_rate") == null
							|| good.get(i).get("coupon_discount") == null) {
						continue;
					}
					String min_group_price = good.get(i).get("min_group_price")
							.toString();
					Integer quan_receive = Integer.valueOf((int) CalcUtil
							.sub(Integer.valueOf(good.get(i).get("coupon_total_quantity").toString()),
									Integer.valueOf(good.get(i).get("coupon_remain_quantity").toString())));
					if (quan_receive < 0) {
						// 把剩余券数当成总券数
						map.put("quan_surplus",
								good.get(i).get("coupon_total_quantity")
										.toString());// < 优惠券剩余数量
						Integer quan_receive_va = Integer
								.valueOf((int) CalcUtil.sub(
										Integer.valueOf(good.get(i).get("coupon_remain_quantity").toString()),
										Integer.valueOf(good.get(i).get("coupon_total_quantity").toString())));
						map.put("quan_receive", String.valueOf(quan_receive_va));// <
																					// 已领券=总-剩余数量
					} else {
						map.put("quan_surplus",
								good.get(i).get("coupon_remain_quantity")
										.toString());// < 优惠券剩余数量
						map.put("quan_receive", String.valueOf(quan_receive));// <
																				// 已领券=总-剩余数量
					}
					Double cutPrice = Double.valueOf(good.get(i)
							.get("coupon_discount").toString()) / 100;
					Double price = CalcUtil.sub(
							Double.valueOf(good.get(i).get("min_group_price")
									.toString()) / 100, cutPrice);
					
					Double earnMoney = CalcUtil.mul(
							price,
							Double.valueOf(good.get(i).get("promotion_rate")
									.toString()) / 1000, 2);
					earnMoney = CalcUtil.mul(earnMoney, 0.4, 2);
					map.put("goods_id", good.get(i).get("goods_id").toString());// <商品id
					map.put("d_title", good.get(i).get("goods_name").toString());// <
																					// 商品名称
					map.put("org_price", String.valueOf(Double
							.valueOf(min_group_price) / 100));// 原价--最小团买价格
					map.put("quan_price",
							String.valueOf(Double.valueOf(good.get(i)
									.get("coupon_discount").toString()) / 100));// <
																				// 优惠券金额
					map.put("price", String.valueOf(price));// 现价即券后价 = 最小团价- 券价

					map.put("cut_price", String.valueOf(cutPrice));// 券价
					map.put("pic", good.get(i).get("goods_thumbnail_url"));// <
																			// 商品图片
					map.put("earnMoney", String.valueOf(earnMoney));// < 赚多少
																	// =最小团价-券价
																	// )*佣金比例goods.add(map);
					goods.add(map);
				}
				data.put("PageNum", (good.size()%10)>0?(good.size()/10)+1:good.size()/10);
				data.put("goods", goods);
				data.put("shareContent", "");
				data.put("shareTitle", "");
				data.put("shareIconUrl", "");
				data.put("shareTargetUrl", "");
				statusMap.put("data", data);
				statusMap.put("status", 1);
				statusMap.put("message", "请求成功");

			}
		} catch (Exception e) {

			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "数据请求错误");
		}
		return statusMap;
	}

	// 得到品牌列表
	@RequestMapping("/brand")
	@ResponseBody
	public Map<String, Object> brand(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String source = parameter.getData().getString("source");

		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			if (source.equals("0")) {// 0:淘宝天猫品牌商品、
				Map<String, Object> dataMap = new HashMap<String, Object>();
				List<Map<String, Object>> brandList = new ArrayList<Map<String, Object>>();
				brandList = UrlUtil.getStringForUrl("http://www.518wtk.com/admin.php?s=/Interface/brand");
				for(int i =0;i<brandList.size();i++){
					if(brandList.get(i).get("name").equals("暴龙") || brandList.get(i).get("name").equals("九牧") || brandList.get(i).get("name").equals("海尔")){
						brandList.remove(i);
					}
				}
				
				dataMap.put("brand", brandList);
				statusMap.put("data", dataMap);
				statusMap.put("message", "请求成功");
				statusMap.put("status", 1);
			}
			if (source.equals("3")) {// 3:拼多多品牌商品 后续添加
				// statusMap =
				// UrlUtil.getTaoKeToMap("http://www.518wtk.com/admin.php?s=/Interface/brand");

				List<CashshopType> typeList = cashshopTypeDAO.queryAll();
				String RESOURCE_LOCAL_URL = request.getServletContext()
						.getAttribute("RESOURCE_LOCAL_URL").toString();
				Map<String, Object> dataMap = new HashMap<String, Object>();
				List<Map<String, Object>> brand = new ArrayList<Map<String, Object>>();
				for (CashshopType cashshopType : typeList) {
					// 获取ctMap中的信息保存
					Map<String, Object> keyMap = new HashMap<String, Object>();
					Integer id = cashshopType.getId();
					
					if(116 <= id && id <= 130 || 163 <= id && id<= 168 ){
						keyMap.put("keyword", cashshopType.getRemark());
						keyMap.put("logo",
								RESOURCE_LOCAL_URL + cashshopType.getImg());
						keyMap.put("name", cashshopType.getName());
						keyMap.put("bid", cashshopType.getId());
						brand.add(keyMap);
					}
				}
				dataMap.put("brand", brand);
				statusMap.put("data", dataMap);
				statusMap.put("message", "请求成功");
				statusMap.put("status", 1);

			}

		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "数据请求错误");
		}
		return statusMap;
	}

	// 得到品牌商品
	@RequestMapping("/getBrandGoods")
	@ResponseBody
	public Map<String, Object> getBrandGoods(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {

			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			// 如果pid为空 那么就给一个默认值 StringUtil.TAOKEPID
			String pid = parameter.getData().getString("pid") == null ? StringUtil.TAOKEPID
					: parameter.getData().getString("pid");
			String keyword = parameter.getData().getString("keyword") == null ? ""
					: parameter.getData().getString("keyword");
			Integer source = parameter.getData().getInteger("source");
			Integer pageIndex = (parameter.getData().getInteger("pageIndex") == null ? 1
					: parameter.getData().getInteger("pageIndex"));

			String themeId = parameter.getData().getString("themeId");

			if (source == null) {
				source = 0;
			}

			if (source == 0) {
				statusMap = UrlUtil
						.getTaoKeToMap("http://www.518wtk.com/admin.php?s=/Interface/getBrandGoods&pid="
								+ pid
								+ "&keyword="
								+ URLEncoder.encode(keyword, "UTF-8")
								+ "&page_num=" + pageIndex);

			} else if (source == 2) { // 以主题Id 查询的 避免3里面出现关键之修正 查询到的不对应
				Map<String, String> signMap = new HashMap<String, String>();
				signMap.put("client_id", StringUtil.pdd_client_id);
				String sign = MD5Util.getSign(signMap,
						StringUtil.pdd_client_screct);

				String url = "http://open.aixiaoping.cn:8080/open/api/findThemeGoods?data={\"client_id\":\""
						+ StringUtil.pdd_client_id
						+ "\",\"sign\":\""
						+ sign
						+ "\",\"data\":{\"themeId\":\"" + themeId + "\"}}";
				List<Map<String, Object>> goods = new ArrayList<Map<String, Object>>();
				Map<String, Object> data = new HashMap<String, Object>();
				// 把所有的字段全部改成和淘宝字段一样的
				Map<String, Object> goodMap = UrlUtil.getTaoKeToMap(url);

				List<Map<String, Object>> good = (List<Map<String, Object>>) goodMap
						.get("orderList");
				for (int i = 0; i < good.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (good.get(i).get("coupon_total_quantity") == null
							|| good.get(i).get("coupon_remain_quantity") == null
							|| good.get(i).get("min_group_price") == null
							|| good.get(i).get("promotion_rate") == null
							|| good.get(i).get("coupon_discount") == null) {
						continue;
					}
					String min_group_price = good.get(i).get("min_group_price")
							.toString();
					Integer quan_receive = Integer.valueOf((int) CalcUtil
							.sub((Integer) good.get(i).get(
									"coupon_total_quantity"), (Integer) good
									.get(i).get("coupon_remain_quantity")));
					if (quan_receive < 0) {
						// 把剩余券数当成总券数
						map.put("quan_surplus",
								good.get(i).get("coupon_total_quantity")
										.toString());// < 优惠券剩余数量
						Integer quan_receive_va = Integer
								.valueOf((int) CalcUtil.sub(
										(Integer) good.get(i).get(
												"coupon_remain_quantity"),
										(Integer) good.get(i).get(
												"coupon_total_quantity")));
						map.put("quan_receive", String.valueOf(quan_receive_va));// <
																					// 已领券=总-剩余数量
					} else {
						map.put("quan_surplus",
								good.get(i).get("coupon_remain_quantity")
										.toString());// < 优惠券剩余数量
						map.put("quan_receive", String.valueOf(quan_receive));// <
																				// 已领券=总-剩余数量
					}
					Double cutPrice = Double.valueOf(good.get(i)
							.get("coupon_discount").toString()) / 100;
					Double price = CalcUtil.sub(
							Double.valueOf(good.get(i).get("min_group_price")
									.toString()) / 100, cutPrice);
					Double earnMoney = CalcUtil.mul(
							price,
							Double.valueOf(good.get(i).get("promotion_rate")
									.toString()) / 1000, 2);
					earnMoney = CalcUtil.mul(earnMoney, 0.4, 2);
					map.put("goods_id", good.get(i).get("goods_id").toString());// <商品id
					map.put("d_title", good.get(i).get("goods_name").toString());// <
																					// 商品名称
					map.put("org_price", String.valueOf(Double
							.valueOf(min_group_price) / 100));// 原价--最小团买价格
					map.put("quan_price",
							String.valueOf(Double.valueOf(good.get(i)
									.get("coupon_discount").toString()) / 100));// <
																				// 优惠券金额
					map.put("price", String.valueOf(price));// 现价即券后价 = 最小团价- 券价
					map.put("quan_surplus",
							good.get(i).get("coupon_remain_quantity")
									.toString());// < 优惠券剩余数量
					map.put("cut_price", String.valueOf(cutPrice));// 券价
					map.put("pic", good.get(i).get("goods_thumbnail_url"));// <
																			// 商品图片
					map.put("earnMoney", String.valueOf(earnMoney));// < 赚多少
																	// =最小团价-券价
																	// )*佣金比例goods.add(map);
					map.put("quan_receive", String.valueOf(quan_receive));// <
																			// 已领券=总-剩余数量
					goods.add(map);
				}
				data.put("goods", goods);
				data.put("shareContent", "");
				data.put("shareTitle", "");
				data.put("shareIconUrl", "");
				data.put("shareTargetUrl", "");
				statusMap.put("data", data);
				statusMap.put("status", 1);
				statusMap.put("message", "请求成功");

			} else if (source == 3) {
				Map<String, String> signMap = new HashMap<String, String>();
				signMap.put("client_id", StringUtil.pdd_client_id);
				signMap.put("limit", "20");
				signMap.put("page", String.valueOf(pageIndex));
				String sign = MD5Util.getSign(signMap,
						StringUtil.pdd_client_screct);
				String url = "http://open.aixiaoping.cn:8080/open/api/product?data={\"client_id\":\""
						+ StringUtil.pdd_client_id
						+ "\",\"sign\":\""
						+ sign
						+ "\",\"data\":{\"limit\":\"20\",\"page\":\""
						+ pageIndex
						+ "\",\"search\":\""
						+ URLEncoder.encode(keyword, "UTF-8") + "\"}}";
				List<Map<String, Object>> goods = new ArrayList<Map<String, Object>>();
				Map<String, Object> data = new HashMap<String, Object>();
				// 把所有的字段全部改成和淘宝字段一样的
				Map<String, Object> goodMap = UrlUtil.getTaoKeToMap(url);
				Map<String, Object> result = (Map<String, Object>) goodMap
						.get("result");
				List<Map<String, Object>> good = (List<Map<String, Object>>) result
						.get("products");
				for (int i = 0; i < good.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (good.get(i).get("coupon_total_quantity") == null
							|| good.get(i).get("coupon_remain_quantity") == null
							|| good.get(i).get("min_group_price") == null
							|| good.get(i).get("promotion_rate") == null
							|| good.get(i).get("coupon_discount") == null) {
						continue;
					}
					String min_group_price = good.get(i).get("min_group_price")
							.toString();
					Integer quan_receive = Integer.valueOf((int) CalcUtil
							.sub(Integer.valueOf(good.get(i).get("coupon_total_quantity").toString()), 
									Integer.valueOf(good.get(i).get("coupon_remain_quantity").toString())));
					if (quan_receive < 0) {
						// 把剩余券数当成总券数
						map.put("quan_surplus",
								good.get(i).get("coupon_total_quantity")
										.toString());// < 优惠券剩余数量
						Integer quan_receive_va = Integer
								.valueOf((int)CalcUtil.sub(
										Integer.valueOf(good.get(i).get("coupon_remain_quantity").toString()),
										Integer.valueOf(good.get(i).get("coupon_total_quantity").toString())));
						map.put("quan_receive", String.valueOf(quan_receive_va));// <
																					// 已领券=总-剩余数量
					} else {
						map.put("quan_surplus",
								good.get(i).get("coupon_remain_quantity")
										.toString());// < 优惠券剩余数量
						map.put("quan_receive", String.valueOf(quan_receive));// <
																				// 已领券=总-剩余数量
					}
					Double cutPrice = Double.valueOf(good.get(i)
							.get("coupon_discount").toString()) / 100;
					Double price = CalcUtil.sub(
							Double.valueOf(good.get(i).get("min_group_price")
									.toString()) / 100, cutPrice);
					Double earnMoney = CalcUtil.mul(
							price,
							Double.valueOf(good.get(i).get("promotion_rate")
									.toString()) / 1000, 2);
					earnMoney = CalcUtil.mul(earnMoney, 0.4, 2);
					map.put("goods_id", good.get(i).get("goods_id").toString());// <商品id
					map.put("d_title", good.get(i).get("goods_name").toString());// <
																					// 商品名称
					map.put("org_price", String.valueOf(Double
							.valueOf(min_group_price) / 100));// 原价--最小团买价格
					map.put("quan_price",
							String.valueOf(Double.valueOf(good.get(i)
									.get("coupon_discount").toString()) / 100));// <
																				// 优惠券金额
					map.put("price", String.valueOf(price));// 现价即券后价 = 最小团价- 券价
					map.put("quan_surplus",
							good.get(i).get("coupon_remain_quantity")
									.toString());// < 优惠券剩余数量
					map.put("cut_price", String.valueOf(cutPrice));// 券价
					map.put("pic", good.get(i).get("goods_thumbnail_url"));// <
																			// 商品图片
					map.put("earnMoney", String.valueOf(earnMoney));// < 赚多少
																	// =最小团价-券价
																	// )*佣金比例goods.add(map);
					map.put("quan_receive", String.valueOf(quan_receive));// <
																			// 已领券=总-剩余数量
					goods.add(map);

				}
				
				data.put("PageNum", (good.size()%10)>0?(good.size()/10)+1:good.size()/10);
				data.put("goods", goods);
				data.put("shareContent", "");
				data.put("shareTitle", "");
				data.put("shareIconUrl", "");
				data.put("shareTargetUrl", "");
				statusMap.put("data", data);
				statusMap.put("status", 1);
				statusMap.put("message", "请求成功");

			}

		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "数据请求错误");
		}
		return statusMap;
	}

	// 宝贝推广详情
	@RequestMapping("/ticketGoodsDetail")
	@ResponseBody
	public Map<String, Object> ticketGoodsDetail(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				String userId = parameter.getUserId() == null ? "-1"
						: parameter.getUserId();
				// Integer ticketId =
				// parameter.getData().getInteger("ticketId")==null?0:parameter.getData().getInteger("ticketId");
				String ticketId = parameter.getData().getString("ticketId") == null ? "0"
						: parameter.getData().getString("ticketId");
				// statusMap =
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/ticketGoodsDetail?&ticketId="+ticketId+"&user_id="+userId);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/taoke/ticketGoodsDetail?&ticketId="+ticketId+"&user_id="+userId);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "数据请求错误");
		}
		return statusMap;
	}

	// 合伙人订单
	@RequestMapping("/getPartnerOrders")
	@ResponseBody
	public Map<String, Object> getPartnerOrders(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();

		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}

			String time_num = parameter.getData().getString("timeNum") == null ? "5"
					: parameter.getData().getString("timeNum");
			String seach = parameter.getData().getString("seach") == null ? ""
					: parameter.getData().getString("seach");
			String pageIndex = parameter.getData().getString("pageIndex") == null ? "0"
					: parameter.getData().getString("pageIndex");
			String orderType = parameter.getData().getString("orderType") == null ? "1"
					: parameter.getData().getString("orderType");

			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.parseInt(parameter.getUserId());
				// statusMap=
				// UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/getPartnerOrder?&user_id="+userId+"&time_num="+time_num+"&seach="+seach+"&p="+pageIndex+"&orderType="+orderType);
				statusMap = UrlUtil.getTaoKeToMap(url+"/home/taoke/getPartnerOrder?&user_id="+userId+"&time_num="+time_num+"&seach="+seach+"&p="
						+pageIndex+"&orderType="+orderType);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return statusMap;
	}

	// 周边店铺和各地特产分类
	@RequestMapping("/commodityType")
	@ResponseBody
	public Map<String, Object> commodityType(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer areaType = parameter.getData().getInteger("areaType") == null ? 1
						: parameter.getData().getInteger("areaType"); // 区域选项
																		// 0周边产品
																		// 1全国特产
				statusMap = UrlUtil.getTaoKeToMap(url
						+ "/home/taoke/commodityType?&areaType=" + areaType);
			} else {
				return JsonResponseUtil.getJson(-0x02, "用户不存在");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}

	// 优惠券
	@RequestMapping("/coupons")
	@ResponseBody
	public Map<String, Object> coupons(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.valueOf(parameter.getUserId());
				Integer ticketId = parameter.getData().getInteger("ticketId") == null ? 0
						: parameter.getData().getInteger("ticketId");
				Integer shareId = parameter.getData().getInteger("shareId") == null ? 811
						: parameter.getData().getInteger("shareId");
				statusMap = UrlUtil.getTaoKeToMap(sellerUrl
						+"/Json/Index/coupons?&ticketId="+ticketId
						+"&shareId="+shareId+"&userId="+userId);
			} else {
				return JsonResponseUtil.getJson(-0x02, "请先登录账号！");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}

	// 领券接口
	@RequestMapping("/achieve")
	@ResponseBody
	public Map<String, Object> achieve(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				Integer userId = Integer.valueOf(parameter.getUserId());
				Integer ticketId = parameter.getData().getInteger("ticketId") == null ? 0
						: parameter.getData().getInteger("ticketId");
				Integer shareId = parameter.getData().getInteger("shareId") == null ? userId
						: parameter.getData().getInteger("shareId");
				statusMap = UrlUtil.getTaoKeToMap(sellerUrl
						+"/Json/Index/achieve?&ticketId="+ticketId
						+"&shareId="+shareId+"&userId="+userId);
			} else {
				return JsonResponseUtil.getJson(-0x02, "请先登录账号！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}

	// 单个商品跳转链接
	@RequestMapping("/findGoodsUrl")
	@ResponseBody
	public Map<String, Object> findGoodsUrl(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);

			Integer source = parameter.getData().getInteger("source");
			String pid = parameter.getData().getString("pid") == null ? ""
					: parameter.getData().getString("pid");
			String goodsId = parameter.getData().getString("goodsId") == null ? ""
					: parameter.getData().getString("goodsId");
			if (source == null) {
				source = 0;
			}

			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}

			if (source == 0) {// 淘宝单个商品跳转链接
				if (StringUtils.isNotBlank(parameter.getUserId())) {
					statusMap = UrlUtil.getTaoKeToMap("http://www.518wtk.com/admin.php?s=/Interface/findGoodsUrl&pid="+pid+"&goodsId="+goodsId);

				} else {
					return JsonResponseUtil.getJson(-0x02, "请先登录账号！");
				}

			} else if (source == 3) {// 拼多多单个商品跳转链接

				// 通过pid 找到上级 上上级 代理的pid 放进去

				String custom_parameters = tkldPidService.findUp(pid); 

				
				
				Map<String, String> signMap = new HashMap<String, String>();
				signMap.put("client_id", StringUtil.pdd_client_id);
				String sign = MD5Util.getSign(signMap,
						StringUtil.pdd_client_screct);
				Map<String, Object> map = new HashMap<String, Object>();
				map = UrlUtil
						.getTaoKeToMap("http://open.aixiaoping.cn:8080/open/api/OpenPddPromotionUrl?data={\"client_id\":\""
								+ StringUtil.pdd_client_id
								+ "\",\"sign\":\""
								+ sign
								+ "\",\"data\":{\"goodsIdList\":\""
								+ goodsId + "\",\"pid\":\"" + pid + "\",\"custom_parameters\":\""+custom_parameters+"\"}}");
				if (map != null) {
					List<Object> goodUrlList = (List<Object>) map.get("goods_promotion_url_list");
					if(goodUrlList.size()>0 && goodUrlList != null){
						
						Map<String, Object> datail = (Map<String, Object>) goodUrlList.get(0);
						dataMap.put("link", datail.get("we_app_web_view_url"));// 唤醒拼多多app推广链接
						statusMap.put("status", 1);
						statusMap.put("message", "请求成功");
						statusMap.put("data", dataMap);
					}else{
						statusMap.put("status", -2);
						statusMap.put("message", "没有数据");
					}
				} else {
					statusMap.put("status", -2);
					statusMap.put("message", "请求失败");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}

	// 删除优惠券
	@RequestMapping("/delcoupon")
	@ResponseBody
	public Map<String, Object> delcoupon(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			if (StringUtils.isNotBlank(parameter.getUserId())) {
				String userId = parameter.getUserId();
				String couponId = parameter.getData().getString("couponId") == null ? ""
						: parameter.getData().getString("couponId");
				statusMap = UrlUtil.getTaoKeToMap(sellerUrl
						+ "/Json/Index/delcoupon?&userId=" + userId
						+ "&couponId=" + couponId);
			} else {
				return JsonResponseUtil.getJson(-0x02, "请先登录账号！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return statusMap;
	}

	@RequestMapping("/concurrentOrder")
	@ResponseBody
	public Map<String, Object> concurrentOrder(HttpServletRequest request,
			Integer pid) {
		try {
			if (pid == -1) {
				tkThePidOrderService.saveOrderAll();
			} else {
				tkThePidOrderService.saveOrUpdateOrder(pid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-1, e.getMessage());
		}
		return JsonResponseUtil.getJson(1, "同步成功");
	}

}

class GetData extends Thread {
	private Integer userId;

	public GetData(Integer userId) {
		this.userId = userId;
	}

	public void run() {
		UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/index?&user_id="
				+ userId);

	}
}

class GetGoods extends Thread {

	public GetGoods(String pid, Integer cid, Integer pageIndex, String tile,
			String keyword, String userId, String zoneId) {
		this.pid = pid;
		this.cid = cid;
		this.pageIndex = pageIndex;
		this.title = tile;
		this.keyword = keyword;
		this.userId = userId;
		this.zoneId = zoneId;
	}

	private String pid;
	private Integer cid;
	private Integer pageIndex;
	private String title;
	private String keyword;
	private String userId;
	private String zoneId;

	public void run() {
		UrlUtil.prestrain("http://www.518wtk.com/admin.php?s="
				+ "/Interface/getGoodsAjax&pid=" + pid + "&cid=" + cid
				+ "&page_num=" + pageIndex + "" + "&title=" + title
				+ "&keyword=" + keyword + "&user_id=" + userId + "&zone_id="
				+ zoneId);
	}
}
