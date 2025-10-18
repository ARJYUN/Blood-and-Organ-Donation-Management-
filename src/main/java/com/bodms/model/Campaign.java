package com.bodms.model;

public class Campaign {
    private Integer id;
    private Integer charityId;
    private String title;
    private String description;
    private String date; // ISO string for simplicity

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCharityId() { return charityId; }
    public void setCharityId(Integer charityId) { this.charityId = charityId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
