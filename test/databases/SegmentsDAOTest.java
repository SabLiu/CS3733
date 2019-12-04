package databases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.amazonaws.services.s3.model.Encryption;

import databases.SegmentDAO;
import definitions.Id;
import definitions.Segment;

public class SegmentsDAOTest {
   // (Id id, boolean isRemotelyAvailable, String sentence, String character, String videoFileAddress) 	
	/**
	 * Tests get all the segments function(doesn't pass now bc the database changed)
	 */
	@Test
	public void getSegmentsTest() {
		//make the control data
		List<Segment> controllerSegments = new ArrayList<>();
		List<Id> id = new ArrayList<>();
		id.add(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		id.add(new Id("3c4bdc3a-a3f5-4f39-bf3a-b7f65fa9399b.ogg"));
		id.add(new Id("3e3b9c56-1a2d-45ed-b676-29de0f4e4486.ogg"));
		id.add(new Id("525b2e69-5140-47d9-b2d5-ff7bb9f67d5e.ogg"));
		id.add(new Id("72ba5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		id.add(new Id("c9314e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		id.add(new Id("cf7ccbaa-321f-4365-ab2d-1aabc7354f23.ogg"));
		id.add(new Id("d16d709b-5b90-48f8-a3c4-57acb0062a0c.ogg"));
		id.add(new Id("d4117ef5-72bf-46c3-be31-d6a1f275194a.ogg")); 
		id.add(new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		
		controllerSegments.add(new Segment(id.get(0), false, "You had a normal emotion", "McCoy"));
		controllerSegments.add(new Segment(id.get(1), false, "Then I need a drink", "McCoy"));
		controllerSegments.add(new Segment(id.get(2), false, "Colloquially expressed, but essentially correct.", "Spock"));
		controllerSegments.add(new Segment(id.get(3), false, "Engineering to Captain Kirk", "Scotty"));
		controllerSegments.add(new Segment(id.get(4), false, "Do you smell something?", "Spock"));
		controllerSegments.add(new Segment(id.get(5), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy"));
		controllerSegments.add(new Segment(id.get(6), false, "Kindness, Mr. Garrovick, is another human emotion, and I believe we have enough of that.", "Spock"));
		controllerSegments.add(new Segment(id.get(7), false, "Mr. Spock, why aren’t you dead?", "Kirk"));
		controllerSegments.add(new Segment(id.get(8), false, "You know, self-pity is a terrible first course", "Chapel"));
		controllerSegments.add(new Segment(id.get(9), false, "I know you would prefer to wallow in a pool of emotion", "Spock"));
		 
		SegmentDAO getter = new SegmentDAO();
		List<Segment> gottenSegments = new ArrayList<>();
		try{
			//test
			gottenSegments = getter.getAllLocalSegments();
			int i = 0;
			while(i<10) {
				if(!gottenSegments.get(i).equals(controllerSegments.get(i))) {
					System.out.println(gottenSegments.get(i) + "\n" + controllerSegments.get(i));
					assertEquals(false, true);
				}
				i++;
			}
			assertEquals(true, true);
		}catch(Exception e){
			System.out.println("exception");
			System.out.println(e.getMessage());
			assertEquals(false, true);
		}
				
	}
	
	/**
	 * Test the get segment function
	 */
	@Test
	public void getSegmentTestLocal() {
		String testID = "c9314e2c-68df-48ec-af09-de17bac46ecd.ogg";
		Segment controlSegment = new Segment(new Id(testID), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy");

		SegmentDAO getter = new SegmentDAO();
		try {
			Segment returnedSegment = getter.getSegment(new Id(testID));
			System.out.println(returnedSegment);
			assertTrue(returnedSegment.equals(controlSegment));
		}catch(Exception e) {
			System.out.print("Exception: ");
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Tests the addsegment function
	 */
	@Test
	public void addSegmentsTest() {
		Id id = new Id("d92c327e-1615-4869-90c4-13f797ad72f2.ogg");
		Segment sentSegment = new Segment(id, false, "testing testing 123", "erich");
		SegmentDAO setter = new SegmentDAO();
		try {	
			setter.addSegment(sentSegment);
			Segment returnedSegment = setter.getSegment(id);
			boolean didSetPass = returnedSegment.equals(sentSegment);
			boolean didTryToSetPass = false;
			if(didSetPass) {
				//makes sure you cant add it twice
				didTryToSetPass = !setter.addSegment(sentSegment); 
			}
			assertTrue(didTryToSetPass && didSetPass);
		}catch(Exception e){
			System.out.print("Exception: ");
			System.out.println(e.getMessage());
			fail();
		}		
	}
	
	/**
	 * Tests the delete segment function that takes in a segment
	 */
	@Test
	public void deleteSegmentsTest() {
		Id id = new Id("d92c327e-1615-4869-90c4-13f797ad72f2.ogg");
		Segment deletedSegment = new Segment(id, false, "testing testing 123", "erich");
		SegmentDAO deleter = new SegmentDAO();
		List<Segment> gottenSegmentsBeforDelete = new ArrayList<>();
		List<Segment> gottenSegmentsAfterDelete = new ArrayList<>();
		int difference  = -1;
		boolean oneAffected = false;
		try {	
			gottenSegmentsBeforDelete = deleter.getAllLocalSegments();
			int lengthBefor = gottenSegmentsBeforDelete.size();
			oneAffected = deleter.deleteSegment(deletedSegment);
			gottenSegmentsAfterDelete = deleter.getAllLocalSegments();
			int lengthAfter = gottenSegmentsAfterDelete.size();
			//the number of segments deleted
			difference = lengthBefor - lengthAfter;
		}catch(Exception e){
			assertEquals(false, true);
		}
		try {

			Segment returnedSegment = deleter.getSegment(id);
			assertTrue(false);
		}catch(Exception e) {
			//Should throw an exception if there isn't a segment there
			assertTrue(difference == 1 && oneAffected);
		}
	}
	
	/**
	 * Tests the delete segment function that takes in an Id
	 */
	@Test
	public void deleteSegmentsTestTwo() {
		Id id = new Id("d92c327e-1615-4869-90c4-13f797ad72f2.ogg");
		Segment deletedSegment = new Segment(id, false, "testing testing 123", "erich");
		SegmentDAO deleter = new SegmentDAO();
		List<Segment> gottenSegmentsBeforDelete = new ArrayList<>();
		List<Segment> gottenSegmentsAfterDelete = new ArrayList<>();
		int difference  = -1;
		boolean oneAffected = false;
		try {	
			deleter.addSegment(deletedSegment);
			gottenSegmentsBeforDelete = deleter.getAllLocalSegments();
			int lengthBefor = gottenSegmentsBeforDelete.size();
			oneAffected = deleter.deleteSegment(id);
			gottenSegmentsAfterDelete = deleter.getAllLocalSegments();
			int lengthAfter = gottenSegmentsAfterDelete.size();
			//the number of segments deleted
			difference = lengthBefor - lengthAfter;
		}catch(Exception e){
			assertEquals(false, true);
		}
		try {

			Segment returnedSegment = deleter.getSegment(id);
			assertTrue(false);
		}catch(Exception e) {
			//Should throw an exception if there isn't a segment there
			assertTrue(difference == 1 && oneAffected);
		}
	}
	
	/**
	 * Tests the search by character function
	 */
	@Test
	public void searchCharacterTest() {
		List<Segment> controllerSegments = new ArrayList<>();
		List<Id> id = new ArrayList<>();
		id.add(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		id.add(new Id("3c4bdc3a-a3f5-4f39-bf3a-b7f65fa9399b.ogg"));
		id.add(new Id("3e3b9c56-1a2d-45ed-b676-29de0f4e4486.ogg"));
		id.add(new Id("525b2e69-5140-47d9-b2d5-ff7bb9f67d5e.ogg"));
		id.add(new Id("72ba5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		id.add(new Id("c9314e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		id.add(new Id("cf7ccbaa-321f-4365-ab2d-1aabc7354f23.ogg"));
		id.add(new Id("d16d709b-5b90-48f8-a3c4-57acb0062a0c.ogg"));
		id.add(new Id("d4117ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		id.add(new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		
		controllerSegments.add(new Segment(id.get(0), false, "You had a normal emotion", "McCoy"));
		controllerSegments.add(new Segment(id.get(1), false, "Then I need a drink", "McCoy"));
		controllerSegments.add(new Segment(id.get(5), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy"));
		
		controllerSegments.add(new Segment(id.get(2), false, "Colloquially expressed, but essentially correct.", "Spock"));
		controllerSegments.add(new Segment(id.get(4), false, "Do you smell something?", "Spock"));
		controllerSegments.add(new Segment(id.get(6), false, "Kindness, Mr. Garrovick, is another human emotion, and I believe we have enough of that.", "Spock"));
		controllerSegments.add(new Segment(id.get(9), false, "I know you would prefer to wallow in a pool of emotion", "Spock"));
		
		controllerSegments.add(new Segment(id.get(3), false, "Engineering to Captain Kirk", "Scotty"));
		
		controllerSegments.add(new Segment(id.get(7), false, "Mr. Spock, why aren’t you dead?", "Kirk"));
		controllerSegments.add(new Segment(id.get(8), false, "You know, self-pity is a terrible first course", "Chapel"));
		SegmentDAO tester = new SegmentDAO();
		//set the control arrays
		List<Segment> McCoyControllerSegments = new ArrayList<>();
		McCoyControllerSegments.add(controllerSegments.get(0));
		McCoyControllerSegments.add(controllerSegments.get(1));
		McCoyControllerSegments.add(controllerSegments.get(2));
		
		List<Segment> SpockControllerSegments = new ArrayList<>();
		SpockControllerSegments.add(controllerSegments.get(3));
		SpockControllerSegments.add(controllerSegments.get(4));
		SpockControllerSegments.add(controllerSegments.get(5));
		SpockControllerSegments.add(controllerSegments.get(6));
		
		List<Segment> ScottyControllerSegments = new ArrayList<>();
		ScottyControllerSegments.add(controllerSegments.get(7));
		
		List<Segment> gottenSegments = new ArrayList<>();
		
		try {
			//test on a few characters
			gottenSegments = tester.searchSegmentCharacter("McCoy");
			int i = 0;
			boolean ta = true;
			while(i<gottenSegments.size()) {
				if(!gottenSegments.get(i).equals(McCoyControllerSegments.get(i))) {
					ta = false;
				}
				i++;
			}
			gottenSegments = tester.searchSegmentCharacter("Spock");
			i = 0;
			boolean tb = true;
			while(i<gottenSegments.size()) {
				if(!gottenSegments.get(i).equals(SpockControllerSegments.get(i))) {
					tb = false;
				}
				i++;
			}
			gottenSegments = tester.searchSegmentCharacter("Scotty");
			i = 0;
			boolean tc = true;
			while(i<gottenSegments.size()) {
				if(!gottenSegments.get(i).equals(ScottyControllerSegments.get(i))) {
					tc = false;
				}
				i++;
			}
			gottenSegments = tester.searchSegmentCharacter("bill");
			boolean td = gottenSegments.size() == 0;
			assertTrue(ta && tb && tc && td);
		}catch(Exception e) {
			fail("exception");
		}
	}
	
	/**
	 * Tests the MarkSegment function
	 */
	@Test
	public void testMarkSegment() {
		//control data
		Id i = new Id("f45j87yt-6666-6666-6666-2lolwaba4uwu.ogg");
		Segment test = new Segment(i, false, "that's what", "she");
		SegmentDAO tester = new SegmentDAO();
		Segment ret;
		try {
			//add the segment
			tester.addSegment(test);
			ret = tester.getSegment(i);
			if(!test.equals(ret)) {
				fail("couldn't add");
			}else {
				//it was uploaded
				//mark it true, mark it false chek that they worked delete to clean out the database
				tester.markSegment(i, true);
				ret = tester.getSegment(i);
				boolean testOne = ret.isRemotelyAvailable();
				tester.markSegment(i, false);
				ret = tester.getSegment(i);
				boolean testTwo = !ret.isRemotelyAvailable();
				tester.deleteSegment(i);
				assertTrue(testOne && testTwo);
			}
		}catch(Exception e) {
			fail("exception");
		}
	}

}
