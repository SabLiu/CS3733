package databases;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import definitions.Playlist;
import definitions.Segment;
import definitions.Id;

public class PlaylistDAOTest {

	@Test
	public void getPlaylistsTest() {
		List<Playlist> controllerPlaylists = new ArrayList<>();
		List<Id> id = new ArrayList<>();
		id.add(new Id("339cee8c-fdc5-4e72-8aaa-a23969877969"));
		id.add(new Id("413c71ec-d5a0-4433-b491-9f4e557bbbc8"));
		id.add(new Id("c136cdf2-5877-4e86-b6c7-e72b3f05ea9b"));
		id.add(new Id("fc11d60f-c6f1-4138-a0b1-cb7fc2010e9d"));
		controllerPlaylists.add(new Playlist(id.get(0), ""));
		controllerPlaylists.add(new Playlist(id.get(1), ""));
		controllerPlaylists.add(new Playlist(id.get(2), ""));
		controllerPlaylists.add(new Playlist(id.get(3), ""));
		
		
		PlaylistDAO getter = new PlaylistDAO();
		List<Playlist> gottenPlaylists = new ArrayList<>();
		try{
			gottenPlaylists = getter.getAllPlaylists();
			System.out.println(gottenPlaylists);
			int i = 0;
			while(i<controllerPlaylists.size()) {
				if(!gottenPlaylists.get(i).equals(gottenPlaylists.get(i))) {
					fail();
				}
				i++;
			}
			assertEquals(true, true);
		}catch(Exception e){
			System.out.println("exception");
			System.out.println(e.getMessage());
			fail(); 
		}
				
	}
	
	@Test
	public void addEmptyPlaylistTest() {
		Id idEmpty = new Id("d53f987e-1615-4948-90c4-13f630ad72f2");
		String nameEmpty = "testEmptyPL";
		Playlist emptyPlaylist = new Playlist(idEmpty, nameEmpty);
		//Segment sentSegment = new Segment(id, false, "testing testing 123", "erich", "test.ogg");
		PlaylistDAO setter = new PlaylistDAO();
		try {	
			boolean set = setter.addPlaylist(emptyPlaylist);
			Playlist returnedPlaylistEmpty = setter.getFullPlaylist(idEmpty);
			boolean didSetEmptyPass = returnedPlaylistEmpty.equals(emptyPlaylist);
			boolean didTryToSetEmptyPass = false;
			if(didSetEmptyPass) {
				didTryToSetEmptyPass = !setter.addPlaylist(emptyPlaylist); 
			}
			assertTrue(didTryToSetEmptyPass && didSetEmptyPass && set);
		}catch(Exception e){
			assertEquals(false, true);
		}		
	}
	
	@Test
	public void addFilledPlaylistTest() {
		
		List<Segment> controllerSegments = new ArrayList<>();
		List<Id> id = new ArrayList<>();
		id.add(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		id.add(new Id("72ba5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		id.add(new Id("c9314e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		id.add(new Id("d4117ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		id.add(new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		
		controllerSegments.add(new Segment(id.get(0), false, "You had a normal emotion", "McCoy", "Two.ogg"));
		controllerSegments.add(new Segment(id.get(1), false, "Do you smell something?", "Spock", "Six.ogg"));
		controllerSegments.add(new Segment(id.get(2), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy", "Ten.ogg"));
		controllerSegments.add(new Segment(id.get(3), false, "You know, self-pity is a terrible first course", "Chapel", "Three.ogg"));
		controllerSegments.add(new Segment(id.get(4), false, "I know you would prefer to wallow in a pool of emotion", "Spock", "Four.ogg"));
		
		Segment[] segs = {controllerSegments.get(0), controllerSegments.get(1),
				controllerSegments.get(2), controllerSegments.get(3), controllerSegments.get(4)};
		
		
		
		
		
		Id idFull = new Id("h12f987e-4209-6969-90c4-13f630ad72f2");
		String nameFull = "testFullPL";

		Playlist fullPlaylist = new Playlist(idFull, nameFull, segs);
		//Segment sentSegment = new Segment(id, false, "testing testing 123", "erich", "test.ogg");
		PlaylistDAO setter = new PlaylistDAO();
		try {	
			boolean set = setter.addPlaylist(fullPlaylist);
			Playlist returnedPlaylistEmpty = setter.getFullPlaylist(idFull);
			boolean didSetPass = returnedPlaylistEmpty.equals(fullPlaylist);
			boolean didTryToSetPass = false;
			if(didSetPass) {
				didTryToSetPass = !setter.addPlaylist(fullPlaylist); 
			}
			assertTrue(didTryToSetPass && didSetPass && set);
		}catch(Exception e){
			assertEquals(false, true);
		}		
	}
	
		
	@Test
	public void deletePlaylistTestOne() {
		Id idEmpty = new Id("d53f987e-1615-4948-90c4-13f630ad72f2");
		String nameEmpty = "testEmptyPL";
		Playlist emptyPlaylist = new Playlist(idEmpty, nameEmpty);
		PlaylistDAO deleter = new PlaylistDAO();
		List<Playlist> gottenPlaylistsBeforDelete = new ArrayList<>();
		List<Playlist> gottenPlaylistsAfterDelete = new ArrayList<>();
		int difference = 0;
		boolean oneAffected = false;
		try {	
			gottenPlaylistsBeforDelete = deleter.getAllPlaylists();
			int lengthBefor = gottenPlaylistsBeforDelete.size();
			oneAffected = deleter.deletePlaylist(emptyPlaylist);
			gottenPlaylistsAfterDelete = deleter.getAllPlaylists();
			int lengthAfter = gottenPlaylistsAfterDelete.size();
			difference = lengthBefor - lengthAfter;
		}catch(Exception e){
			assertEquals(false, true);
		}
		
		try {

			Playlist returnedPlaylist = deleter.getFullPlaylist(idEmpty);
			assertTrue(false);
		}catch(Exception e) {
			//Should throw an exception if there isn't a playlist there
			assertTrue(difference == 1 && oneAffected);
		}
	}
	
	@Test
	public void deletePlaylistTestTwo() {
		Id idFull = new Id("h12f987e-4209-6969-90c4-13f630ad72f2");
		PlaylistDAO deleter = new PlaylistDAO();
		List<Playlist> gottenPlaylistsBeforDelete = new ArrayList<>();
		List<Playlist> gottenPlaylistsAfterDelete = new ArrayList<>();
		int difference = 0;
		boolean oneAffected = false;
		try {	
			gottenPlaylistsBeforDelete = deleter.getAllPlaylists();
			int lengthBefor = gottenPlaylistsBeforDelete.size();
			oneAffected = deleter.deletePlaylist(idFull);
			gottenPlaylistsAfterDelete = deleter.getAllPlaylists();
			int lengthAfter = gottenPlaylistsAfterDelete.size();
			difference = lengthBefor - lengthAfter;
		}catch(Exception e){
			assertEquals(false, true);
		}
		
		try {

			Playlist returnedPlaylist = deleter.getFullPlaylist(idFull);
			assertTrue(false);
		}catch(Exception e) {
			//Should throw an exception if there isn't a playlist there
			assertTrue(difference == 1 && oneAffected);
		}
	}
	
	@Test
	public void appendToPlaylistTestOne() {
		List<Segment> controllerSegmentsStart = new ArrayList<>();
		List<Id> idStart = new ArrayList<>();
		idStart.add(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idStart.add(new Id("72ba5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idStart.add(new Id("c9314e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		idStart.add(new Id("d4117ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idStart.add(new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		
		controllerSegmentsStart.add(new Segment(idStart.get(0), false, "You had a normal emotion", "McCoy", "Two.ogg"));
		controllerSegmentsStart.add(new Segment(idStart.get(1), false, "Do you smell something?", "Spock", "Six.ogg"));
		controllerSegmentsStart.add(new Segment(idStart.get(2), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy", "Ten.ogg"));
		controllerSegmentsStart.add(new Segment(idStart.get(3), false, "You know, self-pity is a terrible first course", "Chapel", "Three.ogg"));
		controllerSegmentsStart.add(new Segment(idStart.get(4), false, "I know you would prefer to wallow in a pool of emotion", "Spock", "Four.ogg"));
		
		Segment[] segsStart = {controllerSegmentsStart.get(0), controllerSegmentsStart.get(1),
				controllerSegmentsStart.get(2), controllerSegmentsStart.get(3), controllerSegmentsStart.get(4)};
		
		
		List<Segment> controllerSegmentsEnd = new ArrayList<>();
		List<Id> idEnd = new ArrayList<>();
		idEnd.add(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idEnd.add(new Id("72ba5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idEnd.add(new Id("c9314e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		idEnd.add(new Id("d4117ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idEnd.add(new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		idEnd.add(new Id("d16d709b-5b90-48f8-a3c4-57acb0062a0c.ogg"));
		
		controllerSegmentsEnd.add(new Segment(idEnd.get(0), false, "You had a normal emotion", "McCoy", "Two.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(1), false, "Do you smell something?", "Spock", "Six.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(2), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy", "Ten.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(3), false, "You know, self-pity is a terrible first course", "Chapel", "Three.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(4), false, "I know you would prefer to wallow in a pool of emotion", "Spock", "Four.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(5), false, "Mr. Spock, why aren’t you dead?", "Kirk", "Seven.ogg"));
		
		Segment[] segsEnd = {controllerSegmentsEnd.get(0), controllerSegmentsEnd.get(1),
				controllerSegmentsEnd.get(2), controllerSegmentsEnd.get(3), controllerSegmentsEnd.get(4), controllerSegmentsEnd.get(5)};
		
		
		Id id = new Id("t7659m486e-6969-4023-90c4-13f630ad72f2");
		String nameAppendTo = "testAppendPL";

		Playlist startedPlaylist = new Playlist(id, nameAppendTo, segsStart);
		Playlist appendedPlaylist = new Playlist(id, nameAppendTo, segsEnd);
		
		PlaylistDAO appender = new PlaylistDAO();
		try {
			appender.addPlaylist(startedPlaylist);
			boolean appendedOne = appender.appendToPlaylist(appendedPlaylist);
			Playlist returnedPlaylist = appender.getFullPlaylist(id);
			appender.deletePlaylist(id);
			assertTrue(appendedOne && appendedPlaylist.equals(returnedPlaylist));
		}catch(Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void appendToPlaylistTestTwo() {
		List<Segment> controllerSegmentsStart = new ArrayList<>();
		List<Id> idStart = new ArrayList<>();
		idStart.add(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idStart.add(new Id("72ba5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idStart.add(new Id("c9314e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		idStart.add(new Id("d4117ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idStart.add(new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		
		controllerSegmentsStart.add(new Segment(idStart.get(0), false, "You had a normal emotion", "McCoy", "Two.ogg"));
		controllerSegmentsStart.add(new Segment(idStart.get(1), false, "Do you smell something?", "Spock", "Six.ogg"));
		controllerSegmentsStart.add(new Segment(idStart.get(2), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy", "Ten.ogg"));
		controllerSegmentsStart.add(new Segment(idStart.get(3), false, "You know, self-pity is a terrible first course", "Chapel", "Three.ogg"));
		controllerSegmentsStart.add(new Segment(idStart.get(4), false, "I know you would prefer to wallow in a pool of emotion", "Spock", "Four.ogg"));
		
		Segment[] segsStart = {controllerSegmentsStart.get(0), controllerSegmentsStart.get(1),
				controllerSegmentsStart.get(2), controllerSegmentsStart.get(3), controllerSegmentsStart.get(4)};
		
		
		List<Segment> controllerSegmentsEnd = new ArrayList<>();
		List<Id> idEnd = new ArrayList<>();
		idEnd.add(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idEnd.add(new Id("72ba5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idEnd.add(new Id("c9314e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		idEnd.add(new Id("d4117ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idEnd.add(new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		idEnd.add(new Id("d16d709b-5b90-48f8-a3c4-57acb0062a0c.ogg"));
		
		controllerSegmentsEnd.add(new Segment(idEnd.get(0), false, "You had a normal emotion", "McCoy", "Two.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(1), false, "Do you smell something?", "Spock", "Six.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(2), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy", "Ten.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(3), false, "You know, self-pity is a terrible first course", "Chapel", "Three.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(4), false, "I know you would prefer to wallow in a pool of emotion", "Spock", "Four.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(5), false, "Mr. Spock, why aren’t you dead?", "Kirk", "Seven.ogg"));
		
		Segment[] segsEnd = {controllerSegmentsEnd.get(0), controllerSegmentsEnd.get(1),
				controllerSegmentsEnd.get(2), controllerSegmentsEnd.get(3), controllerSegmentsEnd.get(4), controllerSegmentsEnd.get(5)};
		
		
		Id id = new Id("t7659m486e-6969-4023-90c4-13f630ad72f2");
		String nameAppendTo = "testAppendPL";

		Playlist startedPlaylist = new Playlist(id, nameAppendTo, segsStart);
		Playlist appendedPlaylist = new Playlist(id, nameAppendTo, segsEnd);
		
		PlaylistDAO appender = new PlaylistDAO();
		try {
			appender.addPlaylist(startedPlaylist);
			boolean appendedOne = appender.appendToPlaylist(id, new Id("d16d709b-5b90-48f8-a3c4-57acb0062a0c.ogg"));
			Playlist returnedPlaylist = appender.getFullPlaylist(id);
			appender.deletePlaylist(id);
			assertTrue(appendedOne && appendedPlaylist.equals(returnedPlaylist));
		}catch(Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void appendToPlaylistTestThree() {
				
		List<Segment> controllerSegmentsEnd = new ArrayList<>();
		List<Id> idEnd = new ArrayList<>();
		idEnd.add(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idEnd.add(new Id("72ba5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idEnd.add(new Id("c9314e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		idEnd.add(new Id("d4117ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idEnd.add(new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		idEnd.add(new Id("d16d709b-5b90-48f8-a3c4-57acb0062a0c.ogg"));
		
		controllerSegmentsEnd.add(new Segment(idEnd.get(0), false, "You had a normal emotion", "McCoy", "Two.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(1), false, "Do you smell something?", "Spock", "Six.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(2), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy", "Ten.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(3), false, "You know, self-pity is a terrible first course", "Chapel", "Three.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(4), false, "I know you would prefer to wallow in a pool of emotion", "Spock", "Four.ogg"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(5), false, "Mr. Spock, why aren’t you dead?", "Kirk", "Seven.ogg"));
		
		Segment[] segsEnd = {controllerSegmentsEnd.get(0), controllerSegmentsEnd.get(1),
				controllerSegmentsEnd.get(2), controllerSegmentsEnd.get(3), controllerSegmentsEnd.get(4), controllerSegmentsEnd.get(5)};
		
		
		Id id = new Id("j4567h586y-5555-6666-90c4-13f630ad72f2");
		String nameAppendTo = "testAppendPL";
		Playlist startPlaylist = new Playlist(id, nameAppendTo);
		Playlist appendedPlaylist = new Playlist(id, nameAppendTo, segsEnd);
		
		PlaylistDAO appender = new PlaylistDAO();
		try {
			appender.addPlaylist(startPlaylist);
			boolean appendedOne = appender.appendToPlaylist(id, idEnd.get(0));
			boolean appendedTwo = appender.appendToPlaylist(id, idEnd.get(1));
			boolean appendedThree = appender.appendToPlaylist(id, idEnd.get(2));
			boolean appendedFour = appender.appendToPlaylist(id, idEnd.get(3));
			boolean appendedFive = appender.appendToPlaylist(id, idEnd.get(4));
			boolean appendedSix = appender.appendToPlaylist(id, idEnd.get(5));
			
			boolean allAppended = appendedOne && appendedTwo && appendedThree && appendedFour && appendedFive && appendedSix;
			
			Playlist returnedPlaylist = appender.getFullPlaylist(id);
			appender.deletePlaylist(id);
			assertTrue(allAppended && appendedPlaylist.equals(returnedPlaylist));
		}catch(Exception e) {
			assertTrue(false);
		}
		assertTrue(true);
	}
	
}

