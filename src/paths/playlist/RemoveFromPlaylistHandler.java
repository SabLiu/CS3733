package paths.playlist;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import definitions.Id;
import definitions.Playlist;

 class RemoveFromPlaylistHandler implements RequestHandler<Id, Playlist>{

	@Override
	public Playlist handleRequest(Id input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
