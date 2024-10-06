package com.bookclub.service;

import com.bookclub.dao.RSVPDAO;
import com.bookclub.dao.UserDAO;
import com.bookclub.iao.IRSVPAO;
import com.bookclub.iao.IUserAO;
import com.bookclub.model.Event;
import com.bookclub.model.RSVP;
import com.bookclub.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class RSVPService {
    private static RSVPService instance;
    private IRSVPAO rsvpAO;
    private IUserAO userAO;

    private RSVPService() {
        rsvpAO = new RSVPDAO();
        userAO = new UserDAO();
    }

    public static RSVPService getInstance() {
        if (instance == null) {
            instance = new RSVPService();
        }
        return instance;
    }

    public List<String> getEventRSVPUsernames(Event event) {
        // TODO: handle this more efficiently
        List<RSVP> rsvps = rsvpAO.findRSVPsByEvent(event.getId());
        return rsvps.stream().map(rsvp -> {
                    return userAO.findUserById(rsvp.getUserId()).getUsername();  // Return the username
                })
                .collect(Collectors.toList());
    }

    public RSVP getRSVP(Event event, User user) {
        return rsvpAO.findRSVPByEventAndUser(event.getId(), user.getId());
    }

    public void saveRSVP(Event event, User user, RSVP.RSVPStatus status) {
        RSVP rsvp = getRSVP(event, user);
        if (getRSVP(event, user) != null) {
            rsvp.setStatus(status);
            rsvpAO.updateRSVP(rsvp);
        }
        else {
            rsvpAO.addRSVP(new RSVP(event.getId(), user.getId(), status));
        }
    }
}
