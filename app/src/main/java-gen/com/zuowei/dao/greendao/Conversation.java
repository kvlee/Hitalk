package com.zuowei.dao.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CONVERSATION".
 */
public class Conversation {

    private String conversationId;
    private String content;

    public Conversation() {
    }

    public Conversation(String conversationId) {
        this.conversationId = conversationId;
    }

    public Conversation(String conversationId, String content) {
        this.conversationId = conversationId;
        this.content = content;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
