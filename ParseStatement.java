//Have these already been imported with jar files and the like?
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class ParseStatement {

	public void readStatement(MonthClass targetMonth) throws Exception {
		
		String statementName;
		String strCurrentLine;
		float oDollarValue;
		int oInputType = 2; //2 indicates type as from statement
		String oTransactionType;
		int oDay;
		boolean oExpense;
		
		JFileChooser fileSelector = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int selectionValue = fileSelector.showOpenDialog(null);
		
		if (selectionValue == JFileChooser.APPROVE_OPTION) {
			
			File fileSelection = fileSelector.getSelectedFile();
			
			statementName = fileSelection.getAbsolutePath();
			
			try {
	
				BufferedReader br = new BufferedReader(new FileReader(statementName));
				
				//main logic
				while((strCurrentLine = br.readLine()) != null) {
					String[] parts = strCurrentLine.split("\\s+");
					
					//Get day of transaction
					oDay = Integer.parseInt(((parts[0]).split("\\/"))[1].toString());
	
					//Determines if transaction is an expense or income
					//Assigns dollar value accordingly
					if (parts[1].startsWith("-")) {
						oExpense = true;
						oDollarValue = Float.parseFloat(parts[1].substring(1));
					} else {
						oExpense = false;
						oDollarValue = Float.parseFloat(parts[1]);
	
					}
	
					//Grabs description of transaction
					oTransactionType = strCurrentLine.substring(strCurrentLine.indexOf(parts[2]));
					
					//Transaction object created accordingly
					TransactionObject newTranObj = new TransactionObject(oDollarValue, oInputType, oTransactionType, oDay, oExpense);
					targetMonth.monthTransactions.addTranNode(newTranObj);
	
				}
				
				br.close();
	
			} catch (IOException e) {

				e.printStackTrace();

			}
		}
	}
	
}