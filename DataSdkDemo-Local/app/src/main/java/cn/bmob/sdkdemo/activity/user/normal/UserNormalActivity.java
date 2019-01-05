package cn.bmob.sdkdemo.activity.user.normal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.OtherService;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 2018/11/27 16:11
 * 用户的正常操作
 *
 * @author zhangchaozhou
 */
public class UserNormalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_normal);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_sign_up, R.id.btn_login, R.id.btn_current_user, R.id.btn_is_login,
            R.id.btn_logout, R.id.btn_update_user, R.id.btn_reset_password,
            R.id.btn_fetch_user_info, R.id.btn_fetch_user_info_json,R.id.btn_update_user_other_process})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                //账号密码注册
                signUp(view);
                break;
            case R.id.btn_login:
                //账号密码登录1
//                login(view);
                //账号密码登录2
                loginByAccount(view);
                break;
            case R.id.btn_current_user:
                if (BmobUser.isLogin()) {
                    User user = BmobUser.getCurrentUser(User.class);
                    Snackbar.make(view, "当前用户：" + user.getUsername() + "-" + user.getAge(), Snackbar.LENGTH_LONG).show();
                    String username = (String) BmobUser.getObjectByKey("username");
                    Integer age = (Integer) BmobUser.getObjectByKey("age");
                    Snackbar.make(view, "当前用户属性：" + username + "-" + age, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_is_login:
                if (BmobUser.isLogin()) {
                    User user = BmobUser.getCurrentUser(User.class);
                    Snackbar.make(view, "已经登录：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "尚未登录", Snackbar.LENGTH_LONG).show();
                }
                break;

            case R.id.btn_update_user:
                if (BmobUser.isLogin()) {
                    updateUser(view);
                } else {
                    Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show();
                }
                break;


            case R.id.btn_update_user_other_process:
                if (BmobUser.isLogin()) {
                    Intent intent=new Intent(getApplication(),OtherService.class);
                    startService(intent);
                } else {
                    Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show();
                }
                break;

            case R.id.btn_reset_password:
                if (BmobUser.isLogin()) {
                    resetPassword(view);
                } else {
                    Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_fetch_user_info:
                if (BmobUser.isLogin()) {
                    fetchUserInfo(view);
                } else {
                    Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_fetch_user_info_json:
                if (BmobUser.isLogin()) {
                    fetchUserJsonInfo(view);
                } else {
                    Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_logout:
                BmobUser.logOut();
                break;

            default:
                break;
        }
    }


    /**
     * 账号密码注册
     */
    private void signUp(final View view) {
        final User user = new User();
        user.setUsername("" + System.currentTimeMillis());
        user.setPassword("" + System.currentTimeMillis());
        user.setAge(18);
        user.setGender(0);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "注册成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 账号密码登录
     */
    private void login(final View view) {
        final User user = new User();
        //此处替换为你的用户名
        user.setUsername("username");
        //此处替换为你的密码
        user.setPassword("password");
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 账号密码登录
     */
    private void loginByAccount(final View view) {
        //此处替换为你的用户名密码
        BmobUser.loginByAccount("username", "password", new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }


    /**
     * 更新用户操作并同步更新本地的用户信息
     */
    private void updateUser(final View view) {
        final User user = BmobUser.getCurrentUser(User.class);
        user.setAge(20);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "更新用户信息成功：" + user.getAge(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "更新用户信息失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.e("error", e.getMessage());
                }
            }
        });
    }


    /**
     * 修改当前用户密码
     */
    private void resetPassword(final View view) {
        BmobUser.updateCurrentUserPassword("oldPassword", "newPassword", new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "重置密码成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "重置密码失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 同步控制台数据到缓存中
     *
     * @param view
     */
    private void fetchUserInfo(final View view) {
        BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    final User user = BmobUser.getCurrentUser(User.class);
                    Snackbar.make(view, "更新用户本地缓存信息成功：" + user.getUsername() + "-" + user.getAge(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("error", e.getMessage());
                    Snackbar.make(view, "更新用户本地缓存信息失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 获取控制台最新数据
     *
     * @param view
     */
    private void fetchUserJsonInfo(final View view) {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String json, BmobException e) {
                if (e == null) {
                    Log.e("success", json);
                    Snackbar.make(view, "获取控制台最新数据成功：" + json, Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("error", e.getMessage());
                    Snackbar.make(view, "获取控制台最新数据失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 查询用户表
     */
    private void queryUser(final View view) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "查询失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 提供旧密码修改密码
     */
    private void updatePassword(final View view){
        //TODO 此处替换为你的旧密码和新密码
        BmobUser.updateCurrentUserPassword("oldPwd", "newPwd", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "查询失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
