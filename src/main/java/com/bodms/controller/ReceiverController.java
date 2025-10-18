package com.bodms.controller;

import com.bodms.database.DonorDAO;
import com.bodms.database.HospitalDAO;
import com.bodms.database.ReceiverDAO;
import com.bodms.model.Donor;
import com.bodms.model.Hospital;
import com.bodms.model.Receiver;

import java.sql.SQLException;
import java.util.List;

public class ReceiverController {
    private final DonorDAO donorDAO = new DonorDAO();
    private final HospitalDAO hospitalDAO = new HospitalDAO();
    private final ReceiverDAO receiverDAO = new ReceiverDAO();

    public List<Donor> searchDonors(String bloodGroup, String organ, String location) {
        return donorDAO.search(bloodGroup, organ, location);
    }

    public List<Hospital> nearbyHospitals(String organType, String location) {
        return hospitalDAO.findNearby(organType, location);
    }

    public List<Receiver> myRequests(int userId) {
        return receiverDAO.findByUserId(userId);
    }

    public Receiver createOrUpdate(int userId, Receiver r) throws SQLException {
        if (r.getId() == null) {
            r.setUserId(userId);
            return receiverDAO.create(r);
        } else {
            receiverDAO.update(r);
            return r;
        }
    }
}
