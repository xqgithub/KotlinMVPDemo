package com.example.baselibrary.data.api;

import com.example.baselibrary.utils.LogUtils;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import retrofit2.HttpException;

import java.net.ConnectException;

/**
 * 网络请求  异常处理类
 */
public class ExceptionHandle {

    public static final int UNAUTHORIZED = 401;//未授权：登录失败
    public static final int FORBIDDEN = 403;//访问被拒绝
    public static final int NOT_FOUND = 404;//找不到网页，地址错误
    public static final int REQUEST_TIMEOUT = 408;//请求超时
    public static final int INTERNAL_SERVER_ERROR = 500;//内部服务器错误
    public static final int BAD_GATEWAY = 502;//网关错误
    public static final int SERVICE_UNAVAILABLE = 503;//服务器错误
    public static final int GATEWAY_TIMEOUT = 504;//网关超时


    public static ResponeThrowable handleException(Throwable e) {
        try {
            ResponeThrowable ex = null;
            if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                ex = new ResponeThrowable(e, httpException.code());
                return ex;
            } else if (e instanceof ServerException) {
                ServerException resultException = (ServerException) e;
                ex = new ResponeThrowable(resultException, resultException.code);
                ex.message = resultException.message;
                return ex;
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
//                    || e instanceof ParseException
                    || e instanceof MalformedJsonException) {
                ex = new ResponeThrowable(e, ERROR.PARSE_ERROR);
                ex.message = "解析错误";
                return ex;
            } else if (e instanceof ConnectException) {
                ex = new ResponeThrowable(e, ERROR.NETWORD_ERROR);
                ex.message = "连接失败";
                return ex;
            } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
                ex = new ResponeThrowable(e, ERROR.SSL_ERROR);
                ex.message = "证书验证失败";
                return ex;
            } else if (e instanceof ConnectTimeoutException) {
                ex = new ResponeThrowable(e, ERROR.TIMEOUT_ERROR);
                ex.message = "连接超时";
                return ex;
            } else if (e instanceof java.net.SocketTimeoutException) {
                ex = new ResponeThrowable(e, ERROR.TIMEOUT_ERROR);
                ex.message = "连接超时";
                return ex;
            } else if (e instanceof IllegalArgumentException) {
                ex = new ResponeThrowable(e, ERROR.PARAMETER_ERROR);
                ex.message = "参数有误";
                return ex;
            } else {
                ex = new ResponeThrowable(e, ERROR.UNKNOWN);
                ex.message = "未知错误";
                return ex;
            }
        } catch (Exception e1) {
            e.printStackTrace();
            LogUtils.e(e1.getMessage());
            ResponeThrowable ex = null;
            ex = new ResponeThrowable(e, ERROR.UNKNOWN);
            ex.message = "异常错误";
            return ex;
        }
    }


    /**
     * 约定异常
     */
    class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;
        /**
         * 方法的参数错误
         */
        public static final int PARAMETER_ERROR = 1007;
    }


    public static class ResponeThrowable extends Exception {
        public int code;
        public String message;

        public ResponeThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;

        }
    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;
    }

}
