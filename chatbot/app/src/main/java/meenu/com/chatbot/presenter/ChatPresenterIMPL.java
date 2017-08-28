package meenu.com.chatbot.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import javax.inject.Inject;

import meenu.com.chatbot.ChatApplication;
import meenu.com.chatbot.SyncService;
import meenu.com.chatbot.dbmanager.DatabaseManager;
import meenu.com.chatbot.models.MessageModel;
import meenu.com.chatbot.models.ServerCall;
import meenu.com.chatbot.models.ServerListener;
import meenu.com.chatbot.utils.Utils;
import retrofit2.Retrofit;

/**
 * Created by ${Meenu} on 27/8/17.
 */

public class ChatPresenterIMPL implements ChatPresenter, ServerListener {

    private Context context;
    private ChatView chatView;
    @Inject
    Retrofit retrofit;
    private ServerCall serverCall;

    public ChatPresenterIMPL(Context context, ChatView chatView) {
        this.context = context;
        this.chatView = chatView;
        ((ChatApplication) ChatApplication.getContext()).getNetworkComponent().inject(this);
        getChatHistory();
        syncChats();
    }

    @Override
    public void sendMessage(String message) {
        if (message != null && message.length() > 0) {
            handleSave(message);
        } else {
            chatView.OnError("Please enter any message");
        }
    }

    public void getChatHistory() {
        ArrayList<MessageModel> messageModels = DatabaseManager.getInstance().getChatList(false);
        if (messageModels != null && messageModels.size() > 0) {
            chatView.onChatHistory(messageModels);
        }
    }

    private void handleSave(String message) {
        MessageModel messageModel = new MessageModel();
        messageModel.setExternalId(Utils.externalId);
        messageModel.setMessage(message);
        messageModel.setChatBotId(Utils.chatBotId);
        int id = DatabaseManager.getInstance().saveChat(messageModel);
        if (id > 0) {
            messageModel.setId(id);
            chatView.OnSaved(messageModel);
            getCallObject().serverCall(retrofit, messageModel);
        }

    }


    private void syncChats() {
        if (Utils.getInstance().checkInternetConnection(context)) {
            ArrayList<MessageModel> messageModels = DatabaseManager.getInstance().getChatList(true);
            if (messageModels != null && messageModels.size() > 0) {
                Intent intent = new Intent(context, SyncService.class);
                intent.putParcelableArrayListExtra("array", messageModels);
                context.startService(intent);
            }
        }
    }

    @Override
    public void OnSuccess(MessageModel messageModel) {
        chatView.OnSaved(messageModel);
    }

    private ServerCall getCallObject() {
        if (serverCall == null) {
            serverCall = new ServerCall(context, this);
        }
        return serverCall;
    }
}
