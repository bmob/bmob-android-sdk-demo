package cn.bmob.v3.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import androidx.core.content.FileProvider;


/**
 * Created on 18/5/11 14:11
 *
 * @author zhangchaozhou
 */
public class InstallUtil {

    private static String sAuthorities;


    public static void setAuthorities(String authorities) {
        sAuthorities = authorities;
    }

    private static boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    public static String getAppDir() {

        if (hasSDCard) {
            return mkdirs(Environment.getExternalStorageDirectory() + "/Download/bmob");
        } else {
            return mkdirs(Environment.getDownloadCacheDirectory() + "/Download/bmob");
        }
    }

    private static String mkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }



    /**
     *
     * @param context
     * @param file
     */
    public static Intent startInstall(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //provider authorities
            if (TextUtils.isEmpty(sAuthorities)){
                sAuthorities = context.getPackageName();
            }
            Uri apkUri = FileProvider.getUriForFile(context, sAuthorities, file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
        return intent;
    }





}
