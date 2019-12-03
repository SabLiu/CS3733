package paths.playlist;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.PlaylistDAO;
import definitions.Id;
import definitions.Playlist;
import definitions.Response;

/**
 * gets a playlist (with segments) given an id 
 * @author maria
 *
 */
public class GetPlaylistHandler implements RequestHandler<Id, Response<Playlist>>{

	public LambdaLogger logger;

	public GetPlaylistHandler() {}
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	static Playlist getPlaylist(Id PlaylistId, LambdaLogger logger, PlaylistDAO dao) throws Exception {
			logger.log("in getPlaylist\n");
			logger.log("Playlist Id:\n" + PlaylistId.toString() +"\n");
			Playlist p = dao.getFullPlaylist(PlaylistId);
			return p;
		}
	
	@Override
	public Response<Playlist> handleRequest(Id id, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all playlists\n");

		Playlist p;
		Response<Playlist> response;
		
		
		try {
			p = getPlaylist(id, logger, new PlaylistDAO());
			logger.log(p.toString());
			response = new Response<Playlist>(p, 200);
			logger.log("finished getSegments\n");
		} catch (Exception e) {
			response = new Response<Playlist>(400, e.getMessage());
			logger.log("EXEPCTION: " + e.getMessage());
		}
		
		return response;  
	}

}
