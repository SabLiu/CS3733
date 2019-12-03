package paths.site;

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
import definitions.Site;
import lamnda.LambdaTest;

public class CreateAndDeleteSiteHandlersTest extends LambdaTest{
	Id testIdPass;
	Id testIdFail = new Id();
	
	@Test
	public void testShouldPass() {
		testCreateSegmentHandler();
		testDeleteSegmentHandler();
	}
	 
	@Test 
	public void testShouldFail() {
		testDeleteSegmentHandlerShouldFail();
	}
	
	public void testCreateSegmentHandler(){
    	RegisterRemoteSiteHandler resgisterHandler = new RegisterRemoteSiteHandler();
    	ListRemoteSitesHandler listHandler = new ListRemoteSitesHandler();
        Site site = new Site("bing.com");
        try {
        	Response<Site[]> response = resgisterHandler.handleRequest(site, createContext("list"));
			Response<Site[]> expectedResponse =  listHandler.handleRequest(null, createContext("list"));
			
			testIdPass = site.getId();
			
			System.out.println(response);
			boolean addedToDatabase = false;
			for(Site s: response.getModel()){
				if(s.getId().equals(testIdPass)){
					addedToDatabase = true;
					break;
				}
			}
			
			
			assertTrue("Response indicates sucess", response.statusCode == 200);
			assertTrue("Site added to database", addedToDatabase);
			assertArrayEquals("All sites in database returned", response.getModel(), expectedResponse.getModel());  
		} catch (Exception e) {
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
		}
        
	}
	
	public void testDeleteSegmentHandler(){
    	RemoveRemoteSiteHandler removeHandler = new RemoveRemoteSiteHandler();
    	ListRemoteSitesHandler listHandler = new ListRemoteSitesHandler();
        try {
        	Response<Site[]> response = removeHandler.handleRequest(testIdPass, createContext("list"));
			Response<Site[]> expectedResponse =  listHandler.handleRequest(null, createContext("list"));
			
			boolean removedFromDatabase = true;
			for(Site s: response.getModel()){
				if(s.getId().equals(testIdPass)){
					removedFromDatabase = false;
					break;
				}
			}
			
			assertTrue("Response indicates sucess", response.statusCode == 200);
			assertTrue("Site removed from database", removedFromDatabase);
			assertArrayEquals("All sites in database returned", response.getModel(), expectedResponse.getModel());  
			
		} catch (Exception e) {
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
		}
        
	}
	
	public void testDeleteSegmentHandlerShouldFail() {
    	RemoveRemoteSiteHandler removeHandler = new RemoveRemoteSiteHandler();
    	ListRemoteSitesHandler listHandler = new ListRemoteSitesHandler();
        
        try {
			Response<Site[]> databaseBefore =  listHandler.handleRequest(null, createContext("list"));
			Response<Site[]> response = removeHandler.handleRequest(testIdFail, createContext("list"));
        	Response<Site[]> databaseAfter =  listHandler.handleRequest(null, createContext("list"));
			
			assertTrue("Response indicates failure", response.getStatusCode() != 200);
			assertArrayEquals("Sites in database didn't change", databaseBefore.getModel(), databaseAfter.getModel());  
        	
        }catch(Exception e){
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
        }

	}
	

}
