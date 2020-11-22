package com.example.aparking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> datas = new ArrayList<String>();
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        datas.add(new String("If you have any problems using this application, please write a question here. We will check and send you a reply."));

        listView=findViewById(R.id.listview);
        et = findViewById(R.id.et);

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);


    }

    public void clicksend(View view)
    {
        String str = et.getText().toString();

        datas.add(str);

        adapter.notifyDataSetChanged();
        listView.setSelection(datas.size()-1);
        et.setText("");
    }
}