package definitions;

public class Playlist {
	final Id id;
	String name;
	Segment[] segments;
	
	public Playlist(Id id, String name) {
		this.id = id;
		this.name = name;
		this.segments = new Segment[0];
	}
	
	public Playlist(Id id, String name, Segment[] segments) {
		this.id = id;
		this.name = name;
		this.segments = segments;
	}
	
	public void addSegments(Segment[] segments) {
        Segment[] newSegments = new Segment[this.segments.length + segments.length];
        System.arraycopy(this.segments, 0, newSegments, 0, this.segments.length);
        System.arraycopy(this.segments, 0, newSegments, this.segments.length, segments.length);
		this.segments = newSegments;
	}
	
	public void addSegment(Segment segment) {
        Segment[] newSegments = new Segment[this.segments.length + 1];
        System.arraycopy(this.segments, 0, newSegments, 0, this.segments.length);
        newSegments[newSegments.length] = segment;
		this.segments = newSegments;
	}

	public Id getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Segment[] getSegments() {
		return segments;
	}
	
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) && (obj instanceof Playlist) && (this.id.equals((((Playlist)obj).id)));
	}
	
	@Override
	public String toString() {
		String str = "Playlist: " + name + "\n\t" + id + "\n";
		
		for(Segment segment : segments) {
			str += "\t" + segment.toString();
		}
			
		return str;	
	}
}
