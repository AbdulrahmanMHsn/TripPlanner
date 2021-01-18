package amo.tripplanner.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

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
    long idTrip;

    public long getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(long idTrip) {
        this.idTrip = idTrip;
    }

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

    public LiveData<Trip> getTripById(int tripId) {
        return tripDao.getTripById(tripId);
    }


    public long insert(final Trip trip) {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
//               tripDao.insertTrip(trip);
                idTrip = tripDao.insertTrip(trip);
                Log.i("RunRunRunRunRun", "run:1 " + idTrip);
            }
        });
        Log.i("RunRunRunRunRun", "run:2 " + idTrip);
        return idTrip;
    }

//    public long insert(final Trip trip) {
//        new InsertTripAsyncTask(tripDao).execute(trip);
//
//        return idTrip;
//    }


    public void update(final Trip trip) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tripDao.updateTrip(trip);
            }
        });
    }


    public void update(final int id, final List<Note> notes) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tripDao.updateTrip(id, notes);
            }
        });
    }


    public void deleteItemNote(final int id, final List<Note> notes) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tripDao.deleteItemNote(id, notes);
            }
        });
    }


    public void deleteItemNote(final Trip trip) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                tripDao.deleteItemNote(trip);
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

    private class InsertTripAsyncTask extends AsyncTask<Trip, Void, Long> {

        TripDao tripDao;

        public InsertTripAsyncTask(TripDao tripDao) {
            this.tripDao = tripDao;
        }

        @Override
        protected Long doInBackground(Trip... trips) {
//            long idTrip = tripDao.insertTrip(trips[0]);
//            trips[0].setTripId((int) idTrip);
            return tripDao.insertTrip(trips[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            idTrip = aLong;
        }
    }
}

