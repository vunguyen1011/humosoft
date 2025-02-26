package com.example.Humosoft.DTO.Response;

import lombok.Builder;
import lombok.Data;


/**
 *
 * @author Admin
 * @param <T>
 */
@Builder
@Data

public class Apiresponse<T> {
    private int code=200;
    private String message;
    private T result;
}