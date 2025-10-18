package com.bodms.database;

import com.bodms.model.Donor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {
    private static final Logger log = LoggerFactory.getLogger(DonorDAO.class);

    private Donor map(ResultSet rs) throws SQLException {
        Donor d = new Donor();
        d.setId(rs.getInt("id"));
        d.setUserId(rs.getInt("userId"));
        d.setName(rs.getString("name"));
        d.setBloodGroup(rs.getString("bloodGroup"));
        d.setOrgan(rs.getString("organ"));
        d.setLocation(rs.getString("location"));
        d.setContactInfo(rs.getString("contactInfo"));
        d.setAvailability(rs.getInt("availability") == 1);
        d.setApproved(rs.getInt("approved") == 1);
        return d;
    }

    public Donor create(Donor d) throws SQLException {
        String sql = "INSERT INTO donors (userId,name,bloodGroup,organ,location,contactInfo,availability,approved) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, d.getUserId());
            ps.setString(2, d.getName());
            ps.setString(3, d.getBloodGroup());
            ps.setString(4, d.getOrgan());
            ps.setString(5, d.getLocation());
            ps.setString(6, d.getContactInfo());
            ps.setInt(7, d.isAvailability() ? 1 : 0);
            ps.setInt(8, d.isApproved() ? 1 : 0);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) d.setId(keys.getInt(1));
            return d;
        }
    }

    public void update(Donor d) throws SQLException {
        String sql = "UPDATE donors SET name=?, bloodGroup=?, organ=?, location=?, contactInfo=?, availability=?, approved=? WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getBloodGroup());
            ps.setString(3, d.getOrgan());
            ps.setString(4, d.getLocation());
            ps.setString(5, d.getContactInfo());
            ps.setInt(6, d.isAvailability()?1:0);
            ps.setInt(7, d.isApproved()?1:0);
            ps.setInt(8, d.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM donors WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Donor> findAll() {
        List<Donor> list = new ArrayList<>();
        try (Statement st = Database.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM donors ORDER BY id DESC");
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("findAll donors failed", e);
        }
        return list;
    }

    public List<Donor> search(String bloodGroup, String organ, String location) {
        List<Donor> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT * FROM donors WHERE approved=1 AND availability=1");
        if (bloodGroup != null && !bloodGroup.isBlank()) sb.append(" AND bloodGroup = ?");
        if (organ != null && !organ.isBlank()) sb.append(" AND organ = ?");
        if (location != null && !location.isBlank()) sb.append(" AND location LIKE ?");
        sb.append(" ORDER BY id DESC");
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sb.toString())) {
            int idx = 1;
            if (bloodGroup != null && !bloodGroup.isBlank()) ps.setString(idx++, bloodGroup);
            if (organ != null && !organ.isBlank()) ps.setString(idx++, organ);
            if (location != null && !location.isBlank()) ps.setString(idx++, "%" + location + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("search donors failed", e);
        }
        return list;
    }

    public List<Donor> findByUserId(int userId) {
        List<Donor> list = new ArrayList<>();
        try (PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM donors WHERE userId=?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("findByUserId donors failed", e);
        }
        return list;
    }
}
