import java.util.ArrayList;


public class State {
	ArrayList<Item> items;
	ArrayList<Bag> bags;
	ArrayList<Constraint> constraints;
	int MIN;
	int MAX;
	
	public State(ArrayList<Item> items, ArrayList<Bag> bags, ArrayList<Constraint> constraints,
			int MIN, int MAX) {
		this.items = new ArrayList<Item>(items);
		this.bags = new ArrayList<Bag>(bags);
		this.constraints = new ArrayList<Constraint>(constraints);
		this.MIN = MIN;
		this.MAX = MAX;
	}
	public State( State csp) {
		this.items = new ArrayList<Item>(csp.items);
		this.bags = new ArrayList<Bag>(csp.bags);
		this.constraints = new ArrayList<Constraint>(csp.constraints);
		this.MIN = csp.MIN;
		this.MAX = csp.MAX;
	}
	
	public boolean checkSystemConstraints() {
		for(Constraint i : this.constraints) {
			if( !i.checkConstraint()) {
				return false;
			}
		}
		return true;
	}
	
	private Item getUnassignedItem() {
		for(Item i : items) {
			if(i.inBag == null) {
				return i;
			}
		}
		return null;
	}

	public State backtrackingSearch() {
		return backtrack(this);
	}
	
	private State backtrack(State CSP){
		if(CSP.checkSystemConstraints()) {
			return CSP;
		}
		State newCSP = new State(CSP);
		Item var = newCSP.getUnassignedItem();
		for(Bag i : newCSP.bags) {
			var.inBag = i;
			if(newCSP.checkSystemConstraints()) {
				State testCSP = backtrack(newCSP);
				if(testCSP.checkSystemConstraints()) {
					return testCSP;
				}
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return items.toString();
	}
}
