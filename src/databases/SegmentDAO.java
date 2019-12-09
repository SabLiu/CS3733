package databases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import definitions.Id;
import definitions.Playlist;
import definitions.Segment;




public class SegmentDAO extends DAO{
	
	public SegmentDAO() {super();}
	public SegmentDAO(java.sql.Connection conn) {super(conn);}
   
	/**
	 * Marks the segment public or private based on input
	 * @param segmentId the segment to make public or private
	 * @param mark true if it wants to be public false if it wants to be private
	 * @return true if it was successful false otherwise
	 * @throws Exception
	 */
	public boolean markSegment(Id segmentId, boolean mark) throws Exception {
		 try {
			 //get the segment and update the isSegmentPublic colum
	        	String query = "UPDATE Library SET isSegmentPublic=? WHERE SegmentID=?;";
	        	PreparedStatement ps = conn.prepareStatement(query);  
	            ps.setBoolean(1, mark);
	            ps.setString(2, segmentId.getId());
	            int numAffected = ps.executeUpdate();
	            ps.close();
	            //make sure only one is changed 
	            return (numAffected == 1); 
	        } catch (Exception e) { 
	            throw new Exception("Failed to update Segments: " + e.getMessage());
	        }
	}
	
	
	/**
	 * Finds all the segments that were spoken by the specified character that are in the database
	 * @param str the characters name
	 * @return a list of segments that were spoken by that character
	 * @throws Exception
	 */
	public List<Segment> searchSegmentCharacter(String str) throws Exception {
        //TODO later update to also get remote segments
		List<Segment> allSegments = new ArrayList<>();
        try {
            Segment segment = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Library WHERE SegmentSpeaker=?;");
            //set the ? to the characters name and get all those segments
            ps.setString(1,  str);
            
            ResultSet resultSet = ps.executeQuery();
            //add the segments to the list
            while (resultSet.next()) {
                segment = generateSegment(resultSet);
                allSegments.add(segment);
            }
            
            resultSet.close();
            ps.close();
            
            return allSegments;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting segment: " + e.getMessage());
        }
        
    }
	
	/**
	 * Deletes the specified segment from the database
	 * @param segmentId the segmentID to delete
	 * @return true if the delete was successful false otherwise
	 * @throws Exception
	 */
	public boolean deleteSegment(Id segmentId) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Library WHERE SegmentID = ?;");
            //set the ? to the segmentId
            ps.setString(1, segmentId.getId());
            int numAffected = ps.executeUpdate();
            ps.close();
            //was only one deleted
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to deleat segment: " + e.getMessage());
        }
    }
	
	/**
	 * Deletes the specified segment from the database(keep, useful for test cases)
	 * @param segment the segment to delete
	 * @return true if the delete was successful false otherwise
	 * @throws Exception
	 */
    public boolean deleteSegment(Segment segment) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Library WHERE SegmentID = ?;");
            //set the ? to the segmentId and delete
            ps.setString(1, segment.getId().getId());
            int numAffected = ps.executeUpdate();
            ps.close();
            //makes sure only one entry is deleted
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete segment: " + e.getMessage());
        }
    }
    
    /**
     * Adds the inputed segment to the database
     * @param segment the segment to add
     * @return true if the segment was successfully added false othewise
     * @throws Exception
     */
    public boolean addSegment(Segment segment) throws Exception {
        try {
        	
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Library  WHERE SegmentID = ?;");
            ps.setString(1, segment.getId().getId());
            ResultSet resultSet = ps.executeQuery();
           
            // already present?
            while (resultSet.next()) {              
                resultSet.close();
                return false;
            }
            //Set the ?s and execute the command
            ps = conn.prepareStatement("INSERT INTO Library (SegmentID,SegmentWords,SegmentSpeaker,IsSegmentPublic) values(?,?,?,?);");
            ps.setString(1,  segment.getId().getId());
            ps.setString(2,  segment.getSentence());
            ps.setString(3,  segment.getCharacter());
            ps.setBoolean(4,  segment.isRemotelyAvailable());
            
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert segment: " + e.getMessage());
        }
    }

    /**
     * Get the specified segment that is in the database
     * @param id the id of the segment to get
     * @return the segment
     * @throws Exception
     */
    public Segment getSegment(Id id) throws Exception {
        //TODO later update to also get remote segments
 
        try {
            Segment segment = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Library WHERE SegmentID=?;");
            //set the ? to the segmentId to look for and execute
            ps.setString(1,  id.getId());
            
            ResultSet resultSet = ps.executeQuery();
            //generate the segment
            while (resultSet.next()) {
                segment = generateSegment(resultSet);
            }
            
            resultSet.close();
            ps.close();
            
            if(segment == null) {
				throw new Exception("Null Pointer: segment not found");
			}
            
            return segment;

        } catch (Exception e) {
            throw new Exception("Failed in getting segment: " + e.getMessage());
        }
        
    }
    
    /**
     * Gets all the segments that are in the database
     * @return the list of segments that are in the database
     * @throws Exception
     */
    public List<Segment> getAllLocalSegments() throws Exception {
        List<Segment> allSegments = new ArrayList<>();
        try {
        	//get all the segment entries
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM Library";
            ResultSet resultSet = statement.executeQuery(query);
            //turn them into Segments and add it to the list
            while (resultSet.next()) {
                Segment c = generateSegment(resultSet);
                allSegments.add(c);
            }
            resultSet.close();
            statement.close();
            return allSegments;

        } catch (Exception e) {
            throw new Exception("Failed in getting books: " + e.getMessage());
        }
    }
    
    /**
     * Generates a segment from the database entry
     * @param resultSet the database entry
     * @return the segment
     * @throws Exception
     */
    private Segment generateSegment(ResultSet resultSet) throws Exception {
        String idStr  = resultSet.getString("SegmentID");
        Id id = new Id(idStr);
        String words = resultSet.getString("SegmentWords");
        String speaker = resultSet.getString("SegmentSpeaker");
        Boolean p = resultSet.getBoolean("IsSegmentPublic");
        return new Segment (id, p, words, speaker);
    }

}
