package paths.playlist;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;
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

public class AppendToPlaylistHandlerTest extends LambdaTest{
	
	@Test
	public void testAppendHandler(){
    	AppendToPlaylistHandler appendHandler = new AppendToPlaylistHandler();
    	GetPlaylistHandler getHandler = new GetPlaylistHandler();
    	SegmentAndPlaylistRequest request = new SegmentAndPlaylistRequest(new Id("525b2e69-5140-47d9-b2d5-ff7bb9f67d5e.ogg"), new Id("c136cdf2-5877-4e86-b6c7-e72b3f05ea9b"));
        try {
        	Response<Playlist> beforeResponse = getHandler.handleRequest(request.getPlaylistId(), createContext("list"));
        	Response<Playlist> response = appendHandler.handleRequest(request,createContext("list"));
			Response<Playlist> afterResponse = getHandler.handleRequest(request.getPlaylistId(), createContext("list"));
			
			System.out.println(beforeResponse.getModel());
			//System.out.println(response.getModel());
			System.out.println(afterResponse.getModel());
			
			Segment[] responseSegments = response.getModel().getSegments();
			boolean addedToPlaylist = responseSegments[responseSegments.length-1].getId().equals(request.getSegmentId());
		
			boolean segmentsArrayIncreased;
			if(beforeResponse.getModel().getSegments() != null) {
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
	

	

}
