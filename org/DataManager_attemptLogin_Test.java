import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class DataManager_attemptLogin_Test {
	
	@Test
	public void testSuccessfulLogin() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if (resource.equals("/findContributorNameById")) {
					return "{\"status\":\"success\",\"data\":\"cleoyaojiang\"}";
				}
				
				return "{\"status\":\"success\", \"data\": {\"_id\": \"123\", "
						+ "\"description\": \"org description\", \"name\": \"cleoyaojiang\","
						+ "\"funds\": [{\"_id\": \"1\", \"name\": \"cleoyaojiang\", \"target\": \"1000\","
						+ "\"description\": \"fund description\", \"donations\": [{"
						+ "\"contributor\": \"jkl\", \"amount\": \"100\", \"date\": \"03-June-2023\"}]}]}}"; 
			}
			
		});
		
		
		Organization org = dm.attemptLogin("cleoyaojiang", "password");
		
		assertNotNull(org);
		assertEquals(org.getId(), "123");
		assertEquals(org.getDescription(), "org description");
		assertEquals(org.getName(), "cleoyaojiang");
		
		List<Fund> funds = org.getFunds(); 
		Fund fund = funds.get(0);
		assertEquals(fund.getId(), "1");
		assertEquals(fund.getName(), "cleoyaojiang");
		assertEquals(fund.getTarget(), 1000);
		assertEquals(fund.getDescription(), "fund description");
		
		Donation donation = fund.getDonations().get(0);
		assertEquals(donation.getAmount(), 100);
		assertEquals(donation.getDate(), "03-June-2023");
		assertEquals(donation.getFundId(), "1");
	}
	

	@Test
	public void testLoginFailedNullResponse() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "";

			}
			
		});
		
		
		Organization org = dm.attemptLogin("cleoyaojiang", "password");
		
		assertNull(org);
	}


	@Test
	public void testLoginFailedStatusMissing() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"unknown_field\":\"unauthorized\"}";

			}
			
		});
		
		
		Organization org = dm.attemptLogin("cleoyaojiang", "password");
		
		assertNull(org);
	}


	@Test
	public void testLoginFailedNotWellFormed() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"unauthorized\"}";

			}
			
		});
		
		
		Organization org = dm.attemptLogin("cleoyaojiang", "password");
		
		assertNull(org);
	}
	
	
	@Test
	public void testLoginFailedNotJSON() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "this_is_not_json";

			}
			
		});
		
		
		Organization org = dm.attemptLogin("cleoyaojiang", "password");
		
		assertNull(org);
	}
}
