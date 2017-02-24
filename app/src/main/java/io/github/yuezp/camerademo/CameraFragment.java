package io.github.yuezp.camerademo;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import io.github.yuezp.camerademo.utils.MyUtils;

/**
 * Created by YueZp on 17/2/24.
 */

public class CameraFragment extends Fragment implements CameraContract.View, View.OnClickListener {

    private android.widget.Button capture;
    private android.widget.Button capturegallery;
    private android.widget.ImageView mImageView;
    private Activity activity;

    private CameraContract.Presenter cameraPresenter;

    public CameraFragment() {
    }

    @SuppressLint("ValidFragment")
    public CameraFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        this.mImageView = (ImageView) root.findViewById(R.id.display);
        this.capturegallery = (Button) root.findViewById(R.id.capture_gallery);
        this.capture = (Button) root.findViewById(R.id.capture);
        initViews();
        return root;
    }

    @Override
    public void initViews() {
        this.mImageView.setOnClickListener(this);
        this.capturegallery.setOnClickListener(this);
        this.capture.setOnClickListener(this);

    }

    @Override
    public void showPicture(String picturePath) {
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(picturePath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void setPresenter(CameraContract.Presenter presenter) {
        cameraPresenter = MyUtils.checkNotNUll(presenter, "cameraPresenter is NULL!");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.capture) {
            cameraPresenter.openCamera(1);
        } else if (id == R.id.capture_gallery) {
            cameraPresenter.openCamera(2);
        } else if (id == R.id.display) {
            cameraPresenter.showPictureMessage();
        }
    }
}
