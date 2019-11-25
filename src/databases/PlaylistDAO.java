package databases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import definitions.Id;
import definitions.Playlist;
import definitions.Segment;

public class PlaylistDAO extends DAO{
    
	/**
	 * get id and name of all playlist
	 * @return
	 * @throws Exception
	 */ 
    public List<Playlist> getAllPlaylists() throws Exception {;
	    List<Playlist> allPlaylists = new ArrayList<>();
	    try {
	        Statement statement = conn.createStatement();
	        
	        String query = "SELECT * FROM Playlists";
	        ResultSet resultSet = statement.executeQuery(query);
	
	        while (resultSet.next()) {
	            Playlist p = generatePlaylist(resultSet); //get playlist name and id (no segments)
	            allPlaylists.add(p);
	        }
	        resultSet.close();
	        statement.close();
	        return allPlaylists;
	
	    } catch (Exception e) { 
	        throw new Exception("Failed in getting books: " + e.getMessage());
	    }
	}
    
    public Playlist getFullPlaylist(Id playlistId) throws Exception{;
	    try {
	        Playlist playlist = null;
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Playlists WHERE PlayListID=?;");
	        ps.setString(1,  playlistId.getId());
	        
	        ResultSet resultSet = ps.executeQuery();
	    	SegmentsDAO segDAO = new SegmentsDAO(conn);
	    	
	        while (resultSet.next()) {
	            playlist = generatePlaylist(resultSet, segDAO); //get playlist name, id and segments
	        }
	        
	        resultSet.close();
	        ps.close();
	        
			if(playlist == null) {
				throw new NullPointerException("playlist not found");
			}
	        return playlist;
	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        throw new Exception("Failed in getting playlist: " + e.getMessage());
	    }
    }
    
    /**
     * get playlist with only id and name
     * @param resultSet
     * @return
     * @throws Exception
     */
	private Playlist generatePlaylist(ResultSet resultSet) throws Exception {
	    String idStr  = resultSet.getString("PlayListID");
	    Id id = new Id(idStr);
	    String name = resultSet.getString("PlaylistName");
	    	
	    return new Playlist(id, name);
	}
	
	/**
	 * get playlist with id, name, and list of segments
	 * @param resultSet
	 * @param segDAO
	 * @return
	 * @throws Exception
	 */
	private Playlist generatePlaylist(ResultSet resultSet, SegmentsDAO segDAO) throws Exception {
	    Playlist p = generatePlaylist(resultSet);
	    
	    String segmentIDsStr = resultSet.getString("SegmentIDs");
	    String[] segmentIDs = segmentIDsStr.split(",");
	    Segment[] segments = new Segment[segmentIDs.length];
	    
	    for(int i = 0; i < segments.length; i++) {
	    	segments[i] = segDAO.getSegment(new Id(segmentIDs[i]));
	    }
	    
	    p.addSegments(segments);
	    	
	    return p;
	}
	
}
