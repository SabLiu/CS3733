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
	 
	 public SegmentSearchHandler() {}
	 
	 /**
	  * Searches for segments that match the search request
	  * @param logger
	  * @param dao the segment database
	  * @param input the search request with the character name and the words said
	  * @return an array of segments that match the search request
	  * @throws Exception
	  */
	 static Segment[] getSegments(LambdaLogger logger, SegmentDAO dao, SearchRequest input) throws Exception {
			logger.log("in getSegments\n");
			Segment[] s = {};
			List<Segment> ls = new ArrayList<Segment>();
			//check if there is a character specified, in which case use the DAO function to get the starting list of segments
			if(input.getCharacterKeyphrase() == null) {
				ls = dao.getAllLocalSegments();
			}else {
				ls = dao.searchSegmentCharacter(input.getCharacterKeyphrase());
			}
			//check if there is a word specified, in which case filet out all the segments that don't contain that substring
			if(input.getSentenceKeyphrase() != null) {
				int i = 0;
				while(i < ls.size()) {
					//should this segment be kept
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
	 
	 /**
	  * Handels the request
	  * @param input the search request with the character name and the words said
	  * @param context
	  */
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
