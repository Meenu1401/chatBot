package meenu.com.chatbot.presenter;

import java.util.ArrayList;

import meenu.com.chatbot.models.MessageModel;

/**
 * Created by ${Meenu} on 27/8/17.
 */

public interface ChatView {

    void OnError(String message);

    void OnSaved(MessageModel messageModel);

    void onChatHistory(ArrayList<MessageModel> messageModels);

}
