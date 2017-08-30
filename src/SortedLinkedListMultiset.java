import java.io.PrintStream;

public class SortedLinkedListMultiset<T> extends Multiset<T> {
	private Node<T> first;
	
	public SortedLinkedListMultiset() {	
		this.first = null;
	}
	
	public void add(T item) {
		Node<T> newNode = new Node<T>(item, 1);
		if (this.first == null) { //empty list
			this.first = newNode;
			return;
		}
		if (((String) item).compareTo((String) this.first.getValue()) < 0) { //belong to the front of a list			
			newNode.setNext(this.first);
			this.first = newNode;
		}
		else {			
			Node<T> current = this.first;
			Node<T> prev = null;
			
			while (current != null) {
				int compare = ((String) item).compareTo((String) current.getValue());
							
				if (compare < 0) { //after previous and before current, previous always has a value here
					prev.setNext(newNode);
					newNode.setNext(current);
					break;
				}
				else if (compare == 0) { //find existing along the way
					current.setCount(current.getCount() + 1);
					break;
				}
				else if (current.getNext() == null) { //non existing value and loop until the end of a list
					current.setNext(newNode);
					break;
				}
				prev = current;
				current = current.getNext();
			
			}
		}
	}
		
	public int search(T item) {
		if (this.first != null) {
			Node<T> found = this.nodeFromItem(item);
			if (found != null) {
				return found.getCount();
			}
		}
		return 0;
	}
		
	public void removeOne(T item) {
		if (this.first != null) {
			Node<T> found = this.nodeFromItem(item);
			if (found != null) {
				if (found.getCount() == 1) {	//node need to be removed
					this.first = found.getNext();
					found = null;
				}
				else {	//just decrease count by 1
					found.setCount(found.getCount() - 1);
				}
			}
		}
	}
	
	
	public void removeAll(T item) {
		if (this.first != null) {
			Node<T> current = this.first;
			Node<T> prev = null;
			
			while (current != null) {
				int compare = ((String) item).compareTo((String) current.getValue());
				
				//found
				if (compare == 0) {					
					if (prev == null) { //found at the head
						this.first = current.getNext();
						return;
					}					
					else {	//found at the middle
						prev.setNext(current.getNext());
					}
				}
				prev = current;
				current = current.getNext();
			}
		}		
	}
	
	public void print(PrintStream out) {
		Node<T> currentNode = this.first;
		while (currentNode != null) {
			out.println(currentNode.getValue() + printDelim + currentNode.getCount());
			currentNode = currentNode.getNext();
		}
	}		
	
	private Node<T> nodeFromItem(T aItem) {
		Node<T> currentNode = this.first;
		while (currentNode != null) {
			if (currentNode.getValue().equals(aItem)) {
				return currentNode;
			}
			currentNode = currentNode.getNext(); 
		}		
		return null;
	}
	
	@SuppressWarnings("hiding")
	public class Node<T> {        
        private T value; //value of node
        private Node<T> next; //reference of next node
        private int count; //number of value
        
        public Node(T aValue, int aCount) {
        	super();
        	this.setValue(aValue);
        	this.setCount(aCount);
        	this.setNext(null);
        }       
        
		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}	
    }
}
