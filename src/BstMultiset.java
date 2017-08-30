import java.io.PrintStream;

public class BstMultiset<T> extends Multiset<T> {
	
	Node rootNode;
	
	public BstMultiset() {
		this.rootNode = null;
	} // end of BstMultiset()

	public void add(T item) {
		this.insert(this.rootNode, item);		
	}

	public int search(T item) {
		Node node = nodeFromItem(item);
		if (node == null) {
			return 0;
		}
		else {
			return node.getCount();
		}
	} // end of add()


	public void removeOne(T item) {
		Node node = this.nodeFromItem(item);
		if (node != null) {
			node.setCount(node.getCount() - 1);
			if (node.getCount() == 0) {
				deleteRec(this.rootNode, item);		
			}
		}		
	} // end of removeOne()
	
	
	public void removeAll(T item) {
		this.rootNode = this.deleteRec(this.rootNode, item);	
	} // end of removeAll()


	public void print(PrintStream out) {
		display(this.rootNode, out);	
	} // end of print()

	public class Node {
		T value;
		Node left;
		Node right;	
		int count; //number of value
		
		public Node(T aValue, int aCount) {
			this.count = aCount;
			this.value = aValue;
			this.left = null;
			this.right = null;
		}
		
		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public Node getLeft() {
			return this.left;
		}

		public void setLeft(Node aLeft) {
			this.left = aLeft;
		}

		public Node getRight() {
			return this.right;
		}

		public void setRight(Node aRight) {
			this.right = aRight;
		}
		
		public int getCount() {
			return this.count;
		}
		
		public  void setCount(int aCount) {
			this.count = aCount;
		}
	}
	
	private void display(Node aRoot, PrintStream aOut) {
		if (aRoot != null) {
			display(aRoot.getLeft(), aOut);
			aOut.println(aRoot.getValue() + printDelim + aRoot.getCount());
			display(aRoot.getRight(), aOut);
		}
	}
	
	private Node insert(Node aRoot, T aItem) {
		Node newNode = new Node(aItem, 1);
		if (aRoot == null) { //set root
			aRoot = newNode;
			if (this.rootNode == null) {
				this.rootNode = aRoot;
			}
			return aRoot;
		}
		
		int compare = ((String) aItem).compareTo((String) aRoot.getValue());	
		if (compare == 0) { //value is equal to item
			aRoot.setCount(aRoot.getCount() + 1);
		}
		else if (compare > 0) { //value is less than so loop again			
			aRoot.setRight(this.insert(aRoot.getRight(), aItem));
		}
		else if (compare < 0) { //value is more than so loop again
			aRoot.setLeft(this.insert(aRoot.getLeft(), aItem));
		}
		return aRoot;
	}
	
	Node deleteRec(Node aRoot, T aItem) {
        if (aRoot == null) 
        	return aRoot;
         
        int compare = ((String) aItem).compareTo((String) aRoot.getValue());
        if (compare < 0) {
        	aRoot.left = deleteRec(aRoot.left, aItem);
        }
        else if (compare > 0) {
        	aRoot.right = deleteRec(aRoot.right, aItem);
        }
        else { // if a item is the same as the root, then delete            
            if (aRoot.left == null) //node with no or one child
                return aRoot.right;
            else if (aRoot.right == null)
                return aRoot.left;
 
            //2 children
            aRoot.setValue(minValue(aRoot.right));
            aRoot.setCount(aRoot.right.count);
            
            aRoot.right = deleteRec(aRoot.right, aRoot.getValue()); //delete
        }
 
        return aRoot;
    }
	
	private T minValue(Node aRoot) {
        T minv = aRoot.getValue();
        while (aRoot.left != null){
            minv = aRoot.left.value;
            aRoot = aRoot.left;
        }
        return minv;
    }
	
	private Node nodeFromItem(T aItem) {
		Node current = this.rootNode;
		while (current != null) {			
			int compare = ((String) aItem).compareTo((String) current.getValue());
			if (compare == 0) {
				return current;
			}
			else if (compare < 0) {
				current = current.getLeft(); 
			}
			else {
				current = current.getRight();
			}
		}		
		return null;
	}

}