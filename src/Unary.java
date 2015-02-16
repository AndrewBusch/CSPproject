
public class Unary implements Constraint {

	boolean inclusive;
	Bag[] bags;
	Item item;
	
	public Unary(boolean inclusive, Bag[] bags, Item item) {
		this.inclusive = inclusive;
		this.bags = bags;
		this.item = item;
	}
	
	@Override
	public boolean checkConstraint() {
		for(Bag i : bags) {
			if(item.inBag.name == i.name) {
				return inclusive;
			}
		}
		return !inclusive;
	}
	
	public String toString() {
		String str = "";
		str += inclusive + " " + item.name;
		for(Bag bg: bags) {
			str += " " + bg.name;
		}
		return str;
	}

}
