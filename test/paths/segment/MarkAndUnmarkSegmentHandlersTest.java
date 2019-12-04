package paths.segment;

import static org.junit.Assert.*;

import org.junit.Test;

import definitions.Id;
import definitions.Response;
import definitions.Segment;
import lamnda.LambdaTest;

public class MarkAndUnmarkSegmentHandlersTest extends LambdaTest{
	MarkSegmentHandler markHandler = new MarkSegmentHandler();
	UnmarkSegmentHandler unmarkHandler = new UnmarkSegmentHandler();
	Id segmentId = new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg");
	
	@Test
	public void test() {
		markHandler.handleRequest(segmentId, createContext("list"));
		
		Response<Segment[]> unMarked = unmarkHandler.handleRequest(segmentId, createContext("list"));
		boolean isunMarked = false;
		for(Segment s: unMarked.model) {
			if(s.getId().equals(segmentId) && !s.isRemotelyAvailable()) {
				isunMarked = true;
			}
		}
		assertTrue("Segment is unmarked", isunMarked);
		
		Response<Segment[]> marked = markHandler.handleRequest(segmentId, createContext("list"));
		boolean isMarked = false;
		for(Segment s: marked.model) {
			if(s.getId().equals(segmentId) && s.isRemotelyAvailable()) {
				isMarked = true;
			}
		}
		assertTrue("Segment is marked", isMarked);
	}
	
	
	
}
