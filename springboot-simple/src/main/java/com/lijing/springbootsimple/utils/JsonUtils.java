package com.lijing.springbootsimple.utils;

import com.lijing.springbootsimple.contants.Status;

/**
 * @Description
 * @Author crystal
 * @CreatedDate 2018年05月13日 星期日 18时38分.
 */

public class JsonUtils {


    public static Result ok(Object data){
        Result result = new Result();
        result.setStatus(Status.status_200);
        result.setData(data);
        result.setMsg(Status.status_200);
        return result;
    }

    public static Result notFound(Object data){
        Result result = new Result();
        result.setStatus(Status.status_404);
        result.setData(data);
        result.setMsg(Status.status_404);
        return result;
    }

    public static Result interServerError(Object data){
        Result result = new Result();
        result.setStatus(Status.status_500);
        result.setData(data);
        result.setMsg(Status.status_500);
        return result;
    }
}
