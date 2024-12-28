package com.bypen.common.advice;

import com.bypen.common.dto.ResponseDto;
import com.bypen.common.exception.BusinessException;
import com.bypen.common.exception.CommonExceptionMessage;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

public abstract class ExceptionControllerAdvice {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ExceptionControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex) {
        return this.handleException(ex);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handle(NullPointerException ex) {
        ex.printStackTrace();
        return this.handleException(ex);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handle(BusinessException ex) {
        return new ResponseEntity(
                new ResponseDto<>(
                        ex.getCode(),
                        this.getMessage(ex.getMessageKey(), ex.getArgs())
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ResponseEntity<?> handleException(Exception ex) {
        int randomTrackingNo = RandomUtils.secure().nextInt(100000, 999999);
        LOGGER.error("trackingNo:{}, {} exception occur: {}", randomTrackingNo, ex.getClass().getSimpleName(), ex);
        return new ResponseEntity(
                new ResponseDto<>(
                        CommonExceptionMessage.INTERNAL_SERVER_ERROR.getCode(),
                        this.getMessage(CommonExceptionMessage.INTERNAL_SERVER_ERROR.getMessageKey(), randomTrackingNo)
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public String getMessage(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, args, locale);
    }
}
