import java.io.PrintStream;

public class LinkedListMultiset<T> extends Multiset<T>
{
	private Node<T> first;	//beginning of bag
	
	public LinkedListMultiset() {
		this.first = null;
	}	
	
	public void add(T item) {		
		if (this.first == null) {
			this.first = new Node<T>(item, 1);
		}
		else {
			Node<T> found = this.nodeFromItem(item);
			if (found != null) {	//increase count
				found.setCount(found.getCount() + 1);
			}
			else {	//creating new node by pushing back a existing one to next
				Node<T> newNode = new Node<T>(item, 1);
				newNode.setNext(first);
				this.first = newNode;
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
} // end of class LinkedListMultiset