import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class DataManager_updateOrgAccount_Test {

    @Test
    public void testUpdateAccount_Success() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\"}";
            }
        });

        boolean result = dm.updateOrgAccount("myOrganizationID123", "UpdateOrgName", "Updated Org Description");

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    public void testUpdateAccount_Failure() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"not found\"}";
            }
        });

        boolean result = dm.updateOrgAccount("myOrganizationID123", "UpdateOrgName", "Updated Org Description");
        assertNotNull(result);
        assertFalse(result);
    }

    @Test(expected=IllegalStateException.class)
    public void testUpdateOrgAccount_NullWebClient() {
        DataManager dm = new DataManager(null);
        boolean result = dm.updateOrgAccount("myOrganizationID123", "UpdateOrgName", "Updated Org Description");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testUpdateOrgAccount_NullId() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001));
        dm.updateOrgAccount(null, "UpdateOrgName", "Updated Org Description");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testUpdateOrgAccount_NullName() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001));
        boolean result = dm.updateOrgAccount("myOrganizationID123", null, "Updated Org Description");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testUpdateOrgAccount_NullDescription() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001));
        boolean result = dm.updateOrgAccount("myOrganizationID123", "UpdateOrgName", null);
    }

    @Test(expected=IllegalStateException.class)
    public void testUpdateOrgAccount_WebClientFailToConnectServer() {
        DataManager dm = new DataManager(new WebClient("localhost", 4000));
        boolean result = dm.updateOrgAccount("myOrganizationID123", "UpdateOrgName", "Updated Org Description");
    }

    @Test(expected=IllegalStateException.class)
    public void testUpdateOrgAccount_WebClientReturnsNull() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return null;
            }
        });
        boolean result = dm.updateOrgAccount("myOrganizationID123", "UpdateOrgName", "Updated Org Description");
    }

    @Test(expected=IllegalStateException.class)
    public void testUpdateOrgAccount_WebClientStatusError() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"error\",\"error\":\"An unexpected database error occurred\"}";
            }
        });
        boolean result = dm.updateOrgAccount("myOrganizationID123", "UpdateOrgName", "Updated Org Description");
    }

    @Test(expected=IllegalStateException.class)
    public void testUpdateOrgAccount_WebClientReturnsWrongFormat() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "Wrong Format";
            }
        });
        boolean result = dm.updateOrgAccount("myOrganizationID123", "UpdateOrgName", "Updated Org Description");
    }

    @Test(expected=IllegalStateException.class)
    public void testUpdateOrgAccount_WebClientReturnsMalformedJSONStatus() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"error\":\"An unexpected database error occurred\"}";
            }
        });
        boolean result = dm.updateOrgAccount("myOrganizationID123", "UpdateOrgName", "Updated Org Description");
    }
}
