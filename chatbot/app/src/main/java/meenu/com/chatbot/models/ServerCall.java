package meenu.com.chatbot.models;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import meenu.com.chatbot.dbmanager.DatabaseManager;
import meenu.com.chatbot.network.RetroInterface;
import meenu.com.chatbot.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ${Meenu} on 28/8/17.
 */

public class ServerCall {


    public Context context;
    public ServerListener serverListener;

    public ServerCall(Context context, ServerListener serverListener) {
        this.context = context;
        this.serverListener = serverListener;
    }


    public void serverCall(Retrofit retrofit, final MessageModel messageModel) {
        if (Utils.getInstance().checkInternetConnection(context)) {
            RetroInterface mService = retrofit.create(RetroInterface.class);
            Call<JsonObject> call = mService.getCall(Utils.apiKey, messageModel.getMessage(), messageModel.getChatBotId(), messageModel.getExternalId());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    parseData(response.body().toString(), messageModel);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }


    private void parseData(String response, MessageModel model) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optInt("success") == 1) {
                updateChatStatus(model);
                JSONObject messageObject = jsonObject.optJSONObject("message");
                if (messageObject != null) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    MessageModel messageModel = gson.fromJson(messageObject.toString(), MessageModel.class);
                    messageModel.setStatus(true);
                    int id_1 = DatabaseManager.getInstance().saveChat(messageModel);
                    if (id_1 > 0) {
                        messageModel.setId(id_1);
                        serverListener.OnSuccess(messageModel);
                    }
                }
            } else {
                Utils.getInstance().showCustomToast(context, jsonObject.optString("errorMessage"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateChatStatus(MessageModel messageModel) {
        if (DatabaseManager.getInstance().updateIndividualChat(messageModel.getId())) {
            messageModel.setStatus(true);
        }
    }

}
