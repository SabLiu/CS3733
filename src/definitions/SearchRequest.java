package definitions;

public class SearchRequest {
	String sentenceKeyphrase;
	String characterKeyphrase;
	
	public SearchRequest() {
	}
	
	public SearchRequest(String characterKeyphrase, String sentenceKeyphrase) {
		this.sentenceKeyphrase = sentenceKeyphrase;
		this.characterKeyphrase = characterKeyphrase;
	}

	public String getSentenceKeyphrase() {
		return sentenceKeyphrase;
	}

	public void setSentenceKeyphrase(String sentenceKeyphrase) {
		this.sentenceKeyphrase = sentenceKeyphrase;
	}

	public String getCharacterKeyphrase() {
		return characterKeyphrase;
	}

	public void setCharacterKeyphrase(String characterKeyphrase) {
		this.characterKeyphrase = characterKeyphrase;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((characterKeyphrase == null) ? 0 : characterKeyphrase.hashCode());
		result = prime * result + ((sentenceKeyphrase == null) ? 0 : sentenceKeyphrase.hashCode());
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
		SearchRequest other = (SearchRequest) obj;
		if (characterKeyphrase == null) {
			if (other.characterKeyphrase != null)
				return false;
		} else if (!characterKeyphrase.equals(other.characterKeyphrase))
			return false;
		if (sentenceKeyphrase == null) {
			if (other.sentenceKeyphrase != null)
				return false;
		} else if (!sentenceKeyphrase.equals(other.sentenceKeyphrase))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String str = "SearchRequest:";
		
		if(sentenceKeyphrase != null) {
			str += "\n\tSentence Keyphrase: " + sentenceKeyphrase;
		}
		
		if(characterKeyphrase != null) {
			str += "\n\tcharacterKeyphrase: " + characterKeyphrase;
		}
		
		str += "\n";
		
		return str;

	}
	
	
}
