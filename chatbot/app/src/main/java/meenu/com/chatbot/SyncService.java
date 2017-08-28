package meenu.com.chatbot;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import javax.inject.Inject;

import meenu.com.chatbot.models.MessageModel;
import meenu.com.chatbot.models.ServerCall;
import meenu.com.chatbot.models.ServerListener;
import meenu.com.chatbot.utils.Utils;
import retrofit2.Retrofit;

/**
 * Created by ${Meenu} on 28/8/17.
 */

public class SyncService extends IntentService implements ServerListener {

    @Inject
    Retrofit retrofit;
    private ServerCall serverCall;

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ((ChatApplication) ChatApplication.getContext()).getNetworkComponent().inject(this);
        ArrayList<MessageModel> messageModels = intent.getParcelableArrayListExtra("array");
        for (MessageModel messageModel : messageModels) {
            if (messageModel.getChatBotName() == null && !messageModel.isStatus()) {
                getCallObject().serverCall(retrofit, messageModel);
            }
        }


    }

    private ServerCall getCallObject() {
        if (serverCall == null) {
            serverCall = new ServerCall(this, this);
        }
        return serverCall;
    }

    @Override
    public void OnSuccess(MessageModel messageModel) {
        Intent i = new Intent();
        i.setAction(Utils.CustomFilter);
        sendBroadcast(i);
    }
}
