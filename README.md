[![](https://jitpack.io/v/lany192/umeng.svg)](https://jitpack.io/#lany192/umeng)
### 【友盟+】统计、推送、分享三合一组件化SDK集成Demo

https://developer.umeng.com/docs/119267/detail/182050


	allprojects {
		repositories {
			//依赖仓库
			maven { url 'https://jitpack.io' }
		}
	}
	
    dependencies {
        //友盟统计（必选）
        implementation 'com.github.lany192.umeng:analysis:1.0.6'
        //友盟推送（可选）
        implementation 'com.github.lany192.umeng:push:1.0.6'
        //友盟分享（可选）
        implementation 'com.github.lany192.umeng:share:1.0.6'
    }

