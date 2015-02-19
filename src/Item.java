import java.util.ArrayList;


public class Item implements Comparable<Item> {
	char name;
	int weight;
	Bag inBag;
	ArrayList<Bag> allowedBags = new ArrayList<Bag>();
	
	public Item(char name, int weight) {
		this.name = name;
		this.weight = weight;
	}
	
	public String toString() {
		return name + ": " + weight + " in bag " + inBag;
	}
	
	public Item copy() {
		Item it =  new Item(this.name, this.weight);
		it.inBag = this.inBag;
		return it;
	}

	@Override
	public int compareTo(Item o) {
		if(this.allowedBags.size() > o.allowedBags.size()){
			return 1;
		}
		if(this.allowedBags.size() == o.allowedBags.size()) {
			return 0;
		}
		else{
			return -1;
		}
	}
}
