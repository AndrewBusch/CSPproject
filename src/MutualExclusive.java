
public class MutualExclusive implements Constraint {

	Bag bag1, bag2;
	Item item1, item2;
	
	public MutualExclusive(Bag bag1, Bag bag2, Item item1, Item item2) {
		this.bag1 = bag1;
		this.bag2 = bag2;
		this.item1 = item1;
		this.item2 = item2;
	}
	
	@Override
	public boolean checkConstraint() {
		// TODO Auto-generated method stub
		return false;
	}

}
