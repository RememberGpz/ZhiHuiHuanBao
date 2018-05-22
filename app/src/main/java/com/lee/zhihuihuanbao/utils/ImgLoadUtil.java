package com.lee.zhihuihuanbao.utils;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.lee.zhihuihuanbao.R;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ImgLoadUtil {

    private static ImgLoadUtil instance;

    private ImgLoadUtil() {
    }

    public static ImgLoadUtil getInstance() {
        if (instance == null) {
            instance = new ImgLoadUtil();
        }
        return instance;
    }

    /**
     * 显示高斯模糊效果
     */
    private static void displayGaussian(Context context, String url, ImageView imageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊
        Glide.with(context)
                .load(url)
                .error(R.mipmap.stackblur_default)
                .placeholder(R.mipmap.stackblur_default)
                .crossFade(500)
                .bitmapTransform(new BlurTransformation(context, 23, 4))
                .into(imageView);
    }

     /**
     * 加载圆角像（暂时用到显示头像）
     */
    public static void displayCircleImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .crossFade(500)
                .error(R.mipmap.ic_launcher)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }


    /**
     * 用于干货item，将gif图转换为静态图
     */
    public static void displayGif(String url, ImageView imageView) {

        Glide.with(imageView.getContext()).load(url)
                .asBitmap()
                .placeholder(R.mipmap.img_one_bi_one)
                .error(R.mipmap.img_one_bi_one)
                //                .skipMemoryCache(true) //跳过内存缓存
                //                .crossFade(1000)
                //                .diskCacheStrategy(DiskCacheStrategy.SOURCE)// 缓存图片源文件（解决加载gif内存溢出问题）
                //                .into(new GlideDrawableImageViewTarget(imageView, 1));
                .into(imageView);
    }


}
