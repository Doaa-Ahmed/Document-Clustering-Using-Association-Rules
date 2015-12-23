package mining.directHashing;
import java.util.*;
import java.util.Map.Entry;

public class dhp {
	public dhp() {
	}

	
	// baby is the full documents to get the value of important words in the
	// document
	public ArrayList<HashMap<ArrayList<String>, Integer>> h_func(ArrayList<HashMap<String, Integer>> baby,
			ArrayList<ArrayList<String>> imp, double thresh) {
		int k = 0;
		ArrayList<HashMap<ArrayList<String>, Integer>> itemsets = new ArrayList<HashMap<ArrayList<String>, Integer>>();

		itemsets.add(new HashMap<ArrayList<String>, Integer>()); // the
		// H
		// k

		for (int j = 0; j < imp.size(); j++)// loop on the first
		// arraylist of words
		{
			for (int x = 0; x < imp.get(j).size(); x++) // perform on a word
														// from the first
														// arraylist
			{
				// System.out.println("core: " + imp.get(j).get(x)+" ");
				ArrayList<String> newList = new ArrayList<String>();
				newList.add(imp.get(j).get(x));
				if (itemsets.get(k).containsKey(newList)) {
					itemsets.get(k).put(
							newList,
							new Integer(itemsets.get(k).get(newList)
									+ (baby.get(j).get(imp.get(j).get(x)))));
					newList.clear();

				} else
					itemsets.get(k).put(newList,
							baby.get(j).get(imp.get(j).get(x)));
			}
		}

		// Adding to the frequent itemset :D
		ArrayList<String> deleted=new ArrayList<String>();
		for (Entry<ArrayList<String>, Integer> entry : itemsets.get(k).entrySet()) {
			if (entry.getValue() < thresh) {
				deleted.addAll(entry.getKey());
				for (int j = 0; j < baby.size(); j++) {
					if (baby.get(j).containsKey(entry.getKey()))
						baby.get(j).remove(entry.getKey()); // and
					// remove it
					// from your
					// database
				}

			} /*
			 * else { System.out.println("here here " + entry.getKey() +
			 * " gives " + entry.getValue());
			 * System.out.println(itemsets.get(k).isEmpty()); }
			 */

		}
		for(int q=0; q<deleted.size();q++){
			itemsets.get(k).remove(deleted.get(q));
		}
		deleted.clear();
		
		
		Set<ArrayList<String>> keyset = itemsets.get(0).keySet();
		ArrayList<ArrayList<String>> words = new ArrayList<ArrayList<String>>(keyset);
		
		//for (int t=0; t<words.size();t++)
			//System.out.println("before while "+words.get(t)+" " );
		
		ArrayList<ArrayList<String>> k_items = new ArrayList<ArrayList<String>>();
		k_items.addAll(words);
		
		while (k<itemsets.size()) {
			//itemsets.get(k - 1).isEmpty()
			k = k + 1;
			System.out.println("entered while ");
			k_items = new ArrayList<ArrayList<String>>();
			
			//k_items.add(new ArrayList<String>());// of k
			
					int kk = 1;
					int equ = 0;
					int x = 1;
					int word_size = words.size();
					
					while (equ < word_size) {
						while (kk < word_size-k) {
							ArrayList<String> newer = new ArrayList<String>();
							newer.addAll(words.get(equ));
							while (newer.size() <= k) {
								newer.addAll(words.get(kk));
								//System.out.println("here: "+words.get(kk));
								kk=kk+1;
								
							}

							if (!k_items.contains(newer)) {
								
								k_items.add(newer);
								
								//newer.clear();
							}
							x=x+1;
							kk=x;
						}
						
						equ=equ+1; 
						kk=equ+1;
						x= equ+1;
						word_size=word_size-1;
					}
					System.out.println( "items are " + k_items);
			// ///////////////// adding to the dynamic Hash Table :D
				for (int j = 0; j < k_items.size(); j++) {
					int count = 0;
					for (int d = 0; d < baby.size(); d++) {
						Set<String> keys = baby.get(d).keySet(); // words of
																	// doc
						ArrayList<String> docwords = new ArrayList<String>(keys);
					//	System.out.println("words in the doc: "+ docwords);
						//System.out.println("check"+" "+k_items.get(j));
						if (docwords.containsAll(k_items.get(j)))
							count++;
							
					}
					
					if (count >= thresh) {
						ArrayList<String> arr = new ArrayList<String>();
						arr.addAll(k_items.get(j));
						HashMap<ArrayList<String>, Integer> hash = new HashMap<ArrayList<String>, Integer>();
						hash.put(arr, count);
						itemsets.add(hash);
								}

				}

			// Stopping condition
			if (itemsets.get(k-1).isEmpty()) // .get(k).isEmpty()
			{
				System.out.print("empty");
				break;
			} else {
				
				System.out.println("in the else "+itemsets.get(k-1)+" ");
				
			}
		}
		// ///// output
		System.out.println("items here_________ " + itemsets);
		return itemsets;
	}
}
