package com.example.aparking;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Signin extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnCancelListener{
    final int AUTHRAND = 123456;

    EditText authEmail;
    Button authButton;
    LayoutInflater dialog;      //layout inflater
    View dialogLayout;          //layout view
    Dialog authDialog;          //dialog 객체

    TextView timeCounter;
    EditText emailAuthNumber;
    Button emailAuthButton;
    CountDownTimer timer;

    final int TIMEOUT = 180*1000;   //3분
    final int COUNT_INTERVAL = 1000;    //간격 1초
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Button b = (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        SnsSignin.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }
        });

        authEmail = findViewById(R.id.authEmail);
        authButton = findViewById(R.id.authButton);
        authButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.authButton:
                dialog = LayoutInflater.from(this);
//                layout resource를 view의 형태로 반환
                dialogLayout = dialog.inflate(R.layout.auth_dialog, null);
                authDialog = new Dialog(this);
                authDialog.setContentView(dialogLayout);        //Dialog에 inflate한 view를 삽입
                authDialog.setCanceledOnTouchOutside(false);       //Dialog 바깥부분 선택 시 닫히지 않음.
                authDialog.setOnCancelListener(this);
                authDialog.show();      //show

                countdownTimer();

                break;


            case R.id.emailAuth_btn:
                int userAnswer = Integer.parseInt(emailAuthNumber.getText().toString());
                if (userAnswer == AUTHRAND) {
                    Toast.makeText(this, "이메일 인증 성공", Toast.LENGTH_LONG).show();

//                    이메일 인증이 완료 되어야 회원가입이 가능토록. - 회원 정보 -> db 적재 ..

                } else {
                    Toast.makeText(this, "이메일 인증 실패", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    public void countdownTimer() {
        timeCounter = dialogLayout.findViewById(R.id.emailAuth_time_counter);
        emailAuthNumber = dialogLayout.findViewById(R.id.emailAuth_number);
        emailAuthButton = dialogLayout.findViewById(R.id.emailAuth_btn);

        timer = new CountDownTimer(TIMEOUT, COUNT_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                Long emailAuthCount = millisUntilFinished/1000;

                if((emailAuthCount - ((emailAuthCount/60)*60)) >= 10)
                    timeCounter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                else
                    timeCounter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
            }

            @Override
            public void onFinish() {
                authDialog.cancel();
            }
        }.start();

        emailAuthButton.setOnClickListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        timer.cancel();
    }
}
