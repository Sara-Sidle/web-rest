package com.rest.agent.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rest.util.DataUtil;
import com.rest.util.http.ContentTypeUtil;
import com.rest.util.http.HttpUtils;
import com.rest.util.DataUtil;
import com.rest.util.http.ContentTypeUtil;
import com.rest.util.http.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rest.agent.dao.RyxUserMobileMapper;
import com.rest.agent.dao.TestUserMapper;
import com.rest.agent.dao.entity.RyxUserMobile;
import com.rest.agent.dao.entity.TestUser;
import com.rest.agent.service.IUserInfoService;
import com.rest.util.DataUtil;
import com.rest.util.http.ContentTypeUtil;
import com.rest.util.http.HttpUtils;
import com.ryx.framework.beans.ResultObject;
import com.ryx.framework.utils.Constants;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

	@Autowired
	private TestUserMapper userMapper;
	
	private static final String RESET = "/platform/platform.do?method=updateUserPwd";
	
	@Autowired
	private RyxUserMobileMapper ryxUserMobileMapper;
	
	@Autowired
	private DataUtil data;
	
	@Override
	public ResultObject searchUserInfo(int pageNum, int pageSize, String order){
		ResultObject result = new ResultObject(Constants.CODE_SUCCESS, Constants.MSG_SUCCESS);
		PageHelper.startPage(pageNum, pageSize);
		Page pageResult = userMapper.selectByAll();
		result.setResult(pageResult.getResult());
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("pageSize", pageResult.getPageSize());
		pageMap.put("pageNum", pageResult.getPageNum());
		pageMap.put("pages", pageResult.getPages());
		pageMap.put("total", pageResult.getTotal());
		result.setPage(pageMap);
		return result;
	}

	@Override
	public ResultObject addUserInfo() {
		TestUser user = null;
		List<TestUser> list = new ArrayList<TestUser>();
		for (int i = 0; i < 2; i++) {
			user = new TestUser();
			user.setId(6L);
			user.setName("测试用户6");
			if(i == 1) {
				user.setId(1L);
			}
			list.add(user);
		}
		TestUser user1 = new TestUser();
		user1.setId(2L);
		user1.setName("测试用户22");
		userMapper.updateByPrimaryKeySelective(user1);
		userMapper.insertBatch(list);
		return null;
	}

	@Override
	public ResultObject resetPassword(Map<String, String> param, String loginType) {
		ResultObject result = new ResultObject(Constants.CODE_SUCCESS, Constants.MSG_SUCCESS);
		if (loginType.equals("PhonePwd") || loginType.equals("PhoneSms")) {
			RyxUserMobile userInfo = ryxUserMobileMapper.selectByPrimaryKey(param.get("mobile"));
			RyxUserMobile userMobile = null;
			if(StringUtils.isBlank(userInfo.getPasswd())) {
				userMobile = new RyxUserMobile();
				userMobile.setMobile(param.get("mobile"));
				userMobile.setPasswd(param.get("newPwd"));
				ryxUserMobileMapper.updateByPrimaryKeySelective(userMobile);
			} else {
				if(userInfo.getPasswd().equals(param.get("oldPwd"))) {
					userMobile = new RyxUserMobile();
					userMobile.setMobile(param.get("mobile"));
					userMobile.setPasswd(param.get("newPwd"));
					ryxUserMobileMapper.updateByPrimaryKeySelective(userMobile);
				} else {
					result.setCode("2000");
					result.setMsg("原始密码输入错误");
					return result;
				}
			}
			
		} else {// 非手机号登录
			Map<String, String> updateResult = JSONObject.parseObject(HttpUtils.httpForForm(param.get("httpUrl") + RESET,
					param, ContentTypeUtil.APPLICATION_FORM_URLENCODED), Map.class);
			
			if(!"true".equals(updateResult.get("success"))) {
				result.setCode("2000");
				result.setMsg(updateResult.get("message"));
			}
		}
		return result;
	}

	@Override
	public ResultObject getUserExist(String mobile) {
		ResultObject result = new ResultObject(Constants.CODE_SUCCESS, Constants.MSG_SUCCESS);
		RyxUserMobile userInfo = ryxUserMobileMapper.selectByPrimaryKey(mobile);
		Map<String, String> map = null;
		if(userInfo.getPasswd() == null) {
			map = new HashMap<String, String>();
			map.put("status", "null");
		} else {
			map = new HashMap<String, String>();
			map.put("status", "exist");
		}
		result.setResult(map);
		return result;
	}


}
