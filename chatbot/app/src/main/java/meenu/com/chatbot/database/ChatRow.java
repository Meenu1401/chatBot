package meenu.com.chatbot.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ${Meenu} on {4/8/16}.
 */

@DatabaseTable(tableName = "message_table")
public class ChatRow {
    public static final String ID = "id";
    public static final String CHATBOTID = "chat_botid";
    public static final String MESSAGE = "message";
    public static final String EXTERNALID = "externalId";
    public static final String CHATBOTNAME = "chat_botname";
    public static final String STATUS = "status";


    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true, columnName = ID)
    private int id;


    @DatabaseField(columnName = CHATBOTID)
    private String chatbotId;

    @DatabaseField(columnName = EXTERNALID)
    private String externalId;

    @DatabaseField(columnName = CHATBOTNAME)
    private String chatBotName;


    @DatabaseField(columnName = MESSAGE)
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @DatabaseField(columnName = STATUS)
    private boolean status = false;


    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getChatbotId() {
        return chatbotId;
    }

    public void setChatbotId(String chatbotId) {
        this.chatbotId = chatbotId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getChatBotName() {
        return chatBotName;
    }

    public void setChatBotName(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
