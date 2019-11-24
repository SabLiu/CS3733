package databases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import databases.SegmentsDAO;
import definitions.Id;
import definitions.Segment;

public class GetSegmentsTest {

	@Test
	public void test() {
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
		
		controllerSegments.add(new Segment(id.get(0), false, "You had a normal emotion", "McCoy", "Two.ogg"));
		controllerSegments.add(new Segment(id.get(1), false, "Then I need a drink", "McCoy", "One.ogg"));
		controllerSegments.add(new Segment(id.get(2), false, "Colloquially expressed, but essentially correct.", "Spock", "Eight.ogg"));
		controllerSegments.add(new Segment(id.get(3), false, "Engineering to Captain Kirk", "Scotty", "Nine.ogg"));
		controllerSegments.add(new Segment(id.get(4), false, "Do you smell something?", "Spock", "Six.ogg"));
		controllerSegments.add(new Segment(id.get(5), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy", "Ten.ogg"));
		controllerSegments.add(new Segment(id.get(6), false, "Kindness, Mr. Garrovick, is another human emotion, and I believe we have enough of that.", "Spock", "Five.ogg"));
		controllerSegments.add(new Segment(id.get(7), false, "Mr. Spock, why aren’t you dead?", "Kirk", "Seven.ogg"));
		controllerSegments.add(new Segment(id.get(8), false, "You know, self-pity is a terrible first course", "Chapel", "Three.ogg"));
		controllerSegments.add(new Segment(id.get(9), false, "I know you would prefer to wallow in a pool of emotion", "Spock", "Four.ogg"));
		
		SegmentsDAO getter = new SegmentsDAO();
		List<Segment> gottenSegments = new ArrayList<>();
		try{
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

}
