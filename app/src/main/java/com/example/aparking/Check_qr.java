package com.example.aparking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Check_qr extends AppCompatActivity {
    public static Context context;

    private Button createQRbtn;
    private Button scanQRbtn;
    private Button closeBtn;
    private TextView deleteQR;
    public ImageView QRcode;
    private String text;
    private String getQR;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_qr);

        createQRbtn = findViewById(R.id.nav_btn);
        scanQRbtn = findViewById(R.id.call_btn);
        closeBtn = findViewById(R.id.close_btn);
        deleteQR = findViewById(R.id.qrcheck_cancel);

        Intent intent = getIntent();
        getQR = intent.getStringExtra("qrcode"); //qrIntent
        if (getQR.equals("qrIntent")) {
            ImageView qr = findViewById(R.id.qrcode);
            text = "create QRcode";

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                qr.setImageBitmap(bitmap);
            } catch (Exception e) {
            }
        }


        scanQRbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"확인 누름",Toast.LENGTH_LONG).show();
                //  Intent intent = new Intent(MainActivity.this, ScanQR.class);
                //startActivity(intent);
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        deleteQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRcode = findViewById(R.id.qrcode);
                QRcode.setImageResource(0);
            }
        });
    }


}
