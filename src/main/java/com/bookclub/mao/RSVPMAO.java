package com.bookclub.mao;

import com.bookclub.iao.IRSVPAO;
import com.bookclub.model.RSVP;
import java.util.ArrayList;
import java.util.List;

public class RSVPMAO implements IRSVPAO {

    private List<RSVP> rsvps;

    public RSVPMAO() {
        rsvps = new ArrayList<>();
    }

    public RSVP findRSVPByEventAndUser(int eventId, int userId) {
        for (RSVP rsvp : rsvps) {
            if (rsvp.getEventId() == eventId && rsvp.getUserId() == userId) {
                return rsvp;
            }
        }
        return null;
    }

    public List<RSVP> findRSVPsByEvent(int eventId) {
        List<RSVP> eventRsvps = new ArrayList<>();
        for (RSVP rsvp : rsvps) {
            if (rsvp.getEventId() == eventId) {
                eventRsvps.add(rsvp);
            }
        }
        return eventRsvps;
    }

    public List<RSVP> findRSVPsByUser(int userId) {
        List<RSVP> userRsvps = new ArrayList<>();
        for (RSVP rsvp : rsvps) {
            if (rsvp.getEventId() == userId) {
                userRsvps.add(rsvp);
            }
        }
        return userRsvps;
    }

    public boolean addRSVP(RSVP rsvp) {
        return rsvps.add(rsvp);
    }

    public boolean updateRSVP(RSVP rsvp) {
        return rsvps.set(rsvps.indexOf(findRSVPByEventAndUser(rsvp.getEventId(), rsvp.getUserId())), rsvp) != null;
    }

    public boolean deleteRSVP(RSVP rsvp) {
        return rsvps.remove(rsvp);
    }
}
