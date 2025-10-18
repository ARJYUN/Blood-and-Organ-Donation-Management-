package com.bodms.model;

public class Charity {
    private Integer id;
    private Integer userId;
    private String name;
    private String purpose;
    private String contactInfo;
    private double funds;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public double getFunds() { return funds; }
    public void setFunds(double funds) { this.funds = funds; }
}
