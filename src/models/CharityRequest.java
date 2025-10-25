package models;

import java.sql.Timestamp;

/**
 * CharityRequest class demonstrating Encapsulation
 */
public class CharityRequest {
    private int requestId;
    private String title;
    private String description;
    private String requesterName;
    private String type;
    private double goalAmount;
    private double raisedAmount;
    private Timestamp createdDate;
    private String status;
    
    // Constructors
    public CharityRequest() {
    }
    
    public CharityRequest(String title, String description, String requesterName, 
                         String type, double goalAmount) {
        this.title = title;
        this.description = description;
        this.requesterName = requesterName;
        this.type = type;
        this.goalAmount = goalAmount;
        this.raisedAmount = 0.0;
        this.status = "ACTIVE";
    }
    
    public CharityRequest(int requestId, String title, String description, 
                         String requesterName, String type, double goalAmount, 
                         double raisedAmount, Timestamp createdDate, String status) {
        this.requestId = requestId;
        this.title = title;
        this.description = description;
        this.requesterName = requesterName;
        this.type = type;
        this.goalAmount = goalAmount;
        this.raisedAmount = raisedAmount;
        this.createdDate = createdDate;
        this.status = status;
    }
    
    // Getter and Setter methods
    public int getRequestId() {
        return requestId;
    }
    
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getRequesterName() {
        return requesterName;
    }
    
    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public double getGoalAmount() {
        return goalAmount;
    }
    
    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }
    
    public double getRaisedAmount() {
        return raisedAmount;
    }
    
    public void setRaisedAmount(double raisedAmount) {
        this.raisedAmount = raisedAmount;
    }
    
    public Timestamp getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Business logic methods
    public double getProgressPercentage() {
        if (goalAmount == 0) return 0;
        return (raisedAmount / goalAmount) * 100;
    }
    
    public double getRemainingAmount() {
        return goalAmount - raisedAmount;
    }
    
    public boolean isGoalReached() {
        return raisedAmount >= goalAmount;
    }
    
    @Override
    public String toString() {
        return "CharityRequest{" +
                "requestId=" + requestId +
                ", title='" + title + '\'' +
                ", requesterName='" + requesterName + '\'' +
                ", type='" + type + '\'' +
                ", goalAmount=" + goalAmount +
                ", raisedAmount=" + raisedAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
