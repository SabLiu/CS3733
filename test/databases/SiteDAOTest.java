package databases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import definitions.Id;
import definitions.Playlist;
import definitions.Site;



public class SiteDAOTest {
	/**
	 * Tests getting all the sites from the database, assumes it is empty(no longer works because the database changed)
	 */
	/*
	@Test
	public void testGetAll() {
		SiteDAO getter = new SiteDAO();
		List<Site> gottenSites = new ArrayList<>();
		try {
			gottenSites = getter.getAllSites();
			assertTrue(gottenSites.size() == 0);
		}catch(Exception e) {
			fail("exception");
		}
	}
	*/
	/**
	 * Tests the set, get, and delete site functions
	 */
	@Test
	public void testSetGetAndDelete() {
		SiteDAO tester = new SiteDAO();
		List<Site> gottenSites = new ArrayList<>();
		Id ia = new Id();
		Site sa = new Site(ia, "https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		Id ib = new Id();
		Site sb = new Site(ib, "https://downloadmoreram.com/");
		Id ic = new Id();
		Site sc = new Site(ic, "https://www.google.com/search?rlz=1C1CHBF_enUS809US809&biw=1536&bih=722&tbm=isch&sxsrf=ACYBGNTUkf3YauPod0akRWSaM_xH88sNlw%3A1575322078499&sa=1&ei=3oHlXeOEHo7u_QbO7IeQAg&q=carrot+costume&oq=carrot+costume&gs_l=img.3..0l10.3041.5033..6027...0.0..1.216.781.9j0j1......0....1..gws-wiz-img.......35i39j0i67.6zMc65mzIgg&ved=0ahUKEwjj65SH9JfmAhUOd98KHU72ASIQ4dUDCAc&uact=5");
		List<Site> controleSitesa = new ArrayList<>();
		controleSitesa.add(sa);
		try {
			//adding/getting test
			boolean ta = tester.addSite(sa);
			boolean tb = !tester.addSite(sa);
			gottenSites = tester.getAllSites();
			int i = 0;
			boolean tc = true;
			//checking to make sure the controle and returned arrays are the same
			while(i<controleSitesa.size()) {
				int j = 0;
				boolean tca = false;
				while(j < gottenSites.size()) {
					if(controleSitesa.get(i).equals(gottenSites.get(j))) {
						tca = true;
						//System.out.println("tc");
						//fail("tc");
					}
					j++;
				}
				if(!tca) {
					tc = false;
				}
				i++;
			}
			boolean td = tester.addSite(sb);
			List<Site> controleSitesb = new ArrayList<>();
			controleSitesb.add(sa);
			controleSitesb.add(sb);
			boolean te = tester.addSite(sc);
			controleSitesb.add(sc);
			boolean tf = true;
			gottenSites = tester.getAllSites();
			i = 0;
			//checking to make sure the controle and returned arrays are the same
			while(i<controleSitesb.size() ) {
				int j = 0;
				boolean m = true;
				while(j<gottenSites.size()) {
					if(controleSitesb.get(i).equals(gottenSites.get(j))) {
					m = true;
					controleSitesb.remove(i);
					gottenSites.remove(j);
					j = gottenSites.size()+10;
					}else {
						m = false;
					}
					j++;
				}
				if(!m) {
					tf = false;
				}
				i++;
			}
			//deleting test
			boolean tg = tester.deleteSite(ib);
			List<Site> controleSitesc = new ArrayList<>();
			controleSitesc.add(sa);
			controleSitesc.add(sc);
			boolean th = true;
			gottenSites = tester.getAllSites();
			i = 0;
			//checking to make sure the controle and returned arrays are the same
			while(i<controleSitesc.size() ) {
				int j = 0;
				boolean m = true;
				while(j<gottenSites.size()) {
					if(controleSitesc.get(i).equals(gottenSites.get(j))) {
					m = true;
					controleSitesc.remove(i);
					gottenSites.remove(j);
					j = gottenSites.size()+10;
					}else {
						m = false;
					}
					j++;
				}
				if(!m) {
					th = false;
				}
				i++;
			}
			
			boolean ti = tester.deleteSite(ic);
			List<Site> controleSitesd = new ArrayList<>();
			controleSitesd.add(sa);
			boolean tj = true;
			gottenSites = tester.getAllSites();
			i = 0;
			//checking to make sure the controle and returned arrays are the same
			while(i<controleSitesd.size() ) {
				int j = 0;
				boolean m = true;
				while(j<gottenSites.size()) {
					if(controleSitesd.get(i).equals(gottenSites.get(j))) {
					m = true;
					controleSitesd.remove(i);
					gottenSites.remove(j);
					j = gottenSites.size()+10;
					}else {
						m = false;
					}
					j++;
				}
				if(!m) {
					tj = false;
				}
				i++;
			}
			
			boolean tk = tester.deleteSite(ia);
			List<Site> controleSitese = new ArrayList<>();
			boolean tl = true;
			gottenSites = tester.getAllSites();
			i = 0;
			//checking to make sure the controle and returned arrays are the same
			while(i<controleSitese.size() ) {
				int j = 0;
				boolean m = true;
				while(j<gottenSites.size()) {
					if(controleSitese.get(i).equals(gottenSites.get(j))) {
					m = true;
					controleSitese.remove(i);
					gottenSites.remove(j);
					j = gottenSites.size()+10;
					}else {
						m = false;
					}
					j++;
				}
				if(!m) {
					tl = false;
				}
				i++;
			}
			
			assertTrue(ta && tb && tc && td && te && tf && tg && th && ti && tj && tk && tl);
		}catch(Exception e) {
			fail("exception");
		}
	}

}
