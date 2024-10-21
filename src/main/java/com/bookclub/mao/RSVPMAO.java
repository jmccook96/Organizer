package com.bookclub.mao;

import com.bookclub.iao.IRSVPAO;
import com.bookclub.model.RSVP;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock Access Object (MAO) implementation for managing RSVP records.
 * This class provides methods to retrieve, add, update, and delete RSVP entries from an in-memory list.
 */
public class RSVPMAO implements IRSVPAO {

    private List<RSVP> rsvps;

    /**
     * Constructs a new RSVPMAO instance and initializes the list of RSVPs.
     */
    public RSVPMAO() {
        rsvps = new ArrayList<>();
    }

    /**
     * Finds an RSVP entry by event ID and user ID.
     * @param eventId The ID of the event.
     * @param userId The ID of the user.
     * @return The RSVP entry if found, otherwise null.
     */
    public RSVP findRSVPByEventAndUser(int eventId, int userId) {
        for (RSVP rsvp : rsvps) {
            if (rsvp.getEventId() == eventId && rsvp.getUserId() == userId) {
                return rsvp;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of RSVPs for a specific event.
     * @param eventId The ID of the event.
     * @return A list of RSVPs associated with the event.
     */
    public List<RSVP> findRSVPsByEvent(int eventId) {
        List<RSVP> eventRsvps = new ArrayList<>();
        for (RSVP rsvp : rsvps) {
            if (rsvp.getEventId() == eventId) {
                eventRsvps.add(rsvp);
            }
        }
        return eventRsvps;
    }

    /**
     * Retrieves a list of RSVPs made by a specific user.
     * @param userId The ID of the user.
     * @return A list of RSVPs associated with the user.
     */
    public List<RSVP> findRSVPsByUser(int userId) {
        List<RSVP> userRsvps = new ArrayList<>();
        for (RSVP rsvp : rsvps) {
            if (rsvp.getEventId() == userId) {
                userRsvps.add(rsvp);
            }
        }
        return userRsvps;
    }

    /**
     * Adds a new RSVP entry to the list.
     * @param rsvp The RSVP entry to be added.
     * @return True if the RSVP was added successfully, false otherwise.
     */
    public boolean addRSVP(RSVP rsvp) {
        return rsvps.add(rsvp);
    }

    /**
     * Updates an existing RSVP entry in the list.
     * @param rsvp The RSVP entry to update.
     * @return True if the RSVP was updated successfully, false otherwise.
     */
    public boolean updateRSVP(RSVP rsvp) {
        return rsvps.set(rsvps.indexOf(findRSVPByEventAndUser(rsvp.getEventId(), rsvp.getUserId())), rsvp) != null;
    }

    /**
     * Deletes an RSVP entry from the list.
     * @param rsvp The RSVP entry to delete.
     * @return True if the RSVP was deleted successfully, false otherwise.
     */
    public boolean deleteRSVP(RSVP rsvp) {
        return rsvps.remove(rsvp);
    }
}
