import java.text.NumberFormat;
import java.util.*;

import java.text.NumberFormat;

public class UserInterface {

	private DataManager dataManager;
	private Organization org;
	private Scanner in = new Scanner(System.in);
	private Map<Integer, HashMap<String, ArrayList<Integer>>>cacheMap = new HashMap<>();
	public UserInterface(DataManager dataManager, Organization org) {
		this.dataManager = dataManager;
		this.org = org;
	}
	
	public void start() {
		while (true) {
			System.out.println("\n\n");
			if (org.getFunds().size() > 0) {
				System.out.println("There are " + org.getFunds().size() + " funds in this organization:");

				int count = 1;
				for (Fund f : org.getFunds()) {

					System.out.println(count + ": " + f.getName());

					count++;
				}
				System.out.println("Enter the fund number to see more information.");
			}

			System.out.println("Enter 0 to create a new fund or Enter -1 to logout:");
			System.out.println("Enter -2 to see all the contributors for this organization:");
			// System.out.println("Enter -3 to change the password:");

			int option = 0;
			boolean isValidOption = false;

			while (!isValidOption) {
				try {
					option = Integer.parseInt(in.nextLine());
					isValidOption = true;
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter a valid fund number or 0 to create a new fund:");
				}
			}

			if (option == -1) {
				System.out.println("You have logged out.");
				break;
			} else if (option == 0) {
				createFund();
			} else if (option >= 1 && option <= org.getFunds().size()) {
				displayFund(option);
			} else if(option == -2) {
				allContributors();
			} else {
				System.out.println("Invalid fund number. Please enter a valid fund number or 0 to create a new fund:");
			}
		}
	}

	/**
	 * This is implemented in phase 3
	 * This method is used to change the password of the organization
	 * @param password
	 * @return true if the password is changed successfully, false otherwise
	 */
	public boolean changePassword(String password) {
		System.out.println("Enter the current password:");
		String currentPassword = in.nextLine().trim();
		if (!currentPassword.equals(password)) {
			System.out.println("The current password is incorrect.");
			return false;
		}
		System.out.println("Enter the new password first time:");
		String newPassword1 = in.nextLine();
		System.out.println("Enter the new password second time:");
		String newPassword2 = in.nextLine();
		if(!newPassword1.equals(newPassword2)) {
			System.out.println("The two new passwords are not the same. Please try again.");
			return false;
		}
		try {
			boolean r = dataManager.updateOrg(org.getId(), newPassword1);
			if (r) {
				System.out.println("The password has been changed successfully.");
				return true;
			} else {
				System.out.println("The password has not been changed.");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	public void createFund() {
		
		System.out.print("Enter the fund name: ");
		String name = in.nextLine().trim();
		while (name.isEmpty()) {
			System.out.println("Invalid name. Please enter a name that is non-empty: ");
			name = in.nextLine().trim();
		}
		
		System.out.print("Enter the fund description: ");
		String description = in.nextLine().trim();
		while (description.isEmpty()) {
			System.out.println("Invalid description. Please enter a description that is non-empty: ");
			description = in.nextLine().trim();
		}
		
		long target = 0;
		boolean isValidTarget = false;
		
		while (!isValidTarget) {
			System.out.print("Enter the fund target: ");
			try {
				target = Long.parseLong(in.nextLine());
				if (target <= 0) {
					System.out.println("Invalid target. Please enter a target that is greater than 0: ");
					continue;
				}
				isValidTarget = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid target amount: ");
			}
		}

		try {
			Fund fund = dataManager.createFund(org.getId(), name, description, target);
			org.getFunds().add(fund);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			if (retryOperation("createFund")) {
				createFund();
			}
		}
	}
	
	public void displayFund(int fundNumber) {
		Fund fund = org.getFunds().get(fundNumber - 1);


		System.out.println("\n\n");
		System.out.println("Here is information about this fund:");
		System.out.println("Name: " + fund.getName());
		System.out.println("Description: " + fund.getDescription());
		System.out.println("Target: $" + fund.getTarget());

		List<Donation> donations = fund.getDonations();
		System.out.println("Number of donations: " + donations.size());
		long totalDonation = 0;

		System.out.println("Press 1 for showing individual donation(s), 2 for showing donations aggregated by Contributor, 3 for deleting this fund.");
		int choice = Integer.parseInt(in.nextLine());

		if (choice  == 1) {
			for (Donation donation : donations) {
				if(donation == null) {
					continue;
				}
				System.out.println("* " + donation.getContributorName() + ": $" + donation.getAmount() + " on " + donation.getDate());
				totalDonation += donation.getAmount();
				long target = fund.getTarget();
				double number = (double) totalDonation / target;

				NumberFormat percent = NumberFormat.getPercentInstance();//import header
				percent.setMinimumFractionDigits(0); //
				String percentage = percent.format(number);
				System.out.print("Total donation amount: $"+totalDonation+"(" + percentage + " of target)." + "\r\n");
				totalDonation = 0;
			}
		}

		if (choice == 2) {
			Map<String, ArrayList<Integer>> donationMap = new HashMap<>();
			PriorityQueue<Map.Entry<String, ArrayList<Integer>>> pq = new PriorityQueue<>((a, b) ->
					b.getValue().get(1) - a.getValue().get(1));

			if (cacheMap.containsKey(fundNumber)) {
				for(Map.Entry<String, ArrayList<Integer>> donationSet : cacheMap.get(fundNumber).entrySet()) {
					pq.add(donationSet);
				}
				while(!pq.isEmpty()) {
					Map.Entry<String, ArrayList<Integer>> donation  = pq.poll();
					String Contributor = donation.getKey();
					long times = donation.getValue().get(0);
					long totalAmount = donation.getValue().get(1);
					System.out.println(Contributor + ", " + times + " donations, " + "$" + totalAmount + " total");
				}
			} else {
				for (Donation donation : donations) {
					if(donation == null) {
						continue;
					}
					if (!donationMap.containsKey(donation.getContributorName())) {
						ArrayList<Integer> detail = new ArrayList<>();
						detail.add(1);
						detail.add((int)donation.getAmount());
						donationMap.put(donation.getContributorName(), detail);
					} else {
						int times = donationMap.get(donation.getContributorName()).get(0);
						int amount = donationMap.get(donation.getContributorName()).get(1);
						donationMap.get(donation.getContributorName()).set(0,times = times + 1);
						donationMap.get(donation.getContributorName()).set(1, amount + (int)donation.getAmount());
					}
				}
				cacheMap.put(fundNumber, (HashMap<String, ArrayList<Integer>>) donationMap);
				for(Map.Entry<String, ArrayList<Integer>> donationSet : donationMap.entrySet()) {
					pq.add(donationSet);
				}
				while(!pq.isEmpty()) {
					Map.Entry<String, ArrayList<Integer>> donation  = pq.poll();
					String Contributor = donation.getKey();
					long times = donation.getValue().get(0);
					long totalAmount = donation.getValue().get(1);
					System.out.println(Contributor + ", " + times + " donations, " + "$" + totalAmount + " total");
				}
			}
		}

		if (choice == 3) {
			System.out.println("You will delete the fund : " + fund.getName() + "\".");
			System.out.println("Enter \"I CONFIRM\" in the exact format in order to proceed (without quotation marks) or Enter anything else to abort.");
			String res = in.nextLine();
			if (res.equals("I CONFIRM")) {
				String fundId;
				try {
					fundId = dataManager.deleteFund(fund.getId());
					if (fundId == null) {
						System.out.println("Deletion Failed! We cannot retrieve the fund ID.");
					} else {
						System.out.println("Deletion Succeed!");
						org.getFunds().remove(fundNumber - 1);
					}
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
					if (retryOperation("deleteFund")) {
						displayFund(fundNumber);
					}
				}
			} else {
				System.out.println("You have aborted the deletion request.");
				System.out.println("If you indeed intend to delete this fund, please try again by entering \"I CONFIRM\".");
			}
		}

		System.out.println("Press the Enter key to go back to the listing of funds");
		in.nextLine();
	}
	
    public void allContributors() {
		Map<String, ArrayList> contributorMap;
		PriorityQueue<Map.Entry<String, ArrayList>> pq = new PriorityQueue<>(new EntryComparator());
		for (Fund f : org.getFunds()) {
			List<Donation> getDonations = f.getDonations();
			String fundName = f.getName();
			contributorMap = new HashMap<>();
			int i = 2;
			for(Donation d : getDonations) {
				
				if(!contributorMap.containsKey(d.getContributorName())) {
					contributorMap.put(d.getContributorName(), new ArrayList<>(Arrays.asList(fundName,
							d.getAmount(),d.getDate(), d.totalday())));
					
				}else {
					String key  = d.getContributorName() + " "+ i + "th time";
					contributorMap.put(key, new ArrayList<>(Arrays.asList(fundName,
							d.getAmount(),d.getDate(), d.totalday())));
					i = i + 1;
				}
				
			
			}

			pq.addAll(contributorMap.entrySet());
		}
		
		while (!pq.isEmpty()) {
            Map.Entry<String, ArrayList> entry = pq.poll();
            System.out.println("Contributor: "+entry.getKey() 
            + " donated " + "$" + entry.getValue().get(1)+ " on " + entry.getValue().get(2)+ " for Fund "
            +entry.getValue().get(0));
        }

		System.out.println("Press the Enter key to go back to the listing of funds");
		in.nextLine();
	}
	
	static class EntryComparator implements Comparator<Map.Entry<String, ArrayList>> {
        @Override
        public int compare(Map.Entry<String, ArrayList> entry1, Map.Entry<String, ArrayList> entry2) {
            int totalDays1 = (int) entry1.getValue().get(3);
            int totalDays2 = (int) entry2.getValue().get(3);
            if (totalDays1 != totalDays2) {
                return totalDays2 - totalDays1;
            } else {
                return entry1.getKey().compareTo(entry2.getKey());
            }
        }
    }

	/**
	 * helper method trying to avoid duplicate code
	 * @param operation the operation that is being performed
	 */
	private boolean retryOperation(String operation) {
		System.out.println("Do you want to retry the operation of " + operation + "? (Yes/No)");
		String answer = in.nextLine().trim().toLowerCase();
		return answer.equals("yes");
	}


	public static void main(String[] args) {
		
		DataManager ds = new DataManager(new WebClient("localhost", 3001));
		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.print("Enter 1 to login or Enter 2 to create a new organization: or Enter 'exit' to quit: ");
			String choice = in.nextLine().trim();

			if (choice.equals("1")) {
				while (true) {
					System.out.print("Enter your login:");
					String login = in.nextLine().trim();
					System.out.print("Enter your password: ");
					String password = in.nextLine().trim();

					Organization org = null;
					try {
						org = ds.attemptLogin(login, password);
						if (org == null) {
							System.out.println("Login failed. Username/Password combination is incorrect");
							System.out.println("Do you want to retry the operation of login? (Yes/No)");
							String answer = in.nextLine().trim().toLowerCase();
							if (!answer.equals("yes")) {
								break;
							}
						} else {
							UserInterface ui = new UserInterface(ds, org);
							System.out.println("You have logged in.");
							while (true) {
								System.out.println("Do you want to change your password? (Yes/No)");
								String answer = in.nextLine().trim().toLowerCase();
								if (answer.equals("yes")) {
									if (ui.changePassword(password)) {
										break;
									}
								} else {
									break;
								}
							}
							ui.start();
						}
					} catch (Exception e) {
						if (e instanceof IllegalArgumentException) {
							System.out.println("Invalid Argument");
						} else if (e instanceof IllegalStateException) {
							System.out.println("an error occurs in communicating with the server");
						}
						System.out.println("Error: " + e.getMessage());
						System.out.println("Do you want to retry the operation of login? (Yes/No)");
						String answer = in.nextLine().trim().toLowerCase();
						if (!answer.equals("yes")) {
							break;
						}
					}
				}
			}

			if (choice.equals("2")) {
				while (true) {
					System.out.print("Enter the login of the organization: ");
					String newLogin = in.nextLine().trim();
					System.out.print("Enter the password of the organization: ");
					String newPassword = in.nextLine().trim();
					System.out.print("Enter the name of the organization: ");
					String newName = in.nextLine().trim();
					System.out.print("Enter the description of the organization: ");
					String newDescription = in.nextLine().trim();

					Organization org = null;
					try {
						org = ds.createOrg(newLogin, newPassword, newName, newDescription);
						System.out.println("Organization created successfully");

						if (org == null) {
							System.out.println("Organization creation failed.");
						} else {
							UserInterface ui = new UserInterface(ds, org);
							while (true) {
								System.out.println("You have logged in.");
								System.out.println("Do you want to change your password? (Yes/No)");
								String answer = in.nextLine().trim().toLowerCase();
								if (answer.equals("yes")) {
									if (ui.changePassword(newPassword)) {
										break;
									}
								} else {
									break;
								}
							}
							ui.start();
						}
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
						System.out.println("Do you want to retry the operation of creating a new organization? (Yes/No)");
						String answer = in.nextLine().trim().toLowerCase();
						if (!answer.equals("yes")) {
							break;
						}
					}
				}
			}

			if (choice.equals("exit")) {
				break;
			}

			if (!choice.equals("1") && !choice.equals("2") && !choice.equals("exit")) {
				System.out.println("Invalid choice. Please try again.");
			}
		}
		in.close();
	}



}
