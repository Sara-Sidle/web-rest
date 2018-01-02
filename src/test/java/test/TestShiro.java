package test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;

public class TestShiro {

	public static void main(String[] args) {
		JSONObject.parseObject(JSONObject.toJSONString("111"), new TypeReference<ArrayList<?>>() {
		});
	}

//	@Test
//	public void testHelloworld() {
//		// 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
//		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//		// 2、得到SecurityManager实例 并绑定给SecurityUtils
//		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
//		SecurityUtils.setSecurityManager(securityManager);
//		// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
//		Subject subject = SecurityUtils.getSubject();
//		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123r");
//
//		try {
//			// 4、登录，即身份验证
//			subject.login(token);
//		} catch (AuthenticationException e) {
//			// 5、身份验证失败
//			e.printStackTrace();
//		}
//
////		Assert.assertEquals(true, ); // 断言用户已经登录
//
//		System.err.println(subject.isAuthenticated());
//		// 6、退出
//		subject.logout();
//	}
}
