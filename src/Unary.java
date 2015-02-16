
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
		// TODO Auto-generated method stub
		return false;
	}

}
