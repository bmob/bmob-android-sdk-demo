package cn.bmob.sdkdemo;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * @author zhangchaozhou
 */
public class BmobApplication extends Application {


    /**
     * APPID：在控制台的应用-设置-
     */
    public static String APPID = "12784168944a56ae41c4575686b7b332";


    @Override
    public void onCreate() {
        super.onCreate();


        /**
         * TODO 1.1、重置域名为VIP域名，此域名仅限企业版及以上套餐用户使用，此方法需要放置在初始化SDK操作之前。
         * TODO 1.2、海外加速
         */
//        Bmob.resetDomain("http://open-vip.bmob.cn/8/");


        /**
         * TODO 2.1、SDK初始化方式一
         */

        Bmob.initialize(this, APPID);



        /**
         * TODO 2.2、SDK初始化方式二
         * 设置BmobConfig，允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间
         */
        BmobConfig config = new BmobConfig.Builder(this)
                //设置APPID
                .setApplicationId(APPID)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(5500)
                .build();
        Bmob.initialize(config);




//		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());


    }


}