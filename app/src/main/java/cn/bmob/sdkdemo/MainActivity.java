package cn.bmob.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.sdkdemo.acl.ACLActivity;
import cn.bmob.sdkdemo.autoupdate.ActAutoUpdate;
import cn.bmob.sdkdemo.crud.CRUDActivity;
import cn.bmob.sdkdemo.crud.QuerySQLActivity;
import cn.bmob.sdkdemo.crud.QueryStatisticActivity;
import cn.bmob.sdkdemo.file.BmobFileActivity;
import cn.bmob.sdkdemo.location.LocationActivity;
import cn.bmob.sdkdemo.push.ActBmobPush;
import cn.bmob.sdkdemo.relation.WeiboListActivity;
import cn.bmob.sdkdemo.sms.SMSCodeActivity;
import cn.bmob.sdkdemo.user.UserActivity;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobTableSchema;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.ValueEventListener;

public class MainActivity extends BaseActivity {

	protected ListView mListview;
	protected BaseAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toast.makeText(this, "请记得将BmobApplication当中的APPID替换为你的appid", Toast.LENGTH_LONG).show();
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.tv_item, getResources().getStringArray(R.array.bmob_list));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				testBmob(position + 1);
			}
		});
	}

	private void testBmob(int pos) {
		switch (pos) {
			case 1:
				startActivity(new Intent(this, UserActivity.class));
				break;
			case 2:
				startActivity(new Intent(this, CRUDActivity.class));
				break;
			case 3:
				// 关联数据
				startActivity(new Intent(this, WeiboListActivity.class));
				break;
			case 4:
				// 批量操作
				startActivity(new Intent(this, BatchActionActivity.class));
				break;
			case 5:
				startActivity(new Intent(this, ACLActivity.class));
				break;
			case 6:
				startActivity(new Intent(this, BmobFileActivity.class));
				break;
			case 7:
				cloudCode();
				break;
			case 8:
				realTime();
				break;
			case 9:
				// 推送服务
				startActivity(new Intent(this, ActBmobPush.class));
				break;
			case 10:
				// 应用自动更新
				startActivity(new Intent(this, ActAutoUpdate.class));
				break;
			case 11:
				//地理位置
				startActivity(new Intent(this, LocationActivity.class));
				break;
			case 12:
				getServerTime();
				break;
			case 13://统计查询
				startActivity(new Intent(this, QueryStatisticActivity.class));
				break;
			case 14://SQL查询
				startActivity(new Intent(this, QuerySQLActivity.class));
				break;
			case 15://
				startActivity(new Intent(this, SMSCodeActivity.class));
				break;
			case 16://表结构
//				getAllTableSchema();
				getTableSchema();
				break;
		}
	}

	/**
	 * 获取服务器时间
	 */
	private void getServerTime() {
		Bmob.getServerTime(new QueryListener<Long>() {

			@Override
			public void done(Long time, BmobException e) {
				if(e==null){
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					String times = formatter.format(new Date(time * 1000L));
					toast("当前服务器时间为:" + times);
				}else{
					toast("获取服务器时间失败:" + e.getMessage());

				}
			}

		});
	}

	/**
	 * 云端代码
	 */
	private void cloudCode() {
//		后台的云端代码：
//		function onRequest(request, response, modules) {
//			  //获取SDK客户端上传的name参数
//			  var name = request.body.inputData;
//			  var json = JSON.parse(name);
//			  response.end(json.Type);
//			}
		//测试json请求参数
		AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
		JSONObject obj = new JSONObject();
		try {
			JSONObject o= new JSONObject();
			o.put("Type", "SignUp");
			o.put("Phone", "111");
			obj.put("inputData", o);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		ace.callEndpoint("t", obj, new CloudCodeListener() {

			@Override
			public void done(Object object, BmobException e) {
				if(e==null){
					String result = object.toString();
					log("云端usertest方法返回:"+result);
					toast("云端usertest方法返回:" + result);
				}else{
					toast("访问云端usertest方法失败:" + e.getMessage());
				}
			}
		});
//		//不带请求的云端代码
//		ace.callEndpoint("testJSONObject", new CloudCodeListener() {
//
//			@Override
//			public void done(Object object, BmobException e) {
//				if(e==null){
//					toast("云端usertest方法返回:" + object.toString());
//					String json = object.toString();
//					try {
//						JSONObject obj = new JSONObject(json);
//						String ud = obj.getString("ud");
//						toast("云端usertest方法返回ud:" + ud);
//					} catch (Exception e1) {
//						toast("云端usertest方法返回错误:" + e1.getMessage());
//					}
//				}else{
//					toast("访问云端usertest方法失败:" + e.getMessage());
//				}
//			}
//		});
	}

	private void realTime(){
		final BmobRealTimeData rtd = new BmobRealTimeData();
		rtd.start(new ValueEventListener() {

			@Override
			public void onDataChange(JSONObject data) {
				log("onDataChange：data = "+data);
			}

			@Override
			public void onConnectCompleted(Exception ex) {
				log("连接成功:"+rtd.isConnected());
				if(rtd.isConnected()){
					// 监听表更新
					rtd.subTableUpdate("Person");
				}
			}
		});
	}

	/**获取指定账户下的所有表的表结构信息
	 * @method getAllTableSchema
	 * @return void
	 * @exception
	 */
	private void getAllTableSchema(){
		Bmob.getAllTableSchema(new QueryListListener<BmobTableSchema>() {

			@Override
			public void done(List<BmobTableSchema> schemas, BmobException ex) {
				if(ex==null && schemas!=null && schemas.size()>0){
					log(""+schemas.get(0).getClassName()+"---"+schemas.get(0).getFields().toString());
				}else{
					toast("获取所有表的表结构信息失败:" + ex.getLocalizedMessage()+"("+ex.getErrorCode()+")");
				}
			}
		});
	}

	/** 获取指定表的表结构信息
	 * @method getTableSchema
	 * @return void
	 * @exception
	 */
	public void getTableSchema(){
		Bmob.getTableSchema("_User", new QueryListener<BmobTableSchema>() {

			@Override
			public void done(BmobTableSchema schema, BmobException ex) {
				if(ex==null){
					log(""+schema.getClassName()+"---"+schema.getFields().toString());
				}else{
					toast("获取用户表的表结构信息失败:" + ex.getLocalizedMessage()+"("+ex.getErrorCode()+")");
				}
			}
		});
	}

	public void onBackPressed() {
		finish();
	};
}
