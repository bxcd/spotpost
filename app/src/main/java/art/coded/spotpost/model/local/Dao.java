package art.coded.spotpost.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import art.coded.spotpost.model.entity.Event;

/**
 * Database interactions required to store fetched Elements and retrieve stored Elements
 */
@Dao public interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(Event event);
    @Query("SELECT * from element_table") LiveData<List<Event>> getAll();
}