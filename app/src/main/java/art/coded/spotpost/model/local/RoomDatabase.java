package art.coded.spotpost.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import art.coded.spotpost.model.entity.Event;

/**
 * A database for storing fetched Elements and retrieving stored Elements
 */
@Database(entities={Event.class}, version=1, exportSchema=false)
public abstract class RoomDatabase extends RoomDatabase {

    private static final String LOG_TAG = RoomDatabase.class.getSimpleName();

    public abstract Dao dao();
    private static RoomDatabase INSTANCE;

    public static RoomDatabase getInstance(final Context context) {

        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, RoomDatabase.class, "event_database")
                            .build();
                }
            }
        } return INSTANCE;
    }
}