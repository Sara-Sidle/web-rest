package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.rest.agent.dao.RyxSendMsgMapper;
import com.rest.agent.dao.RyxUserMobileMapper;
import com.rest.agent.dao.TestUserMapper;
import com.rest.publish.pulisher.AgentMsgPublisher;
import com.rest.util.ConfigUtil;
import com.ryx.framework.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-config.xml" })
public class TestJdbc {

	@Autowired
	private TestUserMapper userMapper;

	@Autowired
	private RyxUserMobileMapper ryxUserMobileMapper;
	
	@Autowired
	private RyxSendMsgMapper ryxSendMsgMapper;
	

	@Autowired
	private AgentMsgPublisher agentMsgPublisher;
	
	// @Autowired;
	private ConfigUtil configUtil;

	@Test
	public void testGetUserInfo() {
//		PageHelper.startPage(1, 3);
//		RyxUserMobile r = ryxUserMobileMapper.selectByPrimaryKey("18678876092");
//		Page a = userMapper.selectByAll();
//		System.out.println(r.getPasswd());
//		System.out.println(a.getPageNum());
//		System.out.println(a.getPages());
//		System.out.println(a.getTotal());
//		System.out.println(JSONObject.toJSONString(r));
	}

	// @Test
	public void testDataUtil() {
		//System.out.println(configUtil.getUrl());
	}
	
	@Test
	public void testSendMsg() {
		
		agentMsgPublisher.say("wukp");
//		System.out.println(JSONObject.toJSONString(
//				ryxSendMsgMapper.selectByPrimaryKey("18668917331", 
//						DateUtil.getCurrentTime())));
		
	}
}
