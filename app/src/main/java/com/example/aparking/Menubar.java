package com.example.aparking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.aparking.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

public class Menubar extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    //private DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference();
    //DatabaseReference qrcode = mDatabase.child("user_qrcode");
    // Map<String, Object> childupdate = new HashMap<>();
    // Map<String, Object> postValues = null;

    DrawerLayout drawer;
    //Parking_lot test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menubar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_bookmark,R.id.nav_payInfo, R.id.nav_mypage, R.id.nav_reservationInfo, R.id.nav_consultant, R.id.nav_review)
                .setDrawerLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    // 툴바 오른쪽 점 세 개짜리 메뉴
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawers();
        }
        else {
            super.onBackPressed();
        }
    }

    /* fragmnet 화면 전환 */
    public void replaceFragment(int i, Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment).commit();
    }

    /* fragment 화면 전환(홈 -> check_qr */
    public void replaceFragment2(String i, Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment).commit();
    }
    public void call(String url){
        Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
        startActivity(in);
    }

    //주차장 데이터 베이스 초기화
    /*
    @Override
    protected void onStart() {
        super.onStart();
        test = new Parking_lot("복현동진아파트","대구 북구 동북로50길 10 (복현동)",
                "010-1234-5678",35.895073,128.616657,1500, 20, 13);
        postValues = test.toMap();

        childupdate.put("/parking_lot/"+"lot1",postValues);
        mDatabase.updateChildren(childupdate);

        test = new Parking_lot("뉴그랜드아파트","대구 북구 경진로남1길 17-5 (복현동)",
                "010-0000-0000",35.895456,128.615767,1000, 15, 5);
        postValues = test.toMap();

        childupdate.put("/parking_lot/"+"lot2",postValues);
        mDatabase.updateChildren(childupdate);

        test = new Parking_lot("동양아파트","대구 북구 경진로남1길 20 (복현동)",
                "010-0000-0000",35.894874,128.615608,1000, 21, 12);
        postValues = test.toMap();

        childupdate.put("/parking_lot/"+"lot3",postValues);
        mDatabase.updateChildren(childupdate);

        test = new Parking_lot("석우로즈빌아파트","대구 북구 경진로12길 6-14 (복현동)",
                "010-0000-0000",35.893167,128.616869,1200, 10, 1);
        postValues = test.toMap();

        childupdate.put("/parking_lot/"+"lot4",postValues);
        mDatabase.updateChildren(childupdate);

        test = new Parking_lot("복현아이파크아파트","대구 북구 경대로27길 40 (복현동)",
                "010-0000-0000",35.894312,128.617684,1000, 22, 16);
        postValues = test.toMap();

        childupdate.put("/parking_lot/"+"lot5",postValues);
        mDatabase.updateChildren(childupdate);

        test = new Parking_lot("리치파크아파트","대구 북구 경진로51 (복현동)",
                "010-0000-0000",35.893198,128.617419,1000, 10, 10);
        postValues = test.toMap();

        childupdate.put("/parking_lot/"+"lot6",postValues);
        mDatabase.updateChildren(childupdate);
    }
 */
}