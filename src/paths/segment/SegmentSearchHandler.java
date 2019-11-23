package paths;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import definitions.SearchRequest;
import definitions.Segment;

 class SegmentSearchHandler implements RequestHandler<SearchRequest, Segment[]>{

	@Override
	public Segment[] handleRequest(SearchRequest input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
