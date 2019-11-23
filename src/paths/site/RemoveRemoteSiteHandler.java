package paths.site;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import definitions.Id;
import definitions.Site;

 class RemoveRemoteSiteHandler implements RequestHandler<Id, Site[]>{

	@Override
	public Site[] handleRequest(Id input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
