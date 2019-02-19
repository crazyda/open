package com.axp.dao;

import com.axp.domain.NewRedPaperSetting;

public interface INewRedPaperSettingDao extends IBaseDao<NewRedPaperSetting>{

	public void updateAllNunUsed(Integer id, Integer value, String string);

	Integer updateDaySurplus();

}
