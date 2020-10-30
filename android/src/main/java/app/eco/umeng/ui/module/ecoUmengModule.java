package app.eco.umeng.ui.module;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import app.eco.framework.extend.module.ecoJson;
import app.eco.framework.extend.module.ecoMap;
import app.eco.framework.extend.module.ecoParse;
import app.eco.umeng.ui.entry.eco_umeng;


/**
 * Created by WDM on 2018/3/27.
 */

public class ecoUmengModule extends WXModule {

    private static final String TAG = "ecoUmengModule";

    /**
     * 获取deviceToken
     */
    @JSMethod(uiThread = false)
    public Object getToken() {
        return eco_umeng.getToken();
    }

    /**
     * 设置点击通知事件
     * @param callback
     */
    @JSMethod
    public void setNotificationClickHandler(JSCallback callback) {
        if (callback == null) {
            return;
        }
        eco_umeng.addNotificationClickHandler(mWXSDKInstance.getContext(), callback);
    }

    /**
     * 自定义统计事件
     * @param event
     * @param object
     */
    @JSMethod
    public void onEvent(String event, String object) {
        JSONObject json = ecoJson.parseObject(object);
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            map.put(entry.getKey(), ecoParse.parseStr(entry.getValue()));
        }
        MobclickAgent.onEvent(mWXSDKInstance.getContext(), event, map);
    }
}
