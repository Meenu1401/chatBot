package meenu.com.chatbot.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import meenu.com.chatbot.R;
import meenu.com.chatbot.adapters.ChatAdapter;
import meenu.com.chatbot.models.MessageModel;
import meenu.com.chatbot.presenter.ChatPresenterIMPL;
import meenu.com.chatbot.presenter.ChatView;
import meenu.com.chatbot.utils.Utils;

/**
 * Created by ${Meenu} on {4/8/16}.
 */

public class ChatActivity extends AppCompatActivity implements ChatView, View.OnClickListener {

    private ChatPresenterIMPL chatPresenterIMPL;
    private RecyclerView recyclerView;
    private EditText editText;
    private ChatAdapter mChatAdapter;
    private ImageView button;
    private ArrayList<MessageModel> messageModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageModels = new ArrayList<>();
        initViews();
        chatPresenterIMPL = new ChatPresenterIMPL(ChatActivity.this, this);

    }

    private void initViews() {
        button = findViewById(R.id.send_button);
        editText = findViewById(R.id.et_message);
        recyclerView = findViewById(R.id.chat_list);
        button.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(Utils.CustomFilter));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void OnError(String message) {
        button.setEnabled(true);
    }

    @Override
    public void OnSaved(MessageModel messageModel) {
        editText.getText().clear();
        button.setEnabled(true);
        messageModels.add(messageModel);
        setAdapter();
    }


    private void setAdapter() {
        if (mChatAdapter == null) {
            mChatAdapter = new ChatAdapter(messageModels);
            recyclerView.setAdapter(mChatAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mChatAdapter.update(messageModels);
        }
        recyclerView.scrollToPosition(messageModels.size() - 1);
    }

    @Override
    public void onChatHistory(ArrayList<MessageModel> messageModels) {
        this.messageModels.clear();
        this.messageModels.addAll(messageModels);
        setAdapter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_button:
                button.setEnabled(false);
                chatPresenterIMPL.sendMessage(editText.getText().toString().trim());
                editText.setText("");
                break;
            default:
                break;
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            chatPresenterIMPL.getChatHistory();
        }
    };
}
