package app.eco.umeng.ui.entry;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXException;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UHandler;
import com.umeng.message.entity.UMessage;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.eco.framework.BuildConfig;
import app.eco.framework.extend.annotation.ModuleEntry;
import app.eco.framework.extend.module.ecoBase;
import app.eco.framework.extend.module.ecoCommon;
import app.eco.framework.extend.module.ecoJson;
import app.eco.framework.extend.module.ecoMap;
import app.eco.framework.extend.module.ecoParse;
import app.eco.framework.ui.eco;
import app.eco.umeng.ui.module.ecoUmengModule;

/**
 * Created by WDM on 2018/3/27.
 */
@ModuleEntry
public class eco_umeng {

    private static boolean isRegister = false;

    /**
     * ModuleEntry
     * @param content
     */
    public void init(Context content) {
        JSONObject umeng = ecoJson.parseObject(ecoBase.config.getObject("umeng").get("android"));
        if (ecoJson.getBoolean(umeng, "enabled")) {
            eco_umeng.init(ecoJson.getString(umeng, "appKey"), ecoJson.getString(umeng, "messageSecret"), ecoJson.getString(umeng, "channel"));
        }
        //注册模块
        if (!isRegister) {
            isRegister = true;
            try {
                WXSDKEngine.registerModule("ecoUmeng", ecoUmengModule.class);
            } catch (WXException e) {
                e.printStackTrace();
            }
        }
    }

    /****************************************************************************************/
    /****************************************************************************************/
    /****************************************************************************************/

    private static final String TAG = "eco_umeng";

    private static JSONObject umeng_token = new JSONObject();

    private static List<notificationClickHandlerBean> mNotificationClickHandler = new ArrayList<>();

    public static void init(String key, String secret) {
        init(key, secret, null);
    }

    public static void init(String key, String secret, String channel) {
        //初始化
        UMConfigure.init(eco.getApplication(), key, channel, UMConfigure.DEVICE_TYPE_PHONE, secret);
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        //注册统计
        eco.getApplication().registerActivityLifecycleCallbacks(mCallbacks);
        //注册推送
        PushAgent mPushAgent = PushAgent.getInstance(eco.getApplication());
        mPushAgent.setResourcePackageName(eco.getApplication().getPackageName());
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                umeng_token.put("status", "success");
                umeng_token.put("msg", "");
                umeng_token.put("token", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                umeng_token.put("status", "error");
                umeng_token.put("msg", s);
                umeng_token.put("token", "");
            }
        });
        //打开通知动作
        mPushAgent.setNotificationClickHandler(new UHandler() {
            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                try {
                    clickHandleMessage(uMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //
                LinkedList<Activity> mLinkedList = eco.getActivityList();
                Activity mActivity = mLinkedList.getLast();
                if (mActivity != null) {
                    Intent intent = new Intent(context, mActivity.getClass());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    public static void addNotificationClickHandler(Context context, JSCallback callback) {
        mNotificationClickHandler = new ArrayList<>();
        mNotificationClickHandler.add(new notificationClickHandlerBean(context, callback));
    }

    public static JSONObject getToken() {
        return umeng_token;
    }

    private static class notificationClickHandlerBean {
        Context context;
        JSCallback callback;

        notificationClickHandlerBean(Context context, JSCallback callback) {
            this.context = context;
            this.callback = callback;
        }
    }

    private static void clickHandleMessage(UMessage uMessage) throws JSONException {
        mNotificationClickHandler = ecoCommon.removeNull(mNotificationClickHandler);
        if (mNotificationClickHandler.size() == 0) {
            return;
        }
        LinkedList<Activity> mLinkedList = eco.getActivityList();
        for (int i = 0; i < mNotificationClickHandler.size(); i++) {
            notificationClickHandlerBean mBean = mNotificationClickHandler.get(i);
            if (mBean != null) {
                boolean isCallBack = false;
                for (int j = 0; j < mLinkedList.size(); j++) {
                    if (mLinkedList.get(j).equals(mBean.context)) {
                        Map<String, Object> temp = ecoMap.jsonToMap(uMessage.getRaw());
                        Map<String, Object> body = ecoMap.objectToMap(temp.get("body"));
                        Map<String, Object> extra = ecoMap.objectToMap(temp.get("extra"));
                        if (body != null) {
                            Map<String, Object> data = new HashMap<>();
                            data.put("status", "click");
                            data.put("msgid", ecoParse.parseStr(body.get("msg_id")));
                            data.put("title", ecoParse.parseStr(body.get("title")));
                            data.put("subtitle", "");
                            data.put("text", ecoParse.parseStr(body.get("text")));
                            data.put("extra", extra != null ? extra : new HashMap<>());
                            data.put("rawData", temp);
                            mBean.callback.invokeAndKeepAlive(data);
                            isCallBack = true;
                        }
                        break;
                    }
                }
                if (!isCallBack) {
                    mNotificationClickHandler.set(i, null);
                }
            }
        }
    }

    private static ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            MobclickAgent.onResume(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            MobclickAgent.onPause(activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

}
