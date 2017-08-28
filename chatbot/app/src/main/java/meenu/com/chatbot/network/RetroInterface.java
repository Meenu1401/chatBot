package meenu.com.chatbot.network;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${Meenu} on 28/8/17.
 */

public interface RetroInterface {

    @GET("/api/chat/")
    Call<JsonObject> getCall(@Query("apiKey") String apikey, @Query("message") String message, @Query("chatBotID") String chatBotID, @Query("externalID") String externalID);
}
