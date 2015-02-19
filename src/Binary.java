
public class Binary implements Constraint {

	boolean isEqual;
	Item item1, item2;
	int item1Index, item2Index;
	
	public Binary(boolean isEqual, Item item1, Item item2) {
		this.isEqual = isEqual;
		this.item1 = item1;
		this.item2 = item2;
	}
	
	public Binary(boolean isEqual, Item item1, Item item2, int item1Index, int item2Index) {
		this.isEqual = isEqual;
		this.item1 = item1;
		this.item2 = item2;
		this.item1Index = item1Index;
		this.item2Index = item2Index;
	}
	
	@Override
	public boolean checkConstraint( State CSP) {
		if(isEqual && item1.inBag != null && item2.inBag!= null && item1.inBag.name != item2.inBag.name) {
			return false;
		} else if(!isEqual && item1.inBag != null && item2.inBag!= null && item1.inBag.name == item2.inBag.name) {
			return false;
		} else {
			return true;
		}
	}
	

	public int type() {
		return 1;
	}
	
	
	public String toString() {
		String str = "";
		str += "isEqual: " + isEqual + " " + item1.name + " " + item2.name;
		return str;
	}
}
