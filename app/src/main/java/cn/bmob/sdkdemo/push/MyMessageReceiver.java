package cn.bmob.sdkdemo.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.sdkdemo.R;
import cn.bmob.v3.helper.NotificationCompat;


public class MyMessageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String message = intent.getStringExtra("msg");
		Log.i("bmob", "收到的推送消息："+message);
		Toast.makeText(context.getApplicationContext(), ""+message, Toast.LENGTH_LONG).show();
		//使用cn.bmob.v3.helper包下的NotificationCompat来创建通知栏，也可以使用support_v4里面的NotificationCompat类
		Intent i = new Intent();
		i.setClass(context, ActBmobPush.class);
		i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
		NotificationCompat.Builder mBuilder =new NotificationCompat.Builder(context)
				.setTicker("BmobExample收到消息推送")
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("消息")
				.setContentText(message)
				.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_SOUND| Notification.DEFAULT_VIBRATE)
				.setContentIntent(pi);
		// 发送通知
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = mBuilder.build();
		nm.notify(0, n);
	}

}
