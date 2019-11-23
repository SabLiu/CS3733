package paths;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import definitions.Id;
import definitions.Segment;

 class GetSegmentHandler implements RequestHandler<Id, Segment>{

	@Override
	public Segment handleRequest(Id input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
