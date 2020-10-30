//
//  ecoUmengModule.m
//  WeexTestDemo
//
//  Created by apple on 2018/6/19.
//  Copyright © 2018年 TomQin. All rights reserved.
//

#import "ecoUmengModule.h"
#import "ecoUmengManager.h"
#import <UMAnalytics/MobClick.h>
#import <WeexPluginLoader/WeexPluginLoader.h>

@implementation ecoUmengModule

WX_PlUGIN_EXPORT_MODULE(ecoUmeng, ecoUmengModule)
WX_EXPORT_METHOD_SYNC(@selector(getToken))
WX_EXPORT_METHOD(@selector(setNotificationClickHandler:))
WX_EXPORT_METHOD(@selector(onEvent:attributes:))


- (NSDictionary*)getToken
{
    return [[ecoUmengManager sharedIntstance] token];
}

- (void)setNotificationClickHandler:(WXModuleKeepAliveCallback)callback
{
    [[ecoUmengManager sharedIntstance] setNotificationClickHandler:callback];
}

- (void)onEvent:(NSString*)event attributes:(NSDictionary *)attributes
{
    [MobClick event:event attributes:attributes];
}

@end
