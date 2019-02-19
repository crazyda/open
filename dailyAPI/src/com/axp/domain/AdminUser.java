package com.axp.domain;

/**
 * AdminUser entity. @author MyEclipse Persistence Tools
 */
public class AdminUser extends AbstractAdminUser implements java.io.Serializable {

	// Constructors

	private String levelname;

	private String fathername;
	private String proxyonename;

	private String centername;

	private Boolean isTeam; //是否开通拼团
	
	public Boolean getIsTeam() {
		return isTeam;
	}

	public void setIsTeam(Boolean isTeam) {
		this.isTeam = isTeam;
	}

	private Integer rePermissionRoleId;
	public void setProxyonename(String proxyonename) {
		this.proxyonename = proxyonename;
	}

	public void setCentername(String centername) {
		this.centername = centername;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public Integer getRePermissionRoleId() {
		return rePermissionRoleId;
	}

	public void setRePermissionRoleId(Integer rePermissionRoleId) {
		this.rePermissionRoleId = rePermissionRoleId;
	}

	/** default constructor */
	public AdminUser() {
	}

}
