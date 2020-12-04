package com.example.aparking.ui.home;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.aparking.Menubar;
import com.example.aparking.Parking_lot;
import com.example.aparking.R;
import com.example.aparking.ui.BookmarkFragment;
import com.example.aparking.ui.Check_qr;
import com.example.aparking.ui.ReviewFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    LayoutInflater parent;
    private GoogleMap mMap;
    MapView mapFragment;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference qrcode_storage = mDatabase.child("user_qr"); //유저에 대한 큐알코드 스트링 저장소

    View marker_root_view; // 커스텀 마커
    TextView tv_marker; // 커스텀 마커 위에 띄우는 가격
    View sliding; // 슬라이딩 윈도우
    boolean isUp; // 슬라이드 업/다운 판단
    List<Apartment> aptList = new ArrayList<Apartment>(); // 아파트 객체 리스트 (주차장 관련 모든 정보 여기 있음)
    TextView aptName, aptAddr, totalSpace, availSpace; // 슬라이딩 윈도우에 띄울 텍스트들
    Button bookmarkBtn, reviewBtn, naviBtn, shareBtn; // 슬라이딩 창 아이콘들
    Button btnSelectDate, btnSelectTime; // 예약일, 예약시간 버튼
    View btnBookmark, btnQRcode; // floating buttons (즐겨찾기, 예약확인)
    String qrcodeString; //qrcode대한 랜덤 스트링
    int reser_index; //예약하기 누른 아파트 인덱스
    String qrcode;
    String name, addr, tel;

    Intent intent;

    private final String packageName = "com.nhn.android.nmap";

    public static HomeFragment newinstance() {
        return new HomeFragment();
    }

    TimeSetListener timeSetListener = new TimeSetListener();
    DateSetListener dateSetListener = new DateSetListener();

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        parent = inflater;
        View root = inflater.inflate(R.layout.activity_map, container, false);

        aptInitialize(); // 아파트 객체들 생성

        // 마커
        marker_root_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_marker, null);
        tv_marker = marker_root_view.findViewById(R.id.tv_marker);

        // 지도
        mapFragment = root.findViewById(R.id.map);
        if (mapFragment != null) {
            mapFragment.onCreate(savedInstanceState);
        }
        mapFragment.getMapAsync(this);

        // 날짜 선택 버튼 클릭
        btnSelectDate = (Button) root.findViewById(R.id.btnDate);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(inflater.getContext(),
                        dateSetListener, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // 시간 선택 버튼 클릭
        btnSelectTime = (Button) root.findViewById(R.id.btnTime);
        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(parent.getContext(),
                        timeSetListener, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        //예약하기 버튼 클릭
        TextView reserve = root.findViewById(R.id.reserve);
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = aptList.get(reser_index).getAptName();
                addr = aptList.get(reser_index).aptAddr;
                tel = "010-1111-1111";
                qrcodeString = getRandomString();
                qrcode_storage.setValue(qrcodeString);
                Fragment fragment = new Check_qr();
                Bundle bundle = new Bundle(4);
                bundle.putString("apart_name", name);
                bundle.putString("apart_addr", addr);
                bundle.putString("apart_tel", tel);
                bundle.putString("qrcode",qrcodeString);
                fragment.setArguments(bundle);
                ((Menubar) getActivity()).replaceFragment(R.layout.activity_check_qr, fragment);
                //Toast.makeText(inflater.getContext(), name, Toast.LENGTH_SHORT).show();
                Toast.makeText(inflater.getContext(), name + " 주차장을 예약했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // sliding window 처음엔 안 보이게
        sliding = (FrameLayout) root.findViewById(R.id.sliding);
        sliding.setVisibility(View.INVISIBLE);
        isUp = false;

        // sliding 창의 Textviews
        aptName = sliding.findViewById(R.id.slidingAptName);
        aptAddr = sliding.findViewById(R.id.slidingAptAddr);
        totalSpace = sliding.findViewById(R.id.slidingTotalParking);
        availSpace = sliding.findViewById(R.id.slidingAvailParking);

        // 즐겨찾기 floating 버튼 클릭
        btnBookmark = (LinearLayout) root.findViewById(R.id.btnBookmark);
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BookmarkFragment();
                ((Menubar) getActivity()).replaceFragment(R.layout.activity_bookmark, fragment);
            }
        });

        // 예약확인 floating 버튼 클릭
        btnQRcode = (LinearLayout) root.findViewById(R.id.btnQRcode);
        btnQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = Check_qr.newinstance();
                if(qrcodeString == "null")
                    qrcodeString = null;
                else{
                    Bundle bundle = new Bundle(4);
                    bundle.putString("apart_name", name);
                    bundle.putString("apart_addr", addr);
                    bundle.putString("apart_tel", tel);
                    bundle.putString("qrcode",qrcodeString);
                    fragment.setArguments(bundle);
                }
                ((Menubar) getActivity()).replaceFragment(R.layout.activity_check_qr, fragment);
            }
        });

        // 슬라이딩 창 아이콘
        bookmarkBtn = (Button) root.findViewById(R.id.slidingBookmartBtn);
        reviewBtn = (Button) root.findViewById(R.id.slidingReviewBtn);
        naviBtn = (Button) root.findViewById(R.id.slidingNaviBtn);
        shareBtn = (Button) root.findViewById(R.id.slidingShareBtn);

        // 즐겨찾기 아이콘 클릭
        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBookmark();
            }
        });

        // 리뷰 아이콘 클릭
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ReviewFragment();
                ((Menubar) getActivity()).replaceFragment(R.layout.fragment_review, fragment);
            }
        });

        // 네비게이션 아이콘 클릭
        naviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(parent.getContext(), "준비 중인 서비스입니다.", Toast.LENGTH_SHORT).show();
//                fragement를 얻어  activity(Map??)에 접근 -> activity에서 네이버 지도 앱 실행
                intent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
                startActivity(intent);
            }
        });

        // 공유 아이콘 클릭
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        return root;
    }

    // 즐겨찾기 클릭 시 불리는 함수
    public void updateBookmark() {
        final Drawable drawable = (Drawable) ContextCompat.getDrawable(parent.getContext(), R.drawable.sliding_star_customizing);
        String text = bookmarkBtn.getText().toString();
        int num = Integer.parseInt(text);

        if (bookmarkBtn.getCurrentTextColor() == Color.WHITE) {
            // 별 노랗게
            drawable.setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.MULTIPLY);
            bookmarkBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            // 숫자에 1 더하기
            num += 1;
            bookmarkBtn.setText(Integer.toString(num));
            // 숫자 노랗게
            bookmarkBtn.setTextColor(getResources().getColor(R.color.colorYellow));
        } else {
            // 별 하얗게
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            bookmarkBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            // 숫자에 1 빼기
            num -= 1;
            bookmarkBtn.setText(Integer.toString(num));
            // 숫자 하얗게
            bookmarkBtn.setTextColor(Color.WHITE);
        }
    }

    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        //((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        tv_marker.setBackgroundResource(R.drawable.marker);
        tv_marker.setTextColor(Color.WHITE);

        for (int i = 0; i < 6; i++) {
            String price = NumberFormat.getCurrencyInstance(Locale.KOREA).format(aptList.get(i).getAptPrice());
            tv_marker.setText(price);
            LatLng aptAddr = new LatLng(aptList.get(i).getLatitude(), aptList.get(i).getLongitude());
            MarkerOptions marker = new MarkerOptions();
            marker.position(aptAddr);
            marker.title(aptList.get(i).getAptName());
            marker.snippet(aptList.get(i).getAptAddr());
            marker.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(parent.getContext(), marker_root_view)));
            mMap.addMarker(marker);
        }

        mMap.setOnMarkerClickListener(this); // 마커 클릭 시 슬라이딩 윈도우 뜸
        mMap.setOnMapClickListener(this);
        LatLng aptAddr = new LatLng(aptList.get(0).getLatitude(), aptList.get(0).getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aptAddr, 17));
    }

    // 여기부터 Lifecycle 함수들 implement.
    // 이거 없으니까 fragment로 바꿨을 때 지도 안 뜸
    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    public void onPause() {
        mapFragment.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapFragment.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }
    // Lifecycle 함수 끝


    // 지도의 마커 누르면 슬라이딩 창 뜸뜸
    @Override
    public boolean onMarkerClick(Marker marker) {
        // 아파트 이름, 주소 setText
        String temp;
        temp = marker.getSnippet();
        aptAddr.setText(temp);
        temp = marker.getTitle();
        aptName.setText(temp);

        // 어느 아파트 객체인지 리스트에서 찾기
        int index;
        for (index = 0; index < 6; index++) {
            if (temp.equals(aptList.get(index).getAptName()))
                break;
        }
        reser_index = index;

        // 총 주차면, 사용 가능 주차면, 즐겨찾기 수, 리뷰 수 setText
        DecimalFormat formatter = new DecimalFormat("###,###");
        totalSpace.setText(formatter.format(aptList.get(index).getTotalSpace()));
        availSpace.setText(formatter.format(aptList.get(index).getAvailSpace()));
        bookmarkBtn.setText(Integer.toString(aptList.get(index).getBookmarkCnt()));
        reviewBtn.setText(Integer.toString(aptList.get(index).getReviewCnt()));

        // 즐겨찾기 아이콘 하얗게 초기화
        if (bookmarkBtn.getCurrentTextColor() == getResources().getColor(R.color.colorYellow))
            updateBookmark();

        if (!isUp) {
            slideUp(sliding);
            isUp = !isUp;
        }

        // return false로 바꾸면 아래 두 문장 필요 X
        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);
        return true; // return false로 바꾸면 기존 스니펫도 보임
    }

    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", view.getHeight(), 0);
        animator.setDuration(500);
        animator.start();
    }

    public void slideDown(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0, view.getHeight());
        animator.setDuration(500);
        animator.start();
    }

    // 지도의 마커 아닌 곳을 누르면 슬라이딩 창 닫힘
    @Override
    public void onMapClick(LatLng latLng) {
        if (isUp) {
            slideDown(sliding);
            isUp = !isUp;
        }
    }

    public class DateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            btnSelectDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        }
    }

    public class TimeSetListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (hourOfDay < 13)
                btnSelectTime.setText(hourOfDay + ":" + minute + " A.M.");
            else
                btnSelectTime.setText(hourOfDay - 12 + ":" + minute + " P.M.");
        }
    }

    // 아파트 클래스
    public class Apartment {
        private String aptName;
        private String aptAddr;
        private int aptPrice;
        private int totalSpace;
        private int availSpace;
        private int bookmarkCnt;
        private int reviewCnt;
        double latitude;
        double longitude;

        public Apartment(String aptName, String aptAddr, int aptPrice, int totalSpace, int availSpace, int bookmarkCnt, int reviewCnt, double latitude, double longitude) {
            this.aptName = aptName;
            this.aptAddr = aptAddr;
            this.aptPrice = aptPrice;
            this.totalSpace = totalSpace;
            this.availSpace = availSpace;
            this.bookmarkCnt = bookmarkCnt;
            this.reviewCnt = reviewCnt;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getAptName() {
            return aptName;
        }

        public String getAptAddr() {
            return aptAddr;
        }

        public int getAptPrice() {
            return aptPrice;
        }

        public int getTotalSpace() {
            return totalSpace;
        }

        public int getAvailSpace() {
            return availSpace;
        }

        public int getBookmarkCnt() {
            return bookmarkCnt;
        }

        public int getReviewCnt() {
            return reviewCnt;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    // 아파트 객체들 생성
    public void aptInitialize() {
        this.add(new Apartment("복현동진아파트", "대구 북구 동북로50길 10 (복현동)", 1400,
                1900, 47, 236, 11, 35.895073, 128.616657));
        this.add(new Apartment("뉴그랜드아파트", "대구 북구 경진로남1길 17-5 (복현동)", 1300,
                1800, 198, 106, 13, 35.895456, 128.615767));
        this.add(new Apartment("동양아파트", "대구 북구 경진로남1길 20 (복현동)", 1900,
                3500, 274, 371, 27, 35.894874, 128.615608));
        this.add(new Apartment("석우로즈빌아파트", "대구 북구 경진로12길 6-14 (복현동)", 2500,
                2400, 126, 105, 16, 35.893167, 128.616869));
        this.add(new Apartment("복현아이파크아파트", "대구 북구 경대로27길 40 (복현동)", 2800,
                1500, 28, 113, 31, 35.894312, 128.617684));
        this.add(new Apartment("리치파크아파트", "대구 북구 경진로51 (복현동)", 1600,
                2300, 153, 295, 18, 35.893198, 128.617419));
    }

    // 아파트 객체 리스트에 추가
    public void add(Apartment apt) {
        aptList.add(apt);
    }

    private static String getRandomString() {
        int length = 20;
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();

        String chars[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        for (int i = 0; i < length; i++) {
            buffer.append(chars[random.nextInt(chars.length)]);
        }
        return buffer.toString();
    }

    @Override
    public void onStart() {
        super.onStart();
        db_read();
    }

    public void db_read(){
        qrcode_storage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                qrcode = snapshot.getValue().toString();
                if(qrcode == "null"){
                    qrcodeString = null;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

