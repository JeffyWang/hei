package com.kzone.bo;

/**
 * Created by Jeffy on 2014/5/27 0027.
 */
public class ErrorMessage {
    private String errorCode;
    private String message;

    public ErrorMessage(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
