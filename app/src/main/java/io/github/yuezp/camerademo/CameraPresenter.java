package io.github.yuezp.camerademo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.github.yuezp.camerademo.utils.MyUtils;

/**
 * Created by YueZp on 17/2/24.
 */

public class CameraPresenter implements CameraContract.Presenter {


    private static final int RQUEST_CAMERA = 0x11;
    private CameraContract.View cameraFragment;
    private String cameraPrivatePath;
    private String cameraPublicPath;
    private Activity mActivity;

    private int operate = 0;

    public CameraPresenter(Activity activity, CameraContract.View cameraView) {
        mActivity = activity;
        this.cameraFragment = MyUtils.checkNotNUll(cameraView, "cameraView is NULL!");
        cameraFragment.setPresenter(this);
    }


    @Override
    public void openCamera(int tag) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(mActivity.getPackageManager()) != null) {//判断是否有相机应用
            File photoFile = null;
            this.operate = tag;
            try {
                if (tag == 1) {
                    photoFile = createPrivatePath();//创建临时图片文件
                } else if (tag == 2) {
                    photoFile = createPublicPath();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mActivity,
                        "io.github.yuezp.camerademo",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mActivity.startActivityForResult(intent, RQUEST_CAMERA);
            }

        }

    }

    @Override
    public void showPictureMessage() {
    }

    @Override
    public File createPrivatePath() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //.getExternalFilesDir()方法获取应用程序下的存储目录，/data/data/your_package/，随着应用的卸载存储的文件被删除
        File storageDir = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES); // 私有文件保存目录
        //创建临时文件,文件前缀不能少于三个字符,后缀如果为空默认未".tmp"
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",         /* 后缀 */
                storageDir      /* 文件夹 */
        );
        cameraPrivatePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public File createPublicPath() throws IOException {
        File path = Environment.getExternalStoragePublicDirectory( // 共享文件保存目录 path:/storage/emulated/0/DCIM
                Environment.DIRECTORY_DCIM);
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",         /* 后缀 */
                path      /* 文件夹 */
        );
        cameraPublicPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("yuezp", "CameraPresenter onActivityResult ...");
        if (requestCode == RQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            // Get the dimensions of the View
            if (operate == 1) {
                cameraFragment.showPicture(cameraPrivatePath);
            } else if (operate == 2) {
                cameraFragment.showPicture(cameraPublicPath);
                addGallery();
            }
        }
    }

    @Override
    public void addGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(cameraPublicPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mActivity.sendBroadcast(mediaScanIntent);
    }

}
