package com.rest.agent.dao;

import com.rest.agent.dao.entity.RyxUserAccountRelation;
import com.rest.agent.dao.entity.RyxUserAccountRelation;
import com.rest.agent.dao.entity.RyxUserAccountRelation;

import org.apache.ibatis.annotations.Param;

public interface RyxUserAccountRelationMapper {
    int deleteByPrimaryKey(@Param("agencyNo") String agencyNo, @Param("onlinechannel") String onlinechannel);

    int insert(RyxUserAccountRelation record);

    int insertSelective(RyxUserAccountRelation record);

    RyxUserAccountRelation selectByPrimaryKey(@Param("agencyNo") String agencyNo, @Param("onlinechannel") String onlinechannel);

    int updateByPrimaryKeySelective(RyxUserAccountRelation record);

    int updateByPrimaryKey(RyxUserAccountRelation record);
}