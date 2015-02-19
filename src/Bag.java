import java.util.ArrayList;


public class Bag implements Comparable<Bag> {
	char name;
	int capacity;
	int weight = 0;
	int itemsContained;
	int constrainted;
	
	public Bag(char name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}
	
	public String toString() {
		return name + " of Cap: " + capacity;
	}

	@Override
	public int compareTo(Bag arg0) {
		if(arg0.constrainted < this.constrainted) return 1;
		if(arg0.constrainted == this.constrainted) return 0;
		else
			return -1;
	}
}
