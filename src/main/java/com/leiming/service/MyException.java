package com.leiming.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyException extends RuntimeException{
    private String code;
    private String msg;
    public MyException(String code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
