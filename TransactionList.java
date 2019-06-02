//SE577 Project: Monease
//TransactionObject list for a MonthClass

import java.util.ListIterator;


public class TransactionList {
	
	private Node listHead;
	
	//nested Node class
	private class Node{
		
		TransactionObject transaction;
		private Node nextNode;
		
		//default Node constructor
		public Node(){
			
			transaction = new TransactionObject();
			nextNode = null;
		}
		
		//alternate Node constructor
		public Node(TransactionObject newTranObj, Node newNode) {
			
			transaction = newTranObj;
			nextNode = newNode;
		}
	}
	
	//linked list constructor
	public TransactionList() {
		
		listHead = null;
		
	}
	
	//method for adding a transaction node to the list
	public void addTranNode(TransactionObject addTranObj) {
		
		listHead = new Node(addTranObj, listHead);
	}
	
	public TransactionObject getTarget(int target) {
		
		Node targetNode = listHead;
		int index = 0;
		
		while(targetNode != null) {
			
			if (index == target) {
				
				return targetNode.transaction;	
			}
			
			index++;
			targetNode = targetNode.nextNode;
			
			}
		
		//THIS SHOULD BE REPLACED WITH A TRY CATCH EXCEPTION
		assert(false);
		return targetNode.transaction;
		}
	
	public int getLength() {
		
		Node target = listHead;
		int index = 0;
		
		while(target != null) {
			
			index++;
			target = target.nextNode;
		}
		
		return index;
	}
	
}


