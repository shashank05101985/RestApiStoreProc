package com.common.auth.dto;

public class MessageResponse {

   private String message;

   private String type;

    public MessageResponse(String message,String type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
