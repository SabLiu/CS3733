package paths.segment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.SegmentDAO;
import definitions.Response;
import definitions.Segment;

public class ListLocalSegmentsHandler implements RequestHandler<Object, Response<Segment[]>>{
	
	public LambdaLogger logger;

	public ListLocalSegmentsHandler() {}
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	public static Segment[] getSegments(LambdaLogger logger, SegmentDAO dao) throws Exception {
			logger.log("in getSegments\n");
			Segment[] s = {};
			s = dao.getAllLocalSegments().toArray(s);
			return s;
		}
	
	@Override
	public Response<Segment[]> handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all segments\n");

		Segment[] list;
		Response<Segment[]> response;
		
		
		try {
			list = getSegments(logger, new SegmentDAO());	
			response = new Response<Segment[]>(list, 200);
			logger.log("finished getSegments\n");
		} catch (Exception e) {
			response = new Response<Segment[]>(400, e.getMessage());
			logger.log("EXEPCTION: " + e.getMessage());
		}
		
		return response;
	}
	
}
