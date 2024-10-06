package com.bookclub.model;

public class RSVP {
    public enum RSVPStatus {
        ACCEPTED,
        MAYBE,
        DECLINED,
        PENDING
    }

    private int id;
    private int eventId;
    private int userId;
    private RSVPStatus status;

    public RSVP(int id, int eventId, int userId, RSVPStatus status) {
        setId(id);
        setEventId(eventId);
        setUserId(userId);
        setStatus(status);
    }

    public RSVP(int eventId, int userId, RSVPStatus status) {
        setEventId(eventId);
        setUserId(userId);
        setStatus(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public RSVPStatus getStatus() {
        return status;
    }

    public void setStatus(RSVPStatus status) {
        this.status = status;
    }
}
