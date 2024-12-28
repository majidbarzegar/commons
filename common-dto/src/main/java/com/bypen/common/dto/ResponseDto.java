package com.bypen.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<R> implements Serializable {
    private R response;
    private Integer code;
    private String text;

    public ResponseDto(R response) {
        this.response = response;
    }

    public ResponseDto(int code, String text) {
        this.code = code;
        this.text = text;
    }
}
