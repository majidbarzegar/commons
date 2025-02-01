package com.penovatech.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto<R> implements Serializable {
    private R result;
    private boolean status;
    private Alert alert;

    public ResultDto(R result) {
        this.result = result;
        this.status = true;
    }

    public ResultDto(String title, String message) {
        this.alert = new Alert(title, message);
        this.status = false;
    }

    public ResultDto(String title, String message, Boolean status) {
        this.alert = new Alert(title, message);
        this.status = status;
    }

    public ResultDto(R result, String title, String message) {
        this.result = result;
        this.alert = new Alert(title, message);
        this.status = true;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Alert {
        private String title;
        private String message;
    }
}

