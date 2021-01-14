package amo.tripplanner.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    @ColumnInfo(name = "trip_timestamp")
    private long tripTimestamp;

    @ColumnInfo(name = "trip_status")
    private String tripStatus;

    @ColumnInfo(name = "trip_round")
    private boolean tripIsRound;

    @ColumnInfo(name = "trip_repeat")
    private String tripRepeat;

    @ColumnInfo(name = "trip_note")
    private List<Note> tripNotes;


    public Trip() {
    }


    @Ignore
    public Trip(String tripName, Location tripStartLocation, Location tripEndLocation, long tripTimestamp, String tripStatus, boolean tripIsRound, String tripRepeat) {
        this.tripName = tripName;
        this.tripStartLocation = tripStartLocation;
        this.tripEndLocation = tripEndLocation;
        this.tripTimestamp = tripTimestamp;
        this.tripStatus = tripStatus;
        this.tripIsRound = tripIsRound;
        this.tripRepeat = tripRepeat;
    }

    @Ignore
    public Trip(String tripName, Location tripStartLocation, Location tripEndLocation, long tripTimestamp, String tripStatus, boolean tripIsRound, String tripRepeat,List<Note> tripNotes) {
        this.tripName = tripName;
        this.tripStartLocation = tripStartLocation;
        this.tripEndLocation = tripEndLocation;
        this.tripTimestamp = tripTimestamp;
        this.tripStatus = tripStatus;
        this.tripIsRound = tripIsRound;
        this.tripRepeat = tripRepeat;
        this.tripNotes = tripNotes;
    }



    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public long getTripTimestamp() {
        return tripTimestamp;
    }

    public void setTripTimestamp(long tripTimestamp) {
        this.tripTimestamp = tripTimestamp;
    }

    public boolean isTripIsRound() {
        return tripIsRound;
    }

    public void setTripIsRound(boolean tripIsRound) {
        this.tripIsRound = tripIsRound;
    }

    public String getTripRepeat() {
        return tripRepeat;
    }

    public void setTripRepeat(String tripRepeat) {
        this.tripRepeat = tripRepeat;
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
                ", tripTimestamp=" + tripTimestamp +
                ", tripStatus='" + tripStatus + '\'' +
                ", tripIsRound=" + tripIsRound +
                ", tripRepeat='" + tripRepeat + '\'' +
                '}';
    }
}