import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class DataStructureTester {

    private ArrayList<String> arDataSetForAdding = new ArrayList<String>();
    private ArrayList<String> arDataSetForRemoving = new ArrayList<String>();
    private ArrayList<String> arDataSetForSearching = new ArrayList<String>();
    
	public static void main(String[] args) {
		String type = args[0];
		int add = Integer.parseInt(args[1]); //no of adding
		int remove = Integer.parseInt(args[2]); //percentage of removing comparing to adding
		int search = Integer.parseInt(args[3]); //percentage of searching comparing to adding
		DataStructureTester tester = new DataStructureTester(add, remove, search);
		tester.run(type);
	}

	public DataStructureTester(int aAdd, int aRemove, int aSearch) {
		//reading file
		
		ArrayList<String> words = new ArrayList<String>();
		BufferedReader dataset = null;
		try {
            String word;
            dataset = new BufferedReader(new FileReader("dataset.txt"));
            while ((word = dataset.readLine()) != null) {
            	words.add(word);
            }
            dataset.close();
        }
		catch (IOException e) {
            e.printStackTrace();
        }
		
		//prepare data for adding				
		Random generator = new Random(); 	
		for (int i = 0; i < aAdd; i++) {
			int index = generator.nextInt(words.size()) + 1;
			this.arDataSetForAdding.add(words.get(index));
		}		
		
		//prepare data for removing
		if (aRemove > 0) {
			double ratio = (double)aRemove / (double)100 * (double)aAdd;
			for (int i = 0; i < ratio; i++) {
				int index = generator.nextInt(this.arDataSetForAdding.size());
				this.arDataSetForRemoving.add(this.arDataSetForAdding.get(index));
			}		
		}
		
		//prepare data for searching
		if (aSearch > 0) {
			double ratio = (double)aSearch / (double)100 * (double)aAdd;
			for (int i = 0; i < ratio; i++) {
				int index = generator.nextInt(this.arDataSetForAdding.size());
				this.arDataSetForSearching.add(this.arDataSetForAdding.get(index));
			}		
		}		
	}
	
	private void run(String aType) {
		Multiset<String> multiset = null;
		switch(aType) {
			case "linkedlist":
				multiset = new LinkedListMultiset<String>();
				break;
			case "sortedlinkedlist":
				multiset = new SortedLinkedListMultiset<String>();
				break;
			case "bst":
				multiset = new BstMultiset<String>();
				break;
//			case "hash":
//				multiset = new HashMultiset<String>();
//				break;
//			case "baltree":
//				multiset = new BalTreeMultiset<String>();
//				break;
			default:
				System.err.println("Unknown implmementation type.");
		}
		
		long startTime = System.nanoTime();

		for (String element: this.arDataSetForAdding) {
			multiset.add(element);			
		}
		
		for (String element: this.arDataSetForRemoving) {
			multiset.removeOne(element);
		}
		
		for (String element: this.arDataSetForSearching) {
			multiset.search(element);
		}
        long endTime = System.nanoTime();
        
        System.out.println("Structure: " + aType + ", time taken = " + 1000*((double)(endTime - startTime)) / Math.pow(10, 9) + " milliseconds");
	}
}
