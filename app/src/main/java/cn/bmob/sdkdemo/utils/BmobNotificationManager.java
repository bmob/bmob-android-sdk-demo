package cn.bmob.sdkdemo.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import androidx.core.app.NotificationCompat;


/**
 * 通知管理器
 *
 * @author smile
 * @project BmobNotificationManager
 * @date 2016-02-24-17:45
 */
public class BmobNotificationManager extends ContextWrapper {

    private static volatile BmobNotificationManager INSTANCE;

    private static android.app.NotificationManager manager;

    public BmobNotificationManager(Context context) {
        super(context);
    }

    /**
     * 获取BmobNotificationManager实例
     *
     * @param context
     */
    public static BmobNotificationManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BmobNotificationManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BmobNotificationManager(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }




    /**
     * 获取应用图标
     *
     * @return bitmap
     */
    private Bitmap getAppIcon() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getApplicationInfo().loadIcon(this.getPackageManager());
        Bitmap appIcon = bitmapDrawable.getBitmap();
        return appIcon;
    }

    /**
     * 获取状态栏显示的内容
     *
     * @return
     */
    private String getTicker() {
        return this.getResources().getString(getResourcesId("bmob_im_notification_ticker"));
    }



    /**
     * 获取指定资源id
     *
     * @param name
     * @return
     */
    private int getResourcesId(String name) {
        int resId = this.getResources().getIdentifier(name, "string", this.getPackageName());
        if (resId == 0) {
            throw new RuntimeException("Error getting resource. Make sure you have copied all resources (values/bmob_im_notification_strings.xml) from SDK to your project. ");
        }
        return resId;
    }


    /**
     * 显示通知
     *
     * @param largerIcon 通知栏图标 开发者可传应用图标，也可以将聊天头像转成bitmap
     * @param title      标题
     * @param content    内容
     * @param ticker     状态栏上显示的内容
     * @param intent     跳转的intent
     */
    public void showNotification(Bitmap largerIcon, String title, String content, String ticker, Intent intent,int importance,int sound) {
        showNotification(largerIcon, title, content, ticker, intent, "BMOBIM_ID", "BMOBIM_NAME",importance,sound);
    }


    public void showNotification(Bitmap largerIcon, String title, String content, String ticker, PendingIntent intent,int importance,int sound) {
        showNotification(largerIcon, title, content, ticker, intent, "BMOBIM_ID", "BMOBIM_NAME",importance,sound);
    }



    /**
     * 显示通知
     *
     * @param largerIcon 通知栏图标 开发者可传应用图标，也可以将聊天头像转成bitmap
     * @param title      标题
     * @param content    内容
     * @param ticker     状态栏上显示的内容
     * @param intent     跳转的intent
     * @param channelId  消息渠道
     */
    public void showNotification(Bitmap largerIcon, String title, String content, String ticker, Intent intent, String channelId, String channelName,int importance,int sound) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId, channelName,importance);
            notification = getNotification_O(largerIcon, title, content, ticker, pendingIntent, channelId).build();
        } else {
            notification = getNotification_N(largerIcon, title, content, ticker, pendingIntent, channelId,sound).build();
        }
        getManager().notify(0, notification);
    }


    public void showNotification(Bitmap largerIcon, String title, String content, String ticker, PendingIntent pendingIntent, String channelId, String channelName,int importance,int sound) {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId, channelName,importance);
            notification = getNotification_O(largerIcon, title, content, ticker, pendingIntent, channelId).build();
        } else {
            notification = getNotification_N(largerIcon, title, content, ticker, pendingIntent, channelId,sound).build();
        }
        getManager().notify(0, notification);
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        INSTANCE = null;
        android.app.NotificationManager nm = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(0);
    }


    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel(String channelId, String channelName,int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        getManager().createNotificationChannel(channel);
    }


    /**
     * @return
     */
    private android.app.NotificationManager getManager() {
        if (manager == null) {
            manager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }


    /**
     * 适配android O
     *
     * @param largerIcon
     * @param title
     * @param content
     * @param ticker
     * @param pendingIntent
     * @param channelId
     * @return
     */
    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getNotification_O(Bitmap largerIcon, String title, String content, String ticker, PendingIntent pendingIntent, String channelId) {
        return new Notification.Builder(getApplicationContext(), channelId)
                .setTicker(ticker)
                //状态栏
                .setLargeIcon(largerIcon)
                .setSmallIcon(getApplicationInfo().icon)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }


    /**
     * 适配android N 及以下版本
     *
     * @param largerIcon
     * @param title
     * @param content
     * @param ticker
     * @param pendingIntent
     * @param channelId
     * @return
     */
    public NotificationCompat.Builder getNotification_N(Bitmap largerIcon, String title, String content, String ticker, PendingIntent pendingIntent, String channelId, int sound) {
        return new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setTicker(ticker)
                //状态栏
                .setLargeIcon(largerIcon)
                .setSmallIcon(getApplicationInfo().icon)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setDefaults(sound)
                .setContentIntent(pendingIntent);
    }

}
