# 全面解读Bmob_v3.5.0-支持Rx链式调用


最近不断有开发者询问我：

`Bmob什么时候支持RxJava？`、

`Bmob如何和RxJava一起使用？`

...

从上述问题中就可以看到Rx的火爆，相信很多开发者都已经在自己的项目中运用且享受着Rx带来的快乐啦。

而我们提供的方法均是异步回调方法，无法享受Rx带来的便捷。

就在昨天，我们发布了新的BmobSDK版本**[ v3.5.0 ](http://docs.bmob.cn/data/Android/j_updatalog/doc/index.html)**，这一版本最大的亮点在于：`支持Rx链式调用`。

因此，在这里给大家介绍下在BmobSDK中如何使用Rx？

## 如何导入SDK

具体可查看[SDK导入](http://docs.bmob.cn/data/Android/a_faststart/doc/index.html#SDK导入)这部分的文档。

## 如何使用

假如有一个 **Person** 实体对象，它继承者BmobObject，有`name`和`age`两个属性：

	public class Person extends BmobObject {
	
	      private String name;//姓名
	
	      private Integer age;//年龄
	
	      getter/setter...
	
	}

### 添加对象

SaveListener方式:v3.5.0将原来的`SaveListener`的返回参数中添加objectId

	Person person = new Person();
	person.setName("smile");
	person.save(new SaveListener<String>() {

		@Override
		public void done(String objectId, BmobException e) {
			if(e==null){
				Log.i("bmob","成功："+objectId);
			}else{
				Log.i("bmob", "失败: "+e.getMessage());
			}
		}
	});

Observable方式

	Person person = new Person();
	person.setName("smile");
	Observable<String> saveObservable = person.saveObservable();
	saveObservable.subscribe(new Observer<String>() {

		@Override
		public void onCompleted() {

		}

		@Override
		public void onError(Throwable e) {
			Log.i("bmob", "onError: "+e.getMessage());
		}

		@Override
		public void onNext(String objectId) {
			Log.i("bmob","保存成功："+objectId);
		}
	});

### 更新对象

UpdateListener方式

	Person person = new Person();
	person.setName("smile");
	person.update("6b6c11c537",new UpdateListener() {

		@Override
		public void done(BmobException e) {
			if(e==null){
				Log.i("bmob","成功");
			}else{
				Log.i("bmob", "失败: "+e.getMessage());
			}
		}
	});

Observable方式

	Person person = new Person();
	person.setName("smile");
	Observable<Void> observable = person.updateObservable("6b6c11c537");
	observable.subscribe(new Observer<Void>() {

		@Override
		public void onCompleted() {

		}

		@Override
		public void onError(Throwable e) {
			Log.i("bmob", "onError: "+e.getMessage());
		}

		@Override
		public void onNext(Void v) {
			Log.i("bmob","成功");
		}
	});

### 删除对象

UpdateListener方式：v3.5.0将原来的`DeleteListener`调整为`UpdateListener`

	Person person = new Person();
	person.setName("smile");
	person.delete("6b6c11c537",new UpdateListener() {

		@Override
		public void done(BmobException e) {
			if(e==null){
				Log.i("bmob","成功");
			}else{
				Log.i("bmob", "失败: "+e.getMessage());
			}
		}
	});

Observable方式

	Person person = new Person();
	Observable<Void> observable = person.deleteObservable("6b6c11c537");
	observable.subscribe(new Observer<Void>() {

		@Override
		public void onCompleted() {

		}

		@Override
		public void onError(Throwable e) {
			Log.i("bmob", "onError: "+e.getMessage());
		}

		@Override
		public void onNext(Void v) {
			Log.i("bmob","成功");
		}
	});

### 查询数据

#### 查询单条

QueryListener方式：v3.5.0将原来的`GetListener<T>`调整为`QueryListener<T>`

	BmobQuery<Person> query = new BmobQuery();
	query.getObject("5f1042e183", new QueryListener<Person>() {

			@Override
			public void done(Person p, BmobException e) {
				if(e==null){
					Log.i("bmob","成功:"+p.getName());
				}else{
					Log.i("bmob", "失败: "+e.getMessage());
				}
			}
		});

Observable方式

	BmobQuery<Person> query = new BmobQuery();
	Observable<Person> observable = query.getObjectObservable(Person.class,"5f1042e183");
	observable.subscribe(new Observer<Person>() {
		@Override
		public void onCompleted() {
			
		}

		@Override
		public void onError(Throwable e) {
			Log.i("bmob", "失败: "+e.getMessage());
		}

		@Override
		public void onNext(Person p) {
			Log.i("bmob","成功:"+p.getName());
		}
	});

#### 查询多条

FindListener方式

	BmobQuery<Person> bmobQuery	 = new BmobQuery<Person>();
	bmobQuery.addWhereEqualTo("age", 25);
	bmobQuery.order("createdAt");
	bmobQuery.findObjects(new FindListener<Person>() {

		@Override
		public void done(List<Person> object, BmobException e) {
			if(e==null){
				Log.i("bmob","成功:"+persons.size());
			}else{
				Log.i("bmob", "失败: "+e.getMessage());
			}
		}
	});

observable方式

	BmobQuery<Person> bmobQuery	 = new BmobQuery<Person>();
	bmobQuery.addWhereEqualTo("age", 25);
	bmobQuery.order("createdAt");
	Observable<List<Person>> observable = bmobQuery.findObjectsObservable(Person.class)
	observable.subscribe(new Subscriber<List<Person>>() {
				@Override
				public void onCompleted() {}

				@Override
				public void onError(Throwable e) {
					Log.i("bmob", "失败: "+e.getMessage());
				}

				@Override
				public void onNext(List<Person> persons) {
					Log.i("bmob","成功:"+persons.size());
				}
			});

**注：**

从上面的例子可以看出，`SDK每个方法均额外提供了对应的Observable方法，其命名规则：原有方法名+Observable。`

例如：

`save      	--->  saveObservable`

`findObjects ---> findObjectsObservable`

以此类推：

`signUp	   ---> signUpObservable(用户注册)`

`login      ---> loginObservable(用户登录)`

...


## 使用Rx我们还可以做什么？

使用`RxJava操作符` 可以链式调用BmobSDK提供的多个Observable方法来完成一系列操作：

案例：`我想上传一个文件，且文件上传成功后将该文件保存到Bmob的数据表（Movie）中，最后再将该文件下载下来`：

### v3.5.0版本之前

只能这样做：
	
	//执行上传操作
	final BmobFile bmobFile = new BmobFile(file);
	bmobFile.uploadblock(context, new UploadFileListener() {
				
		@Override
		public void onSuccess() {
			dialog.dismiss();
			Log.i("bmob", "文件上传成功:"+bmobFile.getUrl());
			//保存到Movie表中
			insertObject(bmobFile);
		}

		@Override
		public void onProgress(Integer arg0) {
			dialog.setProgress(arg0);
			Log.i("bmob", "onProgress:"+arg0);
		}

		@Override
		public void onFailure(int errorCode, String errorMsg) {
			dialog.dismiss();
			Log.i("bmob", "onFailure:"+errorCode+",:"+errorMsg);
		}
	});
	
保存到Movie表中：

	private void insertObject(BmobFile bmobFile){
		Movie movie = new Movie("冰封：重生之门",bmobFile)
		movie.save(context, new SaveListener() {
			
			@Override
			public void onSuccess() {
				Log.i("bmob", "保存成功:"+obj.getObjectId());
				//下载文件
				downloadFile(bmobFile);
			}
			
			@Override
			public void onFailure(int code, String msg) {
				Log.i("bmob", "onFailure:"+code+",:"+msg);
			}
		});
	}
	
下载该文件：
	 
	private void downloadFile(BmobFile file){
		File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
		file.download(this,saveFile, new DownloadFileListener() {
			
			@Override
			public void onStart() {
				Log.i("bmob", "开始下载...");
			}
			
			@Override
			public void onSuccess(String savePath) {
				Log.i("bmob", "下载成功,保存路径:"+savePath);
			}
			
			@Override
			public void onProgress(Integer value, long total) {
				Log.i("bmob","下载进度："+value+","+total);
			}
			
			@Override
			public void onFailure(int code, String msg) {
				Log.i("bmob", "下载失败:"+code+",:"+msg);
			}
		});
	}

上面就是所谓的回调地狱，如果回调Listener多的话，会让代码的可读性变得很差且不易维护。

### v3.5.0之后，使用RxJava:

	final BmobFile bmobFile = new BmobFile(file);
	bmobFile.uploadObservable(new ProgressCallback() {//上传文件操作
		@Override
		public void onProgress(Integer value, long total) {
			log("uploadMovoieFile-->onProgress:"+value);
			dialog.setProgress(value);
		}
	}).doOnNext(new Action1<Void>() {
		@Override
		public void call(Void aVoid) {
			Log.i("bmob", "文件上传成功:"+bmobFile.getUrl());
		}
	}).concatMap(new Func1<Void, Observable<String>>() {//将bmobFile保存到movie表中
		@Override
		public Observable<String> call(Void aVoid) {
			Movie movie =new Movie("冰封：重生之门",bmobFile);
			return movie.saveObservable();
		}
	}).concatMap(new Func1<String, Observable<String>>() {//下载文件
		@Override
		public Observable<String> call(String s) {
			return bmobFile.downloadObservable(new ProgressCallback() {
				@Override
				public void onProgress(Integer value, long total) {
					log("download-->onProgress:"+value+","+total);
				}
			});
		}
	}).subscribe(new Subscriber<String>() {
		@Override
		public void onCompleted() {
			log("--onCompleted--");
		}

		@Override
		public void onError(Throwable e) {
			log("--onError--:"+e.getMessage());
			dialog.dismiss();
		}

		@Override
		public void onNext(String s) {
			dialog.dismiss();
			log("download的文件地址："+s);
		}
	});

大家有木有觉得很简洁，这里使用了RxJava的一个操作符：`concatMap`。

注：关于RxJava的操作符的介绍，这里介绍一个网站给大家：

[ReactiveX/RxJava文档中文版](https://mcxiaoke.gitbooks.io/rxdocs/content/index.html)
