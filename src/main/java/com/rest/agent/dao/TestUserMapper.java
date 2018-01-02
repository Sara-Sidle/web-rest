package com.rest.agent.dao;

import java.util.List;

import com.github.pagehelper.Page;
import com.rest.agent.dao.entity.TestUser;
import com.rest.agent.dao.entity.TestUser;
import com.rest.agent.dao.entity.TestUser;

public interface TestUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TestUser record);

    int insertSelective(TestUser record);

    TestUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TestUser record);

    int updateByPrimaryKey(TestUser record);
    
    Page<TestUser> selectByAll();
    
    int insertBatch(List<TestUser> record);
}