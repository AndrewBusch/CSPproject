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

	public static void main(String[] args) throws IOException {
		inputFileName = args[0];
		// outputFileName = args[1];
		System.out.println("Beginning Data Parsing...");
		processFile();
		System.out.println("Data Parsing Complete!");
		printOut();
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
					int j = 0;
					for (Item it : items) {
						if (it.name == dataRow.get(0).charAt(0)) {
							tempItem = it;
							break;
						}
					}
					for (Bag bg : bags) {
						if (dataRow.contains((bg.name + ""))) {
							tempBags[j++] = bg;
						}
					}
					constraints.add(new Unary(inclusive, tempBags, tempItem));
					break;
				case 5:
					isEqual = true;
				case 6:
					Item item1 = null;
					Item item2 = null;
					for (Item it : items) {
						if (it.name == dataRow.get(0).charAt(0)) {
							item1 = it;
						} else if (it.name == dataRow.get(1).charAt(0)) {
							item2 = it;
						}
					}
					constraints.add(new Binary(isEqual, item1, item2));
					break;
				case 7:
					Item itm1 = null;
					Item itm2 = null;
					Bag bg1 = null;
					Bag bg2 = null;
					for (Item it : items) {
						if (it.name == dataRow.get(0).charAt(0)) {
							itm1 = it;
						} else if (it.name == dataRow.get(1).charAt(0)) {
							itm2 = it;
						}
					}
					for (Bag bg : bags) {
						if (bg.name == dataRow.get(2).charAt(0)) {
							bg1 = bg;
						} else if (bg.name == dataRow.get(3).charAt(0)) {
							bg2 = bg;
						}
					}
					constraints.add(new MutualExclusive(itm1, itm2, bg1, bg2));
					break;
				default:
					break;
				}
			}
		}

		// bw.write(currentLine);
		// bw.newLine();

		br.close();
		// bw.close();
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
