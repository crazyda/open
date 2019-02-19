package com.axp.dao;

import java.util.List;

import com.axp.domain.OrderComment;


public interface OrderCommentDAO extends IBaseDao<OrderComment> {

	List<OrderComment> findListBySnapGoodId(Integer snapshotId);

	Integer findCountBySnapGoodId(Integer snapshotId);

	Double findAvgBySnapGoodId(Integer snapshotId);

	List<OrderComment> findListBySnapGoodId(Integer snapshotId, Integer start,
			Integer size);

}
