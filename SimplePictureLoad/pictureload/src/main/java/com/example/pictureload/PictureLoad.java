package com.example.pictureload;

import android.content.Context;

/**
 * Created by chtlei on 19-9-5.
 */

public class PictureLoad {
    public static BitmapRequest with(Context context){
        return new BitmapRequest(context);
    }
}
