package paths.segment;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import definitions.Id;
import definitions.Response;
import definitions.Segment;

import lamnda.LambdaTest;

public class CreateAndDeleteSegmentsHandlersTest extends LambdaTest{
	Id testIdPass = new Id(".ogg");
	Id testIdFail = new Id(".ogg");
	
	@Test
	public void testShouldPass() {
		testCreateSegmentHandler();
		testDeleteSegmentHandler();
	}
	
	@Test
	public void testShouldFail() {
		testCreateSegmentHandlerShouldFail();
		testDeleteSegmentHandlerShouldFail();
	}
	
	public void testCreateSegmentHandler(){
    	CreateSegmentHandler createHandler = new CreateSegmentHandler();
    	ListLocalSegmentsHandler listHandler = new ListLocalSegmentsHandler();
        Segment segment = new Segment(testIdPass, false, "Test upload", "test", "test\\resources\\test_segment.ogg");
        try {
        	Response<Segment[]> response = createHandler.handleRequest(segment, createContext("list"));
			Response<Segment[]> expectedResponse =  listHandler.handleRequest(null, createContext("list"));
			
			boolean addedToDatabase = false;
			for(Segment s: response.getModel()){
				if(s.equals(segment)){
					addedToDatabase = true;
					break;
				}
			}
			
			AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		   
			assertTrue("Segment added into bucket", s3.doesObjectExist("hotspurproject/segments", segment.getId().getId()));
			assertTrue("Segment added to database", addedToDatabase);
			assertArrayEquals("All segments in database returned", response.getModel(), expectedResponse.getModel());  
			
		} catch (Exception e) {
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
		}
        
	}
	
	public void testCreateSegmentHandlerShouldFail() {
    	CreateSegmentHandler createHandler = new CreateSegmentHandler();
    	ListLocalSegmentsHandler listHandler = new ListLocalSegmentsHandler();
        Segment segment = new Segment(testIdFail, false, "Test upload (should fail)", "test"); //no data provided
        
        try {
			Response<Segment[]> databaseBefore =  listHandler.handleRequest(null, createContext("list"));
        	Response<Segment[]> response = createHandler.handleRequest(segment, createContext("list"));
        	Response<Segment[]> databaseAfter =  listHandler.handleRequest(null, createContext("list"));
        	
			boolean addedToDatabase = false;
			for(Segment s: databaseAfter.getModel()){
				if(s.equals(segment)){
					addedToDatabase = true;
					break;
				}
			}
			
			AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			
			assertTrue("Response indicates failure", response.getStatusCode() != 200);
			assertTrue("Segment not added into bucket", !s3.doesObjectExist("hotspurproject/segments", segment.getId().getId()));
			assertTrue("Segment not added to database", !addedToDatabase);
			assertArrayEquals("Segments in database didn't change", databaseBefore.getModel(), databaseAfter.getModel());  
        	
        }catch(Exception e){
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
        }

	}
	
	public void testDeleteSegmentHandler(){
    	DeleteSegmentHandler deleteHandler = new DeleteSegmentHandler();
    	ListLocalSegmentsHandler listHandler = new ListLocalSegmentsHandler();
        try {
        	Response<Segment[]> response = deleteHandler.handleRequest(testIdPass, createContext("list"));
			Response<Segment[]> expectedResponse =  listHandler.handleRequest(null, createContext("list"));
			
			boolean removedFromDatabase = true;
			for(Segment s: response.getModel()){
				if(s.getId().equals(testIdPass)){
					removedFromDatabase = false;
					break;
				}
			}
			
			AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		   
			assertTrue("Segment removed from bucket", !s3.doesObjectExist("hotspurproject/segments", testIdPass.getId()));
			assertTrue("Segment removed from", removedFromDatabase);
			assertArrayEquals("All segments in database returned", response.getModel(), expectedResponse.getModel());  
			
		} catch (Exception e) {
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
		}
        
	}
	
	public void testDeleteSegmentHandlerShouldFail() {
		DeleteSegmentHandler deleteHandler = new DeleteSegmentHandler();
    	ListLocalSegmentsHandler listHandler = new ListLocalSegmentsHandler();
        
        try {
			Response<Segment[]> databaseBefore =  listHandler.handleRequest(null, createContext("list"));
			Response<Segment[]> response = deleteHandler.handleRequest(testIdFail, createContext("list"));
        	Response<Segment[]> databaseAfter =  listHandler.handleRequest(null, createContext("list"));
			
			assertTrue("Response indicates failure", response.getStatusCode() != 200);
			assertArrayEquals("Segments in database didn't change", databaseBefore.getModel(), databaseAfter.getModel());  
        	
        }catch(Exception e){
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
        }

	}

}
