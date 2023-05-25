package cn.bmob.sdkdemo.activity.realtime;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Chat;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.realtime.Client;
import cn.bmob.v3.realtime.RealTimeDataListener;
import cn.bmob.v3.realtime.RealTimeDataManager;

/**
 * 数据监听是付费功能，请确保您appkey对应的应用已开通数据监听功能
 *
 * @author zhangchaozhou
 */
public class RealTimeDataActivity extends AppCompatActivity {

    private final static String tableName = "Chat";
    private final static String objectId = "aab9a68b46";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_data);
    }

    public void onConnectListen(View view) {
        RealTimeDataManager.getInstance().start(new RealTimeDataListener() {
            @Override
            public void onConnectCompleted(Client client, Exception e) {
                if (e == null) {
                    System.out.println("数据监听：已连接");
                    // 监听表
                    client.subTableUpdate(tableName);
                    // 监听表中的某行
                    // client.subRowUpdate(tableName,objectId);
                    Toast.makeText(RealTimeDataActivity.this, "已连接", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RealTimeDataActivity.this, "连接出错：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataChange(Client client, JSONObject jsonObject) {
                /**
                 * {"nameValuePairs":{"appKey":"f25fe6dad5bca9d0bb090072ea1e3c65","tableName":"Chat","objectId":"","action":"updateTable",
                 * "data":{"nameValuePairs":{"content":"更新Chat表测试+1537324483401","createdAt":"2018-09-19 10:35:00","name":"RDT","objectId":"d5fffe82e9","updatedAt":"2018-09-19 10:35:00"}}}}
                 */
                Log.d("bmob", jsonObject.toString());
                //更新动作
                String action = jsonObject.optString("action");
                if (action.equals(Client.ACTION_UPDATE_TABLE)) {
                    //TODO 如果监听表更新
                    //更新内容
                    JSONObject data = jsonObject.optJSONObject("data");
                    Toast.makeText(RealTimeDataActivity.this, "监听到更新：" + data.optString("name") + "-" + data.optString("content"), Toast.LENGTH_SHORT).show();
                } else if (Client.ACTION_UPDATE_ROW.equals(action)) {
                    // 监听的行更新数据
                    JSONObject data = jsonObject.optJSONObject("data");
                }
            }

            @Override
            public void onDisconnectCompleted(Client client) {
                System.out.println(client.toString() + "已断开");
            }
        });

    }

    public void onUpdateChat(View view) {
        Chat chat = new Chat();
        chat.setName("RDT");
        chat.setContent("更新Chat表测试" + System.currentTimeMillis());
        chat.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(RealTimeDataActivity.this, "新增表数据成功：" + objectId + "\n", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RealTimeDataActivity.this, "新增表数据出错：" + e.getErrorCode() + "-" + e.getMessage() + "\n", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
