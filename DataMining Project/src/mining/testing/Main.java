package mining.testing;

import mining.directHashing.*;
import mining.preprocessing.*;
import mining.partitioningNclustering.*;

import java.util.*;
import java.util.Map.Entry;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
/*import java.util.Collection;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.List;*/
//import java.util.HashMap;
import java.util.Scanner;

import javax.swing.*;

//import javax.swing.text.html.HTML.Tag;

@SuppressWarnings("serial")
public class Main extends JFrame {
	static File[] sf;
	double weight;
	double conf;
	double minsup;
	public static ArrayList<ArrayList<String>> totals;

	public Main(double weight, double conf, double minsup) {

		super("File Chooser Test Frame");
		setSize(350, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		JButton openButton = new JButton("Open");
		JButton saveButton = new JButton("Save");

		final JLabel statusbar = new JLabel(
				"Output of your selection will go here");

		// Create a file chooser that opens up as an Open dialog
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser(
						"F:\\personal\\studies\\term 8\\mining\\project\\DM test");
				chooser.setMultiSelectionEnabled(true);
				int option = chooser.showOpenDialog(Main.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					/* File[] */sf = chooser.getSelectedFiles();
					String filelist = "nothing";
					if (sf.length > 0)
						filelist = sf[0].getName();
					for (int i = 1; i < sf.length; i++) {
						filelist += ", " + sf[i].getName();
					}
					statusbar.setText("You chose " + filelist);
					// System.out.println("start processing in");
					try {
						startPreprocessing(sf);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					statusbar.setText("You canceled.");
				}
			}
		});

		// Create a file chooser that opens up as a Save dialog
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				int option = chooser.showSaveDialog(Main.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					statusbar.setText("You saved "
							+ ((chooser.getSelectedFile() != null) ? chooser
									.getSelectedFile().getName() : "nothing"));
				} else {
					statusbar.setText("You canceled.");
				}
			}
		});

		c.add(openButton);
		c.add(saveButton);
		c.add(statusbar);
		this.weight = weight;
		this.conf = conf;
		this.minsup = minsup;

	}

	public void startPreprocessing(File[] filelist) throws Exception {
		// Array list of input documents , to take the words as string
		ArrayList<String> inputD = new ArrayList<String>();
		// ArrayList of the whole words
		ArrayList<String> Words = new ArrayList<String>();
		// ArrayList of documents
		ArrayList<HashMap<String, Integer>> ourBaby = new ArrayList<HashMap<String, Integer>>();

		// Input

		for (int i = 0; i < sf.length; i++) {
			Scanner sc = new Scanner(sf[i]);
			while (sc.hasNext()) {
				String j = sc.next();
				inputD.add(j);

			}

			System.out.println("");
			stop stopwords = new stop();
			ArrayList<String> temp = stopwords.removeStopWords(inputD);
			inputD.clear();

			inputD.addAll(temp);

			ArrayList<String> r = stemWithPorter(inputD);
			for (int w = 0; w < r.size(); w++) {
				if (Words.contains(r.get(w)) == false)
					Words.add(r.get(w));
			}

			HashMap<String, Integer> mytable = new HashMap<String, Integer>();
			for (int j = 0; j < r.size(); j++) {
				int freq = 1;
				if (mytable.containsKey(r.get(j))) {
					freq = ((Integer) mytable.get(r.get(j))).intValue();

					mytable.put(r.get(j), (freq + 1));
				} else
					mytable.put(r.get(j), freq);
			}
			ourBaby.add(mytable);
			// System.out.print(mytable);
			// mytable.clear();
			inputD.clear();
		}
		indexing index = new indexing();
		ArrayList<ArrayList<String>> s = index.weightscheme(Words, ourBaby,
				weight); // the important words

		for (int f = 0; f < ourBaby.size(); f++) {
			if (s.get(f).isEmpty()) {
				ourBaby.remove(ourBaby.get(f));
				System.out.println("remove: " + ourBaby.get(f));
				s.remove(s.get(f));

			} else {
				// System.out.println("before filteration: "+ ourBaby.get(f));
				Set<String> keyset = ourBaby.get(f).keySet();
				ArrayList<String> words = new ArrayList<String>(keyset);
				for (int d = 0; d < ourBaby.get(f).size(); d++) {
					if (!s.get(f).contains(words.get(d))) {
						ourBaby.get(f).remove(words.get(d));
						// System.out.println("inside: "+ words.get(d));
					}
				}
				// System.out.println("after filteration: "+ ourBaby.get(f));
			}
		}
		/*
		 * for (int f=0; f< ourBaby.size(); f++) System.out.println("up: "+
		 * ourBaby.get(f)+ s.get(f));
		 */

		System.out.println("entering hashing");
		dhp p = new dhp();
		ArrayList<HashMap<ArrayList<String>, Integer>> hash = p.h_func(ourBaby, s, minsup*ourBaby.size());
		System.out.println("out from hashing");
		
		/*FileWriter fstream;
	    BufferedWriter out;fstream = new FileWriter("values.txt");
	    out = new BufferedWriter(fstream); 
	    for(int i=0; i<hash.size(); i++){
	    Iterator<Entry<ArrayList<String>, Integer>> it = hash.get(i).entrySet().iterator();
	    while (it.hasNext()) {

	        // the key/value pair is stored here in pairs
	        Map.Entry<ArrayList<String>, Integer> pairs = it.next();
	        System.out.println("hashmap "+ i);
	        for(int j=0; j<pairs.getKey().size(); j++)
	        System.out.println("word is " + pairs.getKey().get(j));

	        // since you only want the value, we only care about pairs.getValue(), which is written to out
	        out.write(pairs.getValue() + "\n");
	    
	    // lastly, close the file and end
	    out.close();
	    }}*/
		
		System.out.println("entering rules");
		Rule rule = new Rule();
		System.out.println("new rule");
		Rules rules = new Rules();
		System.out.println("new rules");
		rules.assocRules = rule.generateRules(hash);
		System.out.println("rules generated finished");

		totals = new ArrayList<ArrayList<String>>();
		System.out.println("new totals");
		
/*
		System.out.println("before strong");
		for(int i=0; i<totals.size(); i++)
		{
			System.out.println("rule" + i);
			for(int j=0; j<totals.get(i).size(); j++){
				System.out.println(totals.get(i).get(j));
			}
		}*/
		
		rules.assocRules = partitioning. findStrongRules(hash, rules.assocRules, conf);
		

		System.out.println("after strong");
		for(int i=0; i<totals.size(); i++)
		{
			System.out.println("rule" + i);
			for(int j=0; j<totals.get(i).size(); j++){
				System.out.println(totals.get(i).get(j));
			}
		}

		System.out.println("strong rules finished");

		ArrayList<ArrayList<String>> partitions = new ArrayList<ArrayList<String>>();
		
		System.out.println("new partitions");
		
		for (int j =0; j<rules.assocRules.size(); j++){
			ArrayList<String> temp = new ArrayList<String>();
			for (int i = 0; i<ourBaby.size(); i++){
			if (ourBaby.get(i).keySet().containsAll(totals.get(j))){temp.add(sf[i].getName());}
			}
			partitions.add(temp);
		}

		System.out.println("names of documents added");
		
		for(int i=0; i<partitions.size(); i++)
			{System.out.println("partition" + i);
			for(int j=0; j<partitions.get(i).size(); j++)
				System.out.println(partitions.get(i).get(j));
			}
		
		System.out.println("processing Done");
	}

	// main
	public static void main(String args[]) {
		// input from user constraint of weight
		// threshold confidence
		Scanner in = new Scanner(System.in);

		double weight_constraint = in.nextDouble();
		double threshold_conf = in.nextDouble();
		double minsupp = in.nextDouble(); // minimum support of word
		Main sfc = new Main(weight_constraint, threshold_conf, minsupp);
		sfc.setVisible(true);

	}

	public ArrayList<String> stemWithPorter(ArrayList<String> inputD) {
		ArrayList<String> doc = new ArrayList<String>();

		stemmer s = new stemmer();
		stemmer.Porter p = s.new Porter();
		for (int i = 0; i < inputD.size(); i++) {
			doc.add(p.stripAffixes(inputD.get(i)));
		}

		return doc;

	}

}
