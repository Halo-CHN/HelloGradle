package com.chn.halo.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * @author Halo-CHN
 * @version 1.0
 * @description 对 android-async-http 进行封装的类
 * @mail halo-chn@outlook.com
 * @date 2015年1月16日
 */
public class AndroidAsyncHttpHelper {

    private static AndroidAsyncHttpHelper instance;

    private AndroidAsyncHttpHelper() {
    }

    public static AndroidAsyncHttpHelper getInstance() {
        if (null == instance) {
            synchronized (AndroidAsyncHttpHelper.class) {
                if (null == instance) {
                    instance = new AndroidAsyncHttpHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 定义一个异步网络客户端 默认超时未20秒 当超过，默认重连次数为5次 默认最大连接数为10个
     */
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setTimeout(20000);
    }

    /**
     * 为http client 添加 header （默认清空所有之前的添加）
     *
     * @param header key
     * @param value  value
     */
    public void addHeader(String header, String value) {
        addHeader(header, value, true);
    }

    /**
     * 为http client 添加 header
     *
     * @param header        key
     * @param value         value
     * @param needRemoveAll 添加前是否需要清空
     */
    public void addHeader(String header, String value, boolean needRemoveAll) {
        if (needRemoveAll) {
            removeAllHeaders();
        }
        client.addHeader(header, value);
    }

    /**
     * 清空所有header
     */
    public void removeAllHeaders() {
        if (null != client) {
            client.removeAllHeaders();
        }
    }

    /**
     * HTTP GET METHODs --无参数 -- 存在异常或者请求超时情况下，回调返回值将是空字符串
     *
     * @param context  调用的页面
     * @param url      请求的URL
     * @param callback 请求完成后回调的方法
     */
    public void get(Context context, String url, final HaloAsyncHttpResponseHandler callback) {
        httpRequest(context, url, null, callback, EHttpMethod.GET);
    }

    /**
     * HTTP GET METHODs --有参数 -- 存在异常或者请求超时情况下，回调返回值将是空字符串
     *
     * @param context  调用的页面
     * @param url      请求的URL
     * @param params   参数
     * @param callback 请求完成后回调的方法
     */
    public void get(Context context, String url, Map<String, Object> params, final HaloAsyncHttpResponseHandler callback) {
        httpRequest(context, url, params, callback, EHttpMethod.GET);
    }

    /**
     * HTTP POST METHODs -- 无参数 --存在异常或者请求超时情况下，回调返回值将是空字符串
     * *
     *
     * @param context  调用的页面
     * @param url      请求的URL
     * @param callback 请求完成后回调的方法
     */
    public void post(Context context, String url, final HaloAsyncHttpResponseHandler callback) {
        httpRequest(context, url, null, callback, EHttpMethod.POST);
    }

    /**
     * HTTP POST METHODs -- 有参数 --存在异常或者请求超时情况下，回调返回值将是空字符串
     *
     * @param context  调用的页面
     * @param url      请求的URL
     * @param params   参数
     * @param callback 请求完成后回调的方法
     */
    public void post(Context context, String url, Map<String, Object> params, final HaloAsyncHttpResponseHandler callback) {
        httpRequest(context, url, params, callback, EHttpMethod.POST);
    }

    /**
     * HTTP请求
     *
     * @param context  传入调用的页面
     * @param url      请求的URL
     * @param params   传入的参数
     * @param callback 请求完成后回调的方法
     * @param method   POST or GET
     */
    private void httpRequest(Context context, String url, Map<String, Object> params, final HaloAsyncHttpResponseHandler callback, EHttpMethod method) {
        /* 判断网络状态 */
        AsyncHttpResponseHandler asyncHttpResponseHandler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String res = "";
                try {
                    res = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } finally {
                    callback.callback(res);
                    client.removeAllHeaders();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 404) {
                } else {
                    callback.callback("");
                }
                client.removeAllHeaders();
            }
        };
        if (!InternetUtil.isNetWorking(context)) {
            String str = "无网络";
            asyncHttpResponseHandler.sendFailureMessage(404, null, str.getBytes(), null);
            return;
        }
        /* 得到请求参数 */
        RequestParams requestParams = new RequestParams();
        if (null != params && params.size() > 0) {
            for (String key : params.keySet()) {
                requestParams.put(key, params.get(key));
            }
        }
        switch (method) {
            case GET:
                client.get(context, url, requestParams, asyncHttpResponseHandler);
                break;

            case POST:
                client.post(context, url, requestParams, asyncHttpResponseHandler);
                break;

            default:
                break;
        }
    }

    /**
     * HTTP POST METHODs -- 上传文件
     *
     * @param path 要上传的文件路径
     * @param url  服务端接收URL
     * @throws Exception 异常信息
     */
    public static void uploadFile(String path, String url) throws Exception {
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("uploadfile", file);
            /* 上传文件 */
            client.post(url, params, new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } else {
			/* 文件不存在 */
        }
    }

    public static void uploadFiles(String path, String url) throws Exception {
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("uploadfile", file);
			/* 上传文件 */
            client.post(url, params, new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } else {
			/* 文件不存在 */
        }
    }

    /**
     * @author Halo-CHN
     * @version 1.0
     * @description 请求类型的枚举
     * @mail halo-chn@outlook.com
     * @date 2015年1月6日
     */
    public enum EHttpMethod {
        GET, POST
    }
}
