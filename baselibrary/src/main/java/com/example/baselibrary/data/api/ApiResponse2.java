package com.example.baselibrary.data.api;

/**
 * 解析基类
 */

public class ApiResponse2<T> {

    private int status;
    private T content;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ApiResponse2{" +
                "status=" + status +
                ", content=" + content +
                '}';
    }
}
