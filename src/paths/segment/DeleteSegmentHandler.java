package paths.segment;

import java.io.ByteArrayInputStream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import databases.SegmentsDAO;
import definitions.Id;
import definitions.Response;
import definitions.Segment;

 class DeleteSegmentHandler implements RequestHandler<Id, Response<Segment[]>>{
	 LambdaLogger logger;
	 SegmentsDAO dao;
		
	@Override
	public Response<Segment[]> handleRequest(Id id, Context context) {
		logger = context.getLogger();
		logger.log(id.toString());

		Response<Segment[]> response;
		dao = new SegmentsDAO();
		try {
			if (removeFromDatabase(id)) {
				//return all local segments
				removeFromBucket(id);
				response = new Response<Segment[]>(ListLocalSegmentsHandler.getSegments(logger, dao), 200);
			} else {
				response = new Response<Segment[]>(400, "Segment not deleted");
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
	boolean removeFromDatabase(Id id) throws Exception {
		if (logger != null) { logger.log("in removeFromDatabase"); }
		
		return dao.deleteSegment(id);
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	boolean removeFromBucket(Id id) throws Exception {
		logger.log("in removeFromBucket");
		
		//create s3 object
		logger.log("attach to S3 request");
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		logger.log("attach to S3 succeed");
		
		s3.deleteObject("hotspurproject/segments/", id.getId());
		
		// if we ever get here, then whole thing was deleted
		logger.log("removeFromBucket complete");
		return true;
	}

}
