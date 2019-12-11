package definitions;

public class Playlist {
	Id id;
	String name;
	String[] segmentUrls;
	
	public Playlist() {
		this.segmentUrls = new String[0];
		this.id = new Id();
	}
	
	public Playlist(String name) {
		this.name = name;
		this.segmentUrls = new String[0];
		this.id = new Id();
	}
	
	public Playlist(Id id, String name) {
		this.id = id;
		this.name = name;
		this.segmentUrls = new String[0];
	}
	
	public Playlist(Id id, String name, Segment[] segments) {
		this.id = id;
		this.name = name;
		this.segmentUrls = new String[segments.length];
		int i = 0;
		while(i<segments.length) {
			this.segmentUrls[i] = segments[i].getUrl();
			i++;
		}
	} 
	
	public void addSegments(String[] segmentUrls) {
		String[] newSegments = new String[this.segmentUrls.length + segmentUrls.length];
	    System.arraycopy(this.segmentUrls, 0, newSegments, 0, this.segmentUrls.length);
	    System.arraycopy(segmentUrls, 0, newSegments, this.segmentUrls.length, segmentUrls.length);
		this.segmentUrls = newSegments;
	}
	
	public void addSegments(Segment[] segments) {
		String[] newSegments = new String[this.segmentUrls.length + segments.length];
	    System.arraycopy(this.segmentUrls, 0, newSegments, 0, this.segmentUrls.length);
	    int ins = this.segmentUrls.length;
	    int is = 0;
	    while(ins < this.segmentUrls.length + segments.length) {
	    	newSegments[ins] = segments[is].getUrl();
	    	ins++;
	    	is++;
	    }
		this.segmentUrls = newSegments;
	}
	
	public void addSegment(Segment segment) {
		String[] newSegments = new String[this.segmentUrls.length + 1];
	    System.arraycopy(this.segmentUrls, 0, newSegments, 0, this.segmentUrls.length);
	    newSegments[newSegments.length - 1] = segment.getUrl();
		this.segmentUrls = newSegments;
	}

	public Id getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String[] getSegmentUrls() {
		return segmentUrls;
	}
	
	public void setSegmentUrls(String[] newUrls) {
		this.segmentUrls = newUrls;
	}
	
	
	public void setId(Id id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSegments(Segment[] segments) {
		String[] newSegments = new String[segments.length];
		int i = 0;
		while(i < segments.length) {
			newSegments[i] = segments[i].getUrl();
			i++;
		}
		this.segmentUrls = newSegments;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean x = false;
		boolean y = false;
		if((obj != null) && (obj instanceof Playlist)) {
			int i = 0;
			x = (this.id.equals((((Playlist)obj).id)));
			y = (this.segmentUrls.length == ((Playlist)obj).segmentUrls.length);
			while(i<this.segmentUrls.length && i< ((Playlist)obj).segmentUrls.length && x && y) {
				if(!this.segmentUrls[i].equals(((Playlist)obj).segmentUrls[i])) {
					x = false;
				}
				i++;
			}
		}
		return x && y;
	}
	
	@Override
	public String toString() {
		String str = "Playlist: " + name + "\n\t" + id + "\n";
		
		if(segmentUrls == null) {return str;}
		for(String segment : segmentUrls) {
			str += "\t" + "\"" + segment + "\"";
		}
			
		return str;	
	}
}
