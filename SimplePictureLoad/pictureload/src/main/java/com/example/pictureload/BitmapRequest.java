package com.example.pictureload;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

/**
 * Created by chtlei on 19-9-4.
 */

public class BitmapRequest {

    //图片唯一标识
    private String picMd5Url;

    //图片占位符
    private int id;

    //图片请求回调
    RequestListener requestListener;

    //图片请求地址
    private String pictureUrl;

    //图片显示控件
    private SoftReference<ImageView> imageView;

    private Context context;

    public BitmapRequest(Context context) {
        this.context = context;
    }

    public BitmapRequest load (String url) {
        this.pictureUrl = url;
        this.picMd5Url = Md5Utils.toMD5(url);
        return this;
    }

    public BitmapRequest loading (int resId) {
        this.id = resId;
        return this;
    }

    public BitmapRequest listen (RequestListener requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    public void into(ImageView imageView) {
        imageView.setTag(this.picMd5Url);
        this.imageView = new SoftReference<ImageView>(imageView);
        BitmapManager.getsInstance().addBitmapRequest(this);
    }

    public int getResId() {
        return id;
    }

    public ImageView getImageView() {
        return imageView.get();
    }


    public RequestListener getRequestListener() {
        return requestListener;
    }

    public String getUrl() {
        return pictureUrl;
    }

    public String getUrlMD5() {
        return picMd5Url;
    }
}
