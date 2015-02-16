
public class Binary implements Constraint {

	boolean isEqual;
	Bag bag1, bag2;
	
	public Binary(boolean isEqual, Bag bag1, Bag bag2) {
		this.isEqual = isEqual;
		this.bag1 = bag1;
		this.bag2 = bag2;
	}
	
	@Override
	public boolean checkConstraint() {
		// TODO Auto-generated method stub
		return false;
	}

}
