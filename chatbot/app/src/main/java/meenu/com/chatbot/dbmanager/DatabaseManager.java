package meenu.com.chatbot.dbmanager;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import meenu.com.chatbot.ChatApplication;
import meenu.com.chatbot.R;
import meenu.com.chatbot.database.ChatRow;
import meenu.com.chatbot.models.MessageModel;

/**
 * Created by ${Meenu} on {4/8/16}.
 */

public class DatabaseManager extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "chat_database.db";
    private static final int DB_VERSION = 1;
    private static volatile DatabaseManager mInstance;
    private Dao<ChatRow, Integer> chatDao = null;

    public DatabaseManager() {
        super(ChatApplication.getContext(), DB_NAME, null, DB_VERSION, R.raw.db_schema);
        try {
            getChatsDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() {
        if (mInstance == null) {
            synchronized (DatabaseManager.class) {
                if (mInstance == null) {
                    mInstance = new DatabaseManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, ChatRow.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Dao<ChatRow, Integer> getChatsDao() throws SQLException {
        if (chatDao == null) {
            chatDao = getDao(ChatRow.class);
        }
        return chatDao;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ChatRow.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int saveChat(MessageModel messageData) {
        ChatRow chatRow = new ChatRow();
        chatRow.setMessage(messageData.getMessage());
        chatRow.setChatBotName(messageData.getChatBotName());
        chatRow.setExternalId(messageData.getExternalId());
        chatRow.setStatus(messageData.isStatus());
        chatRow.setChatbotId(messageData.getChatBotId());
        try {
            chatDao.createOrUpdate(chatRow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatRow.getId();

    }

    public ArrayList<MessageModel> getChatList(boolean is_pending) {
        List<ChatRow> result;
        ArrayList<MessageModel> resultData = new ArrayList<>();
        try {
            if (is_pending) {
                result = getChatsDao().queryBuilder().where().eq(ChatRow.STATUS, false).query();
            } else {
                result = getChatsDao().queryBuilder().query();
            }
            for (int i = 0; i < result.size(); i++) {
                MessageModel messageModel = new MessageModel();
                messageModel.setId(result.get(i).getId());
                messageModel.setChatBotId(result.get(i).getChatbotId());
                messageModel.setExternalId(result.get(i).getExternalId());
                messageModel.setMessage(result.get(i).getMessage());
                messageModel.setChatBotName(result.get(i).getChatBotName());
                messageModel.setStatus(result.get(i).isStatus());
                resultData.add(messageModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return resultData;
    }

    public boolean updateIndividualChat(int id) {
        ChatRow chatRow;
        try {
            chatRow = getChatsDao().queryBuilder().where().eq(ChatRow.ID, id).queryForFirst();
            chatRow.setStatus(true);
            chatDao.createOrUpdate(chatRow);
            return chatRow.isStatus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
