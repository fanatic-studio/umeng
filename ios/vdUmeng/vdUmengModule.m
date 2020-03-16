//
//  vdUmengModule.m
//  WeexTestDemo
//
//  Created by apple on 2018/6/19.
//  Copyright © 2018年 TomQin. All rights reserved.
//

#import "vdUmengModule.h"
#import "vdUmengManager.h"
#import <UMAnalytics/MobClick.h>
#import <WeexPluginLoader/WeexPluginLoader.h>

@implementation vdUmengModule

WX_PlUGIN_EXPORT_MODULE(vdUmeng, vdUmengModule)
WX_EXPORT_METHOD_SYNC(@selector(getToken))
WX_EXPORT_METHOD(@selector(setNotificationClickHandler:))
WX_EXPORT_METHOD(@selector(onEvent:attributes:))


- (NSDictionary*)getToken
{
    return [[vdUmengManager sharedIntstance] token];
}

- (void)setNotificationClickHandler:(WXModuleKeepAliveCallback)callback
{
    [[vdUmengManager sharedIntstance] setNotificationClickHandler:callback];
}

- (void)onEvent:(NSString*)event attributes:(NSDictionary *)attributes
{
    [MobClick event:event attributes:attributes];
}

@end
