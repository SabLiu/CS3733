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
	
	/**
	 * Tests the function that gets all the playlists in the database
	 * used as one of our first tests(no longer works)
	 */
	/*
	@Test
	public void getPlaylistsTest() {
		//Controle data
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
		
		//test
		PlaylistDAO getter = new PlaylistDAO();
		List<Playlist> gottenPlaylists = new ArrayList<>();
		try{
			gottenPlaylists = getter.getAllPlaylists(); 
			int i = 0;
			while(i<controllerPlaylists.size()) {
				if(!gottenPlaylists.get(i).equals(controllerPlaylists.get(i))) {
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
	*/
	
	/**
	 * Tests adding an empty playlist to the database
	 */
	@Test
	public void addEmptyPlaylistTest() {
		//playlist construction
		Id idEmpty = new Id("d53f987e-1615-4948-90c4-13f630ad72f2");
		String nameEmpty = "testEmptyPL";
		Playlist emptyPlaylist = new Playlist();
		emptyPlaylist.setName(nameEmpty);
		emptyPlaylist.setId(idEmpty);
		//Segment sentSegment = new Segment(id, false, "testing testing 123", "erich", "test.ogg");
		//test
		PlaylistDAO setter = new PlaylistDAO();
		try {	
			//add
			boolean set = setter.addPlaylist(emptyPlaylist);
			//get
			Playlist returnedPlaylistEmpty = setter.getFullPlaylist(idEmpty);
			boolean didSetEmptyPass = returnedPlaylistEmpty.equals(emptyPlaylist);
			System.out.println("returned\n" + returnedPlaylistEmpty.toString() + " ");
			System.out.println("returned\n" + emptyPlaylist.toString() + " ");
			boolean didTryToSetEmptyPass = false;
			if(didSetEmptyPass) {
				didTryToSetEmptyPass = !setter.addPlaylist(emptyPlaylist); 
			}
			assertTrue(didTryToSetEmptyPass && didSetEmptyPass && set);
		}catch(Exception e){
			assertEquals(false, true);
		}		
	}
	
	/**
	 * Tests adding a full playlist to the database
	 */
	@Test
	public void addFilledPlaylistTest() {
		//controle data
		List<Segment> controllerSegments = new ArrayList<>();
		List<Id> id = new ArrayList<>();
		id.add(new Id("test1234-9077-450e-9c94-21f2eaba4e7b.ogg"));
		id.add(new Id("test1234-9180-4976-b24d-93cdc98ff6cc.ogg"));
		id.add(new Id("test1234-68df-48ec-af09-de17bac46ecd.ogg"));
		id.add(new Id("test1234-72bf-46c3-be31-d6a1f275194a.ogg"));
		id.add(new Id("test1234-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		
		controllerSegments.add(new Segment(id.get(0), false, "You  a normal emotion", "Mcoy"));
		controllerSegments.add(new Segment(id.get(1), false, "Do you  something?", "Spck"));
		controllerSegments.add(new Segment(id.get(2), false, "Crazy way to , spreading a man's molecules all over the universe.", "MCoy"));
		controllerSegments.add(new Segment(id.get(3), false, "You know, self- is a terrible first course", "Chael"));
		controllerSegments.add(new Segment(id.get(4), false, "I know you would  to wallow in a pool of emotion", "Spck"));
		
		Segment[] segs = {controllerSegments.get(0), controllerSegments.get(1),
				controllerSegments.get(2), controllerSegments.get(3), controllerSegments.get(4)};
		
		
		
		
		
		Id idFull = new Id("h12f987e-4209-6969-90c4-13f630ad72f2");
		String nameFull = "testFullPL";

		Playlist fullPlaylist = new Playlist(idFull, nameFull);
		fullPlaylist.setSegments(segs);
		//Segment sentSegment = new Segment(id, false, "testing testing 123", "erich", "test.ogg");
		//test
		PlaylistDAO setter = new PlaylistDAO();
		SegmentDAO helper = new SegmentDAO();
		try {	
			//add the segments to the library
			helper.addSegment(segs[0]);
			helper.addSegment(segs[1]);
			helper.addSegment(segs[2]);
			helper.addSegment(segs[3]);
			helper.addSegment(segs[4]);
			//add playlist
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
	
	/**
	 * Tests the delete playlist function that takes in a playlist also deletes the playlist from the earlier test
	 */	
	@Test
	public void deletePlaylistTestOne() {
		//playlist from the first test
		Id idEmpty = new Id("d53f987e-1615-4948-90c4-13f630ad72f2");
		String nameEmpty = "testEmptyPL";
		Playlist emptyPlaylist = new Playlist(idEmpty, nameEmpty);
		PlaylistDAO deleter = new PlaylistDAO();
		List<Playlist> gottenPlaylistsBeforDelete = new ArrayList<>();
		List<Playlist> gottenPlaylistsAfterDelete = new ArrayList<>();
		int difference = 0;
		boolean oneAffected = false;
		try {	
			//delete the playlist to test and clean up the database
			gottenPlaylistsBeforDelete = deleter.getAllPlaylists();
			int lengthBefor = gottenPlaylistsBeforDelete.size();
			oneAffected = deleter.deletePlaylist(emptyPlaylist);
			gottenPlaylistsAfterDelete = deleter.getAllPlaylists();
			int lengthAfter = gottenPlaylistsAfterDelete.size();
			//makes sure only one is deleted
			difference = lengthBefor - lengthAfter;
		}catch(Exception e){
			assertEquals(false, true);
		}
		
		try {
			//make sure you cant get the deleted playlist
			Playlist returnedPlaylist = deleter.getFullPlaylist(idEmpty);
			assertTrue(false);
		}catch(Exception e) {
			//Should throw an exception if there isn't a playlist there
			assertTrue(difference == 1 && oneAffected);
		}
	}
	/**
	 * Tests the delete playlist function that takes in a playlistId also deletes the playlist from the earlier test
	 */
	@Test
	public void deletePlaylistTestTwo() {
		//playlist from second test
		Id idFull = new Id("h12f987e-4209-6969-90c4-13f630ad72f2");
		PlaylistDAO deleter = new PlaylistDAO();
		List<Playlist> gottenPlaylistsBeforDelete = new ArrayList<>();
		List<Playlist> gottenPlaylistsAfterDelete = new ArrayList<>();
		int difference = 0;
		boolean oneAffected = false;
		SegmentDAO helper = new SegmentDAO();
		try {	
			gottenPlaylistsBeforDelete = deleter.getAllPlaylists();
			int lengthBefor = gottenPlaylistsBeforDelete.size();
			oneAffected = deleter.deletePlaylist(idFull);
			gottenPlaylistsAfterDelete = deleter.getAllPlaylists();
			int lengthAfter = gottenPlaylistsAfterDelete.size();
			//makes sure only 1 is deleted
			difference = lengthBefor - lengthAfter;
			//clean up the library
			helper.deleteSegment(new Id("test1234-9077-450e-9c94-21f2eaba4e7b.ogg"));
			helper.deleteSegment(new Id("test1234-9180-4976-b24d-93cdc98ff6cc.ogg"));
			helper.deleteSegment(new Id("test1234-68df-48ec-af09-de17bac46ecd.ogg"));
			helper.deleteSegment(new Id("test1234-72bf-46c3-be31-d6a1f275194a.ogg"));
			helper.deleteSegment(new Id("test1234-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
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
	
	/**
	 * Tests appending a segment to a playlist with segments already in there, this tests the function that takes in an appended playlist
	 */
	@Test
	public void appendToPlaylistTestOne() {
		//controle data
		//the starting playlist
		List<Segment> controllerSegmentsStart = new ArrayList<>();
		List<Id> idStart = new ArrayList<>();
		idStart.add(new Id("test4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idStart.add(new Id("test5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idStart.add(new Id("test4e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		idStart.add(new Id("test7ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idStart.add(new Id("testc86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		
		controllerSegmentsStart.add(new Segment(idStart.get(0), false, "You  a normal emotion", "McCy"));
		controllerSegmentsStart.add(new Segment(idStart.get(1), false, "Do  smell something?", "Spok"));
		controllerSegmentsStart.add(new Segment(idStart.get(2), false, "Crazy  to travel, spreading a man's molecules all over the universe.", "McCy"));
		controllerSegmentsStart.add(new Segment(idStart.get(3), false, "You , self-pity is a terrible first course", "Chapl"));
		controllerSegmentsStart.add(new Segment(idStart.get(4), false, "I  you would prefer to wallow in a pool of emotion", "Spoc"));
		
		Segment[] segsStart = {controllerSegmentsStart.get(0), controllerSegmentsStart.get(1),
				controllerSegmentsStart.get(2), controllerSegmentsStart.get(3), controllerSegmentsStart.get(4)};
		
		//the ending playlist
		List<Segment> controllerSegmentsEnd = new ArrayList<>();
		List<Id> idEnd = new ArrayList<>();
		idEnd.add(new Id("test4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idEnd.add(new Id("test5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idEnd.add(new Id("test4e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		idEnd.add(new Id("test7ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idEnd.add(new Id("testc86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		idEnd.add(new Id("test709b-5b90-48f8-a3c4-57acb0062a0c.ogg"));
		
		controllerSegmentsEnd.add(new Segment(idEnd.get(0), false, "You  a normal emotion", "McCy"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(1), false, "Do  smell something?", "Spok"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(2), false, "Crazy  to travel, spreading a man's molecules all over the universe.", "McCy"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(3), false, "You , self-pity is a terrible first course", "Chapl"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(4), false, "I  you would prefer to wallow in a pool of emotion", "Spoc"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(5), false, "Mrs. Spock, why aren�t you dead?", "Kik"));
		
		Segment[] segsEnd = {controllerSegmentsEnd.get(0), controllerSegmentsEnd.get(1),
				controllerSegmentsEnd.get(2), controllerSegmentsEnd.get(3), controllerSegmentsEnd.get(4), controllerSegmentsEnd.get(5)};
		
		
		Id id = new Id("t7659m486e-6969-4023-90c4-13f630ad72f2");
		String nameAppendTo = "testAppendPL";

		Playlist startedPlaylist = new Playlist(id, nameAppendTo);
		startedPlaylist.addSegments(segsStart);
		Playlist appendedPlaylist = new Playlist(id, nameAppendTo, segsEnd);
		
		PlaylistDAO appender = new PlaylistDAO();
		SegmentDAO helper = new SegmentDAO();
		//test
		try {
			//add segments to library
			helper.addSegment(segsEnd[0]);
			helper.addSegment(segsEnd[1]);
			helper.addSegment(segsEnd[2]);
			helper.addSegment(segsEnd[3]);
			helper.addSegment(segsEnd[4]);
			helper.addSegment(segsEnd[5]);
			appender.addPlaylist(startedPlaylist);
			boolean appendedOne = appender.appendToPlaylist(appendedPlaylist);
			Playlist returnedPlaylist = appender.getFullPlaylist(id);
			appender.deletePlaylist(id);
			//clean up library
			helper.deleteSegment(segsEnd[0]);
			helper.deleteSegment(segsEnd[1]);
			helper.deleteSegment(segsEnd[2]);
			helper.deleteSegment(segsEnd[3]);
			helper.deleteSegment(segsEnd[4]);
			helper.deleteSegment(segsEnd[5]);
			assertTrue(appendedOne && appendedPlaylist.equals(returnedPlaylist));
		}catch(Exception e) {
			assertTrue(false);
		}
	}
	
	/**
	 * Tests appending a segment to a playlist with segments already in there, this tests the function that takes in a playlistId and a segmentId
	 */
	@Test
	public void appendToPlaylistTestTwo() {
		//controle data
		//satart playlist
		List<Segment> controllerSegmentsStart = new ArrayList<>();
		List<Id> idStart = new ArrayList<>();
		idStart.add(new Id("test4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idStart.add(new Id("test5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idStart.add(new Id("test4e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		idStart.add(new Id("test7ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idStart.add(new Id("testc86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		
		controllerSegmentsStart.add(new Segment(idStart.get(0), false, "You had a normal emotion", "McCoy"));
		controllerSegmentsStart.add(new Segment(idStart.get(1), false, "Do you smell something?", "Spock"));
		controllerSegmentsStart.add(new Segment(idStart.get(2), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy"));
		controllerSegmentsStart.add(new Segment(idStart.get(3), false, "You know, self-pity is a terrible first course", "Chapel"));
		controllerSegmentsStart.add(new Segment(idStart.get(4), false, "I know you would prefer to wallow in a pool of emotion", "Spock"));
		
		Segment[] segsStart = {controllerSegmentsStart.get(0), controllerSegmentsStart.get(1),
				controllerSegmentsStart.get(2), controllerSegmentsStart.get(3), controllerSegmentsStart.get(4)};
		
		//end playlist
		List<Segment> controllerSegmentsEnd = new ArrayList<>();
		List<Id> idEnd = new ArrayList<>();
		idEnd.add(new Id("test4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idEnd.add(new Id("test5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idEnd.add(new Id("test4e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		idEnd.add(new Id("test7ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idEnd.add(new Id("testc86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		idEnd.add(new Id("test709b-5b90-48f8-a3c4-57acb0062a0c.ogg"));
		
		controllerSegmentsEnd.add(new Segment(idEnd.get(0), false, "You had a normal emotion", "McCoy"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(1), false, "Do you smell something?", "Spock"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(2), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(3), false, "You know, self-pity is a terrible first course", "Chapel"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(4), false, "I know you would prefer to wallow in a pool of emotion", "Spock"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(5), false, "Mr. Spock, why aren�t you dead?", "Kirk"));
		
		Segment[] segsEnd = {controllerSegmentsEnd.get(0), controllerSegmentsEnd.get(1),
				controllerSegmentsEnd.get(2), controllerSegmentsEnd.get(3), controllerSegmentsEnd.get(4), controllerSegmentsEnd.get(5)};
		
		
		Id id = new Id("t7659m486e-6969-4023-90c4-13f630ad72f2");
		String nameAppendTo = "testAppendPL";

		Playlist startedPlaylist = new Playlist(id, nameAppendTo, segsStart);
		Playlist appendedPlaylist = new Playlist(id, nameAppendTo);
		appendedPlaylist.setSegments(segsStart);
		appendedPlaylist.addSegment(controllerSegmentsEnd.get(5));
		
		PlaylistDAO appender = new PlaylistDAO();
		SegmentDAO helper = new SegmentDAO();
		//test
		try {
			//add segments to library
			helper.addSegment(segsEnd[0]);
			helper.addSegment(segsEnd[1]);
			helper.addSegment(segsEnd[2]);
			helper.addSegment(segsEnd[3]);
			helper.addSegment(segsEnd[4]);
			helper.addSegment(segsEnd[5]);
			appender.addPlaylist(startedPlaylist);
			boolean appendedOne = appender.appendToPlaylist(id, segsEnd[5].getUrl());
			Playlist returnedPlaylist = appender.getFullPlaylist(id);
			appender.deletePlaylist(id);
			//clean up library
			helper.deleteSegment(segsEnd[0]);
			helper.deleteSegment(segsEnd[1]);
			helper.deleteSegment(segsEnd[2]);
			helper.deleteSegment(segsEnd[3]);
			helper.deleteSegment(segsEnd[4]);
			helper.deleteSegment(segsEnd[5]);
			assertTrue(appendedOne && appendedPlaylist.equals(returnedPlaylist));
		}catch(Exception e) {
			assertTrue(false);
		}
	}
	
	
	/**
	 * Tests the append to playlist function, by appending 5 segments to an empty playlist
	 */
	@Test
	public void appendToPlaylistTestThree() {
		//control data
		List<Segment> controllerSegmentsEnd = new ArrayList<>();
		List<Id> idEnd = new ArrayList<>();
		idEnd.add(new Id("test4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idEnd.add(new Id("test5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idEnd.add(new Id("test4e2c-68df-48ec-af09-de17bac46ecd.ogg"));
		idEnd.add(new Id("test7ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idEnd.add(new Id("testc86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		idEnd.add(new Id("test709b-5b90-48f8-a3c4-57acb0062a0c.ogg"));
		
		controllerSegmentsEnd.add(new Segment(idEnd.get(0), false, "You had a normal emotion", "McCoy"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(1), false, "Do you smell something?", "Spock"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(2), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(3), false, "You know, self-pity is a terrible first course", "Chapel"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(4), false, "I know you would prefer to wallow in a pool of emotion", "Spock"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(5), false, "Mr. Spock, why aren�t you dead?", "Kirk"));
		
		Segment[] segsEnd = {controllerSegmentsEnd.get(0), controllerSegmentsEnd.get(1),
				controllerSegmentsEnd.get(2), controllerSegmentsEnd.get(3), controllerSegmentsEnd.get(4), controllerSegmentsEnd.get(5)};
		
		
		Id id = new Id("j4567h586y-5555-6666-90c4-13f630ad72f2");
		String nameAppendTo = "testAppendPL";
		Playlist startPlaylist = new Playlist(id, nameAppendTo);
		Playlist appendedPlaylist = new Playlist(id, nameAppendTo, segsEnd);
		//test
		PlaylistDAO appender = new PlaylistDAO();
		SegmentDAO helper = new SegmentDAO();
		try {
			//add segments to the library
			helper.addSegment(segsEnd[0]);
			helper.addSegment(segsEnd[1]);
			helper.addSegment(segsEnd[2]);
			helper.addSegment(segsEnd[3]);
			helper.addSegment(segsEnd[4]);
			helper.addSegment(segsEnd[5]);
			appender.addPlaylist(startPlaylist);
			//test the adds
			boolean appendedOne = appender.appendToPlaylist(id, segsEnd[0].getUrl());
			boolean appendedTwo = appender.appendToPlaylist(id, segsEnd[1].getUrl());
			boolean appendedThree = appender.appendToPlaylist(id, segsEnd[2].getUrl());
			boolean appendedFour = appender.appendToPlaylist(id, segsEnd[3].getUrl());
			boolean appendedFive = appender.appendToPlaylist(id, segsEnd[4].getUrl());
			boolean appendedSix = appender.appendToPlaylist(id, segsEnd[5].getUrl());
			boolean allAppended = appendedOne && appendedTwo && appendedThree && appendedFour && appendedFive && appendedSix;
			
			Playlist returnedPlaylist = appender.getFullPlaylist(id);
			//clean up the library
			helper.deleteSegment(segsEnd[0]);
			helper.deleteSegment(segsEnd[1]);
			helper.deleteSegment(segsEnd[2]);
			helper.deleteSegment(segsEnd[3]);
			helper.deleteSegment(segsEnd[4]);
			helper.deleteSegment(segsEnd[5]);
			appender.deletePlaylist(id);
			assertTrue(allAppended && appendedPlaylist.equals(returnedPlaylist));
		}catch(Exception e) {
			assertTrue(false);
		}
		assertTrue(true);
	}
	
	
	
	
	/**
	 * Tests the append to playlist function, by appending 5 segments to an empty playlist
	 * Includes appending a remote segment
	 * that code
	 */
	@Test
	public void appendToPlaylistTestFour() {
		//control data
		List<Segment> controllerSegmentsEnd = new ArrayList<>();
		List<Id> idEnd = new ArrayList<>();
		idEnd.add(new Id("test4225-9077-450e-9c94-21f2eaba4e7b.ogg"));
		idEnd.add(new Id("test5621-9180-4976-b24d-93cdc98ff6cc.ogg"));
		idEnd.add(new Id("Remote11-68df-48ec-af09-de17bac46ecd.ogg"));
		idEnd.add(new Id("test7ef5-72bf-46c3-be31-d6a1f275194a.ogg"));
		idEnd.add(new Id("Remote22-e60b-44d7-a231-7a3d7f9ff6cc.ogg"));
		idEnd.add(new Id("Remote33-5b90-48f8-a3c4-57acb0062a0c.ogg"));
		
		controllerSegmentsEnd.add(new Segment(idEnd.get(0), false, "You had a normal emotion", "McCoy"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(1), false, "Do you smell something?", "Spock"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(2), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(3), false, "You know, self-pity is a terrible first course", "Chapel"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(4), false, "I know you would prefer to wallow in a pool of emotion", "Spock"));
		controllerSegmentsEnd.add(new Segment(idEnd.get(5), false, "Mr. Spock, why aren�t you dead?", "Kirk"));
		
		String[] segsEnd = {controllerSegmentsEnd.get(0).getUrl(), controllerSegmentsEnd.get(1).getUrl(),
				"remote", controllerSegmentsEnd.get(3).getUrl(), "remote", "remote"};
		
		Segment[] addSegs = {controllerSegmentsEnd.get(0), controllerSegmentsEnd.get(1), controllerSegmentsEnd.get(3)};
		
		
		
		Id id = new Id("j4567h586y-5555-6666-90c4-13f630ad72f2");
		String nameAppendTo = "testAppendPL";
		Playlist startPlaylist = new Playlist(id, nameAppendTo);
		Playlist appendedPlaylist = new Playlist(id, nameAppendTo);
		appendedPlaylist.setSegmentUrls(segsEnd);
		//test
		PlaylistDAO appender = new PlaylistDAO();
		SegmentDAO helper = new SegmentDAO();
		try {
			//add local segments to the library
			helper.addSegment(addSegs[0]);
			helper.addSegment(addSegs[1]);
			helper.addSegment(addSegs[2]);
			appender.addPlaylist(startPlaylist);
			boolean appendedOne = appender.appendToPlaylist(id, segsEnd[0]);
			boolean appendedTwo = appender.appendToPlaylist(id, segsEnd[1]);
			boolean appendedThree = appender.appendToPlaylist(id, segsEnd[2]);
			boolean appendedFour = appender.appendToPlaylist(id, segsEnd[3]);
			boolean appendedFive = appender.appendToPlaylist(id, segsEnd[4]);
			boolean appendedSix = appender.appendToPlaylist(id, segsEnd[5]);
			boolean allAppended = appendedOne && appendedTwo && appendedThree && appendedFour && appendedFive && appendedSix;
			
			Playlist returnedPlaylist = appender.getFullPlaylist(id);
			//clean up library
			helper.deleteSegment(addSegs[0]);
			helper.deleteSegment(addSegs[1]);
			helper.deleteSegment(addSegs[2]);
			appender.deletePlaylist(id);
			assertTrue(allAppended && appendedPlaylist.equals(returnedPlaylist));
		}catch(Exception e) {
			assertTrue(false);
		}
		assertTrue(true);
	}
	
	/**
	 * Tests the get full playlist function
	 */
	@Test
	public void getFullPlaylistTest() {
		//make the controlle data
		Id i = new Id("testd60f-c6f1-4138-a0b1-cb7fc2010e9d");
		List<Segment> controllerSegments = new ArrayList<>();
		controllerSegments.add(new Segment(new Id("test4225-9077-450e-9c94-21f2eaba4e7b.ogg"), false, "You had a normal emotion", "McCoy"));
		controllerSegments.add(new Segment(new Id("testdc3a-a3f5-4f39-bf3a-b7f65fa9399b.ogg"), false, "Then I need a drink", "McCoy"));
		controllerSegments.add(new Segment(new Id("test9c56-1a2d-45ed-b676-29de0f4e4486.ogg"), false, "Colloquially expressed, but essentially correct.", "Spock"));
		Segment segs[] = {controllerSegments.get(0), controllerSegments.get(1), controllerSegments.get(2)};
		Playlist control = new Playlist(i, "Erich's PLaylist", segs);
		PlaylistDAO getter = new PlaylistDAO();
		SegmentDAO helper = new SegmentDAO();
		//test
		try {
			helper.addSegment(segs[0]);
			helper.addSegment(segs[1]);
			helper.addSegment(segs[2]);
			getter.addPlaylist(control);
			Playlist gotten = getter.getFullPlaylist(i);
			helper.deleteSegment(segs[0]);
			helper.deleteSegment(segs[1]);
			helper.deleteSegment(segs[2]);
			getter.deletePlaylist(control);
			assertTrue(control.equals(gotten));
		}catch(Exception e) {
			fail("exception");
		}
	}
	
	/**
	 * Tests the delete from playlist function
	 */
	@Test
	public void deletFromPlaylistTest() {
		//make the control data
		Id i = new Id("one6h8uh-3344-uwu9-owo6-cb7fc2010e9d");
		Id ii = new Id("two6h8uh-3344-uwu9-owo6-cb7fc2010e9d");
		Id iii = new Id("three8uh-3344-uwu9-owo6-cb7fc2010e9d");
		iii.setId("three8uh-3344-uwu9-owo6-cb7fc2010e9d");
		Id iiii = new Id("four8uh-3344-uwu9-owo6-cb7fc2010e9d");
		List<Segment> controllerSegments = new ArrayList<>();
		controllerSegments.add(new Segment(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"), false, ">:~(", "Erich"));
		controllerSegments.add(new Segment(new Id("3c4bdc3a-a3f5-4f39-bf3a-b7f65fa9399b.ogg"), false, "delete delete delete", "sabrina"));
		controllerSegments.add(new Segment(new Id("3e3b9c56-1a2d-45ed-b676-29de0f4e4486.ogg"), false, "Its yoour own fault erich", "maria"));
		//for deleting the first segment
		Segment segsOne[] = {controllerSegments.get(0), controllerSegments.get(1), controllerSegments.get(2)};
		Playlist testOne = new Playlist(i, "Test PLaylist one", segsOne);
		Segment conSegsOne[] = {controllerSegments.get(1), controllerSegments.get(2)};
		Playlist conOne = new Playlist(i, "Test PLaylist one", conSegsOne);
		//for deleting the middle segment and multipul segmetns
		Segment segsTwo[] = {controllerSegments.get(0), controllerSegments.get(1), controllerSegments.get(2), controllerSegments.get(1)};
		Playlist testTwo = new Playlist(ii, "Test PLaylist two", segsTwo);
		Segment conSegsTwo[] = {controllerSegments.get(0), controllerSegments.get(2)};
		Playlist conTwo = new Playlist(ii, "Test PLaylist two", conSegsTwo);
		//for deleting the last segment
		Segment segsThree[] = {controllerSegments.get(0), controllerSegments.get(1), controllerSegments.get(2)};
		Playlist testThree = new Playlist(iii, "Test PLaylist three", segsThree);
		Segment conSegsThree[] = {controllerSegments.get(0), controllerSegments.get(1)};
		Playlist conThree = new Playlist(iii, "Test PLaylist three", conSegsThree);
		//for deleting the only segment
		Segment segsFour[] = {controllerSegments.get(0)};
		Playlist testFour = new Playlist(iiii, "Test PLaylist three", segsFour);
		Segment conSegsFour[] = {};
		Playlist conFour = new Playlist(iiii, "Test PLaylist three", conSegsFour);
		
		
		PlaylistDAO tester = new PlaylistDAO();
		SegmentDAO helper = new SegmentDAO();
		
		try {
			//add
			
			helper.addSegment(segsOne[0]);
			helper.addSegment(segsOne[1]);
			helper.addSegment(segsOne[2]);
			
			tester.addPlaylist(testOne);
			tester.addPlaylist(testTwo);
			tester.addPlaylist(testThree);
			tester.addPlaylist(testFour);
			//test
			
			tester.deleteFromPlaylist(i, controllerSegments.get(0).getUrl());
			tester.deleteFromPlaylist(ii, controllerSegments.get(1).getUrl());
			tester.deleteFromPlaylist(iii, controllerSegments.get(2).getUrl());
			tester.deleteFromPlaylist(iiii, controllerSegments.get(0).getUrl());
			
			
			Playlist gottonOne = tester.getFullPlaylist(i);
			Playlist gottonTwo = tester.getFullPlaylist(ii);
			Playlist gottonThree = tester.getFullPlaylist(iii);
			Playlist gottonFour = tester.getFullPlaylist(iiii);
			//clean
			
			tester.deletePlaylist(i);
			tester.deletePlaylist(ii);
			tester.deletePlaylist(iii);
			tester.deletePlaylist(iiii);
			helper.deleteSegment(segsOne[0]);
			helper.deleteSegment(segsOne[1]);
			helper.deleteSegment(segsOne[2]);
			
			boolean testOnePassed = conOne.equals(gottonOne);
			boolean testTwoPassed = conTwo.equals(gottonTwo);
			boolean testThreePassed = conThree.equals(gottonThree);
			boolean testFourPassed = conFour.equals(gottonFour);
			
			assertTrue(testOnePassed && testTwoPassed && testThreePassed && testFourPassed);
		}catch(Exception e) {
			fail("exception");
		}
	}
	
	
	/*
	@Test
	public void getFilledPlaylistWithNonexistentSegmentsTest(){
		Playlist testPlaylist = new Playlist("DAO test");
		Id nonExistentSegId = new Id();
		PlaylistDAO tester = new PlaylistDAO();
		
		try {
			tester.addPlaylist(testPlaylist);
			tester.appendToPlaylist(testPlaylist.getId(), "noURL");
			Playlist result = tester.getFullPlaylist(testPlaylist.getId());
			
			assertTrue("no segments returned", result.getSegmentUrls().length == 0);
			
		}catch(Exception e){
			fail("exception: " + e.getMessage());
		}
		try {
			tester.deletePlaylist(testPlaylist.getId());
		}catch(Exception e){
			fail("exception" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void getFilledPlaylistWithNoUrlsTest(){
		Playlist testPlaylist = new Playlist("DAO test");
		PlaylistDAO tester = new PlaylistDAO();
		
		try {
			tester.addPlaylist(testPlaylist);
			
			Playlist result = tester.getFullPlaylist(testPlaylist.getId());
			
			System.out.println(result);
			
			assertTrue("no urls returned", result.getSegmentUrls().length == 0);
			
		}catch(Exception e){
			fail("exception" + e.getMessage());
			//e.printStackTrace();
		}
		
		try {
			tester.deletePlaylist(testPlaylist.getId());
		}catch(Exception e){
			fail("exception" + e.getMessage());
			e.printStackTrace();
		}
	}
	*/
	
}

