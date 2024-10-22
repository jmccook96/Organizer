package com.example.model;

import com.bookclub.model.RSVP;
import com.bookclub.model.RSVP.RSVPStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RSVPTest {

    private RSVP rsvp;

    @BeforeEach
    public void setUp() {
        rsvp = new RSVP(1, 100, 200, RSVPStatus.ACCEPTED);
    }

    // Basic functionality tests
    @Test
    public void testGetId() {
        assertEquals(1, rsvp.getId());
    }

    @Test
    public void testSetId() {
        rsvp.setId(2);
        assertEquals(2, rsvp.getId());
    }

    @Test
    public void testGetEventId() {
        assertEquals(100, rsvp.getEventId());
    }

    @Test
    public void testSetEventId() {
        rsvp.setEventId(101);
        assertEquals(101, rsvp.getEventId());
    }

    @Test
    public void testGetUserId() {
        assertEquals(200, rsvp.getUserId());
    }

    @Test
    public void testSetUserId() {
        rsvp.setUserId(201);
        assertEquals(201, rsvp.getUserId());
    }

    @Test
    public void testGetStatus() {
        assertEquals(RSVPStatus.ACCEPTED, rsvp.getStatus());
    }

    @Test
    public void testSetStatus() {
        rsvp.setStatus(RSVPStatus.DECLINED);
        assertEquals(RSVPStatus.DECLINED, rsvp.getStatus());
    }

    // Test for different RSVPStatus values
    @Test
    public void testRSVPStatusMaybe() {
        rsvp.setStatus(RSVPStatus.MAYBE);
        assertEquals(RSVPStatus.MAYBE, rsvp.getStatus());
    }

    @Test
    public void testRSVPStatusPending() {
        rsvp.setStatus(RSVPStatus.PENDING);
        assertEquals(RSVPStatus.PENDING, rsvp.getStatus());
    }

    // Constructor tests
    @Test
    public void testConstructorWithId() {
        RSVP rsvpWithId = new RSVP(2, 101, 201, RSVPStatus.PENDING);
        assertEquals(2, rsvpWithId.getId());
        assertEquals(101, rsvpWithId.getEventId());
        assertEquals(201, rsvpWithId.getUserId());
        assertEquals(RSVPStatus.PENDING, rsvpWithId.getStatus());
    }

    @Test
    public void testConstructorWithoutId() {
        RSVP rsvpWithoutId = new RSVP(102, 202, RSVPStatus.MAYBE);
        assertEquals(102, rsvpWithoutId.getEventId());
        assertEquals(202, rsvpWithoutId.getUserId());
        assertEquals(RSVPStatus.MAYBE, rsvpWithoutId.getStatus());
    }

    // Edge case tests

    @Test
    public void testSetStatusNull() {
        assertThrows(IllegalArgumentException.class, () -> rsvp.setStatus(null));
    }

    @Test
    public void testConstructorInvalidStatusNull() {
        assertThrows(IllegalArgumentException.class, () -> new RSVP(1, 100, 200, null));
    }

    @Test
    public void testSetEventIdNegative() {
        assertThrows(IllegalArgumentException.class, () -> rsvp.setEventId(-1));
    }

    @Test
    public void testSetUserIdNegative() {
        assertThrows(IllegalArgumentException.class, () -> rsvp.setUserId(-1));
    }
}
