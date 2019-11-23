package definitions;

import java.util.ArrayList;

public class Playlist {
	final Id id;
	String name;
	Segment[] segments;
	
	public Playlist(Id id, String name) {
		this.id = id;
		this.name = name;
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
