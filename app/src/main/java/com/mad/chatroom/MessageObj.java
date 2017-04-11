package com.mad.chatroom;

import java.util.ArrayList;

/**
 * Created by Chinmay Rawool on 4/10/2017.
 */

public class MessageObj {
    String content, name;
    long  time;
    Boolean textFlag;
    String comments;

    public MessageObj() {
    }

    public MessageObj(String content, String name, long time, Boolean textFlag) {
        this.content = content;
        this.name = name;
        this.time = time;
        this.textFlag = textFlag;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Boolean getTextFlag() {
        return textFlag;
    }

    public void setTextFlag(Boolean textFlag) {
        this.textFlag = textFlag;
    }

    @Override
    public String toString() {
        return "MessageObj{" +
                "content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", textFlag=" + textFlag +
                ", comments=" + comments +
                '}';
    }
}
