package definitions;

public class Segment {
	final Id id;
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
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) && (obj instanceof Segment) && (this.id.equals(((Segment)obj).id));
	}

	@Override
	public String toString() {
		return ("Segment: " + sentence + " " + character + "\n" + 
				"\t\tRemotelyAvailable: " + isRemotelyAvailable + "\n" +
				"\t\t" + id + "\n\t\t" + videoFileAddress + "\n");
	}
	
}
