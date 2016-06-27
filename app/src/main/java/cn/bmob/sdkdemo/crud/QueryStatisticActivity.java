package cn.bmob.sdkdemo.crud;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.sdkdemo.BaseActivity;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.GameScore;
import cn.bmob.sdkdemo.bean.MyUser;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;


public class QueryStatisticActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
						R.array.bmob_statistic_list));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				testBmob(position + 1);
			}
		});
		createGameScores();
	}

	public void createGameScores() {
		List<BmobObject> scores = new ArrayList<BmobObject>();
		//添加测试数据
		for (int i = 0; i < 5; i++) {
			GameScore score = new GameScore();
			score.setGps(new BmobGeoPoint(112.934755,24.52065));
			MyUser user = BmobUser.getCurrentUser(MyUser.class);
			score.setName(user.getUsername());
			score.setPlayer(user);
			score.setGame("地铁跑酷");
			score.setPlayScore(5 + i);
			score.setSignScore(i);
			scores.add(score);
		}
		//
		for (int i = 0; i < 5; i++) {
			GameScore score = new GameScore();
			score.setGps(new BmobGeoPoint(111.934755,25.52065));
			MyUser user = BmobUser.getCurrentUser(MyUser.class);//为了测试pointer类型，因此需要用户登陆
			score.setName(user.getUsername());
			score.setPlayer(user);
			score.setGame("部落冲突");
			score.setPlayScore(10 + i);
			score.setSignScore(5+i);
			scores.add(score);
		}
		BmobBatch batch = new BmobBatch();
		batch.insertBatch(scores);
		batch.doBatch(new QueryListListener<BatchResult>() {
			@Override
			public void done(List<BatchResult> results, BmobException ex) {
				if(ex==null){//这个ex只是代表此次请求正常返回-至于返回的是正确的还是错误的，需要查看BatchResult里面的数据
					for(int i=0;i<results.size();i++){
						BatchResult result= results.get(i);
						if(result.isSuccess()){//只有批量添加才返回objectId
							log(result.getObjectId());
						}else{
							BmobException error= result.getError();
							log(error.getErrorCode()+","+error.getMessage());
						}
					}
				}else{
					loge(ex);
				}
			}
		});
	}

	private void testBmob(int pos) {
		switch (pos) {
			case 1:// 计算单个列的总和
				querySum();
				break;
			case 2:// 分组计算总和
				querySumByGroup();
				break;
			case 3:// 多个分组并计算多个列的总和
				querySumsByGroups();
				break;
			case 4:// 分组计算总和并返回满足过滤条件的
				querySumByHaving();
				break;
			case 5:// 分组计算总和并返回每个分组的记录数
				querySumByGroupCount();
				break;
			case 6:// 获取不重复的列值
				queryScores();
				break;
			case 7:// 其他（平均值、最大、最小值）
				queryOthers();
				break;
		}
	}

	/**
	 * 查询游戏总分
	 *
	 * @method querySum
	 * @params
	 * @return void
	 * @exception
	 */
	private void querySum() {
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.sum(new String[] { "playScore" });
		query.findStatistics(GameScore.class,new QueryListener<JSONArray>() {

			@Override
			public void done(JSONArray ary, BmobException e) {
				if(e==null){
					if(ary!=null){//
						try {
							JSONObject obj = ary.getJSONObject(0);
							int sum = obj.getInt("_sumPlayScore");
							showToast("游戏总得分：" + sum);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					}else{
						showToast("查询成功，无数据");
					}
				}else{
					loge(e);
				}
			}

		});

	}

	/**
	 * 分组计算总和
	 *
	 * @method queryGroupSum
	 * @return void
	 * @exception
	 */
	private void querySumByGroup() {
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.sum(new String[] { "playScore" });//计算得分总和
		query.groupby(new String[] { "createdAt" });//按照时间进行分组
		query.order("-createdAt");// 降序排列
		query.setLimit(1000);
		query.findStatistics(GameScore.class,new QueryListener<JSONArray>() {

			@Override
			public void done(JSONArray ary, BmobException e) {
				if(e==null){
					if(ary!=null){//
						int length = ary.length();
						try {
							for (int i = 0; i < length; i++) {
								JSONObject obj = ary.getJSONObject(i);
								int playscore = obj.getInt("_sumPlayScore");
								String createDate = obj.getString("createdAt");
								showToast("游戏总得分：" + playscore + ",时间："
										+ createDate);
							}
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					}else{
						showToast("查询成功，无数据");
					}
				}else{
					loge(e);
				}
			}

		});
	}

	/**
	 * 多个分组并计算多个总和
	 * @method queryGroupSum
	 * @return void
	 * @exception
	 */
	private void querySumsByGroups() {
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.sum(new String[] { "playScore", "signScore" });//求多个列的总和
		query.groupby(new String[] { "createdAt", "game" });//按照时间和游戏进行分组
		query.order("-createdAt");//降序排列
		query.setLimit(100);
		query.findStatistics(GameScore.class,new QueryListener<JSONArray>() {

			@Override
			public void done(JSONArray ary, BmobException e) {
				if(e==null){
					if(ary!=null){
						int length = ary.length();
						try {
							for (int i = 0; i < length; i++) {
								JSONObject obj = ary.getJSONObject(i);
								int playscore = obj.getInt("_sumPlayScore");
								int signscore = obj.getInt("_sumSignScore");
								String createDate = obj.getString("createdAt");
								String game = obj.getString("game");
								showToast("游戏总得分：" + playscore + ",签到得分："
										+ signscore + ",时间:" + createDate+",game:"+game);
							}
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					} else {
						showToast("查询成功，无数据");
					}
				}else{
					loge(e);
				}
			}
		});
	}

	/**
	 * 分组计算总和并只返回满足条件的部分值
	 * @method queryGroupSum
	 * @return void
	 * @exception
	 */
	private void querySumByHaving() {
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.sum(new String[] {"playScore"});//计算总得分数
		query.groupby(new String[] {"game"});//分组条件：按游戏名进行分组
		query.order("-createdAt");// 降序排列
		HashMap<String, Object> map = new HashMap<String, Object>();
		JSONObject js = new JSONObject();
		try {
			js.put("$gt", 150);
		} catch (JSONException e1) {
		}
		map.put("_sumPlayScore", js);//过滤条件：总得分数大于150,只能过滤（sum等的查询条件）
		query.having(map);
		query.addWhereGreaterThan("playScore", 10);//增加where查询条件，得分数大于10的
		query.setLimit(100);
		query.findStatistics(GameScore.class,new QueryListener<JSONArray>() {

			@Override
			public void done(JSONArray ary, BmobException e) {
				if(e==null){
					if(ary!=null){
						int length = ary.length();
						try {
							for (int i = 0; i < length; i++) {
								JSONObject obj = ary.getJSONObject(i);
								int playscore = obj.getInt("_sumPlayScore");//过滤条件的key是什么，返回的数据中就有什么
								String game = obj.getString("game");
								showToast("游戏得分：" + playscore + ",游戏名 = "+ game);
							}
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					} else {
						showToast("查询成功，无数据");
					}
				}else{
					loge(e);
				}
			}

		});
	}

	/**
	 * 分组计算总和并返回每个分组的记录数
	 * @method queryGroupSum
	 * @return void
	 * @exception
	 */
	private void querySumByGroupCount() {
		// 查询创建时间按天统计所有玩家的得分和每一天有多少条玩家的得分记录，并按时间降序:
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.sum(new String[] { "playScore" });// 统计总得分
		query.groupby(new String[] { "createdAt" });// 按照时间分组
		query.order("-createdAt");// 降序排列
		query.setHasGroupCount(true);// 统计每一天有多少玩家的得分记录，默认不返回分组个数
		query.findStatistics(GameScore.class,new QueryListener<JSONArray>() {

			@Override
			public void done(JSONArray ary, BmobException e) {
				if(e==null){
					if (ary!=null) {
						int length = ary.length();
						try {
							for (int i = 0; i < length; i++) {
								JSONObject obj = ary.getJSONObject(i);
								int playscore = obj.getInt("_sumPlayScore");
								String createDate = obj.getString("createdAt");
								int count = obj.getInt("_count");
								showToast("游戏总得分：" + playscore + ",总共统计了"
										+ count + "条记录,统计时间 = "+ createDate);
							}
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					} else {
						showToast("查询成功，无数据");
					}
				}else{
					loge(e);
				}
			}
		});
	}

	/**查询所有的得分
	 * @method queryScore
	 * @params
	 * @return void
	 * @exception
	 */
	private void queryScores(){
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.groupby(new String[]{"playScore"});
		query.order("-createdAt");
		query.findStatistics(GameScore.class, new QueryListener<JSONArray>() {

			@Override
			public void done(JSONArray ary, BmobException e) {
				if(e==null){
					if (ary!=null) {
						int length = ary.length();
						try {
							for (int i = 0; i < length; i++) {
								JSONObject obj = ary.getJSONObject(i);
								String score = obj.getString("playScore");
								log("游戏分数：" + score);
							}
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					} else {
						showToast("查询成功，无数据");
					}
				}else{
					loge(e);
				}
			}

		});
	}

	/**查询其他
	 * @method queryOthers
	 * @params
	 * @return void
	 * @exception
	 */
	private void queryOthers(){
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.average(new String[]{"playScore"});//查询某列的平均值
//		query.min(new String[]{"playScore"});//查询最小值
//		query.max(new String[]{"playScore"});//查询最大值
		query.groupby(new String[]{"createdAt"});
		query.findStatistics(GameScore.class, new QueryListener<JSONArray>() {

			@Override
			public void done(JSONArray ary, BmobException e) {
				if(e==null){
					if (ary!=null) {
						try {
							JSONObject obj = ary.getJSONObject(0);
						double playscore = obj.getDouble("_avgPlayScore");
//							int minscore = obj.getInt("_minPlayScore");
//						int maxscore = obj.getInt("_maxPlayScore");
							String createDate = obj.getString("createdAt");
							showToast("avgscore = " + playscore+ ",统计时间 = "+ createDate);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					} else {
						showToast("查询成功，无数据");
					}
				}else{
					loge(e);
				}
			}
		});
	}

}
