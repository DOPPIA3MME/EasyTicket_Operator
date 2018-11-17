package com.example.maurizio.appminieri2;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;









import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCode extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    Toast my_Toast;
    TextView v;
    View view;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }


    @Override
    public void handleResult(Result rawResult) {

        String qrcodeRequest = "http://ingsoftw.eu-central-1.elasticbeanstalk.com/validate/qrcode/" + rawResult.getText();


        Request request = new Request.Builder().url(qrcodeRequest).build();
        Accesso.client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            Log.i("messaggi", "FAILURE");
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            if (response.isSuccessful()) {
                Log.i("messaggi", "response ACCETTATO : " + response);
            } else {
                Log.i("messaggi", "response FALLITO : " + response);
                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(190);
                if (response.code() == 302) {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            my_Toast = Toast.makeText(QRCode.this, "BIGLIETTO VALIDO", Toast.LENGTH_LONG);
                            v = (TextView) my_Toast.getView().findViewById(android.R.id.message);
                            view = my_Toast.getView();
                            view.getBackground().setColorFilter(getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                            v.setTextColor(Color.WHITE);
                            my_Toast.show();
                        }
                    });

                } else {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            my_Toast = Toast.makeText(QRCode.this, "BIGLIETTO NON VALIDO", Toast.LENGTH_LONG);
                            v = (TextView) my_Toast.getView().findViewById(android.R.id.message);
                            view = my_Toast.getView();
                            view.getBackground().setColorFilter(getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                            v.setTextColor(Color.WHITE);
                            my_Toast.show();
                        }
                    });

                }}}});

        onBackPressed();
    }

}