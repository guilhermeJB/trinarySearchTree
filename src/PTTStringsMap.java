import java.util.ArrayDeque;
import java.util.ArrayList;

public class PTTStringsMap<V> implements StringsMap<V>, Cloneable{

	private int size;
	private Node<V> root;


	public PTTStringsMap(){
		//		root = new Node<V>();
	}

	public boolean containsKey(String key) {
		if(key == null)
			throw new IllegalArgumentException("o parametro dado e null");
		return get(key) != null;
	}

	public V get(String key) {
		if(key == null)
			throw new IllegalArgumentException("o parametro dado e null");
		if(key.length() < 1)
			throw new IllegalArgumentException("key.length needs to be greater than 0");
		Node<V> noh = get(root, key, 0);
		if (noh == null)
			return null;
		return noh.value;
	}

	private Node<V> get(Node<V> localRoot, String key, int i) {
		if(localRoot == null)
			return null;
		if(key.length() < 1)
			throw new IllegalArgumentException("key.length needs to be greater than 0");
		char c = key.charAt(i);
		if(c < localRoot.character)
			return get(localRoot.left, key, i);		
		else if(c > localRoot.character)
			return get(localRoot.right, key, i);
		else if(i < key.length() - 1)
			return get(localRoot.mid, key, i+1);
		else
			return localRoot;
	}

	public int size() {
		return size;
	}

	public void put(String key, V val) {
		if (key == null) {
			throw new IllegalArgumentException("calls put() with null key");
		}
		if (!containsKey(key))
			size++;
		root = put(root, key, val, 0);
	}

	private Node<V> put(Node<V> x, String key, V val, int d) {
		char c = key.charAt(d);
		if (x == null) {
			x = new Node<V>();
			x.character = c;
		}
		if (c < x.character)
			x.left  = put(x.left,  key, val, d);

		else if (c > x.character)  
			x.right = put(x.right, key, val, d);

		else if (d < key.length() - 1)
			x.mid   = put(x.mid,   key, val, d+1);

		else 
			x.value   = val;

		return x;
	}

	@Override
	public Iterable keys() {
		ArrayDeque<String> ad = new ArrayDeque<String>();
		collectsKeys(root, new StringBuilder(), ad);
		return ad;
	}


	// all keys in subtrie rooted at x with given prefix
	private void collectsKeys(Node<V> noh, StringBuilder prefix, ArrayDeque<String> armazenamento) {
		if (noh == null)
			return;
		collectsKeys(noh.left,  prefix, armazenamento);
		if (noh.value != null) 
			armazenamento.add(prefix.toString() + noh.character);
		collectsKeys(noh.mid, prefix.append(noh.character), armazenamento);
		prefix.deleteCharAt(prefix.length() - 1);
		collectsKeys(noh.right, prefix, armazenamento);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof PTTStringsMap))
			return false;
		PTTStringsMap other = (PTTStringsMap) obj;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "mapa: " + keys();
	}

	//acabar isto
	public PTTStringsMap<V> clone(){
		PTTStringsMap< V> clone  = new PTTStringsMap<>();
		clone.root = root;
		clone.root.value = null;
		for(Object s : keys()){
			clone.put(s.toString(), null);
		}
		return clone;
	}

	
	public Iterable<String> keysStartingWith(String pref){
		if (pref == null) {
			throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
		}
		ArrayDeque<String> queue = new ArrayDeque<String>();
		Node<V> x = get(root, pref, 0);
		if (x == null)
			return queue;

		if (x.value != null)
			queue.add(pref);

		collectsKeys(x.mid, new StringBuilder(pref), queue);

		return queue;
	}




	//private Node class (ex 1)
	private class Node<V>{

		private char character;
		private V value;
		private Node<V> left, right, mid;

		private Node(V data, char character, Node<V> left, Node<V> right, Node<V> mid){
			this.value = data;
			this.character = character;
			this.left = left;
			this.right = right;
			this.mid = mid;
		}

		private Node(){
			this.value = null;
			this.character = 0;
			this.left = null;
			this.right = null;
			this.mid = null;
		}
	}
	
	public static void main(String[] args) {
		PTTStringsMap<String> v = new PTTStringsMap<>();
		v.put("ola", "2");
		v.put("adeus", "3");
		v.put("xau", "0");
		
		PTTStringsMap<String> v2 = v.clone();
		System.out.println("v: ");
		System.out.println(v.toString());
		System.out.println("v2: ");
		System.out.println(v2.toString());
	}
}
