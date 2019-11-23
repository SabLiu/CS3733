package definitions;

public class SegmentAndPlaylistRequest {
	Id segmentId;
	Id playlistId;
	
	public SegmentAndPlaylistRequest(Id segmentId, Id playlistId) {
		this.segmentId = segmentId;
		this.playlistId = playlistId;
	}
	
	public Id getSegmentId() {
		return segmentId;
	}

	public Id getPlaylistId() {
		return playlistId;
	}

	@Override
	public int hashCode() {
		return segmentId.hashCode() + playlistId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) && (obj instanceof SegmentAndPlaylistRequest) && 
				(this.segmentId.equals((((SegmentAndPlaylistRequest)obj).segmentId))) &&
				(this.playlistId.equals((((SegmentAndPlaylistRequest)obj).playlistId)));
	}
	
	@Override
	public String toString() {			
		return "Segment ID: " + segmentId + 
				"\nPlaylist ID: " + playlistId + "\n";	
	}
}
