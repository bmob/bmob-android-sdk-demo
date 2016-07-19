package cn.bmob.sdkdemo.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.sdkdemo.BaseActivity;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.BankCard;
import cn.bmob.sdkdemo.bean.MyUser;
import cn.bmob.sdkdemo.bean.Person;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Subscriber;

public class UserActivity extends BaseActivity {
	
	EditText et_number,et_code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code);
		et_number = (EditText) findViewById(R.id.et_number);
		et_code = (EditText) findViewById(R.id.et_code);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(R.array.bmob_user_list));
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
			testSignUp();
			break;
		case 2:
			testLogin();
			break;
		case 3:
			testGetCurrentUser();
			break;
		case 4:
			testLogOut();
			break;
		case 5:
			updateUser();
			break;
		case 6:
			checkPassword();
			break;
		case 7:
			testResetPasswrod();
			break;
		case 8:
			emailVerify();
			break;
		case 9:
			testFindBmobUser();
			break;
		case 10:
			loginByEmailPwd();
			break;
		case 11:
			loginByPhonePwd();
			break;
		case 12:
			loginByPhoneCode();
			break;
		case 13:
			signOrLogin();
			break;
		case 14:
			resetPasswordBySMS();
			break;
		case 15:
			updateCurrentUserPwd();
			break;
		}
	}
	
	@SuppressLint("UseValueOf")
	private void testSignUp() {
		final MyUser myUser = new MyUser();
		myUser.setUsername("smile");
		myUser.setPassword("123456");
		myUser.setAge(18);
		addSubscription(myUser.signUp(new SaveListener<MyUser>() {
			@Override
			public void done(MyUser s, BmobException e) {
				if(e==null){
					toast("注册成功:" +s.toString());
				}else{
					loge(e);
				}
			}
		}));
	}

	private void testLogin() {
		final BmobUser bu2 = new BmobUser();
		bu2.setUsername("smile");
		bu2.setPassword("123456");
		//login回调
//		bu2.login(new SaveListener<BmobUser>() {
//
//			@Override
//			public void done(BmobUser bmobUser, BmobException e) {
//				if(e==null){
//					toast(bu2.getUsername() + "登陆成功");
//					testGetCurrentUser();
//				}else{
//					loge(e);
//				}
//			}
//		});
		//新增加的Observable
		bu2.loginObservable(BmobUser.class).subscribe(new Subscriber<BmobUser>() {
			@Override
			public void onCompleted() {
				log("----onCompleted----");
			}

			@Override
			public void onError(Throwable e) {
				loge(new BmobException(e));
			}

			@Override
			public void onNext(BmobUser bmobUser) {
				toast(bmobUser.getUsername() + "登陆成功");
				testGetCurrentUser();
			}
		});
	}

	/**
	 * 获取本地用户
	 */
	private void testGetCurrentUser() {
//		MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
//		if (myUser != null) {
//			log("本地用户信息:objectId = " + myUser.getObjectId() + ",name = " + myUser.getUsername()
//					+ ",age = "+ myUser.getAge());
//		} else {
//			toast("本地用户为null,请登录。");
//		}
		//V3.4.5版本新增加getObjectByKey方法获取本地用户对象中某一列的值
		String username = (String) BmobUser.getObjectByKey("username");
		Integer age = (Integer) BmobUser.getObjectByKey("age");
		Boolean sex = (Boolean) BmobUser.getObjectByKey("sex");
		JSONArray hobby= (JSONArray) BmobUser.getObjectByKey("hobby");
		JSONArray cards= (JSONArray) BmobUser.getObjectByKey("cards");
		JSONObject banker= (JSONObject) BmobUser.getObjectByKey("banker");
		JSONObject mainCard= (JSONObject) BmobUser.getObjectByKey("mainCard");
		log("username："+username+",\nage："+age+",\nsex："+ sex);
		log("hobby:"+(hobby!=null?hobby.toString():"为null")+"\ncards:"+(cards!=null ?cards.toString():"为null"));
		log("banker:"+(banker!=null?banker.toString():"为null")+"\nmainCard:"+(mainCard!=null ?mainCard.toString():"为null"));
	}

	/**
	 * 清除本地用户
	 */
	private void testLogOut() {
		BmobUser.logOut();
	}

	/**
	 * 更新用户操作并同步更新本地的用户信息
	 */
	private void updateUser() {
		final MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
		if (bmobUser != null) {
			final MyUser newUser = new MyUser();
			//-----------------------普通setter操作-------------------------------
			//number类型
			newUser.setAge(25);
			newUser.setSex(false);
			//object类型
//			newUser.setMainCard(new BankCard("工行", "10086"));
			//BmobObject类型
			Person person = new Person();
			person.setObjectId("721fe0cdf2");
			newUser.setBanker(person);
			//---------------------数组操作(add、addAll、addUnique、addAllUnique)---------------------------------------
			//添加Object类型的数组,Object数组调用addAllUnique、addUnique方法后本地用户信息未支持去重
			List<BankCard> cards =new ArrayList<BankCard>();
			cards.add(new BankCard("建行", "111"));
			newUser.addAll("cards", cards);
//			添加String类型的数组--String数组支持去重
			newUser.addAllUnique("hobby", Arrays.asList("游泳"));
			//----------------------自增操作---------------------------------------
//			newUser.increment("num",-2);
//			//----------------------setValue方式更新用户信息（必须先保证更新的列存在，否则会报internal error）----------------------------
//			//更新number
//			newUser.setValue("age",25);
//			//更新整个Object
//			newUser.setValue("banker",person);
//			//更新String数组
//			newUser.setValue("hobby",Arrays.asList("看书","游泳"));
////			//更新某个Object的值
//			newUser.setValue("mainCard.cardNumber","10011");
//			//更新数组中某个Object
//			newUser.setValue("cards.0", new BankCard("工行", "10086"));
			//更新数组中某个Object的某个字段的值
//			newUser.setValue("cards.0.bankName", "中行");
			addSubscription(newUser.update(bmobUser.getObjectId(),new UpdateListener() {

				@Override
				public void done(BmobException e) {
					if(e==null){
						testGetCurrentUser();
					}else{
						loge(e);
					}
				}
			}));
		} else {
			toast("本地用户为null,请登录。");
		}
	}

	/**
	 * 验证旧密码是否正确
	 * @param
	 * @return void
	 */
	private void checkPassword() {
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		final MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
		// 如果你传的密码是正确的，那么arg0.size()的大小是1，这个就代表你输入的旧密码是正确的，否则是失败的
		query.addWhereEqualTo("password", "123456");
		query.addWhereEqualTo("username", bmobUser.getUsername());
		addSubscription(query.findObjects(new FindListener<MyUser>() {

			@Override
			public void done(List<MyUser> object, BmobException e) {
				if(e==null){
					toast("查询密码成功:" + object.size());
				}else{
					loge(e);
				}
			}

		}));
	}

	/**
	 * 重置密码
	 */
	private void testResetPasswrod() {
		final String email = "123456789@qq.com";
		addSubscription(BmobUser.resetPasswordByEmail(email, new UpdateListener() {

			@Override
			public void done(BmobException e) {
				if(e==null){
					toast("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
				}else{
					loge(e);
				}
			}
		}));
	}

	/**
	 * 查询用户
	 */
	private void testFindBmobUser() {
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.addWhereEqualTo("username", "lucky");
		addSubscription(query.findObjects(new FindListener<MyUser>() {

			@Override
			public void done(List<MyUser> object, BmobException e) {
				if(e==null){
					toast("查询用户成功：" + object.size());
				}else{
					loge(e);
				}
			}

		}));
	}

	/**
	 * 验证邮件
	 */
	private void emailVerify() {
		final String email = "75727433@qq.com";
		addSubscription(BmobUser.requestEmailVerify(email, new UpdateListener() {

			@Override
			public void done(BmobException e) {
				if(e==null){
					toast("请求验证邮件成功，请到" + email + "邮箱中进行激活账户。");
				}else{
					loge(e);
				}
			}

		}));
	}

	private void loginByEmailPwd(){
		addSubscription(BmobUser.loginByAccount("123456@163.com", "123456", new LogInListener<MyUser>() {

			@Override
			public void done(MyUser user, BmobException e) {
				if(user!=null){
					log(user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
				}
			}
		}));
	}

	private void loginByPhonePwd(){
		String number = et_number.getText().toString();
		addSubscription(BmobUser.loginByAccount(number, "123456", new LogInListener<MyUser>() {

			@Override
			public void done(MyUser user, BmobException e) {
				if(user!=null){
					toast("登录成功");
					log(user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
				}else{
					toast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
				}
			}
		}));
	}

	private void loginByPhoneCode(){
		//1、调用请求验证码接口
//		BmobSMS.requestSMSCode("手机号码", "模板名称",new QueryListener<Integer>() {
//
//			@Override
//			public void done(Integer smsId,BmobException ex) {
//				if(ex==null){//验证码发送成功
//					log("短信id："+smsId);
//				}
//			}
//		});
		String number = et_number.getText().toString();
		String code = et_code.getText().toString();
		//2、使用验证码进行登陆
		addSubscription(BmobUser.loginBySMSCode(number, code, new LogInListener<MyUser>() {

			@Override
			public void done(MyUser user, BmobException e) {
				if(user!=null){
					toast("登录成功");
					log(""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
				}else{
					toast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
				}
			}
		}));
	}
	/** 一键注册登录
	 * @method signOrLogin
	 * @return void
	 * @exception
	 */
	private void signOrLogin(){
		//1、调用请求验证码接口
//		BmobSMS.requestSMSCode("18312662735", "模板名称",new QueryListener<Integer>() {
//
//			@Override
//			public void done(Integer smsId,BmobException ex) {
//				if(ex==null){//验证码发送成功
//					log("smile", "短信id："+smsId);
//				}
//			}
//
//		});
		String number = et_number.getText().toString();
		String code = et_code.getText().toString();
		//2、使用手机号和短信验证码进行一键注册登录,这步有两种方式可以选择
//		//第一种：
//		BmobUser.signOrLoginByMobilePhone(number, code,new LogInListener<MyUser>() {
//
//			@Override
//			public void done(MyUser user, BmobException e) {
//				if(user!=null){
//					toast("登录成功");
//					log(""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
//				}else{
//					toast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
//				}
//			}
//		});
		//第二种：这种方式比较灵活，可以在注册或登录的同时设置保存多个字段值
		final MyUser user = new MyUser();
		user.setPassword("123456");
		user.setMobilePhoneNumber(number);
		addSubscription(user.signOrLogin(code, new SaveListener<MyUser>() {

			@Override
			public void done(MyUser myUser, BmobException e) {
				if(e==null){
					toast("登录成功");
					log(""+myUser.getAge()+"-"+myUser.getObjectId()+"-"+myUser.getEmail());
				}else{
					loge(e);
				}
			}

		}));
	}

	/** 通过短信验证码来重置用户密码
	 * @method requestSmsCode
	 * @return void
	 * 注：整体流程是先调用请求验证码的接口获取短信验证码，随后调用短信验证码重置密码接口来重置该手机号对应的用户的密码
	 */
	private void resetPasswordBySMS(){
		//1、请求短信验证码
//		BmobSMS.requestSMSCode("手机号码", "模板名称",new QueryListener<Integer>() {
//
//			@Override
//			public void done(Integer smsId,BmobException ex) {
//				if(ex==null){//验证码发送成功
//					log("短信id："+smsId);
//				}
//			}
//		});
		String code = et_code.getText().toString();
		//2、重置的是绑定了该手机号的账户的密码
		addSubscription(BmobUser.resetPasswordBySMSCode(code,"1234567", new UpdateListener() {

			@Override
			public void done(BmobException e) {
				if(e==null){
					toast("密码重置成功");
				}else{
					toast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
				}
			}
		}));
	}

	/**修改当前用户密码
	 * @return void
	 * @exception
	 */
	private void updateCurrentUserPwd(){
		addSubscription(BmobUser.updateCurrentUserPassword("旧密码", "新密码", new UpdateListener() {

			@Override
			public void done(BmobException e) {
				if(e==null){
					toast("密码修改成功，可以用新密码进行登录");
				}else{
					loge(e);
				}
			}
		}));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
