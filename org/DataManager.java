

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataManager {

	private final WebClient client;

	public DataManager(WebClient client) {
		this.client = client;
	}

	/**
	 * Attempt to log the user into an Organization account using the login and password.
	 * This method uses the /findOrgByLoginAndPassword endpoint in the API
	 * @return an Organization object if successful; null if unsuccessful
	 */
	public Organization attemptLogin(String login, String password) {
		if (login == null || password == null) {
			throw new IllegalArgumentException("login and password cannot be null");
		}

		try {
			Map<String, Object> map = new HashMap<>();
			map.put("login", login);
			map.put("password", password);
			String response = client.makeRequest("/findOrgByLoginAndPassword", map);

	        if (response == null) {
	            throw new IllegalStateException("an error occurs in communicating with the server because webClient returns null");
	        }

			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(response);
			String status = (String)json.get("status");

			if (status.equals("success")) {
				JSONObject data = (JSONObject)json.get("data");
				String fundId = (String)data.get("_id");
				String name = (String)data.get("name");
				String description = (String)data.get("description");
				Organization org = new Organization(fundId, name, description);

				JSONArray funds = (JSONArray)data.get("funds");
				Iterator it = funds.iterator();
				while(it.hasNext()){
					JSONObject fund = (JSONObject) it.next(); 
					fundId = (String)fund.get("_id");
					name = (String)fund.get("name");
					description = (String)fund.get("description");
					long target = (Long)fund.get("target");

					Fund newFund = new Fund(fundId, name, description, target);

					JSONArray donations = (JSONArray)fund.get("donations");
					List<Donation> donationList = new LinkedList<>();
					Iterator it2 = donations.iterator();
					while(it2.hasNext()){
						JSONObject donation = (JSONObject) it2.next();
						String contributorId = (String)donation.get("contributor");
						String contributorName = this.getContributorName(contributorId);
						long amount = (Long)donation.get("amount");
						String date = (String)donation.get("date");
						donationList.add(new Donation(fundId, contributorName, amount, date));
					}

					newFund.setDonations(donationList);

					org.addFund(newFund);

				}

				return org;
			} else if (status.equals("error")) {
				throw new IllegalStateException("an error occurs in communicating with the server because webClient returns error");
			}
			else return null;
		}
		catch (Exception e) {
		    throw new IllegalStateException("an error occurs in communicating with the server", e);
			// return null;
		}
	}

	/**
	 * Look up the name of the contributor with the specified ID.
	 * This method uses the /findContributorNameById endpoint in the API.
	 * @return the name of the contributor on success; null if no contributor is found
	 */
	public String getContributorName(String id) {
		Map<String, Object> map = new HashMap<>();
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		map.put("id", id);

		if (client == null) {
			throw new IllegalStateException("webClient is null");
		}
		String response = client.makeRequest("/findContributorNameById", map);
		if (response == null) {
			throw new IllegalStateException("webClient returns null");
		}

		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(response);
		} catch (Exception e) {
			throw new IllegalStateException("WebClient returns malformed JSON");
		}
		String status = (String) json.get("status");
		if (status == null) {
			throw new IllegalStateException("webClient returns missing status");
		}

		if (status.equals("success")) {
			// String name = (String) json.get("data");
			String name = json.get("data").toString();
			return name;
		} else if (status.equals("error")) {
			throw new IllegalStateException("webClient returns error");
		} else return null;
	}

	/**
	 * This method creates a new fund in the database using the /createFund endpoint in the API
	 * @return a new Fund object if successful; null if unsuccessful
	 */
	public Fund createFund(String orgId, String name, String description, long target) {
        if (orgId == null || orgId.isEmpty()) {
            // System.out.println("Organization ID is invalid.");
			throw new IllegalArgumentException("Organization ID is invalid.");
            // return null;
        }
        if (name == null || name.isEmpty()) {
            // System.out.println("Fund name is invalid.");
			throw new IllegalArgumentException("Fund name is invalid.");
            // return null;
        }
        if (description == null || description.isEmpty()) {
            // System.out.println("Fund description is invalid.");
			throw new IllegalArgumentException("Fund description is invalid.");
            // return null;
        }
        if (target < 0) {
            // System.out.println("Target amount is invalid. It should be a non-negative number.");
			throw new IllegalArgumentException("Target amount is invalid. It should be a non-negative number.");
            // return null;
        }

		Map<String, Object> map = new HashMap<>();
		map.put("orgId", orgId);
		map.put("name", name);
		map.put("description", description);
		map.put("target", target);
		if (client == null) {
			throw new IllegalStateException("webClient is null");
		}
		String response = client.makeRequest("/createFund", map);
		if (response == null) {
			throw new IllegalStateException("webClient returns null");
		}

		JSONParser parser = new JSONParser();
		JSONObject json;
		try {
			json = (JSONObject) parser.parse(response);
		} catch (Exception e) {
			throw new IllegalStateException("WebClient returns malformed JSON");
		}
		String status = (String) json.get("status");
		if (status == null) {
			throw new IllegalStateException("webClient returns missing status");
		}

		if (status.equals("success")) {
			JSONObject fund = (JSONObject) json.get("data");
			String fundId = (String) fund.get("_id");
			return new Fund(fundId, name, description, target);
		} else if (status.equals("error")) {
			throw new IllegalStateException("webClient returns error");
		} else return null;
	}


}
