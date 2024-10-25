package com.bookclub.iao;

import com.bookclub.model.RSVP;

import java.util.List;

/**
 * Interface for RSVP Access Object (IAO) that defines methods for managing RSVPs in the system.
 */
public interface IRSVPAO {
    /**
     * Retrieves an RSVP from the database
     * @param eventId The eventId of the RSVP to retrieve
     * @param userId The userId of the RSVP to retrieve
     * @return The RSVP with the given eventId and userId, or null if not found.
     */
    RSVP findRSVPByEventAndUser(int eventId, int userId);

    /**
     * Retrieves a list of RSVPs from the database
     * @param eventId The eventId of the RSVPs to retrieve
     * @return A list of RSVPs with the given eventId.
     */
    List<RSVP> findRSVPsByEvent(int eventId);

    /**
     * Retrieves a list of RSVPs from the database
     * @param userId The userId of the RSVPs to retrieve
     * @return A list of RSVPs with the given userId.
     */
    List<RSVP> findRSVPsByUser(int userId);

    /**
     * Adds a new RSVP to the database
     * @param rsvp The RSVP to add.
     * @return If operation succeeded
     */
    boolean addRSVP(RSVP rsvp);

    /**
     * Updates an existing RSVP in the database.
     * @param rsvp The RSVP with information to update.
     * @return If operation succeeded
     */
    boolean updateRSVP(RSVP rsvp);

    /**
     * Removes an RSVP from the database.
     * @param rsvp The RSVP to delete.
     * @return If operation succeeded
     */
    boolean deleteRSVP(RSVP rsvp);
}
