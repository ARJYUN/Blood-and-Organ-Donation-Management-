package com.bodms.database;

import com.bodms.model.Charity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharityDAO {
    private static final Logger log = LoggerFactory.getLogger(CharityDAO.class);

    private Charity map(ResultSet rs) throws SQLException {
        Charity c = new Charity();
        c.setId(rs.getInt("id"));
        c.setUserId(rs.getInt("userId"));
        c.setName(rs.getString("name"));
        c.setPurpose(rs.getString("purpose"));
        c.setContactInfo(rs.getString("contactInfo"));
        c.setFunds(rs.getDouble("funds"));
        return c;
    }

    public Charity create(Charity c) throws SQLException {
        String sql = "INSERT INTO charities (userId,name,purpose,contactInfo,funds) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getUserId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getPurpose());
            ps.setString(4, c.getContactInfo());
            ps.setDouble(5, c.getFunds());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) c.setId(keys.getInt(1));
            return c;
        }
    }

    public void update(Charity c) throws SQLException {
        String sql = "UPDATE charities SET name=?, purpose=?, contactInfo=?, funds=? WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getPurpose());
            ps.setString(3, c.getContactInfo());
            ps.setDouble(4, c.getFunds());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM charities WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Charity> findAll() {
        List<Charity> list = new ArrayList<>();
        try (Statement st = Database.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM charities ORDER BY id DESC");
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("findAll charities failed", e);
        }
        return list;
    }

    public List<Charity> findByUserId(int userId) {
        List<Charity> list = new ArrayList<>();
        try (PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM charities WHERE userId=?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            log.error("findByUserId charities failed", e);
        }
        return list;
    }
}
