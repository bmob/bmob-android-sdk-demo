# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android\Eclipse\adt-bundle-windows-x86_64-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes Signature,*Annotation*

# BmobSDK
-dontwarn cn.bmob.v3.**
-keep class cn.bmob.v3.** {*;}

# BmobPush
-dontwarn  cn.bmob.push.**
-keep class cn.bmob.push.** {*;}

# 保证继承自BmobObject、BmobUser类的JavaBean不被混淆-gson解析
-keep class * extends cn.bmob.v3.BmobObject {
    *;
}
# 也可逐个填写
-keep class cn.bmob.sdkdemo.bean.BankCard{*;}
-keep class cn.bmob.sdkdemo.bean.GameScore{*;}
-keep class cn.bmob.sdkdemo.bean.MyUser{*;}
-keep class cn.bmob.sdkdemo.bean.Person{*;}

-keep class cn.bmob.sdkdemo.file.Movie{*;}
-keep class cn.bmob.sdkdemo.file.Song{*;}

-keep class cn.bmob.sdkdemo.relation.Post{*;}
-keep class cn.bmob.sdkdemo.relation.Comment{*;}

# okhttp3、okio
-dontwarn okhttp3.**
-keep class okhttp3.** { *;}
-keep interface okhttp3.** { *; }
-dontwarn okio.**

# rx
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# gson
-dontwarn com.google.gson.**
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *;}

# 如果你需要兼容6.0系统，请不要混淆org.apache.http.legacy.jar
 -dontwarn android.net.compatibility.**
 -dontwarn android.net.http.**
 -dontwarn com.android.internal.http.multipart.**
 -dontwarn org.apache.commons.**
 -dontwarn org.apache.http.**
 -keep class android.net.compatibility.**{*;}
 -keep class android.net.http.**{*;}
 -keep class com.android.internal.http.multipart.**{*;}
 -keep class org.apache.commons.**{*;}
 -keep class org.apache.http.**{*;}