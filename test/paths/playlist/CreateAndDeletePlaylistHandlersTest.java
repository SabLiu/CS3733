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

import lamnda.LambdaTest;

public class CreateAndDeletePlaylistHandlersTest extends LambdaTest{
	Id testIdPass = new Id();
	Id testIdFail = new Id();
	
	@Test
	public void testShouldPass() {
		System.out.println(testIdPass);
		testCreateSegmentHandler();
		testDeleteSegmentHandler();
	}
	 
	@Test
	public void testShouldFail() {
		testDeleteSegmentHandlerShouldFail();
	}
	
	public void testCreateSegmentHandler(){
    	CreatePlaylistHandler createHandler = new CreatePlaylistHandler();
    	ListPlaylistsHandler listHandler = new ListPlaylistsHandler();
        Playlist playlist = new Playlist(testIdPass, "test");
        try {
        	Response<Playlist[]> response = createHandler.handleRequest(playlist, createContext("list"));
			Response<Playlist[]> expectedResponse =  listHandler.handleRequest(null, createContext("list"));
			
			System.out.println(response);
			boolean addedToDatabase = false;
			for(Playlist p: response.getModel()){
				if(p.equals(playlist)){
					addedToDatabase = true;
					break;
				}
			}
			
			
			assertTrue("Response indicates sucess", response.statusCode == 200);
			assertTrue("Segment added to database", addedToDatabase);
			assertArrayEquals("All playlists in database returned", response.getModel(), expectedResponse.getModel());  
		} catch (Exception e) {
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
		}
        
	}
	
	public void testDeleteSegmentHandler(){
    	DeletePlaylistHandler deleteHandler = new DeletePlaylistHandler();
    	ListPlaylistsHandler listHandler = new ListPlaylistsHandler();
        try {
        	Response<Playlist[]> response = deleteHandler.handleRequest(testIdPass, createContext("list"));
			Response<Playlist[]> expectedResponse =  listHandler.handleRequest(null, createContext("list"));
			
			boolean removedFromDatabase = true;
			for(Playlist p: response.getModel()){
				if(p.getId().equals(testIdPass)){
					removedFromDatabase = false;
					break;
				}
			}
			
			assertTrue("Response indicates sucess", response.statusCode == 200);
			assertTrue("Playlist removed from", removedFromDatabase);
			assertArrayEquals("All segments in database returned", response.getModel(), expectedResponse.getModel());  
			
		} catch (Exception e) {
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
		}
        
	}
	
	public void testDeleteSegmentHandlerShouldFail() {
    	DeletePlaylistHandler deleteHandler = new DeletePlaylistHandler();
    	ListPlaylistsHandler listHandler = new ListPlaylistsHandler();
        
        try {
			Response<Playlist[]> databaseBefore =  listHandler.handleRequest(null, createContext("list"));
			Response<Playlist[]> response = deleteHandler.handleRequest(testIdFail, createContext("list"));
        	Response<Playlist[]> databaseAfter =  listHandler.handleRequest(null, createContext("list"));
			
			assertTrue("Response indicates failure", response.getStatusCode() != 200);
			assertArrayEquals("Segments in database didn't change", databaseBefore.getModel(), databaseAfter.getModel());  
        	
        }catch(Exception e){
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
        }

	}
	

}
