
public class Binary implements Constraint {

	boolean isEqual;
	Item item1, item2;
	
	public Binary(boolean isEqual, Item item1, Item item2) {
		this.isEqual = isEqual;
		this.item1 = item1;
		this.item2 = item2;
	}
	
	@Override
	public boolean checkConstraint() {
		if(item1.inBag.name == item2.inBag.name) {
			return isEqual;
		} else{
			return !isEqual;
		}
	}
	
	public String toString() {
		String str = "";
		str += isEqual + " " + item1.name + " " + item2.name;
		return str;
	}
}
