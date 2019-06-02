public final class Constants
{
	static String[] longMonths = {"01", "03", "05", "07", "08", "10", "12"};
	static String[] mediumMonths = {"04", "06", "09", "11"};
	static String[] shortMonths = {"02"};
	static String[] allowableMonths = {"01", "02", "03", "04", "05", "06", 
			"07", "08", "09", "10", "11", "12"};
	static String[] longMonthDays = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", 
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
	static String[] mediumMonthDays = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", 
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
	static String[] shortMonthDays = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", 
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"21", "22", "23", "24", "25", "26", "27", "28"};
	static String[] leapYearDays = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", 
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"21", "22", "23", "24", "25", "26", "27", "28", "29"};
	
	public static boolean isLeapYear(int year)
	{
		if(year % 4 == 0)
		{
			if(year % 100 == 0)
			{
				if(year % 400 == 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isInteger(String str)
	{
		try 
		{
			int strAsInt = Integer.parseInt(str);
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
}
