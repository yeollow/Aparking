package com.example.aparking.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aparking.R;

public class MypageFragment extends Fragment {
    LayoutInflater parent;
    Button changeBtn;
    LinearLayout addCardBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        parent = inflater;
        View root = inflater.inflate(R.layout.activity_mypage, container, false);

        /* 내 정보 변경 Click 처리 */
        changeBtn = root.findViewById(R.id.changeBtn);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(parent.getContext(), "내 정보 변경", Toast.LENGTH_LONG).show();
            }
        });

        /* 카드 등록 Click 처리 */
        addCardBtn = root.findViewById(R.id.addCardBtn);
        addCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(parent.getContext(), "카드 등록하기", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
}