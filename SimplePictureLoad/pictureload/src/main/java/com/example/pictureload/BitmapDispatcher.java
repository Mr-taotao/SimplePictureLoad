package com.example.pictureload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by chtlei on 19-9-4.
 */

//===============================================
    //处理每个图片请求,从队列中获取请求并认证后处理
//===============================================
public class BitmapDispatcher extends Thread {
    private LinkedBlockingQueue<BitmapRequest> requestsQueue;
    private Handler handler = new Handler(Looper.getMainLooper());

    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> requestsQueue) {
        this.requestsQueue = requestsQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                //从队列中获取图片请求
                BitmapRequest request = requestsQueue.take();
                //显示占位图片
                showLoadingImage(request);
                //加载图片
                Bitmap bitmap = findBitmap(request);
                //将图片显示到ImageView
                showImageView(request,bitmap);
            } catch (Exception e) {

            }
        }
    }

    private void showImageView(final BitmapRequest request, final Bitmap bitmap) {
        if (request.getImageView() != null && bitmap != null
                && (request.getUrlMD5().equals(request.getImageView().getTag()))) {
            final ImageView imageView = request.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    if (request.getRequestListener() != null) {
                        request.getRequestListener().onSuccess(bitmap);
                    }
                }
            });
        }
    }

    private Bitmap findBitmap(BitmapRequest request) {
        if (TextUtils.isEmpty(request.getUrl())) {
            return null;
        }
        return downloadBitmap(request.getUrl());
    }

    private Bitmap downloadBitmap(String uri) {
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private void showLoadingImage(BitmapRequest request) {
        final ImageView imageView = request.getImageView();
        final int resId = request.getResId();
        if (resId > 0 && imageView != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId);
                }
            });
        }
    }
}
