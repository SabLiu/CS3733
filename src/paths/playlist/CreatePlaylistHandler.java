package paths.playlist;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.PlaylistDAO;
import definitions.Id;
import definitions.Playlist;
import definitions.Response;
import definitions.Segment;
import paths.segment.ListLocalSegmentsHandler;

public class CreatePlaylistHandler implements RequestHandler<Playlist, Response<Playlist[]>>{
	LambdaLogger logger;
	PlaylistDAO dao;
		
	@Override
	public Response<Playlist[]> handleRequest(Playlist playlist, Context context) {
		logger = context.getLogger();
		logger.log(playlist.toString());

		Response<Playlist[]> response;
		dao = new PlaylistDAO(); 
		try {
			if (addToDatabase(playlist)) {
				response = new Response<Playlist[]>(ListPlaylistsHandler.getPlaylists(logger,dao), 200);
			} else {
				response = new Response<Playlist[]>(400, "Segment not added"); 
			}
		} catch(Exception e) { 
			response = new Response<Playlist[]>(400, "Unable to complete request: " +  "(" + e.getMessage() + ")");
			
			for(StackTraceElement st: e.getStackTrace()){logger.log(st + "\n");}
		}

		return response;
	}
		
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean addToDatabase(Playlist playlist) throws Exception {
		if (logger != null) { logger.log("in addToDatabase\n"); }
		
		return dao.addPlaylist(playlist);
	}
}
