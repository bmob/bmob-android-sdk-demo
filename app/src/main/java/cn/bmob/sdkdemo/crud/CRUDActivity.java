package cn.bmob.sdkdemo.crud;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.bmob.sdkdemo.BaseActivity;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.BankCard;
import cn.bmob.sdkdemo.bean.Person;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 增删改查
 */
public class CRUDActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
						R.array.bmob_crud_list));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				testBmob(position + 1);
			}
		});

	}
	
	private void testBmob(int pos) {
		switch (pos) {
		case 1:
			testinsertObject();
			break;
		case 2:
			testUpdateObjet();
			break;
		case 3:
			testDeleteObject();
			break;
		case 4:
			startActivity(new Intent(this, QueryActivity.class));
			break;
		}
	}
	
	public static String objectId="";


	/**
	 * 插入对象
	 */
	private void testinsertObject() {
		final Person p2 = new Person();
		p2.setName("lucky");
		p2.setAddress("北京市海淀区");
		p2.setAge(25);
		//添加Object类型
		p2.setBankCard(new BankCard("哈哈", "111"));
		//添加Object类型的数组
		List<BankCard> cards =new ArrayList<BankCard>();
		for(int i=0;i<2;i++){
			cards.add(new BankCard("建行", "111"+i));
		}
		p2.addAll("cards", cards);
		//添加String类型的数组
		p2.addAll("hobby", Arrays.asList("游泳", "看书"));    // 一次添加多个值到hobby字段中
//		p2.add("cards",new BankCard("建行", "111"));//一次添加单个值
		p2.setGpsAdd(new BmobGeoPoint(112.934755, 24.52065));
		p2.setUploadTime(new BmobDate(new Date()));
		p2.save(new SaveListener<String>() {

			@Override
			public void done(String o, BmobException e) {
				if(e==null){
					objectId = p2.getObjectId();
					toast("创建数据成功：" + p2.getObjectId());
					Log.d("bmob", "objectId = " + p2.getObjectId());
					Log.d("bmob", "name =" + p2.getName());
					Log.d("bmob", "age =" + p2.getAge());
					Log.d("bmob", "address =" + p2.getAddress());
					Log.d("bmob", "gender =" + p2.isGender());
					Log.d("bmob", "createAt = " + p2.getCreatedAt());
				}else{
					loge(e);
				}
			}
		});
	}

	/**
	 * 更新对象
	 */
	private void testUpdateObjet() {
		final Person p2 = new Person();
		//更新数组中的某个位置的对象值
		p2.setValue("cards.0", new BankCard("cards.0", "cards.0的值"));
		//更新对象数组中指定对象的指定字段的值
//		p2.setValue("cards.0.bankName", "银行卡");
//		p2.setValue("cards.0.cardNumber", "卡号");
//		p2.setValue("cards.1.bankName", "银行卡");
		//更新BmobObject的值
//		p2.setValue("author", BmobUser.getCurrentUser(this, MyUser.class));
		//更新Object类型的数组
//		List<BankCard> cards =new ArrayList<BankCard>();
//		for(int i=0;i<2;i++){
//			cards.add(new BankCard("叫姐姐"+i, "111"+i));
//		}
//		p2.setValue("cards",cards);
		//更新Object对象
		p2.setValue("bankCard",new BankCard("bankCard", "bankCard的值"));
		//更新Object对象的值
//		p2.setValue("bankCard.bankName","你妹");
		//更新Integer类型
//		p2.setValue("age",11);
//		p2.setValue("gender", true);
		p2.update(objectId, new UpdateListener() {

			@Override
			public void done(BmobException e) {
				if(e==null){
					log("更新成功：" + p2.getUpdatedAt());
				}else{
					loge(e);
				}
			}
		});

	}

	/**
	 * 删除对象
	 */
	private void testDeleteObject() {
		Person p2 = new Person();
		p2.removeAll("cards", Arrays.asList(new BankCard("建行", "111")));
		p2.setObjectId(objectId);
		p2.update(new UpdateListener() {
			@Override
			public void done(BmobException e) {
				if(e==null){
					log("删除成功");
				}else{
					loge(e);
				}
			}
		});
	}
}
