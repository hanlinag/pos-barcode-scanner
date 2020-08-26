package com.ucsm.tylersai.pos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRActivity extends  AppCompatActivity implements ZXingScannerView.ResultHandler{

private ZXingScannerView mScannerView;

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
public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
        }

@Override
public void handleResult(Result rawResult) {
        // Do something with the result here
        // Log.v("tag", rawResult.getText()); // Prints scan results
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        //MainActivity.et_ip.setText(rawResult.getText());
        //onBackPressed();
        Common.result = rawResult.getText();
        Intent intent = new Intent(QRActivity.this, AfterScanActivity.class);
        startActivity(intent);

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        }
        }