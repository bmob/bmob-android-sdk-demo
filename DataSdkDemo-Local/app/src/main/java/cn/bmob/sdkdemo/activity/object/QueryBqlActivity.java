package cn.bmob.sdkdemo.activity.object;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * bql查询：Bmob Query Language
 *
 * @author zhangchaozhou
 */
public class QueryBqlActivity extends AppCompatActivity {

    @BindView(R.id.btn_query_sql)
    AppCompatButton mBtnQuerySql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_sql);
        ButterKnife.bind(this);
    }


    /**
     * bql查询
     */


    /**
     * 查询全部
     * select * from _User
     */


    /**
     * 比较查询
     * 查询分数小于10的用户
     * select * from _User where score<10
     * 查询国家是中国的用户
     * select * from _User where country='中国'
     * 查询国家不是中国的用户
     * select * from _User where country!='中国'
     */


    /**是否存在
     * 查询不存在犯规的用户
     * select * from _User where foul is not exists
     */


    /**
     * 模糊查询
     * 查询昵称以玫瑰开头的用户
     * select * from _User where nickname like 玫瑰%
     * 查询昵称不以玫瑰开头的用户
     * select * from _User where nickname not like 玫瑰%
     * 查询昵称以玫瑰开头的用户
     * select * from _User where nickname regexp 玫瑰.*
     */


    /**
     * 数组查询
     * 查询别名是玫瑰的用户
     * select * from _User where alias ='玫瑰'
     * 查询别名是玫瑰和rose的用户
     * select * from _User where alias all ('玫瑰','rose')
     */


    /**
     * 地理位置查询
     * 1、查询指定地理位置的用户
     * 第一种写法
     * select * from _User where address = geopoint(112.934755,24.52065)
     * 第二种写法
     * select * from _User where address = {'__type':'GeoPoint','latitude':24.52065,'longitude':112.934755}
     * 2、查询指定地理位置附近的用户
     * select * from _User where address near geopoint(112.934755,24.52065)";//查询在指定地理位置附近的游戏记录（由近及远的排列）
     * select * from _User where address near [112.934755,24.52065]
     * 3、为附近的用户限定最大搜索距离
     * select * from _User where address near [112.934755,24.52065] max 1 km
     * 4、查询矩形范围内的用户
     * select * from _User where address within [102.934755,24.52065] and [112.934755,24.52065]
     */


    /**
     * 时间查询
     * Date date = new Date();
     * 查询现在之前创建的用户
     * 第一种写法
     * select * from _User where createdAt < date('"+new BmobDate(date).getDate()+"')
     * 第二种写法
     * select * from _User where createdAt < {'__type': 'Date','iso': '"+new BmobDate(date).getDate()+"'}
     */


    /**
     * 关联pointer查询
     * 查询当前用户发布的帖子
     * User user = BmobUser.getCurrentUser( User.class);
     * select * from Post where author = pointer('_User', "+"'"+user.getObjectId()+"')
     * select * from Post where author = {'__type':'Pointer','className':'_User','objectId':'"+user.getObjectId()+"'}
     */


    /**
     * include查询
     * 查询帖子包括帖子作者的信息
     * select include author,* from Post limit 5
     */


    /**
     * 子查询
     * 查询昵称为玫瑰、牡丹的用户
     * select * from _User where nickname in ('玫瑰','牡丹')
     * 查询大于10岁的且昵称为玫瑰、牡丹的用户
     * select * from _User where nickname in (select name from _User where age>10)
     */


    /**
     *
     * 关系查询relation
     * 查询某条帖子Post的评论包含评论的发布者信息
     * select include author,* from Comment where related comment to pointer('Post','15e67b68ce')
     */


    /**
     * 复合查询
     * select * from _User where score>10 and score<=15
     *
     * select * from _User where score>10 and score<=15 or score=5
     *
     * select * from _User where (score>10 and score<=15) or score=5
     *
     */


    /**
     * 分页查询
     *
     * 从10条开始向后查找10条数据
     *
     * select * from _User limit 10,10
     *
     */


    /**
     * 结果排序
     *
     * select * from _User order by score,foul desc
     * select * from Post where author in (b0fa96dd7d)
     * select * from _User where age in (22,223)
     * select * from Post where content in (select username in _User where age = 456)
     * select * from Post where title in (select username in _User where age = 456)
     * select * from Post where title in (select username from _User where age = 456)
     * select * from Post where author in (select username from _User where age = 789)
     * select include author,* from Post where author = pointer('_User','b0fa96dd7d')
     * select * from Post where author = pointer('_User','b0fa96dd7d')
     * select postId from Post where comment = pointer('Comment','90f0da7767')
     * select * from Post where postId not in(23,231)
     * select * from Post where comment =  pointer('Comment','90f0da7767')
     * select * from Post where comment  in (select objectId from Comment where author=pointer('_User', 'af873c6d32'))
     * select * from _User where username = 'smile'
     * select distinct nickname from _User
     * select * from Post where author in (select * from _User where age is exists)
     * select username from _User where age = 456
     * select * from Post where name is not exists
     * select * from _User limit 3,4
     * select * from Post where name like 'ha%'
     * select * from Post where  age = 22
     *
     */


    /**
     * 结果计数
     * 查询_User表的总数
     * select count(*) from _User
     * 查询_User表中总记录数并返回所有记录信息
     * select count(*),* from _User
     * 查询_User表中的总数并返回每条记录的游戏名
     * select count(*),game from _User
     * 查询总的记录数并返回得分在10-20之间的信息
     * select count(*) from _User where playScore>10 and where playScore<20
     */


    /**
     * 执行sql语句查询_User表
     * select count(*),* from _User
     * select count(*),game from _User
     *
     * @param sql
     */
    public void queryBql(String sql) {
        BmobQuery<User> query = new BmobQuery<>();
        query.doSQLQuery(sql, new SQLQueryListener<User>() {

            @Override
            public void done(BmobQueryResult<User> result, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnQuerySql, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnQuerySql, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 统计查询
     * 求分数和，按照用户昵称进行分组并按照时间降序排列
     * select sum(score) from _User group by nickname order by -createdAt
     */
    private void queryStatisticSql() {
        String bql = "select sum(score) from _User group by nickname order by -createdAt";
        new BmobQuery<User>().doStatisticQuery(bql, new QueryListener<JSONArray>() {

            @Override
            public void done(JSONArray ary, BmobException e) {
                if (e == null) {
                    if (ary != null) {
                        try {
                            JSONObject obj = ary.getJSONObject(0);
                            int sum = obj.getInt("_sumScore");
                            String nickname = obj.getString("nickname");
                            Snackbar.make(mBtnQuerySql, nickname + "-" + sum, Snackbar.LENGTH_LONG).show();
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        Snackbar.make(mBtnQuerySql, "查询成功，无数据", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mBtnQuerySql, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 占位符查询1
     * 查询在某时间之前，在特定地理位置附近的用户
     * select * from _User where createdAt > date( ?) and address near geopoint ( ?,?)
     */
    private void queryPlaceholderSql1() {
        BmobQuery<User> query = new BmobQuery<>();
        String sql = "select * from _User where createdAt > date( ?) and address near geopoint ( ?,?)";
        query.setSQL(sql);
        query.setPreparedParams(new Object[]{"2015-05-12 00:00:00", 112.934755, 24.52065});
        query.doSQLQuery(new SQLQueryListener<User>() {

            @Override
            public void done(BmobQueryResult<User> result, BmobException e) {
                if (e == null) {
                    List<User> list =  result.getResults();
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            User user = list.get(i);
                            Snackbar.make(mBtnQuerySql, user.getNickname()+"-"+user.getScore(), Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(mBtnQuerySql, "查询成功，无数据", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mBtnQuerySql, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 占位符查询2
     * 查询在某时间之前，在特定地理位置附近的用户
     * select * from _User where createdAt > date( ?) and address near geopoint ( ?,?)
     */
    private void queryPlaceholderSql2() {
        String sql = "select * from _User where createdAt > date( ?) and address near geopoint ( ?,?)";
        //等同于下面的查询方法
        new BmobQuery<User>().doSQLQuery(sql,new SQLQueryListener<User>(){

            @Override
            public void done(BmobQueryResult<User> result, BmobException e) {
                if (e == null) {
                    List<User> list =  result.getResults();
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            User user = list.get(i);
                            Snackbar.make(mBtnQuerySql, user.getNickname()+"-"+user.getScore(), Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(mBtnQuerySql, "查询成功，无数据", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mBtnQuerySql, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        },"2015-05-12 00:00:00",112.934755,24.52065);

    }

    @OnClick(R.id.btn_query_sql)
    public void onViewClicked() {
        String sql = "select * from _User";
        queryBql(sql);


        queryStatisticSql();

        queryPlaceholderSql1();

        queryPlaceholderSql2();
    }
}
