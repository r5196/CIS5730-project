import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;


public class DataManager_deleteFund_Test {
	
	/*
	 * This is a test class for the DataManager.deleteFund method.
	 */

	@Test
	public void testSuccessfulDeletion() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
		    
		    @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";

            }
        });
		
		
		String returnId = dm.deleteFund("12345");
        
        assertNotNull(returnId);
        assertEquals("12345", returnId);
		
	}
	
	@Test
    public void testUnsuccessfulDeletion() {
	    
        DataManager dm = new DataManager(new WebClient("localhost", 3000) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"failure\",\"data\":{}}";
            }
        });

        String returnId = dm.deleteFund("12345");
        assertNull(returnId);
    }
	
	@Test
	public void testDeleteFund_WebClientIsNull() {
	    DataManager dm = new DataManager(null);
	    try {
	        dm.deleteFund("12345");
	        // If no exception is thrown, fail the test
	        fail("DataManager.deleteFund does not throw IllegalStateException when WebClient is null");
	    } catch (IllegalStateException e) {
	    } 
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteFund_FundIdIsNull() {
	    DataManager dm = new DataManager(new WebClient("localhost", 3001));
		dm.deleteFund(null);
		fail("DataManager.deleteFund does not throw IllegalArgumentException when fundId is null");
	}
	
	@Test
	public void testDeleteFund_WebClientFailToConnectServer() {
	    DataManager dm = new DataManager(new WebClient("localhost", 4000));
	    try {
	        dm.deleteFund("12345");
	        fail("DataManager.deleteFund does not throw IllegalStateException when WebClient cannot connect to server");
	    } catch (IllegalStateException e) {
	    }
	}

	@Test
	public void testDeleteFund_NullWebClient() {
	    DataManager dm = new DataManager(new WebClient("localhost", 3001) {
	        @Override
	        public String makeRequest(String resource, Map<String, Object> queryParams) {
	            return null;
	        }
	    });

	    try {
	        dm.deleteFund("12345");
	        fail("DataManager.deleteFund does not throw IllegalStateException when WebClient returns null");
	    } catch (IllegalStateException e) {
	    }
	}
	
	@Test
    public void testDeleteFund_WebClientStatusError() {
	    DataManager dm = new DataManager(new WebClient("localhost", 3001) {
	        @Override
	        public String makeRequest(String resource, Map<String, Object> queryParams) {
	            return "{\"status\":\"error\",\"error\":\"An unexpected database error occurred\"}";
	        }
	    });

	    try {
	        dm.deleteFund("12345");
	        fail("DataManager.deleteFund does not throw IllegalStateException when WebClient returns error");
	    } catch (IllegalStateException e) {

	    }
	}
	
	@Test
    public void testDeleteFund_WebClientReturnsWrongFormat() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "Wrong Format";
            }
        });

        try {
            dm.deleteFund("12345");
            fail("DataManager.deleteFund does not throw IllegalStateException when WebClient returns wrong format");
        } catch (IllegalStateException e) {

        }
    }

	@Test(expected = IllegalStateException.class)
	public void testDeleteFund_WebClientReturnsMissingStatus() {
		DataManager dm = new DataManager(new WebClient("localhost", 3001) {
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\",\"donations\":[],\"__v\":0}}";
			}
		});
		dm.deleteFund("12345");
		fail("DataManager.deleteFund does not throw IllegalStateException when WebClient returns missing status");
	}

}
