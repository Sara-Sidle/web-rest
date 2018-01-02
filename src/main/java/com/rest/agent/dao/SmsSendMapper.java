package com.rest.agent.dao;

import org.apache.ibatis.annotations.Param;

import com.rest.agent.dao.entity.SmsSend;

public interface SmsSendMapper {
    int deleteByPrimaryKey(String id);

    int insert(SmsSend record);

    int insertSelective(SmsSend record);

    SmsSend selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SmsSend record);

    int updateByPrimaryKey(SmsSend record);
    
    SmsSend selectPhoneCode(@Param("mobile") String mobile, @Param("validtime") String validtime);
    
}