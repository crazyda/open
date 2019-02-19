package com.axp.domain;

import java.sql.Timestamp;

/**
 * UsersOperationRecord entity. @author MyEclipse Persistence Tools
 */
public class UsersOperationRecord extends AbstractUsersOperationRecord
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public UsersOperationRecord() {
	}

	/** full constructor */
	public UsersOperationRecord(Integer userId, Timestamp createtime,
			String requsetUri, String data, Double lng, Double lat,
			String channelId, String os, String machineCode, String appVersion,
			Integer zoneId, Integer adminuserId ) {
		super(userId, createtime, requsetUri, data, lng, lat, channelId, os, machineCode, appVersion, zoneId, adminuserId);
	}

}
