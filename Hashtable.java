import java.lang.Math;
import java.util.ArrayList;


public class Hashtable {


	int buckets;
	int sizeOfTable = 0;
	ArrayList<Node> bucket = new ArrayList<>();
	float loadFactor = (float) 0.5;

	public Hashtable() {
		buckets = 2027;

		for (int i = 0; i < buckets; i++){
			bucket.add(null);
		}
	}

	public int getSize() {
		return sizeOfTable;
	}

	private boolean isEmpty() {
		if (sizeOfTable == 0)
			return true;
		return false;
	}

	private int getHash(String key) {
		return Math.abs(key.hashCode());
	}

	private int getIndex(String key) {
		return getHash(key) % numBuckets;
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

		if ((1.0 * sizeOfTable) / numBuckets >= loadFactor) {
			ArrayList<Node> temp = bucket;
			bucket= new ArrayList<>();
			buckets *= 2;
			sizeOfTable = 0;

			for (int i = 0; i < numBuckets; i++)
				bucketArray.add(null);

			for (int i = 0; i < temp.sizeOfTable(); i++) {
				Node oldNode = temp.get(i);
				while (oldNode != null) {
					put(oldNode.key, oldNode.value);
					oldNode = oldNode.next;
				}
			}
		}

	}

	public String remove(String key) {
		int index = getIndex(key);
		Node head = bucketArray.get(index);

		Node prev = null;
		while (head != null) {
			if (head.key.equals(key))
				break;

			prev = head;
			head = head.next;
		}

		if (head == null)
			return null;

		sizeOfTable--;

		if (prev != null)
			prev.next = head.next;
		else
			bucketArray.set(index, head.next);
		return head.value;
	}

}
