package com.bookclub.dao;

import com.bookclub.iao.IRSVPAO;
import com.bookclub.model.RSVP;
import com.bookclub.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The RSVPDAO class implements the IRSVPAO interface and provides methods for interacting with the RSVP
 * entries in the database. It uses the DatabaseManager to handle SQL connections and operations.
 */
public class RSVPDAO implements IRSVPAO {

    private DatabaseManager dbManager;

    /**
     * Constructs an RSVPDAO instance and initializes the DatabaseManager.
     * It also creates the RSVPs table in the database if it does not exist.
     */
    public RSVPDAO() {
        dbManager = DatabaseManager.getInstance();
        createTable();
    }

    /**
     * Retrieves an RSVP entry by the event ID and user ID.
     *
     * @param eventId The ID of the event for which to find the RSVP.
     * @param userId  The ID of the user for whom to find the RSVP.
     * @return An RSVP object if found, or null if no RSVP exists for the given event and user IDs.
     */
    @Override
    public RSVP findRSVPByEventAndUser(int eventId, int userId) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM RSVPs WHERE eventId = ? AND userId = ?");
            statement.setInt(1, eventId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int rsvpEventId = resultSet.getInt("eventId");
                int rsvpUserId = resultSet.getInt("userId");
                RSVP.RSVPStatus status = RSVP.RSVPStatus.values()[resultSet.getInt("status")];
                RSVP rsvp = new RSVP(id, rsvpEventId, rsvpUserId, status);
                return rsvp;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all RSVP entries for a given event ID.
     *
     * @param eventId The ID of the event for which to find all RSVPs.
     * @return A list of RSVP objects associated with the specified event ID.
     */
    @Override
    public List<RSVP> findRSVPsByEvent(int eventId) {
        List<RSVP> rsvps = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM RSVPs WHERE eventId = ?");
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int rsvpEventId = resultSet.getInt("eventId");
                int userId = resultSet.getInt("userId");
                RSVP.RSVPStatus status = RSVP.RSVPStatus.values()[resultSet.getInt("status")];
                RSVP rsvp = new RSVP(id, rsvpEventId, userId, status);
                rsvps.add(rsvp);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rsvps;
    }

    /**
     * Retrieves all RSVP entries for a given user ID.
     *
     * @param userId The ID of the user for whom to find all RSVPs.
     * @return A list of RSVP objects associated with the specified user ID.
     */
    @Override
    public List<RSVP> findRSVPsByUser(int userId) {
        List<RSVP> rsvps = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM RSVPs WHERE userId = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int eventId = resultSet.getInt("eventId");
                int rsvpUserId = resultSet.getInt("userId");
                RSVP.RSVPStatus status = RSVP.RSVPStatus.values()[resultSet.getInt("status")];
                RSVP rsvp = new RSVP(id, eventId, rsvpUserId, status);
                rsvps.add(rsvp);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rsvps;
    }

    /**
     * Adds a new RSVP entry to the database.
     *
     * @param rsvp The RSVP object to add to the database.
     * @return True if the RSVP was added successfully, otherwise false.
     */
    @Override
    public boolean addRSVP(RSVP rsvp) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("INSERT INTO RSVPs (eventId, userId, status) VALUES (?, ?, ?)");
            statement.setInt(1, rsvp.getEventId());
            statement.setInt(2, rsvp.getUserId());
            statement.setInt(3, rsvp.getStatus().ordinal());
            statement.executeUpdate();

            // Set the id of the new RSVP
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                rsvp.setId(generatedKeys.getInt(1));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Updates an existing RSVP entry in the database (by rsvp id).
     *
     * @param rsvp The RSVP object containing updated information.
     * @return True if the RSVP was updated successfully, otherwise false.
     */
    @Override
    public boolean updateRSVP(RSVP rsvp) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("UPDATE RSVPs SET eventId = ?, userId = ?, status = ? WHERE id = ?");
            statement.setInt(1, rsvp.getEventId());
            statement.setInt(2, rsvp.getUserId());
            statement.setInt(3, rsvp.getStatus().ordinal());
            statement.setInt(4, rsvp.getId());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Deletes an RSVP entry from the database (by rsvp id).
     *
     * @param rsvp The RSVP object to delete from the database.
     * @return True if the RSVP was deleted successfully, otherwise false.
     */
    @Override
    public boolean deleteRSVP(RSVP rsvp) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("DELETE FROM RSVPs WHERE id = ?");
            statement.setInt(1, rsvp.getId());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Creates the RSVPs table in the database if it does not already exist.
     */
    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = dbManager.getConnection().createStatement();
            String query = "CREATE TABLE IF NOT EXISTS RSVPs ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "eventId INTEGER NOT NULL,"
                    + "userId INTEGER NOT NULL,"
                    + "status INTEGER NOT NULL,"
                    + "FOREIGN KEY (eventId) REFERENCES Events(eventId),"
                    + "FOREIGN KEY (userId) REFERENCES Users(Id)"
                    + ")";
            statement.execute(query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
