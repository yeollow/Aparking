package com.example.aparking;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Signin extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnCancelListener {
    static final String mailSender = "ljy2784437@gmail.com";
    static final String mailSenderPW = "Dlwjdduf1!";

    TextInputLayout authEmail;
    TextInputLayout username;
    TextInputLayout passwd;
    TextInputLayout confirmPw;
    TextInputLayout phone;

    EditText authEmailText;

    String acc_name;
    String acc_pw;
    String acc_pw_confirm;
    String phone_num;
    String acc_email;


    Button authButton;
    LayoutInflater dialog;      //layout inflater
    View dialogLayout;          //layout view
    Dialog authDialog;          //dialog 객체

    TextView timeCounter;

    String userEmailAddress;
    EditText emailAuthNumber;
    Button emailAuthButton;
    CountDownTimer timer;

    //    DatabaseReference databaseReference;
    final int TIMEOUT = 180 * 1000;   //3분
    final int COUNT_INTERVAL = 1000;    //간격 1초


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

//        이거 사용하기 전에는 선언해놓으면 안되나보다.
//         databaseReference = FirebaseDatabase.getInstance().getReference();

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

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        authEmail = findViewById(R.id.email);
        authButton = findViewById(R.id.authButton);
        authButton.setOnClickListener(this);

    }

    String SMTPEmailCode = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.authButton:
                username = findViewById(R.id.username);
                username.setCounterEnabled(true);

                passwd = findViewById(R.id.passwd);
                phone = findViewById(R.id.phone);

                confirmPw = findViewById(R.id.cofirm_pw);

                acc_name = username.getEditText().getText().toString();
                acc_pw = passwd.getEditText().getText().toString();
                phone_num = phone.getEditText().getText().toString();
                acc_email = authEmail.getEditText().getText().toString();
                acc_pw_confirm = confirmPw.getEditText().getText().toString();

                authEmailText = authEmail.getEditText();
                authEmailText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        boolean email_check = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", acc_email);

                        if (!email_check)
                            authEmailText.setError("올바른 이메일 형식을 사용해주세요");
                        else
                            authEmailText.setError(null);
                    }
                });

                if (!acc_pw_confirm.equals(acc_pw)) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                } else {
                    dialog = LayoutInflater.from(this);
//                layout resource를 view의 형태로 반환
                    dialogLayout = dialog.inflate(R.layout.auth_dialog, null);
                    authDialog = new Dialog(this);
                    authDialog.setContentView(dialogLayout);        //Dialog에 inflate한 view를 삽입
                    authDialog.setCanceledOnTouchOutside(false);       //Dialog 바깥부분 선택 시 닫히지 않음.
                    authDialog.setOnCancelListener(this);
                    authDialog.show();      //show

                    try {
                        GMailSender GmailSender = new GMailSender(mailSender, mailSenderPW);
                        SMTPEmailCode = GmailSender.getEmailCode();
                        userEmailAddress = authEmail.getEditText().getText().toString();     //사용자 입력 email정보

                        GmailSender.sendMail("Aparking 인증코드 입니다", SMTPEmailCode, userEmailAddress);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    countdownTimer();


                    break;
                }
        }
    }

    public void countdownTimer() {
        timeCounter = dialogLayout.findViewById(R.id.emailAuth_time_counter);
        emailAuthNumber = dialogLayout.findViewById(R.id.emailAuth_number);
        emailAuthButton = dialogLayout.findViewById(R.id.signUp);
        final EditText emailAuthCode = dialogLayout.findViewById(R.id.emailAuth_number);

        timer = new CountDownTimer(TIMEOUT, COUNT_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                Long emailAuthCount = millisUntilFinished / 1000;

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10)
                    timeCounter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                else
                    timeCounter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
            }

            @Override
            public void onFinish() {
                authDialog.cancel();
            }
        }.start();


//        이거 null인거 해결하면 인증코드랑 입력된 인증코드랑 비교해서 dialog닫고 회원가입 버튼을 누르면 디비로 회원 정보가 전송 -> Login화면으로 이동 -> Login에서 로그인 정보를 받아와서 디비에 질의 -> 아이디가 있으면 로그인!
        emailAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailCode = emailAuthCode.getText().toString();

                if (emailCode.equals(SMTPEmailCode)) {
//                    회원정보 테이블은 aparking-d0735-2ba22 이거인듯.. 콘솔 들어가서 확인해봐ㅋㅋ 데이터베이스에 어떻게 값 넣는지도 한번 봐라.
//                    분명 database 이름과 json객체 설정, json field(key,value)를 설정할 수 있는 방법이 있을거야
//                    acc_name, acc_pw, phone_num, acc_email 전부 보내면 됨.

                    Toast.makeText(getApplicationContext(), "FirebaseDatabase로 acc_name, acc_pw, phone_num, acc_email을 보내고 저장하자. (회원가입 완료)", Toast.LENGTH_LONG).show();

                    authDialog.cancel();
                    timer.cancel();

                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "인증번호 다시 인증해!!", Toast.LENGTH_LONG).show();
                    authDialog.cancel();
                    timer.cancel();
                }
            }
        });
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        timer.cancel();
    }
}