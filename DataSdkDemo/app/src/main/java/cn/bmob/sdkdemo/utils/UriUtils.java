package cn.bmob.sdkdemo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

/**
 * Created on 2018/11/20 15:15
 *
 * @author zhangchaozhou
 */
public class UriUtils {



        public static String getRealPathFromURI(Context context, Uri contentURI) {
            String result;
            Cursor cursor = context.getContentResolver().query(contentURI, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (cursor == null) {
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(index);
                cursor.close();
            }
            return result;
        }

        public static String getRealPathFromUri(Context context, Uri uri) {
            int sdkVersion = Build.VERSION.SDK_INT;
            if (sdkVersion < 11) {
                return getRealPathFromUri_BelowApi11(context, uri);
            }
            if (sdkVersion < 19) {
                return getRealPathFromUri_Api11To18(context, uri);
            } else {
                return getRealPathFromUri_AboveApi19(context, uri);
            }
        }

        /**
         * 适配api19以上,根据uri获取图片的绝对路径
         */
        @TargetApi(Build.VERSION_CODES.KITKAT)
        private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
            String filePath = null;
            String wholeID = DocumentsContract.getDocumentId(uri);

            // 使用':'分割
            String id = wholeID.split(":")[1];

            String[] projection = {MediaStore.Images.Media.DATA};
            String selection = MediaStore.Images.Media._ID + "=?";
            String[] selectionArgs = {id};

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);
            int columnIndex = cursor.getColumnIndex(projection[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        }

        /**
         * 适配api11-api18,根据uri获取图片的绝对路径
         */
        private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
            String filePath = null;
            String[] projection = {MediaStore.Images.Media.DATA};
            //这个有两个包不知道是哪个。。。。不过这个复杂版一般用不到
            CursorLoader loader = new CursorLoader(context, uri, projection, null, null, null);
            Cursor cursor = loader.loadInBackground();

            if (cursor != null) {
                cursor.moveToFirst();
                filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
                cursor.close();
            }
            return filePath;
        }

        /**
         * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
         */
        private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
            String filePath = null;
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
                cursor.close();
            }
            return filePath;
        }



}
