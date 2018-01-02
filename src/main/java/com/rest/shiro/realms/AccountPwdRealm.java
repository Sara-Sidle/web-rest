package com.rest.shiro.realms;

import java.util.List;
import java.util.Map;

import com.rest.shiro.tokens.MyExUsernamePasswordToken;
import com.rest.shiro.tokens.MyExUsernamePasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.rest.agent.dao.RyxUserMobileMapper;
import com.rest.shiro.tokens.MyExUsernamePasswordToken;

/**
 * 用户登录验证<br>
 * 老分润平台代理商账户用户名和密码登录 注意： 1，代理商编号来自于两个系统，两个数据库ryxfr和ryxzf用户名下，要注意分辨和查询；
 * 2，新代理商平台用户名需要被授予查询ryxfr和ryxzf用户名的权限； 3，后续获取菜单列表和连接的时候，要保留500或600平台的连接头；
 * 
 * @author
 * 
 */
public class AccountPwdRealm extends AuthorizingRealm {

	private static Logger LOG = LoggerFactory.getLogger(AccountPwdRealm.class);

	@Autowired
	private RyxUserMobileMapper ryxUserMobileMapper;

	public AccountPwdRealm() {
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
		String username = (String) principalcollection.getPrimaryPrincipal();
		// User user = (User)
		// principalcollection.fromRealm(this.getName()).iterator().next();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Map<String, String> map = ryxUserMobileMapper.selectRoleByPrimaryKey(username);
		String roleId = map.get("roleId");
		if (StringUtils.isEmpty(roleId)) {
			authorizationInfo.addRole("AGENT");
		} else {
			authorizationInfo.addRole(roleId);
		}

		return authorizationInfo;
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
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationtoken) throws AuthenticationException {
		MyExUsernamePasswordToken token = (MyExUsernamePasswordToken) authenticationtoken;
		// 查询数据库，对比用户信息
		List<Map<String, String>> agentInfo = ryxUserMobileMapper.selectAgencyByAccount(token.getUsername());
		if (agentInfo == null || agentInfo.size() != 1) {
			LOG.error(token.getUsername()+"不存在或者存在多个用户信息。");
			throw new UnknownAccountException("账号不存在或密码错误。");
		} else {
			Map<String, String> agentMap = agentInfo.get(0);
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(agentMap.get("agencyId"), agentMap.get("password"), this.getName());

			return simpleAuthenticationInfo;
		}

	}

}
