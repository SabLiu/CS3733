package paths.playlist;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.PlaylistDAO;
import definitions.Id;
import definitions.Playlist;
import definitions.Response;
import definitions.SegmentAndPlaylistRequest;

public class RemoveFromPlaylistHandler implements RequestHandler<SegmentAndPlaylistRequest, Response<Playlist>>{ 
	LambdaLogger logger;
	PlaylistDAO dao;
	
	@Override
	public Response<Playlist> handleRequest(SegmentAndPlaylistRequest ids, Context context) {
		logger = context.getLogger();
		logger.log(ids.toString());

		Response<Playlist> response;
		dao = new PlaylistDAO();
		try {
			if (removeSegment(ids.getSegmentId(), ids.getPlaylistId())) {
				response = new Response<Playlist>(GetPlaylistHandler.getPlaylist(ids.getPlaylistId(), logger, dao), 200);
			}  else {
				response = new Response<Playlist>(400, "Segment not removed");
			}
		} catch(Exception e) {
			response = new Response<Playlist>(400, "Unable to complete request: " +  "(" + e.getMessage() + ")");
		}

		return response;
	}
		
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean removeSegment(Id segmentId, Id playlistId) throws Exception {
		if (logger != null) { logger.log("in removeSegment (Database)\n"); }
		
		return dao.deleteFromPlaylist(playlistId, segmentId);
	}
}
