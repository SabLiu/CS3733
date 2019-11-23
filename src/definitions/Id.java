package definitions;

import java.util.UUID;

public class Id {
	final UUID id;
	
	public Id() {
		this.id = UUID.randomUUID();
	}
	
	public Id(UUID uuid) {
		this.id = uuid;
	}
	
	public UUID getId() {
		return id;
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
		return id.toString();
	}
			
}
