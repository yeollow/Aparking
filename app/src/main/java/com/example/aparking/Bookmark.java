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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Bookmark extends AppCompatActivity {
    ListView list;

    String[] apt_name = {
            "복현동진아파트",
            "뉴그랜드아파트",
            "동양아파트",
            "석우로즈빌아파트",
            "복현아이파크아파트",
            "리치파크아파트"
    };

    String[] apt_address = {
            "대구 북구 동북로50길 10 (복현동)",
            "대구 북구 경진로남1길 17-5 (복현동)",
            "대구 북구 경진로남1길 20 (복현동)",
            "대구 북구 경진로12길 6-14 (복현동)",
            "대구 북구 경대로27길 40 (복현동)",
            "대구 북구 경진로51 (복현동)"
    };

    String apt_distance_text1 = "현위치로부터 ";
    String[] apt_distance_text2 = {
            "398m",
            "409m",
            "350m",
            "171m",
            "325m",
            "215m"
    };
    String apt_distance_text3 = " 떨어짐";

    String[] apt_price = {
            "1,900원",
            "3,000원",
            "2,000원",
            "2,500원",
            "3,000원",
            "2,900원"
    };

    int[] apt_avail = {
            254, 198, 47, 126, 28, 153
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        Intent it = getIntent();

        CustomList adapter = new CustomList(Bookmark.this);
        list = (ListView)findViewById(R.id.list);
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
            super(context, R.layout.listitem_bookmark, apt_name);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.listitem_bookmark, null, true);

            TextView apt_names = (TextView)rowView.findViewById(R.id.apt_name);
            TextView apt_addresses = (TextView)rowView.findViewById(R.id.apt_address);
            TextView apt_distances = (TextView)rowView.findViewById(R.id.apt_distance);
            Button apt_prices = (Button)rowView.findViewById(R.id.apt_price);
            Button apt_avails = (Button)rowView.findViewById(R.id.apt_avail);

            apt_names.setText(apt_name[position]);
            apt_addresses.setText(apt_address[position]);
            apt_distances.setText(apt_distance_text1+apt_distance_text2[position]+apt_distance_text3);

            String content = apt_distances.getText().toString();
            SpannableString spannableString = new SpannableString(content);

            int start = content.indexOf(apt_distance_text2[position]);
            int end = start + apt_distance_text2[position].length();
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef5253")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            apt_distances.setText(spannableString);

            apt_prices.setText(apt_price[position]);
            apt_avails.setText(apt_avail[position]+"대");

            // 100대 미만이면 붉게 표시
            if(apt_avail[position] < 100)
                apt_avails.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ef5253")));

            return rowView;
        }
    }
}
