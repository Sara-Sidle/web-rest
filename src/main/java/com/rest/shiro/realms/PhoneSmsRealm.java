package com.rest.shiro.realms;

import java.util.Date;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.rest.agent.dao.RyxSendMsgMapper;
import com.rest.agent.dao.SmsSendMapper;
import com.rest.agent.dao.entity.RyxSendMsg;
import com.rest.agent.dao.entity.SmsSend;
import com.rest.shiro.tokens.MyExUsernamePasswordToken;
import com.ryx.framework.utils.DateUtil;

/**
 * 用户登录验证
 * 
 * @author
 * 
 */
public class PhoneSmsRealm extends AuthorizingRealm {

	@Autowired
	private SmsSendMapper smsSendMapper;


	public PhoneSmsRealm() {
		this.setAuthenticationTokenClass(MyExUsernamePasswordToken.class);

	}

	/**
	 * 获取用户权限信息<br>
	 * 角色<br>
	 * 权限<br>
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalcollection) {

		return null;
	}

	/**
	 * 清除缓存中的特定用户 身份验证 信息
	 * 
	 * @param principal
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, this.getName());
		this.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除缓存中的所有用户 身份验证 信息
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = this.getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	/**
	 * 获得身份验证信息 登录<br>
	 * // DisabledAccountException（禁用的帐号）、LockedAccountException（锁定的帐号）、
	 * UnknownAccountException（错误的帐
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationtoken)
			throws AuthenticationException {
		MyExUsernamePasswordToken token = (MyExUsernamePasswordToken) authenticationtoken;
		String  validTime = DateUtil.formatDatetime(new Date((DateUtil.getCurrentTimeMillis()/1000-600)*1000), "yyyyMMddHHmmss");
		SmsSend codeResult = smsSendMapper.selectPhoneCode(token.getUsername(), validTime);
		if(codeResult != null) {
			String content = codeResult.getContent();
			String codeDb = content.substring(content.indexOf(":") + 1, content.indexOf(":") + 5);
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				codeResult.getPhoneno(), codeDb,
				this.getName());
		return simpleAuthenticationInfo;
		}
		throw new UnknownAccountException("账号密码错误");
	}

}
