package paths.playlist;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.Id;
import definitions.Playlist;
import definitions.Response;
import definitions.Segment;
import definitions.SegmentAndPlaylistRequest;
import lamnda.LambdaTest;
import paths.segment.CreateSegmentHandler;
import paths.segment.DeleteSegmentHandler;

public class GetPlaylistHandlerTest extends LambdaTest{
	Playlist playlist = new Playlist("get playlist test");
	Segment segment1 = new Segment(false, "Test upload", "test", getEncodedValue("test\\resources\\test_segment.ogg"));
	Segment segment2 = new Segment(false, "Test upload", "test", getEncodedValue("test\\resources\\test_segment.ogg"));

	@Before
	public void setup() {
		(new CreatePlaylistHandler()).handleRequest(playlist, createContext("list"));
		(new CreateSegmentHandler()).handleRequest(segment1, createContext("list"));
		(new AppendToPlaylistHandler()).handleRequest(new SegmentAndPlaylistRequest(segment1.getId(), playlist.getId()), createContext("list"));
		(new CreateSegmentHandler()).handleRequest(segment2, createContext("list"));
		(new AppendToPlaylistHandler()).handleRequest(new SegmentAndPlaylistRequest(segment2.getId(), playlist.getId()), createContext("list"));
	}
	
	@Test
	public void hasSegments(){
    	GetPlaylistHandler handler = new GetPlaylistHandler();
        Response<Playlist> resp = handler.handleRequest(playlist.getId(), createContext("list"));
        
        boolean hasSegments = resp.model.getSegments().length == 2;
        
        System.out.println(resp.model);
        
        assertTrue("the segments in the playlist were returned from the database", hasSegments);
        Assert.assertEquals(200, resp.statusCode); 
	} 
	
	@After
	public void teardown() {
		(new DeletePlaylistHandler()).handleRequest(playlist.getId(), createContext("list"));
		(new DeleteSegmentHandler()).handleRequest(segment1.getId(), createContext("list"));
		(new DeleteSegmentHandler()).handleRequest(segment2.getId(), createContext("list"));
	}
}
