dora-glide-support
![Release](https://jitpack.io/v/dora4/dora-glide-support.svg)
--------------------------------

#### gradle依赖配置

添加以下代码到项目根目录下的settings.gradle.kts
```kotlin
dependencyResolutionManagement { {
    repositories {
        maven { setUrl("https://jitpack.io") }
    }
}
```
添加以下代码到app模块的build.gradle.kts
```kotlin
dependencies {
    // 扩展包必须在有主框架dora的情况下使用
    implementation("com.github.dora4:dora:1.1.63")
    implementation("com.github.dora4:dora-glide-support:1.4")
}
```

#### 使用方式

在AndroidManifest中加入配置。
```xml
<application>
        <!-- Dora生命周期注入的配置 -->
        <meta-data
            android:name="dora.lifecycle.config.GlideGlobalConfig"
            android:value="GlobalConfig"/>
</application>
```



