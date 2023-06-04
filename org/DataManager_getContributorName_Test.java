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

    @Test
    public void testContributorNameLookupWithNullResponse() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return null;
            }
            
        });
        
        String name = dm.getContributorName("789");
        
        assertNull(name);
    }

    @Test
    public void testContributorNameLookupWithStatusMissingInResponse() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"data\":\"newUser\"}";
            }
            
        });
        
        String name = dm.getContributorName("101112");
        
        assertNull(name);
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

    @Test
    public void testContributorNameLookupWithNonJSONResponse() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "Json is invalid";
            }
            
        });
        
        String name = dm.getContributorName("161718");
        
        assertNull(name);
    }

}
