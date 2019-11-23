package paths.site;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import definitions.Site;

 class ListRemoteSitesHandler implements RequestHandler<Object, Site[]>{

	@Override
	public Site[] handleRequest(Object input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
