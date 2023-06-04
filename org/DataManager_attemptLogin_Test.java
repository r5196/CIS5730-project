import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class DataManager_attemptLogin_Test {
    
    @Test
    public void testSuccessfulAttemptLogin() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\", \"data\": {\"_id\":\"999\", \"name\":\"New Org\", \"description\":\"This is a new organization\", "
                        + "\"funds\":[{\"_id\":\"888\", \"name\":\"Fund New\", \"description\":\"This is a new fund\", \"target\":5000, "
                        + "\"donations\": [{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}]}}";
            }
        });
        
        Organization org = dm.attemptLogin("login", "password");
        
        assertNotNull(org);
        assertEquals("999", org.getId());
        assertEquals("New Org", org.getName());
        assertEquals("This is a new organization", org.getDescription());
        assertEquals(1, org.getFunds().size());
        Fund fund = org.getFunds().get(0);
        assertEquals("888", fund.getId());
        assertEquals("Fund New", fund.getName());
        assertEquals("This is a new fund", fund.getDescription());
        assertEquals(5000, fund.getTarget());
        assertEquals(1, fund.getDonations().size());
        Donation donation = fund.getDonations().get(0);
        assertEquals("888", donation.getFundId());
        assertEquals(3000, donation.getAmount());
        assertEquals("August 04, 2023", donation.getDate());
    }

	

	@Test(expected = IllegalStateException.class)
	public void testUnsucessfulAttemptLoginWithNullResponse() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return null;

			}
			
		});
		
		
		dm.attemptLogin("login", "password");
	}


	@Test(expected = IllegalStateException.class)
	public void testUnsucessfulAttemptLoginWithStatusMissing() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"data\": {\"_id\":\"999\", \"description\":\"This is a new organization\", "
                + "\"funds\":[{\"_id\":\"888\", \"name\":\"Fund New\", \"description\":\"This is a new fund\", \"target\":5000, "
                + "\"donations\": [{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}]}}";

			}
			
		});
		
		
		dm.attemptLogin("login", "password");
	}


	@Test
	public void testUnsucessfulAttemptLoginWithStatusFailed() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"unsuccessful\", \"data\": {\"_id\":\"999\", \"name\":\"New Org\", \"description\":\"This is a new organization\", "
                + "\"funds\":[{\"_id\":\"888\", \"name\":\"Fund New\", \"description\":\"This is a new fund\", \"target\":5000, "
                + "\"donations\": [{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}]}}";

			}
			
		});
		
		
		Organization org = dm.attemptLogin("login", "password");
		
		assertNull(org);
	}
	
	
	@Test(expected = IllegalStateException.class)
	public void testLoginFailsWithInvalidJSON() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "Json is invalid";

			}
			
		});
		
		
		dm.attemptLogin("login", "password");
	}
}
