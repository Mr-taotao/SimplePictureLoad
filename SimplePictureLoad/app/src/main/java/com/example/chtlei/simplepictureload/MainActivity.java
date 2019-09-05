package com.example.chtlei.simplepictureload;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pictureload.PictureLoad;
import com.example.pictureload.RequestListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button1;

    @BindView(R.id.button2)
    Button button2;

    @BindView(R.id.lly)
    LinearLayout lly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClickButton1 () {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        PictureLoad.with(this).loading(R.drawable.ic_launcher_background).load("http://icweiliimg1.pstatp.com/weili/l/326991554114224131.webp")
                .listen(new RequestListener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        Toast.makeText(MainActivity.this,"picture load success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {

                    }
                }).into(imageView);
        lly.addView(imageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
