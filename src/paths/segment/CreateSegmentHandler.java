package paths.segment;

import java.io.ByteArrayInputStream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import databases.SegmentDAO;
import definitions.Response;
import definitions.Segment;


public class CreateSegmentHandler implements RequestHandler<Segment,Response<Segment[]>>{
	LambdaLogger logger;
	SegmentDAO dao; 
	
	@Override
	public Response<Segment[]> handleRequest(Segment segment, Context context) {
		logger = context.getLogger();
		//logger.log(segment.toString());

		Response<Segment[]> response;
		dao = new SegmentDAO();
		try {
			if (addToBucket(segment) &&  addToDatabase(segment)) {
				//return all local segments
				response = new Response<Segment[]>(ListLocalSegmentsHandler.getSegments(logger, dao), 200);
			} else {
				response = new Response<Segment[]>(400, "Segment not added"); 
			}
		} catch(Exception e) { 
			response = new Response<Segment[]>(400, "Unable to complete request: " +  "(" + e.getMessage() + ")");
		}

		return response;
	}
		
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean addToDatabase(Segment segment) throws Exception {
		if (logger != null) { logger.log("in addToDatabase\n"); }
		
		return dao.addSegment(segment);
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	boolean addToBucket(Segment segment) throws Exception {
		logger.log("in addToBucket\n");
		
		//create s3 object
		logger.log("attach to S3 request\n");
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		logger.log("attach to S3 succeed\n");
		
		//turn video file into byte array
		//mkyong.com/java/how-to-convert-file-into-an-array-of-bytes/
		byte[] contents = java.util.Base64.getDecoder().decode(segment.getContents());
		//contents = Files.readAllBytes(Paths.get("test\\resources\\test_segment.ogg"));  
		
		if(contents == null) {
			return false;
		}
		
		//add video to bucket 
		ByteArrayInputStream bais = new ByteArrayInputStream(contents);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(contents.length);
		PutObjectRequest p = new PutObjectRequest("hotspurproject", "segments/" + segment.getId().getId(), bais, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		
		@SuppressWarnings("unused")
		PutObjectResult res = s3.putObject(p);
		
		// if we ever get here, then whole thing was stored
		
		logger.log("addToBucket complete\n");
		return true;
	}

}
