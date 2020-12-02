package com.example.aparking.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aparking.R;
import com.example.aparking.ui.consultant.ConsultantFragment;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {
    ListView list;
    EditText chatText;
    Button buttonSend;
    ReviewAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_review, container, false);

        adapter = new ReviewAdapter(inflater.getContext(),R.layout.listitem_review);
        list = (ListView)root.findViewById(R.id.reviewList);
        list.setAdapter(adapter);

        // 새 data 입력 시 키보드 위로 바로 뜨도록
        list.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setStackFromBottom(true);


        // 입력창에서 엔터키 누르면 등록
        chatText = (EditText) root.findViewById(R.id.reviewChat);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    adapter.add(new Item(getString(R.string.customer_name), chatText.getText().toString()));
                    chatText.setText("");
                    adapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        // 후기 작성 버튼 누르면 등록
        buttonSend = (Button) root.findViewById(R.id.reviewBtn);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                adapter.add(new Item(getString(R.string.customer_name), chatText.getText().toString()));
                chatText.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        // adapter에 묶여있는 item 변경 감지
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                list.setSelection(adapter.getCount() - 1);
                //list.smoothScrollToPosition(adapter.getCount() -1);
            }
        });

        return root;
    }

    // 어댑터 클래스
    public class ReviewAdapter extends BaseAdapter {
        Context context;
        int layout;
        LayoutInflater inflater;
        List<Item> reviewList = new ArrayList<Item>();

        // 생성자
        public ReviewAdapter(Context context, int layout) {
            this.context = context;
            this.layout = layout;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            initialize();
        }

        // 초기 data 입력
        public void initialize() {
            add(new Item("김도희", "주차장이 넓어서 좋아요!!"));
            add(new Item("백혜원", "문제가 생겨서 관리자분께 연락해서 해결했습니다. 친절하고 좋았어요~~"));
            add(new Item("이정열", "깔끔하고 괜찮음. 근데 다른 곳보다 좀 비싼 것 같다;; 그래도 여기만 한 데가 없어서 거의 여기만 씀. 복현동 출장갈 때 강추"));
            add(new Item("오주영", "낮 2시쯤 갔는데 자리 별로 없었어요..."));
        }

        // 리스트에 새 data 추가
        public void add(Item item) {
            reviewList.add(item);
        }

        @Override
        public int getCount() {
            return this.reviewList.size();
        }

        @Override
        public Item getItem(int i) {
            return (Item) this.reviewList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null)
                view = inflater.inflate(layout, null, true);

            Item item = getItem(position);

            TextView user_names = (TextView) view.findViewById(R.id.reviewUserName);
            user_names.setText(item.getUser_name());

            TextView texts = (TextView) view.findViewById(R.id.reviewText);
            texts.setText(item.getText());
            return view;
        }
    }
    // 리스트에 넣을 후기 박스 아이템
    public class Item {
        private String user_name;
        private String text;

        public Item (String user_name, String text){
            this.user_name = user_name;
            this.text = text;
        }
        public String getUser_name() {
            return user_name;
        }
        public String getText() {
            return text;
        }
    }
}
