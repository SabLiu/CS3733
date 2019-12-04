package databases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import definitions.Id;
import definitions.Segment;




public class SegmentDAO extends DAO{
	
	public SegmentDAO() {super();}
	public SegmentDAO(java.sql.Connection conn) {super(conn);}
   
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
	
	public List<Segment> searchSegmentCharacter(String str) throws Exception {
        //TODO later update to also get remote segments
		List<Segment> allSegments = new ArrayList<>();
        try {
            Segment segment = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Library WHERE SegmentSpeaker=?;");
            ps.setString(1,  str);
            
            ResultSet resultSet = ps.executeQuery();
            
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
	
	public boolean deleteSegment(Id segmentId) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Library WHERE SegmentID = ?;");
            ps.setString(1, segmentId.getId());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to deleat segment: " + e.getMessage());
        }
    }
	
    public boolean deleteSegment(Segment segment) throws Exception {
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
    
    public List<Segment> getAllLocalSegments() throws Exception {;
        List<Segment> allSegments = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM Library";
            ResultSet resultSet = statement.executeQuery(query);

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
    
    private Segment generateSegment(ResultSet resultSet) throws Exception {
        String idStr  = resultSet.getString("SegmentID");
        Id id = new Id(idStr);
        String words = resultSet.getString("SegmentWords");
        String speaker = resultSet.getString("SegmentSpeaker");
        Boolean p = resultSet.getBoolean("IsSegmentPublic");
        return new Segment (id, p, words, speaker);
    }

}
