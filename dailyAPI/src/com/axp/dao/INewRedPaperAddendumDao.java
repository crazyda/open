package com.axp.dao;

import com.axp.domain.NewRedPaperAddendum;

public interface INewRedPaperAddendumDao extends IBaseDao<NewRedPaperAddendum>{

	void updateOneAvail(Integer id,double userMoney);

}
