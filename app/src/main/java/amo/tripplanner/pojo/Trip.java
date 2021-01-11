package amo.tripplanner.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity(tableName = "trip_table")
public class Trip {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trip_id")
    private int tripId;

    @ColumnInfo(name = "trip_name")
    private String tripName;

    @ColumnInfo(name = "trip_start")
    private Location tripStartLocation;

    @ColumnInfo(name = "trip_end")
    private Location tripEndLocation;

    @ColumnInfo(name = "trip_date")
    private Date tripDate;

    @ColumnInfo(name = "trip_time")
    private Date tripTime;

    @ColumnInfo(name = "trip_status")
    private String tripStatus;

    @ColumnInfo(name = "trip_round")
    private String tripRound;

    @ColumnInfo(name = "trip_note")
    private List<Note> tripNotes;


    public Trip() {
    }

    @Ignore
    public Trip(String tripName, Location tripStartLocation, Location tripEndLocation) {
        this.tripName = tripName;
        this.tripStartLocation = tripStartLocation;
        this.tripEndLocation = tripEndLocation;
    }

    @Ignore
    public Trip(String tripName, Location tripStartLocation, Location tripEndLocation, List<Note> tripNotes) {
        this.tripName = tripName;
        this.tripStartLocation = tripStartLocation;
        this.tripEndLocation = tripEndLocation;
        this.tripNotes = tripNotes;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public Date getTripTime() {
        return tripTime;
    }

    public void setTripTime(Date tripTime) {
        this.tripTime = tripTime;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public String getTripRound() {
        return tripRound;
    }

    public void setTripRound(String tripRound) {
        this.tripRound = tripRound;
    }

    public List<Note> getTripNotes() {
        return tripNotes;
    }

    public void setTripNotes(List<Note> tripNotes) {
        this.tripNotes = tripNotes;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Location getTripStartLocation() {
        return tripStartLocation;
    }

    public void setTripStartLocation(Location tripStartLocation) {
        this.tripStartLocation = tripStartLocation;
    }

    public Location getTripEndLocation() {
        return tripEndLocation;
    }

    public void setTripEndLocation(Location tripEndLocation) {
        this.tripEndLocation = tripEndLocation;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", tripName='" + tripName + '\'' +
                ", tripStartLocation=" + tripStartLocation +
                ", tripEndLocation=" + tripEndLocation +
                ", tripNotes=" + tripNotes +
                '}';
    }
}