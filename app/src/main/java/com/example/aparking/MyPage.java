package com.example.aparking;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
    }

    public void changeInfo(View v){
        Toast.makeText(this.getApplicationContext(), "내 정보 변경", Toast.LENGTH_LONG).show();
    }

    public void addCard(View v){
        Toast.makeText(this.getApplicationContext(), "카드 등록하기", Toast.LENGTH_LONG).show();
    }
}
