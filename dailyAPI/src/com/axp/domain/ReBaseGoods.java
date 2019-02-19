package com.axp.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.axp.dao.IReGoodsStandardDao;
import com.axp.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 六个商城商品的基础表，包含商城都需要的通用内容；
 *
 * @author Administrator
 */

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }) 
public class ReBaseGoods extends ReBaseDomain {
	
	
	
    private Integer baseGoodsId;//对应的基础商品id值；
    private ReGoodsSnapshot snapshotGoods;//此商城商品对应的基础商品的快照对象；
    private String standardDetails;//用户记录商品规格详细内容的json字符串；

    private Double displayPrice = 0d;//显示售价，即原价；
    private Integer defaultRepertory = 0;//默认库存；
    private Double price = 0d;//售价，商品的真实售价，并且，这个售价是用来展示商品时候的售价，也就是所有规格中最便宜的那个商品规格售价；
    private Integer score = 0;//积分，商品所需要的积分；
    private Double redPaper = 0d;//商品需要的红包金额；

    private Integer countLimit;

    private Integer transportationType = 1;//运输方式；
    private Double transportationPrice = 0d;//运输费用（如果需要运输费用的话）；

    private Timestamp addedTime; //上架时间
    private Timestamp shelvesTime;//下架时间
    private Timestamp endTime;
    private Boolean isChecked = false;//审核状态；
    
    private String want;
    private Integer startQuantity;
    private Integer isChange;
     
    private ReGoodsOfSellerMall reGoodsOfSellerMall;//周边店铺
    
    private String rightsProtect; //权益保障
    
	private String checkDesc;//审核描述；
	
	private int count;
	

    public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCheckDesc() {
		return checkDesc;
	}

	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}

	public String getWant() {
		return want;
	}

	public void setWant(String want) {
		this.want = want;
	}

	

	public Integer getStartQuantity() {
		return startQuantity;
	}

	public void setStartQuantity(Integer startQuantity) {
		this.startQuantity = startQuantity;
	}

	public Integer getIsChange() {
		return isChange;
	}

	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}

   public String getRightsProtect() {
		return rightsProtect;
	}

	public void setRightsProtect(String rightsProtect) {
		this.rightsProtect = rightsProtect;
	}
	private Boolean isNoStandard = false;//无商品规格；
    private Integer noStandardRepertory;//无商品规格时的库存；
    private Double noStandardPrice = 0d;//无商品规格时的真实售价；
    private Integer noStandardScore = 0;//无商品规格时的真实售价；
    private Double noStandardRedPaper = 0d;//无商品规格时的真实售价；
    /**
     * 运输方式
     */
    public static final Integer bao_you = 1;//包邮
    public static final Integer bu_bao_you = 2;//不包邮；
    public static final Integer shang_men_zi_qu = 3;//上门自取；
    public static final Integer dao_dian_xiao_fei = 4;//到店消费；
    public static final Integer shuang_fang_xie_shang=5; // 双方协商 
    private String goodsOrder;//商品序号；形成规则就是商城前缀+商品id；

    private Boolean isValid;//对应的基础商品id值；
    /**
     * 商城前缀；   	获取板块类工具类：AnalyzeMallUtil
     */
    public static final String SellerMall = "sem";
    public static final String ScoreMall = "scm";
    public static final String SeckillMall = "skm";
    public static final String LocalSpecialtyMall = "lsm";
    public static final String NineNineMall = "nnm";
    public static final String MemberMall = "mem";
    public static final String changeMall = "cha";
    public static final String teamMall = "tim";
    public static final String yhqMall = "yhq";
    public static final String lockMall = "ldm";

    private static String[] VIDEO_EXT = new String[]{".mp4", ".flv"};
    private static String[] PIC_EXT = new String[]{".jpg", ".jpeg", ".png", ".gif"};

    private Integer commentCount = 0;//评论总数；
    private Integer salesVolume = 0;//销售总量；

    private String api;
    
    
    
    private Boolean isNotChange;
    private Boolean isSendScore;
    private Integer sendScoreNum;
    private Integer weChatTypeId;
	public Integer getWeChatTypeId() {
		return weChatTypeId;
	}

	public void setWeChatTypeId(Integer weChatTypeId) {
		this.weChatTypeId = weChatTypeId;
	}

	public Boolean getIsNotChange() {
		return isNotChange;
	}

	public void setIsNotChange(Boolean isNotChange) {
		this.isNotChange = isNotChange;
	}

	


	public Boolean getIsSendScore() {
		return isSendScore;
	}

	public void setIsSendScore(Boolean isSendScore) {
		this.isSendScore = isSendScore;
	}

	public Integer getSendScoreNum() {
		return sendScoreNum;
	}

	public void setSendScoreNum(Integer sendScoreNum) {
		this.sendScoreNum = sendScoreNum;
	}
    
    

    //=============有关商品详细规格的一些方法开始=========

    /**
     * 通过商城字符串表示获取商城Integer标识；
     * 说明：
     * 商城有Integer的表示在ReGoodsOfBase类中；
     * 还有字符串表示，在ReBaseGoods类中；
     *
     * @param s
     * @return
     */
    @JsonIgnore
    public static Integer getMallTypeId(String s) {
        if (SellerMall.equals(s)) {
            return ReGoodsOfBase.sellerMall;
        } else if (ScoreMall.equals(s)) {
            return ReGoodsOfBase.scoreMall;

        } else if (SeckillMall.equals(s)) {
            return ReGoodsOfBase.seckillMall;

        } else if (LocalSpecialtyMall.equals(s)) {
            return ReGoodsOfBase.localSpecialtyMall;

        } else if (NineNineMall.equals(s)) {
            return ReGoodsOfBase.nineNineMall;

        } else if (MemberMall.equals(s)) {
            return ReGoodsOfBase.memberMall;
        }else if(teamMall.equals(s)){
        	return ReGoodsOfBase.teamMall;
        }else if(changeMall.equals(s)){
        	return ReGoodsOfBase.changeMall;
        }else if(yhqMall.equals(s)){
        	return ReGoodsOfBase.yhqMall;
        }else if(lockMall.equals(s)){
        	return ReGoodsOfBase.lockMall;
        }
        return 0;
    }

    /**
     * 通过商城字符串表示获取商城前序标识；
     * 说明：
     * 商城有Integer的表示在ReGoodsOfBase类中；
     * 还有字符串表示，在ReBaseGoods类中；
     *
     * @param s
     * @return
     */
    @JsonIgnore
    public static String getMallType(Integer mallType) {
        if (mallType == null) {
            return SellerMall;
        }
        int s = mallType.intValue();
        if (s == ReGoodsOfBase.sellerMall) {
            return SellerMall;
        } else if (s == ReGoodsOfBase.scoreMall) {
            return ScoreMall;
        } else if (s == ReGoodsOfBase.seckillMall) {
            return SeckillMall;
        } else if (s == ReGoodsOfBase.localSpecialtyMall) {
            return LocalSpecialtyMall;

        } else if (s == ReGoodsOfBase.nineNineMall) {
            return NineNineMall;

        } else if (s == ReGoodsOfBase.memberMall) {
            return MemberMall;
        }  else if(s ==ReGoodsOfBase.teamMall){
        	return teamMall;
        }else if(s ==ReGoodsOfBase.lockMall){
        	return lockMall;
        }
        
        return "";
    }

    /**
     * 通过goodsOrder获取商城的名称；
     */
    public static String getMallNameByGoodsOrder(String goodsOrder) {
        if (StringUtil.isEmpty(goodsOrder)) {
            return null;
        }
        if (goodsOrder.startsWith(SellerMall)) {
            return "ReGoodsOfSellerMall";
        } else if (goodsOrder.startsWith(ScoreMall)) {
            return "ReGoodsOfScoreMall";
        } else if (goodsOrder.startsWith(SeckillMall)) {
            return "ReGoodsOfSeckillMall";
        } else if (goodsOrder.startsWith(LocalSpecialtyMall)) {
            return "ReGoodsOfLocalSpecialtyMall";
        } else if (goodsOrder.startsWith(NineNineMall)) {
            return "ReGoodsOfNineNineMall";
        } else if (goodsOrder.startsWith(MemberMall)) {
            return "ReGoodsOfMemberMall";
        }else if (goodsOrder.startsWith(lockMall)) {
            return "ReGoodsOfLockMall";
        }
        return null;
    }

    /**
     * 获取邮递类型的中文名称；
     *
     * @return
     */
/*    @JsonIgnore
    public String getTransportationName() {
        if (transportationType == null) {
            return "请与商家联系";
        } else if (transportationType == 1) {
            return "包邮";
        } else if (transportationType == 2) {
            return "邮费" + this.getTransportationPrice();
        } else if (transportationType == 3) {
            return "到店消费";
        } else if (transportationType == 4) {
            return "上门自取";
        }
        return "";
    }*/
    
    @JsonIgnore
    public String getTransportationName() {
        if (transportationType == null) {
            return "请与商家联系";
        } else if (transportationType == 1) {
            return "全国包邮";
        } else if (transportationType == 2) {
            return "邮费:" + this.getTransportationPrice();
        } else if (transportationType == 3) {
            return "上门自取";
        } else if (transportationType == 4) {
            return "到店消费";
        }else if (transportationType == 5) {
            return "双方协商";
        }
        return "";
    }

    /**
     * 解析商品的五张预览图片，并将结果放到一个集合中；
     *
     * @return
     */
    @JsonIgnore
    public List<String> getListFormImageJson() {

        //返回值信息；
        List<String> list = new ArrayList<>();

        //获取数据；
        String pics = this.snapshotGoods.getDescriptionPics();
        if (!StringUtil.hasLength(pics)) {
            return list;
        }
        JSONArray imageArray = JSONObject.parseArray(pics);

        //解析数据；
        for (int i = 0; i < imageArray.size(); i++) {
            String image = imageArray.getJSONObject(i).getString("imgUrl");
            String ext = image.substring(image.lastIndexOf("."), image.length());//文件后序
            for (String s : PIC_EXT) {
                if (s.equals(ext)) {
                    list.add(image);
                }
            }
        }

        //返回结果；
        return list;
    }

    /**
     * 解析商品的五张预览图片，并将结果放到一个集合中；
     *
     * @return
     */
    @JsonIgnore
    public List<String> getListFormVideoJson() {

        //返回值信息；
        List<String> list = new ArrayList<>();

        //获取数据；
        String pics = this.snapshotGoods.getDescriptionPics();
        if (!StringUtil.hasLength(pics)) {
            return list;
        }
        JSONArray imageArray = JSONObject.parseArray(pics);

        //解析数据；
        for (int i = 0; i < imageArray.size(); i++) {
            String image = imageArray.getJSONObject(i).getString("imgUrl");
            String ext = image.substring(image.lastIndexOf("."), image.length());//文件后序
            for (String s : VIDEO_EXT) {
                if (s.equals(ext)) {
                    list.add(image);
                }
            }
        }

        //返回结果；
        return list;
    }

    /**
     * 获取手机端需要的该商品的json格式的图片信息；
     *
     * @param basePath 图片的基础路径；
     * @return
     */
    public List<Map<String, Object>> getImageJsonForPhone(String basePath) {

        //参数检查；
        if (StringUtil.isEmpty(basePath)) {
            basePath = "";
        }

        //装载最终返回值的List集合；
        List<Map<String, Object>> list = new ArrayList<>();

        //获取信息，并拼装json；
        List<String> imageList = getListFormImageJson();
        for (int i = 0; i < imageList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "");
            map.put("image", basePath + imageList.get(i));
           // map.put("image", "http://jifen.aixiaoping.cn:8080/dailyRes/upload-res/goodsDetails/1766/240/3423000830094031600.jpg");
            list.add(map);
        }

        //返回结果；
        return list;
    }

    public List<Map<String, Object>> getVideoJsonForPhone(String basePath) {

        //参数检查；
        if (StringUtil.isEmpty(basePath)) {
            basePath = "";
        }

        //装载最终返回值的List集合；
        List<Map<String, Object>> list = new ArrayList<>();

        //获取信息，并拼装json；
        List<String> imageList = getListFormVideoJson();
        for (int i = 0; i < imageList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "");
            map.put("image", basePath + imageList.get(i));
            list.add(map);
        }

        //返回结果；
        return list;
    }

    List<Map<String, Object>> firstStandardDetailsList = null;//一级商品规格的明细信息；
    List<Map<String, Object>> secondStandardDetailsList = null;//一级商品规格的明细信息；

    /**
     * 为了防止重复的执行这两个方法，定义一个初始化方法，执行一次；
     */
    private void init() {
        if (firstStandardDetailsList == null || secondStandardDetailsList == null) {
            firstStandardDetailsList = getParentStrandardList();//一级商品规格的明细信息；
            secondStandardDetailsList = getStandardDetailsList();//一级商品规格的明细信息；
        }
    }

    /**
     * 获取该商品的所有规格明细，及其每条规格明细对应的库存，价格，积分，红包信息；
     *
     * @return 返回的结果值是一个List集合；
     */
    public List<Map<String, Object>> getStandardDetailsList() {
        List<Map<String, Object>> list = new ArrayList<>();
        if (!StringUtil.hasLength(this.standardDetails)) {
            return list;
        }
        //检查json的完整性；
        JSONObject parseObject = JSONObject.parseObject(this.standardDetails);
        Integer success = parseObject.getInteger("success");
        if (success ==null || success != 1) {
            return list;
        }

        //获取需要解析的那个数组；
        JSONObject dataObject = parseObject.getJSONObject("data");
        JSONArray standardDetailsArray = dataObject.getJSONArray("standardDetails");

        //封装成List；
        for (int i = 0; i < standardDetailsArray.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            JSONObject each = standardDetailsArray.getJSONObject(i);
            map.put("id1", each.getString("id1"));
            map.put("name1", each.getString("name1"));
            map.put("id2", each.getString("id2"));
            map.put("name2", each.getString("name2"));
            map.put("id3", each.getString("id3"));
            map.put("name3", each.getString("name3"));
            map.put("repertory", each.getString("repertory"));
            map.put("price", each.getString("price"));
            map.put("redPaper", each.getString("redPaper"));
            map.put("score", each.getString("score"));
            list.add(map);
        }

        return list;
    }

    /**
     * 获取当前商品对应的一级商品规格的详细信息；
     * 包含：
     * 一级商品规格的id；
     * 一级商品规格的name；
     *
     * @return
     */
    public List<Map<String, Object>> getParentStrandardList() {
        List<Map<String, Object>> list = new ArrayList<>();
        if (!StringUtil.hasLength(this.standardDetails)) {
            return list;
        }

        //检查json的完整性；
        JSONObject parseObject = JSONObject.parseObject(this.standardDetails);
       
        Integer success = parseObject.getInteger("success");
        if (success==null || success != 1) {
            return list;
        }

        //获取需要解析的那个数组；
        JSONObject dataObject = parseObject.getJSONObject("data");
        JSONArray standardDetailsArray = dataObject.getJSONArray("parentStandard");

        //封装成List；
        for (int i = 0; i < standardDetailsArray.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            JSONObject each = standardDetailsArray.getJSONObject(i);
            map.put("standardId", each.getString("standardId"));
            map.put("standardName", each.getString("standardName"));
            list.add(map);
        }

        return list;
    }

    /**
     * 通过id的集合去寻找某条二级规格中的全部信息；
     *
     * @param idList
     * @return
     */
    public Map<String, Object> getStandardDetailByIdList(List<Integer> idList) {

        Integer id1 = idList.size() >= 1 ? idList.get(0) : null;
        Integer id2 = idList.size() >= 2 ? idList.get(1) : null;
        Integer id3 = idList.size() >= 3 ? idList.get(2) : null;
        return getStandardDetailByIds(id1, id2, id3);
    }

    /**
     * 根绝二级规格的id去寻找该条二级规格的全部信息，如果二级商品规格数量不足，使用null填充；
     *
     * @param id1 第一个二级商品规格的id；
     * @param id2 第二个二级商品规格的id；
     * @param id3 第三个二级商品规格的id；
     * @return
     */
    public Map<String, Object> getStandardDetailByIds(Integer id1, Integer id2, Integer id3) {

        //检查参数，并确定，该条明细由几个二级商品规格组成；
        //        if (id1 == null) {
        //            return null;
        //        }
        int idLength = id1 == null ? 0 : 1;
        if (id2 != null && id2 != 0) {
            idLength++;
            if (id3 != null && id3 != 0) {
                idLength++;
            }
        }

        //寻找匹配元素并返回；
        init();
        List<Map<String, Object>> standardDetailsList = secondStandardDetailsList;
        for (int i = 0; i < standardDetailsList.size(); i++) {
            Map<String, Object> eachMap = standardDetailsList.get(i);
            //根据传入的参数长度进行匹配；
            if (idLength == 1) {
                Integer i1 = Integer.parseInt(eachMap.get("id1").toString());
                if (id1.equals(i1)) {
                    return eachMap;
                }
            } else if (idLength == 2) {
                Integer i1 = Integer.parseInt(eachMap.get("id1").toString());
                Integer i2 = Integer.parseInt(eachMap.get("id2").toString());
                if (id1.equals(i1) && id2.equals(i2)) {
                    return eachMap;
                }
            } else if (idLength == 3) {
                Integer i1 = Integer.parseInt(eachMap.get("id1").toString());
                Integer i2 = Integer.parseInt(eachMap.get("id2").toString());
                Integer i3 = Integer.parseInt(eachMap.get("id3").toString());
                if (id1.equals(i1) && id2.equals(i2) && id3.equals(i3)) {
                    return eachMap;
                }
            }
        }
        return null;
    }

    /**
     * 根据二级商品规格的id去寻找对应的库存信息，如果二级商品规格数量不足，使用null填充；
     *
     * @param id1 第一个二级商品规格的id；
     * @param id2 第二个二级商品规格的id；
     * @param id3 第三个二级商品规格的id；
     * @return
     */
    public Integer getRepertoryByIds(Integer id1, Integer id2, Integer id3) {
        Map<String, Object> detailMap = getStandardDetailByIds(id1, id2, id3);
        if (detailMap != null) {
            return Integer.parseInt(detailMap.get("repertory").toString());
        }
        return null;
    }

    public Integer getRepertoryByIdList(List<Integer> idList) {

        //参数检查；
        if (idList == null || idList.size() == 0) {
            return null;
        }

        Map<String, Object> detailMap = getStandardDetailByIdList(idList);
        if (detailMap != null) {
            return Integer.parseInt(detailMap.get("repertory").toString());
        }
        return null;
    }

    /**
     * 根据规格的明细获取该规格商品的售价；
     *
     * @param id1 规格1；
     * @param id2 规格2；
     * @param id3 规格3；
     * @return
     */
    public Double getPriceByIds(Integer id1, Integer id2, Integer id3) {
        Map<String, Object> detailMap = getStandardDetailByIds(id1, id2, id3);
        if (detailMap != null) {
            return Double.parseDouble(detailMap.get("price").toString());
        }
        return null;
    }

    public Double getPriceByIdList(List<Integer> idList) {

        //参数检查；
        if (idList == null || idList.size() == 0) {
            return null;
        }

        Map<String, Object> detailMap = getStandardDetailByIdList(idList);
        if (detailMap != null) {
            return Double.parseDouble(detailMap.get("price").toString());
        }
        return null;
    }

    /**
     * 根据规格的明细获取该规格商品的积分；
     *
     * @param id1 规格1；
     * @param id2 规格2；
     * @param id3 规格3；
     * @return
     */
    public Integer getScoreByIds(Integer id1, Integer id2, Integer id3) {
        Map<String, Object> detailMap = getStandardDetailByIds(id1, id2, id3);
        if (detailMap != null) {
            return Integer.parseInt(detailMap.get("score").toString());
        }
        return null;
    }

    public Integer getScoreByIdList(List<Integer> idList) {

        //参数检查；
        if (idList == null || idList.size() == 0) {
            return null;
        }

        Map<String, Object> detailMap = getStandardDetailByIdList(idList);
        if (detailMap != null) {
            return Integer.parseInt(detailMap.get("score").toString());
        }
        return null;
    }

    /**
     * 根据规格的明细获取该规格商品的红包；
     *
     * @param id1 规格1；
     * @param id2 规格2；
     * @param id3 规格3；
     * @return
     */
    public Double getRedPaperByIds(Integer id1, Integer id2, Integer id3) {
        Map<String, Object> detailMap = getStandardDetailByIds(id1, id2, id3);
        if (detailMap != null) {
            return Double.parseDouble(detailMap.get("redPaper").toString());
        }
        return null;
    }

    public Double getRedPaperByIdList(List<Integer> idList) {

        //参数检查；
        if (idList == null || idList.size() == 0) {
            return null;
        }

        Map<String, Object> detailMap = getStandardDetailByIdList(idList);
        if (detailMap != null) {
            return Double.parseDouble(detailMap.get("redPaper").toString());
        }
        return null;
    }

    /**
     * 编辑商品具体商品规格明细的库存；
     * 返回值：
     * 1：表示一切正常；
     * 2：表示库存不足；
     *
     * @param id1
     * @param id2
     * @param id3
     * @param number 修改数量；
     * @param isAdd  是否是增加，默认为增加；
     * @return
     */
    public Integer editGoodsRepertory(Integer id1, Integer id2, Integer id3, Integer number, Boolean isAdd) {

        //检查参数；
        int idLength = 1;
        if (id2 != null && id2 != 0) {
            idLength++;
            if (id3 != null && id3 != 0) {
                idLength++;
            }
        }
        if (number == null || number < 1) {
            number = 0;
        }
        if (isAdd == null) {
            isAdd = true;
        }

        //一级商品规格信息；
        init();
        List<Map<String, Object>> parentStandardList = firstStandardDetailsList;

        //商品的具体规格对应的明细信息；
        List<Map<String, Object>> standardDetailsList = secondStandardDetailsList;

        //对standardDetailsList进行遍历，找出那条需要修改的数据进行编辑操作；
        if (parentStandardList != null && standardDetailsList != null) {
            for (Map<String, Object> eachDetails : standardDetailsList) {

                //根据传入的参数长度进行匹配,寻找库存；
                int repertory = -1;//现有库存量；
                if (idLength == 1) {
                    Integer i1 = Integer.parseInt(eachDetails.get("id1").toString());
                    if (id1.equals(i1)) {
                        repertory = Integer.parseInt(eachDetails.get("repertory").toString());
                    }
                } else if (idLength == 2) {
                    Integer i1 = Integer.parseInt(eachDetails.get("id1").toString());
                    Integer i2 = Integer.parseInt(eachDetails.get("id2").toString());
                    if (id1.equals(i1) && id2.equals(i2)) {
                        repertory = Integer.parseInt(eachDetails.get("repertory").toString());
                    }
                } else if (idLength == 3) {
                    Integer i1 = Integer.parseInt(eachDetails.get("id1").toString());
                    Integer i2 = Integer.parseInt(eachDetails.get("id2").toString());
                    Integer i3 = Integer.parseInt(eachDetails.get("id3").toString());
                    if (id1.equals(i1) && id2.equals(i2) && id3.equals(i3)) {
                        repertory = Integer.parseInt(eachDetails.get("repertory").toString());
                    }
                }

                //判断是增加还是减少库存；
                if (repertory > 0) {
                    if (isAdd) {
                        eachDetails.put("repertory", number + repertory);
                    } else {
                        //如果库存不足，则返回数据提醒；
                        if (repertory < number) {
                            return 2;
                        }
                        //扣减库存；
                        eachDetails.put("repertory", repertory - number);
                    }
                }
//                else {
//                    eachDetails.put("repertory", 0);
//                }
            }
        }

        //将更改后的数据重新设置回去；
        setStandardDetails(1, "一切正常", parentStandardList, standardDetailsList);

        return 1;
    }
    
    

    /**
     * 根据所给参数，生成json,并重新设置回去；
     *
     * @param success
     * @param msg
     * @param parentStandardList
     * @param standardDetailsList
     */
    public void setStandardDetails(Integer success, String msg, List<Map<String, Object>> parentStandardList, List<Map<String, Object>> standardDetailsList) {

        //封装到map中，并生成json；
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("msg", msg);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("parentStandard", parentStandardList);
        map2.put("standardDetails", standardDetailsList);
        map.put("data", map2);

        //重新设置standardD字段；
        standardDetails = JSONObject.toJSONString(map);
    }

    /**
     * 为手机端获取当前商品需要的那些一级商品规格的id和name，以及二级商品规格的id和那么；
     *
     * @return
     */
    public List<Map<String, Object>> getFirstStandardJsonForPhone() {

        //需要返回的数据；
        List<Map<String, Object>> resultList = new ArrayList<>();

        //获取一级商品规格明细；
        init();
        List<Map<String, Object>> parentStrandardList = firstStandardDetailsList;

        //获取二级商品规格明细；
        List<Map<String, Object>> standardDetailsList = secondStandardDetailsList;

        //获取二级商品规格明细(只包含二级规格的id和name)；
        List<Map<String, Object>> secondStandardDetails = getSecondStandardDetails(standardDetailsList);

        //获取数据，拼装json；
        for (int i = 0; i < parentStrandardList.size(); i++) {
            Map<String, Object> each = parentStrandardList.get(i);

            //检查这个一级商品规格是否有值，如果没有，则表示不用再执行循环了；
            String firstStandardId = each.get("standardId").toString();
            if (StringUtil.isEmpty(firstStandardId) || "0".equals(firstStandardId)) {
                break;
            }

            Map<String, Object> map = new HashMap<>();
            map.put("specId", each.get("standardId"));
            map.put("name", each.get("standardName"));
           
            //获取二级分类的id和name；
            List<Map<String, Object>> secondStandardList = new ArrayList<>();
            Map<String, Object> each2 = secondStandardDetails.get(i);

            Set<Entry<String, Object>> entrySet = each2.entrySet();
           
            for (Entry<String, Object> eachEntry : entrySet) {
            	LinkedHashMap<String,Object> m = new LinkedHashMap<>();
               // Map<String, Object> m = new HashMap<>();
                m.put("specItemId", eachEntry.getKey());
                m.put("specItemName", eachEntry.getValue());
                secondStandardList.add(m);
            }

            map.put("specItems", secondStandardList);

            resultList.add(map);
        }

        //返回结果；
        return resultList;

    }

    /**
     * 为手机端获取，需要的二级商品规格明细信息；
     * 包含：
     * 1，能唯一进行区分的key，这个key是所有规格明细的id组成的；
     * 2，词条规格明细，包含的库存售价，积分等各种信息；
     * 3, 积分商城在购买时有数量限制，需要在库存和用户能购买的数量之间做取舍，谁小选谁；
     */
    public List<Map<String, Object>> getSecondStandardJsonForPhone(Integer limitNumber) {

        //返回值；
        List<Map<String, Object>> returnList = new ArrayList<>();

        //获取二级商品规格明细；
        List<Map<String, Object>> standardDetailsList = getStandardDetailsList();

        //遍历填充数据；
        for (int i = 0; i < standardDetailsList.size(); i++) {
            Map<String, Object> each = standardDetailsList.get(i);
            Map<String, Object> map = new HashMap<>();
            //首先需要拼接key；
            List<String> idList = new ArrayList<>();
            for (int j = 1; j < 4; j++) {
                //判断获取的id是不是有值，如果没值就不用添加了；
                String id = (String) each.get("id" + j);
                if (StringUtil.isEmpty(id) || "0".equals(id)) {
                    break;
                }
                idList.add(id);
            }
            map.put("key", idList);

            //如果是积分商城，那么limitNumber就会为空，那么此时，就是谁小选谁；
            if (limitNumber != null) {
                int repertory = Integer.parseInt(each.get("repertory").toString());
                map.put("stockNumber", repertory > limitNumber ? limitNumber : repertory);
            } else {
                map.put("stockNumber", each.get("repertory"));
            }
            
            map.put("price", each.get("price"));
            map.put("costPrice", each.get("price"));
            map.put("score", each.get("score"));
            map.put("cashpoint", each.get("redPaper"));
            
            returnList.add(map);
        }

        return returnList;
    }
    
    /**
     * 为手机端获取，需要的二级商品规格明细信息第二版 用来控制新老版本秒杀和积分的价格；
     * 包含：
     * 1，能唯一进行区分的key，这个key是所有规格明细的id组成的；
     * 2，词条规格明细，包含的库存售价，积分等各种信息；
     * 3, 积分商城在购买时有数量限制，需要在库存和用户能购买的数量之间做取舍，谁小选谁；
     */
    public List<Map<String, Object>> getSecondStandardJsonForPhoneSkmOrScm(Integer limitNumber,Double price,Integer score) {

        //返回值；
        List<Map<String, Object>> returnList = new ArrayList<>();

        //获取二级商品规格明细；
        List<Map<String, Object>> standardDetailsList = getStandardDetailsList();

        //遍历填充数据；
        for (int i = 0; i < standardDetailsList.size(); i++) {
            Map<String, Object> each = standardDetailsList.get(i);
            Map<String, Object> map = new HashMap<>();
            //首先需要拼接key；
            List<String> idList = new ArrayList<>();
            for (int j = 1; j < 4; j++) {
                //判断获取的id是不是有值，如果没值就不用添加了；
                String id = (String) each.get("id" + j);
                if (StringUtil.isEmpty(id) || "0".equals(id)) {
                    break;
                }
                idList.add(id);
            }
            map.put("key", idList);

            //如果是积分商城，那么limitNumber就会为空，那么此时，就是谁小选谁；
            if (limitNumber != null) {
                int repertory = Integer.parseInt(each.get("repertory").toString());
                map.put("stockNumber", repertory > limitNumber ? limitNumber : repertory);
            } else {
                map.put("stockNumber", each.get("repertory"));
            }
            
            map.put("price", each.get("price"));
            map.put("costPrice", each.get("price"));
            map.put("score", each.get("score"));
            map.put("cashpoint", each.get("redPaper"));
            

            if(reGoodsOfSellerMall!=null&&goodsOrder.startsWith("skm")){
            	map.put("price", this.price);
                map.put("costPrice", this.price);
                map.put("score", this.score);
            }else if(reGoodsOfSellerMall!=null&&goodsOrder.startsWith("scm")){
            	if(this.noStandardScore!=null&&this.noStandardScore>0){
            		map.put("score", this.score);
            	}else{
            		int scores=(int)Math.ceil(Double.parseDouble(each.get("price").toString()));
            		map.put("score", scores);
            	}
            }else if(reGoodsOfSellerMall!=null&&goodsOrder.startsWith("ldm")){
            	 map.put("score", score);
            }
            
            
            returnList.add(map);
        }

        return returnList;
    }
    

    /**
     * 获取商品二级规格的明细；
     * 描述：
     * 作用是为手机端显示商品的一二级商品规格提供数据；
     * 只包含了二级规格的id和name；
     * 二级商品规格，在规格明细中可能存在重复，需要消除；
     *
     * @return
     */
    public List<Map<String, Object>> getSecondStandardDetails(List<Map<String, Object>> standardDetailsList) {

        //返回值；
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        //拼接数据；
        here:
        for (int i = 1; i < 4; i++) {//商品最多只有三个一级规格，所以最多只有三次循环；
           // Map<String, Object> map = new HashMap<String,Object>();
            LinkedHashMap<String,Object> map = new LinkedHashMap<>();
            for (int j = 0; j < standardDetailsList.size(); j++) {
                Map<String, Object> each = standardDetailsList.get(j);

                //因为一级规格最多有三个，最少有一个，所以，可能会出现第二三个一级商品规格没值的情况；
                if (j == 0 && StringUtil.isEmpty((String) each.get("id" + i))) {
                    break here;
                }

                map.put(each.get("id" + i).toString(), each.get("name" + i));
            }
            resultList.add(map);
        }

        //返回结果；
        return resultList;
    }

    /**
     * 根据商品的二级规格明细，获取购物车需要的规格明细字符串；
     *
     * @param detailIds
     * @return
     */
    public String getShoppingCarStandardDetails(List<Integer> detailIds) {

        int size = detailIds.size();//规格长度；

        //一级规格的详细信息；
        init();
        List<Map<String, Object>> parentStrandardList = firstStandardDetailsList;

        //二级规格的详细信息；
        Map<String, Object> standardDetailByIdList = getStandardDetailByIdList(detailIds);

        Map<String, Object> resultMap = new HashMap<>();

        Map<String, Object> firstStandardMap = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            firstStandardMap.put("id" + i, parentStrandardList.get(i - 1).get("standardId"));
            firstStandardMap.put("name" + i, parentStrandardList.get(i - 1).get("standardName"));
        }
        resultMap.put("firstStandard", firstStandardMap);

        Map<String, Object> secondStandardMap = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            secondStandardMap.put("id" + i, standardDetailByIdList.get("id" + i));
            secondStandardMap.put("name" + i, standardDetailByIdList.get("name" + i));
        }
        resultMap.put("secondStandard", secondStandardMap);

        return JSONObject.toJSONString(resultMap);
    }

    /**
     * 内部类：用来封装商品规格明细的对象；
     *
     * @author Administrator
     */
    public class StandardDetails {
        private List<Integer> parentStandardIds;//选择的一级商品规格的id值；
        private List<String> parentStandardNames;//选择的一级商品规格的name值；

        private List<Integer> place1Ids;//第一个位置的商品规格明细的id值；
        private List<String> place1Names;//第一个位置的商品规格明细的name值；

        private List<Integer> place2Ids;//第二个位置的商品规格明细的id值；
        private List<String> place2Names;//第二个位置的商品规格明细的name值；

        private List<Integer> place3Ids;//第三个位置的商品规格明细的id值；
        private List<String> place3Names;//第三个位置的商品规格明细的name值；

        private List<Integer> repertory;//库存量；
        private List<Double> price;//售价；
        private List<Double> redPaper;//红包；
        private List<Integer> score;//积分；

        //有参构造函数；
        public StandardDetails(List<Integer> parentStandardIds, List<String> parentStandardNames, List<Integer> place1Ids, List<String> place1Names, List<Integer> place2Ids, List<String> place2Names, List<Integer> place3Ids, List<String> place3Names, List<Integer> repertory, List<Double> price, List<Integer> score, List<Double> redPaper) {
            super();
            this.parentStandardIds = parentStandardIds;
            this.parentStandardNames = parentStandardNames;
            this.place1Ids = place1Ids;
            this.place1Names = place1Names;
            this.place2Ids = place2Ids;
            this.place2Names = place2Names;
            this.place3Ids = place3Ids;
            this.place3Names = place3Names;
            this.repertory = repertory;
            this.price = price;
            this.redPaper = redPaper;
            this.score = score;
        }

        /**
         * 获取包含所有数据的json格式的字符串；
         *
         * @return
         */
        public String getJson() {

            //最终需要返回的数据；
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", 0);

            //检查参数；
            int size = price.size();
            if (size < 0 || parentStandardIds.size() != parentStandardNames.size() || place1Ids.size() != size || place1Names.size() != size || repertory.size() != size) {
                map.put("msg", "参数异常");
                return JSONObject.toJSONString(map);
            }

            try {
                //装载所有的规格信息的data；
                Map<String, Object> dataMap = new HashMap<>();

                //将这个商品拥有的一级商品规格放到data的parentStandard中；
                List<Map<String, Object>> parentStandardList = new ArrayList<>();
                for (int i = 0; i < parentStandardIds.size(); i++) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("standardId", parentStandardIds.get(i));
                    m.put("standardName", parentStandardNames.get(i));
                    parentStandardList.add(m);
                }
                dataMap.put("parentStandard", parentStandardList);

                //将这个商品拥有的所有规格明细，放到data中standardDetails中；
                List<Map<String, Object>> standardDetailsList = new ArrayList<>();
                for (int i = 0; i < repertory.size(); i++) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id1", place1Ids.get(i));
                    m.put("name1", place1Names.get(i));

                    if (place2Ids.size() > 0) {
                        m.put("id2", place2Ids.get(i));
                        m.put("name2", place2Names.get(i));

                    }

                    if (place3Ids.size() > 0) {
                        m.put("id3", place3Ids.get(i));
                        m.put("name3", place3Names.get(i));
                    }
                    m.put("repertory", repertory.get(i));

                    if (price != null && price.size() > 0) {//如果有价格就添加价格，没有就设置为0；
                        m.put("price", price.get(i));
                    } else {
                        m.put("price", 0);
                    }

                    if (score != null && score.size() > 0) {//如果有积分就添加积分，没有就设置为0；
                        m.put("score", score.get(i));
                    } else {
                        m.put("score", 0);
                    }

                    if (redPaper != null && redPaper.size() > 0) {//如果有红包就添加红包，如果没有就设置为0；
                        m.put("redPaper", redPaper.get(i));
                    } else {
                        m.put("redPaper", 0);
                    }

                    standardDetailsList.add(m);
                }
                dataMap.put("standardDetails", standardDetailsList);

                map.put("data", dataMap);
                map.put("success", 1);
                map.put("msg", "操作成功");
                return JSONObject.toJSONString(map);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("msg", "生成json过程中抛出异常；");
                return JSONObject.toJSONString(map);
            }
        }

        //===========getAndSetter===========

        public List<Integer> getParentStandardIds() {
            return parentStandardIds;
        }

        public void setParentStandardIds(List<Integer> parentStandardIds) {
            this.parentStandardIds = parentStandardIds;
        }

        public List<String> getParentStandardNames() {
            return parentStandardNames;
        }

        public void setParentStandardNames(List<String> parentStandardNames) {
            this.parentStandardNames = parentStandardNames;
        }

        public List<Integer> getPlace1Ids() {
            return place1Ids;
        }

        public void setPlace1Ids(List<Integer> place1Ids) {
            this.place1Ids = place1Ids;
        }

        public List<String> getPlace1Names() {
            return place1Names;
        }

        public void setPlace1Names(List<String> place1Names) {
            this.place1Names = place1Names;
        }

        public List<Integer> getPlace2Ids() {
            return place2Ids;
        }

        public void setPlace2Ids(List<Integer> place2Ids) {
            this.place2Ids = place2Ids;
        }

        public List<String> getPlace2Names() {
            return place2Names;
        }

        public void setPlace2Names(List<String> place2Names) {
            this.place2Names = place2Names;
        }

        public List<Integer> getPlace3Ids() {
            return place3Ids;
        }

        public void setPlace3Ids(List<Integer> place3Ids) {
            this.place3Ids = place3Ids;
        }

        public List<String> getPlace3Names() {
            return place3Names;
        }

        public void setPlace3Names(List<String> place3Names) {
            this.place3Names = place3Names;
        }

        public List<Integer> getRepertory() {
            return repertory;
        }

        public void setRepertory(List<Integer> repertory) {
            this.repertory = repertory;
        }

        public List<Double> getPrice() {
            return price;
        }

        public void setPrice(List<Double> price) {
            this.price = price;
        }

        public List<Double> getRedPaper() {
            return redPaper;
        }

        public void setRedPaper(List<Double> redPaper) {
            this.redPaper = redPaper;
        }

        public List<Integer> getScore() {
            return score;
        }

        public void setScore(List<Integer> score) {
            this.score = score;
        }

    }

    //=============有关商品详细规格的一些方法结束=========

    public Integer getBaseGoodsId() {
        return baseGoodsId;
    }

    public void setBaseGoodsId(Integer baseGoodsId) {
        this.baseGoodsId = baseGoodsId;
    }

    public String getStandardDetails() {
        return standardDetails;
    }

    public void setStandardDetails(String standardDetails) {
        this.standardDetails = standardDetails;
    }

    public Integer getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(Integer transportationType) {
        this.transportationType = transportationType;
    }

    public ReGoodsSnapshot getSnapshotGoods() {
        return snapshotGoods;
    }

    public void setSnapshotGoods(ReGoodsSnapshot snapshotGoods) {
        this.snapshotGoods = snapshotGoods;
    }

    public Double getTransportationPrice() {
        return transportationPrice;
    }

    public void setTransportationPrice(Double transportationPrice) {
        this.transportationPrice = transportationPrice;
    }

    public Double getDisplayPrice() {
    	if(displayPrice==null){
    		displayPrice = price;
    	}
        return displayPrice;
    }

    public void setDisplayPrice(Double displayPrice) {
        this.displayPrice = displayPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getRedPaper() {
        return redPaper;
    }

    public void setRedPaper(Double redPaper) {
        this.redPaper = redPaper;
    }

    public String getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(String goodsOrder) {
        this.goodsOrder = goodsOrder;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getSalesVolume() {
    	if(salesVolume==null){
    		return 0;
    	}
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

//    public Integer getDefaultRepertory() {
//        if (defaultRepertory == 0 && StringUtils.isNotEmpty(standardDetails)) {
//            JSONObject object = JSONObject.parseObject(standardDetails);
//            if (object.getInteger("success")!=null&&object.getInteger("success").equals(1)) {
//                JSONArray array = object.getJSONObject("data").getJSONArray("standardDetails");
//                for (int i = 0; i < array.size(); i++) {
//                    defaultRepertory += array.getJSONObject(i).getInteger("repertory");
//                }
//            }
//        }
//        if (defaultRepertory == 0 && noStandardRepertory != null) {
//            defaultRepertory = noStandardRepertory;
//        }
//        return defaultRepertory;
//    }
    
    public Integer getDefaultRepertory() {
    	defaultRepertory = 0;
    	JSONArray array = null;//规格集合明细
    	JSONObject object = JSONObject.parseObject(standardDetails);
    	if(object!=null&&object.getInteger("success")!=null&&object.getInteger("success").equals(1)){
    		array = object.getJSONObject("data").getJSONArray("standardDetails");
    	}
        if (array!=null&&array.size()>0) {
            for (int i = 0; i < array.size(); i++) {
            	defaultRepertory += array.getJSONObject(i).getInteger("repertory")==null?0:array.getJSONObject(i).getInteger("repertory");
            }
        } else if (noStandardRepertory != null) { 
        	defaultRepertory = noStandardRepertory;
        }
        return defaultRepertory;
    }
    
    public void setDefaultRepertory(Integer defaultRepertory) {
        this.defaultRepertory = defaultRepertory;
    }

    public Integer getCountLimit() {
        return countLimit;
    }

    public void setCountLimit(Integer countLimit) {
        this.countLimit = countLimit;
    }

    public Timestamp getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Timestamp addedTime) {
        this.addedTime = addedTime;
    }

    public Timestamp getShelvesTime() {
        return shelvesTime;
    }

    public void setShelvesTime(Timestamp shelvesTime) {
        this.shelvesTime = shelvesTime;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Boolean getIsNoStandard() {
        return isNoStandard;
    }

    public void setIsNoStandard(Boolean isNoStandard) {
        this.isNoStandard = isNoStandard;
    }

    public Integer getNoStandardRepertory() {
        return noStandardRepertory;
    }

    public void setNoStandardRepertory(Integer noStandardRepertory) {
        this.noStandardRepertory = noStandardRepertory;
    }

    public Double getNoStandardPrice() {
        return noStandardPrice==null?0d:noStandardPrice;
    }

    public void setNoStandardPrice(Double noStandardPrice) {
        this.noStandardPrice = noStandardPrice;
    }

    public Integer getNoStandardScore() {
        return noStandardScore==null?0:noStandardScore;
    }

    public void setNoStandardScore(Integer noStandardScore) {
        this.noStandardScore = noStandardScore;
    }

    public Double getNoStandardRedPaper() {
        return noStandardRedPaper==null?0d:noStandardRedPaper;
    }

    public void setNoStandardRedPaper(Double noStandardRedPaper) {
        this.noStandardRedPaper = noStandardRedPaper;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

	
	
	

	public ReGoodsOfSellerMall getReGoodsOfSellerMall() {
		return reGoodsOfSellerMall;
	}

	public void setReGoodsOfSellerMall(ReGoodsOfSellerMall reGoodsOfSellerMall) {
		this.reGoodsOfSellerMall = reGoodsOfSellerMall;
	}

	public List<Map<String, Object>> getSpecifications(){
			JSONObject json=JSONObject.parseObject(this.standardDetails);
			JSONObject jsonData = json.getJSONObject("data");
			JSONArray standardArray=jsonData.getJSONArray("standardDetails");
			List<Map<String, Object>> list=new ArrayList<>();
			for (int i = 0; i < standardArray.size(); i++) {
				Map<String, Object> map=new HashMap<String, Object>();
				JSONObject obj = standardArray.getJSONObject(i);
				map.put("specId", obj.getInteger("id1"));
				map.put("specStr", obj.getString("name1"));
				map.put("price", obj.getDouble("price")==null?"0d":obj.getDouble("price").toString());
				map.put("stock", obj.getInteger("repertory"));
				list.add(map);
			}
		return list;
	}
	
	public List<Map<String, Object>> getIsNotSpecifications(){
		
		List<Map<String, Object>> list=new ArrayList<>();
		
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("specId", -1);
			map.put("specStr", "");
			map.put("price", this.noStandardPrice.toString());
			map.put("stock", this.noStandardRepertory.toString());
			list.add(map);
		
	return list;
}
public JSONArray getRightsProtectToJson() {
    	
    	if(StringUtils.isNotBlank(this.rightsProtect)){
    		JSONArray array=JSONArray.parseArray(this.rightsProtect);
    		return array;
    	}else{
    		return new JSONArray();
    	}
	}
	
	
	
}
