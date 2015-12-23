package mining.partitioningNclustering;
import java.util.ArrayList;
import java.util.HashMap;

public class clustering {

	public ArrayList<String> getRepWords(
			ArrayList<HashMap<String, Integer>> partition, double parSupport) {
		int wordsInPartition = 0;
		HashMap<String, Integer> representativeWords = new HashMap<String, Integer>();
		ArrayList<String> repWords = new ArrayList<String>();

		for (int i = 0; i < partition.size(); i++) {
			// increasing words in Partition number to find total
			wordsInPartition += partition.get(i).size();

			// get frequency of each unique word
			for (String key : partition.get(i).keySet()) {
				int freq = partition.get(i).get(key);
				int increase = 1;
				if (partition.get(i).containsKey(freq)) {
					increase = ((Integer) partition.get(i).get(freq))
							.intValue();
					representativeWords.put(key, increase + 1);

				} else
					representativeWords.put(key, increase);
			}
		}

		// making sure a word support> par_supp
				for(String key : representativeWords.keySet()){
					if (representativeWords.get(key) > parSupport){
						repWords.add(key);
					}
				}
			
		return repWords;
	}

/*	public ArrayList<HashMap<String,Integer>> findSim(ArrayList<ArrayList<String>>repWords){
		for(int i=0; i<repWords.size(); i++){
			for(int j=0; j<repWords.get(i).size(); j++)
				if(repWords.get(i).get(j)){}
		}
	
	}*/
	
	
	//this method will return the number of partitions clustered together in an arraylist
	/*public ArrayList<ArrayList<String>>cluster(ArrayList<HashMap<String,Integer>> simMeasure){
		ArrayList<ArrayList<HashMap<String, Integer>>> clusterTotal = new ArrayList<ArrayList<HashMap<String, Integer>>>();

		for(int i=0; i<simMeasure.size(); i++){
			for(int j=0; j<simMear)
			//for(int j=0; j<clusterTotal.size(); j++){
				for(int k=0; k<clusterTotal.get(i).keySet(); k++){
					
				}
				for (String key1 : simMeasure.get(i).keySet()) {
					for(String key2 : simMeasure.get(j).keySet())
			}
		}
		
		return clusterTotal;
	}*/
	
	public ArrayList<ArrayList<Integer>> cluster(ArrayList<Integer> simMeasure){
		ArrayList<ArrayList<Integer>> clusterTotal = new ArrayList<ArrayList<Integer>>();
		boolean added = false;
		
		for(int i=0; i<simMeasure.size(); i++){
			for(int j=0; j<simMeasure.get(i); j++){
				if(i != j){
					if(simMeasure.get(i) == simMeasure.get(j)){
						for(int k=0; k<clusterTotal.size(); k++){
							for(int l=0; l<clusterTotal.size(); l++){
								if (clusterTotal.get(k).contains(i+1) && !clusterTotal.get(k).contains(j+1)){
									clusterTotal.get(k).add(j+1);
									added = true;
								}
								else if (!clusterTotal.get(k).contains(i+1) && clusterTotal.get(k).contains(j+1)){
									clusterTotal.get(k).add(i+1);
									added = true;
								}
								else{
									//do nothing, don't add them because they are already found in clusterTotal
								}
							}
						}
						if (!added){
							ArrayList<Integer> clusterSub = new ArrayList<Integer>();
							clusterSub.add(i+1);
							clusterSub.add(j+1);
						}
					}
				}
			}
		}
		return clusterTotal;
	}
	
}
