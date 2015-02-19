
public class Unary implements Constraint {

	boolean inclusive;
	Bag[] bags;
	Item item;
	int[] bagIndexes;
	int itemIndex;
	
	public Unary(boolean inclusive, Bag[] bags, Item item) {
		this.inclusive = inclusive;
		this.bags = bags;
		this.item = item;
	}
	
	public Unary(boolean inclusive, Bag[] bags, Item item, int[] bagIndexes, int itemIndex) {
		this.inclusive = inclusive;
		this.bags = bags;
		this.item = item;
		this.bagIndexes = bagIndexes;
		this.itemIndex = itemIndex;
		
		for(Bag i : this.bags) {
			i.constrainted += 1;
		}
	}
	
	@Override
	public boolean checkConstraint(State CSP) {
		//System.out.println(this);
		for(Bag i : bags) {
			if(inclusive && (item.inBag == null) || (item.inBag != null && item.inBag.name == i.name)) {
				return true;
			} else if (inclusive) {
				return false;
			} else if (!inclusive && item.inBag != null && item.inBag.name == i.name) {
				return false;
			}
		}
		return true;
	}
	

	public int type() {
		return 0;
	}
	
	
	public String toString() {
		String str = "";
		str += "inclusive: " + inclusive + " " + item.name;
		for(Bag bg: bags) {
			str += " " + bg.name;
		}
		return str;
	}

}
