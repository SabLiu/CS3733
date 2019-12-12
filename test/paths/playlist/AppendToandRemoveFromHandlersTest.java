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
	SegmentAndPlaylistRequest request1 = new SegmentAndPlaylistRequest(seg1.getUrl(), new Id("c136cdf2-5877-4e86-b6c7-e72b3f05ea9b"));
	SegmentAndPlaylistRequest request2 = new SegmentAndPlaylistRequest(seg2.getUrl(), new Id("c136cdf2-5877-4e86-b6c7-e72b3f05ea9b"));
	SegmentAndPlaylistRequest request3 = new SegmentAndPlaylistRequest("dhfghf", new Id("88888-5877-4e86-b6c7-e72b3f05ea9b"));
	AppendToPlaylistHandler appendHandler = new AppendToPlaylistHandler();
	GetPlaylistHandler getHandler = new GetPlaylistHandler();
	RemoveFromPlaylistHandler removeHandler = new RemoveFromPlaylistHandler();
	
	@Test
	public void test() {
		testAppendHandler();
		testRemoveMiddleAndMultiple();
		
	}
	
	public void testAppendHandler(){
        try {
        	Response<Playlist> beforeResponse = getHandler.handleRequest(request1.getPlaylistId(), createContext("list"));
        	Response<Playlist> response = appendHandler.handleRequest(request1,createContext("list"));
			Response<Playlist> afterResponse = getHandler.handleRequest(request1.getPlaylistId(), createContext("list"));
			

			
			String[] responseSegments = response.getModel().getSegmentUrls();
			boolean addedToPlaylist = responseSegments[responseSegments.length-1].equals(request1.getSegmentUrl());
		
			boolean segmentsArrayIncreased;
			if(beforeResponse.getModel() != null) {
				segmentsArrayIncreased = (afterResponse.getModel().getSegmentUrls().length - beforeResponse.getModel().getSegmentUrls().length) == 1;
			}
			else {
				segmentsArrayIncreased = afterResponse.getModel().getSegmentUrls().length == 1;
			}
			
			
			assertTrue("Response indicates sucess", response.statusCode == 200);
			assertTrue("Segment added to database", addedToPlaylist);
			assertTrue("Segments in playlist increased by one", segmentsArrayIncreased);
			assertArrayEquals("Updated playlist returend", response.getModel().getSegmentUrls(), afterResponse.getModel().getSegmentUrls());  
		} catch (Exception e) {
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
		}
        
	}
	
	@Test
	public void testAppendHandlerNonExisting(){
        try {
        	//Response<Playlist> beforeResponse = getHandler.handleRequest(request1.getPlaylistId(), createContext("list"));
        	Response<Playlist> response = appendHandler.handleRequest(request3,createContext("list"));
			//Response<Playlist> afterResponse = getHandler.handleRequest(request1.getPlaylistId(), createContext("list"));
			
			
			assertTrue("Response indicates failure", response.statusCode == 400);
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
			for(String s : response.model.getSegmentUrls()) {
				if(s.equals(request1.getSegmentUrl())) {
					removedFromPlaylist = false;
				}
			}
		
			boolean segmentsArrayDecreased;
			if(afterResponse.getModel() != null) {
				segmentsArrayDecreased = (afterResponse.getModel().getSegmentUrls().length - beforeResponse.getModel().getSegmentUrls().length) < 0;
			}
			else {
				segmentsArrayDecreased = true;
			}
			
			
			assertTrue("Response indicates sucess", response.statusCode == 200);
			assertTrue("Segment removed from database", removedFromPlaylist);
			assertTrue("Number of segments in playlist decreased", segmentsArrayDecreased);
			assertArrayEquals("Updated playlist returend", response.getModel().getSegmentUrls(), afterResponse.getModel().getSegmentUrls());  
		} catch (Exception e) {
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
		}
	}
	
	@Test
	public void testRemoveEnd() {
		try {
		appendHandler.handleRequest(request1,createContext("list"));
    	Response<Playlist> beforeResponse = getHandler.handleRequest(request1.getPlaylistId(), createContext("list"));
    	Response<Playlist> response = removeHandler.handleRequest(request1,createContext("list"));
		Response<Playlist> afterResponse = getHandler.handleRequest(request1.getPlaylistId(), createContext("list"));
		
		System.out.println(beforeResponse.getModel());
		//System.out.println(response.getModel());
		System.out.println(afterResponse.getModel());
		
		boolean removedFromPlaylist = true;
		for(String s : response.model.getSegmentUrls()) {
			if(s.equals(request1.getSegmentUrl())) {
				removedFromPlaylist = false;
			}
		}
	
		boolean segmentsArrayDecreased;
		if(afterResponse.getModel() != null) {
			segmentsArrayDecreased = ((afterResponse.getModel().getSegmentUrls().length - beforeResponse.getModel().getSegmentUrls().length) < 0);
		}
		else {
			segmentsArrayDecreased = true;
		}
		
		
		assertTrue("Response indicates failure", response.statusCode == 200);
		assertTrue("Segment removed from database", removedFromPlaylist);
		assertTrue("Number of segments in playlist decreased", segmentsArrayDecreased);
		assertArrayEquals("Updated playlist returend", response.getModel().getSegmentUrls(), afterResponse.getModel().getSegmentUrls());  
	} catch (Exception e) {
		System.out.println("EXCPETION: " + e.getMessage());
		e.printStackTrace();
		fail("EXCPETION: " + e.getMessage());
	}
	}
	
	@Test
	public void testRemoveNonExistent() {
		try {
    	//Response<Playlist> beforeResponse = getHandler.handleRequest(request2.getPlaylistId(), createContext("list"));
    	Response<Playlist> response = removeHandler.handleRequest(request3,createContext("list"));
		//Response<Playlist> afterResponse = getHandler.handleRequest(request2.getPlaylistId(), createContext("list"));
		
		
		
		assertTrue("Response indicates sucess", response.statusCode == 400);
		} catch (Exception e) {
		System.out.println("EXCPETION: " + e.getMessage());
		e.printStackTrace();
		fail("EXCPETION: " + e.getMessage());
	}
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
