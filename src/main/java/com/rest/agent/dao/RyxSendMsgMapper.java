package com.rest.agent.dao;

import com.rest.agent.dao.entity.RyxSendMsg;
import com.rest.agent.dao.entity.RyxSendMsg;
import com.rest.agent.dao.entity.RyxSendMsg;
import java.util.Date;
import org.apache.ibatis.annotations.Param;

public interface RyxSendMsgMapper {
    int deleteByPrimaryKey(@Param("mobile") String mobile, @Param("insertDate") Date insertDate);

    int insert(RyxSendMsg record);

    int insertSelective(RyxSendMsg record);

    RyxSendMsg selectByPrimaryKey(@Param("mobile") String mobile, @Param("insertDate") String presentTime);
    
    int updateByPrimaryKeySelective(RyxSendMsg record);

    int updateByPrimaryKey(RyxSendMsg record);
    
    RyxSendMsg selectSendMsgCount(RyxSendMsg record);
    
    int addSendMsgCount(RyxSendMsg record);
    
    int updateSendMsgCount(RyxSendMsg record);
    
    RyxSendMsg selectMsgCount(RyxSendMsg record);
}