package com.infocaltechnologies.exceptionhandler.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infocaltechnologies.exceptionhandler.util.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseModel {

    private Boolean success;
    private Integer status;
    private String message;
    private Object data;

    public ResponseModel(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.success = false;
        this.data = new Object();
    }
}