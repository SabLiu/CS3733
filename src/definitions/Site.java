package definitions;

public class Site {
	Id id; 
	String url;
	
	public Site() {
		this.id = new Id();
	}
	
	public Site(Id id, String url) {
		this.id = id;
		this.url = url;
	}  
	
	public Id getId() {
		return id;
	}
	
	public String getUrl() {
		return url;
	}

	@Override
	public int hashCode() {
		return id.hashCode(); 
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) && (obj instanceof Site) && (this.id.equals(((Site)obj).id));
	}

	@Override
	public String toString() {
		return ("Site: " + url + '\n' +
				"\t" + id + "\n");
	}
}
