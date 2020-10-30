//
//  ecoUmengManager.h
//  WeexTestDemo
//
//  Created by apple on 2018/6/20.
//  Copyright © 2018年 TomQin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "WeexSDK.h"
#import <UMPush/UMessage.h>

#define kUmengNotification @"kUmengNotification"

@interface ecoUmengManager : NSObject

@property (nonatomic, strong) NSDictionary *token;
@property (nonatomic, copy) WXModuleKeepAliveCallback callback;

+ (ecoUmengManager *)sharedIntstance;

- (void)init:(NSString*)key channel:(NSString*)channel launchOptions:(NSDictionary*)launchOptions;

- (void)setNotificationClickHandler:(WXModuleKeepAliveCallback)callback;

@end
