package art.coded.spotpost.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import art.coded.spotpost.model.entity.Event;

/**
 * A database for storing fetched entities and retrieving stored entities
 */
@Database(entities={Event.class}, version=1, exportSchema=false)
public abstract class EventRoomDatabase extends androidx.room.RoomDatabase {

    private static final String LOG_TAG = EventRoomDatabase.class.getSimpleName();

    public abstract EventDao dao();
    private static EventRoomDatabase INSTANCE;

    public static EventRoomDatabase getInstance(final Context context) {

        if (INSTANCE == null) {
            synchronized (EventRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, EventRoomDatabase.class, "event_database")
                            .build();
                }
            }
        } return INSTANCE;
    }
}