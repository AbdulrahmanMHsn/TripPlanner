package amo.tripplanner.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import amo.tripplanner.database.AppDatabase;
import amo.tripplanner.database.dao.TripDao;
import amo.tripplanner.pojo.Note;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.utilities.AppExecutors;


public class TripRepository {
    private static final Object LOCK = new Object();
    private static TripRepository mInstance;
    private final TripDao tripDao;
    private LiveData<List<Trip>> listTrips;

    private TripRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        tripDao = database.tripDao();
        listTrips = tripDao.getAllTrips();
    }

    public static TripRepository getInstance(Application application) {

        if (mInstance == null) {
            synchronized (LOCK) {
                mInstance = new TripRepository(application);
            }
        }

        return mInstance;
    }


    public LiveData<List<Trip>> getAllTrips() {
        return listTrips;
    }


    public LiveData<Trip> getAllNotesById(int tripId) {
        return tripDao.getAllNotesById(tripId);
    }


    public void insert(final Trip trip) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tripDao.insertTrip(trip);
            }
        });
    }

    public void update(final Trip trip) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tripDao.updateTrip(trip);
            }
        });
    }


    public void update(final int id,final List<Note> notes) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tripDao.updateTrip(id,notes);
            }
        });
    }

    public void delete(final Trip trip) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tripDao.deleteTrip(trip);
            }
        });
    }

    public void deleteAll() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tripDao.deleteAll();
            }
        });
    }


}

