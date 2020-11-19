package com.example.aparking.ui.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aparking.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    MapView mapFragment;
    Button btnSelectDate, btnSelectTime;
    public LayoutInflater parent;
    TimeSetListener timeSetListener = new TimeSetListener();
    DateSetListener dateSetListener = new DateSetListener();

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        parent = inflater;
        View root = inflater.inflate(R.layout.activity_map, container, false);

        mapFragment = root.findViewById(R.id.map);
        if (mapFragment != null) {
            mapFragment.onCreate(savedInstanceState);
        }
        mapFragment.getMapAsync(this);

        btnSelectDate = (Button) root.findViewById(R.id.btnDate);
        btnSelectTime = (Button) root.findViewById(R.id.btnTime);

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
        TextView reserve = root.findViewById(R.id.reserve);
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), Check_qr.class);
                //intent.putExtra("qrcode", "qrIntent");

                //startActivity(intent);
                Toast.makeText(inflater.getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);


        // 기존에 사용하던 다음 2줄은 문제가 있습니다.

        // CameraUpdateFactory.zoomTo가 오동작하네요.
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.895073, 128.616657), 17));
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


    class DateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            btnSelectDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }
    }


    class TimeSetListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            btnSelectTime.setText(hourOfDay + ":" + minute);
        }
    }
}

