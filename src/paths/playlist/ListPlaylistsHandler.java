package paths.playlist;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.PlaylistDAO;
import databases.SegmentDAO;
import definitions.Playlist;
import definitions.Response;
import definitions.Segment;

/**
 * Gets names and ids (metadata) of all playlists (doesn't get segments in each playlist)
 * @author maria
 *
 */
public class ListPlaylistsHandler implements RequestHandler<Object, Response<Playlist[]>>{

	public LambdaLogger logger;

	public ListPlaylistsHandler() {} 
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	static Playlist[] getPlaylists(LambdaLogger logger, PlaylistDAO dao) throws Exception {
		logger.log("in getPlaylists\n");
		Playlist[] p = {};
		p = dao.getAllPlaylists().toArray(p);
		return p;
	}
	
	@Override
	public Response<Playlist[]> handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all playlists\n");

		Playlist[] list;
		Response<Playlist[]> response;
		
		
		try {
			list = getPlaylists(logger, new PlaylistDAO());	 
			response = new Response<Playlist[]>(list, 200);
			logger.log("finished getSegments\n");
		} catch (Exception e) {
			response = new Response<Playlist[]>(400, e.getMessage());
			logger.log("EXEPCTION: " + e.getMessage());
		}
		
		return response;
	}

}
