package com.lijing.springbootsimple.utils;

/**
 * @Description
 * @Author crystal
 * @CreatedDate 2018年05月13日 星期日 18时43分.
 */
public class Result {
    private String status;
    private Object data;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
