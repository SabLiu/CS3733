package definitions.remoteapi;

public class SegmentsToSend {
	String url;
	String character;
	String text;
	
	public SegmentsToSend() {}
	
	public SegmentsToSend(String url, String character, String text) {
		this.url = url;
		this.character = character;
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
