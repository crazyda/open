package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractGetmoneyRecord entity provides the base persistence definition of the
 * GetmoneyRecord entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractGetmoneyRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private Promoter promoter;
	private Double money;
	private String remark;
	private Integer status;
	private Boolean isValid;
	private Timestamp createTime;
	private Users users;
	private Integer type;
	private String name;
	private String account;
	private String address;
	private Double counterFee;
	// Constructors

	
	
	//提现支付状态；
    public static final Integer wei_zhi_fu = 0;//未支付状态；
    public static final Integer yi_shen_he =3;//改为已审核
    public static final Integer zhi_fu_cheng_gong =6;//支付成功
    public static final Integer zhi_fu_shi_bai =9;//支付失败
    public static final Integer yi_zhi_fu =10;//已支付；
    
    
    
    
    public static String getStatus(Integer status){
    	if(status.equals(wei_zhi_fu)){
    		return "(待审核)";
    	}
    	if(status.equals(yi_shen_he)){
    		return "(已审核)";
    	}
    	if(status.equals(zhi_fu_cheng_gong)){
    		return "(银行待支付)";
    	}
    	if(status.equals(yi_zhi_fu)){
    		return "(支付完成)";
    	}
    	
    	return "未知状态";
    }
    
    
	public Double getCounterFee() {
		return counterFee;
	}

	public void setCounterFee(Double counterFee) {
		this.counterFee = counterFee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/** default constructor */
	public AbstractGetmoneyRecord() {
	}

	/** full constructor */
	public AbstractGetmoneyRecord(Promoter promoter, Double money,
			String remark, Integer status, Boolean isValid, Timestamp createTime) {
		this.promoter = promoter;
		this.money = money;
		this.remark = remark;
		this.status = status;
		this.isValid = isValid;
		this.createTime = createTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Promoter getPromoter() {
		return this.promoter;
	}

	public void setPromoter(Promoter promoter) {
		this.promoter = promoter;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}