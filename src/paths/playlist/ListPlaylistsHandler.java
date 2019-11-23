package paths.playlist;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import definitions.Playlist;

 class ListPlaylistsHandler implements RequestHandler<Object, Playlist[]>{

	@Override
	public Playlist[] handleRequest(Object input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
