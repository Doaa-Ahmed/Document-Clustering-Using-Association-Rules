package mining.partitioningNclustering;
import mining.directHashing.*;
import mining.testing.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class partitioning {
	
	public partitioning() {
	}

	/**
	 * Calculate support and confidence for each rule. Also checks to make sure
	 * the rule meets minConf
	 */
	public static ArrayList<Rule> findStrongRules(ArrayList<HashMap<ArrayList<String>,Integer>> baby, ArrayList<Rule> rules, double minConf) {
		ArrayList<String> leftSide = new ArrayList<String>();
		ArrayList<String> rightSide = new ArrayList<String>();
		ArrayList<String> total = new ArrayList<String>();
		//ArrayList<Rule> strongRules = new ArrayList<Rule>();
		int support;
		double tempConfidence;
		
		HashMap<ArrayList<String>,Integer> findT= new HashMap<ArrayList<String>,Integer>();
		HashMap<ArrayList<String>,Integer> findL= new HashMap<ArrayList<String>,Integer>();
		HashMap<Rule,Integer> strong= new HashMap<Rule,Integer>();
		for (Rule tempRule : rules) {
			leftSide = tempRule.getLeft();
			rightSide = tempRule.getRight();
			total = new ArrayList<String>(leftSide);
			total.addAll(rightSide);
			
			//for(int i=0; i<total.size(); i++)
				//System.out.println(total.get(i));
			
			Main.totals.add(total);
			int k= total.size();
			//if(baby.get(k-1).containsAll(total))
			
			findT= baby.get(k-1);
			//System.out.print(findT);
			Set<ArrayList<String>> keyset = findT.keySet();
			ArrayList<ArrayList<String>> keys = new ArrayList<ArrayList<String>>(keyset); 
			int ind=0;
			for (int a=0; a<keys.size();a++)
			{
				if (keys.get(a).containsAll(total)&& total.containsAll(keys.get(a)))
				{
					ind=a; break;
				}
			}
			
			support = findT.get(keys.get(ind));

			int k_= leftSide.size();
			findL= baby.get(k_-1);
			tempConfidence = (double) support
					/ (double) findL.get(leftSide);

			if (tempConfidence >= minConf) {
				//strongRules.add(tempRule);
				strong.put(tempRule,support );
			}
		}
		MyComparator comp=new MyComparator(strong);

	    TreeMap<Rule, Integer> newMap = new TreeMap<Rule, Integer>(comp);
	    newMap.putAll(strong);
	    Set<Rule> keyset = newMap.keySet();
		ArrayList<Rule> strongRules = new ArrayList<Rule>(keyset);
return strongRules;
	}
}
class MyComparator implements Comparator<Object> {

HashMap<Rule, Integer> map;

public MyComparator(HashMap<Rule, Integer> map) {
    this.map = map;
}

public int compare(Object o1, Object o2) {

    return ((Integer) map.get(o2)).compareTo((Integer) map.get(o1));

}
}