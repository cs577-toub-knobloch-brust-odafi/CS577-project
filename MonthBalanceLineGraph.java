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
	
	String[] longMonths = {"January", "March", "May", "July", "August", "October", "December"};
	String[] mediumMonths = {"April", "June", "September", "November"};
	String[] shortMonths = {"February"};

	//initialize the pie chart
	public void initializeLineGraph(MonthList months)
	{
		//initialize the JFrame and the dataset
		lineGraphFrame = new JFrame("Line Graph");
		dataset = new XYSeriesCollection();
		monthList = months;
	}
	
	//show the actual line graph	
	public void drawLineGraph()
	{
		//initializes and sizes the GUI frame
		lineGraphFrame = new JFrame("Line Graph");
		JFreeChart chart = ChartFactory.createXYLineChart("Month Balance", "Day", "Balance", dataset, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		
		JButton addMonthButton = new JButton("Add Month to Graph");
		JButton removeMonthButton = new JButton("Remove Month from Graph");
		
		JPanel buttonGroup = new JPanel();
		buttonGroup.setLayout(new BoxLayout(buttonGroup,BoxLayout.X_AXIS));
		buttonGroup.add(addMonthButton);
		buttonGroup.add(removeMonthButton);
		
		JPanel verticalLayout = new JPanel();
		verticalLayout.setLayout(new BoxLayout(verticalLayout,BoxLayout.Y_AXIS));
		verticalLayout.add(chartPanel);
		verticalLayout.add(buttonGroup);

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
		XYSeries newMonth = new XYSeries(month.getMonth()+month.getYear());
		if(Arrays.asList(longMonths).contains(month.getMonth()))
		{
			int day = 1;
			while(day<=31)
			{
				newMonth.add(day, 0);
			}
		}
		else if(Arrays.asList(mediumMonths).contains(month.getMonth()))
		{
			int day = 1;
			while(day<=30)
			{
				newMonth.add(day, 0);
			}
		}
		else if(Arrays.asList(shortMonths).contains(month.getMonth()))
		{
			int day = 1;
			while(day<=28)
			{
				newMonth.add(day, 0);
			}
		}
		while(numTransactions>currentTransaction)
		{
			TransactionObject transaction = transactionList.getTarget(currentTransaction);
			int transactionDate = transaction.getDay();
			float transactionValue = transaction.getDollarValue();
			int index = newMonth.indexOf(transactionDate);
			float previousValue = (float) newMonth.getY(index);
			newMonth.update((Number)transactionDate, previousValue+transactionValue);
			updateLaterDays(newMonth, transactionDate);
			currentTransaction++;
		}
		dataset.addSeries(newMonth);
		
	}
	
	//update the future days so that we can keep an accurate record of how much was earned or spent in the month
	private void updateLaterDays(XYSeries series, int transactionDate) 
	{
		if(transactionDate<series.getItemCount())
		{
			float transactionValue = (float) series.getY(series.indexOf(transactionDate));
			float nextDayValue = (float) series.getY(series.indexOf(transactionDate+1));
			series.update((Number)(transactionDate+1), transactionValue+nextDayValue);
			updateLaterDays(series, transactionDate+1);
		}
	}

	//remove all of the transactions from a specific month
	public void removeMonth(MonthClass month)
	{
		XYSeries oldMonthSeries = dataset.getSeries(month.getMonth()+month.getYear());
		dataset.removeSeries(oldMonthSeries);
	}
}
