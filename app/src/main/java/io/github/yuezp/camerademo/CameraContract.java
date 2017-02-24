package io.github.yuezp.camerademo;

import android.content.Intent;

import java.io.File;
import java.io.IOException;

/**
 * Created by YueZp on 17/2/23.
 */

public interface CameraContract {
    interface View extends BaseView<Presenter> {
        void initViews(); // 初始化视图 设置listener

        void showPicture(String picturePath); // 显示图片
    }

    interface Presenter extends BasePresenter {


        void openCamera(int tag); // 打开相机

        void showPictureMessage(); // 显示图片信息

        File createPrivatePath() throws IOException; // 创建私有的路径 为app独享

        File createPublicPath() throws IOException; // 创建公开的路径 为添加相册

        void onActivityResult(int requestCode, int resultCode, Intent data); // 模拟Activity的onActivityResult

        void addGallery(); // 添加图片到相册

    }
}
