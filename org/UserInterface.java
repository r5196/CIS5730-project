import java.text.NumberFormat;
import java.util.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
	
	
	private DataManager dataManager;
	private Organization org;
	private Scanner in = new Scanner(System.in);
	
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

			System.out.println("Enter 0 to create a new fund:");

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

			if (option == 0) {
				createFund();
			} else if (option >= 1 && option <= org.getFunds().size()) {
				displayFund(option);
			} else {
				System.out.println("Invalid fund number. Please enter a valid fund number or 0 to create a new fund:");
			}

		}				
			
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
				isValidTarget = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid target amount: ");
			}
		}

		Fund fund = dataManager.createFund(org.getId(), name, description, target);
		org.getFunds().add(fund);

		
	}
	
	public void displayFund(int fundNumber) {
		
		Map<String, ArrayList<Integer>> donationMap = new HashMap<>();
		Map<Donation, Integer> donationMapTimes = new HashMap<>();
		PriorityQueue<Map.Entry<String, ArrayList<Integer>>> pq = new PriorityQueue<>((a, b) ->
		b.getValue().get(1) - a.getValue().get(1));
		Fund fund = org.getFunds().get(fundNumber - 1);
		
		
		System.out.println("\n\n");
		System.out.println("Here is information about this fund:");
		System.out.println("Name: " + fund.getName());
		System.out.println("Description: " + fund.getDescription());
		System.out.println("Target: $" + fund.getTarget());
		
		List<Donation> donations = fund.getDonations();
		System.out.println("Number of donations: " + donations.size());
		long totalDonation = 0;
		
		System.out.println("Press 1 for Individual Donation Displays, 2 for Aggregate Donation Display");
		int choice = Integer.parseInt(in.nextLine());
		
		if(choice  == 1) {
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
	   }else if(choice  == 2) {
		  
		  for (Donation donation : donations) {
				
				if(donation == null) {
					continue;
				}
				
				if(!donationMap.containsKey(donation.getContributorName())) {
					ArrayList<Integer> detail = new ArrayList<>();
					detail.add(1);
					detail.add((int)donation.getAmount());
					
					donationMap.put(donation.getContributorName(), detail);
					
				}else {
					int times = donationMap.get(donation.getContributorName()).get(0);
					int amount = donationMap.get(donation.getContributorName()).get(1);
					donationMap.get(donation.getContributorName()).set(0,times = times + 1);
					donationMap.get(donation.getContributorName()).set(1, amount + (int)donation.getAmount());
				
				}
				
				
			}
		  
		  
			for(Map.Entry<String, ArrayList<Integer>> donationSet : donationMap.entrySet()) {
				pq.add(donationSet);
//				System.out.println(donationSet.getKey());
//				System.out.println(donationSet.getValue().get(0));
//				System.out.println(donationSet.getValue().get(1));
//				
			}
			
			
			while(!pq.isEmpty()) {
				Map.Entry<String, ArrayList<Integer>> donation  = pq.poll();
				String Contributor = donation.getKey();
				long times = donation.getValue().get(0);
				long totalAmount = donation.getValue().get(1);
				System.out.println(Contributor + ", " + times + " donations, " + "$" + totalAmount + " total");
//				System.out.println(pq.poll().getKey());
//				System.out.println(pq.poll().getValue().get(0));
//				System.out.println(pq.poll().getValue().get(1));
			}

		}
		
		
		
		
		System.out.println("Press the Enter key to go back to the listing of funds");
		in.nextLine();
		
		
		
	}
	
	
	public static void main(String[] args) {
		
		DataManager ds = new DataManager(new WebClient("localhost", 3001));
		
		String login = args[0];
		String password = args[1];

		try {
		    Organization org = ds.attemptLogin(login, password);
		    if (org == null) {
			System.out.println("Login failed.");
		    } else {
			UserInterface ui = new UserInterface(ds, org);      
			ui.start();     
		    }
		} catch (IllegalStateException e) {
		    System.out.println("an error occurs in communicating with the server");
		}

		
	}

}
