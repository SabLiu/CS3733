package paths.segment.remoteapi;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import definitions.Response;
import definitions.Segment;
import definitions.remoteapi.SegmentsSendResponse;
import definitions.remoteapi.SegmentsToSend;
import lamnda.LambdaTest;

public class SendLocalSegmentsHandlerTest extends LambdaTest{

	@Test
	public void hasSpock(){
    	SendLocalSegmentsHandler handler = new SendLocalSegmentsHandler();
        SegmentsSendResponse resp = handler.handleRequest(null, createContext("list"));
        
        boolean hasSpock = false;
        System.out.println(resp);
        for (SegmentsToSend s : resp.segments) {
        	System.out.println(s);
        	if (s.getCharacter().equals("Spock")) { hasSpock = true; }
        }
        
        assertTrue("there is a publicly avalialable segment with Spock as a character in the database", hasSpock);
        Assert.assertEquals(200, resp.statusCode);
	}

}
