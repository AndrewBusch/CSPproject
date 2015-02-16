
public class MutualExclusive implements Constraint {

	Item item1, item2;
	Bag bag1, bag2;
	
	public MutualExclusive(Item item1, Item item2, Bag bag1, Bag bag2) {
		this.item1 = item1;
		this.item2 = item2;
		this.bag1 = bag1;
		this.bag2 = bag2;
	}
	
	@Override
	public boolean checkConstraint() {
		if(item1.inBag.name == bag1.name && item2.inBag.name == bag2.name) {
			return false;
		}
		if(item1.inBag.name == bag2.name && item2.inBag.name == bag1.name) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		String str = "";
		str += item1.name + " " + item2.name + " " + bag1.name + " " + bag2.name;
		return str;
	}

}
