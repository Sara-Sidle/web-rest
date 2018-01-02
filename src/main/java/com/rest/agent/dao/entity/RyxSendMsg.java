package com.rest.agent.dao.entity;

import java.util.Date;

public class RyxSendMsg {
    private String mobile;

    private String smsContent;

    private String templateId;

    private String respSmsid;

    private String failedReason;

    private String firstDate;

    private String lastDate;

    private String status;

    private Date insertDate;
    
    private String sendCount;//验证码次数
    private String sendDate;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getRespSmsid() {
        return respSmsid;
    }

    public void setRespSmsid(String respSmsid) {
        this.respSmsid = respSmsid == null ? null : respSmsid.trim();
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason == null ? null : failedReason.trim();
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate == null ? null : firstDate.trim();
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate == null ? null : lastDate.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendCount() {
		return sendCount;
	}

	public void setSendCount(String sendCount) {
		this.sendCount = sendCount;
	}
}