package com.penovatech.common.advice;

import com.penovatech.common.dto.ResultDto;
import com.penovatech.common.exception.BusinessException;
import com.penovatech.common.exception.CommonExceptionMessage;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class ExceptionControllerAdvice {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
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
                new ResultDto<>(
                        ex.getCode().toString(),
                        this.getMessage(ex.getMessageKey(), ex.getArgs())
                ),
                HttpStatus.NOT_ACCEPTABLE
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException ex) {
        if (null == ex || CollectionUtils.isEmpty(ex.getBindingResult().getFieldErrors())) {
            int randomTrackingNo = RandomUtils.secure().nextInt(100000, 999999);
            LOGGER.error("trackingNo:{}, exception occur: {}", randomTrackingNo, ex);
            return new ResponseEntity(
                    new ResultDto<>(
                            CommonExceptionMessage.INTERNAL_SERVER_ERROR.getCode().toString(),
                            this.getMessage(CommonExceptionMessage.INTERNAL_SERVER_ERROR.getMessageKey(), randomTrackingNo)
                    ),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        List<String> messageList = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            messageList.add(this.getMessage(fieldError.getDefaultMessage()));
        }
        return new ResponseEntity(
                new ResultDto<>(
                        CommonExceptionMessage.INPUT_VALIDATION_ERROR.getCode().toString(),
                        String.join(" | ", messageList)
                ),
                HttpStatus.NOT_ACCEPTABLE
        );
    }

    private ResponseEntity<?> handleException(Exception ex) {
        int randomTrackingNo = RandomUtils.secure().nextInt(100000, 999999);
        LOGGER.error("trackingNo:{}, {} exception occur: {}", randomTrackingNo, ex.getClass().getSimpleName(), ex);
        return new ResponseEntity(
                new ResultDto<>(
                        CommonExceptionMessage.INTERNAL_SERVER_ERROR.getCode().toString(),
                        this.getMessage(CommonExceptionMessage.INTERNAL_SERVER_ERROR.getMessageKey(), randomTrackingNo)
                ),
                HttpStatus.NOT_ACCEPTABLE
        );
    }

    public String getMessage(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, args, locale);
    }
}
