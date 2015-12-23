package mining.partitioningNclustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Rule {
	ArrayList<String> left;
	ArrayList<String> right;
	double support;
	double confidence;

	public Rule() {
	}

	public Rule(ArrayList<String> left, ArrayList<String> right) {
		this.left = left;
		this.right = right;
	}

	public ArrayList<String> getLeft() {
		return left;
	}

	public void setLeft(ArrayList<String> left) {
		this.left = left;
	}

	public ArrayList<String> getRight() {
		return right;
	}

	public void setRight(ArrayList<String> right) {
		this.right = right;
	}

	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double tempConfidence) {
		this.confidence = tempConfidence;
	}

	public ArrayList<Rule> generateRules(
			ArrayList<HashMap<ArrayList<String>, Integer>> pHashmap) {
		

		ArrayList<Rule> returnList = new ArrayList<Rule>();
		
		for (int i = 0; i < pHashmap.size(); i++) {
			for (ArrayList<String> s : pHashmap.get(i).keySet()) {
				int leftSize;
				ArrayList<ArrayList<String>> subsetList = new ArrayList<ArrayList<String>>();

				ArrayList<String> leftSideSingle = new ArrayList<String>();
				ArrayList<String> rightSide = new ArrayList<String>();
				ArrayList<String> tempList = new ArrayList<String>(s);
				//tempList.addAll(s);

				if (s.size() < 2) {
					// Well there aren't any rules if the itemsets only have one
					// item...don't generate any.
				} else if (s.size() == 2) {
					// Generate two pairs
					leftSideSingle.add(tempList.get(0));
					rightSide.add(tempList.get(1));
					returnList.add(new Rule(leftSideSingle, rightSide));
					returnList.add(new Rule(rightSide, leftSideSingle));
				} else {
					for (leftSize = 1; leftSize < s.size(); leftSize++) {
						// Generate the subsets for the left side
						subsetList = generateSubset(tempList, leftSize);
						// Loop through the resulting left side subsets and
						// determine
						// the right side
						for (ArrayList<String> leftSide : subsetList) {
							// Create the right side from all of the elements
							rightSide = new ArrayList<String>(tempList);
							// Remove all those that occur in the left side
							rightSide.removeAll(leftSide);
							// Add a rule of the left side and right side
							returnList.add(new Rule(leftSide, rightSide));

						}
					}
				}
			}
		}
		
	for(int i=0; i<returnList.size(); i++){
		System.out.println("rule" + i);
		for(int j=0; j<returnList.get(i).getLeft().size(); j++)
		System.out.print(returnList.get(i).getLeft().get(j) + ", ");
		System.out.print(" -> ");
		for(int j=0; j<returnList.get(i).getRight().size(); j++)
		System.out.print(returnList.get(i).getRight().get(j) + ", ");
		System.out.println();
	}
		
		return returnList;
	}

	public ArrayList<ArrayList<String>> generateSubset(
			List<String> originalSet, int comboSize) {
		ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
		getSubset(originalSet, comboSize, 0, new ArrayList<String>(),
				resultList);
		return resultList;
	}

	private static void getSubset(List<String> originalSet, int comboSize,
			int pivot, ArrayList<String> current,
			List<ArrayList<String>> resultSet) {
		// If this is the base case
		if (current.size() == comboSize) {
			resultSet.add(new ArrayList<String>(current));
			return;
		}

		// If the pivot has somehow reached the size of the originalSet (not
		// good..)
		if (pivot == originalSet.size()) {
			return;
		}

		String x = originalSet.get(pivot);
		current.add(x);

		// Recurse another level
		getSubset(originalSet, comboSize, pivot + 1, current, resultSet);
		// Remove the just added value from the current set
		current.remove(x);
		// Recurse another level without the just removed value
		getSubset(originalSet, comboSize, pivot + 1, current, resultSet);
	}

}
