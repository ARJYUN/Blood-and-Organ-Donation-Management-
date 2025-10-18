package com.bodms.database;

import com.bodms.model.Campaign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampaignDAO {
    private static final Logger log = LoggerFactory.getLogger(CampaignDAO.class);

    private Campaign map(ResultSet rs) throws SQLException {
        Campaign c = new Campaign();
        c.setId(rs.getInt("id"));
        c.setCharityId(rs.getInt("charityId"));
        c.setTitle(rs.getString("title"));
        c.setDescription(rs.getString("description"));
        c.setDate(rs.getString("date"));
        return c;
    }

    public Campaign create(Campaign c) throws SQLException {
        String sql = "INSERT INTO campaigns (charityId,title,description,date) VALUES (?,?,?,?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getCharityId());
            ps.setString(2, c.getTitle());
            ps.setString(3, c.getDescription());
            ps.setString(4, c.getDate());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) c.setId(keys.getInt(1));
            return c;
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM campaigns WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Campaign> listByCharity(int charityId) {
        List<Campaign> list = new ArrayList<>();
        String sql = "SELECT * FROM campaigns WHERE charityId=? ORDER BY id DESC";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setInt(1, charityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("listByCharity failed", e);
        }
        return list;
    }
}
