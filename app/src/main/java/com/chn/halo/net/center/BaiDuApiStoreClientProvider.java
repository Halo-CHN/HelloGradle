package com.chn.halo.net.center;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.chn.halo.GlobalConfig;
import com.chn.halo.net.AndroidAsyncHttpHelper;
import com.chn.halo.net.HaloAsyncHttpResponseHandler;
import com.chn.halo.util.StringUtils;

/**
 * @author Halo-CHN
 * @version 1.0
 * @description 百度API STORE http请求提供器
 * @mail halo-chn@outlook.com
 * @date 2015年7月13日
 */
public class BaiDuApiStoreClientProvider {
    private static BaiDuApiStoreClientProvider instance;

    public static BaiDuApiStoreClientProvider getInstance() {
        if (null == instance)
            synchronized (BaiDuApiStoreClientProvider.class) {
                if (null == instance) {
                    instance = new BaiDuApiStoreClientProvider();
                }
            }
        return instance;
    }

    private final String APIKEY = "apikey";
    private final String APIVALUE = "2083e721a57243b6b686519827cfeeaf";

    /**
     * @param location 位置
     * @return 交通事件查询请求参数
     */
    private Map<String, Object> getTafficEventParams(String location) {
        return getTafficEventParams(location, "json", null, null);
    }

    /**
     * @param location     必填 - 输入城市名或经纬度，经纬度格式为 lng,lat
     * @param output       非必填 - 输出的数据格式，默认为xml格式，当output设置为’json’时，输出的为json格式的数据
     * @param coordType    非必填 - 请求参数坐标类型，默认为gcj02经纬度坐标。允许的值为bd09ll、bd09mc、gcj02、wgs84。
     *                     bd09ll表示百度经纬度坐标
     *                     ，bd09mc表示百度墨卡托坐标，gcj02表示经过国测局加密的坐标。wgs84表示gps获取的坐标
     * @param putCoordType 非必填 - 返回结果输出时的坐标类型，默认为gcj02经纬度坐标。允许的值为bd09ll、bd09mc、gcj02。
     *                     bd09ll表示百度经纬度坐标，bd09mc表示百度墨卡托坐标，gcj02表示经过国测局加密的坐标
     * @return 交通事件查询请求参数
     */
    private Map<String, Object> getTafficEventParams(String location, String output, String coordType, String putCoordType) {
        if (StringUtils.isBlank(location))
            throw new NullPointerException("location 不能为空!");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("location", location);
        if (!StringUtils.isBlank(output))
            map.put("output", output);
        if (!StringUtils.isBlank(coordType))
            map.put("coord_type", coordType);
        if (!StringUtils.isBlank(putCoordType))
            map.put("out_coord_type", putCoordType);
        return map;
    }

    /**
     * HTTP GET METHODs --无参数 -- 存在异常或者请求超时情况下，回调返回值将是空字符串
     *
     * @param context  调用的页面
     * @param uri      请求的URL
     * @param callback 请求完成后回调的方法
     */
    private void get(Context context, String url, final HaloAsyncHttpResponseHandler callback) {
        AndroidAsyncHttpHelper.getInstance().addHeader(APIKEY, APIVALUE);
        AndroidAsyncHttpHelper.getInstance().get(context, url, callback);
    }

    /**
     * HTTP GET METHODs --有参数 -- 存在异常或者请求超时情况下，回调返回值将是空字符串
     *
     * @param context  调用的页面
     * @param url      请求的URL
     * @param params   参数
     * @param callback 请求完成后回调的方法
     */
    private void get(Context context, String url, Map<String, Object> params, final HaloAsyncHttpResponseHandler callback) {
        AndroidAsyncHttpHelper.getInstance().addHeader(APIKEY, APIVALUE);
        AndroidAsyncHttpHelper.getInstance().get(context, url, params, callback);
    }

    /**
     * 移除header中信息
     */
    private void removeClientHeader() {
        AndroidAsyncHttpHelper.getInstance().removeAllHeaders();
    }

    /********************************************************************************************/
    /**
     * 获取交通路况
     *
     * @param city                         城市
     * @param haloAsyncHttpResponseHandler 回调
     */
    public void getTrafficEvent(final Context context, String city, final HaloAsyncHttpResponseHandler haloAsyncHttpResponseHandler) {
        get(context, GlobalConfig.baiDuTrafficEventUrl, getTafficEventParams(city), haloAsyncHttpResponseHandler);
    }
}
