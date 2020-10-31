package com.example.aparking.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aparking.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Check_qr extends Fragment {
    private Button createQRbtn;
    private Button scanQRbtn;

    private String text;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.activity_check_qr, container, false);

        //Intent it = getIntent();

        createQRbtn = (Button)root.findViewById(R.id.nav_btn);
        scanQRbtn = root.findViewById(R.id.call_btn);

        createQRbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Intent intent = new Intent(MainActivity.this, CreateQR.class);
                //startActivity(intent);

                ImageView iv = root.findViewById(R.id.qrcode);
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

        return root;
    }
}
