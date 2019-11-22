package definitions;

public class Site {
	Id id;
	String url;
	
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
}
