package com.domain;

import java.util.ArrayList;

public class Message {
    public Double message;
    public String time;

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message=" + message +
                ", time='" + time + '\'' +
                '}';
    }
}
