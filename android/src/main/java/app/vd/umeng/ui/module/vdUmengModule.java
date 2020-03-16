package app.vd.umeng.ui.module;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import app.vd.framework.extend.module.vdJson;
import app.vd.framework.extend.module.vdMap;
import app.vd.framework.extend.module.vdParse;
import app.vd.umeng.ui.entry.vd_umeng;


/**
 * Created by WDM on 2018/3/27.
 */

public class vdUmengModule extends WXModule {

    private static final String TAG = "vdUmengModule";

    /**
     * 获取deviceToken
     */
    @JSMethod(uiThread = false)
    public Object getToken() {
        return vd_umeng.getToken();
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
        vd_umeng.addNotificationClickHandler(mWXSDKInstance.getContext(), callback);
    }

    /**
     * 自定义统计事件
     * @param event
     * @param object
     */
    @JSMethod
    public void onEvent(String event, String object) {
        JSONObject json = vdJson.parseObject(object);
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            map.put(entry.getKey(), vdParse.parseStr(entry.getValue()));
        }
        MobclickAgent.onEvent(mWXSDKInstance.getContext(), event, map);
    }
}
