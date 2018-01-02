package com.rest.agent.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rest.agent.service.ILoginService;
import com.rest.exception.PasswordNullException;
import com.rest.agent.service.ILoginService;
import com.rest.exception.PasswordNullException;
import com.rest.shiro.tokens.MyExUsernamePasswordToken;
import com.rest.util.DataUtil;
import com.rest.shiro.tokens.MyExUsernamePasswordToken;
import com.rest.util.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rest.agent.dao.RyxUserMobileMapper;
import com.rest.agent.service.ILoginService;
import com.rest.exception.PasswordNullException;
import com.rest.shiro.tokens.MyExUsernamePasswordToken;
import com.rest.util.DataUtil;
import com.ryx.framework.beans.ResultObject;
import com.ryx.framework.utils.Constants;

/**
 * 
 * @author
 * 
 */
@Controller
public class CommonLoginController {
	
	private static final String QT_ZF = "/ryxzf";
	private static final String QT_RF = "/qtfr";
	
	@Autowired
	private RyxUserMobileMapper ryxUserMobileMapper;

	@Autowired
	private ILoginService loginServiceImpl;
	
	@Autowired
	private DataUtil data;


	/**
	 * 进入登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String getLogin() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return "/index";
		}
		return "/login";
	}

	/**
	 * 进入重新登录页面 客户被踢出后的调用方法
	 * 
	 * @return
	 */
	@RequestMapping(value = "/kickOut")
	public String kickOut() {
		return "/logout";
	}

	/**
	 * 代理商平台登录<br>
	 * 用户名 密码登录//手机短信登陆<br>
	 * 
	 * @param param
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/dologin")
	@ResponseBody
	public ResultObject getDoLogin(@RequestBody Map<String, String> param, HttpSession session, HttpServletResponse response) {
		ResultObject result = null;
		String userName = param.get("username");
		String password = param.get("password");
		
		// 创建用户名和密码的令牌// PHONESMS("PhoneSms"), PHONEPWD("PhonePwd"), ACCOUNTPWD("AccountPwd");
		if (param.get("loginType").toString().equals("AccountPwd") || param.get("loginType").toString().equals("PhonePwd")) {
			Session se = SecurityUtils.getSubject().getSession();
			String code = (String) se.getAttribute("KAPTCHA_SESSION_KEY");
			if(!param.get("code").equals(code)) {
				result = new ResultObject("2002", "验证码错误");
				return result;
			}
		}
		
		MyExUsernamePasswordToken token = new MyExUsernamePasswordToken(userName, password, param.get("loginType"));
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				System.err.println("本地不允许登录多个账户");
				result = new ResultObject(Constants.CODE_FAIL, "本地不允许登录多个账户");
				return result;
			}
			subject.login(token);
			System.err.println("用户： " + userName + " 登录验证：" + subject.isAuthenticated());
			if (subject.isAuthenticated()) {
				Session secruytySession = SecurityUtils.getSubject().getSession();
				// Cookie名字和值都不能包含空白字符以及下列字符：[ ] ( ) = , " / ? @ : ;
				// 所以添加操作员的时候要进行过滤
				// 添加cookie
				Cookie opCookie = new Cookie("loginType", param.get("loginType").toString());
				opCookie.setMaxAge(-1);
				opCookie.setPath("/");
				response.addCookie(opCookie);
				// 设置登录方式（三选一）
				secruytySession.setAttribute("loginType", param.get("loginType").toString());

				if (param.get("loginType").toString().equals("PhoneSms") || param.get("loginType").toString().equals("PhonePwd")) {
					secruytySession.setAttribute("mobile", userName);
					
					// 操作员编号
					opCookie = new Cookie("mobile", userName);
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
				} else {
					secruytySession.setAttribute("account", userName);
					opCookie = new Cookie("account", userName);
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
				}
				result = new ResultObject(Constants.CODE_SUCCESS, Constants.MSG_SUCCESS);
			}
		} catch (UnknownAccountException ex) {// 用户名没有找到。
			result = new ResultObject(Constants.CODE_FAIL, "账号不存在");
		} catch (IncorrectCredentialsException ex) {// 用户名密码不匹配。
			result = new ResultObject(Constants.CODE_FAIL, "账号/密码错误");
		} catch(PasswordNullException e) {
			result = new ResultObject("2001", "您的账号尚未激活，请使用短信登录。"); 
		}  catch (AuthenticationException e) {// 其他的登录错误
			result = new ResultObject(Constants.CODE_SYSTEM_ERROR, "登录失败");
		}
		return result;
	}

	/**
	 * 进入选择代理商编号页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/choseAgent", method = { RequestMethod.GET, RequestMethod.POST })
	public String getIndex(HttpServletRequest request, ModelMap model) {
		Session secruytySession = SecurityUtils.getSubject().getSession();
		String loginType = secruytySession.getAttribute("loginType").toString();
		
		model.put("loginType", loginType);
		// 手机号登录
		// PHONESMS("PhoneSms"), PHONEPWD("PhonePwd"), ACCOUNTPWD("AccountPwd");
		if (StringUtils.isNotBlank(loginType) && (loginType.equals("PhonePwd") || loginType.equals("PhoneSms"))) {
			String mobile = secruytySession.getAttribute("mobile").toString();
			List<Map<String, String>> agentMap = ryxUserMobileMapper.selectAgencyByMobile(mobile);
			model.put("agentMap", agentMap);
			model.put("mobile", mobile);
		} else {// 非手机号登录
			String agencyId = secruytySession.getAttribute("account").toString();
			List<Map<String, String>> agentMap = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("agencyId", agencyId);
			map.put("companyName", agencyId);
			agentMap.add(map);
			model.put("agentMap", agentMap);
			model.put("agencyId", agencyId);
		}

		String sessionId = request.getSession().getId();
		model.put("sessionId", sessionId);
		return "/choseAgent";
	}

	/**
	 * 进入主要页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{agencyId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String gotoMain(@PathVariable("agencyId") String agencyId, HttpServletRequest request, ModelMap model, HttpServletResponse response) {
		Session secruytySession = SecurityUtils.getSubject().getSession();
		String loginType = secruytySession.getAttribute("loginType").toString();
		String sessionId = secruytySession.getId().toString();
		model.put("loginType", loginType);
		Cookie opCookie = new Cookie("agencyId", agencyId);
		opCookie.setMaxAge(-1);
		opCookie.setPath("/");
		response.addCookie(opCookie);
		
		opCookie = new Cookie("sessionId", sessionId);
		opCookie.setMaxAge(-1);
		opCookie.setPath("/");
		response.addCookie(opCookie);
		// 手机号登录
		// PHONESMS("PhoneSms"), PHONEPWD("PhonePwd"), ACCOUNTPWD("AccountPwd");
		if (StringUtils.isNotBlank(loginType) && (loginType.equals("PhonePwd") || loginType.equals("PhoneSms"))) {
			String mobile = secruytySession.getAttribute("mobile").toString();
			model.put("mobile", mobile);
			model.put("agencyId", agencyId);
			model.put("sessionId", sessionId);
		} else {// 非手机号登录
			model.put("agencyId", agencyId);
			model.put("sessionId", sessionId);
		}
		//将用户选择的代理商编号，写入session
		secruytySession.setAttribute("agencyId", agencyId);
		
		if (agencyId.substring(0,1).equals("6")) {
			model.put("http", data.getQtUrlPath() + QT_ZF);
		} else {
			model.put("http", data.getQtUrlPath() + QT_RF);
		}
		return "/index";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/home")
	public String getHome(HttpServletRequest request, ModelMap model, HttpServletResponse response) {
		return "/";
	}

	@RequestMapping(value = "/homePage")
	public String getAffiche() {
		return "/homePage";
	}
	
	/**
	 * 验证码发送
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/sendMsg")
	@ResponseBody
	public ResultObject sendSmsMsgs(@RequestBody Map<String, String> param) {
		ResultObject result = null;
		result = loginServiceImpl.sendSms(param.get("phoneNo"));
		return result;
	}
	/**
	 * 用户登录超时
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login_timeout", method = RequestMethod.GET)
	public String timeout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		SecurityUtils.getSubject().logout();
		return "redirect:login";
	}

	/**
	 * 登录状态心跳
	 * */
	@RequiresRoles("AGENT")
	@RequestMapping(value = "/chkLogined", method = { RequestMethod.GET, RequestMethod.POST })
	public String chkLogined() {
		Subject currentUser = SecurityUtils.getSubject();
		String flag = Boolean.toString(currentUser.isAuthenticated());
		System.err.println(flag);
		return flag;
	}

	/**
	 * Home界面的专属退出
	 * 
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/homelogout")
	public String homelogout(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "type", required = false)
	String type) {
		SecurityUtils.getSubject().logout();
		return "redirect:login";
	}
	
}
