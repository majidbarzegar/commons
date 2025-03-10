package com.penovatech.common.exception;

public enum CommonExceptionMessage implements ExceptionMessage{
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    RECOURSE_NOT_FOUND(404, "RECOURSE_NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    INPUT_VALIDATION_ERROR(1000, "INPUT_VALIDATION_ERROR")
    ;

    private Integer code;
    private String messageKey;

    CommonExceptionMessage(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }

}
