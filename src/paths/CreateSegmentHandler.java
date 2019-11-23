package paths;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import definitions.Segment;


public class PostSegmentHandler implements RequestHandler<Segment,Segment[]>{

	
	@Override
	public Segment[] handleRequest(Segment input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
