package definitions;

public class Segment {
	Id id;
	boolean isRemotelyAvailable;
	String sentence;
	String character;
	String videoFileAddress;
	
	public Segment(Id id, boolean isRemotelyAvailable, String sentence, String character, String videoFileAddress) {
		this.id = id;
		this.isRemotelyAvailable = isRemotelyAvailable;
		this.sentence = sentence;
		this.character = character;
		this.videoFileAddress = videoFileAddress;
	}

	public Id getId() {
		return id;
	}

	public boolean isRemotelyAvailable() {
		return isRemotelyAvailable;
	}

	public String getSentence() {
		return sentence;
	}

	public String getCharacter() {
		return character;
	}

	public String getVideoFileAddress() {
		return videoFileAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((character == null) ? 0 : character.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isRemotelyAvailable ? 1231 : 1237);
		result = prime * result + ((sentence == null) ? 0 : sentence.hashCode());
		result = prime * result + ((videoFileAddress == null) ? 0 : videoFileAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segment other = (Segment) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isRemotelyAvailable != other.isRemotelyAvailable)
			return false;
		if (sentence == null) {
			if (other.sentence != null)
				return false;
		} else if (!sentence.equals(other.sentence))
			return false;
		if (videoFileAddress == null) {
			if (other.videoFileAddress != null)
				return false;
		} else if (!videoFileAddress.equals(other.videoFileAddress))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Segment [id=" + id + ", isRemotelyAvailable=" + isRemotelyAvailable + ", sentence=" + sentence
				+ ", character=" + character + ", videoFileAddress=" + videoFileAddress + "]";
	}
	
	
}
