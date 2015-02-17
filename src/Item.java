
public class Item {
	char name;
	int weight;
	Bag inBag;
	
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
}
