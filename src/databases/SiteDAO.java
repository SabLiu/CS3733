package databases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import definitions.Id;
import definitions.Site;

public class SiteDAO extends DAO{
	public SiteDAO() {super();}
	public SiteDAO(java.sql.Connection conn) {super(conn);}
   
    /*
    public boolean updateConstant(Constant constant) throws Exception {
        try {
        	String query = "UPDATE constants SET value=? WHERE name=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, constant.value);
            ps.setString(2, constant.name);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }
    */
	
	public boolean deleteSite(Id siteId) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM SiteLibrary WHERE SiteID = ?;");
            ps.setString(1, siteId.getId());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to deleat segment: " + e.getMessage());
        }
    }
	/*
    public boolean deleteSite(Site site) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Library WHERE SegmentID = ?;");
            ps.setString(1, segment.getId().getId());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete segment: " + e.getMessage());
        }
    }
*/
    public boolean addSite(Site site) throws Exception {
        try {
        	
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM SiteLibrary  WHERE SiteID = ?;");
            ps.setString(1, site.getId().getId());
            ResultSet resultSet = ps.executeQuery();
           
            // already present?
            while (resultSet.next()) {              
                resultSet.close();
                return false;
            }
          
            ps = conn.prepareStatement("INSERT INTO SiteLibrary (SiteID,SiteURL) values(?,?);");
            ps.setString(1,  site.getId().getId());
            ps.setString(2,  site.getUrl());
            
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert segment: " + e.getMessage());
        }
    }

    /*
    public Segment getSegment(Id id) throws Exception {
        //TODO later update to also get remote segments
 
        try {
            Segment segment = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Library WHERE SegmentID=?;");
            ps.setString(1,  id.getId());
            
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                segment = generateSegment(resultSet);
            }
            
            resultSet.close();
            ps.close();
            
            if(segment == null) {
				throw new NullPointerException("segment not found");
			}
            
            return segment;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting segment: " + e.getMessage());
        }
        
    }
    */
    public List<Site> getAllSites() throws Exception {;
        List<Site> allSites = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM SiteLibrary";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Site s = generateSite(resultSet);
                allSites.add(s);
            }
            resultSet.close();
            statement.close();
            return allSites;

        } catch (Exception e) {
            throw new Exception("Failed in getting books: " + e.getMessage());
        }
    }
    
    private Site generateSite(ResultSet resultSet) throws Exception {
        String idStr  = resultSet.getString("SiteID");
        Id id = new Id(idStr);
        String URL = resultSet.getString("SiteURL");
        return new Site (id, URL);
    }

}
