package definitions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Segment {
	Id id;
	boolean isRemotelyAvailable;
	String sentence;
	String character;
	String contents;
	
	public Segment(Id id, boolean isRemotelyAvailable, String sentence, String character) {
		this.id = id;
		this.isRemotelyAvailable = isRemotelyAvailable;
		this.sentence = sentence;
		this.character = character;
	}
	
	public Segment(Id id, boolean isRemotelyAvailable, String sentence, String character, String filePath) {
		this.id = id;
		this.isRemotelyAvailable = isRemotelyAvailable;
		this.sentence = sentence;
		this.character = character;
		setContents(filePath);
	}
	
	public Segment() {
	}
	
	public String getContents() {
		return contents;
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

	public void setId(Id id) {
		this.id = id;
	}

	public void setRemotelyAvailable(boolean isRemotelyAvailable) {
		this.isRemotelyAvailable = isRemotelyAvailable;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public void setCharacter(String character) {
		this.character = character;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
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
				"\t\t" + id + "\n");
	}
	
}
