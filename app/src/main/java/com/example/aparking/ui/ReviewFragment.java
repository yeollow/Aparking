package com.example.aparking.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
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
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aparking.R;
import com.example.aparking.ui.consultant.ConsultantFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReviewFragment extends Fragment {
    ListView list;
    EditText chatText;
    Button buttonSend;
    ReviewAdapter adapter;
    RatingBar ratingBar;
    RatingBar ratingsAvg;
    TextView reviewCnt;
    int cnt;
    float sum = 0;
    float avg = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_review, container, false);

        ratingBar = (RatingBar)root.findViewById((R.id.reviewRatingInput));
        ratingsAvg = (RatingBar)root.findViewById(R.id.reviewRatingAvg);
        reviewCnt = (TextView)root.findViewById((R.id.reviewCnt));

        // 평균 별점 노란색으로
        LayerDrawable stars = (LayerDrawable) ratingsAvg.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#feee7d"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.parseColor("#feee7d"), PorterDuff.Mode.SRC_ATOP);

        // 어댑터 등록
        adapter = new ReviewAdapter(inflater.getContext(),R.layout.listitem_review);
        list = (ListView)root.findViewById(R.id.reviewList);
        list.setAdapter(adapter);
        setRatingAvg();
        setReviewCnt();

        // 아이템을 리스트 맨 뒤에 추가할 때 적용
        //bookmarkList.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        // 입력창에서 엔터키 누르면 등록
        chatText = (EditText) root.findViewById(R.id.reviewChat);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    adapter.add(new Item(getString(R.string.customer_name), chatText.getText().toString(), getTodayDate(), ratingBar.getRating()));
                    adapter.notifyDataSetChanged();
                    setRatingAvg();
                    setReviewCnt();
                    // 입력 창 초기화
                    chatText.setText("");
                    ratingBar.setRating(0);
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
                adapter.add(new Item(getString(R.string.customer_name), chatText.getText().toString(), getTodayDate(), ratingBar.getRating()));
                adapter.notifyDataSetChanged();
                setRatingAvg();
                setReviewCnt();
                // 입력 창 초기화
                chatText.setText("");
                ratingBar.setRating(0);
            }
        });

        // adapter에 묶여있는 item 변경 감지
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                list.setSelection(adapter.getCount() - 1);
                //bookmarkList.smoothScrollToPosition(adapter.getCount() -1);
            }
        });

        return root;
    }

    // 평균 별점 업데이트 함수
    public void setRatingAvg() {
        ratingsAvg.setRating(avg);
    }

    // 리뷰 개수 업데이트 함수
    public void setReviewCnt() {
        reviewCnt.setText(String.format("%d", cnt));
    }

    // 오늘 날짜 얻는 함수
    public String getTodayDate() {
        String date;
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR) - 2000;
        int mMonth = c.get(Calendar.MONTH) + 1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // 한 자리 수이면 앞에 0 붙이기 (ex. 20.07.05)
        if(mMonth<10 && mDay>=10)
            date = String.format("%d.0%d.%d", mYear, mMonth, mDay);
        else if(mMonth>=10 && mDay<10)
            date = String.format("%d.%d.0%d", mYear, mMonth, mDay);
        else if(mMonth<10 && mDay<10)
            date = String.format("%d.0%d.0%d", mYear, mMonth, mDay);
        else
            date = String.format("%d.%d.%d", mYear, mMonth, mDay);

        return date;
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
            add(new Item("김남준", "가격 안 올랐으면 좋겠어요... 몇 달 전보다 많이 오른 것 같네요.", "19.12.31", 1));
            add(new Item("정호석", "갈 때마다 자리도 널널하고 쾌적합니다.", "20.05.11", (float)2.5));
            add(new Item("민윤기", "딴 데보다 싼 거 같음 굿굿", "20.06.25", 5));
            add(new Item("김태형", "그냥 동네 아파트 주차장이에요.", "20.06.30", 3));
            add(new Item("김석진", "무난합니다~", "20.07.08", 4));
            add(new Item("전정국", "가격 싸고 괜찮네영", "20.07.16", (float) 4.5));
            add(new Item("박지민", "입차할 때 큐알코드 에러났는데 관리자가 계속 통화중이어서 10분 넘게 기다렸네요ㅋㅋ 후기 왜 좋은지 모르겠네", "20.08.01", 1));
            add(new Item("김도희", "주차장이 넓어서 좋아요!!", "20.08.05", 5));
            add(new Item("백혜원", "문제가 생겨서 관리자분께 연락해서 해결했습니다. 친절하고 좋았어요~~", "20.09.17", (float) 4.5));
            add(new Item("이정열", "깔끔하고 괜찮음. 근데 다른 곳보다 좀 비싼 것 같다;; 그래도 여기만 한 데가 없어서 거의 여기만 씀. 복현동 출장갈 때 강추", "20.11.23", 4));
            add(new Item("오주영", "낮 2시쯤 갔는데 자리 별로 없었어요...", "20.12.01", (float) 2.5));
        }

        // 리스트에 새 data 추가
        public void add(Item item) {
            //reviewList.add(item); // 아이템을 리스트 맨 뒤에 추가
            reviewList.add(0, item); // 아이템을 리스트 맨 앞에 추가

            // 평균 별점 계산
            sum += item.getRating();
            avg = sum/this.getCount();

            // 리뷰 개수
            cnt = getCount();
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

            // 회원 이름
            TextView user_names = (TextView)view.findViewById(R.id.reviewUserName);
            user_names.setText(item.getUser_name());

            // 후기 내용
            TextView texts = (TextView)view.findViewById(R.id.reviewText);
            texts.setText(item.getText());

            // 날짜
            TextView dates = (TextView)view.findViewById(R.id.reviewDate);
            dates.setText(item.getDate());

            // 별점
            RatingBar ratings = (RatingBar)view.findViewById(R.id.reviewRating);
            ratings.setRating(item.getRating());

            return view;
        }
    }
    // 리스트에 넣을 후기 아이템 클래스
    public class Item {
        private String user_name;
        private String text;
        private String date;
        private float rating;

        public Item (String user_name, String text, String date, float rating){
            this.user_name = user_name;
            this.text = text;
            this.date = date;
            this.rating = rating;
        }
        public String getUser_name() {
            return user_name;
        }
        public String getText() {
            return text;
        }
        public String getDate() {
            return date;
        }
        public float getRating() {
            return rating;
        }
    }
}
