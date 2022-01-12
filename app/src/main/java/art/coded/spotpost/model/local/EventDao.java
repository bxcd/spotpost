package art.coded.spotpost.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import art.coded.spotpost.model.entity.Event;

/**
 * Database interactions required to store fetched entitys and retrieve stored entitys
 */
@Dao public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(Event event);
    @Query("SELECT * from event_table") LiveData<List<Event>> getAll();
}