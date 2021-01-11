package amo.tripplanner.pojo;

import android.widget.CheckBox;

import java.util.Date;

public class TripPojo {
    String tripName,startPoint,endPoint,date,time;
    CheckBox checkBox;

    public TripPojo() {
        //empty constructor needed
    }

    public TripPojo(String tripName, String startPoint) {
        this.tripName = tripName;
        this.startPoint = startPoint;
    }

    public TripPojo(String tripDate, String tripName, String time, String endPoint, String startPoint) {
        this.date = tripDate;
        this.tripName=tripName;
        this.endPoint=endPoint;
        this.startPoint=startPoint;
        this.time=time;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDate() {
        return date;
    }

    public void setTripDate(String tripDate) {
        this.date = tripDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
