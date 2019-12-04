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
	
	/**
	 * Deleats the site from the database
	 * @param siteId the id of the site to be deleted
	 * @return true if the delete was successful
	 * @throws Exception
	 */
	public boolean deleteSite(Id siteId) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM SiteLibrary WHERE SiteID = ?;");
            //set the ? to the site id
            ps.setString(1, siteId.getId());
            int numAffected = ps.executeUpdate();
            ps.close();
            //make sure only one was deleted
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to deleat segment: " + e.getMessage());
        }
    }
	
	/**
	 * Adds a site to the database
	 * @param site the site to add
	 * @return true if it is sucessful false otherwise
	 * @throws Exception
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
            //set the ?s to the correct values and execute
            ps = conn.prepareStatement("INSERT INTO SiteLibrary (SiteID,SiteURL) values(?,?);");
            ps.setString(1,  site.getId().getId());
            ps.setString(2,  site.getUrl());
            
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert segment: " + e.getMessage());
        }
    }

    /**
     * Returns all the sites in the data base
     * @return a list of sites that are in the database
     * @throws Exception
     */
    public List<Site> getAllSites() throws Exception {;
        List<Site> allSites = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM SiteLibrary";
            //get everything from the database
            ResultSet resultSet = statement.executeQuery(query);
            //make them and add them to the array
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
    
    /**
     * Generates a site from a database entry
     * @param resultSet the database entry
     * @return the site
     * @throws Exception
     */
    private Site generateSite(ResultSet resultSet) throws Exception {
        String idStr  = resultSet.getString("SiteID");
        Id id = new Id(idStr);
        String URL = resultSet.getString("SiteURL");
        return new Site (id, URL);
    }

}
