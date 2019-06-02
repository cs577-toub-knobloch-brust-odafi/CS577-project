//SE577 Project: Monease
//Month Linked List

import java.util.ListIterator;

public class MonthList {
	
	//stores first object in list
	private Node listHead;
	
	//nested node class
	private class Node{
		
		MonthClass month;
		private Node nextNode;
		
		//default node constructor
		public Node() {
			
			month = new MonthClass();
			nextNode = null;
		}
		
		//alternate node constructor with mutators
		public Node(MonthClass newMonth, Node newNode) {
			
			month = newMonth;
			nextNode = newNode;
		}
		
	}
	
	//linked list constructor
	public MonthList() {
		
		listHead = null;
	}
	
	//method for adding a month node to the list
	public void addMonthNode(MonthClass addMonth) {
		
		listHead = new Node(addMonth, listHead);
	}
	
	public MonthClass getTarget(int target) {
		
		Node targetNode = listHead;
		int index = 0;
		
		while(targetNode != null) {
			
			if (index == target) {
				
				return targetNode.month;	
			}
			
			index++;
			targetNode = targetNode.nextNode;
			
			}
		
		//THIS SHOULD BE REPLACED WITH A TRY CATCH EXCEPTION
		assert(false);
		return targetNode.month;
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
