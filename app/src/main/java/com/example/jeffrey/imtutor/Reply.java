package com.example.jeffrey.imtutor;

public class Reply {
    String replyContent;
    String replyDate;
    String replyTime;
    String replyUser;

    public Reply(){
    }

    public Reply(String replyContent, String replyDate, String replyTime, String replyUser) {
        this.replyContent = replyContent;
        this.replyDate = replyDate;
        this.replyTime = replyTime;
        this.replyUser = replyUser;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }
}
