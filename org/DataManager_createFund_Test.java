import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_createFund_Test {
	
	/*
	 * This is a test class for the DataManager.createFund method.
	 * Add more tests here for this method as needed.
	 * 
	 * When writing tests for other methods, be sure to put them into separate
	 * JUnit test classes.
	 */

	@Test
	public void testSuccessfulCreation() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\":\"success\",\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

			}
			
		});
		
		
		Fund f = dm.createFund("12345", "new fund", "this is the new fund", 10000);
		
		assertNotNull(f);
		assertEquals("this is the new fund", f.getDescription());
		assertEquals("12345", f.getId());
		assertEquals("new fund", f.getName());
		assertEquals(10000, f.getTarget());
		
	}
	
	
    @Test
    public void testInvalidJsonObjectInCreation() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "json object is invalid";
            }
        });

        Fund f = dm.createFund("12345", "new fund", "this is the new fund", 10000);
        assertNull(f);
    }


    @Test
    public void testUnsuccessfulCreation() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"unsuccessful\",\"message\":\"Can not create the new fund\"}";

            }
            
        });
        
        
        Fund f = dm.createFund("12345", "new fund", "this is the new fund", 10000);
        
        assertNull(f);
    }


    @Test
    public void testStatusMissingCreation() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

            }
            
        });
        
        
        Fund f = dm.createFund("12345", "new fund", "this is the new fund", 10000);
        
        assertNull(f);
    }
    
    @Test
    public void testInvalidInputIdCreationWithNull() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":null,\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

            }
            
        });
        
        // id is null
        Fund f = dm.createFund(null, "new fund", "this is the new fund", 10000);
        
        assertNull(f);       
    }
    
    @Test
    public void testInvalidInputNameCreationWithNull() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"12345\",\"name\":null,\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

            }
            
        });
        
        // name is null
        Fund f = dm.createFund("12345", null, "this is the new fund", 10000);
        
        assertNull(f);       
    }
    
    @Test
    public void testInvalidInputDesCreationWithNull() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":null,\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

            }
            
        });
        
        // des is null
        Fund f = dm.createFund("12345", "new fund", null, 10000);
        
        assertNull(f);       
    }
    
    @Test
    public void testInvalidInputIdCreationWithEmptyString() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

            }
            
        });
        
        // id is empty
        Fund f = dm.createFund("", "new fund", "this is the new fund", 10000);
        
        assertNull(f);    
    }
    
    @Test
    public void testInvalidInputNameCreationWithEmptyString() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"12345\",\"name\":\"\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

            }
            
        });
        
        // name is empty
        Fund f = dm.createFund("12345", "", "this is the new fund", 10000);
        
        assertNull(f);       
    }
    
    @Test
    public void testInvalidInputDesCreationWithEmptyString() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

            }
            
        });
        
        // des is empty
        Fund f = dm.createFund("12345", "new fund", "", 10000);
        
        assertNull(f);       
    }
    
    @Test
    public void testInvalidInputCreationWithNegativeNumber() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":-10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

            }
            
        });
        
        // target is negative
        Fund f = dm.createFund("12345", "new fund", "this is the new fund", -10000);
        
        assertNull(f);    
    }
    
    @Test
    public void testExceptionInCreation() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                throw new RuntimeException("Some unexpected error");
            }
        });
        
        Fund f = dm.createFund("12345", "new fund", "this is the new fund", 10000);
        assertNull(f);
    }

}
