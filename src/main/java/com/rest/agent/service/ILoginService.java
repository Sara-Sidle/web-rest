package com.rest.agent.service;

import java.util.Map;

import com.rest.agent.dao.entity.RyxUserMobile;
import com.ryx.framework.beans.ResultObject;

public interface ILoginService {

	public ResultObject smsLogin();
	
	public ResultObject phoneAndPasswordLogin();
	
	public ResultObject accountAndPasswordLogin();
	
	public ResultObject sendSms(String phoneNo);
}
