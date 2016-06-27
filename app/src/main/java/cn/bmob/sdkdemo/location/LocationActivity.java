package cn.bmob.sdkdemo.location;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import cn.bmob.sdkdemo.BaseActivity;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Person;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 *   地理位置
 * @class  LocationActivity
 * @author smile
 * @date   2015-4-13 上午11:31:47
 */
public class LocationActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
						R.array.bmob_location_list));
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
			queryNear();
			break;
		case 2:
			queryKiloMeters();
			break;
		case 3:
			queryBox();
			break;
		}
	}

	/**查询最接近某个坐标的用户
	 * @method queryNear
	 * @params
	 * @return void
	 * @exception
	 */
	private void queryNear(){
		BmobQuery<Person> query =new BmobQuery<Person>();
		BmobGeoPoint location = new BmobGeoPoint(112.934755,24.52065);
		query.addWhereNear("gpsAdd", location);
		query.findObjects(new FindListener<Person>() {

			@Override
			public void done(List<Person> object, BmobException e) {
				if(e==null){
					toast("查询成功：共" + object.size() + "条数据。");
				}else{
					loge(e);
				}
			}


		});
	}

	/** 查询指定距离范围内的用户
	 * @method queryKiloMeters
	 * @params
	 * @return void
	 * @exception
	 */
	private void queryKiloMeters(){
		BmobQuery<Person> query =new BmobQuery<Person>();
		BmobGeoPoint southwestOfSF = new BmobGeoPoint(112.934755,24.52065);
		//查询指定坐标指定半径内的用户
		query.addWhereWithinRadians("gpsAdd", southwestOfSF, 10.0);
		//查询指定坐标指定公里范围内的用户
//		query.addWhereWithinKilometers("gpsAdd", southwestOfSF, 10);
		//查询指定坐标指定英里范围内的用户
		query.addWhereWithinMiles("gpsAdd", southwestOfSF, 10.0);
		query.findObjects( new FindListener<Person>() {

			@Override
			public void done(List<Person> object, BmobException e) {
				if(e==null){
					toast("查询成功：共" + object.size() + "条数据。");
				}else{
					loge(e);
				}
			}


		});
	}

	/**  查询矩形范围内的用户
	 * @method queryBox
	 * @params
	 * @return void
	 * @exception
	 */
	private void queryBox(){
		BmobQuery<Person> query =new BmobQuery<Person>();
		BmobGeoPoint southwestOfSF = new BmobGeoPoint(112.934755,24.52065);
		BmobGeoPoint northeastOfSF = new BmobGeoPoint(116.627623, 40.143687);
		query.addWhereWithinGeoBox("gpsAdd", southwestOfSF, northeastOfSF);
		query.findObjects(new FindListener<Person>() {

			@Override
			public void done(List<Person> object, BmobException e) {
				if(e==null){
					toast("查询成功：共" + object.size() + "条数据。");
				}else{
					loge(e);
				}
			}
		});
	}
}
