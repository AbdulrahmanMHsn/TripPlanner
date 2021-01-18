package amo.tripplanner.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import amo.tripplanner.database.converter.ConverterDate;
import amo.tripplanner.database.converter.ConverterLocation;
import amo.tripplanner.database.converter.ConverterNote;
import amo.tripplanner.database.dao.TripDao;
import amo.tripplanner.pojo.Trip;
import timber.log.Timber;

@Database(entities = {Trip.class}, version = 3, exportSchema = false)
@TypeConverters({ConverterLocation.class, ConverterNote.class, ConverterDate.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "tripssss";
    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
                Timber.d("getInstance: Creating a new database instance");
                mInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Timber.d("getInstance: Getting the database instance, no need to recreated it.");
        return mInstance;
    }

    public abstract TripDao tripDao();

}
