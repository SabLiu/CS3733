package paths.segment.remoteapi;

import java.awt.List;
import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.SegmentDAO;
import definitions.Response;
import definitions.Segment;
import definitions.remoteapi.SegmentsSendResponse;
import definitions.remoteapi.SegmentsToSend;
import paths.segment.ListLocalSegmentsHandler;

public class SendLocalSegmentsHandler implements RequestHandler<Object, SegmentsSendResponse>{
	
	LambdaLogger logger;
	String S3_base_url = "https://hotspurproject.s3.us-east-2.amazonaws.com/segments";

	public SendLocalSegmentsHandler() {}
	
	@Override
	public SegmentsSendResponse handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all segments\n");

		Segment[] list;
		SegmentsSendResponse response;
		
		
		try {
			list = ListLocalSegmentsHandler.getSegments(logger, new SegmentDAO());	
			
			ArrayList<Segment> publiclyAvaliableSegments = new ArrayList<Segment>();
			for(Segment s: list) {
				if(s.isRemotelyAvailable()) { publiclyAvaliableSegments.add(s); }
			}
			
			SegmentsToSend[] segments = new SegmentsToSend[publiclyAvaliableSegments.size()];
			int i = 0;
			for(Segment s: publiclyAvaliableSegments) {
				String url = S3_base_url + "/" + s.getId();
				String character = s.getCharacter();
				String text = s.getSentence();
				segments[i] = new SegmentsToSend(url, character, text);
				i++;
			}
			
			response = new SegmentsSendResponse(segments, 200);
			logger.log("finished getSegments\n");
		} catch (Exception e) {
			response = new SegmentsSendResponse(400, e.getMessage());
			logger.log("EXEPCTION: " + e.getMessage());
		}
		
		return response;
	}
	
}
