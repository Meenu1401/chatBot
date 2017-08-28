package meenu.com.chatbot.models;

import android.os.Parcel;
import android.os.Parcelable;

import meenu.com.chatbot.utils.Utils;

/**
 * Created by ${Meenu} on {4/8/16}.
 */

public class MessageModel implements Parcelable {

    private String message;
    private String chatBotId;
    private String chatBotName;
    private String externalId;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatBotName() {
        return chatBotName;
    }

    public void setChatBotName(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    public String getChatBotId() {
        return chatBotId;
    }

    public void setChatBotId(String chatBotId) {
        this.chatBotId = chatBotId;
    }


    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public MessageModel() {

    }


    public static final Creator<MessageModel> CREATOR = new Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel in) {
            return new MessageModel(in);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(message);
        parcel.writeString(chatBotId);
        parcel.writeString(chatBotName);
        parcel.writeString(Utils.externalId);

    }

    protected MessageModel(Parcel in) {
        id = in.readInt();
        message = in.readString();
        chatBotId = in.readString();
        chatBotName = in.readString();
        externalId = in.readString();
    }
}
