package com.penovatech.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BusinessException extends RuntimeException implements Serializable {
    private Integer code;
    private String messageKey;
    private Object[] args;

    public BusinessException(String messageKey, Object... args) {
        this.messageKey = messageKey;
        this.args = args;
    }

    public BusinessException(Integer code, String messageKey, Object... args) {
        this.code = code;
        this.messageKey = messageKey;
        this.args = args;
    }

    public BusinessException(ExceptionMessage message, Object... args) {
        this.code = message.getCode();
        this.messageKey = message.getMessageKey();
        this.args = args;
    }
}
