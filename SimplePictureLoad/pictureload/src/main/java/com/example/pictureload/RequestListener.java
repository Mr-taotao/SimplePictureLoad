package com.example.pictureload;

import android.graphics.Bitmap;

/**
 * Created by chtlei on 19-9-4.
 */

public interface RequestListener {

    void onSuccess(Bitmap bitmap);

    void onFailure();

}
