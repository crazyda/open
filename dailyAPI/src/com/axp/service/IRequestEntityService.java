package com.axp.service;import com.axp.domain.RequestEntity;public interface IRequestEntityService extends IBaseService<RequestEntity> {	/**	 * 根据请求的名称找寻请求的实体类；	 * @param ret	 * @return	 * @throws Exception 	 */	RequestEntity getRequestEntityByRequestName(String requestName) throws Exception;		}