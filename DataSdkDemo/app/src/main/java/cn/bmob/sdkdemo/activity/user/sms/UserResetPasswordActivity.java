package cn.bmob.sdkdemo.activity.user.sms;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 18/9/26 09:39
 * TODO 通过原始密码重置密码，只针对已经通过密码注册登录的用户
 *
 * @author zhangchaozhou
 */
public class UserResetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.edt_origin_password)
    EditText mEdtOriginPassword;
    @BindView(R.id.edt_new_password)
    EditText mEdtNewPassword;
    @BindView(R.id.tv_info)
    TextView mTvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reset_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_reset)
    public void onViewClicked() {
        String originPassword = mEdtOriginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(originPassword)) {
            Toast.makeText(this, "请输入原始密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String newPassword = mEdtNewPassword.getText().toString().trim();
        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        BmobUser.updateCurrentUserPassword(originPassword, newPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    mTvInfo.append("密码重置成功");
                } else {
                    mTvInfo.append("密码重置失败：" + e.getErrorCode() + "-" + e.getMessage());
                }
            }
        });
    }
}
