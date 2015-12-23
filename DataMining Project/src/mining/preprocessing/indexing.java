package mining.preprocessing;
import java.util.ArrayList;
import java.util.HashMap;


public class indexing {

	public indexing() {	
		
	}
public ArrayList<ArrayList<String>> weightscheme(ArrayList <String> words,ArrayList<HashMap<String, Integer>> baby, double threshold){
	//ArrayList<String> m= new ArrayList<String>();
	ArrayList<ArrayList<String>> Imp= new ArrayList<ArrayList<String>>();
	for (int j=0; j< baby.size(); j++)
	Imp.add(new ArrayList<String>());
	
	for (int i=0; i<words.size(); i++)
	{
	int numdoc_occur=0;
	for (int j=0; j< baby.size(); j++)
	{
		 
		if (baby.get(j).containsKey(words.get(i)))
		numdoc_occur++;
	}
	// now I have the number of documents that the word occurs in
	
	for (int j=0; j< baby.size(); j++)
	{  
		double wei=0.0;
		if (baby.get(j).containsKey(words.get(i)))
		{
			wei=(baby.get(j).get(words.get(i)))*(Math.log(baby.size()/numdoc_occur)/Math.log(2));
		}	
		// now I have the weight of a word in a certain document
		if (wei>=threshold)
		Imp.get(j).add(words.get(i));
	}	
}
	
return Imp;
}
}
