import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataParser {
	static String inputFileName;
	static String outputFileName;
	static int MIN;
	static int MAX;
	static ArrayList<Item> items = new ArrayList<Item>();
	static ArrayList<Bag> bags = new ArrayList<Bag>();
	static ArrayList<Constraint> constraints = new ArrayList<Constraint>();
	static int nodeCount = 0;

	public static void main(String[] args) throws IOException {
		inputFileName = args[0];
		// outputFileName = args[1];
		processFile();
		//printOut();
	}

	public static void processFile() throws IOException {
		File inputFile = new File(inputFileName);
		// File outputFile = new File(outputFileName);
		// if(!outputFile.exists()) {
		// outputFile.createNewFile();
		// }
		//
		// FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
		// BufferedWriter bw = new BufferedWriter(fw);

		FileReader fr = new FileReader(inputFile.getAbsoluteFile());
		BufferedReader br = new BufferedReader(fr);

		String currentLine;
		int state = -1;
		while ((currentLine = br.readLine()) != null) {
			if (currentLine.substring(0, 3).equals("###")) {
				state++;
			} else {
				boolean inclusive = false;
				boolean isEqual = false;
				List<String> dataRow = Arrays.asList(currentLine.split(" "));
				switch (state) {
				case 0:
					items.add(new Item(dataRow.get(0).charAt(0), Integer
							.parseInt(dataRow.get(1))));
					break;
				case 1:
					bags.add(new Bag(dataRow.get(0).charAt(0), Integer
							.parseInt(dataRow.get(1))));
					break;
				case 2:
					MIN = Integer.parseInt(dataRow.get(0));
					MAX = Integer.parseInt(dataRow.get(1));
					break;
				case 3:
					inclusive = true;
				case 4:
					Bag[] tempBags = new Bag[dataRow.size() - 1];
					Item tempItem = null;
					int itemIndex = 0;
					int[] bagIndexes = new int[dataRow.size() -1];
					int j = 0;
					for (Item it : items) {
						if (it.name == dataRow.get(0).charAt(0)) {
							tempItem = it;
							itemIndex = items.indexOf(it);
							break;
						}
					}
					for (Bag bg : bags) {
						if (dataRow.contains((bg.name + ""))) {
							tempBags[j] = bg;
							bagIndexes[j] = bags.indexOf(bg);
							j++;
						}
					}
					constraints.add(new Unary(inclusive, tempBags, tempItem, bagIndexes, itemIndex));
					break;
				case 5:
					isEqual = true;
				case 6:
					Item item1 = null;
					Item item2 = null;
					int item1Index = 0;
					int item2Index = 0;
					for (Item it : items) {
						if (it.name == dataRow.get(0).charAt(0)) {
							item1 = it;
							item1Index = items.indexOf(it);
						} else if (it.name == dataRow.get(1).charAt(0)) {
							item2 = it;
							item2Index = items.indexOf(it);
						}
					}
					constraints.add(new Binary(isEqual, item1, item2, item1Index, item2Index));
					break;
				case 7:
					Item itm1 = null;
					Item itm2 = null;
					Bag bg1 = null;
					Bag bg2 = null;
					int itm1Index = 0;
					int itm2Index = 0;
					int bg1Index = 0;
					int bg2Index = 0;
					for (Item it : items) {
						if (it.name == dataRow.get(0).charAt(0)) {
							itm1 = it;
							itm1Index = items.indexOf(it);
						} else if (it.name == dataRow.get(1).charAt(0)) {
							itm2 = it;
							itm2Index = items.indexOf(it);
						}
					}
					for (Bag bg : bags) {
						if (bg.name == dataRow.get(2).charAt(0)) {
							bg1 = bg;
							bg1Index = bags.indexOf(bg);
						} else if (bg.name == dataRow.get(3).charAt(0)) {
							bg2 = bg;
							bg2Index = bags.indexOf(bg);
						}
					}
					constraints.add(new MutualExclusive(itm1, itm2, bg1, bg2, itm1Index, itm2Index, bg1Index, bg2Index));
					break;
				default:
					break;
				}
			}
		}
		if(MAX == 0) {
			MAX = Integer.MAX_VALUE;
		}
	
		br.close();
		System.out.println("Data Parsing Complete");
		// bw.close();
		
		State startState = new State(items, bags, constraints, MIN, MAX);
		State endState = startState.backtrackingSearch();
		
		if(endState == null) {
			pout("System Unsolvable.");
		} else{
			pout("success!");
			pout(endState.toString());
		}
	}

	public static void printOut() {
		pout("Items");
		for (Item it : items) {
			pout(it.name + " " + it.weight);
		}
		pout("");
		pout("Bags");
		for (Bag bg : bags) {
			pout(bg.name + " " + bg.capacity);
		}
		pout("");
		pout("Constraints");
		for (Constraint cs : constraints) {
			pout(cs.toString());
		}
		pout("MIN: " + MIN + " MAX: "+ MAX);
	}

	public static void pout(String str) {
		System.out.println(str);
	}
}
