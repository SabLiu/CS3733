package paths.site;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.SiteDAO;
import definitions.Response;
import definitions.Site;

public class ListRemoteSitesHandler implements RequestHandler<Object, Response<Site[]>>{

	public LambdaLogger logger;
	
	static Site[] getSites(LambdaLogger logger, SiteDAO dao) throws Exception {
		logger.log("in GetSites\n");
		Site[] s = {};
		s = dao.getAllSites().toArray(s);
		return s;
	}
	
	@Override
	public Response<Site[]> handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all sites\n");

		Site[] list;
		Response<Site[]> response;
		
		
		try {
			list = getSites(logger, new SiteDAO());	 
			response = new Response<Site[]>(list, 200);
			logger.log("finished getSites\n");
		} catch (Exception e) {
			response = new Response<Site[]>(400, e.getMessage());
			logger.log("EXEPCTION: " + e.getMessage());
		}
		
		return response;
	}

}
