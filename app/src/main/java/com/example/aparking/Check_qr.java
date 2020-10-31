package com.example.aparking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Check_qr extends AppCompatActivity {
    private Button createQRbtn;
    private Button scanQRbtn;
    private ImageView iv;
    private String text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_qr);

        Intent it = getIntent();

        createQRbtn = (Button)findViewById(R.id.nav_btn);
        scanQRbtn = findViewById(R.id.call_btn);

        createQRbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Intent intent = new Intent(MainActivity.this, CreateQR.class);
                //startActivity(intent);

                iv = findViewById(R.id.qrcode);
                text = "123";

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    iv.setImageBitmap(bitmap);
                }catch (Exception e){}
            }

        });

        scanQRbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Toast.makeText(getApplicationContext(),"확인 누름",Toast.LENGTH_LONG).show();
              //  Intent intent = new Intent(MainActivity.this, ScanQR.class);
                //startActivity(intent);
            }
        });
    }

}
