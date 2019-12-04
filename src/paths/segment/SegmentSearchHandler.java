package paths.segment;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.SegmentDAO;
import definitions.Response;
import definitions.Segment;
import definitions.SearchRequest;


public class SegmentSearchHandler implements RequestHandler<SearchRequest, Response<Segment[]>>{
	 public LambdaLogger logger;
	 
	 static Segment[] getSegments(LambdaLogger logger, SegmentDAO dao, SearchRequest input) throws Exception {
			logger.log("in getSegments\n");
			Segment[] s = {};
			List<Segment> ls = new ArrayList<Segment>();
			if(input.getCharacterKeyphrase() == null) {
				ls = dao.getAllLocalSegments();
			}else {
				ls = dao.searchSegmentCharacter(input.getCharacterKeyphrase());
			}
			if(input.getSentenceKeyphrase() != null) {
				int i = 0;
				while(i < ls.size()) {
					logger.log("in\n");
					if(ls.get(i).getSentence().contains(input.getSentenceKeyphrase())) {
						i++;
					}else {
						ls.remove(i);
					}
				}
			}
			s = ls.toArray(s);
			return s;
		}
	 
	@Override
	public Response<Segment[]> handleRequest(SearchRequest input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list searched segments\n");
		
		Segment[] list;
		Response<Segment[]> response;
		
		try {
			list = getSegments(logger, new SegmentDAO(), input);	
			response = new Response<Segment[]>(list, 200);
			logger.log("finished getSearchedSegments\n");
		} catch (Exception e) {
			response = new Response<Segment[]>(400, e.getMessage());
			logger.log("EXEPCTION: " + e.getMessage());
		}
		
		return response;
	}

}
