package com.example.aparking.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aparking.R;

public class MypageFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_mypage, container, false);

        return root;
    }

    public void changeInfo(View v){
        Toast.makeText(getActivity(), "내 정보 변경", Toast.LENGTH_LONG).show();
    }

    public void addCard(View v){
        Toast.makeText(getActivity(), "카드 등록하기", Toast.LENGTH_LONG).show();
    }
}