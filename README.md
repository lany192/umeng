[![](https://jitpack.io/v/lany192/umeng.svg)](https://jitpack.io/#lany192/umeng)
### 【友盟+】统计、推送、分享三合一组件化SDK集成Demo


---
## 集成升级必读(Android)

#### apm SDK集成示例工程，请参考 [APM Demo](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/176778/UMDP_zh/1608017839531/CrashDemo.zip)。

#### 1. 统计SDK 9.3.0版本不能和低版本推送、分享SDK混用。混用可能导致不可预知后果(例如：崩溃，无数据等)。

下表列出统计SDK 9.3.0支持的各业务SDK最低版本，请确保App中配合9.3.0统计SDK集成的各业务SDK版本号满足下表要求。


| 推送SDK | 分享SDK | 游戏统计SDK| 智能登录SDK| 
| -------- | -------- | --------| ---------|
| >= v6.2.0    | >= v7.1.0 |  >= v9.2.0+G | >= v1.4.0 |


#### 2. 集成本版本SDK 请务必在混淆时增加：-keep class com.uc.** {*;}

#### 3. 集成移动统计时，如果App不能保证在Appcalition.onCreate函数中调用UMConfigure.init初始化函数 请查看https://developer.umeng.com/docs/119267/detail/118588#h3-u9884u521Du59CBu5316

#### 4. 集成移动统计时，如果开发者调用kill或者exit之类的方法杀死进程，请查看，https://developer.umeng.com/docs/119267/detail/118637#h1-u5176u4ED6u529Fu80FD6

#### 5. 如集成移动统计后无数据，请查看https://developer.umeng.com/docs/119267/cate/121449 

#### 6. 详细升级文档，请查看：https://developer.umeng.com/docs/119267/detail/118642


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
    dependencies {
        implementation 'com.github.lany192:umeng:1.0.3'
        //微博官方SDK
        implementation 'com.sina.weibo.sdk:core:10.10.0:openDefaultRelease@aar'
        //微信官方依赖库
        implementation 'com.tencent.mm.opensdk:wechat-sdk-android-without-mta:6.6.22'
    }

