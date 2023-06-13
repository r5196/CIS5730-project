import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

public class Donation {
	
	private String fundId;
	private String contributorName;
	private long amount;
	private String date;
	
	
	public Donation(String fundId, String contributorName, long amount, String date) {
		this.fundId = fundId;
		this.contributorName = contributorName;
		this.amount = amount;
		this.date = date;
	}

	public String getFundId() {
		return fundId;
	}

	public String getContributorName() {
		return contributorName;
	}

	public long getAmount() {
		return amount;
	}
	
	/**
	 * For Prject1 1.9
	 * 
	 */
	//“2021-06-18T04:21:04.807Z”.  ->  “June 18, 2021” 
	public String getDate() {
		String year = date.substring(0,4);
		String day = date.substring(8,10);
		String month = dataTransfer(date.substring(5,7));
		String newDate = month + " " + day + ", " + year;
		return newDate;
	}
	
	public int totalday() {
		String years = date.substring(0,4);
		String days = date.substring(8,10);
		String months = date.substring(5,7);
		int year = Integer.valueOf(years);
		int month = Integer.valueOf(months);
		int day = Integer.valueOf(days);
		int totalday = 0;
		if(month == 1||month == 3|| month == 5 || month == 7 ||
		           month == 8 || month == 10 || month == 12) {
			totalday = year*365 + month * 31 + day;
		}else if(month == 2 && year % 4 == 0) {
			totalday = year*365 + month * 28 + day;
			
		}else if(month == 2 && year % 4 != 0) {
			totalday = year*365 + month * 29 + day;
		}else {
			totalday = year*365 + month * 30 + day;
		}
		return totalday;
	}
	
	public String dataTransfer(String month){
		Map<String, String> monthMap = new HashMap<>();
		monthMap.put("01", "January");
		monthMap.put("02", "February");
		monthMap.put("03", "March");
		monthMap.put("04", "April");
		monthMap.put("05", "May");
		monthMap.put("06", "June");
		monthMap.put("07", "July");
		monthMap.put("08", "August");
		monthMap.put("09", "September");
		monthMap.put("10", "October");
		monthMap.put("11", "November");
		monthMap.put("12", "December");
		
		return monthMap.get(month);
		
	}
	
	

}
