package amo.tripplanner.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import amo.tripplanner.pojo.Note;
import amo.tripplanner.pojo.Trip;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trip_table")
    LiveData<List<Trip>> getAllTrips();

    @Query("SELECT * FROM trip_table WHERE trip_id = :tripId")
    LiveData<Trip> getAllNotesById(int tripId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrip(Trip trip);

    @Update
    void updateTrip(Trip trip);

    @Query("UPDATE trip_table SET trip_note =:notes WHERE trip_id = :id")
    void updateTrip(int id,List<Note> notes);

    @Delete
    void deleteTrip(Trip trip);

    @Query("DELETE FROM trip_table")
    void deleteAll();
}
