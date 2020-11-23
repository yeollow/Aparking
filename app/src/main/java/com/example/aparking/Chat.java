package com.example.aparking;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> datas = new ArrayList<String>();
    EditText et;
    private EditText chatText;
    View row;
   ChatArrayAdapter chatArrayAdapter;
    private boolean side = false;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_chat);

        buttonSend = (Button) findViewById(R.id.buttonSend);

        listView = (ListView) findViewById(R.id.listview);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.et);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    private boolean sendChatMessage(){
        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
        chatText.setText("");
        side = !side;
        return true;
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

class ChatArrayAdapter extends ArrayAdapter {

    private TextView chatText;
    private List chatMessageList = new ArrayList();
    private LinearLayout singleMessageContainer;


    public void add(@Nullable ChatMessage object) {
        super.add(object);
        chatMessageList.add(object);
    }


    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return (ChatMessage) this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.activity_chat, parent, true);


        }
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        ChatMessage chatMessageObj = getItem(position);
        chatText = (TextView) row.findViewById(R.id.singleMessage);
        chatText.setText(chatMessageObj.message);
        chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.bubble_a : R.drawable.bubble_b);
        singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
        return row;


    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}


class ChatMessage {
    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
}