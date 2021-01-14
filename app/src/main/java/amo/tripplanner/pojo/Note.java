package amo.tripplanner.pojo;

public class Note {

    private String body;
//    private boolean isChecked;


    public Note(String body) {
        this.body = body;
    }


    public Note() {
    }


//    public Note(String body, boolean isChecked) {
//        this.body = body;
//        this.isChecked = isChecked;
//    }
//
//
//    public boolean isChecked() {
//        return isChecked;
//    }
//
//
//    public void setChecked(boolean checked) {
//        isChecked = checked;
//    }


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