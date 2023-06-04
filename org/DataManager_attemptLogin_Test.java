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
                return "{\"status\":\"success\", \"data\": {\"_id\":\"123\", \"name\":\"Test Org\", \"description\":\"This is a test organization\", "
                        + "\"funds\":[{\"_id\":\"456\", \"name\":\"Fund 1\", \"description\":\"This is fund 1\", \"target\":1000, "
                        + "\"donations\": [{\"contributor\":\"789\", \"amount\":500, \"date\":\"2023-06-04\"}]}]}}";
            }
        });
        
        
        Organization org = dm.attemptLogin("login", "password");
        
        assertNotNull(org);
        assertEquals("123", org.getId());
        assertEquals("Test Org", org.getName());
        assertEquals("This is a test organization", org.getDescription());
        assertEquals(1, org.getFunds().size());
        Fund fund = org.getFunds().get(0);
        assertEquals("456", fund.getId());
        assertEquals("Fund 1", fund.getName());
        assertEquals("This is fund 1", fund.getDescription());
        assertEquals(1000, fund.getTarget());
        assertEquals(1, fund.getDonations().size());
        Donation donation = fund.getDonations().get(0);
        assertEquals("456", donation.getFundId());
        assertEquals(500, donation.getAmount());
        assertEquals("June 04, 2023", donation.getDate());
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
