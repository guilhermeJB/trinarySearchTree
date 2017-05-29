import java.util.ArrayDeque;
import java.util.ArrayList;

public class PTTStringsMap<V> implements StringsMap<V>, Cloneable{

	private int size;
	private Node<V> root;
	private int indice;


	public PTTStringsMap(){
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

	private Node<V> put(Node<V> noh, String key, V val, int indiceKey) {
		char c = key.charAt(indiceKey);
		if (noh == null) {
			noh = new Node<V>();
			noh.character = c;
		}
		if (c < noh.character)
			noh.left  = put(noh.left,  key, val, indiceKey);

		else if (c > noh.character)  
			noh.right = put(noh.right, key, val, indiceKey);

		else if (indiceKey < key.length() - 1)
			noh.mid   = put(noh.mid,   key, val, indiceKey+1);

		else 
			noh.value   = val;

		return noh;
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
		StringBuilder sb1 = new StringBuilder("[");
		appends(root, indice, sb1);
		sb1.append("]");
		if(sb1.length() > 3)
			sb1.deleteCharAt(sb1.length() - 2);
		String s = sb1.toString();
		return s.replaceAll("," , ", ");
	}

	private void appends(Node<V> noh, int index, StringBuilder sb1) {
		indice = sb1.length();
		if(noh != null){
			sb1.append(noh.character);
			appends(noh.mid, sb1.length(), sb1);
			if(sb1.charAt(indice - 1) != ',')
				sb1.append(',');
			appends(noh.left, sb1.length(), sb1);
			appends(noh.right, sb1.length(), sb1);
		}
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
			this.left = null;
			this.right = null;
			this.mid = null;
		}
	}

	public static void main(String[] args) {
		PTTStringsMap<String> v = new PTTStringsMap<>();
		System.out.println(v.toString());
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
