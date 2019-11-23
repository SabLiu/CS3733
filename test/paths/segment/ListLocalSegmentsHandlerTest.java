package paths.segment;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.Response;
import definitions.Segment;

import lamnda.LambdaTest;

public class ListLocalSegmentsHandlerTest extends LambdaTest{

	@Test
	public void hasMcCoy() throws Exception{
    	ListLocalSegmentsHandler handler = new ListLocalSegmentsHandler();
        Response<Segment[]> resp = handler.handleRequest(null, createContext("list"));
        
        boolean hasMcCoy = false;
        for (Segment s : resp.model) {
        	if (s.getCharacter().equals("McCoy")) { hasMcCoy = true; break; }
        }
        
        assertTrue("there is a segment with McCoy as a character in the database", hasMcCoy);
        Assert.assertEquals(200, resp.statusCode);
	}

}
