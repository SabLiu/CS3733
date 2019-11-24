package paths.segment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.SegmentsDAO;
import definitions.Id;
import definitions.Response;
import definitions.Segment;

public class ListLocalSegmentsHandler implements RequestHandler<Object, Response<Segment[]>>{
	
	public LambdaLogger logger;

	public ListLocalSegmentsHandler() {}
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	Segment[] getSegments() throws Exception {
			logger.log("in getSegments\n");
			SegmentsDAO dao = new SegmentsDAO();
			//Segment[] s = {new Segment(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"), false, "You had a normal emotion", "McCoy", "Two.ogg")};
			Segment[] s = {};
			s = dao.getAllLocalSegments().toArray(s);
			//logger.log(s.toString());
			return s;
		}
	
	@Override
	public Response<Segment[]> handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all segments\n");

		Segment[] list;
		Response<Segment[]> response;
		
		
		try {
			list = getSegments();	
			response = new Response<Segment[]>(list, 200);
			logger.log("finished getSegments\n");
		} catch (Exception e) {
			response = new Response<Segment[]>(400, e.getMessage());
			logger.log("EXEPCTION: " + e.getMessage());
		}
		
		return response;
	}
	
}
