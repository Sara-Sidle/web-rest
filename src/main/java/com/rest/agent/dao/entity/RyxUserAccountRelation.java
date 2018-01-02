package com.rest.agent.dao.entity;

public class RyxUserAccountRelation {
    private String agencyNo;

    private String onlinechannel;

    private String timestamp;

    private String userId;

    private String relationTime;

    private String defaultAccount;

    public String getAgencyNo() {
        return agencyNo;
    }

    public void setAgencyNo(String agencyNo) {
        this.agencyNo = agencyNo == null ? null : agencyNo.trim();
    }

    public String getOnlinechannel() {
        return onlinechannel;
    }

    public void setOnlinechannel(String onlinechannel) {
        this.onlinechannel = onlinechannel == null ? null : onlinechannel.trim();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp == null ? null : timestamp.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRelationTime() {
        return relationTime;
    }

    public void setRelationTime(String relationTime) {
        this.relationTime = relationTime == null ? null : relationTime.trim();
    }

    public String getDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(String defaultAccount) {
        this.defaultAccount = defaultAccount == null ? null : defaultAccount.trim();
    }
}