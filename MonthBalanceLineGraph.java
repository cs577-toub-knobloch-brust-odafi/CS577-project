import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;

public class MonthBalanceLineGraph extends JFrame
{
	
	XYSeriesCollection dataset;
	JFrame lineGraphFrame;
	MonthList monthList;
	MonthList selectedMonths;

	//initialize the pie chart
	public void initializeLineGraph(MonthList allMonths)
	{
		//initialize the JFrame and the dataset
		lineGraphFrame = new JFrame("Line Graph");
		dataset = new XYSeriesCollection();
		monthList = allMonths;
		selectedMonths = new MonthList();
	}
	public void initializeLineGraph()
	{
		//initialize the JFrame and the dataset
		lineGraphFrame = new JFrame("Line Graph");
		dataset = new XYSeriesCollection();
		selectedMonths = new MonthList();
	}
	
	//show the actual line graph	
	public void drawLineGraph()
	{
		//initializes and sizes the GUI frame
		lineGraphFrame = new JFrame("Line Graph");
		JFreeChart chart = ChartFactory.createXYLineChart("Month Balance", "Day", "Balance", dataset, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		
		JPanel verticalLayout = new JPanel();
		verticalLayout.setLayout(new BoxLayout(verticalLayout,BoxLayout.Y_AXIS));
		verticalLayout.add(chartPanel);

		lineGraphFrame.add(verticalLayout);

		lineGraphFrame.pack();
		lineGraphFrame.setTitle("Line Graph");
		
		//lineGraphFrame.setLayout(null);
		
		lineGraphFrame.setSize(750, 550);
		lineGraphFrame.setVisible(true);
	}
	
	//add all of the transactions from a specific month
	public void addMonth(MonthClass month)
	{
		TransactionList transactionList = month.monthTransactions;
		int numTransactions = transactionList.getLength();
		int currentTransaction = 0;
		XYSeries newMonth = new XYSeries(month.getMonth()+"/"+month.getYear());
		if(Arrays.asList(Constants.longMonths).contains(month.getMonth()))
		{
			for(String day : Constants.longMonthDays)
			{
				newMonth.add(Integer.parseInt(day), 0);
			}
		}
		else if(Arrays.asList(Constants.mediumMonths).contains(month.getMonth()))
		{
			for(String day : Constants.mediumMonthDays)
			{
				newMonth.add(Integer.parseInt(day), 0);
			}
		}
		else if(Arrays.asList(Constants.shortMonths).contains(month.getMonth()) && 
				Constants.isLeapYear(Integer.parseInt(month.getYear())))
		{
			for(String day : Constants.leapYearDays)
			{
				newMonth.add(Integer.parseInt(day), 0);
			}
		}
		else
		{
			for(String day : Constants.shortMonthDays)
			{
				newMonth.add(Integer.parseInt(day), 0);
			}
		}
		while(numTransactions>currentTransaction)
		{
			TransactionObject transaction = transactionList.getTarget(currentTransaction);
			int transactionDate = transaction.getDay();
			float transactionValue = transaction.getDollarValue();
			int index = newMonth.indexOf(transactionDate);
			double previousValue = (double) newMonth.getY(index);
			newMonth.update((Number)transactionDate, previousValue+transactionValue);
			updateLaterDays(newMonth, transactionDate, transactionValue);
			currentTransaction++;
		}
		dataset.addSeries(newMonth);
		selectedMonths.addMonthNode(month);
	}
	
	//update the future days so that we can keep an accurate record of how much was earned or spent in the month
	private void updateLaterDays(XYSeries series, int transactionDate, float newValue) 
	{
		if(transactionDate<series.getItemCount())
		{
			double dayValue = (double) series.getY(series.indexOf(transactionDate+1));
			series.update((Number)(transactionDate+1), newValue+dayValue);
			updateLaterDays(series, transactionDate+1, newValue);
		}
	}

	//remove all of the transactions from a specific month
	public void removeMonth(MonthClass month)
	{
		XYSeries oldMonthSeries = dataset.getSeries(month.getMonth()+month.getYear());
		dataset.removeSeries(oldMonthSeries);
		//selectedMonths. want to be able to remove the selected month from the MonthList
	}
}
