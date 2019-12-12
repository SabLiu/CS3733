package paths.site;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.SiteDAO;
import definitions.Response;
import definitions.Site;

public class RegisterRemoteSiteHandler implements RequestHandler<Site, Response<Site[]>>{
	LambdaLogger logger;
	SiteDAO dao;
		
	@Override
	public Response<Site[]> handleRequest(Site site, Context context) {
		logger = context.getLogger();
		logger.log(site.toString());

		Response<Site[]> response;
		dao = new SiteDAO(); 
		try {
			if (addToDatabase(site)) {
				response = new Response<Site[]>(ListRemoteSitesHandler.getSites(logger,dao), 200);
			} else {
				response = new Response<Site[]>(400, "Site allready there"); 
			}
		} catch(Exception e) { 
			response = new Response<Site[]>(400, "Unable to complete request: " +  "(" + e.getMessage() + ")");
			
			for(StackTraceElement st: e.getStackTrace()){logger.log(st + "\n");}
		}

		return response;
	}
		
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean addToDatabase(Site site) throws Exception {
		if (logger != null) { logger.log("in addToDatabase\n"); }
		
		return dao.addSite(site);
	}

}
