package paths.site;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import definitions.Playlist;
import definitions.Response;
import definitions.Site;
import lamnda.LambdaTest;

public class ListSitesHandlerTest extends LambdaTest{

	/**
	 * 
	 */
	@Test
	public void hasGoogle(){
    	ListRemoteSitesHandler handler = new ListRemoteSitesHandler();
        Response<Site[]> resp = handler.handleRequest(null, createContext("list"));
        
        boolean hasGoogle = false;
        
        for (Site s: resp.model) {
        	System.out. println(s);
        	if (s.getUrl().equals("https://21et641mh5.execute-api.us-east-2.amazonaws.com/Kirk/getAllRemotelyAvailableSegments?apikey=8yb0UifInF2OLxxkbGzIp58ZUmcifAYQ6llkTISP")) { hasGoogle = true; }
        }
        
        assertTrue("has a site with url = 'google.com' in the database", hasGoogle);
        Assert.assertEquals(200, resp.statusCode);
	}
 
}
