package com.axp.dao;

import java.sql.Timestamp;
import java.util.List;

import com.axp.domain.NewRedPaperLog;

public interface INewRedPaperLogDao extends IBaseDao<NewRedPaperLog>{

	List<NewRedPaperLog> getMinNewRedPaperLog(Integer id, Timestamp createTime);

	List<Object[]> findBySql(String sql);

}
