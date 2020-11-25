package com.example.aparking.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView info, cancel;
    private String text = null;
    public static Check_qr newinstance(){
        return new Check_qr();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.activity_check_qr, container, false);

        createQRbtn = (Button)root.findViewById(R.id.nav_btn);
        scanQRbtn = root.findViewById(R.id.call_btn);
        info = root.findViewById(R.id.qrcheckInfotext);
        cancel = root.findViewById(R.id.qrcheck_cancel);

        info.setText("예약된 주차장이 없습니다.");

        ImageView iv = root.findViewById(R.id.qrcode);
        String key = getArguments().getString("qrcode");
        text = key;
        if(text != null) {
            info.setText("주차장 QR코드 인식기에 인식시켜주세요");
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                iv.setImageBitmap(bitmap);
            } catch (Exception e) {
            }
        }

        scanQRbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //popstack 부르기?
            }
        });

        //왜 안되지?
        /*
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(root.getContext());
                alertDialogBuilder.setTitle("예약 취소");
                alertDialogBuilder.setMessage("예약취소하겠습니까?");
                alertDialogBuilder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text = null;
                        Toast.makeText(root.getContext(),"예약이 취소되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });
                alertDialogBuilder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
            }
        });
         */

        return root;
    }

}
