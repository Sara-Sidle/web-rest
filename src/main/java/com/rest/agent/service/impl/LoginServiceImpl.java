package com.rest.agent.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.rest.util.ContentServerUtil;
import com.rest.util.DataUtil;
import com.rest.util.ContentServerUtil;
import com.rest.util.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rest.agent.dao.RyxSendMsgMapper;
import com.rest.agent.dao.RyxUserMobileMapper;
import com.rest.agent.dao.entity.RyxSendMsg;
import com.rest.agent.dao.entity.RyxUserMobile;
import com.rest.agent.service.ILoginService;
import com.rest.util.ContentServerUtil;
import com.rest.util.DataUtil;
import com.rest.util.http.ContentTypeUtil;
import com.rest.util.http.HttpUtils;
import com.ryx.framework.beans.ResultObject;
import com.ryx.framework.utils.Constants;
import com.ryx.framework.utils.DateUtil;

@Service
public class LoginServiceImpl implements ILoginService{
	
	private static final String RESET = "/platform/platform.do?method=updateUserPwd";
	
	@Autowired
	private RyxSendMsgMapper ryxSendMsgMapper;
	
	@Autowired
	private RyxUserMobileMapper ryxUserMobileMapper;
	
	@Autowired
	private DataUtil data;
	
	@Override
	public ResultObject smsLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultObject phoneAndPasswordLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultObject accountAndPasswordLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultObject sendSms(String phoneNo) {
		ResultObject result = new ResultObject(Constants.CODE_SUCCESS, Constants.MSG_SUCCESS);
//		RyxSendMsg sendMsg = new RyxSendMsg();
//		sendMsg.setMobile(phoneNo);
//		RyxSendMsg sendMsgCount = ryxSendMsgMapper.selectMsgCount(sendMsg);//查询验证码次数表
//		int count = sendMsgCount == null ? 0 : Integer.parseInt(sendMsgCount.getSendCount());
//		if(count > 20){
//			result.setCode("3000");//当日验证码达到上限
//		} else {
			//{send_code=5410, smsBatchId=0d8adbe95bfc4de0b46ce721bdfbbac3, rspCode=00, trancode=000030, http=200, rspMsg=succuess}
		    //TODO 短信发送 sms_send  branchid 字段为 agent 区分平台发送短信
			ContentServerUtil.sendMessage(phoneNo);
//			if("00".equals(smsResult.get("rspCode"))) {
//				String lastTime = DateUtil.formatDatetime(new Date((DateUtil.getCurrentTimeMillis() / 1000 + 600) * 1000), "yyyyMMddHHmmss");
//				sendMsg = new RyxSendMsg();
//				sendMsg.setMobile(phoneNo);
//				sendMsg.setSmsContent(smsResult.get("send_code"));
//				sendMsg.setRespSmsid(smsResult.get("smsBatchId"));
//				sendMsg.setTemplateId(smsResult.get("trancode"));
//				sendMsg.setFirstDate(DateUtil.getCurrentTime());
//				sendMsg.setLastDate(lastTime);
//				sendMsg.setStatus("1");
//				sendMsg.setInsertDate(new Date());
//				
//				Date now = new Date(); 
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//				String sysdate = dateFormat.format( now );
//				sendMsg.setSendDate(sysdate);
//				int num = 0;
//				if (count == 0) {// 不存在，insert
//					num = ryxSendMsgMapper.addSendMsgCount(sendMsg);	
//				} else {// 存在，+1	
//					num = ryxSendMsgMapper.updateSendMsgCount(sendMsg);	
//				}		
//			} else {
//	 			String time = DateUtil.getCurrentTime();
//				sendMsg = new RyxSendMsg();
//				sendMsg.setMobile(phoneNo);
//				sendMsg.setSmsContent(smsResult.get("send_code"));
//				sendMsg.setRespSmsid(smsResult.get("smsBatchId"));
//				sendMsg.setTemplateId(smsResult.get("trancode"));
//				sendMsg.setFirstDate(time);
//				sendMsg.setLastDate(time);
//				result.setCode("2000");
//				result.setMsg("短信发送失败");
//				sendMsg.setStatus("2");
//				sendMsg.setInsertDate(new Date());
//			}
//			if(sendMsg != null) ryxSendMsgMapper.insert(sendMsg);
//		}
		return result;
	}

}
