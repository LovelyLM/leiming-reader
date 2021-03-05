package com.leiming.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

public class LMResultBuilder {

    //成功，不返回具体数据
    public static <T> LMResponse<T> successNoData(ResultCode code){
        LMResponse<T> result = new LMResponse<T>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        return result;
    }
    //成功，返回数据
    public static <T> LMResponse<T> success(T t, ResultCode code){
        LMResponse<T> result = new LMResponse<T>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        if (ObjectUtil.isEmpty(t)){
            return result;
        }
        result.setData(t);
        return result;
    }
    public static <T> LMResponse<T> success(ResultCode code){
        LMResponse<T> result = new LMResponse<T>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());

        return result;
    }

    //失败，返回失败信息
    public static <T> LMResponse<T> faile(ResultCode code){
        LMResponse<T> result = new LMResponse<>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        return result;
    }

}
