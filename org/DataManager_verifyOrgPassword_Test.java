import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class DataManager_verifyOrgPassword_Test {

    @Test
    public void testVerifyOrgPassword_Success() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\"}";
            }
        });

        boolean result = dm.verifyOrgPassword("myOrganizationID123", "myPassword123");
        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    public void testVerifyOrgPassword_Failure() {
        DataManager dm = new DataManager(new WebClient("localhost", 3000) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"not found\"}";
            }
        });

        boolean result = dm.verifyOrgPassword("myOrganizationID123", "myPassword123");
        assertNotNull(result);
        assertFalse(result);
    }

    @Test(expected = IllegalStateException.class)
    public void testVerifyOrgPassword_NullWebClient() {
        DataManager dm = new DataManager(null);
        boolean result = dm.verifyOrgPassword("myOrganizationID123", "myPassword123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerifyOrgPassword_NullId() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001));
        boolean result = dm.verifyOrgPassword(null, "myPassword123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerifyOrgPassword_NullPassword() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001));
        boolean result = dm.verifyOrgPassword("myOrganizationID123", null);
    }

    @Test(expected = IllegalStateException.class)
    public void testVerifyOrgPassword_WebClientFailToConnectServer() {
        DataManager dm = new DataManager(new WebClient("localhost", 4000));
        boolean result = dm.verifyOrgPassword("myOrganizationID123", "myPassword123");
    }

    @Test(expected = IllegalStateException.class)
    public void testVerifyOrgPassword_WebClientReturnsNull() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return null;
            }
        });
        boolean result = dm.verifyOrgPassword("myOrganizationID123", "myPassword123");
    }

    @Test(expected = IllegalStateException.class)
    public void testVerifyOrgPassword_WebClientStatusError() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"error\",\"error\":\"An unexpected database error occurred\"}";
            }
        });
        boolean result = dm.verifyOrgPassword("myOrganizationID123", "myPassword123");
    }

    @Test(expected = IllegalStateException.class)
    public void testVerifyOrgPassword_WebClientReturnsWrongFormat() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "Wrong Format";
            }
        });
        boolean result = dm.verifyOrgPassword("myOrganizationID123", "myPassword123");
    }

    @Test(expected = IllegalStateException.class)
    public void testVerifyOrgPassword_WebClientReturnsMalformedJSONStatus() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"error\":\"An unexpected database error occurred\"}";
            }
        });
        boolean result = dm.verifyOrgPassword("myOrganizationID123", "myPassword123");
    }

}

