package cn.bmob.sdkdemo.activity.file;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.activity.user.UserActivity;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.sdkdemo.utils.UriUtils;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteBatchListener;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created on 2018/11/20 14:06
 * <p>
 * TODO 1、兼容android6.0运行时权限
 * TODO 2、兼容android7.0文件提供器
 *
 * @author zhangchaozhou
 */
public class FileManagerActivity extends AppCompatActivity {


    private static final int REQUEST_UPLOAD_SINGLE = 1001;
    private static final int REQUEST_UPLOAD_SINGLE_TO_TABLE = 1002;
    private static final int REQUEST_UPLOAD_MULTI = 1003;
    private static final int REQUEST_UPLOAD_MULTI_TO_TABLE = 1004;

    private static final int REQUEST_DOWNLOAD_SINGLE = 1005;

    private static final int REQUEST_DELETE_SINGLE = 1006;

    @BindView(R.id.btn_upload_single)
    AppCompatButton mBtnUploadSingle;
    @BindView(R.id.btn_upload_single_to_table)
    AppCompatButton mBtnUploadSingleToTable;
    @BindView(R.id.btn_upload_multi)
    AppCompatButton mBtnUploadMulti;
    @BindView(R.id.btn_upload_multi_to_table)
    AppCompatButton mBtnUploadMultiToTable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_upload_single, R.id.btn_upload_single_to_table, R.id.btn_upload_multi, R.id.btn_upload_multi_to_table, R.id.btn_delete_single})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_upload_single:
                checkStoragePermissions(REQUEST_UPLOAD_SINGLE);
                break;
            case R.id.btn_upload_single_to_table:
                checkStoragePermissions(REQUEST_UPLOAD_SINGLE_TO_TABLE);
                break;
            case R.id.btn_upload_multi:
                checkStoragePermissions(REQUEST_UPLOAD_MULTI);
                break;
            case R.id.btn_upload_multi_to_table:
                checkStoragePermissions(REQUEST_UPLOAD_MULTI_TO_TABLE);
                break;
            case R.id.btn_download_single:
                checkStoragePermissions(REQUEST_DOWNLOAD_SINGLE);
                break;
            case R.id.btn_delete_single:
                checkStoragePermissions(REQUEST_DELETE_SINGLE);
                break;
            default:
                break;
        }
    }


    /**
     * TODO 1、兼容android6.0运行时权限
     * 检查权限
     *
     * @param requestCode
     */
    public void checkStoragePermissions(int requestCode) {
        List<String> permissions = new ArrayList<>();
        int permissionCheckWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheckWrite != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        int permissionCheckRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissions.size() > 0) {
            String[] missions = new String[]{};
            ActivityCompat.requestPermissions(this, permissions.toArray(missions), requestCode);
        } else {
            switch (requestCode) {
                case REQUEST_UPLOAD_SINGLE:
                    chooseFile(requestCode);
                    break;
                case REQUEST_UPLOAD_SINGLE_TO_TABLE:
                    chooseFile(requestCode);
                    break;
                case REQUEST_UPLOAD_MULTI:
                    break;
                case REQUEST_UPLOAD_MULTI_TO_TABLE:
                    break;
                case REQUEST_DOWNLOAD_SINGLE:
                    downloadUserAvatar();
                    break;
                case REQUEST_DELETE_SINGLE:
                    deleteUserAvatar();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 删除用户头像
     */
    private void deleteUserAvatar() {
        if (BmobUser.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            if (user.getAvatar() != null) {
                deleteFile(user.getAvatar());
            }
        }
    }

    /**
     * 下载用户头像
     */
    private void downloadUserAvatar() {
        if (BmobUser.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            if (user.getAvatar() != null) {
                downloadFile(user.getAvatar());
            }
        }
    }

    /**
     * TODO 1、兼容android6.0运行时权限
     * 检查授权结果
     *
     * @param grantResults
     * @return
     */
    public boolean checkResults(int[] grantResults) {
        if (grantResults == null || grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * TODO 1、兼容android6.0运行时权限
     * <p>
     * 权限授权结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_UPLOAD_SINGLE:
                if (checkResults(grantResults)) {
                    chooseFile(requestCode);
                }
                break;
            case REQUEST_UPLOAD_SINGLE_TO_TABLE:
                if (checkResults(grantResults)) {
                    chooseFile(requestCode);
                }
                break;
            case REQUEST_UPLOAD_MULTI:
                if (checkResults(grantResults)) {
                }
                break;
            case REQUEST_UPLOAD_MULTI_TO_TABLE:
                if (checkResults(grantResults)) {
                }
                break;
            default:
                break;
        }
    }


    /**
     * 选择文件
     *
     * @param requestCode
     */
    private void chooseFile(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "选择文件"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_UPLOAD_SINGLE:
                if (resultCode == RESULT_OK) {
                    String path =UriUtils.getRealPathFromUri(this, data.getData());
                    if (!TextUtils.isEmpty(path)){
                        Log.e("BMOB", "url:"+path);
                        uploadSingleFile(new File(path));
                    }
                }
                break;
            case REQUEST_UPLOAD_SINGLE_TO_TABLE:
                if (resultCode == RESULT_OK) {
                    String path =UriUtils.getRealPathFromUri(this, data.getData());
                    if (!TextUtils.isEmpty(path)){
                        Log.e("BMOB", "url:"+path);
                        uploadSingleFile(new File(path));
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 上传单一文件
     *
     * @param file
     */
    private void uploadSingleFile(File file) {
        BmobFile bmobFile = new BmobFile(file);
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnUploadSingle, "上传成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.getErrorCode()+"-"+e.getMessage());
                    Snackbar.make(mBtnUploadSingle, "上传失败：" +e.getErrorCode()+"-"+ e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 上传单一文件后设置到表中，此处是上传头像
     *
     * @param file
     */
    private void uploadSingleFileToTable(File file) {
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnUploadSingleToTable, "上传成功", Snackbar.LENGTH_LONG).show();
                    setFileToTable(bmobFile);
                } else {
                    Snackbar.make(mBtnUploadSingleToTable, "上传失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 设置文件到表中，此处是设置头像
     *
     * @param bmobFile
     */
    private void setFileToTable(BmobFile bmobFile) {
        if (!BmobUser.isLogin()) {
            Snackbar.make(mBtnUploadSingleToTable, "请先登录", Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(this, UserActivity.class));
            return;
        }
        User user = BmobUser.getCurrentUser(User.class);
        user.setAvatar(bmobFile);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnUploadSingleToTable, "设置文件到表中成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mBtnUploadSingleToTable, "设置文件到表中失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 下载文件
     *
     * @param bmobFile
     */
    private void downloadFile(BmobFile bmobFile) {
        File saveFile = new File(Environment.getExternalStorageDirectory(), bmobFile.getFilename());
        bmobFile.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                Snackbar.make(mBtnUploadSingle, "开始下载", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnUploadSingle, "下载成功,保存路径:" + s, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mBtnUploadSingle, "下载失败：" + e.getErrorCode() + "," + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onProgress(Integer value, long networkSpeed) {
                Snackbar.make(mBtnUploadSingle, "下载进度：" + value + "," + networkSpeed, Snackbar.LENGTH_LONG).show();
            }

        });
    }


    /**
     * 删除文件
     *
     * @param bmobFile
     */
    private void deleteFile(BmobFile bmobFile) {
        bmobFile.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnUploadSingle, "删除成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mBtnUploadSingle, "删除失败：" + e.getErrorCode() + "," + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 批量删除文件
     *
     * @param urls
     */
    private void deleteBatchFile(String[] urls) {
        BmobFile.deleteBatch(urls, new DeleteBatchListener() {

            @Override
            public void done(String[] failUrls, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnUploadSingle, "删除成功", Snackbar.LENGTH_LONG).show();
                } else {
                    if (failUrls != null) {
                        Snackbar.make(mBtnUploadSingle, "删除失败个数：" + failUrls.length + "," + e.toString(), Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(mBtnUploadSingle, "全部文件删除失败：" + e.getErrorCode() + "," + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }







    List<BmobObject> movies = new ArrayList<>();

    /**
     * 此方法适用于批量更新数据且每条数据只有一个BmobFile字段
     */
    public void insertBatchDatasWithOne(final String[] filePaths) {
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {

                //有可能上传不完整，中间可能会存在未上传成功的情况，你可以自行处理
                if (urls.size()==filePaths.length){
                    Log.e("success","上传结束");
                }

                insertBatch(movies);
            }

            @Override
            public void onError(int statusCode, String errorMsg) {
                Snackbar.make(mBtnUploadSingle,"错误码：" + statusCode + ",错误描述：" + errorMsg, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                Snackbar.make(mBtnUploadSingle,"insertBatchDatasWithOne -onProgress :" + curIndex + "---" + curPercent + "---" + total + "----" + totalPercent, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 创建操作
     * insertObject
     *
     * @return void
     * @throws
     */
    private void insertObject(final BmobObject obj) {
        obj.save(new SaveListener<String>() {

            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnUploadSingle, "新增数据成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mBtnUploadSingle, "新增数据失败：" + e.getErrorCode() + "," + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    /**
     * 批量插入操作
     * insertBatch
     *
     * @return void
     * @throws
     */
    public void insertBatch(List<BmobObject> files) {
        new BmobBatch().insertBatch(files).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < o.size(); i++) {
                        BatchResult result = o.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Snackbar.make(mBtnUploadSingle, "新增数据成功", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(mBtnUploadSingle, "新增数据失败：" + ex.getErrorCode() + "," + ex.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Snackbar.make(mBtnUploadSingle, "新增数据失败：" + e.getErrorCode() + "," + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
