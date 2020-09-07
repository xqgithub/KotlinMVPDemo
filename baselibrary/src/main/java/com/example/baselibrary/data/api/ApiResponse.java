package com.example.baselibrary.data.api;

/**
 * 解析基类
 */

public class ApiResponse<T> {

    /**
     * 用于描述数据是否请求成功
     */
    private boolean success;

    /**
     * 如果请求失败，会显示此字段将包含失败原因
     */
    private String message;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 错误code
     */
    private int error_code;

    /**
     * 系统时间
     */
    private long server_time;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }


    public long getServer_time() {
        return server_time;
    }

    public void setServer_time(long server_time) {
        this.server_time = server_time;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", error_code=" + error_code +
                ", server_time=" + server_time +
                '}';
    }
}
