package meenu.com.chatbot.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by ${Meenu} on 27/8/17.
 */

public class Utils {

    private static Utils instance;
    public static final String Base_Url = "https://www.personalityforge.com";
    public static final String apiKey = "6nt5d1nJHkqbkphe";
    public static final String chatBotId = "63906";
    public static final String externalId = "chirag1";
    public static final String CustomFilter = "refresh_filter";
    public static final String network_error = "Please check internet connection";

    private Utils() {
        //do nothing
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        try {
            networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (networkInfo != null && networkInfo.isAvailable()
                && networkInfo.isConnected()) {
            return true;
        }

        networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (networkInfo != null && networkInfo.isAvailable()
                && networkInfo.isConnected()) {
            return true;
        }
        //showCustomToast(context, network_error);
        return false;

    }

    public void showCustomToast(Context context, String message) {
        try {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
