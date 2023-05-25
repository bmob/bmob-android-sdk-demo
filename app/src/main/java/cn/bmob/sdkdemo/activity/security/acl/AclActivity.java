package cn.bmob.sdkdemo.activity.security.acl;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Post;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobACL;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * ACL：Access Control List 访问控制列表
 * <p>
 * 每条数据对于每个用户或角色都可以设置相应的访问权限
 *
 * @author zhangchaozhou
 */
public class AclActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acl);
    }

    /**
     * 设置发布的帖子对所有用户的访问控制权限
     */
    public void onPublicAcl(View view) {
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            Snackbar.make(view, "请先登录", Snackbar.LENGTH_LONG).show();
        } else {
            Post post = new Post();
            post.setAuthor(user);
            post.setContent("content" + System.currentTimeMillis());
            post.setTitle("title" + System.currentTimeMillis());
            BmobACL bmobACL = new BmobACL();
            //设置此帖子为所有用户不可写
            bmobACL.setPublicWriteAccess(false);
            //设置此帖子为所有用户可读
            bmobACL.setPublicReadAccess(true);
            post.setACL(bmobACL);
            post.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Snackbar.make(view, "发布帖子成功", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * 设置发布的帖子对当前用户的访问控制权限
     */
    public void onUserAcl(View view) {
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            Snackbar.make(view, "请先登录", Snackbar.LENGTH_LONG).show();
        } else {
            Post post = new Post();
            post.setAuthor(user);
            post.setContent("content" + System.currentTimeMillis());
            post.setTitle("title" + System.currentTimeMillis());
            BmobACL bmobACL = new BmobACL();
            //设置此帖子为当前用户可写
            bmobACL.setReadAccess(user, true);
            //设置此帖子为所有用户可读
            bmobACL.setPublicReadAccess(true);
            post.setACL(bmobACL);
            post.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Snackbar.make(view, "发布帖子成功", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    /**
     * 设置发布的帖子对某种角色的访问控制权限
     */
    public void onRoleAcl(View view) {
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            Snackbar.make(view, "请先登录", Snackbar.LENGTH_LONG).show();
        } else {
            Post post = new Post();
            post.setAuthor(user);
            post.setContent("content" + System.currentTimeMillis());
            post.setTitle("title" + System.currentTimeMillis());
            BmobACL bmobACL = new BmobACL();
            //设置此帖子为当前用户可写
            bmobACL.setWriteAccess(user, true);
            //设置此帖子为某种角色可读
            bmobACL.setRoleReadAccess("female", true);
            post.setACL(bmobACL);
            post.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Snackbar.make(view, "发布帖子成功", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
