import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_getContributorName_Test {
	
    @Test
    public void testSuccessfulContributorNameLookup() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":\"newUser\"}";

            }
            
        });
        
        String name = dm.getContributorName("456");
        
        assertNotNull(name);
        assertEquals("newUser", name);
    }

    @Test(expected = IllegalStateException.class)
    public void testContributorNameLookupWithNullResponse() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return null;
            }
            
        });
        
        dm.getContributorName("789");

    }

    @Test(expected = IllegalStateException.class)
    public void testContributorNameLookupWithStatusMissingInResponse() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"data\":\"newUser\"}";
            }
            
        });
        
        dm.getContributorName("101112");
    }

    @Test
    public void testContributorNameLookupWithInvalidResponse() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"unsuccessful\"}";
            }
            
        });
        
        String name = dm.getContributorName("131415");
        
        assertNull(name);
    }

    @Test(expected = IllegalStateException.class)
    public void testContributorNameLookupWithNonJSONResponse() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "Json is invalid";
            }
            
        });
        
        dm.getContributorName("161718");

    }

}
