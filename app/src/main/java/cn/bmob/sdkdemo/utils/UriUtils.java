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
            /**
             * Caused by: java.lang.IllegalArgumentException: Invalid URI: content://com.estrongs.files/storage/emulated/0/Android/data/com.google.android.googlequicksearchbox/files/pending_blobs/%E7%8E%AF%E5%A2%83%E5%BD%95%E9%9F%B3_2018%E5%B9%B409%E6%9C%8821%E6%97%A511%E6%97%B650%E5%88%8620%E7%A7%92.txt
             */

            /**
             * Caused by: java.lang.IllegalArgumentException: Invalid URI: content://com.estrongs.files/storage/emulated/0/cell.txt
             */
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
