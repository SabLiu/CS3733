package paths.site;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.SiteDAO;
import definitions.Id;
import definitions.Response;
import definitions.Site;

public class RemoveRemoteSiteHandler implements RequestHandler<Id, Response<Site[]>>{
		LambdaLogger logger;
		SiteDAO dao; 
			
		@Override
		public Response<Site[]> handleRequest(Id id, Context context) {
			logger = context.getLogger();
			logger.log(id.toString());

			Response<Site[]> response;
			dao = new SiteDAO(); 
			try {
				if (removeFromDatabase(id)) {
					response = new Response<Site[]>(ListRemoteSitesHandler.getSites(logger,dao), 200);
				} else {
					response = new Response<Site[]>(400, "Site not removed"); 
				}
			} catch(Exception e) { 
				response = new Response<Site[]>(400, "Unable to complete request: " +  "(" + e.getMessage() + ")");
				
				for(StackTraceElement st: e.getStackTrace()){logger.log(st + "\n");}
			}

			return response;
		}
	boolean removeFromDatabase(Id id) throws Exception {
		if (logger != null) { logger.log("in remove from database\n"); }
		
		return dao.deleteSite(id);
	}
}
