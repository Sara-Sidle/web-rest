package com.rest.agent.dao.entity;

public class SmsSend {
    private String id;

    private String phoneno;

    private String content;

    private String channel;

    private String timestamp;

    private String branchid;

    private String sendstate;

    private String origdate;

    private String origtime;

    private String origlogno;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno == null ? null : phoneno.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp == null ? null : timestamp.trim();
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid == null ? null : branchid.trim();
    }

    public String getSendstate() {
        return sendstate;
    }

    public void setSendstate(String sendstate) {
        this.sendstate = sendstate == null ? null : sendstate.trim();
    }

    public String getOrigdate() {
        return origdate;
    }

    public void setOrigdate(String origdate) {
        this.origdate = origdate == null ? null : origdate.trim();
    }

    public String getOrigtime() {
        return origtime;
    }

    public void setOrigtime(String origtime) {
        this.origtime = origtime == null ? null : origtime.trim();
    }

    public String getOriglogno() {
        return origlogno;
    }

    public void setOriglogno(String origlogno) {
        this.origlogno = origlogno == null ? null : origlogno.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}