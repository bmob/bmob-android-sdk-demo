package cn.bmob.sdkdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.sdkdemo.bean.BankCard;
import cn.bmob.sdkdemo.bean.MyUser;
import cn.bmob.sdkdemo.bean.Person;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

public class BatchActionActivity extends BaseActivity {

	ListView mListview;
	BaseAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
				R.array.batch_action_list));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				testBatch(position + 1);
			}
		});
	}

	private void testBatch(int pos){
		switch (pos) {
			case 1:
				// 批量添加
				batchInsert();
				break;
			case 2:
				// 批量更新
				batchUpdate();
				break;
			case 3:
				// 批量删除
				batchDelete();
				break;
			case 4:
				//批量添加、删除、更新的同步提交batch();
				batch();
				break;
			default:
				break;
		}
	}

	/**
	 * 批量添加
	 */
	private void batchInsert(){
		List<BmobObject> persons = new ArrayList<BmobObject>();
		for (int i = 0; i < 3; i++) {
			Person person = new Person();
			person.setName("张三 "+i);
			person.setAddress("上海朝阳路"+i+"号");
			person.setGpsAdd(new BmobGeoPoint(112.934755, 24.52065));
			person.setUploadTime(new BmobDate(new Date()));
			List<String> hobbys = new ArrayList<String>();
			hobbys.add("阅读");
			hobbys.add("篮球");
			hobbys.add("唱歌");
			person.setHobby(hobbys);
			person.setBankCard(new BankCard("中国银行", "176672673687545097"+i));
			//可批量添加带Poniter类型的数据，例如
			person.setAuthor(BmobUser.getCurrentUser(MyUser.class));
			persons.add(person);
		}
		new BmobBatch().insertBatch(persons).doBatch(new QueryListListener<BatchResult>() {

			@Override
			public void done(List<BatchResult> o, BmobException e) {
				if(e==null){
					for(int i=0;i<o.size();i++){
						BatchResult result = o.get(i);
						BmobException ex =result.getError();
						if(ex==null){
							log("第"+i+"个数据批量添加成功："+result.getCreatedAt()+","+result.getObjectId()+","+result.getUpdatedAt());
						}else{
							log("第"+i+"个数据批量添加失败："+ex.getMessage()+","+ex.getErrorCode());
						}
					}
				}else{
					loge(e);
				}
			}
		});
	}

	/**
	 * 批量更新
	 */
	private void batchUpdate(){
		List<BmobObject> persons = new ArrayList<BmobObject>();
		Person p1 = new Person();
		p1.setObjectId("71ff16bdf9");
		p1.setAge(105);
		Person p2 = new Person();
		p2.setObjectId("599d76109b");
		p2.setAge(106);
		p2.setGender(false);
		Person p3 = new Person();
		p3.setObjectId("97cefecc04");
		p3.setAge(107);

		persons.add(p1);
		persons.add(p2);
		persons.add(p3);

		new BmobBatch().updateBatch(persons).doBatch(new QueryListListener<BatchResult>() {

			@Override
			public void done(List<BatchResult> o, BmobException e) {
				if(e==null){
					for(int i=0;i<o.size();i++){
						BatchResult result = o.get(i);
						BmobException ex =result.getError();
						if(ex==null){
							log("第"+i+"个数据批量更新成功："+result.getUpdatedAt());
						}else{
							log("第"+i+"个数据批量更新失败："+ex.getMessage()+","+ex.getErrorCode());
						}
					}
				}else{
					loge(e);
				}
			}
		});

	}

	/**
	 * 批量删除
	 */
	private void batchDelete(){
		List<BmobObject> persons = new ArrayList<BmobObject>();
		Person p1 = new Person();
		p1.setObjectId("766c9556cf");
		Person p2 = new Person();
		p2.setObjectId("3cfc36d32a");
		Person p3 = new Person();
		p3.setObjectId("e003224592");

		persons.add(p1);
		persons.add(p2);
		persons.add(p3);

		new BmobBatch().deleteBatch(persons).doBatch(new QueryListListener<BatchResult>() {

			@Override
			public void done(List<BatchResult> o, BmobException e) {
				if(e==null){
					for(int i=0;i<o.size();i++){
						BatchResult result = o.get(i);
						BmobException ex =result.getError();
						if(ex==null){
							log("第"+i+"个数据批量删除成功");
						}else{
							log("第"+i+"个数据批量删除失败："+ex.getMessage()+","+ex.getErrorCode());
						}
					}
				}else{
					loge(e);
				}
			}
		});
	}


	/**
	 * 同步执行多种批量操作
	 */
	public void batch(){
		BmobBatch batch =new BmobBatch();
		//批量添加
		List<BmobObject> persons = new ArrayList<BmobObject>();
		Person person = new Person();
		person.setName("张三 ");
		person.setAddress("上海朝阳路0号");
		person.setGpsAdd(new BmobGeoPoint(112.934755, 24.52065));
		person.setUploadTime(new BmobDate(new Date()));
		List<String> hobbys = new ArrayList<String>();
		hobbys.add("阅读");
		hobbys.add("篮球");
		hobbys.add("唱歌");
		person.setHobby(hobbys);
		person.setBankCard(new BankCard("中国银行", "176672673687545097"));
		//可批量添加带Poniter类型的数据，例如
		person.setAuthor(BmobUser.getCurrentUser(MyUser.class));
		persons.add(person);
		batch.insertBatch(persons);

		//批量更新
		List<BmobObject> persons1=new ArrayList<BmobObject>();
		Person p1 = new Person();
		p1.setObjectId("3388eb6caf");
		p1.setAge(35);
		persons1.add(p1);
		batch.updateBatch(persons1);

		//批量删除
		List<BmobObject> persons2 = new ArrayList<BmobObject>();
		Person p2 = new Person();
		p2.setObjectId("9af452ebd");
		persons2.add(p2);
		batch.deleteBatch(persons2);
		//执行批量操作
		batch.doBatch(new QueryListListener<BatchResult>(){

			@Override
			public void done(List<BatchResult> results, BmobException ex) {
				if(ex==null){
					//返回结果的results和上面提交的顺序是一样的，请一一对应
//					{//批量添加
//				      "success": {
//				        "createdAt": "2016-06-07 10:27:45",
//				        "objectId": "a2b30a0b64"
//				      }
//				    },
//				    {//批量更新
//				      "success": {
//				        "updatedAt": "2016-06-07 10:27:45"
//				      }
//				    },
//				    {//批量删除
//				      "error": {
//				        "code": 101,
//				        "error": "object not found for 9af452eb4."
//				      }
//				    }
					for(int i=0;i<results.size();i++){
						BatchResult result= results.get(i);
						if(result.isSuccess()){//只有批量添加才返回objectId
							log("第"+i+"个成功："+result.getObjectId()+","+result.getUpdatedAt());
						}else{
							BmobException error= result.getError();
							log("第"+i+"个失败："+error.getErrorCode()+","+error.getMessage());
						}
					}
				}else{
					loge(ex);
				}
			}
		});
	}
}
