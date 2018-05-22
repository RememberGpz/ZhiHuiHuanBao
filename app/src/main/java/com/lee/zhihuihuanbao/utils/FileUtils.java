package com.lee.zhihuihuanbao.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ronny on 2017/1/12.
 * 文件处理工具
 */

public class FileUtils {

    public static final int REQUEST_CODE_SELECT_PHOTO = 0;//进入图册的请求吗
    public static final int REQUEST_CODE_IMGTAILOR = 1;//图片裁剪请求码
    public static final int REQUEST_CODE_CAMERA = 2;//使用照相机的请求码

    public static final String TAILOR_PATH = android.os.Environment.getExternalStorageDirectory() + "/peixun/tailor/";


    public static void intoPhotoList(Context context) {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);
    }

    /**
     * 进入照相机,并将文件绝对路径返回
     *
     * @param context
     * @param savePath 保存图片的路径
     *                 这里默认文件名用当前时间
     */
    public static String intoCamera(Context context, String savePath) {
        //先检查内存卡是否存在
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            ToastUtil.showToast("内存卡不存在，请插入存储卡！");
            return null;
        }
        File cameraFile = new File(savePath, System.currentTimeMillis() + ".jpg");
        cameraFile.getParentFile().mkdirs();
        ((Activity) context).startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                REQUEST_CODE_CAMERA);

        return cameraFile.getAbsolutePath();
    }

    /**
     * 获取相册图片路径
     *
     * @param context
     * @param imgUri
     * @return
     */
    public static String getImagePath(Context context, Uri imgUri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(imgUri, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                ToastUtil.showToast("没有找到图片");
                return null;
            }
            return picturePath;
        } else {
            File file = new File(imgUri.getPath());
            if (!file.exists()) {
                ToastUtil.showToast("没有找到图片");
                return null;
            }
            return file.getAbsolutePath();
        }
    }

    /**
     * 图片裁剪
     *
     * @param context
     * @param imgUri
     * @param filePath
     * @param requestCode
     */
    public static File imgTailor(Activity context, Uri imgUri, String filePath, int requestCode) {

        File file = new File(TAILOR_PATH, System.currentTimeMillis() + ".png");
        file.getParentFile().mkdirs();
        Intent intentTailor = new Intent(); // 跳到裁剪图片界面的intent
        intentTailor.setAction("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intentTailor.setDataAndType(Uri.fromFile(new File(filePath)), "image/*");
        } else {
            intentTailor.setDataAndType(imgUri, "image/*");// Uri是已经选择的图片Uri
        }
        intentTailor.putExtra("output", Uri.fromFile(file));
        intentTailor.putExtra("crop", "true");
        intentTailor.putExtra("aspectX", 1);// 裁剪框比例
        intentTailor.putExtra("aspectY", 1);
        intentTailor.putExtra("outputX", 325);// 输出图片大小
        intentTailor.putExtra("outputY", 325);
        intentTailor.putExtra("return-data", false);
        context.startActivityForResult(intentTailor, requestCode);
        return file;
    }

    /**
     * 图片base64处理
     *
     * @param filePath
     * @return
     */
    public static String imageToBase64(String filePath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(filePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(data, Base64.DEFAULT);// 返回Base64编码过的字节数组字符串
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void zipImgFromPath(String imgPath){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

    }

}
