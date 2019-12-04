package paths.segment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.SegmentDAO;
import definitions.Id;
import definitions.Response;
import definitions.Segment;

public class UnmarkSegmentHandler implements RequestHandler<Id, Response<Segment[]>>{
	LambdaLogger logger;
	SegmentDAO dao;
		
	@Override
	public Response<Segment[]> handleRequest(Id id, Context context) {
		logger = context.getLogger();
		logger.log(id.toString());

		Response<Segment[]> response;
		dao = new SegmentDAO();
		try {
			if (dao.markSegment(id, false)) {
				response = new Response<Segment[]>(ListLocalSegmentsHandler.getSegments(logger, dao), 200);
			} else {
				response = new Response<Segment[]>(400, "Segment not marked public");
			}
		} catch(Exception e) {
			response = new Response<Segment[]>(400, "Unable to complete request: " +  "(" + e.getMessage() + ")");
	
		}
		return response; 
	
	}
}