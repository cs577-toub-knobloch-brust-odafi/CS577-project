//SE577 Project: Monease
//Month Class

import java.util.ListIterator;

public class MonthClass {
	
	private String year;
	private String monthName;
	TransactionList monthTransactions = new TransactionList();
	
	//default null constructor
	public MonthClass() {
		
		year = null;
		monthName = null;
		
	}
	
	//constructor for user entered year
	public MonthClass(String newYear, String newMonthName) {
		
		year = newYear;
		monthName = newMonthName;
		
	}
	
	public String getYear() {
		
		return year;
	}
	
	public String getMonth() {
		
		return monthName;
	}
	
}
