package com.bodms.controller;

import com.bodms.database.CampaignDAO;
import com.bodms.database.CharityDAO;
import com.bodms.model.Campaign;
import com.bodms.model.Charity;

import java.sql.SQLException;
import java.util.List;

public class CharityController {
    private final CharityDAO charityDAO = new CharityDAO();
    private final CampaignDAO campaignDAO = new CampaignDAO();

    public List<Charity> myCharities(int userId) { return charityDAO.findByUserId(userId); }

    public Charity addOrUpdateCharity(int userId, Charity c) throws SQLException {
        if (c.getId() == null) {
            c.setUserId(userId);
            return charityDAO.create(c);
        } else {
            charityDAO.update(c);
            return c;
        }
    }

    public Campaign addCampaign(int charityId, String title, String desc, String date) throws SQLException {
        Campaign c = new Campaign();
        c.setCharityId(charityId);
        c.setTitle(title);
        c.setDescription(desc);
        c.setDate(date);
        return campaignDAO.create(c);
    }

    public void deleteCampaign(int id) throws SQLException { campaignDAO.delete(id); }

    public List<Campaign> listCampaigns(int charityId) { return campaignDAO.listByCharity(charityId); }
}
