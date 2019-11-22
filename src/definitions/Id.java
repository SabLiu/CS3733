package definitions;

public class Id {
	String id;
	
	public Id(String uuid) {
		this.id = uuid;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Id) && (((Id)obj).id == this.id);
	}
		
}
