package com.penovatech.common.exception;

public enum CommonExceptionMessage implements ExceptionMessage{
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR")
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
