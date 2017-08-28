package meenu.com.chatbot.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import meenu.com.chatbot.R;
import meenu.com.chatbot.models.MessageModel;
import meenu.com.chatbot.viewholders.ChatViewHolder;

/**
 * Created by ${Meenu} on {4/8/16}.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {


    private ArrayList<MessageModel> mChatList;

    public ChatAdapter(ArrayList<MessageModel> mChatList) {
        this.mChatList = mChatList;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.setData(mChatList.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public void update(ArrayList<MessageModel> mChatList) {
        this.mChatList = mChatList;
        this.notifyDataSetChanged();
    }
}
