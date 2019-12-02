package databases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import definitions.Id;
import definitions.Playlist;
import definitions.Site;



public class SiteDAOTest {
	
	//Assumes the db is empty
	
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
			while(i<gottenSites.size() && i<controleSitesa.size()) {
				if(!gottenSites.get(i).equals(controleSitesa.get(i))) {
					tc = false;
					System.out.println("tc");
					//fail("tc");
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
			while(i<gottenSites.size() ) {
				int j = 0;
				boolean m = true;
				while(j<controleSitesb.size()) {
					if(gottenSites.get(i).equals(controleSitesb.get(j))) {
					m = true;
					gottenSites.remove(i);
					controleSitesb.remove(j);
					j = controleSitesb.size()+10;
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
			while(i<gottenSites.size() ) {
				int j = 0;
				boolean m = true;
				while(j<controleSitesc.size()) {
					if(gottenSites.get(i).equals(controleSitesc.get(j))) {
					m = true;
					gottenSites.remove(i);
					controleSitesc.remove(j);
					j = controleSitesc.size()+10;
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
			while(i<gottenSites.size() ) {
				int j = 0;
				boolean m = true;
				while(j<controleSitesd.size()) {
					if(gottenSites.get(i).equals(controleSitesd.get(j))) {
					m = true;
					gottenSites.remove(i);
					controleSitesd.remove(j);
					j = controleSitesd.size()+10;
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
			while(i<gottenSites.size() ) {
				int j = 0;
				boolean m = true;
				while(j<controleSitese.size()) {
					if(gottenSites.get(i).equals(controleSitese.get(j))) {
					m = true;
					gottenSites.remove(i);
					controleSitese.remove(j);
					j = controleSitese.size()+10;
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
