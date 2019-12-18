import java.lang.Math;
import java.util.ArrayList;


public class Hashtable {


	int buckets = 1000;
	int sizeOfTable;
	ArrayList<Node> bucket = new ArrayList<>();
	float loadFactor = (float) 0.5;

	public Hashtable() {

		for (int i = 0; i < buckets; i++){
			bucket.add(null);
		}
	}

	private int getHash(String key) {
		return Math.abs(key.hashCode());
	}

	public int getSize() {
		return sizeOfTable;
	}

	private boolean isEmpty() {
		if (sizeOfTable != 0)
			return false;
		return true;
	}



	private int getIndex(String key) {
		return getHash(key) % buckets;
	}

	public boolean containsKey(String key) {
		Node head = bucket.get(getIndex(key));

		while (head != null) {
			if (head.key.equals(key))
				return true;
			else
				head = head.next;
		}
		return false;
	}

	public String get(String key) {
		Node head = bucket.get(getIndex(key));

		while (head != null) {
			if (head.key.equals(key))
				return head.value;
			else
				head = head.next;
		}

		return null;
	}

	public void put(String key, String value) {
		int index = getIndex(key);
		Node head = bucket.get(index);

		while (head != null) {
			if (head.key.equals(key)) {
				head.value = value;
				return;
			}
			head = head.next;
		}

		head = bucket.get(index);
		Node newNode = new Node(key, value);

		if (head == null) {
			bucket.set(index, newNode);
		}

		else {
			newNode.next = head;
			bucket.set(index, newNode);
		}

		sizeOfTable++;

		if ((float)sizeOfTable / buckets >= loadFactor) {
			ArrayList<Node> temp = bucket;
			bucket= new ArrayList<>();
			buckets *= 2;
			sizeOfTable = 0;

			for (int i = 0; i < buckets; i++)
				bucket.add(null);

			for (int i = 0; i < temp.size(); i++) {
				Node last = temp.get(i);
				while (last != null) {
					put(last.key, last.value);
					last = last.next;
				}
			}
		}

	}

	public String remove(String key) {
		Node previous = null;
		int index = getIndex(key);
		Node head = bucket.get(index);

		
		while (head != null) {
			if (head.key.equals(key)){
				break;
			}

			previous = head;
			head = head.next;
		}

		if (head == null){
			return null;
		}

		sizeOfTable--;

		if (previous != null){
			previous.next = head.next;
		}else{
			bucket.set(index, head.next);
		}
		return head.value;
	}
}
