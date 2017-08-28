package meenu.com.chatbot.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import meenu.com.chatbot.R;
import meenu.com.chatbot.models.MessageModel;

/**
 * Created by ${Meenu} on {4/8/16}.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder {

    private TextView user;
    private TextView doctor;
    private TextView status;

    public ChatViewHolder(View itemView) {
        super(itemView);
        initItemViews(itemView);
    }

    private void initItemViews(View itemView) {
        user = itemView.findViewById(R.id.user);
        doctor = itemView.findViewById(R.id.doctor);
        status = itemView.findViewById(R.id.status);
    }

    public void setData(MessageModel messageData) {
        if (messageData.isStatus()) status.setText(R.string.sent);
        else status.setText(R.string.sending);
        if (messageData.getChatBotName() != null && messageData.getChatBotName().length() > 0) {
            doctor.setVisibility(View.VISIBLE);
            doctor.setText(messageData.getMessage());
            user.setVisibility(View.INVISIBLE);
            status.setVisibility(View.GONE);
        } else {
            user.setVisibility(View.VISIBLE);
            user.setText(messageData.getMessage());
            doctor.setVisibility(View.INVISIBLE);
            status.setVisibility(View.VISIBLE);
        }


    }


}


