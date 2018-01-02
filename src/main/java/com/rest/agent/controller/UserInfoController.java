package com.rest.agent.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rest.agent.dao.entity.Terminal;
import com.rest.agent.service.IUserInfoService;
import com.ryx.framework.beans.ResultObject;

@Controller
@RequestMapping(value="/user") 
public class UserInfoController {
	
	@Autowired
	private IUserInfoService userInfoImpl;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getList() {
		return "terminalManager/terminal_list";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public ResultObject getUserList(@RequestParam(defaultValue = "1", value ="pageNum") String pageNum, 
			@RequestParam(defaultValue = "30", value ="pageSize") String pageSize,
			@RequestParam(defaultValue = "asc", value ="order") String order,
			Terminal ter) {
		
		return userInfoImpl.searchUserInfo(Integer.valueOf(pageNum), Integer.valueOf(pageSize), order);
	}
	
	
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	@ResponseBody
	public void addUserInfo(@RequestBody Map param) {
		userInfoImpl.addUserInfo();
	}
	
	
	/**
	 * 登录密码修改
	 * @param param password
	 * @return
	 * 
	 */
	@RequestMapping(value = "/reset/password")
	@ResponseBody
	public ResultObject resetPassword(@RequestBody Map<String, String> param, ModelMap model) {
		ResultObject result = null;
		Session secruytySession = SecurityUtils.getSubject().getSession();
		String loginType = secruytySession.getAttribute("loginType").toString();
		// 手机号登录
		// PHONESMS("PhoneSms"), PHONEPWD("PhonePwd"), ACCOUNTPWD("AccountPwd");
		if (StringUtils.isNotBlank(loginType) && (loginType.equals("PhonePwd") || loginType.equals("PhoneSms"))) {
			param.put("mobile",secruytySession.getAttribute("mobile").toString());
		} else {// 非手机号登录
			param.put("agencyId",secruytySession.getAttribute("agencyId").toString());
		}
		result = userInfoImpl.resetPassword(param, loginType);
		return result;
	}
	
	
	/**
	 * 首次登录验证
	 * @param param password
	 * @return
	 * 
	 */
	@RequestMapping(value = "/password/exist")
	@ResponseBody
	public ResultObject getUserExist(@RequestBody Map<String, String> param) {
		ResultObject result = null;
		if(param.containsKey("mobile")) {
			result = userInfoImpl.getUserExist(param.get("mobile"));
		} else {
			result = new ResultObject("2000", "手机号不存在");
		}
		return result;
	}
	
}
