package databases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import definitions.Id;
import definitions.Playlist;

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
}
