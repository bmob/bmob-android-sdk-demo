package cn.bmob.sdkdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * @author zhangchaozhou
 */
public class OtherService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (BmobUser.isLogin()){
            final User user = BmobUser.getCurrentUser(User.class);
            user.setAge(20);
            user.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.e("success", "更新用户信息成功：" + user.getAge());
                    } else {
                        Log.e("error", "更新用户信息失败："+e.getMessage());
                    }
                }
            });
        }else {
            Log.e("OtherService","未登录");
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
