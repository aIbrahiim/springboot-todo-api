package com.woodyinho.spring.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ErrorMessage {

    private String message;
    private String url;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private Date timestamp;

    public ErrorMessage(){
        this.timestamp = new Date();
    }
    public ErrorMessage(String message, String url) {
        this();
        this.message = message;
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
