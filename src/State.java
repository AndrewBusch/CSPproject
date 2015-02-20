import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class State {
	ArrayList<Item> items;
	ArrayList<Bag> bags;
	ArrayList<Integer> weights;
	ArrayList<Integer> itemsPerBag;
	ArrayList<Constraint> constraints;
	int MIN;
	int MAX;

	public State(ArrayList<Item> items, ArrayList<Bag> bags,
			ArrayList<Constraint> constraints, int MIN, int MAX) {
		this.items = new ArrayList<Item>(items);
		this.bags = new ArrayList<Bag>(bags);
		this.weights = new ArrayList<Integer>();
		this.itemsPerBag = new ArrayList<Integer>();
		this.constraints = new ArrayList<Constraint>(constraints);
		this.MIN = MIN;
		this.MAX = MAX;
		for(int i = 0; i < bags.size(); i++) {
			weights.add(0);
			itemsPerBag.add(0);
		}
		updateAllowedItems();
	}

	public State(State csp) {
		this.items = new ArrayList<Item>();
		for(Item i: csp.items) {
			this.items.add(i.copy());
		}
		this.bags = new ArrayList<Bag>(csp.bags);
		this.weights = new ArrayList<Integer>();
		for(Integer w: csp.weights) {
			this.weights.add(new Integer(w.intValue()));
		}
		this.itemsPerBag = new ArrayList<Integer>();
		for(Integer i: csp.itemsPerBag) {
			this.itemsPerBag.add(new Integer(i.intValue()));
		}
		this.constraints = new ArrayList<Constraint>();
		for(Constraint cs: csp.constraints) {
			switch(cs.type()) {
			case 0:
				Unary cons = (Unary) cs;
				int index = cons.itemIndex;
				int[] indexes = cons.bagIndexes;
				Item it = this.items.get(index);
				Bag[] bgs = new Bag[indexes.length];
				for(int i = 0; i < indexes.length; i++) {
					bgs[i] = this.bags.get(indexes[i]);
				}
				this.constraints.add(new Unary(cons.inclusive, bgs, it, indexes, index));
				break;
			case 1:
				Binary cns = (Binary) cs;
				int item1Index = cns.item1Index;
				int item2Index = cns.item2Index;
				Item item1 = this.items.get(item1Index);
				Item item2 = this.items.get(item2Index);
				this.constraints.add(new Binary(cns.isEqual, item1, item2, item1Index, item2Index));
				break;
			case 2:
				MutualExclusive cnst = (MutualExclusive) cs;
				int itm1Index = cnst.item1Index;
				int itm2Index = cnst.item2Index;
				int bag1Index = cnst.bag1Index;
				int bag2Index = cnst.bag2Index;
				Item itm1 = this.items.get(itm1Index);
				Item itm2 = this.items.get(itm2Index);
				Bag bag1 = this.bags.get(bag1Index);
				Bag bag2 = this.bags.get(bag2Index);
				this.constraints.add(new MutualExclusive(itm1, itm2, bag1, bag2, itm1Index, itm2Index, bag1Index, bag2Index));			
				break;
			default:
					System.out.println("This shouldn't happen");
					break;
			}
		}
		this.MIN = csp.MIN;
		this.MAX = csp.MAX;
	}

	public boolean checkSystemConstraints() {
		for (Constraint i : this.constraints) {
			if (!i.checkConstraint()) {
				return false;
			}
		}
		return true;
	}

	private Item getUnassignedItem() {
		for (Item i : items) {
			if (i.inBag == null) {
				return i;
			}
		}
		return null;
	}

	public State backtrackingSearch() {
		NodeCount runStats = new NodeCount();
		final long startTime = System.currentTimeMillis();
		State finalState = backtrack(this, runStats);
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) + "ms" );
		System.out.println( "TotalNodes: " + runStats.totalCount);
		return finalState;
	}

	private State backtrack(State CSP, NodeCount runStats) {
		runStats.totalCount += 1;
		
		if (CSP.getUnassignedItem() == null && CSP.weightCheck() && CSP.itemsPerCheck() && CSP.checkSystemConstraints()) {
			return CSP;
		}
		
		CSP.updateAllowedItems();								// Forward Check
		if(!CSP.passForwardCheck()) {
			return null;
		}
		
		ArrayList<Item> cpItems = new ArrayList<Item>(CSP.items);
		Collections.sort(cpItems);								// MRV heuristic sort
		ArrayList<Bag> cpBags = new ArrayList<Bag>(CSP.bags);
		Collections.sort(cpBags);								// LCV heuristic sort
		
		for (int k = 0; k < cpItems.size(); k++) {
			int i = getItemIndexByChar(cpItems.get(k).name, CSP);
			if(CSP.items.get(i).inBag == null) {
				for (int l = 0; l < cpBags.size(); l++) {
					int j = getBagIndexByChar(cpBags.get(l).name, CSP);
					if(CSP.items.get(i).allowedBags.contains(CSP.bags.get(j))) {
						State newCSP = new State(CSP);
						Item var = newCSP.items.get(i);
						newCSP.weights.set(j, (CSP.weights.get(j) + var.weight));
						newCSP.itemsPerBag.set(j, (CSP.itemsPerBag.get(j) + 1));
						var.inBag = bags.get(j);
						if (weightCheck() && upperItemsPerCheck() && newCSP.checkSystemConstraints()) {
							State testCSP = backtrack(newCSP, runStats);
							if (testCSP != null) {
								return testCSP;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private int getBagIndexByChar(char name, State CSP) {
		for(Bag i : CSP.bags) {
			if(i.name == name) {
				return CSP.bags.indexOf(i);
			}
		}
		return -1;
	}
	
	private int getItemIndexByChar(char name, State CSP) {
		for(Item i : CSP.items) {
			if(i.name == name) {
				return CSP.items.indexOf(i);
			}
		}
		return -1;
	}
	
	private boolean passForwardCheck() {
		for(Item i : items) {
			if(i.inBag == null) {
				if(i.allowedBags.size() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public void updateAllowedItems() {
		for(Item i : items) {
			i.allowedBags.clear();
		}
		for(Bag i : bags) {
			for(Item j : items) {
				if( j.inBag == null) {
					j.inBag = i;
					if(weightCheck() && upperItemsPerCheck() && checkSystemConstraints()) {
						j.allowedBags.add(i);
					}
					j.inBag = null;
				}
			}
		}
	}

	private boolean itemsPerCheck() {
		for(Integer i : itemsPerBag) {
			if(i > MAX || i < MIN) {
				return false;
			}
		}
		return true;
	}
	
	private boolean upperItemsPerCheck() {
		for(Integer i : itemsPerBag) {
			if(i > MAX) {
				return false;
			}
		}
		return true;
	}

	private boolean weightCheck() {
		for(int i = 0; i < weights.size(); i++) {
			if(weights.get(i) > bags.get(i).capacity) {
				return false;
			}
		}
		return true;
	}
	
	private boolean endWeightCheck() {
		for(int i = 0; i < weights.size(); i++) {
			if(weights.get(i) > bags.get(i).capacity || weights.get(i) < ((float)bags.get(i).capacity) * .9) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String ret = "";
		for(Bag i : bags) {
			int endWeight = 0;
			int itemCount = 0;
			ret += i.name + " ";
			for(Item j : items) {
				if(j.inBag == i) {
					endWeight += j.weight;
					itemCount++;
					ret += j.name + " ";
				}
			}
			ret +=  "\nEnd Weight: " + endWeight + "/" + weights.get(getBagIndexByChar(i.name, this));
			ret +=  "\nItem Count: " + itemCount + "\n\n";

		}
		
		return ret;
	}
}
