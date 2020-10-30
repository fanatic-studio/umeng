//
//  ecoUmengManager.h
//

#import <Foundation/Foundation.h>
#import "WeexSDK.h"
#import <UMPush/UMessage.h>

@interface ecoUmengManager : NSObject

@property (nonatomic, strong) NSDictionary *token;

+ (ecoUmengManager *)sharedIntstance;
- (void)init:(NSString*)key channel:(NSString*)channel launchOptions:(NSDictionary*)launchOptions;

@end
