package com.bodms.controller;

import com.bodms.database.*;
import com.bodms.model.*;

import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private final UserDAO userDAO = new UserDAO();
    private final DonorDAO donorDAO = new DonorDAO();
    private final ReceiverDAO receiverDAO = new ReceiverDAO();
    private final CharityDAO charityDAO = new CharityDAO();
    private final HospitalDAO hospitalDAO = new HospitalDAO();

    public List<User> listUsers() { return userDAO.findAll(); }
    public void deleteUser(int id) throws SQLException { userDAO.delete(id); }

    public List<Donor> listDonors() { return donorDAO.findAll(); }
    public void approveDonor(int id, boolean approved) throws SQLException {
        for (Donor d : donorDAO.findAll()) if (d.getId()==id) { d.setApproved(approved); donorDAO.update(d); }
    }
    public void deleteDonor(int id) throws SQLException { donorDAO.delete(id); }

    public List<Receiver> listReceivers() { return receiverDAO.findAll(); }
    public void approveReceiver(int id, boolean approved) throws SQLException {
        for (Receiver r : receiverDAO.findAll()) if (r.getId()==id) { r.setApproved(approved); receiverDAO.update(r); }
    }
    public void deleteReceiver(int id) throws SQLException { receiverDAO.delete(id); }

    public List<Charity> listCharities() { return charityDAO.findAll(); }
    public void deleteCharity(int id) throws SQLException { charityDAO.delete(id); }

    public List<Hospital> listHospitals() { return hospitalDAO.findAll(); }
    public Hospital addHospital(Hospital h) throws SQLException { return hospitalDAO.create(h); }
    public void updateHospital(Hospital h) throws SQLException { hospitalDAO.update(h); }
    public void deleteHospital(int id) throws SQLException { hospitalDAO.delete(id); }
}
