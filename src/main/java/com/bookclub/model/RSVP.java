package com.bookclub.model;

/**
 * Represents a response to an event invitation, indicating the user's attendance status.
 */
public class RSVP {
    /**
     * Enum representing the possible RSVP statuses.
     */
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

    /**
     * Constructs an RSVP object with the specified id, eventId, userId, and status.
     *
     * @param id      the unique identifier for the RSVP
     * @param eventId the unique identifier for the associated event
     * @param userId  the unique identifier for the user
     * @param status  the status of the RSVP
     */
    public RSVP(int id, int eventId, int userId, RSVPStatus status) {
        setId(id);
        setEventId(eventId);
        setUserId(userId);
        setStatus(status);
    }

    /**
     * Constructs an RSVP object with the specified eventId, userId, and status.
     * The id is not set and is typically used for new RSVPs.
     *
     * @param eventId the unique identifier for the associated event
     * @param userId  the unique identifier for the user
     * @param status  the status of the RSVP
     */
    public RSVP(int eventId, int userId, RSVPStatus status) {
        setEventId(eventId);
        setUserId(userId);
        setStatus(status);
    }

    /**
     * Gets the unique identifier for the RSVP.
     *
     * @return the unique identifier for the RSVP
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the RSVP.
     *
     * @param id the unique identifier for the RSVP
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the unique identifier for the associated event.
     *
     * @return the unique identifier for the event
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * Sets the unique identifier for the associated event.
     *
     * @param eventId the unique identifier for the event
     */
    public void setEventId(int eventId) {
        if (eventId <= 0) {
            throw new IllegalArgumentException("Event ID must be greater than zero.");
        }
        this.eventId = eventId;
    }

    /**
     * Gets the unique identifier for the user.
     *
     * @return the unique identifier for the user
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * Sets the unique identifier for the user.
     *
     * @param userId the unique identifier for the user
     */
    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be a valid number.");
        }
        this.userId = userId;
    }

    /**
     * Gets the status of the RSVP.
     *
     * @return the status of the RSVP
     */
    public RSVPStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the RSVP.
     *
     * @param status the status of the RSVP
     */
    public void setStatus(RSVPStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("RSVP status cannot be null.");
        }
        this.status = status;
    }
}
