package com.example.aparking.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aparking.R;

public class BookmarkFragment extends Fragment {
    ListView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_bookmark, container, false);

        list = (ListView)root.findViewById(R.id.list);
        //BookmarkAdapter adapter = new BookmarkAdapter(inflater.getContext(),R.layout.listitem_bookmark);
        //list.setAdapter(adapter);

        return root;
    }
}
/*
class BookmarkAdapter extends BaseAdapter{
    Context context;
    int layout;
    LayoutInflater inflater;

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

    public BookmarkAdapter(Context context,int layout){
        this.context = context;
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return apt_avail.length;
    }

    @Override
    public Object getItem(int i) {
        return apt_name[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
       if(view == null)
           view = inflater.inflate(layout, null);

        TextView apt_names = (TextView)view.findViewById(R.id.apt_name);
        TextView apt_addresses = (TextView)view.findViewById(R.id.apt_address);
        TextView apt_distances = (TextView)view.findViewById(R.id.apt_distance);
        Button apt_prices = (Button)view.findViewById(R.id.apt_price);
        Button apt_avails = (Button)view.findViewById(R.id.apt_avail);

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


        return view;
    }
}

 */