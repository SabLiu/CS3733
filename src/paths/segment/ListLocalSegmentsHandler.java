package paths.segment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.SegmentsDAO;
import definitions.Response;
import definitions.Segment;

 class ListLocalSegmentsHandler implements RequestHandler<Object, Response<Segment[]>>{

	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	Segment[] getSegments() throws Exception {
			logger.log("in getSegments");
			SegmentsDAO dao = new SegmentsDAO();
			Segment[] s = {};
			return dao.getAllLocalSegments().toArray(s);
		}
	
	@Override
	public Response<Segment[]> handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all segments");

		Segment[] list;
		Response<Segment[]> response;
		
		try {
			list = getSegments();	
			response = new Response<Segment[]>(list, 200);
		} catch (Exception e) {
			response = new Response<Segment[]>(400, e.getMessage());
			logger.log(e.getMessage());
		}
		
		return response;
	}
	
}
