import java.util.ArrayList;
import java.lang.Math;

public class Hashtable {

	private ArrayList<Node> bucketArray;
	private int numBuckets;
	private int size;
	private float load_threshold = (float) 0.5;

	public Hashtable() {
		bucketArray = new ArrayList<>();
		numBuckets = 2027;
		size = 0;

		for (int i = 0; i < numBuckets; i++)
			bucketArray.add(null);
	}

	public int getSize() {
		return size;
	}

	private boolean isEmpty() {
		if (size == 0)
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
		Node head = bucketArray.get(getIndex(key));

		while (head != null) {
			if (head.key.equals(key))
				return true;
			else
				head = head.next;
		}
		return false;
	}

	public String get(String key) {
		Node head = bucketArray.get(getIndex(key));

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
		Node head = bucketArray.get(index);

		while (head != null) {
			if (head.key.equals(key)) {
				head.value = value;
				return;
			}
			head = head.next;
		}

		head = bucketArray.get(index);
		Node newNode = new Node(key, value);

		if (head == null) {
			bucketArray.set(index, newNode);
		}

		else {
			newNode.next = head;
			bucketArray.set(index, newNode);
		}

		size++;

		if ((1.0 * size) / numBuckets >= load_threshold) {
			ArrayList<Node> temp = bucketArray;
			bucketArray = new ArrayList<>();
			numBuckets *= 2;
			size = 0;

			for (int i = 0; i < numBuckets; i++)
				bucketArray.add(null);

			for (int i = 0; i < temp.size(); i++) {
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

		size--;

		if (prev != null)
			prev.next = head.next;
		else
			bucketArray.set(index, head.next);
		return head.value;
	}

}
