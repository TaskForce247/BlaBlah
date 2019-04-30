package com.AlexPetrovics.blablah.views.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.AlexPetrovics.blablah.R;
import com.AlexPetrovics.blablah.utils.RuntimePermissionUtil;
import com.AlexPetrovics.blablah.views.interfaces.RPResultListener;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class QrActivity extends AppCompatActivity {

    private static final String cameraPerm = Manifest.permission.CAMERA;
    String roomvalue = "0";
    // UI
    private TextView text;

    // QREader
    private SurfaceView mySurfaceView;
    private QREader qrEader;

    boolean hasCameraPermission = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        hasCameraPermission = RuntimePermissionUtil.checkPermissonGranted(this, cameraPerm);

        text = findViewById(R.id.code_info);


        Button sendBtn = findViewById(R.id.btn_send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = roomvalue;

                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", result);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


        // Setup SurfaceView
        // -----------------
        mySurfaceView = findViewById(R.id.camera_view);

        if (hasCameraPermission) {
            // Setup QREader
            setupQREader();
        } else {
            RuntimePermissionUtil.requestPermission(QrActivity.this, cameraPerm, 100);
        }
    }

    void restartActivity() {
        startActivity(new Intent(QrActivity.this, QrActivity.class));
        finish();
    }

    void setupQREader() {
        // Init QREader
        // ------------
        qrEader = new QREader.Builder(this, mySurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                Log.d("QREader", "Value : " + data);
                text.post(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(data);
                    }
                });
                roomvalue = data;

                // EXTRA_ROOM_NAME = text;
            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mySurfaceView.getHeight())
                .width(mySurfaceView.getWidth())
                .build();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (hasCameraPermission) {

            // Cleanup in onPause()
            // --------------------
            qrEader.releaseAndCleanup();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hasCameraPermission) {

            // Init and Start with SurfaceView
            // -------------------------------
            qrEader.initAndStart(mySurfaceView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        if (requestCode == 100) {
            RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                @Override
                public void onPermissionGranted() {
                    if ( RuntimePermissionUtil.checkPermissonGranted(QrActivity.this, cameraPerm)) {
                        restartActivity();
                    }
                }

                @Override
                public void onPermissionDenied() {
                    // do nothing
                }
            });
        }
    }


}
