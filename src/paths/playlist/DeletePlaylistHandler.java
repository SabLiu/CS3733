package paths.playlist;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import databases.PlaylistDAO;
import definitions.Id;
import definitions.Playlist;
import definitions.Response;

public class DeletePlaylistHandler implements RequestHandler<Id, Response<Playlist[]>>{
		LambdaLogger logger;
		PlaylistDAO dao;
			
		@Override
		public Response<Playlist[]> handleRequest(Id id, Context context) {
			logger = context.getLogger();
			logger.log(id.toString());

			Response<Playlist[]> response;
			dao = new PlaylistDAO();
			try {
				if (removeFromDatabase(id)) {
					response = new Response<Playlist[]>(ListPlaylistsHandler.getPlaylists(logger,dao), 200);
				} else {
					response = new Response<Playlist[]>(400, "Segment not removed");
				}
			} catch(Exception e) {
				response = new Response<Playlist[]>(400, "Unable to complete request: " +  "(" + e.getMessage() + ")");
			}

			return response;
		}
			
		/** Store into RDS.
		 * 
		 * @throws Exception 
		 */
		boolean removeFromDatabase(Id id) throws Exception {
			if (logger != null) { logger.log("in addToDatabase\n"); }
			
			return dao.deletePlaylist(id);
		}

}
