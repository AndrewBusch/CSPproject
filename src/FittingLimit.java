
public class FittingLimit implements Constraint {

	int min, max;
	public FittingLimit(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	@Override
	public boolean checkConstraint() {
		// TODO Auto-generated method stub
		return false;
	}

}
