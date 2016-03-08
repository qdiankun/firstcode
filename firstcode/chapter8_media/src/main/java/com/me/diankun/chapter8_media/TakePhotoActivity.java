package com.me.diankun.chapter8_media;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.me.diankun.chapter8_media.utils.CameraUtils;
import com.me.diankun.chapter8_media.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 拍摄照片
 * Created by diankun on 2016/3/8.
 */
public class TakePhotoActivity extends AppCompatActivity {

    private Button btn_take_photo;
    private Button btn_choose_photo;
    private ImageView imageview;

    //这个Uri对象标识着output_image.jpg这张图片的唯一地址
    private Uri imageUri;
    private String imagePath;

    private SimpleDateFormat simpleDateFormat;

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int CHOOSE_PHOTO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        btn_take_photo = (Button) findViewById(R.id.btn_take_photo);
        btn_choose_photo = (Button) findViewById(R.id.btn_choose_photo);
        imageview = (ImageView) findViewById(R.id.imageview);

        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

        btn_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
    }

    private void choosePhoto() {
        //选择照片
        //Intent intent = new Intent("android.intent.action.GET_CONTENT");
        Intent intent = new Intent(
                // 相册
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    private void takePhoto() {
        imageUri = Uri.fromFile(generateFile());
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(i, TAKE_PHOTO); // 启动相机程序
    }

    private File generateFile() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "firstcode";
        File file = new File(path);
        //判断文件夹是否存在，不存在创建
        if (!file.exists()) {
            file.mkdirs();
        }
        //拍摄照片的路径
        imagePath = path + File.separator + simpleDateFormat.format(new Date()) + ".jpg";
        File imageFile = new File(imagePath);
        return imageFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    //imageview.setImageBitmap(bitmap);
                    // 裁剪图片
                    cropImage();
                    break;
                case CHOOSE_PHOTO:
                    if (data == null) return;
                    //获取图片所在的真实路径
                    String picturePath = CameraUtils.getPath(TakePhotoActivity.this, data.getData());
                    //将图片复制到我们firstcode的文件夹下
                    File targetLocation = generateFile();
                    FileUtils.copyFile(new File(picturePath), targetLocation);
                    //指定我们拷贝的文件为待裁剪的图片
                    imageUri = Uri.fromFile(targetLocation);
                    cropImage();
                    break;
                case CROP_PHOTO:

                    try {
                        //使用decodeStream 获取Bitmap
                        Bitmap map = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageview.setImageBitmap(map);

                        //使用decodeFile来，获取Bitmap
                        //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                        //imageview.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    /**
     * 启动裁剪程序
     */
    public void cropImage() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CROP_PHOTO);
    }


}
