package com.example.aparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReservationList extends AppCompatActivity {
    ListView list;

    String[] apt_name = {
            "복현동진아파트",
            "뉴그랜드아파트",
            "동양아파트",
            "석우로즈빌아파트",
            "복현아이파크아파트",
            "리치파크아파트"
    };

    String[] date = {
            "2020.09.22 13:00",
            "2020.08.22 14:00~14:30",
            "2020.08.22 14:00",
            "2020.08.20 11:00",
            "2020.07.20 11:00~12:30",
            "2020.07.19 15:00~16:00"
    };

    String[] state = {
            "예약완료",
            "주차완료",
            "예약취소됨",
            "예약취소됨",
            "주차완료",
            "주차완료"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        CustomList adapter = new CustomList(ReservationList.this);
        list = (ListView)findViewById(R.id.list_reservation);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),
                        apt_name[position],
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList(Activity context) {
            super(context, R.layout.listitem_reservation, apt_name);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.listitem_reservation, null, true);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.p_image);
            TextView apt_names = (TextView)rowView.findViewById(R.id.apt_name2);
            TextView reservation_date = (TextView)rowView.findViewById(R.id.reserve_date);
            TextView isReserved = (TextView)rowView.findViewById(R.id.isReserved);

            imageView.setImageResource(R.drawable.p);
            apt_names.setText(apt_name[position]);
            reservation_date.setText(date[position]);
            isReserved.setText(state[position]);

            return rowView;
        }
    }
}
