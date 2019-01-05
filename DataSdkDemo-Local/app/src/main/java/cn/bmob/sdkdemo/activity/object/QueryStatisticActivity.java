package cn.bmob.sdkdemo.activity.object;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


/**
 *
 * 统计查询
 * @author zhangchaozhou
 */
public class QueryStatisticActivity extends AppCompatActivity {

    @BindView(R.id.iv_avatar)
    AppCompatImageView mIvAvatar;
    @BindView(R.id.btn_statistics)
    AppCompatButton mBtnStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_statistics);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.btn_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_statistics:
                try {
                    statistics();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    /**
     * TODO 不带groupby的查询结果，统计全部。
     * [{
     * 	"_avgFault": 1.625,
     * 	"_avgFoul": 3.75,
     * 	"_avgScore": 25.75,
     * 	"_avgSteal": 2,
     * 	"_count": 79,
     * 	"_maxFault": 3,
     * 	"_maxFoul": 6,
     * 	"_maxScore": 53,
     * 	"_maxSteal": 4,
     * 	"_minFault": 1,
     * 	"_minFoul": 2,
     * 	"_minScore": 11,
     * 	"_minSteal": 1,
     * 	"_sumFault": 13,
     * 	"_sumFoul": 30,
     * 	"_sumScore": 206,
     * 	"_sumSteal": 16
     * }]
     */
    /**
     * TODO 带groupby的查询结果，根据country分组统计。
     * [{
     * "_avgFault": 1.6666666666666667,
     * "_avgFoul": 2.3333333333333335,
     * "_avgScore": 25.666666666666668,
     * "_avgSteal": 1.3333333333333333,
     * "_count": 3,
     * "_maxFault": 2,
     * "_maxFoul": 3,
     * "_maxScore": 53,
     * "_maxSteal": 2,
     * "_minFault": 1,
     * "_minFoul": 2,
     * "_minScore": 12,
     * "_minSteal": 1,
     * "_sumFault": 5,
     * "_sumFoul": 7,
     * "_sumScore": 77,
     * "_sumSteal": 4,
     * "country": "china"
     * }, {
     * "_avgFault": 2,
     * "_avgFoul": 4.5,
     * "_avgScore": 22,
     * "_avgSteal": 2.5,
     * "_count": 2,
     * "_maxFault": 3,
     * "_maxFoul": 5,
     * "_maxScore": 23,
     * "_maxSteal": 3,
     * "_minFault": 1,
     * "_minFoul": 4,
     * "_minScore": 21,
     * "_minSteal": 2,
     * "_sumFault": 4,
     * "_sumFoul": 9,
     * "_sumScore": 44,
     * "_sumSteal": 5,
     * "country": "usa"
     * }, {
     * "_avgFault": 1.3333333333333333,
     * "_avgFoul": 4.666666666666667,
     * "_avgScore": 28.333333333333332,
     * "_avgSteal": 2.3333333333333335,
     * "_count": 3,
     * "_maxFault": 2,
     * "_maxFoul": 6,
     * "_maxScore": 43,
     * "_maxSteal": 4,
     * "_minFault": 1,
     * "_minFoul": 2,
     * "_minScore": 11,
     * "_minSteal": 1,
     * "_sumFault": 4,
     * "_sumFoul": 14,
     * "_sumScore": 85,
     * "_sumSteal": 7,
     * "country": "uk"
     * }, {
     * "_avgFault": null,
     * "_avgFoul": null,
     * "_avgScore": null,
     * "_avgSteal": null,
     * "_count": 71,
     * "_maxFault": null,
     * "_maxFoul": null,
     * "_maxScore": null,
     * "_maxSteal": null,
     * "_minFault": null,
     * "_minFoul": null,
     * "_minScore": null,
     * "_minSteal": null,
     * "_sumFault": 0,
     * "_sumFoul": 0,
     * "_sumScore": 0,
     * "_sumSteal": 0,
     * "country": null
     * }]
     */

    /**
     * TODO 带groupby和having的查询结果
     * [{
     * 	"_avgFault": 1.3333333333333333,
     * 	"_avgFoul": 4.666666666666667,
     * 	"_avgScore": 28.333333333333332,
     * 	"_avgSteal": 2.3333333333333335,
     * 	"_count": 3,
     * 	"_maxFault": 2,
     * 	"_maxFoul": 6,
     * 	"_maxScore": 43,
     * 	"_maxSteal": 4,
     * 	"_minFault": 1,
     * 	"_minFoul": 2,
     * 	"_minScore": 11,
     * 	"_minSteal": 1,
     * 	"_sumFault": 4,
     * 	"_sumFoul": 14,
     * 	"_sumScore": 85,
     * 	"_sumSteal": 7,
     * 	"country": "uk"
     * }]
     */

    /**
     * “group by”从字面意义上理解就是根据“by”指定的规则对数据进行分组，所谓的分组就是将一个“数据集”划分成若干个“小区域”，然后针对若干个“小区域”进行数据处理。
     * where 子句的作用是在对查询结果进行分组前，将不符合where条件的行去掉，即在分组之前过滤数据，where条件中不能包含聚组函数，使用where条件过滤出特定的行。
     * having 子句的作用是筛选满足条件的组，即在分组之后过滤数据，条件中经常包含聚组函数，使用having 条件过滤出特定的组，也可以使用多个分组标准进行分组。
     *
     * @throws JSONException
     */
    private void statistics() throws JSONException {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        //总和
        bmobQuery.sum(new String[]{"score", "steal", "foul", "fault"});
        //平均值
        bmobQuery.average(new String[]{"score", "steal", "foul", "fault"});
        //最大值
        bmobQuery.max(new String[]{"score", "steal", "foul", "fault"});
        //最小值
        bmobQuery.min(new String[]{"score", "steal", "foul", "fault"});
        //是否返回所统计的总条数
        bmobQuery.setHasGroupCount(true);
        //根据所给列分组统计
        bmobQuery.groupby(new String[]{"country"});
        //对统计结果进行过滤
        HashMap<String, Object> map = new HashMap<>(1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("$gt", 28);
        map.put("_avgScore", jsonObject);
        bmobQuery.having(map);
        //开始统计查询
        bmobQuery.findStatistics(User.class, new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnStatistics, "查询成功：" + jsonArray.length(), Snackbar.LENGTH_LONG).show();
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        int sum = jsonObject.getInt("_sumScore");
                        Snackbar.make(mBtnStatistics, "sum：" + sum, Snackbar.LENGTH_LONG).show();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnStatistics, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
