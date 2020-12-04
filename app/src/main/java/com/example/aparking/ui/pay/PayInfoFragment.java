package com.example.aparking.ui.pay;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aparking.R;

public class PayInfoFragment extends Fragment {
    ListView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_payinfo, container, false);
        list = (ListView)root.findViewById(R.id.list_payinfo);
        PayInfoAdapter adapter = new PayInfoAdapter(inflater.getContext(),R.layout.listitem_payinfo);
        list.setAdapter(adapter);

        return root;
    }

    public class PayInfoAdapter extends BaseAdapter {
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

        String[] date = {
                "2020.09.22 13:00",
                "2020.08.22 14:00~14:30",
                "2020.08.22 14:00",
                "2020.08.20 11:00",
                "2020.07.20 11:00~12:30",
                "2020.07.19 15:00~16:00"
        };

        String[] state = {
                "1500원",
                "900원",
                "3000원",
                "2500원",
                "1000원",
                "1000원"
        };

        public PayInfoAdapter( Context context, int layout) {
            this.context = context;
            this.layout = layout;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return apt_name.length;
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

            ImageView imageView = (ImageView) view.findViewById(R.id.p_image);
            TextView apt_names = (TextView)view.findViewById(R.id.apt_name2);
            TextView reservation_date = (TextView)view.findViewById(R.id.reserve_date);
            TextView price = (TextView)view.findViewById(R.id.price);

            imageView.setImageResource(R.drawable.p);
            apt_names.setText(apt_name[position]);
            reservation_date.setText(date[position]);
            price.setText(state[position]);

            return view;
        }
    }
}