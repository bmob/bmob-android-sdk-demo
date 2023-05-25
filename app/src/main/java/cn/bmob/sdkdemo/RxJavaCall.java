package cn.bmob.sdkdemo;

import android.annotation.SuppressLint;

import java.util.List;

import cn.bmob.sdkdemo.bean.Post;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 2019-04-26 18:27
 *
 * @author zhangchaozhou
 */
public class RxJavaCall {

    @SuppressLint("CheckResult")
    public void call() {

        //登录接口
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername("");
        bmobUser.setPassword("");
        Observable<BmobUser> observableLogin = bmobUser.loginObservable(BmobUser.class);
        //查询用户帖子接口
        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        //RxJava链式调用
        observableLogin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user1 -> {
                    //TODO 登录成功
                })
                .observeOn(Schedulers.io())
                .flatMap((Function<BmobUser, ObservableSource<List<Post>>>) bmobUser1 -> {
                    //TODO 查询登录用户所发布的帖子
                    postBmobQuery.addWhereEqualTo("author",bmobUser1);
                    Observable<List<Post>> observableQuery = postBmobQuery.findObjectsObservable(Post.class);
                    return observableQuery;

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posts -> {
                    //TODO 查询用户帖子成功
                }, throwable -> {
                    //TODO 出错
                });
    }
}
