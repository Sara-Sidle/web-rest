package com.rest.shiro.realms;

import java.util.Map;

import com.rest.exception.PasswordNullException;
import com.rest.exception.PasswordNullException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.rest.agent.dao.RyxUserMobileMapper;
import com.rest.agent.dao.entity.RyxUserMobile;
import com.rest.exception.PasswordNullException;
import com.rest.shiro.tokens.MyExUsernamePasswordToken;

/**
 * 用户登录验证<br>
 * 新代理商平台代理商手机号和密码登录，根据用户选择代理商编号，判断是500还是600平台；
 * 注意：
 * 	1，代理商编号来自于两个系统，两个数据库ryxfr和ryxzf用户名下，要注意分辨和查询；
 *  2，新代理商平台用户名需要被授予查询ryxfr和ryxzf用户名的权限；
 *  3，后续获取菜单列表和连接的时候，要保留500或600平台的连接头；
 * @author
 * 
 */									     
public class PhonePwdRealm extends AuthorizingRealm {

	@Autowired
	private RyxUserMobileMapper ryxUserMobileMapper;

	// private final Log LOG;

	public PhonePwdRealm() {
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
//		User user = (User) principalcollection.fromRealm(this.getName()).iterator().next();
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
		RyxUserMobile user = new RyxUserMobile();
		System.out.println(token.getUsername());
		user = ryxUserMobileMapper.selectByPrimaryKey(token.getUsername());
		String username = (String) token.getPrincipal(); // 得到用户名
		String password = new String((char[]) token.getCredentials()); // 得到密码

		if (user != null) {
			if("".equals(user.getPasswd()) || user.getPasswd() == null) {
				throw new PasswordNullException("密码不存在");
			}
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getMobile(), user.getPasswd(), this.getName());
			return simpleAuthenticationInfo;
		} else {
			throw new UnknownAccountException("账号密码错误");
		}
		
	}

}
