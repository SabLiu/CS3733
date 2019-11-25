package paths.playlist;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.PlaylistDAO;
import databases.SegmentsDAO;
import definitions.Playlist;
import definitions.Response;
import definitions.Segment;

public class ListPlaylistsHandler implements RequestHandler<Object, Response<Playlist[]>>{

	public LambdaLogger logger;

	public ListPlaylistsHandler() {}
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	Playlist[] getPlaylists() throws Exception {
			logger.log("in getSegments\n");
			PlaylistDAO dao = new PlaylistDAO();
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
			list = getPlaylists();	
			response = new Response<Playlist[]>(list, 200);
			logger.log("finished getSegments\n");
		} catch (Exception e) {
			response = new Response<Playlist[]>(400, e.getMessage());
			logger.log("EXEPCTION: " + e.getMessage());
		}
		
		return response;
	}

}
