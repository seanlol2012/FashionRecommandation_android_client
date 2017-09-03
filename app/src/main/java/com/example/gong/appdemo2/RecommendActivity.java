package com.example.gong.appdemo2;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecommendActivity extends AppCompatActivity {

    //设置浏览本地相册用的变量
    private Button mButton;
    private ImageView mImageView;//用于显示照片

    //设置拍照用的变量
    private Button mButton2;
    private File mPhotoFile;
    private String mPhotoPath;
    public final static int CAMERA_RESULT = 1;

    //上传图片
    private static String requestURL = "UploadPicServlet";
    //图片uri
    String pic_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        mButton = (Button)findViewById(R.id.button1);
        mButton2 = (Button)findViewById(R.id.button2);
        mImageView = (ImageView)findViewById(R.id.imagePhoto);

        //获取本地图片
        setListener();
        setListener2();
    }

    protected void setListener() {
        mButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //开启pictures画面type设定为image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT这个Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //取得相片后返回本画面
                startActivityForResult(intent, 1);

                new Thread((Runnable)() -> {
                    try {
                        File file1 = new File(Environment.getExternalStorageDirectory(), pic_uri);
                        if(file1!=null) {
                            String request = UploadUtil.uploadFile(file1, "UploadPicServlet");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    protected void setListener2() {
        mButton2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    mPhotoPath = getSDPath() + "/" + getPhotoFileName();
                    mPhotoFile = new File(mPhotoPath);

                    if(!mPhotoFile.exists()) {
                        mPhotoFile.createNewFile();
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(mPhotoFile));
                    startActivityForResult(intent, CAMERA_RESULT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date)+".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Uri uri = data.getData();
            pic_uri = uri.toString();
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                mImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void gotoMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
