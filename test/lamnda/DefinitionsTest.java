package lamnda;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import definitions.Id;
import definitions.Playlist;
import definitions.Response;
import definitions.Segment;
import definitions.Site;
import definitions.remoteapi.SegmentsSendResponse;
import definitions.remoteapi.SegmentsToSend;

public class DefinitionsTest {

	@Test
	public void SiteTest() {
		Id ia = new Id();
		Site sa = new Site();
		sa.setId(ia);
		sa.setUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		Site sb = new Site();
		sb.setId(ia);
		sb.setUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		boolean a = sa.equals(sb);
		boolean b = sa.hashCode() == sb.hashCode();
		assertTrue(a&&b);
	}
	
	@Test
	public void SegmentTest() {
		Id ia = new Id();
		Segment sa = new Segment();
		sa.setId(ia);
		sa.setRemotelyAvailable(true);
		sa.setSentence("heier");
		sa.setCharacter("me");
		sa.setContents("test");
		
		Segment sb = new Segment();
		sb.setId(ia);
		sb.setRemotelyAvailable(true);
		sb.setSentence("heier");
		sb.setCharacter("me");
		sb.setContents("test");
		
		boolean a = sa.equals(sb);
		boolean b = sa.hashCode() == sb.hashCode();
		assertTrue(a&&b);
	}
	
	@Test
	public void SegmentsToSendTest() {
		SegmentsToSend s = new SegmentsToSend();
		s.setUrl("jfvjdf");
		String sa = s.getUrl();
		s.setCharacter("me");
		String sb = s.getCharacter();
		s.setText("hi");
		String sc = s.getText();
		boolean a = sa.equals("jfvjdf");
		boolean b = sb.equals("me");
		boolean c = sc.equals("hi");
		assertTrue(a && b && c);
	}
	
	@Test
	public void SegmentsSendResponseTest() {
		SegmentsSendResponse s = new SegmentsSendResponse(123, "error");
		boolean a = s.getSegments() == null;
		boolean b = s.getError().equals("error");
		boolean c = s.getStatusCode() == 123;
		assertTrue(a && b && c);
	}
	
	@Test
	public void ResponceTest() {
		Response r = new Response(123, "error");
		String sr = r.getError();
		assertTrue(sr.equals("error"));
	}
	
	@Test
	public void PlaylistTest() {
		Segment sa[] = {new Segment()};
		Segment sb[] = {new Segment()};
		Id i = new Id();
		
		Playlist pa = new Playlist(i, "hi", sa);
		Playlist pb = new Playlist(i, "hi", sb);
		Playlist pc = new Playlist(i, "hi", sa);
		boolean a = !pa.equals(pb);
		boolean b = pa.hashCode() == pb.hashCode();
		boolean c = pa.hashCode() == pc.hashCode();
		assertTrue(a && b && c);
	}

}
