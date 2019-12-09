package definitions.remoteapi;

public class SegmentsSendResponse {
	public SegmentsToSend[] segments;
	public int statusCode;
	public String error;
	
	public SegmentsSendResponse(SegmentsToSend[] segments, int code) {
		this.segments = segments;
		this.statusCode = code;
		this.error = ""; 
	}
	
	public SegmentsToSend[] getSegments() {
		return segments;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getError() {
		return error;
	}

	public SegmentsSendResponse(int code, String errorMessage) {
		this.segments = null;
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		String str  = "Response: " + statusCode + " " + error;
		if(segments != null) {
			str += "\n" + segments.toString();
		}
		
		return str;
	}
}
