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
	public boolean appendToPlaylist(Id playlistId, Id segmentId) throws Exception {
		 try {
	        	String query = "UPDATE Playlists SET SegmentIDs=? WHERE PlayListID=?;";
	        	PreparedStatement ps = conn.prepareStatement(query);
	        	String playlistSegments = "";
	        	Playlist p = this.getFullPlaylist(playlistId);
	            Segment[] segs =  p.getSegments();
	            int i = 0;
	            while(i<segs.length) {
	            	 if(segs[i] != null) {
	            		 playlistSegments = playlistSegments + segs[i].getId().getId() + ",";
	            	 }
	            	i++;
	            }
	            playlistSegments = playlistSegments + segmentId.getId();      
	            ps.setString(1, playlistSegments);
	            ps.setString(2, playlistId.getId());
	            int numAffected = ps.executeUpdate();
	            ps.close();
	            //run = 1; 
	            return (numAffected == 1); 
	        } catch (Exception e) { 
	            throw new Exception("Failed to update Segments: " + e.getMessage());
	        }
   }
	
	public boolean appendToPlaylist(Playlist playlist) throws Exception {
		 try {
	        	String query = "UPDATE Playlists SET SegmentIDs=? WHERE PlayListID=?;";
	        	PreparedStatement ps = conn.prepareStatement(query);
	        	
	        	String playlistSegments = "";
	            Segment[] segs =  playlist.getSegments();
	            int i = 0;
	            while(i<segs.length) {
	            	if(i == 0) {
	            		playlistSegments = segs[i].getId().getId();
	            	}else {
	            		playlistSegments = playlistSegments + "," + segs[i].getId().getId();
	            	}
	            	i++;
	            }
	            
	            ps.setString(1, playlistSegments);
	            ps.setString(2, playlist.getId().getId());
	            int numAffected = ps.executeUpdate();
	            ps.close();
	            
	            return (numAffected == 1);
	        } catch (Exception e) {
	            throw new Exception("Failed to update report: " + e.getMessage());
	        }
    }
	
	public boolean deletePlaylist(Id playlistId) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Playlists WHERE PlayListID = ?;");
            ps.setString(1, playlistId.getId());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to deleat segment: " + e.getMessage());
        }
    }
	
	  public boolean deletePlaylist(Playlist playlist) throws Exception {
	        try {
	            PreparedStatement ps = conn.prepareStatement("DELETE FROM Playlists WHERE PlayListID = ?;");
	            ps.setString(1, playlist.getId().getId());
	            int numAffected = ps.executeUpdate();
	            ps.close();
	            
	            return (numAffected == 1);

	        } catch (Exception e) {
	            throw new Exception("Failed to deleat segment: " + e.getMessage());
	        }
	    }

	    public boolean addPlaylist(Playlist playlist) throws Exception {
	        try {
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Playlists  WHERE PlayListID = ?;");
	            ps.setString(1, playlist.getId().getId());
	            ResultSet resultSet = ps.executeQuery();
	            
	            // already present?
	            while (resultSet.next()) { 
	                resultSet.close();
	                return false;
	            }
	          
	            ps = conn.prepareStatement("INSERT INTO Playlists (PlayListID,PlayListName,SegmentIDs) values(?,?,?);");
	            ps.setString(1,  playlist.getId().getId());
	            ps.setString(2,  playlist.getName());
	            String playlistSegments = "";
	            Segment[] segs =  playlist.getSegments();
	            
	            int i = 0;
	            while(i<segs.length) {
	            	if(i == 0) {
	            		playlistSegments = segs[i].getId().getId();
	            	}else {
	            		playlistSegments = playlistSegments + "," + segs[i].getId().getId();
	            	}
	            	i++;
	            }
	            
	            ps.setString(3, playlistSegments);
	            ps.execute();
	            return true;

	        } catch (Exception e) {
	            throw new Exception("Failed to insert playlist: " + e.getMessage());
	        }
	    }
	
    
	/**
	 * get id and name of all playlist
	 * @return
	 * @throws Exception
	 */ 
    public List<Playlist> getAllPlaylists() throws Exception {
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
    
    public Playlist getFullPlaylist(Id playlistId) throws Exception{
	    try {
	        Playlist playlist = null;
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Playlists WHERE PlayListID=?;");
	        ps.setString(1,  playlistId.getId());
	        
	        ResultSet resultSet = ps.executeQuery();
	    	SegmentDAO segDAO = new SegmentDAO(conn);
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
	private Playlist generatePlaylist(ResultSet resultSet, SegmentDAO segDAO) throws Exception {
	    Playlist p = generatePlaylist(resultSet);
	    
	    String segmentIDsStr = resultSet.getString("SegmentIDs");
	    String[] segmentIDs = segmentIDsStr.split(",");
	    Segment[] segments = new Segment[segmentIDs.length];
	    for(int i = 0; i < segments.length; i++) {
	    	if(segments.length == 1 && segmentIDsStr.length() == 0) {
	    		//nothing in there
	    	}else {
	    		segments[i] = segDAO.getSegment(new Id(segmentIDs[i]));
	    		
	    	}
	    }
	    
	    p.addSegments(segments);
	    return p;
	}
	
}
