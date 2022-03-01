package com.example.flashlight;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnflash;
    private static final int Camera_Request = 123;
    boolean h1 = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{Manifest.permission.CAMERA}, Camera_Request);
        h1 = getPackageManager().hasSystemFeature(PackageManager.
                FEATURE_CAMERA_FLASH);
        btnflash = findViewById(R.id.onof);
        btnflash.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (h1) {
                    if (btnflash.getText().toString().contains("ON")) {
                        btnflash.setText("Flashlight off");
                        flashlightoff();
                    } else {
                        btnflash.setText("FlashLight ON");
                        flashlightON();
                    }
                }
                else {
                    Toast.makeText(MainActivity.
                            this, "No flash available in your " +
                            "device", Toast.LENGTH_SHORT).show();
                }

            }
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            private void flashlightoff() {
                CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                try {
                    String CameraId=cameraManager.getCameraIdList()[0];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cameraManager.setTorchMode(CameraId, false);
                    }
                }
                catch (CameraAccessException e) {

                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void flashlightON() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String CameraId=cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(CameraId, true);
            }
        }
        catch (CameraAccessException e) {

        }
    }

    public void OnRequestPermissionResult(int requestCode, @NonNull String[] Permission, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Camera_Request:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    h1 = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                } else {
                    btnflash.setEnabled(false);
                    Toast.makeText(this, "Permission DEnied for thencamera", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}