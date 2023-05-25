package cn.bmob.sdkdemo.activity.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.activity.security.acl.AclActivity;
import cn.bmob.sdkdemo.activity.security.role.BmobRoleActivity;

/**
 * Created on 2018/12/5 15:59
 *
 * @author zhangchaozhou
 */
public class SecurityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
    }

    public void toAclActivity(View view) {
        startActivity(new Intent(this, AclActivity.class));
    }

    public void toBmobRoleActivity(View view) {
        startActivity(new Intent(this, BmobRoleActivity.class));
    }

    public void toSignVerifyActivity(View view) {
        startActivity(new Intent(this, SignVerifyActivity.class));
    }
}
