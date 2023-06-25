import static org.junit.Assert.*;


import java.util.Map;

import org.junit.Test;


public class DataManager_makeDonation_Test {

	
		/*
		 * This is a test class for the DataManager.makeDonation method.
		 * Add more tests here for this method as needed.
		 * 
		 * When writing tests for other methods, be sure to put them into separate
		 * JUnit test classes.
		 */

		@Test
		public void testSuccessfulDonate() {

			DataManager dm = new DataManager(new WebClient("localhost", 3001) {

				@Override
				public String makeRequest(String resource, Map<String, Object> queryParams) {
					return "{\"status\":\"success\"," +
		                    "\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\"," +
		                    "\"donations\":[{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}}";
		        }
		    });


			Donation donation = dm.makeDonation("12345", "777", "50");

			assertNotNull(donation);
			assertEquals(50, donation.getAmount());
			assertEquals("12345", donation.getFundId());
			

		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testInvalidFundInput() {

			DataManager dm = new DataManager(new WebClient("localhost", 3001) {

				@Override
				public String makeRequest(String resource, Map<String, Object> queryParams) {
					return "{\"status\":\"success\"," +
		                    "\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\"," +
		                    "\"donations\":[{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}}";
		        }
		    });


			Donation donation = dm.makeDonation(null, "777", "50");
			

		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testInvalidContributorIdInput() {

			DataManager dm = new DataManager(new WebClient("localhost", 3001) {

				@Override
				public String makeRequest(String resource, Map<String, Object> queryParams) {
					return "{\"status\":\"success\"," +
		                    "\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\"," +
		                    "\"donations\":[{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}}";
		        }
		    });


			Donation donation = dm.makeDonation("12345", null, "50");
			

		}
		@Test(expected = IllegalArgumentException.class)
		public void testInvalidAmountInput1() {

			DataManager dm = new DataManager(new WebClient("localhost", 3001) {

				@Override
				public String makeRequest(String resource, Map<String, Object> queryParams) {
					return "{\"status\":\"success\"," +
		                    "\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\"," +
		                    "\"donations\":[{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}}";
		        }
		    });


			Donation donation = dm.makeDonation("12345", "777", "-1");
			

		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testInvalidAmountInput2() {

			DataManager dm = new DataManager(new WebClient("localhost", 3001) {

				@Override
				public String makeRequest(String resource, Map<String, Object> queryParams) {
					return "{\"status\":\"success\"," +
		                    "\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\"," +
		                    "\"donations\":[{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}}";
		        }
		    });


			Donation donation = dm.makeDonation("12345", "777", null);

			

		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testInvalidAmountInput3() {

			DataManager dm = new DataManager(new WebClient("localhost", 3001) {

				@Override
				public String makeRequest(String resource, Map<String, Object> queryParams) {
					return "{\"status\":\"success\"," +
		                    "\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\"," +
		                    "\"donations\":[{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}}";
		        }
		    });


			Donation donation = dm.makeDonation("12345", "777", "abc");

			

		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testEmptyFund() {

			DataManager dm = new DataManager(new WebClient("localhost", 3001) {

				@Override
				public String makeRequest(String resource, Map<String, Object> queryParams) {
					return "{\"status\":\"success\"," +
		                    "\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\"," +
		                    "\"donations\":[{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}}";
		        }
		    });


			Donation donation = dm.makeDonation("", "777", "50");

			

		}
		@Test(expected = IllegalArgumentException.class)
		public void testEmptyContributor() {

			DataManager dm = new DataManager(new WebClient("localhost", 3001) {

				@Override
				public String makeRequest(String resource, Map<String, Object> queryParams) {
					return "{\"status\":\"success\"," +
		                    "\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\"," +
		                    "\"donations\":[{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}}";
		        }
		    });


			Donation donation = dm.makeDonation("12345", "", "50");

			

		}
		@Test(expected = IllegalArgumentException.class)
		public void testEmptyAmount() {

			DataManager dm = new DataManager(new WebClient("localhost", 3001) {

				@Override
				public String makeRequest(String resource, Map<String, Object> queryParams) {
					return "{\"status\":\"success\"," +
		                    "\"data\":{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,\"org\":\"5678\"," +
		                    "\"donations\":[{\"contributor\":\"777\", \"amount\":3000, \"date\":\"2023-08-04T00:00:00Z\"}]}}";
		        }
		    });


			Donation donation = dm.makeDonation("12345", "777", "");

			

		}
		
		
	    @Test(expected = IllegalStateException.class)
	    public void testInvalidJsonObjectInCreation() {
	        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
	            @Override
	            public String makeRequest(String resource, Map<String, Object> queryParams) {
	                return "json object is invalid";
	            }
	        });

	        Donation donation = dm.makeDonation("12345", "777", "50");
	    }


	    @Test
	    public void testUnsuccessfulDonate() {

	        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
	            
	            @Override
	            public String makeRequest(String resource, Map<String, Object> queryParams) {
	                return "{\"status\":\"unsuccessful\",\"message\":\"Can not make any donate\"}";

	            }
	            
	        });
	        
	        
	        Donation donation = dm.makeDonation("12345", "777", "50");
	        
	        assertNull(donation);
	    }

		@Test(expected = RuntimeException.class)
		public void testExceptionInCreation() {
			DataManager dm = new DataManager(new WebClient("localhost", 3001) {
				@Override
				public String makeRequest(String resource, Map<String, Object> queryParams) {
					throw new RuntimeException("Some unexpected error");
				}
			});

			dm.makeDonation("12345", "777", "50");
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
			dm.makeDonation("12345", "777", "50");
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
			dm.makeDonation("12345", "777", "50");
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
			dm.makeDonation("12345", "777", "50");
		}

		// webClient is null
		@Test(expected = IllegalStateException.class)
		public void testNullWebClientCreation() {
			DataManager dm = new DataManager(null);
			dm.makeDonation("12345", "777", "50");
		}

}
