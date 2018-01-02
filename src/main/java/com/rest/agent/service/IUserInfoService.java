package com.rest.agent.service;

import java.util.Map;

import com.ryx.framework.beans.ResultObject;

public interface IUserInfoService {
	
	/**
	 * test
	 * @param pageNum
	 * @param pageSize
	 * @param order
	 * @return
	 */
	public ResultObject searchUserInfo(int pageNum, int pageSize, String order);
	
	/**
	 * test
	 * @param pageNum
	 * @param pageSize
	 * @param order
	 * @return
	 */
	public ResultObject addUserInfo();
	
	/**
	 * 修改用户的密码
	 * @param param
	 * @param loginType
	 * @return
	 */
	public ResultObject resetPassword(Map<String, String> param, String loginType);
	
	/**
	 * 查询用户密码是否存在
	 * @param mobile
	 * @return
	 */
	public ResultObject getUserExist(String mobile);
}
