package com.lzj.workflow.common.exception;

public class FlowableException extends RuntimeException{
    private int code;
    private String message;

    public FlowableException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
