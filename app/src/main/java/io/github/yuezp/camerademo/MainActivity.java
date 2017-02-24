package io.github.yuezp.camerademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private CameraFragment cameraFragment;
    private CameraPresenter cameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (cameraFragment == null) {
            cameraFragment = new CameraFragment(this);
            getSupportFragmentManager().beginTransaction().add(R.id.camera_fragment, cameraFragment).commit();
        }

        if (cameraPresenter == null) {
            cameraPresenter = new CameraPresenter(this, cameraFragment);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("yuezp", "MainActivity onActivityResult ...");
        cameraPresenter.onActivityResult(requestCode, resultCode, data);
    }
}
