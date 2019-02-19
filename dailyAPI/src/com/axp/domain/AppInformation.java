package com.axp.domain;

import java.sql.Timestamp;

/**
 * AppInformation entity. @author MyEclipse Persistence Tools
 */
public class AppInformation extends AbstractAppInformation implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AppInformation() {
	}

	/** minimal constructor */
	public AppInformation(Boolean isValid, String appVersion, String describe,
			Float AScore, Integer ADownloads, Float ASize,
			String ADirectDownload, String ADirectUrl, String AMarketDownload,
			String AMarketUrl, String AVersion, Float IScore,
			Integer IDownloads, Float ISize, String IMarketDownload,
			String IMarketUrl, String INewVersionContents) {
		super(isValid, appVersion, describe, AScore, ADownloads, ASize,
				ADirectDownload, ADirectUrl, AMarketDownload, AMarketUrl,
				AVersion, IScore, IDownloads, ISize, IMarketDownload,
				IMarketUrl, INewVersionContents);
	}

	/** full constructor */
	public AppInformation(Boolean isValid, Timestamp createTime,
			Timestamp updateTime, String appVersion, String describe,
			Float AScore, Integer ADownloads, Float ASize,
			String ADirectDownload, String ADirectUrl, String AMarketDownload,
			String AMarketUrl, String AVersion, Float IScore,
			Integer IDownloads, Float ISize, String IMarketDownload,
			String IMarketUrl, String INewVersionContents,Integer appType) {
		super(isValid, appVersion, describe, AScore, ADownloads, ASize, ADirectDownload, ADirectUrl, AMarketDownload, AMarketUrl, AVersion, IScore, IDownloads, ISize, IMarketDownload, IMarketUrl, INewVersionContents);
	}

}
