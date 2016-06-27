package cn.bmob.sdkdemo.file;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.smile.filechoose.api.ChooserType;
import com.smile.filechoose.api.ChosenFile;
import com.smile.filechoose.api.FileChooserListener;
import com.smile.filechoose.api.FileChooserManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.sdkdemo.BaseActivity;
import cn.bmob.sdkdemo.R;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteBatchListener;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.ProgressCallback;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/** 文件上传+批量更新数据
 * @ClassName: BmobFileActivity
 * @author smile
 * @date 2014-5-22 下午7:58:58
 *
 * 文件上传包含以下四种形式：（单一文件上传或文件批量上传）
 * 1、插入单条数据（该数据只有一个BmobFile列）
 * 2、批量插入多条数据-且每条数据都存在一个BmobFile列
 * 3、插入单条数据（多个BmobFile列）
 * 4、批量插入多条数据-且每条数据都存在多个BmobFile列
 */
@SuppressLint("SdCardPath")
public class BmobFileActivity extends BaseActivity implements OnClickListener, FileChooserListener {

	Button tv_one_one;
	Button tv_one_many;
	Button tv_many_one;
	Button tv_many_many;
	Button btn_delete;
	Button btn_delete_batch;

	private FileChooserManager fm;
	
	TextView tv_path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
		initViews();
		initListeners();
	}

	public void initViews() {
		tv_one_one = (Button) findViewById(R.id.tv_one_one);
		tv_one_many = (Button) findViewById(R.id.tv_one_many);
		tv_many_one = (Button) findViewById(R.id.tv_many_one);
		tv_many_many = (Button) findViewById(R.id.tv_many_many);
		btn_delete = (Button) findViewById(R.id.btn_delete);
		btn_delete_batch = (Button) findViewById(R.id.btn_delete_batch);
		tv_path = (TextView)findViewById(R.id.tv_path);
	}

	public void initListeners() {
		tv_one_one.setOnClickListener(this);
		tv_one_many.setOnClickListener(this);
		tv_many_one.setOnClickListener(this);
		tv_many_many.setOnClickListener(this);
		btn_delete.setOnClickListener(this);
		btn_delete_batch.setOnClickListener(this);
	}
	
	 public void pickFile() {
        fm = new FileChooserManager(this);
        fm.setFileChooserListener(this);
        try {
            fm.choose();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	 
	 
	@Override
	public void onError(String arg0) {
		showToast(arg0);
	}

	ChosenFile choosedFile;
	
	@Override
	public void onFileChosen(final ChosenFile file) {
		choosedFile = file;
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	Log.i("life", choosedFile.getFilePath());
            	showFileDetails(file);
            	File mp3 = new File(choosedFile.getFilePath());
            	uploadMovoieFile(mp3);
            }
        });
	}
		
	private void showFileDetails(ChosenFile file) {
        StringBuffer text = new StringBuffer();
        text.append("File name: " + file.getFileName() + "\n");
        text.append("File path: " + file.getFilePath() + "\n");
        text.append("File size: " + file.getFileSize() );
        tv_path.setText(text.toString());
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChooserType.REQUEST_PICK_FILE && resultCode == RESULT_OK) {
            if (fm == null) {
                fm = new FileChooserManager(this);
                fm.setFileChooserListener(this);
            }
            Log.i(TAG, "Probable file size: " + fm.queryProbableFileSize(data.getData(), this));
            fm.submit(requestCode, data);
        }
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_one_one://插入单条数据（一个BmobFile列）
				insertDataWithOne();
				break;
			case R.id.tv_one_many://插入单条数据（多个BmobFile列）
				insertDataWithMany();
				break;
			case R.id.tv_many_one://批量插入多条数据-且每条数据都存在一个BmobFile列
				insertBatchDatasWithOne();
				break;
			case R.id.tv_many_many://批量插入多条数据-且每条数据都存在多个BmobFile列
				insertBatchDatasWithMany();
				break;
			case R.id.btn_delete://
				deleteFile();
				break;
			case R.id.btn_delete_batch://批量删除
				deleteBatchFile();
				break;
		}
	}

	private void deleteFile(){
		BmobFile file = new BmobFile();
		file.setUrl(url);
		file.delete(new UpdateListener() {
			@Override
			public void done(BmobException e) {
				if(e==null){
					showToast("文件删除成功");
				}else{
					showToast("文件删除失败："+e.getErrorCode()+",msg = "+e.getMessage());
				}
			}

		});
	}

	/**
	 * 批量删除文件
	 */
	private void deleteBatchFile(){
		if(TextUtils.isEmpty(url)){
			toast("url为空");
			return;
		}
		String[] urls =new String[]{url};
		BmobFile.deleteBatch(urls, new DeleteBatchListener() {

			@Override
			public void done(String[] failUrls, BmobException e) {
				if(e==null){
					toast("全部删除成功");
				}else{
					if(failUrls!=null){
						toast("删除失败个数："+failUrls.length+","+e.toString());
					}else{
						toast("全部文件删除失败："+e.getErrorCode()+","+e.toString());
					}
				}
			}
		});
	}

	//======================单个BmobFile列=======================================

	/** 插入单条数据（单个BmobFile列）
	 * 例如：插入单条电影
	 * @return void
	 * @throws
	 */
	private void insertDataWithOne(){
		if(choosedFile ==null){
			showToast("请先选择文件");
			pickFile();
			return;
		}
	}

	private static String url="";

	ProgressDialog dialog =null;

	/** 上传指定路径下的电影文件
	 * @param file
	 * @return void
	 */
	private void uploadMovoieFile(File file) {
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setTitle("上传中...");
		dialog.setIndeterminate(false);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
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
				url = bmobFile.getUrl();
				log("上传成功："+url+","+bmobFile.getFilename());
			}
		}).concatMap(new Func1<Void, Observable<String>>() {//将bmobFile保存到movie表中
			@Override
			public Observable<String> call(Void aVoid) {
				return saveObservable(new Movie("冰封：重生之门",bmobFile));
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
				choosedFile=null;
			}

			@Override
			public void onNext(String s) {
				dialog.dismiss();
				choosedFile=null;
				log("download的文件地址："+s);
			}
		});
	}

	//============================================
	/**
	 * 注：以下的测试文件路径仅供测试所用，程序若完整运行，请自行替换成sd卡内部文件路径
	 */
	List<BmobObject> movies = new ArrayList<BmobObject>();
	String filePath_mp3 = "/storage/emulated/0/bimagechooser/IMG_20160301_182149.jpg";
	String filePath_lrc = "/storage/emulated/0/bimagechooser/IMG_20160301_182149.jpg";

	/**
	 * 此方法适用于批量更新数据且每条数据只有一个BmobFile字段
	 * 例如：批量上传电影Movies
	 * @Title: insertBatchDatasWithOne
	 * @throws
	 */
	public void insertBatchDatasWithOne(){
		String[] filePaths = new String[2];
		filePaths[0] = filePath_mp3;
		filePaths[1] = filePath_lrc;
		//批量上传是会依次上传文件夹里面的文件
		BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

			@Override
			public void onSuccess(List<BmobFile> files, List<String> urls) {
				Log.i("life","insertBatchDatasWithOne -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
				if(urls.size()==1){//如果第一个文件上传完成
					Movie movie =new Movie("哈利波特1",files.get(0));
					movies.add(movie);
				}else if(urls.size()==2){//第二个文件上传成功
					Movie movie1 =new Movie("哈利波特2",files.get(1));
					movies.add(movie1);
					insertBatch(movies);
				}
			}

			@Override
			public void onError(int statuscode, String errormsg) {
				showToast("错误码："+statuscode +",错误描述："+errormsg);
			}

			@Override
			public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
				log("insertBatchDatasWithOne -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
			}
		});
	}

	/**
	 * save的Observable
	 * @param obj
	 * @return
     */
	private Observable<String> saveObservable(BmobObject obj){
		return obj.saveObservable();
	}

	/** 创建操作
	 * insertObject
	 * @return void
	 * @throws
	 */
	private void insertObject(final BmobObject obj){
		obj.save(new SaveListener<String>() {

			@Override
			public void done(String s, BmobException e) {
				if(e==null){
					showToast("-->创建数据成功：" + s);
				}else{
					showToast("-->创建数据失败：" + e.getErrorCode()+",msg = "+e.getMessage());
				}
			}
		});
	}

	//======================多个BmobFile列=======================================

	List<BmobObject> songs = new ArrayList<BmobObject>();

	/**
	 * 插入单条数据（多个BmobFile列--以两个为例）
	 * 例：上传MP3文件和歌词lrc文件到一条Song数据中
	 */
	private void insertDataWithMany() {
		String[] filePaths = new String[2];
		filePaths[0] = filePath_mp3;
		filePaths[1] = filePath_lrc;
		BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

			@Override
			public void onSuccess(List<BmobFile> files, List<String> urls) {
				log("insertDataWithMany -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
				if(urls.size()==2){//如果全部上传完，则更新该条记录
					Song song =new Song("汪峰0","北京北京0",files.get(0),files.get(1));
					insertObject(song);
				}else{
					//有可能上传不完整，中间可能会存在未上传成功的情况，你可以自行处理
				}
			}
			@Override
			public void onError(int statuscode, String errormsg) {
				showToast("错误码"+statuscode +",错误描述："+errormsg);
			}
			@Override
			public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
				log("insertDataWithMany -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
			}
		});
	}

	/**
	 * 此方法适用于批量更新数据且每条数据都有多个BmobFile字段：
	 * 例如：批量上传歌曲songs
	 * @Title: insertBatchDatasWithOne
	 * @throws
	 */
	private void insertBatchDatasWithMany() {
		File ff = new File("/mnt/sdcard/testbmob/");
		File[] fs = ff.listFiles();
		if(fs==null || fs.length==0){
			toast("请选择文件并上传");
			return;
		}
		String[] filePaths = new String[fs.length];
		if(fs!=null && fs.length>0){
			final int len = fs.length;
			for(int i=0;i<len;i++){
				filePaths[i] = fs[i].getAbsolutePath();
			}
			BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

				@Override
				public void onSuccess(List<BmobFile> files, List<String> urls) {
					log("insertBatchDatasWithMany -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
					if(urls.size()==len){//如果全部上传完，则批量更新
						//因为我的文件夹下面有四种图片每两个插入到一条数据中
						Song song =new Song("汪峰0","太阳的后裔",files.get(0),files.get(1));
						songs.add(song);
						Song song1 =new Song("汪峰1","北京北京1",files.get(2),files.get(3));
						songs.add(song1);
						//批量插入操作
						insertBatch(songs);
					}else{
						//有可能上传不完整，中间可能会存在未上传成功的情况，你可以自行处理
					}
				}

				@Override
				public void onError(int statuscode, String errormsg) {
					showToast("错误码"+statuscode +",错误描述："+errormsg);
				}

				@Override
				public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
					log("insertBatchDatasWithMany -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
				}
			});
		}
	}

	/** 批量插入操作
	 * insertBatch
	 * @return void
	 * @throws
	 */
	public void insertBatch(List<BmobObject> files){
		new BmobBatch().insertBatch(files).doBatch(new QueryListListener<BatchResult>() {
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

	/**新增文件下载方法
	 * @param file
	 */
	private void downloadFile(BmobFile file){
//		if(file==null){
//			file = new BmobFile("haha", "", "http://bmob-cdn-4.b0.upaiyun.com/png/11bf594aad96493e99a3c866eceb8184.png");
//		}
		File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
		file.download(saveFile, new DownloadFileListener() {

			@Override
			public void onStart() {
				toast("开始下载...");
			}

			@Override
			public void done(String s, BmobException e) {
				if(e==null){
					toast("下载成功,保存路径:"+s);
				}else{
					toast("下载失败："+e.getErrorCode()+","+e.getMessage());
				}
			}

			@Override
			public void onProgress(Integer value, long newworkSpeed) {
				log("下载进度："+value+","+newworkSpeed);
			}

		});
	}
}
