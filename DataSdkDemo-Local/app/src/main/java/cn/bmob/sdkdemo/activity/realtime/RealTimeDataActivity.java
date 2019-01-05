package cn.bmob.sdkdemo.activity.realtime;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Chat;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * 数据监听是付费功能，请确保您appkey对应的应用已开通数据监听功能
 * @author zhangchaozhou
 */
public class RealTimeDataActivity extends AppCompatActivity  {


    @BindView(R.id.tv_info)
    AppCompatTextView mTvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_data);
        ButterKnife.bind(this);
    }

    private void startBmobRealTimeData() {

        final BmobRealTimeData bmobRealTimeData = new BmobRealTimeData();
        bmobRealTimeData.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {
                if (e == null) {
                    mTvInfo.setText("连接情况：" + (bmobRealTimeData.isConnected() ? "已连接" : "未连接") + "\n");
                    if (bmobRealTimeData.isConnected()) {
                        //TODO 如果已连接，设置监听动作为：监听Chat表的更新
                        bmobRealTimeData.subTableUpdate("Chat");
                    }
                } else {
                    mTvInfo.setText("连接出错：" + e.getMessage() + "\n");
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                /**
                 * {"nameValuePairs":{"appKey":"f25fe6dad5bca9d0bb090072ea1e3c65","tableName":"Chat","objectId":"","action":"updateTable",
                 * "data":{"nameValuePairs":{"content":"更新Chat表测试+1537324483401","createdAt":"2018-09-19 10:35:00","name":"RDT","objectId":"d5fffe82e9","updatedAt":"2018-09-19 10:35:00"}}}}
                 */
                Gson gson = new Gson();
                String action = jsonObject.optString("action");
                String jsonString = gson.toJson(jsonObject);
                mTvInfo.setText("更新返回内容是：" + jsonString + "\n");
                mTvInfo.setText("当前更新动作是：" + action + "\n");
                if (action.equals(BmobRealTimeData.ACTION_UPDATETABLE)) {
                    //TODO 如果监听表更新
                    JSONObject data = jsonObject.optJSONObject("data");
                    mTvInfo.setText("name：" + data.optString("name") + "\n");
                    mTvInfo.setText("content：" + data.optString("content") + "\n");
                    mTvInfo.setText("监听到更新：" + data.optString("name") + "-" + data.optString("content") + "\n");
                }
            }
        });

    }

    @OnClick({R.id.btn_connect_listen, R.id.btn_update_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_update_chat:
                Chat chat = new Chat();
                chat.setName("RDT");
                chat.setContent("更新Chat表测试" + System.currentTimeMillis());
                chat.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            mTvInfo.setText("新增表数据成功：" + objectId + "\n");
                        } else {
                            mTvInfo.setText("新增表数据出错：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                        }
                    }
                });
                break;
            case R.id.btn_connect_listen:
                startBmobRealTimeData();
                break;
            default:
                break;
        }
    }
}
