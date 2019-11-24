package databases;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import definitions.Id;
import definitions.Playlist;
import definitions.Segment;

public class PlaylistDAO extends DAO{
    
    public List<Playlist> getAllPlaylists() throws Exception {;
	    List<Playlist> allPlaylists = new ArrayList<>();
	    try {
	        Statement statement = conn.createStatement();
	        SegmentsDAO segDAO = new SegmentsDAO(conn);
	        String query = "SELECT * FROM Playlists";
	        ResultSet resultSet = statement.executeQuery(query);
	
	        while (resultSet.next()) {
	            Playlist p = generatePlaylist(resultSet, segDAO);
	            allPlaylists.add(p);
	        }
	        resultSet.close();
	        statement.close();
	        return allPlaylists;
	
	    } catch (Exception e) {
	        throw new Exception("Failed in getting books: " + e.getMessage());
	    }
	}
	
	private Playlist generatePlaylist(ResultSet resultSet, SegmentsDAO segDAO) throws Exception {
	    String idStr  = resultSet.getString("PlayListID");
	    Id id = new Id(idStr);
	    String name = resultSet.getString("PlaylistName");
	    
	    String segmentIDsStr = resultSet.getString("SegmentIDs");
	    String[] segmentIDs = segmentIDsStr.split(",");
	    Segment[] segments = new Segment[segmentIDs.length];
	    
	    for(int i = 0; i < segments.length; i++) {
	    	segments[i] = segDAO.getSegment(segmentIDs[i]);
	    }
	    	
	    return new Playlist (id, name, segments);
	}
}
