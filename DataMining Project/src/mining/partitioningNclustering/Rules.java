package mining.partitioningNclustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rules {

	public ArrayList<Rule> assocRules = new ArrayList<Rule>();

	public ArrayList<Rule> getAssocRules() {
		return assocRules;
	}

	public void setAssocRules(ArrayList<Rule> assocRules) {
		this.assocRules = assocRules;
	}

	public ArrayList<ArrayList<String>> getLeftSide() {

		ArrayList<ArrayList<String>> left = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < assocRules.size(); i++) {
			left.add(assocRules.get(i).getLeft());
		}
		return left;
	}
	
	public ArrayList<ArrayList<String>> getRightSide() {

		ArrayList<ArrayList<String>> right = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < assocRules.size(); i++) {
			right.add(assocRules.get(i).getRight());
		}
		return right;
	}
	
	public void setLeftSide(ArrayList<String> left, int position){
			assocRules.get(position).setLeft(left);
	}
	
	public void setRightSide(ArrayList<String> right, int position){
			assocRules.get(position).setRight(right);
	}
	
	

}
