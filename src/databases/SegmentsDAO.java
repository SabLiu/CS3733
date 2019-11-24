package databases;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import definitions.Id;
import definitions.Segment;




public class SegmentsDAO {

	java.sql.Connection conn;

    public SegmentsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {

    		//********************************//

    		//********************************//

    		//********************************//

    		//********************************//
    		//********************************//
    		
    		
    		//********************************//
    		//this is called thus it isn't conecting
    		
    		
    		System.out.println("jdhbfvhb");
    		conn = null;
    	}
    }
/*
    public Segment getSegment(String id) throws Exception {
        
        try {
            Segment segment = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE name=?;");
            ps.setString(1,  id);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                segment = generateConstant(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return constant;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting constant: " + e.getMessage());
        }
    }
   
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
    
    public boolean deleteConstant(Constant constant) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM constants WHERE name = ?;");
            ps.setString(1, constant.name);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }


    public boolean addConstant(Constant constant) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM constants WHERE name = ?;");
            ps.setString(1, constant.name);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Constant c = generateConstant(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO constants (name,value) values(?,?);");
            ps.setString(1,  constant.name);
            ps.setDouble(2,  constant.value);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }
*/
    public List<Segment> getAllLocalSegments() throws Exception {
        
        List<Segment> allSegments = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM Library";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Segment c = generateSegment(resultSet);
                System.out.println(c);
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
        String name = resultSet.getString("SegmentName");
        Boolean p = resultSet.getBoolean("IsSegmentPublic");
        return new Segment (id, p, words, speaker, name);
    }

}
