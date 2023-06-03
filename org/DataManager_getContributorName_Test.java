import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_getContributorName_Test {
	
	@Test
	public void testSuccessfulLookup() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"success\",\"data\":\"cleoyaojiang\"}";

			}
			
		});
		
		
		String name = dm.getContributorName("123");
		
		assertNotNull(name);
		assertEquals("cleoyaojiang", name);
	}


	@Test
	public void testLookupFailedNullResponse() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "";

			}
			
		});
		
		
		String name = dm.getContributorName("123");
		
		assertNull(name);
	}


	@Test
	public void testLookupFailedStatusMissing() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"unknown_field\":\"unauthorized\"}";

			}
			
		});
		
		
		String name = dm.getContributorName("123");
		
		assertNull(name);
	}


	@Test
	public void testLookupFailedNotWellFormed() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"unauthorized\"}";

			}
			
		});
		
		
		String name = dm.getContributorName("123");
		
		assertNull(name);
	}

	
	@Test
	public void testLookupFailedNotJSON() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "this_is_not_json";

			}
			
		});
		
		
		String name = dm.getContributorName("123");
		
		assertNull(name);
	}
}
