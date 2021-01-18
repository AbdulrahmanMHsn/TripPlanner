package amo.tripplanner.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import amo.tripplanner.pojo.Note;
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

    public LiveData<Trip> getNoteById(int tripId) {
        return mRepository.getAllNotesById(tripId);
    }

    public LiveData<Trip> getTripById(int tripId) {
        return mRepository.getTripById(tripId);
    }

    public void insert(Trip trip) {
       mRepository.insert(trip);
    }

    public long getId(){
       return mRepository.getIdTrip();
    }

    public void update(Trip trip) {
        mRepository.update(trip);
    }

    public void update(int id,List<Note> notes) {
        mRepository.update(id,notes);
    }

    public void deleteItemNote(int id,List<Note> notes) {
        mRepository.deleteItemNote(id,notes);
    }

    public void deleteItemNote(Trip trip) {
        mRepository.deleteItemNote(trip);
    }

    public void delete(Trip trip) {
        mRepository.delete(trip);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
