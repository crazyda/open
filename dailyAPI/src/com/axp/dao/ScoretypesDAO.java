package com.axp.dao;

import java.util.Map;

import com.axp.domain.Scoretypes;

public interface ScoretypesDAO extends IBaseDao<Scoretypes> {

	Map<Integer, String> getScoretypesRemarkMap();
}