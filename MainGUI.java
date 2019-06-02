import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Locale;

public class MainGUI extends JPanel{
	
	int currentMonthSelect = 0;
	
	public MainGUI() {
		
		//LINKED LIST
		MonthList monthsInList = new MonthList();
		
		//MAIN MENU COMPONENTS (these are available upon initialization)
		//------------------------------------------------------------------------------
		//adds the buttons on the GUI
		JButton addMonthButton = new JButton("Add Month");
		addMonthButton.setBounds(10,10,130,40);
		
		JButton saveMonthsButton = new JButton("Save Data");
		saveMonthsButton.setBounds(10,60,130,40);
		
		
		//ADD MONTH COMPONENTS (these are only visible once the add button is pressed)
		//==================================================================================
		//label for enter year
		JLabel enterYear = new JLabel();
		enterYear.setBounds(10,10,150,40);
		enterYear.setText("Enter the year: ");
		
		//label for enter month
		JLabel enterMonth = new JLabel();
		enterMonth.setBounds(10,70,150,40);
		enterMonth.setText("Select the month: ");
		
		//text field for user year input
		JTextField yearText = new JTextField();
		yearText.setBounds(10,50,150,20);
		
		//text field for user month input
		JComboBox monthComboBox = new JComboBox(Constants.allowableMonths);
		monthComboBox.setBounds(10, 110, 150, 20);
		
		//button for creating a new month and adding to lists
		JButton createMonth = new JButton("Create");
		createMonth.setBounds(10,170,130,40);
		
		//button for loading a month from a file
		JButton loadMonth = new JButton("Load");
		loadMonth.setBounds(10,220,130,40);
		
		JButton backFromAddMonth = new JButton("Back");
		backFromAddMonth.setBounds(10,270,130,40);
		//===================================================================================
		
		//TRANSACTION COMPONENTS
		//===================================================================================
		//button for adding a transaction
		JButton addTransaction = new JButton("Add Transaction");
		addTransaction.setBounds(10,10,150,40);
		
		//Button for saving a month and its transactions to file
		JButton saveTransButton = new JButton("Save Transactions");
		saveTransButton.setBounds(10,60,150,40);
		
		//Button for analyzing a month's list of transactions by category
		JButton categoryTransAnalysisButton = new JButton("Category Analysis");
		categoryTransAnalysisButton.setBounds(10,110,150,40);
		
		//Button for analyzing a month's list of transactions by day
		JButton dayTransAnalysisButton = new JButton("Chage Over Time");
		dayTransAnalysisButton.setBounds(10,160,150,40);
		
		//goes back to the month list
		JButton backToMonthsButton = new JButton("Back");
		backToMonthsButton.setBounds(10,260,150,40);
		
		//reads transactions into a month's list from a statement
		JButton readFromStatement = new JButton("Read Statement");
		readFromStatement.setBounds(10,210,150,40);
		
		//label for day text field
		JLabel transDayLabel = new JLabel();
		transDayLabel.setBounds(10,10,150,40);
		transDayLabel.setText("Transaction Day: ");
		
		//label for value text field
		JLabel transValueLabel = new JLabel();
		transValueLabel.setBounds(10,50,150,40);
		transValueLabel.setText("Dollar Amount: ");
		
		//label for input type text field
		JLabel transInTypeLabel = new JLabel();
		transInTypeLabel.setBounds(10,90,150,40);
		transInTypeLabel.setText("Transaction Type: ");
		
		//field for user input of input type
		JTextField transInField = new JTextField();
		transInField.setBounds(10,120,150,20);
		
		//field for user input of dollar value
		JTextField transValField = new JTextField();
		transValField.setBounds(10,80,150,20);
		
		//field for user input of day
		JComboBox<String> transDayField = new JComboBox<String>(Constants.longMonthDays);
		transDayField.setBounds(10,40,150,20);
		
		//button for creating new transaction object from data collected in above fields
		JButton createTransButton = new JButton("Create");
		createTransButton.setBounds(10,170,150,40);
		
		//button for canceling new transaction creation and returning to the month's menu
		JButton cancelTransButton = new JButton("Cancel");
		cancelTransButton.setBounds(10,220,150,40);
		
		//========================================================================
		
		categoryTransAnalysisButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent m) {
				CategoryPieChart monthCategoryPieChart = new CategoryPieChart();
				monthCategoryPieChart.initializePieChart();
				monthCategoryPieChart.addMonth(monthsInList.getTarget(currentMonthSelect));
				monthCategoryPieChart.drawPieChart();
			}
		});
		
		dayTransAnalysisButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent m) {
				MonthBalanceLineGraph monthLineGraph = new MonthBalanceLineGraph();
				monthLineGraph.initializeLineGraph();
				monthLineGraph.addMonth(monthsInList.getTarget(currentMonthSelect));
				monthLineGraph.drawLineGraph();
			}
		});
		
		
		
		//JList implementation for listing month objects
		DefaultListModel monthListModel = new DefaultListModel();
		JList monthGUIList = new JList(monthListModel);
		
		JScrollPane monthPane = new JScrollPane(monthGUIList);
		monthPane.setBounds(200,10,250,500);
			
			//double click action block on a list item
			monthGUIList.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent m) {
					JList monthGUIList = (JList)m.getSource();
					
					if(m.getClickCount() == 2) {
						
						int i = monthGUIList.locationToIndex(m.getPoint());
						
						//updates the index of the month that is selected
						MainGUI.this.currentMonthSelect = i;
						
						if(i >= 0) {
							
							//hides non-relevant options
							saveMonthsButton.setVisible(false);
							addMonthButton.setVisible(false);
							
							//sets relevant components to be visible
							addTransaction.setVisible(true);
							saveTransButton.setVisible(true);
							categoryTransAnalysisButton.setVisible(true);
							dayTransAnalysisButton.setVisible(true);
							backToMonthsButton.setVisible(true);
							readFromStatement.setVisible(true);
							
							//populates list with transactions within month's list
							populateTransList(monthListModel, monthsInList.getTarget(i).monthTransactions);
						}
					}
				}
			});
			
			//action block for the add transaction button
			addTransaction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					
					//sets non-relevant components to be invisible
					addTransaction.setVisible(false);
					saveTransButton.setVisible(false);
					categoryTransAnalysisButton.setVisible(false);
					dayTransAnalysisButton.setVisible(false);
					backToMonthsButton.setVisible(false);
					readFromStatement.setVisible(false);
					
					//sets relevant components visible
					transDayLabel.setVisible(true);
					transInTypeLabel.setVisible(true);
					transValueLabel.setVisible(true);
					transValField.setVisible(true);
					transInField.setVisible(true);
					transDayField.setVisible(true);
					transDayField.removeAllItems();
					createTransButton.setVisible(true);
					cancelTransButton.setVisible(true);
					
					String currentMonth = monthsInList.getTarget(currentMonthSelect).getMonth();	
					String currentYear = monthsInList.getTarget(currentMonthSelect).getYear();
					if(Arrays.asList(Constants.longMonths).contains(currentMonth))
					{
						for(String item : Constants.longMonthDays)
						{
							transDayField.addItem(item);
						}
					}
					else if(Arrays.asList(Constants.mediumMonths).contains(currentMonth))
					{
						for(String item : Constants.mediumMonthDays)
						{
							transDayField.addItem(item);
						}
					}
					else if(Arrays.asList(Constants.shortMonths).contains(currentMonth) && 
							Constants.isLeapYear(Integer.parseInt(currentYear)))
					{
						for(String item : Constants.leapYearDays)
						{
							transDayField.addItem(item);
						}
					}
					else
					{
						for(String item : Constants.shortMonthDays)
						{
							transDayField.addItem(item);
						}
					}
				}
			});
			
				//action block for creating a new transaction object from user input in text fields
				createTransButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						try
						{
						//collects input from text fields
						int dayInput = Integer.parseInt((String) transDayField.getSelectedItem());
						float valueInput = Float.parseFloat(transValField.getText());
						String typeInput = transInField.getText();
						

						if(dayInput > 0 & valueInput > 0) {
							
							//creates new transaction object and adds it the selected month object's list
							TransactionObject newTransObject = new TransactionObject(valueInput, 1, typeInput, dayInput);
							monthsInList.getTarget(currentMonthSelect).monthTransactions.addTranNode(newTransObject);
							
							//repopulates the gui pane
							populateTransList(monthListModel, monthsInList.getTarget(currentMonthSelect).monthTransactions);
							
							//clears text fields
							transValField.setText("");
							transInField.setText("");
							transDayField.setSelectedIndex(-1);
							
							//sets current display components to be invisible to return to former display
							transDayLabel.setVisible(false);
							transInTypeLabel.setVisible(false);
							transValueLabel.setVisible(false);
							transValField.setVisible(false);
							transInField.setVisible(false);
							transDayField.setVisible(false);
							createTransButton.setVisible(false);
							cancelTransButton.setVisible(false);
							
							//sets former display's components to be visible
							addTransaction.setVisible(true);
							saveTransButton.setVisible(true);
							categoryTransAnalysisButton.setVisible(true);
							dayTransAnalysisButton.setVisible(true);
							backToMonthsButton.setVisible(true);
							readFromStatement.setVisible(true);
						}
						
						else {
							
							//clears text fields
							//DIALOG BOX EXPLAINING THAT INPUT WAS NOT VALID
							transValField.setText("");
							transInField.setText("");
							transDayField.setSelectedIndex(-1);							
						}
						}
						catch(Exception e)
						{
							//clears text fields
							//DIALOG BOX EXPLAINING THAT INPUT WAS NOT VALID
							transValField.setText("");
							transInField.setText("");
							transDayField.setSelectedIndex(-1);
						}
					}
				});
				
				cancelTransButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						//clears text fields
						transValField.setText("");
						transInField.setText("");
						transDayField.setSelectedIndex(-1);
						
						//sets current display components to be invisible to return to former display
						transDayLabel.setVisible(false);
						transInTypeLabel.setVisible(false);
						transValueLabel.setVisible(false);
						transValField.setVisible(false);
						transInField.setVisible(false);
						transDayField.setVisible(false);
						createTransButton.setVisible(false);
						cancelTransButton.setVisible(false);
						
						//sets former display's components to be visible
						addTransaction.setVisible(true);
						saveTransButton.setVisible(true);
						categoryTransAnalysisButton.setVisible(true);
						dayTransAnalysisButton.setVisible(true);
						backToMonthsButton.setVisible(true);
						readFromStatement.setVisible(true);
					}
				});
				
				//action block for button that takes user back out of month's transaction menu
				backToMonthsButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						
						//shows relevant options
						saveMonthsButton.setVisible(true);
						addMonthButton.setVisible(true);
						
						//sets irrelevant components to be invisible
						addTransaction.setVisible(false);
						saveTransButton.setVisible(false);
						categoryTransAnalysisButton.setVisible(false);
						dayTransAnalysisButton.setVisible(false);
						backToMonthsButton.setVisible(false);
						readFromStatement.setVisible(false);
						
						populateList(monthListModel, monthsInList);
						
					}
				});
		
			//action block for the add month button
			addMonthButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
						
					///*
					//hides non-relevant options for this task
					saveMonthsButton.setVisible(false);
					addMonthButton.setVisible(false);
					
					//sets relevant components to be visible
					enterYear.setVisible(true);
					enterMonth.setVisible(true);
					monthComboBox.setVisible(true);
					yearText.setVisible(true);
					createMonth.setVisible(true);
					loadMonth.setVisible(true);
					backFromAddMonth.setVisible(true);
					
					yearText.setText("");
					monthComboBox.setSelectedIndex(-1);
				}	
			});
					
					//creates a new month based on user input
					createMonth.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent b) {
								
								String yT = yearText.getText();
								String mT = (String) monthComboBox.getSelectedItem();
								
								//verify input integrity (WORTH IMPROVING FOR BETTER ERROR HANDLING?)
								if(Constants.isInteger(yT) && monthComboBox.getSelectedIndex() >= 0) 
								{
				
									//creates the new month class with input at parameters
									//and adds that to the linked list
									MonthClass newMonth = new MonthClass(yT, mT);
									monthsInList.addMonthNode(newMonth);
									
									yearText.setText("");
									monthComboBox.setSelectedIndex(-1);
									
									//hides non-relevant options for this task
									saveMonthsButton.setVisible(true);
									addMonthButton.setVisible(true);
									
									//sets relevant components to be visible
									enterYear.setVisible(false);
									enterMonth.setVisible(false);
									monthComboBox.setVisible(false);
									yearText.setVisible(false);
									createMonth.setVisible(false);
									loadMonth.setVisible(false);
									backFromAddMonth.setVisible(false);
									
									//fills in the gui list with month objects
									populateList(monthListModel, monthsInList);
								}
								
								else {
									
									//HAVE DIALOG BOX EXPLAINING WRONG INPUT
									yearText.setText("");
									monthComboBox.setSelectedIndex(-1);									
								}
							}
					});
					
					backFromAddMonth.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent a) {
							
							//shows relevant options for this task
							saveMonthsButton.setVisible(true);
							addMonthButton.setVisible(true);
							
							//sets irrelevant components to be invisible
							enterYear.setVisible(false);
							enterMonth.setVisible(false);
							monthComboBox.setVisible(false);
							yearText.setVisible(false);
							createMonth.setVisible(false);
							loadMonth.setVisible(false);
							backFromAddMonth.setVisible(false);
						}
					});
					
					//NEED BUTTON EVENT FOR LOADING A MONTH FILE INTO LIST-------------------------------
			
			//save months button action block
			saveMonthsButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					//SAVES ALL MONTH OBJECTS BY CALLING SAVE METHOD IN EACH MONTH CLASS
				}
			});
		
		//adds elements to the frame
		add(addMonthButton);
		add(saveMonthsButton);
		add(enterYear);
		add(enterMonth);
		add(monthComboBox);
		add(yearText);
		add(createMonth);
		add(loadMonth);
		add(addTransaction);
		add(saveTransButton);
		add(categoryTransAnalysisButton);
		add(dayTransAnalysisButton);
		add(backToMonthsButton);
		add(readFromStatement);
		add(transDayLabel);
		add(transInTypeLabel);
		add(transValueLabel);
		add(transValField);
		add(transInField);
		add(transDayField);
		add(createTransButton);
		add(cancelTransButton);
		add(backFromAddMonth);
		
		add(monthPane);	
		
		//sets stage for initial GUI options
		enterYear.setVisible(false);
		enterMonth.setVisible(false);
		monthComboBox.setVisible(false);
		yearText.setVisible(false);
		createMonth.setVisible(false);
		loadMonth.setVisible(false);
		addTransaction.setVisible(false);
		saveTransButton.setVisible(false);
		categoryTransAnalysisButton.setVisible(false);
		dayTransAnalysisButton.setVisible(false);
		backToMonthsButton.setVisible(false);
		readFromStatement.setVisible(false);
		transDayLabel.setVisible(false);
		transInTypeLabel.setVisible(false);
		transValueLabel.setVisible(false);
		transValField.setVisible(false);
		transInField.setVisible(false);
		transDayField.setVisible(false);
		createTransButton.setVisible(false);
		cancelTransButton.setVisible(false);
		backFromAddMonth.setVisible(false);
	}
	
	//method for populating the pane with Month objects from the linked list
	public void populateList(DefaultListModel targetDLM, MonthList targetList) {
		
		targetDLM.removeAllElements();
		
		int index = 0;
		
		while(index < targetList.getLength()) {
			
			targetDLM.addElement(targetList.getTarget(index).getYear() + ", " + targetList.getTarget(index).getMonth());
			index++;
		}
	}
	
	public void populateTransList(DefaultListModel targetDLM, TransactionList targetList) {
		
		targetDLM.removeAllElements();
		
		int index = 0;
		
		while(index < targetList.getLength()) {
			
			targetDLM.addElement(targetList.getTarget(index).getDay() + ": $" + targetList.getTarget(index).getDollarValue() +
						" ---- " + targetList.getTarget(index).getTranType());
			
			index++;
		}
	}
	
	//MAIN
	public static void main(String s[]) {
		
		//initializes and sizes the GUI frame
		JFrame mainFrame = new JFrame("Monease");
		
		//closing this GUI closes the program!
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.setContentPane(new MainGUI());
				
		mainFrame.setSize(500, 600);
		
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
		
	}
	
}
	
