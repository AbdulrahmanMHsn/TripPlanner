package amo.tripplanner.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import amo.tripplanner.pojo.Trip;
import amo.tripplanner.repository.TripRepository;

public class TripListViewModel extends AndroidViewModel {

    private TripRepository mRepository;
    private LiveData<List<Trip>> mAllTrips;

    public TripListViewModel(@NonNull Application context) {
        super(context);
        mRepository = TripRepository.getInstance(context);
        mAllTrips = mRepository.getAllTrips();
    }

    public LiveData<List<Trip>> getAllTrips() {
        return mAllTrips;
    }

    public void insert(Trip trip) {
        mRepository.insert(trip);
    }

    public void update(Trip trip) {
        mRepository.update(trip);
    }

    public void delete(Trip trip) {
        mRepository.delete(trip);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
