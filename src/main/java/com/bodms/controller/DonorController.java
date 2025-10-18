package com.bodms.controller;

import com.bodms.database.DonorDAO;
import com.bodms.model.Donor;

import java.sql.SQLException;
import java.util.List;

public class DonorController {
    private final DonorDAO donorDAO = new DonorDAO();

    public List<Donor> myDonorProfiles(int userId) {
        return donorDAO.findByUserId(userId);
    }

    public Donor createOrUpdate(int userId, Donor d) throws SQLException {
        if (d.getId() == null) {
            d.setUserId(userId);
            return donorDAO.create(d);
        } else {
            donorDAO.update(d);
            return d;
        }
    }
}
