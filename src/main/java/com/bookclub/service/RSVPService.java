package com.bookclub.service;

import com.bookclub.iao.IRSVPAO;
import com.bookclub.iao.IUserAO;
import com.bookclub.model.Event;
import com.bookclub.model.RSVP;
import com.bookclub.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing RSVP actions related to events and users.
 * Provides functionalities to retrieve and save RSVP information.
 */
public class RSVPService {
    private static RSVPService instance;
    private IRSVPAO rsvpAO;
    private IUserAO userAO;

    /**
     * Gets the singleton instance of RSVPService.
     * @return The singleton instance of RSVPService.
     * @throws IllegalStateException if RSVPService is not initialized.
     */
    public static RSVPService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("RSVPService is not initialized. Call initialize() first.");
        }
        return instance;
    }

    /**
     * Initializes the RSVPService with the necessary data access objects.
     * Required to be called before operations.
     * @param rsvpAO The data access object for RSVP operations.
     * @param userAO The data access object for User operations.
     */
    public static void initialize(IRSVPAO rsvpAO, IUserAO userAO) {
        if (instance == null) {
            instance = new RSVPService();
        }
        instance.rsvpAO = rsvpAO;
        instance.userAO = userAO;
    }

    /**
     * Retrieves a list of usernames of users who have RSVP'd to a given event.
     * @param event The event for which to retrieve the RSVP usernames.
     * @return A list of usernames of users who have RSVP'd to the event.
     */
    public List<String> getEventRSVPUsernames(Event event) {
        // TODO: handle this more efficiently
        List<RSVP> rsvps = rsvpAO.findRSVPsByEvent(event.getId());
        return rsvps.stream().map(rsvp -> {
                    return userAO.findUserById(rsvp.getUserId()).getUsername();  // Return the username
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the RSVP for a specific event and user.
     * @param event The event to check for the user's RSVP.
     * @param user The user whose RSVP is being retrieved.
     * @return The RSVP object if found, or null if not found.
     */
    public RSVP getRSVP(Event event, User user) {
        return rsvpAO.findRSVPByEventAndUser(event.getId(), user.getId());
    }

    /**
     * Saves the RSVP status for a user related to a specific event.
     * If the user has already RSVP'd, their status is updated; otherwise, a new RSVP is created.
     * @param event The event for which the user is RSVP'ing.
     * @param user The user who is RSVP'ing.
     * @param status The RSVP status (ACCEPTED, MAYBE, DECLINED, PENDING).
     */
    public void saveRSVP(Event event, User user, RSVP.RSVPStatus status) {
        RSVP rsvp = getRSVP(event, user);
        if (rsvp != null) {
            rsvp.setStatus(status);
            rsvpAO.updateRSVP(rsvp);
        }
        else {
            rsvpAO.addRSVP(new RSVP(event.getId(), user.getId(), status));
        }
    }
}
