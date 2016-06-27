package cn.bmob.sdkdemo.relation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.sdkdemo.BaseActivity;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.MyUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class WeiboListActivity extends BaseActivity {
	
	ListView listView;
	EditText et_content;
	Button btn_publish;
	
	static List<Post> weibos = new ArrayList<Post>();
	MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weibo);
		setTitle("微博列表");

		adapter = new MyAdapter(this);
		et_content = (EditText) findViewById(R.id.et_content);
		btn_publish = (Button) findViewById(R.id.btn_publish);
		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);

		btn_publish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				publishWeibo(et_content.getText().toString());
			}
		});

		findWeibos();
	}

	/**
	 * 查询微博
	 */
	private void findWeibos(){
		MyUser user = BmobUser.getCurrentUser(MyUser.class);
		BmobQuery<Post> query = new BmobQuery<Post>();
		query.addWhereEqualTo("author", user);	// 查询当前用户的所有微博
		query.order("-updatedAt");
		query.include("author");// 希望在查询微博信息的同时也把发布人的信息查询出来，可以使用include方法
		query.findObjects(new FindListener<Post>() {
			@Override
			public void done(List<Post> object, BmobException e) {
				if(e==null){
					weibos = object;
					adapter.notifyDataSetChanged();
					et_content.setText("");
				}else{
					loge(e);
				}
			}

		});

		//等价于下面的sql语句查询
//		String sql = "select include author,* from Post where author = pointer('_User', "+"'"+user.getObjectId()+"')";
//		new BmobQuery<Post>().doSQLQuery(sql, new SQLQueryListener<Post>(){
//
//			@Override
//			public void done(BmobQueryResult<Post> result, BmobException e) {
//				if(e ==null){
//					List<Post> list = (List<Post>) result.getResults();
//					if(list!=null && list.size()>0){
//						weibos = list;
//						adapter.notifyDataSetChanged();
//						et_content.setText("");
//					}else{
//						Log.i("smile", "查询成功，无数据返回");
//					}
//				}else{
//					Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
//				}
//			}
//		});
	}

	/**
	 * 发布微博，发表微博时关联了用户类型，是一对一的体现
	 */
	private void publishWeibo(String content){
		MyUser user = BmobUser.getCurrentUser(MyUser.class);
		if(user == null){
			toast("发布微博前请先登陆");
			return;
		}else if(TextUtils.isEmpty(content)){
			toast("发布内容不能为空");
			return;
		}
		// 创建微博信息
		Post weibo = new Post();
		weibo.setContent(content);
		weibo.setAuthor(user);
		weibo.save(new SaveListener<String>() {

			@Override
			public void done(String s, BmobException e) {
				if(e==null){
					toast("发布成功");
					findWeibos();
				}else{
					loge(e);
				}
			}
		});
	}

	private static class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		private Context mContext;

		public MyAdapter(Context context) {
			mContext = context;
			mInflater = LayoutInflater.from(context);
		}

		static class ViewHolder {
			TextView tv_content;
			TextView tv_author;
		}

		@Override
		public int getCount() {
			return weibos.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_weibo, null);

				holder = new ViewHolder();
				holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
				holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Bind the data efficiently with the holder.
			final Post weibo = weibos.get(position);
			MyUser user = weibo.getAuthor();
			holder.tv_author.setText("发布人："+(user==null?"":user.getUsername()));

			final String str = weibo.getContent();

			holder.tv_content.setText(str);

			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, CommentListActivity.class);
					intent.putExtra("objectId", weibo.getObjectId());
					mContext.startActivity(intent);
				}
			});

			return convertView;
		}
	}
}

