package definitions;

import java.util.UUID;

public class Id {
	String id;
	
	public Id() {
		setRandomId();
	}
	
	public Id(String uuid) { 
		this.id = uuid;
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setRandomId() {
		this.id = UUID.randomUUID().toString() + ".ogg";
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) &&  (obj instanceof Id) && (((Id)obj).id.equals(this.id));
	}

	@Override
	public String toString() {
		return this.id;
	}
			
}
