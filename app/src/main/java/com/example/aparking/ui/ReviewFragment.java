package com.example.aparking.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aparking.R;

public class ReviewFragment extends Fragment {
    ListView list;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_review, container, false);

        list = (ListView)root.findViewById(R.id.reviewList);
        ReviewAdapter adapter = new ReviewAdapter(inflater.getContext(),R.layout.listitem_review);
        list.setAdapter(adapter);

        return root;
    }

    public class ReviewAdapter extends BaseAdapter{
        Context context;
        int layout;
        LayoutInflater inflater;

        String[] user_name = {
                "김도희",
                "백혜원",
                "이정열",
                "오주영"
        };

        String[] text = {
                "주차장이 넓어서 좋아요!!",
                "문제가 생겨서 관리자분께 연락해서 해결했습니다. 친절하고 좋았어요~~",
                "깔끔하고 괜찮음. 근데 다른 곳보다 좀 비싼 것 같다;; 그래도 여기만 한 데가 없어서 거의 여기만 씀. 복현동 출장갈 때 강추",
                "낮 2시쯤 갔는데 자리 별로 없었어요..."
        };

        // 생성자
        public ReviewAdapter(Context context, int layout){
            this.context = context;
            this.layout = layout;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return user_name.length;
        }

        @Override
        public Object getItem(int i) {
            return user_name[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null)
                view = inflater.inflate(layout, null);

            TextView user_names = (TextView)view.findViewById(R.id.reviewUserName);
            user_names.setText(user_name[position]);

            TextView texts = (TextView)view.findViewById(R.id.reviewText);
            texts.setText(text[position]);
            return view;
        }
    }
}
