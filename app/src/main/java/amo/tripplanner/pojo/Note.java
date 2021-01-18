package amo.tripplanner.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Note implements Serializable {

    private String body;
    private boolean isChecked;


    public Note(String body) {
        this.body = body;
    }


    public Note() {
    }


    public Note(String body, boolean isChecked) {
        this.body = body;
        this.isChecked = isChecked;
    }


    protected Note(Parcel in) {
        body = in.readString();
        isChecked = in.readByte() != 0;
    }


    public boolean isChecked() {
        return isChecked;
    }


    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public String getBody() {
        return body;
    }


    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "Note{" +
                "body='" + body + '\'' +
//                ", isChecked=" + isChecked +
                '}';
    }

}