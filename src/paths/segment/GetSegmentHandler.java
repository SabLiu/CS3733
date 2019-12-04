package paths.segment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import definitions.Id;
import definitions.Response;
import definitions.Segment;

public class GetSegmentHandler implements RequestHandler<Id, Response<Segment>>{

	@Override
	public Response<Segment> handleRequest(Id input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
