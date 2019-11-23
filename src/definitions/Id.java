package definitions;

import java.util.UUID;

public class Id {
	final String id;
	
	public Id() {
		this.id = UUID.randomUUID().toString();
	}
	
	public Id(String uuid) {
		this.id = uuid;
	}
	
	public String getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) &&  (obj instanceof Id) && (((Id)obj).id == this.id);
	}

	@Override
	public String toString() {
		return this.id;
	}
			
}
