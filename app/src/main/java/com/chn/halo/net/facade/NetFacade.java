package com.chn.halo.net.facade;

import com.chn.halo.net.center.BaiDuApiStoreClientProvider;

/**
 * Description:网络请求借口集中对外包装类
 * Version: 1.0
 * Author: Halo-CHN
 * Email: halo-chn@outlook.com
 * Date: 15/11/12
 */
public class NetFacade {
    public static BaiDuApiStoreClientProvider getBaiduHttp()
    {
        return BaiDuApiStoreClientProvider.getInstance();
    }
}
