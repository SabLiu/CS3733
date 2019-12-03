package paths.playlist;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import definitions.Playlist;
import definitions.Response;
import lamnda.LambdaTest;

public class ListPlaylistsHandlerTest extends LambdaTest{

	@Test
	public void hasMariasPlaylist(){
    	ListPlaylistsHandler handler = new ListPlaylistsHandler();
        Response<Playlist[]> resp = handler.handleRequest(null, createContext("list"));
        
        boolean hasMariaPlaylist = false;
        
        for (Playlist p : resp.model) {
        	System.out. println(p);
        	if (p.getName().equals("Maria's Playlist")) { hasMariaPlaylist = true; }
        }
        
        assertTrue("there is a playlist called 'Maria's Playlist' in the database", hasMariaPlaylist);
        Assert.assertEquals(200, resp.statusCode);
	}
 
}
