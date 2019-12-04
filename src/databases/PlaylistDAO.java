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
	 * deletes the inputed segment out of the inputed playlist
	 * @param playlistId the playlistId of the playlist to delete from
	 * @param segmentId the segmentId of the segment to delete
	 * @return true if it was deleted correctly false otherwise
	 * @throws Exception
	 */
	public boolean deleteFromPlaylist(Id playlistId, Id segmentId) throws Exception {
		 try {
			 //get the playlist
	        	String query = "UPDATE Playlists SET SegmentIDs=? WHERE PlayListID=?;";
	        	PreparedStatement ps = conn.prepareStatement(query);
	        	String playlistSegments = "";
	        	List<Segment> playlistSegmentsList = new ArrayList<Segment>();
	        	Playlist p = this.getFullPlaylist(playlistId);
	            Segment[] segs =  p.getSegments();
	            int i = 0;
	            
	            //get a list of segments that won'tbe deleted
	            while(i<segs.length) {
	            	 if(segs[i] != null) {
	            		 if(!segs[i].getId().equals(segmentId)) {
	            			 playlistSegmentsList.add(segs[i]);
	            		 }
	            	 }
	            	i++;
	            }
	             //convert the list to a string to put into the database
	           
	            i = 0;
	            while(i<playlistSegmentsList.size()-1) {
	            	playlistSegments = playlistSegments + playlistSegmentsList.get(i).getId().getId() + ",";
	            	i++;
	            }
	           
	            playlistSegments = playlistSegments + playlistSegmentsList.get(i).getId().getId();
	            
	            ps.setString(1, playlistSegments);
	            ps.setString(2, playlistId.getId());
	          
	            int numAffected = ps.executeUpdate();
	            ps.close(); 
	            return (numAffected == 1); 
	        } catch (Exception e) { 
	            throw new Exception("Failed to update Segments: " + e.getMessage()); //remove segments throws this with e.getMessage() = Index: 0, Size: 0
	        }
   }
	
	
	
	/**
	 * Appendes the inputed segment onto the inputed playlist
	 * @param playlistId the playlistId of the playlist to append to
	 * @param segmentId the segmentId of the segment to add
	 * @return true if it was appended correctly false otherwise
	 * @throws Exception
	 */
	public boolean appendToPlaylist(Id playlistId, Id segmentId) throws Exception {
		 try {
			 //get the playlist
	        	String query = "UPDATE Playlists SET SegmentIDs=? WHERE PlayListID=?;";
	        	PreparedStatement ps = conn.prepareStatement(query);
	        	String playlistSegments = "";
	        	Playlist p = this.getFullPlaylist(playlistId);
	            Segment[] segs =  p.getSegments();
	            int i = 0;
	            //get the curent segments string
	            while(i<segs.length) {
	            	 if(segs[i] != null) {
	            		 playlistSegments = playlistSegments + segs[i].getId().getId() + ",";
	            	 }
	            	i++;
	            }
	            //add the new id and set the new string as the segmentIds string
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
	
	/**
	 * Changes the playlist entry to have the segments from the inputed playlist
	 * @param playlist the playlist in its final form after the append
	 * @return true if it was appended false otherwise
	 * @throws Exception
	 */
	public boolean appendToPlaylist(Playlist playlist) throws Exception {
		 try {
	        	String query = "UPDATE Playlists SET SegmentIDs=? WHERE PlayListID=?;";
	        	PreparedStatement ps = conn.prepareStatement(query);
	        	//get the string of segmentIds
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
	            //set the segmentIds string to the new segmentIds string
	            ps.setString(1, playlistSegments);
	            ps.setString(2, playlist.getId().getId());
	            int numAffected = ps.executeUpdate();
	            ps.close();
	            
	            return (numAffected == 1);
	        } catch (Exception e) {
	            throw new Exception("Failed to update report: " + e.getMessage());
	        }
    }
	
	/**
	 * Deletes the specified playlist from the database
	 * @param playlistId the playlistId to delete
	 * @return true if the delete was successful false otherwise
	 * @throws Exception
	 */
	public boolean deletePlaylist(Id playlistId) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Playlists WHERE PlayListID = ?;");
            //set the ? to the playlistId and delete
            ps.setString(1, playlistId.getId());
            int numAffected = ps.executeUpdate();
            ps.close();
            //make sure only one is deleted
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to deleat segment: " + e.getMessage());
        }
    }
	/**
	 * Deletes the specified playlist from the database
	 * @param playlist the playlist to delete
	 * @return true if the delete was successful false otherwise
	 * @throws Exception
	 */
	  public boolean deletePlaylist(Playlist playlist) throws Exception {
	        try {
	            PreparedStatement ps = conn.prepareStatement("DELETE FROM Playlists WHERE PlayListID = ?;");
	            //set the ? to the playlistId and delete
	            ps.setString(1, playlist.getId().getId());
	            int numAffected = ps.executeUpdate();
	            ps.close();
	            //make sure only one is deleted
	            return (numAffected == 1);

	        } catch (Exception e) {
	            throw new Exception("Failed to deleat segment: " + e.getMessage());
	        }
	    }

	    /**
	     * Adds the specified playlist to the database
	     * @param playlist the playlist to add
	     * @return true if it was added false otherwise
	     * @throws Exception
	     */
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
	            //set the ?s and execute the add
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
	 * get id and name of all playlists
	 * @return a list of playlists that are in the database
	 * @throws Exception
	 */ 
    public List<Playlist> getAllPlaylists() throws Exception {
	    List<Playlist> allPlaylists = new ArrayList<>();
	    try {
	    	//get all the entrys
	        Statement statement = conn.createStatement();
	        
	        String query = "SELECT * FROM Playlists";
	        ResultSet resultSet = statement.executeQuery(query);
	        //generate the playlists
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
    
    /**
     * Gets the specified playlist from the database
     * @param playlistId the playlist ID of the playlist you want to get
     * @return the playlist
     * @throws Exception
     */
    public Playlist getFullPlaylist(Id playlistId) throws Exception{
	    try {
	    	//find the playlist entry
	        Playlist playlist = null;
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Playlists WHERE PlayListID=?;");
	        ps.setString(1,  playlistId.getId());
	        ResultSet resultSet = ps.executeQuery();
	    	SegmentDAO segDAO = new SegmentDAO(conn);
	    	//generate the playlist and return it
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
	    //generate the segments and add them to the playlist in order
	    String segmentIDsStr = resultSet.getString("SegmentIDs");
	    String[] segmentIDs = segmentIDsStr.split(",");
	    List<Segment> segmentList = new ArrayList<Segment>();
	    int si;
	    for(int i = 0; i < segmentIDs.length; i++) {
	    	if(segmentList.size() == 1 && segmentIDsStr.length() == 0) {
	    		//nothing in there
	    	}else {
	    		try{
	    			segmentList.add(segDAO.getSegment(new Id(segmentIDs[i])));
	    		}catch(Exception e) {
	    			System.out.println("segment not avaliable " + e.getMessage());
	    		}
	    		
	    	}
	    	
	    }
	    Segment[] segments = {};
	    segments = segmentList.toArray(segments);
	    
	    p.addSegments(segments);
	    return p;
	}
	
}
