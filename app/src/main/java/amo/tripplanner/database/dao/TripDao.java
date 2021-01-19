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

//    @Query("SELECT * FROM trip_table")
//    LiveData<List<Trip>> getAllTrips();


    @Query("SELECT * FROM trip_table WHERE trip_status = 'Upcoming'")
    LiveData<List<Trip>> getAllTrips();


    @Query("SELECT * FROM trip_table WHERE trip_status != 'Upcoming'")
    LiveData<List<Trip>> getAllHistory();


    @Query("SELECT * FROM trip_table WHERE trip_id = :tripId")
    LiveData<Trip> getAllNotesById(int tripId);


    @Query("SELECT * FROM trip_table WHERE trip_id = :tripId")
    LiveData<Trip> getTripById(int tripId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTrip(Trip trip);


    @Query("UPDATE trip_table SET trip_status =:status WHERE trip_id = :id")
    void updateTrip(int id, String status);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTrip(Trip trip);


    @Query("UPDATE trip_table SET trip_note =:notes WHERE trip_id = :id")
    void updateTrip(int id, List<Note> notes);


    @Query("UPDATE trip_table SET trip_note =:notes WHERE trip_id = :id")
    void deleteItemNote(int id, List<Note> notes);

    @Delete
    void deleteItemNote(Trip trip);

    @Delete
    void deleteTrip(Trip trip);

//    @Delete
//    void deleteTrip(Note note);


    @Query("DELETE FROM trip_table")
    void deleteAll();
}
