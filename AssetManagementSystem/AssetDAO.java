import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Asset entity.
 * Handles all database operations related to Assets.
 */
public class AssetDAO {

    /**
     * Adds a new asset to the database.
     * @param asset The Asset object containing details to be added.
     * @return true if successful, false otherwise.
     */
    public boolean addAsset(Asset asset) {
        String query = "INSERT INTO assets (asset_name, asset_type, condition_status, assigned_to, added_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            
            pstmt.setString(1, asset.getAssetName());
            pstmt.setString(2, asset.getAssetType());
            pstmt.setString(3, asset.getCondition());
            pstmt.setString(4, asset.getAssignedTo());
            pstmt.setDate(5, asset.getAddedDate());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding asset: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all assets from the database.
     * @return List of Asset objects.
     */
    public List<Asset> getAllAssets() {
        List<Asset> assetList = new ArrayList<>();
        String query = "SELECT * FROM assets";
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            while (rs.next()) {
                Asset asset = new Asset(
                        rs.getInt("id"),
                        rs.getString("asset_name"),
                        rs.getString("asset_type"),
                        rs.getString("condition_status"),
                        rs.getString("assigned_to"),
                        rs.getDate("added_date")
                );
                assetList.add(asset);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching assets: " + e.getMessage());
            e.printStackTrace();
        }
        return assetList;
    }

    /**
     * Updates the condition of a specific asset by ID.
     * @param id The ID of the asset.
     * @param newCondition The new condition string (e.g., "Good", "Damaged").
     * @return true if successful, false otherwise.
     */
    public boolean updateCondition(int id, String newCondition) {
        String query = "UPDATE assets SET condition_status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
             
            pstmt.setString(1, newCondition);
            pstmt.setInt(2, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating condition: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an asset from the database by its ID.
     * @param id The ID of the asset to delete.
     * @return true if successful, false otherwise.
     */
    public boolean deleteAsset(int id) {
        String query = "DELETE FROM assets WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
             
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting asset: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Searches assets by matching the asset name (case-insensitive, partial match).
     * @param name The name or partial name to search for.
     * @return List of matched Asset objects.
     */
    public List<Asset> searchAssetsByName(String name) {
        List<Asset> assetList = new ArrayList<>();
        String query = "SELECT * FROM assets WHERE asset_name LIKE ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
             
            pstmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Asset asset = new Asset(
                            rs.getInt("id"),
                            rs.getString("asset_name"),
                            rs.getString("asset_type"),
                            rs.getString("condition_status"),
                            rs.getString("assigned_to"),
                            rs.getDate("added_date")
                    );
                    assetList.add(asset);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching assets: " + e.getMessage());
            e.printStackTrace();
        }
        return assetList;
    }

    /**
     * Exports the list of all assets to a CSV file.
     * @param filePath The path where the CSV file should be saved.
     * @return true if successful, false otherwise.
     */
    public boolean exportToCSV(String filePath) {
        List<Asset> assets = getAllAssets();
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.write("ID,Asset Name,Asset Type,Condition,Assigned To,Added Date\n");
            
            // Write data
            for (Asset asset : assets) {
                writer.write(String.format("%d,%s,%s,%s,%s,%s\n",
                        asset.getId(),
                        escapeCsv(asset.getAssetName()),
                        escapeCsv(asset.getAssetType()),
                        escapeCsv(asset.getCondition()),
                        escapeCsv(asset.getAssignedTo()),
                        asset.getAddedDate().toString()
                ));
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting to CSV: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to escape commas and quotes in CSV fields.
     */
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        return value;
    }
}
