package paths.playlist;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import definitions.Id;
import definitions.Playlist;
import definitions.Response;
import lamnda.LambdaTest;

public class GetPlaylistHandlerTest extends LambdaTest{

	@Test
	public void hasMariasSegments(){
    	GetPlaylistHandler handler = new GetPlaylistHandler();
        Response<Playlist> resp = handler.handleRequest(new Id("c136cdf2-5877-4e86-b6c7-e72b3f05ea9b"), createContext("list"));
        
        boolean hasMariaSegments = resp.model.getSegments().length != 0;
        
        System.out.println(resp.model);
        
        assertTrue("the segments in 'Maria's Playlist' were returned from the database", hasMariaSegments);
        Assert.assertEquals(200, resp.statusCode); 
	}
}
