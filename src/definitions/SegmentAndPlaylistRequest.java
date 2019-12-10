package definitions;

public class SegmentAndPlaylistRequest {
	String segmentUrl;
	Id playlistId;
	
	public SegmentAndPlaylistRequest() {
		
	}
	
	public SegmentAndPlaylistRequest(String segmentUrl, Id playlistId) {
		this.segmentUrl = segmentUrl;
		this.playlistId = playlistId; 
	}
	
	public void setSegmentUrl(String segmentUrl) {
		this.segmentUrl = segmentUrl;
	}

	public void setPlaylistId(Id playlistId) {
		this.playlistId = playlistId;
	}

	public String getSegmentUrl() {
		return segmentUrl;
	}

	public Id getPlaylistId() {
		return playlistId;
	}

	@Override
	public int hashCode() {
		return segmentUrl.hashCode() + playlistId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) && (obj instanceof SegmentAndPlaylistRequest) && 
				(this.segmentUrl.equals((((SegmentAndPlaylistRequest)obj).segmentUrl))) &&
				(this.playlistId.equals((((SegmentAndPlaylistRequest)obj).playlistId)));
	}
	
	@Override
	public String toString() {			
		return "Segment ID: " + segmentUrl + 
				"\nPlaylist ID: " + playlistId + "\n";	
	}
}
