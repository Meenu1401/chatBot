package meenu.com.chatbot;

import android.app.Application;
import android.content.Context;

import meenu.com.chatbot.network.DaggerNetworkComponent;
import meenu.com.chatbot.network.NetworkComponent;
import meenu.com.chatbot.network.NetworkModel;
import meenu.com.chatbot.utils.Utils;

/**
 * Created by ${Meenu} on 27/8/17.
 */

public class ChatApplication extends Application {

    private NetworkComponent networkComponent;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        networkComponent = DaggerNetworkComponent.builder()
                .networkModel(new NetworkModel(Utils.Base_Url))
                .build();
    }

    public static Context getContext() {
        return context;
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
