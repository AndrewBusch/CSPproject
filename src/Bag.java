import java.util.ArrayList;


public class Bag {
	char name;
	int capacity;
	ArrayList<Item> allowedItems = new ArrayList<Item>();
	
	public Bag(char name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}
	
	public String toString() {
		return name + " of Cap: " + capacity;
	}
}
