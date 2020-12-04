package com.example.aparking.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aparking.Menubar;
import com.example.aparking.R;
import com.example.aparking.ui.home.HomeFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BookmarkFragment extends Fragment {
    ListView list;
    List<Item> bookmarkList = new ArrayList<Item>();
    BookmarkAdapter adapter;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_bookmark, container, false);

        list = (ListView) root.findViewById(R.id.bookmarkList);
        adapter = new BookmarkAdapter(inflater.getContext(), R.layout.listitem_bookmark);
        list.setAdapter(adapter);

        return root;
    }

    public class BookmarkAdapter extends BaseAdapter {
        Context context;
        int layout;
        LayoutInflater inflater;

        public BookmarkAdapter(Context context, int layout) {
            this.context = context;
            this.layout = layout;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            initialize();
        }

        public void initialize(){
            add(new Item("복현동진아파트", "대구 북구 동북로50길 10 (복현동)", "398m", 1400, 47));
            add(new Item("뉴그랜드아파트", "대구 북구 경진로남1길 17-5 (복현동)", "409m", 1300, 198));
            add(new Item("동양아파트", "대구 북구 경진로남1길 20 (복현동)", "350m", 1900, 274));
            add(new Item( "석우로즈빌아파트", "대구 북구 경진로12길 6-14 (복현동)", "171m", 2500, 126));
            add(new Item("복현아이파크아파트", "대구 북구 경대로27길 40 (복현동)", "325m", 2800, 28));
            add(new Item("리치파크아파트", "대구 북구 경진로51 (복현동)",  "215m", 1600, 153));
        }
        public void add(Item item){
            bookmarkList.add(item);
        }

        @Override
        public int getCount() {
            return bookmarkList.size();
        }

        @Override
        public Item getItem(int i) {
            return bookmarkList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            if (view == null)
                view = inflater.inflate(layout, null);

            // 길게 클릭 시 즐겨찾기 삭제
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(inflater.getContext());
                    alertDialogBuilder.setTitle("즐겨찾기 삭제");
                    alertDialogBuilder.setMessage("즐겨찾기를 삭제하시겠습니까?");
                    alertDialogBuilder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(inflater.getContext(),"즐겨찾기가 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                            bookmarkList.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialogBuilder.show();

                    return false;
                }
            });
            // 아파트 이름
            TextView apt_names = (TextView) view.findViewById(R.id.apt_name);
            apt_names.setText(bookmarkList.get(position).getAptName());

            // 아파트 주소
            TextView apt_addresses = (TextView) view.findViewById(R.id.apt_address);
            apt_addresses.setText(bookmarkList.get(position).getAptAddr());

            // 현위치로부터의 거리
            TextView apt_distances = (TextView) view.findViewById(R.id.apt_distance);
            String str1 = "현위치로부터 ";
            String str2 = " 떨어짐";
            apt_distances.setText(str1 + bookmarkList.get(position).getDistance() + str2);

            String content = apt_distances.getText().toString();
            SpannableString spannableString = new SpannableString(content);

            int start = content.indexOf(bookmarkList.get(position).getDistance());
            int end = start + bookmarkList.get(position).getDistance().length();
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef5253")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            apt_distances.setText(spannableString);

            // 가격
            Button apt_prices = (Button) view.findViewById(R.id.apt_price);
            DecimalFormat formatter = new DecimalFormat("###,###");
            apt_prices.setText(formatter.format(bookmarkList.get(position).getPrice()) + "원");

            // 사용 가능 주차면
            Button apt_avails = (Button) view.findViewById(R.id.apt_avail);
            apt_avails.setText(formatter.format(bookmarkList.get(position).getAvailSpace()) + "대");

            // 100대 미만이면 붉게 표시
            if (bookmarkList.get(position).getAvailSpace() < 100)
                apt_avails.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ef5253")));

            return view;
        }
    }

    public class Item {
        private String aptName;
        private String aptAddr;
        private String distance;
        private int price;
        private int availSpace;

        public Item(String aptName, String aptAddr, String distance, int price, int availSpace) {
            this.aptName = aptName;
            this.aptAddr = aptAddr;
            this.distance = distance;
            this.price = price;
            this.availSpace = availSpace;
        }

        public String getAptName() {
            return aptName;
        }
        public String getAptAddr() {
            return aptAddr;
        }
        public String getDistance() {
            return distance;
        }
        public int getPrice() {
            return price;
        }
        public int getAvailSpace() {
            return availSpace;
        }
    }
}


