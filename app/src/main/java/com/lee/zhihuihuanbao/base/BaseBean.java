package com.lee.zhihuihuanbao.base;

/**
 * @Author Lee
 * @Time 2017/9/11
 * @Theme  基础实体类
 */

public class BaseBean<T> {

    private String message;
    private String status;
    private T result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setStatus(int status){
        this.status = status+"";
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
