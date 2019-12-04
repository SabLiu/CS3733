package paths.playlist;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import definitions.Id;
import definitions.Playlist;
import definitions.Response;
import definitions.Segment;
import definitions.SegmentAndPlaylistRequest;
import lamnda.LambdaTest;
import paths.segment.CreateSegmentHandler;
import paths.segment.DeleteSegmentHandler;

public class AppendToandRemoveFromHandlersTest extends LambdaTest{
	Segment seg1 = new Segment(false, "Test upload1", "test", getEncodedValue("test\\resources\\test_segment.ogg"));
	Segment seg2 = new Segment(false, "Test upload2", "test", getEncodedValue("test\\resources\\test_segment.ogg"));
	SegmentAndPlaylistRequest request1 = new SegmentAndPlaylistRequest(seg1.getId(), new Id("c136cdf2-5877-4e86-b6c7-e72b3f05ea9b"));
	SegmentAndPlaylistRequest request2 = new SegmentAndPlaylistRequest(seg2.getId(), new Id("c136cdf2-5877-4e86-b6c7-e72b3f05ea9b"));
	AppendToPlaylistHandler appendHandler = new AppendToPlaylistHandler();
	GetPlaylistHandler getHandler = new GetPlaylistHandler();
	RemoveFromPlaylistHandler removeHandler = new RemoveFromPlaylistHandler();
	
	@Test
	public void test() {
		testAppendHandler();
		testRemoveMiddleAndMultiple();
		testRemoveEnd();
	}
	
	public void testAppendHandler(){
        try {
        	Response<Playlist> beforeResponse = getHandler.handleRequest(request1.getPlaylistId(), createContext("list"));
        	Response<Playlist> response = appendHandler.handleRequest(request1,createContext("list"));
			Response<Playlist> afterResponse = getHandler.handleRequest(request1.getPlaylistId(), createContext("list"));
			

			
			Segment[] responseSegments = response.getModel().getSegments();
			boolean addedToPlaylist = responseSegments[responseSegments.length-1].getId().equals(request1.getSegmentId());
		
			boolean segmentsArrayIncreased;
			if(beforeResponse.getModel() != null) {
				segmentsArrayIncreased = (afterResponse.getModel().getSegments().length - beforeResponse.getModel().getSegments().length) == 1;
			}
			else {
				segmentsArrayIncreased = afterResponse.getModel().getSegments().length == 1;
			}
			
			
			assertTrue("Response indicates sucess", response.statusCode == 200);
			assertTrue("Segment added to database", addedToPlaylist);
			assertTrue("Segments in playlist increased by one", segmentsArrayIncreased);
			assertArrayEquals("Updated playlist returend", response.getModel().getSegments(), afterResponse.getModel().getSegments());  
		} catch (Exception e) {
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
		}
        
	}
	
	public void testRemoveMiddleAndMultiple() {
		try {
	    	appendHandler.handleRequest(request2,createContext("list"));
	    	appendHandler.handleRequest(request1,createContext("list"));
	
	    	Response<Playlist> beforeResponse = getHandler.handleRequest(request1.getPlaylistId(), createContext("list"));
	    	Response<Playlist> response = removeHandler.handleRequest(request1,createContext("list"));
			Response<Playlist> afterResponse = getHandler.handleRequest(request1.getPlaylistId(), createContext("list"));
			
			System.out.println(beforeResponse.getModel());
			//System.out.println(response.getModel());
			System.out.println(afterResponse.getModel());
			
			boolean removedFromPlaylist = true;
			for(Segment s: response.model.getSegments()) {
				if(s.getId().equals(request1.getSegmentId())) {
					removedFromPlaylist = false;
				}
			}
		
			boolean segmentsArrayDecreased;
			if(afterResponse.getModel() != null) {
				segmentsArrayDecreased = (afterResponse.getModel().getSegments().length - beforeResponse.getModel().getSegments().length) < 0;
			}
			else {
				segmentsArrayDecreased = true;
			}
			
			
			assertTrue("Response indicates sucess", response.statusCode == 200);
			assertTrue("Segment removed from database", removedFromPlaylist);
			assertTrue("Number of segments in playlist decreased", segmentsArrayDecreased);
			assertArrayEquals("Updated playlist returend", response.getModel().getSegments(), afterResponse.getModel().getSegments());  
		} catch (Exception e) {
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
		}
	}
	
	public void testRemoveEnd() {
		Response<Playlist> response = removeHandler.handleRequest(request2,createContext("list"));
	}
	
	@Before
	public void addSegmentsToLibrary() {
		CreateSegmentHandler createHandler = new CreateSegmentHandler();
		createHandler.handleRequest(seg1,createContext("list"));
		createHandler.handleRequest(seg2,createContext("list"));
	}
	
	@After
	public void removeSegmentsFromLibrary() {
		DeleteSegmentHandler deleteHandler = new DeleteSegmentHandler();
		deleteHandler.handleRequest(seg1.getId(),createContext("list"));
		deleteHandler.handleRequest(seg2.getId(),createContext("list"));
	}
	
	
	

	

}
