package amo.tripplanner.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import amo.tripplanner.pojo.Trip;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trip_table")
    List<Trip> getAllTrips();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrip(Trip trip);

    @Update
    void updateTrip(Trip trip);

    @Delete
    void deleteTrip(Trip trip);

    @Query("DELETE FROM trip_table")
    void deleteAll();
}
