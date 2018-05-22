package com.lee.zhihuihuanbao.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lzy.imagepicker.util.ProviderUtil;
import com.lzy.imagepicker.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.lzy.imagepicker.ImagePicker.createFile;

/**
 * Created by Mander on 2016/10/24.
 */

public class ImageUtil {

    // 获取图片的RequestCode
    public static final int GET_PHOTO = 0x100;
    // 裁剪图片的RequestCode
    public static final int TAILOR = 0x200;
    public static final int REQUEST_CODE_SELECT_PHOTO = 3;//进入图册的请求吗
    public static final int TAKE_PICTURE = 4;//进入拍照的请求吗


    /**
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return "";
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);


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

    public static String imgFileToBase64(String filePath) {
        InputStream in = null;
        byte[] data = null;
       // 读取图片字节数组
        try {
            in = new FileInputStream(filePath);
            data = new byte[in.available()];
            in.read(data);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(in!=null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      // 对字节数组Base64编码
        return Base64.encodeToString(data, Base64.DEFAULT);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 从图库里面拿图片
     */
    public static void getPhotoFromPhotoGallery(Activity context) { // 获取图库图片的方法
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */

        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        context.startActivityForResult(intent, GET_PHOTO);
    }

    /**
     * 从图库里面拿图片
     */
    public static void getPhotoFromPhotoGallery(Activity context, int requestCode) { // 获取图库图片的方法
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 裁剪从相册中获取到的图片
     */
    public static void tailor(Activity context, Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            Uri uri = intent.getData();
            String filePath = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String wholeID = DocumentsContract.getDocumentId(uri);
                String id = wholeID.split(":")[1];
                String[] column = {MediaStore.Images.Media.DATA};
                String sel = MediaStore.Images.Media._ID + "=?";
                Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                        sel, new String[]{id}, null);
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            } else {
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                filePath = cursor.getString(column_index);
                cursor.close();
            }
            /**
             * 跳转到裁剪界面
             */
            Intent intentTailor = new Intent(); // 跳到裁剪图片界面的intent
            intentTailor.setAction("com.android.camera.action.CROP");
            intentTailor.setDataAndType(Uri.fromFile(new File(filePath)), "image/*");// Uri是已经选择的图片Uri
            intentTailor.putExtra("crop", "true");
            intentTailor.putExtra("aspectX", 1);// 裁剪框比例
            intentTailor.putExtra("aspectY", 1);
            intentTailor.putExtra("outputX", 150);// 输出图片大小
            intentTailor.putExtra("outputY", 150);
            intentTailor.putExtra("return-data", true);
            context.startActivityForResult(intentTailor, TAILOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 裁剪从相册中获取到的图片
     */
    public static void tailor(Activity context, Intent intent, int requestCode) {
        if (intent == null) {
            return;
        }
        Uri uri = intent.getData();
        /**
         * 跳转到裁剪界面
         */
        Intent intentTailor = new Intent(); // 跳到裁剪图片界面的intent
        intentTailor.setAction("com.android.camera.action.CROP");
        intentTailor.setDataAndType(uri, "image/*");// Uri是已经选择的图片Uri
        intentTailor.putExtra("crop", "true");
        intentTailor.putExtra("aspectX", 1);// 裁剪框比例
        intentTailor.putExtra("aspectY", 1);
        intentTailor.putExtra("outputX", 150);// 输出图片大小
        intentTailor.putExtra("outputY", 150);
        intentTailor.putExtra("return-data", true);
        context.startActivityForResult(intentTailor, requestCode);
    }

    public static void glideDisplay(Context context, String path, int defaultImage, final ImageView imageView) {
        if (null != path) {
            path = path.replace("\\", "/");
        }
//        DrawableRequestBuilder<String> request = null;

        imageView.setImageResource(defaultImage);
        if (defaultImage > 0) {
            Glide.with(context)
                    .load(path)
                    .placeholder(defaultImage)
                    .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
            });
        }else{
             Glide.with(context)
                    .load(path)
                    .into(new SimpleTarget<GlideDrawable>() {
                              @Override
                              public void onResourceReady(GlideDrawable resource,
                                                          GlideAnimation<? super GlideDrawable> glideAnimation) {
                                  imageView.setImageDrawable(resource);
                              }
                    });
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 通过服务器返回来的图片路径获取完整路径(服务器上的路径只有一半)
     */
    public static String getAbsolutePath(String url) {
        return url;
    }

    /*public static String getAbsolutePathLive(String url) {
        return AppURL.LiveImageURL + url;
    }*/


    ///////////////////////////////////////

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());

    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent
     * @return
     */
    public Uri geturi(Context content, Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = content.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                cur.close();
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    /**
     * 获取相册图片
     *
     * @param context
     * @param intent  系统返回的Intent
     * @return 图片的真实路径
     */
    public static String getImgPath(Context context, Intent intent) {
        Uri imgUri = intent.getData();
        String filePath = null;
        String scheme = imgUri.getScheme();//获取路径的组合方式：file 、content
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {//    dat = file://类型
            filePath = imgUri.getPath();
            return filePath;
        } else {//  dat = content:///类型，该类型也分两种

//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
////                filePath = ImageUtil.getImageAbsolutePath(this, imageUri);
//
//                String wholeID = DocumentsContract.getDocumentId(imgUri);
//                String id = wholeID.split(":")[1];
//                String[] column = {MediaStore.Images.Media.DATA};
//                String sel = MediaStore.Images.Media._ID + "=?";
//                Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
//                        sel, new String[]{id}, null);
//                int columnIndex = cursor.getColumnIndex(column[0]);
//                if (cursor.moveToFirst()) {
//                    filePath = cursor.getString(columnIndex);
//                }
//                cursor.close();
//            } else {
//                String[] projection = {MediaStore.Images.Media.DATA};
//                Cursor cursor = context.getContentResolver().query(imgUri, projection, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                filePath = cursor.getString(column_index);
//            }
//            return filePath;


            if (context == null || imgUri == null)
                return null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imgUri)) {
                if (isExternalStorageDocument(imgUri)) {
                    String docId = DocumentsContract.getDocumentId(imgUri);
                    String[] split = docId.split(":");
                    String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(imgUri)) {
                    String id = DocumentsContract.getDocumentId(imgUri);
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(imgUri)) {
                    String docId = DocumentsContract.getDocumentId(imgUri);
                    String[] split = docId.split(":");
                    String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    String selection = MediaStore.Images.Media._ID + "=?";
                    String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            } // MediaStore (and general)
            else if ("content".equalsIgnoreCase(imgUri.getScheme())) {
                // Return the remote address
                if (isGooglePhotosUri(imgUri)) {
                    return imgUri.getLastPathSegment();
                }
                return getDataColumn(context, imgUri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(imgUri.getScheme())) {
                return imgUri.getPath();
            }
            return null;

        }
    }


    /**
     * 图片裁剪和压缩
     *
     * @param context
     * @param imgUri
     * @param filePath    图片的真实路径
     * @param requestCode
     */
    public static void imgTailor(Object context, Uri imgUri, String filePath, int requestCode) {
        Intent intentTailor = new Intent(); // 跳到裁剪图片界面的intent
        intentTailor.setAction("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intentTailor.setDataAndType(Uri.fromFile(new File(filePath)), "image/*");
        } else {
            intentTailor.setDataAndType(imgUri, "image/*");// Uri是已经选择的图片Uri
        }
        intentTailor.putExtra("crop", "true");
        intentTailor.putExtra("aspectX", 1);// 裁剪框比例
        intentTailor.putExtra("aspectY", 1);
        intentTailor.putExtra("outputX", 200);// 输出图片大小
        intentTailor.putExtra("outputY", 200);
        intentTailor.putExtra("return-data", true);
        if(context instanceof Activity){
            ((Activity)context).startActivityForResult(intentTailor, requestCode);
        }
//        else if(context instanceof BaseFragment){
//            ((BaseFragment) context).startActivityForResult(intentTailor, requestCode);
//        }
    }

    public static Bitmap compress(String imgPath) {
        return compress(imgPath, 30f, 30f);//默认缩放
    }

    /**
     * 尺寸压缩
     *
     * @param imgPath 图片真实路径
     * @param pixelW  压缩后目标宽度
     * @param pixelH  压缩后的目标高度
     * @return
     */

    public static Bitmap compress(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFile(imgPath, newOpts);
        if(newOpts.outWidth <= 0 || newOpts.outHeight <=0){
            try {
                ExifInterface exifInterface = new ExifInterface(imgPath);
                newOpts.outWidth = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);
                newOpts.outHeight = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        while(w>ww*be||h>hh*be){
            be *= 2;
        }
//        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outWidth / ww);
//        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
//            be = (int) (newOpts.outHeight / hh);
//        }
//        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inJustDecodeBounds = false;

        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        return bitmap;
    }

    public static Bitmap setPhoto(Intent intent, ImageView imageView) {
        Bitmap bitmap = intent.getParcelableExtra("data");

        int byteCount = bitmap.getByteCount();
        int rowBytes = bitmap.getRowBytes();
        if (null != bitmap) {
            // 压缩图片
            ByteArrayOutputStream bos = null;
            try {
                bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                int percent = 0;
                long length = bos.toByteArray().length;

                if (length > 2 * 1024 * 1024) {
                    bos.reset();
                    percent = (int) (2f * 1024 * 1024 / length * 100f);
                    if (percent > 100) {
                        percent = 100;
                    }
                    bitmap.compress(Bitmap.CompressFormat.JPEG, percent, bos);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }

        return bitmap;
    }

    public static void intoPhotoList(Object context) {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        if(context instanceof Activity){
            ((Activity)context).startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);
        }
//        else if(context instanceof BaseFragment){
//            ((BaseFragment) context).startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);
//        }


    }

    public static void intoCameraList(Object context) {
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//
//        ContentValues contentValues = new ContentValues(1);
//        contentValues.put(MediaStore.Images.Media.DATA, imgPath);
//        Uri uri = ((Activity)context).getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//
//        ((Activity)context).startActivityForResult(intent, TAKE_PICTURE);

        File takeImageFile = null;
        Activity activity = (Activity) context;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            if (Utils.existSDCard())
                takeImageFile = new File(Environment.getExternalStorageDirectory(), "/DCIM/camera/");
            else takeImageFile = Environment.getDataDirectory();
            takeImageFile = createFile(takeImageFile, "IMG_", ".jpg");
            if (takeImageFile != null) {
                // 默认情况下，即不需要指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                // 照相机有自己默认的存储路径，拍摄的照片将返回一个缩略图。如果想访问原始图片，
                // 可以通过dat extra能够得到原始图片位置。即，如果指定了目标uri，data就没有数据，
                // 如果没有指定uri，则data就返回有数据！

                Uri uri;
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    uri = Uri.fromFile(takeImageFile);
                } else {


                    /**
                     * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
                     * 并且这样可以解决MIUI系统上拍照返回size为0的情况
                     */
                    uri = FileProvider.getUriForFile(activity, ProviderUtil.getFileProviderName(activity), takeImageFile);
                    //加入uri权限 要不三星手机不能拍照
                    List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities
                            (takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        activity.grantUriPermission(packageName, uri, Intent
                                .FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }

                Log.e("nanchen", ProviderUtil.getFileProviderName(activity));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
        }
        if(takeImageFile!=null)
            EventBus.getDefault().post(new TakePicEvent(takeImageFile.getAbsolutePath()));
        activity.startActivityForResult(takePictureIntent, TAKE_PICTURE);
    }




}
