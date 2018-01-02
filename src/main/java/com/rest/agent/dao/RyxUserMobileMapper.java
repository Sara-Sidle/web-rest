package com.rest.agent.dao;

import java.util.List;
import java.util.Map;

import com.rest.agent.dao.entity.RyxUserMobile;
import com.rest.agent.dao.entity.RyxUserMobile;
import com.rest.agent.dao.entity.RyxUserMobile;

public interface RyxUserMobileMapper {

	/**
	 * 
	 * @param mobile
	 * @return
	 */
	int deleteByPrimaryKey(String mobile);

	/**
	 * 
	 * @param record
	 * @return
	 */
	int insert(RyxUserMobile record);

	/**
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(RyxUserMobile record);

	/**
	 * 
	 * @param mobile
	 * @return
	 */
	RyxUserMobile selectByPrimaryKey(String mobile);

	/**
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(RyxUserMobile record);

	/**
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(RyxUserMobile record);

	/**
	 * 
	 * @param mobile
	 * @return
	 */
	Map<String, String> selectRoleByPrimaryKey(String mobile);

	/**
	 * 
	 * @param mobile
	 * @return
	 */
	List<Map<String, String>> selectAgencyByMobile(String mobile);

	/**
	 * 根据登录账户用户名，查询代理商登录信息
	 * 
	 * @param account
	 * @return
	 */
	List<Map<String, String>> selectAgencyByAccount(String account);

}