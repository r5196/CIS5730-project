import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class DataManagerCreateOrgTest {

    @Test
    public void testSuccessfulCreation() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"password1\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });


        Organization o = dm.createOrg("login1", "password1", "new org", "this is the new org");

        assertNotNull(o);
        assertEquals("123", o.getId());
        assertEquals("new org", o.getName());
        assertEquals("this is the new org", o.getDescription());
        assertEquals(0, o.getFunds().size());

    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidJsonObjectInCreation() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "json object is invalid";
            }
        });

        dm.createOrg("login1", "password1", "new org", "this is the new org");
    }


    @Test
    public void testUnsuccessfulCreation() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"unsuccessful\",\"message\":\"Can not create the new fund\"}";

            }

        });


        Organization o = dm.createOrg("login1", "password1", "new org", "this is the new org");
        assertNull(o);
    }


    @Test(expected = IllegalStateException.class)
    public void testStatusMissingCreation() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"password1\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });


        dm.createOrg("login1", "password1", "new org", "this is the new org");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputLoginCreationWithNull() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":null,\"password\":\"password1\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });

        // login is null
        dm.createOrg(null, "password1", "new org", "this is a new org");
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
        dm.createOrg("login1", null, "new org", "this is the new org");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputNameCreationWithNull() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"password1\",\"name\":null,\"description\":null,\"funds\":[],\"__v\":0}}";

            }

        });

        // des is null
        dm.createOrg("login1", "password1", null, "this is the new org");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputDesCreationWithNull() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"password1\",\"name\":\"new org\",\"description\":null,\"funds\":[],\"__v\":0}}";

            }

        });

        // des is null
        dm.createOrg("login1", "password1", "new org", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputLoginCreationWithEmptyString() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":\"\",\"password\":\"password1\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });

        // id is empty
        dm.createOrg("", "new fund", "new org", "this is the new org");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputPasswordCreationWithEmptyString() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"\",\"name\":\"new org\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });

        // name is empty
        dm.createOrg("login1", "", "new org", "this is the new org");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputNameCreationWithEmptyString() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"password1\",\"name\":\"\",\"description\":\"this is the new org\",\"funds\":[],\"__v\":0}}";

            }

        });

        // des is empty
        dm.createOrg("login1", "password1", "", "this is the new org");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputDesCreationWithEmptyString() {

        DataManager dm = new DataManager(new WebClient("localhost", 3001) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"login\":\"login1\",\"password\":\"password1\",\"name\":\"new org\",\"description\":\"\",\"funds\":[],\"__v\":0}}";

            }

        });

        dm.createOrg("login1", "password1", "new org", "");

    }

    @Test(expected = RuntimeException.class)
    public void testExceptionInCreation() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                throw new RuntimeException("Some unexpected error");
            }
        });

        dm.createOrg("login1", "password1", "new org", "this is the new org");
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
        dm.createOrg("login1", "password1", "new org", "this is the new org");
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
        dm.createOrg("login1", "password1", "new org", "this is the new org");
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
        dm.createOrg("login1", "password1", "new org", "this is the new org");
    }

    // webClient is null
    @Test(expected = IllegalStateException.class)
    public void testNullWebClientCreation() {
        DataManager dm = new DataManager(null);
        dm.createOrg("login1", "password1", "new org", "this is the new org");
    }

}