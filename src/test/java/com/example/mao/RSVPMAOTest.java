package com.example.mao;

import com.bookclub.mao.RSVPMAO;
import com.bookclub.model.RSVP;
import com.bookclub.model.RSVP.RSVPStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RSVPMAOTest {

    private RSVPMAO rsvpMAO;

    @BeforeEach
    public void setUp() {
        rsvpMAO = new RSVPMAO();

        // Add some initial test data
        rsvpMAO.addRSVP(new RSVP(1, 101, 201, RSVPStatus.ACCEPTED));
        rsvpMAO.addRSVP(new RSVP(2, 102, 202, RSVPStatus.MAYBE));
        rsvpMAO.addRSVP(new RSVP(3, 101, 202, RSVPStatus.DECLINED));
        rsvpMAO.addRSVP(new RSVP(4, 102, 201, RSVPStatus.PENDING));
    }

    // Test addRSVP
    @Test
    public void testAddRSVP() {
        RSVP newRSVP = new RSVP(103, 203, RSVPStatus.ACCEPTED);
        boolean result = rsvpMAO.addRSVP(newRSVP);
        assertTrue(result);

        RSVP foundRSVP = rsvpMAO.findRSVPByEventAndUser(103, 203);
        assertNotNull(foundRSVP);
        assertEquals(RSVPStatus.ACCEPTED, foundRSVP.getStatus());
    }

    // Test findRSVPByEventAndUser
    @Test
    public void testFindRSVPByEventAndUserFound() {
        RSVP foundRSVP = rsvpMAO.findRSVPByEventAndUser(101, 201);
        assertNotNull(foundRSVP);
        assertEquals(RSVPStatus.ACCEPTED, foundRSVP.getStatus());
    }

    @Test
    public void testFindRSVPByEventAndUserNotFound() {
        RSVP foundRSVP = rsvpMAO.findRSVPByEventAndUser(999, 999);
        assertNull(foundRSVP);
    }

    // Test findRSVPsByEvent
    @Test
    public void testFindRSVPsByEvent() {
        List<RSVP> eventRSVPs = rsvpMAO.findRSVPsByEvent(101);
        assertNotNull(eventRSVPs);
        assertEquals(2, eventRSVPs.size()); // Event 101 has 2 RSVPs
    }

    @Test
    public void testFindRSVPsByEventNoResults() {
        List<RSVP> eventRSVPs = rsvpMAO.findRSVPsByEvent(999); // Non-existing event
        assertTrue(eventRSVPs.isEmpty());
    }

    // Test findRSVPsByUser
    @Test
    public void testFindRSVPsByUser() {
        List<RSVP> userRSVPs = rsvpMAO.findRSVPsByUser(201);
        assertNotNull(userRSVPs);
        assertEquals(2, userRSVPs.size()); // User 201 has 2 RSVPs
    }

    @Test
    public void testFindRSVPsByUserNoResults() {
        List<RSVP> userRSVPs = rsvpMAO.findRSVPsByUser(999); // Non-existing user
        assertTrue(userRSVPs.isEmpty());
    }

    // Test updateRSVP
    @Test
    public void testUpdateRSVP() {
        RSVP existingRSVP = rsvpMAO.findRSVPByEventAndUser(101, 201);
        RSVP updatedRSVP = new RSVP(101, 201, RSVPStatus.DECLINED); // Change status to DECLINED

        boolean result = rsvpMAO.updateRSVP(updatedRSVP);
        assertTrue(result);

        RSVP foundRSVP = rsvpMAO.findRSVPByEventAndUser(101, 201);
        assertNotNull(foundRSVP);
        assertEquals(RSVPStatus.DECLINED, foundRSVP.getStatus());
    }

    @Test
    public void testUpdateRSVPFailure() {
        RSVP nonExistentRSVP = new RSVP(999, 999, RSVPStatus.ACCEPTED); // Non-existing RSVP
        boolean result = rsvpMAO.updateRSVP(nonExistentRSVP);
        assertFalse(result);
    }

    // Test deleteRSVP
    @Test
    public void testDeleteRSVP() {
        RSVP existingRSVP = rsvpMAO.findRSVPByEventAndUser(101, 201);

        boolean result = rsvpMAO.deleteRSVP(existingRSVP);
        assertTrue(result);

        RSVP foundRSVP = rsvpMAO.findRSVPByEventAndUser(101, 201);
        assertNull(foundRSVP); // RSVP should be deleted
    }

    @Test
    public void testDeleteRSVPFailure() {
        RSVP nonExistentRSVP = new RSVP(999, 999, RSVPStatus.ACCEPTED); // Non-existing RSVP
        boolean result = rsvpMAO.deleteRSVP(nonExistentRSVP);
        assertFalse(result);
    }
}
