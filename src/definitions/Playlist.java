package definitions;

public class Playlist {
	Id id;
	String name;
	Segment[] segments;
	
	public Playlist() {
		this.segments = new Segment[0];
		this.id = new Id();
	}
	
	public Playlist(String name) {
		this.name = name;
		this.segments = new Segment[0];
		this.id = new Id();
	}
	
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
	    System.arraycopy(segments, 0, newSegments, this.segments.length, segments.length);
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
	
	
	public void setId(Id id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSegments(Segment[] segments) {
		this.segments = segments;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean x = false;
		if((obj != null) && (obj instanceof Playlist)) {
			int i = 0;
			x = (this.id.equals((((Playlist)obj).id)));
			while(i<this.segments.length && i< ((Playlist)obj).segments.length && x) {
				if(!this.segments[i].equals(((Playlist)obj).segments[i])) {
					x = false;
				}
			}
		}
		return x;
	}
	
	@Override
	public String toString() {
		String str = "Playlist: " + name + "\n\t" + id + "\n";
		
		if(segments == null) {return str;}
		for(Segment segment : segments) {
			str += "\t" + segment.toString();
		}
			
		return str;	
	}
}
