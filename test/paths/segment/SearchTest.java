package paths.segment;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import definitions.Id;
import definitions.Response;
import definitions.SearchRequest;
import definitions.Segment;
import lamnda.LambdaTest;

public class SearchTest extends LambdaTest{

	@Test
	public void searchByCharacter() {
		SegmentSearchHandler handler = new SegmentSearchHandler();
		SearchRequest s1 = new SearchRequest();
		s1.setCharacterKeyphrase("McCoy");
		Response<Segment[]> resp = handler.handleRequest(s1, createContext("list"));
		Segment[] ret = resp.model;
		Segment[] control = {
				new Segment(new Id("1dba4225-9077-450e-9c94-21f2eaba4e7b.ogg"), false, "You had a normal emotion", "McCoy"),
				new Segment(new Id("3c4bdc3a-a3f5-4f39-bf3a-b7f65fa9399b.ogg"), false, "Then I need a drink", "McCoy"),
				new Segment(new Id("c9314e2c-68df-48ec-af09-de17bac46ecd.ogg"), false, "Crazy way to travel, spreading a man's molecules all over the universe.", "McCoy")
		};
		int i = 0;
		boolean isEqual = true;
		while(i<ret.length && i < control.length) {
			if(!ret[i].equals(control[i])) {
				isEqual = false;
			}
			i++;
		}
		boolean wentThrough = i == 3;
		assertTrue(wentThrough && isEqual);
	}
	
	@Test
	public void searchByCharacterTwo() {
		SegmentSearchHandler handler = new SegmentSearchHandler();
		SearchRequest s1 = new SearchRequest();
		s1.setCharacterKeyphrase("Chapel");
		Response<Segment[]> resp = handler.handleRequest(s1, createContext("list"));
		Segment[] ret = resp.model;
		Segment[] control = {
				new Segment(new Id("d4117ef5-72bf-46c3-be31-d6a1f275194a.ogg"), false, "You know, self-pity is a terrible first course", "Chapel")
				};
		int i = 0;
		boolean isEqual = true;
		while(i<ret.length && i < control.length) {
			if(!ret[i].equals(control[i])) {
				isEqual = false;
			}
			i++;
		}
		boolean wentThrough = i == 1;
		assertTrue(wentThrough && isEqual);
	}
	
	@Test
	public void searchByCharacterThree() {
		SegmentSearchHandler handler = new SegmentSearchHandler();
		SearchRequest s1 = new SearchRequest();
		s1.setCharacterKeyphrase("bill");
		Response<Segment[]> resp = handler.handleRequest(s1, createContext("list"));
		Segment[] ret = resp.model;
		assertTrue(ret.length == 0);
	}
	
	@Test
	public void searchBySentence() {
		SegmentSearchHandler handler = new SegmentSearchHandler();
		SearchRequest s1 = new SearchRequest();
		s1.setSentenceKeyphrase("I ");
		Response<Segment[]> resp = handler.handleRequest(s1, createContext("list"));
		Segment[] ret = resp.model;
		Segment[] control = {
				new Segment(new Id("3c4bdc3a-a3f5-4f39-bf3a-b7f65fa9399b.ogg"), false, "Then I need a drink", "McCoy"),
				new Segment(new Id("cf7ccbaa-321f-4365-ab2d-1aabc7354f23.ogg"), false, "Kindness, Mr. Garrovick, is another human emotion, and I believe we have enough of that.", "Spock"),
				new Segment(new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"), false, "I know you would prefer to wallow in a pool of emotion", "Spock")
		};
		int i = 0;
		boolean isEqual = true;
		while(i<ret.length && i < control.length) {
			if(!ret[i].equals(control[i])) {
				isEqual = false;
			}
			i++;
		}
		boolean wentThrough = i == 3;
		assertTrue(wentThrough && isEqual);
	}
	
	@Test
	public void searchBySentenceTwo() {
		SegmentSearchHandler handler = new SegmentSearchHandler();
		SearchRequest s1 = new SearchRequest();
		s1.setSentenceKeyphrase("Garrovick");
		Response<Segment[]> resp = handler.handleRequest(s1, createContext("list"));
		Segment[] ret = resp.model;
		Segment[] control = {
				new Segment(new Id("cf7ccbaa-321f-4365-ab2d-1aabc7354f23.ogg"), false, "Kindness, Mr. Garrovick, is another human emotion, and I believe we have enough of that.", "Spock")
		};
		int i = 0;
		boolean isEqual = true;
		while(i<ret.length && i < control.length) {
			if(!ret[i].equals(control[i])) {
				isEqual = false;
			}
			i++;
		}
		boolean wentThrough = i == 1;
		assertTrue(wentThrough && isEqual);
	}
	
	@Test
	public void searchBySentenceThree() {
		SegmentSearchHandler handler = new SegmentSearchHandler();
		SearchRequest s1 = new SearchRequest();
		s1.setSentenceKeyphrase("no one will ever say this ekrjsdviwshviuwsiuvwsvwhriuwsfnvl");
		Response<Segment[]> resp = handler.handleRequest(s1, createContext("list"));
		Segment[] ret = resp.model;
		assertTrue(ret.length == 0);
	}
	
	@Test
	public void searchBySentenceAndCharacter() {
		SegmentSearchHandler handler = new SegmentSearchHandler();
		SearchRequest s1 = new SearchRequest();
		s1.setSentenceKeyphrase("I ");
		s1.setCharacterKeyphrase("Spock");
		Response<Segment[]> resp = handler.handleRequest(s1, createContext("list"));
		Segment[] ret = resp.model;
		Segment[] control = {
				new Segment(new Id("cf7ccbaa-321f-4365-ab2d-1aabc7354f23.ogg"), false, "Kindness, Mr. Garrovick, is another human emotion, and I believe we have enough of that.", "Spock"),
				new Segment(new Id("f599c86f-e60b-44d7-a231-7a3d7f9ff6cc.ogg"), false, "I know you would prefer to wallow in a pool of emotion", "Spock")
		};
		int i = 0;
		boolean isEqual = true;
		while(i<ret.length && i < control.length) {
			if(!ret[i].equals(control[i])) {
				isEqual = false;
			}
			i++;
		}
		boolean wentThrough = i == 2;
		assertTrue(wentThrough && isEqual);
	}
	
	@Test
	public void searchBySentenceAndCharacterTwo() {
		SegmentSearchHandler handler = new SegmentSearchHandler();
		SearchRequest s1 = new SearchRequest();
		s1.setSentenceKeyphrase("I ");
		s1.setCharacterKeyphrase("McCoy");
		Response<Segment[]> resp = handler.handleRequest(s1, createContext("list"));
		Segment[] ret = resp.model;
		Segment[] control = {
				new Segment(new Id("3c4bdc3a-a3f5-4f39-bf3a-b7f65fa9399b.ogg"), false, "Then I need a drink", "McCoy")
				};
		int i = 0;
		boolean isEqual = true;
		while(i<ret.length && i < control.length) {
			if(!ret[i].equals(control[i])) {
				isEqual = false;
			}
			i++;
		}
		boolean wentThrough = i == 1;
		assertTrue(wentThrough && isEqual);
	}

}
