
public class MutualExclusive implements Constraint {

	Item item1, item2;
	Bag bag1, bag2;
	int item1Index, item2Index, bag1Index, bag2Index;
	
	public MutualExclusive(Item item1, Item item2, Bag bag1, Bag bag2) {
		this.item1 = item1;
		this.item2 = item2;
		this.bag1 = bag1;
		this.bag2 = bag2;
		
		this.bag1.constrainted += 1;
		this.bag2.constrainted += 1;

	}
	
	public MutualExclusive(Item item1, Item item2, Bag bag1, Bag bag2, int item1Index, int item2Index, int bag1Index, int bag2Index) {
		this.item1 = item1;
		this.item2 = item2;
		this.bag1 = bag1;
		this.bag2 = bag2;
		this.item1Index = item1Index;
		this.item2Index = item2Index;
		this.bag1Index = bag1Index;
		this.bag2Index = bag2Index;
		
		this.bag1.constrainted += 1;
		this.bag2.constrainted += 1;
	}
	
	@Override
	public boolean checkConstraint(State CSP) {
		//System.out.println(this);
		if(item1.inBag != null && item2.inBag != null && item1.inBag.name == bag1.name && item2.inBag.name == bag2.name) {
			return false;
		}
		if(item1.inBag != null && item2.inBag != null &&  item1.inBag.name == bag2.name && item2.inBag.name == bag1.name) {
			return false;
		}
		return true;
	}
	
	public int type() {
		return 2;
	}
	
	public String toString() {
		String str = "";
		str += "Mutuals: " + item1.name + " " + item2.name + " " + bag1.name + " " + bag2.name;
		return str;
	}

}
