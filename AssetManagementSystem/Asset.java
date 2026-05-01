import java.util.Date;

/**
 * Model class representing an Asset in the system.
 */
public class Asset {
    private int id;
    private String assetName;
    private String assetType;
    private String condition;
    private String assignedTo;
    private java.sql.Date addedDate; // use sql.Date for direct mapping to db

    // Default constructor
    public Asset() {
    }

    // Parameterized constructor
    public Asset(int id, String assetName, String assetType, String condition, String assignedTo, java.sql.Date addedDate) {
        this.id = id;
        this.assetName = assetName;
        this.assetType = assetType;
        this.condition = condition;
        this.assignedTo = assignedTo;
        this.addedDate = addedDate;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public java.sql.Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(java.sql.Date addedDate) {
        this.addedDate = addedDate;
    }
    
    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", assetName='" + assetName + '\'' +
                ", assetType='" + assetType + '\'' +
                ", condition='" + condition + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }
}
