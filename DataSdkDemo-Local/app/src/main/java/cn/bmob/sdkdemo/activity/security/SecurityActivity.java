package cn.bmob.sdkdemo.activity.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.activity.security.acl.AclActivity;
import cn.bmob.sdkdemo.activity.security.role.BmobRoleActivity;

/**
 * Created on 2018/12/5 15:59
 *
 * @author zhangchaozhou
 */
public class SecurityActivity extends AppCompatActivity {

    @BindView(R.id.btn_acl)
    AppCompatButton mBtnAcl;
    @BindView(R.id.btn_role)
    AppCompatButton mBtnRole;
    @BindView(R.id.btn_app_sign)
    AppCompatButton mBtnAppSign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_acl, R.id.btn_role, R.id.btn_app_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_acl:
                startActivity(new Intent(this,AclActivity.class));
                break;
            case R.id.btn_role:
                startActivity(new Intent(this,BmobRoleActivity.class));
                break;
            case R.id.btn_app_sign:
                startActivity(new Intent(this,SignVerifyActivity.class));
                break;
            default:
                break;
        }
    }
}
