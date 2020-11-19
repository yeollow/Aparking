package com.example.aparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.Locale;


public class MapPrice extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    View marker_root_view;
    TextView tv_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_price);

        setCustomMarkerView();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

//        tv_marker textView onClickListener등록 후 화면이동..
        tv_marker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                startActivity(intent);
            }
        });

       // btnSelectDate = (Button) findViewById(R.id.btnDate);
       // btnSelectTime = (Button) findViewById(R.id.btnTime);
    }

    private void setCustomMarkerView() {
        marker_root_view = LayoutInflater.from(this).inflate(R.layout.custom_marker, null);
        tv_marker = marker_root_view.findViewById(R.id.tv_marker);
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
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        int price1 = 3000;
        int price2 = 2000;
        int price3 = 1900;
        int price4 = 2500;


        tv_marker.setBackgroundResource(R.drawable.marker);
        tv_marker.setTextColor(Color.WHITE);


        // 1: 복현동진아파트 대구 북구 동북로50길 10 (복현동)
        String formatted1 = NumberFormat.getCurrencyInstance(Locale.KOREA).format(price1);
        tv_marker.setText(formatted1);

        LatLng adr1 = new LatLng(35.895073, 128.616657);
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(adr1);
        markerOptions1.title("복현동진아파트");
        markerOptions1.snippet("대구 북구 동북로50길 10 (복현동)");
        markerOptions1.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));
        mMap.addMarker(markerOptions1);

        // 2: 뉴그랜드아파트 대구 북구 경진로남1길 17-5 (복현동)
        String formatted2 = NumberFormat.getCurrencyInstance(Locale.KOREA).format(price2);
        tv_marker.setText(formatted2);

        LatLng adr2 = new LatLng(35.895456, 128.615767);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(adr2);
        markerOptions2.title("뉴그랜드아파트");
        markerOptions2.snippet("대구 북구 경진로남1길 17-5 (복현동)");
        markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));
        mMap.addMarker(markerOptions2);

        // 3: 동양아파트 대구 북구 경진로남1길 20 (복현동)
        String formatted3 = NumberFormat.getCurrencyInstance(Locale.KOREA).format(price3);
        tv_marker.setText(formatted3);

        LatLng adr3 = new LatLng(35.894874, 128.615608);
        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(adr3);
        markerOptions3.title("동양아파트");
        markerOptions3.snippet("대구 북구 경진로남1길 20 (복현동)");
        markerOptions3.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));
        mMap.addMarker(markerOptions3);

        // 4: 석우로즈빌아파트 대구 북구 경진로12길 6-14 (복현동)
        String formatted4 = NumberFormat.getCurrencyInstance(Locale.KOREA).format(price4);
        tv_marker.setText(formatted4);

        LatLng adr4 = new LatLng(35.893167, 128.616869);
        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(adr4);
        markerOptions4.title("석우로즈빌아파트");
        markerOptions4.snippet("대구 북구 경진로12길 6-14 (복현동)");
        markerOptions4.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));
        mMap.addMarker(markerOptions4);

        // 5: 복현아이파크아파트 대구 북구 경대로27길 40 (복현동)
        tv_marker.setText(formatted1);

        LatLng adr5 = new LatLng(35.894312, 128.617684);
        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(adr5);
        markerOptions5.title("복현아이파크아파트");
        markerOptions5.snippet("대구 북구 경대로27길 40 (복현동)");
        markerOptions5.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));
        mMap.addMarker(markerOptions5);

        // 6: 리치파크아파트 대구 북구 경진로51 (복현동)
        tv_marker.setText(formatted2);

        LatLng adr6 = new LatLng(35.893198, 128.617419);
        MarkerOptions markerOptions6 = new MarkerOptions();
        markerOptions6.position(adr6);
        markerOptions6.title("리치파크아파트");
        markerOptions6.snippet("대구 북구 경진로51 (복현동)");
        markerOptions6.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));
        mMap.addMarker(markerOptions6);

        // 기존에 사용하던 다음 2줄은 문제가 있습니다.
        // CameraUpdateFactory.zoomTo가 오동작하네요.
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(adr1, 17));
    }

    public void gotoBookmark(View v){
        Intent it = new Intent(this, Bookmark.class);
        startActivity(it);
    }

    public void gotoCheckqr(View v){
        Intent it = new Intent(this, Check_qr.class);
        startActivity(it);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_mypage:
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);

                break;

            case R.id.nav_reservationInfo:
                intent = new Intent(getApplicationContext(), ReservationList.class);
                startActivity(intent);

                break;
/*
            case R.id.nav_payInfo:
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);

                break;

            case R.id.nav_consultant:
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);

                break;
            */

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(getApplicationContext(), Map.class);
        startActivity(intent);

        return true ;
    }

}