# 友盟推送模块

## 安装

```shell script
eco plugin install https://github.com/iecoe/umeng
```

## 卸载

```shell script
eco plugin uninstall https://github.com/iecoe/umeng
```

## 引用

```js
const umeng = app.requireModule("eco/umeng");
```

## 参数配置

请查阅[配置相关](https://eco.app/guide/config.html)

## 获取token

> `umeng.getToken` 获取友盟token

```
/**
* @返回 {"status":"success", "msg":"", "token":"友盟token"}
 */
let variable = umeng.getToken()

```

## 点击通知事件

> `umeng.setNotificationClickHandler` 自定义通知打开动作（点击通知事件）

```
/**
 * @param callback  回调事件
 */
umeng.setNotificationClickHandler(callback(result))

```

#### callback 回调`result`说明

```
{
    "status":"click",                   //为 “click” 时就是点击通知的动作了
    "msgid":"uuze1vb155496745348510",   //消息ID
    "title":"测试标题",                  //消息标题
    "subtitle":"12345678",              //消息副标题
    "text":"测试内容",                   //消息内容
    "extra":{                           //额外参数

    },
    "rawData":{                         //消息原始数据

    }
}

```

#### 简单示例

```
umeng.setNotificationClickHandler(function(result) {
    //......
});
```
