package paths.site;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import definitions.Response;
import definitions.Site;

public class ListRemoteSitesHandler implements RequestHandler<Object, Response<Site[]>>{

	@Override
	public Response<Site[]> handleRequest(Object input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
