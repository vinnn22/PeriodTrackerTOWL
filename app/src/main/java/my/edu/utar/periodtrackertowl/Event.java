package my.edu.utar.periodtrackertowl;

import java.util.Date;

public class Event {
    private Date date;
    private String event;
    private String Sexual_activity;
    private String Moods;
    private String Pain_rate;
    private String Flow;
    private String photoUrl;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(Event.class)
    }

    public Event(Date date,String Sexual_activity, String Moods,String Pain_rate,String Flow,String event,String photoUrl) {
        this.date = date;
        this.event = event;
        this.Sexual_activity = Sexual_activity;
        this.Moods = Moods;
        this.Pain_rate = Pain_rate;
        this.Flow = Flow;
        this.photoUrl = photoUrl;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSexual_activity() {
        return Sexual_activity;
    }

    public void setSexual_activity(String description) {
        this.Sexual_activity = description;
    }

    public void setMoods(String moods) {
        Moods = moods;
    }

    public String getMoods() {
        return Moods;
    }

    public String getPain_rate() {
        return Pain_rate;
    }

    public void setPain_rate(String pain_rate) {
        Pain_rate = pain_rate;
    }

    public String getFlow() {
        return Flow;
    }

    public void setFlow(String flow) {
        Flow = flow;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
