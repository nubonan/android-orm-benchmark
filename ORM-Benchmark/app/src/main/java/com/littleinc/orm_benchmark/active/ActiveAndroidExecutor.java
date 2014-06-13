package com.littleinc.orm_benchmark.active;

import android.content.Context;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.util.Util;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


import static com.littleinc.orm_benchmark.util.Util.getRandomString;


/**
 * Created by ada on 13.06.14.
 */
public enum ActiveAndroidExecutor implements BenchmarkExecutable {
    INSTANCE;

    @Override
    public int getProfilerId() {
        return 4;
    }

    @Override
    public String getOrmName() {
        return "ActiveAndroid";
    }

    @Override
    public void init(Context context, boolean useInMemoryDb) {
            ActiveAndroid.initialize(context);
    }

    @Override
    public long createDbStructure() throws SQLException {
        return -1;
    }

    @Override
    public long writeWholeData() throws SQLException {
        List<User> users = new LinkedList<User>();
        for (int i = 0; i < NUM_USER_INSERTS; i++) {
            User newUser = new User();
            newUser.setLastName(getRandomString(10));
            newUser.setFirstName(getRandomString(10));
            users.add(newUser);
        }

        List<Message> messages = new LinkedList<Message>();
        for (int i = 0; i < NUM_MESSAGE_INSERTS; i++) {
           Message newMessage = new Message();
            newMessage.setCommandId(i);
            newMessage.setSortedBy(System.nanoTime());
            newMessage.setContent(Util.getRandomString(100));
            newMessage.setClientId(System.currentTimeMillis());
            newMessage
                    .setSenderId(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage
                    .setChannelId(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.setCreatedAt((int) (System.currentTimeMillis() / 1000L));

            messages.add(newMessage);
        }

        long start = System.nanoTime();
        ActiveAndroid.beginTransaction();
    try{
        for (User user : users) {
            user.save();
        }
        Log.d(ActiveAndroid.class.getSimpleName(), "Done, wrote "
                + NUM_USER_INSERTS + " users");

        for (Message message : messages) {
           message.save();
        }
        Log.d(ActiveAndroid.class.getSimpleName(), "Done, wrote "
                + NUM_MESSAGE_INSERTS + " messages");
        ActiveAndroid.setTransactionSuccessful();
    } finally {
        ActiveAndroid.endTransaction();
    }
    return System.nanoTime() - start;
    }

    @Override
    public long readWholeData() throws SQLException {
        long start = System.nanoTime();
        Log.d(ActiveAndroid.class.getSimpleName(),
                "Read, " + new Select().all().from(Message.class).execute().size()
                        + " rows"
        );
        return System.nanoTime() - start;
    }

    @Override
    public long readIndexedField() throws SQLException {
        long start = System.nanoTime();

        Log.d(ActiveAndroid.class.getSimpleName(),
                "Read, "
                        + new Select()
                        .from(Message.class)
                        .where( "command_id=?",LOOK_BY_INDEXED_FIELD).execute()
                        .size() + " rows");
        return System.nanoTime() - start;
    }

    @Override
    public long readSearch() throws SQLException {
        long start = System.nanoTime();
        Log.d(ActiveAndroidExecutor.class.getSimpleName(),
                "Read, "  +
                       new Select().from(Message.class)
                        .limit((int) SEARCH_LIMIT)
                        .where("content LIKE  \'%"
                                + SEARCH_TERM + "%\'").execute().size()
                        + " rows");
        return System.nanoTime() - start;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();
        new Delete().from(User.class).execute();
        new Delete().from(Message.class).execute();
        return System.nanoTime() - start;
    }
}
