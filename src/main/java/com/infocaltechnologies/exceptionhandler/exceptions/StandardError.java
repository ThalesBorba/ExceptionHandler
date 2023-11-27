package com.infocaltechnologies.exceptionhandler.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infocaltechnologies.exceptionhandler.util.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StandardError {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
}