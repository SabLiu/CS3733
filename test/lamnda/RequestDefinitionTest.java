package lamnda;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import definitions.Id;
import definitions.SearchRequest;
import definitions.SegmentAndPlaylistRequest;

public class RequestDefinitionTest {
	
	@Test
	public void SearchRequestTest(){
		System.out.println("here");
		SearchRequest sa = new SearchRequest("McCoy", "Hi");
		System.out.println("here");
		SearchRequest sb = new SearchRequest("McCoy", "Hi");
		System.out.println("here");
		System.out.println(sa);
		boolean a = sa.equals(sb);
		boolean b = sa.equals(sa);
		boolean c = sa.hashCode() == sb.hashCode();
		boolean d = !sa.equals(new SearchRequest("McCoy", "h"));
		boolean e = !sa.equals(new SearchRequest("Mcoy", "Hi"));
		boolean f = !sa.equals(null);
		boolean g = !sa.equals(new Integer(1));
		SearchRequest sd = new SearchRequest();
		sd.setCharacterKeyphrase(null);
		boolean h = !sd.equals(sa);
		SearchRequest sc = new SearchRequest();
		sc.setCharacterKeyphrase("McCoy");
		sc.setSentenceKeyphrase(null);
		boolean i = !sc.equals(sa);
		
		//System.out.println(a + "" + b + "" + c + "" + d + "" + e + "" + f + "" + g + "" + h + "" + i);
		
		assertTrue(a && b && c && d && e && f && g && h && i);
	}
	
	@Test
	public void SegmentAndPlaylistRequestTest(){
		System.out.println("here");
		SegmentAndPlaylistRequest sa = new SegmentAndPlaylistRequest();
		SegmentAndPlaylistRequest sb = new SegmentAndPlaylistRequest();
		sa.setSegmentUrl("google.com");
		sb.setSegmentUrl("google.com");
		sa.setPlaylistId(new Id("jerhvh"));
		sb.setPlaylistId(new Id("jerhvh"));
		System.out.println(sa);
		assertTrue(sa.equals(sb) && sa.hashCode() == sb.hashCode());
	}

}
