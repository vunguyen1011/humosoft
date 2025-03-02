package com.example.Humosoft.Exception;

import lombok.Getter;

@Getter
public class WebErrorConfig  extends RuntimeException{
    ErrorCode errorCode;

    public WebErrorConfig(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
