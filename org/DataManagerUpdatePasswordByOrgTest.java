import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class DataManagerUpdatePasswordByOrgTest {

    @Test
    public void testSuccessfulUpdate() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"password1\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });

        boolean result = dm.updatePasswordByOrg("123", "password1");
        assertTrue(result);
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidJsonObjectInCreation() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "json object is invalid";
            }
        });

        dm.updatePasswordByOrg("123", "password1");
    }

    @Test
    public void testUnsuccessfulCreation() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"unsuccessful\",\"message\":\"Can not create the new fund\"}";

            }

        });


        boolean result = dm.updatePasswordByOrg("123", "password1");
        assertFalse(result);
    }

    @Test(expected = IllegalStateException.class)
    public void testStatusMissingCreation() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"password1\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });


        dm.updatePasswordByOrg("123", "password1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputIdCreationWithNull() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":null,\"login\":\"login1\",\"password\":\"password1\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });

        // id is null
        dm.updatePasswordByOrg(null, "password1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputPasswordCreationWithNull() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":null,\"name\":null,\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });

        // password is null
        dm.updatePasswordByOrg("123", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputIdCreationWithEmptyString() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"\",\"login\":\"login1\",\"password\":\"password1\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });

        // id is empty
        dm.updatePasswordByOrg("", "password1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputPasswordCreationWithEmptyString() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });

        // password is empty
        dm.updatePasswordByOrg("login1", "");

    }

    @Test(expected = RuntimeException.class)
    public void testExceptionInCreation() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                throw new RuntimeException("Some unexpected error");
            }
        });

        dm.updatePasswordByOrg("login1", "password1");
    }

    // webClient returns error
    @Test(expected = IllegalStateException.class)
    public void testErrorStatusCreation() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"error\",\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"password1\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";
            }
        });
        dm.updatePasswordByOrg("login1", "password1");
    }

    // webClient returns unknown status
    @Test(expected = IllegalStateException.class)
    public void testUnknownStatusCreation() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"password1\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";
            }
        });
        dm.updatePasswordByOrg("login1", "password1");
    }

    // webClient returns null
    @Test(expected = IllegalStateException.class)
    public void testNullStatusCreation() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return null;
            }
        });
        dm.updatePasswordByOrg("login1", "password1");
    }

    // webClient is null
    @Test(expected = IllegalStateException.class)
    public void testNullWebClientCreation() {
        DataManager dm = new DataManager(null);
        dm.updatePasswordByOrg("login1", "password1");
    }

}