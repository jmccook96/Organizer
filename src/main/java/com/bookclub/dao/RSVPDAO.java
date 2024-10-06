package com.bookclub.dao;

import com.bookclub.iao.IRSVPAO;
import com.bookclub.model.RSVP;
import com.bookclub.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RSVPDAO implements IRSVPAO {

    private DatabaseManager dbManager;

    public RSVPDAO() {
        dbManager = DatabaseManager.getInstance();
        createTable();
    }

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
