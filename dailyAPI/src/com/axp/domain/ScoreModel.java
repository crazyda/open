package com.axp.domain;

import java.sql.Timestamp;

public class ScoreModel{
	public Integer score;
	public Integer type;
	public Timestamp acquireTime;
	
	
	public Timestamp getAcquireTime() {
		return acquireTime;
	}
	public void setAcquireTime(Timestamp acquireTime) {
		this.acquireTime = acquireTime;
	}
	public ScoreModel(){
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}