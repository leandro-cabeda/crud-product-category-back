package com.crud_service.controllers.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response <T> {

    private T data;
    private String message;
    private List<String> errors;
}
