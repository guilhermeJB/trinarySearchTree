import java.util.ArrayDeque;

/**
 * Creates a trinary search tree using StringsMap
 * 		as to store the data
 * 
 * @author Guilherme Bernardo Nº 49504
 * @author Bruno Teixeira Nº 49498
 * 
 * @param <V>
 */
public class PTTStringsMap<V> implements StringsMap<V>, Cloneable{

	/* ************ FIELDS ************ */


	/*
	 * Trees size
	 */
	private int size;

	/*
	 * Trees root
	 */
	private Node<V> root;



	/* ************ CONSTRUCTORS  ************ */

	/**
	 * Creates a trinary search tree
	 */
	public PTTStringsMap(){
		root = null;
		size = 0;
	}

	/* ************ METHODS ************ */


	@Override
	public boolean containsKey(String key) {
		if(key == null)
			throw new IllegalArgumentException("o parametro dado e null");

		Node<V> noh = get(root, key, 0);
		if(noh == null)
			return false;
		StringBuilder sb1 = new StringBuilder(key);
		sb1.deleteCharAt(sb1.length() - 1);
		appendsMid(noh, sb1);
		if(sb1.toString().equals(key) && noh != null){
			return true;
		}

		return false;
	}

	private void appendsMid(Node<V> noh, StringBuilder sb1) {
		if(noh != null){
			appendsMid(noh.mid, sb1.append(noh.character));
		}
	}


	@Override
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

	/**
	 * Obtain the value associated with a given key.
	 * 
	 * @param key The key being sought
	 * @param currentNode A given node
	 * @param i The index of the char to add
	 * @return The value associated with this key if found; otherwise, null
	 * @requires key != null && key.lenght > 0;
	 */
	private Node<V> get(Node<V> currentNode, String key, int i) {
		if(currentNode == null)
			return null;
		if(key.length() < 1)
			throw new IllegalArgumentException("key.length needs to be greater than 0");
		char c = key.charAt(i);
		if(c < currentNode.character)
			return get(currentNode.left, key, i);		
		else if(c > currentNode.character)
			return get(currentNode.right, key, i);
		else if(i < key.length() - 1)
			return get(currentNode.mid, key, i+1);
		else
			return currentNode;
	}


	@Override
	public int size() {
		return size;
	}


	@Override
	public void put(String key, V val) {
		if (key == null) {
			throw new IllegalArgumentException("calls put() with null key");
		}
		if (!containsKey(key))
			size++;
		root = put(root, key, val, 0);
	}

	/**
	 * Puts a key and its value in the tree
	 * 
	 * @param noh current node
	 * @param key key to be put
	 * @param val value to assign
	 * @param indiceKey index of the char in the key string
	 * @return the root node after the key as been put
	 * @requires noh != null && key != null && key.length > 0
	 * 				&& val != null && indiceKey >= 0
	 */
	private Node<V> put(PTTStringsMap<V>.Node<V> noh, String key, V val, int indiceKey) {
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
			noh.value = val;

		return noh;
	}


	@Override
	public Iterable<String> keys() {
		ArrayDeque<String> ad = new ArrayDeque<String>();
		collectsKeys(root, new StringBuilder(), ad);
		return ad;
	}


	/**
	 * Goes through the tree starting in the given node and puts every key
	 * 		of the following nodes in the ArrayDeque provided (ad)
	 * 
	 * @param noh the current node
	 * @param sb1 stringbuilder to keep the word formed till that level in the tree
	 * @param ad arraydeque to save every key in the tree
	 * @requires noh != null && sb1 != null & ad != null
	 */
	private void collectsKeys(Node<V> noh, StringBuilder sb1, ArrayDeque<String> ad) {
		if (noh == null)
			return;
		collectsKeys(noh.mid, sb1.append(noh.character), ad);
		sb1.deleteCharAt(sb1.length() - 1);
		if (noh.mid == null) 
			ad.add(sb1.toString() + noh.character);
		collectsKeys(noh.left,  sb1, ad);
		collectsKeys(noh.right, sb1, ad);
	}


	@Override
	public boolean equals(Object other) {
		return this == other || other instanceof PTTStringsMap &&
				equalStringsMap((PTTStringsMap<V>) other);
	}

	/**
	 * Verifies whether a given PTTStringsMap is equal to this
	 * 
	 * @param other the PTTStringsMap to verify
	 * @return true if is equals; false otherwise
	 */
	private boolean equalStringsMap(PTTStringsMap<V> other) {
		if(other == null)
			return false;
		Node<V> p = this.root;
		Node<V> q = other.root;

		if(this.size != other.size)
			return false;

		return equalStringsMap(p, q);
	}


	/**
	 * Verifies whether two given nodes are equal
	 * 
	 * @param p one node
	 * @param q another node
	 * @return true if both nodes are equal; false otherwise
	 */
	private boolean equalStringsMap(Node<V> p, Node<V> q) {
		if(p == null && q != null || p != null && q == null)
			return false;
		else{
			if(p != null && q != null){
				if(Character.toString(q.character).equals(Character.toString(p.character))){
					if (equalStringsMap(p.mid, q.mid)){
						if(equalStringsMap(p.left, q.left))
							return equalStringsMap(p.right, q.right);
						else
							return false;
					}else
						return false;
				}else
					return false;
			}
		}
		return true;
	}

	
	@Override
	public String toString() {
		ArrayDeque<String> ad = new ArrayDeque<String>();
		collectsKeys(root, new StringBuilder(), ad);
		return ad.toString();
	}


	@Override
	public PTTStringsMap<V> clone(){
		PTTStringsMap<V> clone  = new PTTStringsMap<>();
		copiesKeys(root, clone);
		clone.size = size;
		return clone;
	}


	/**
	 * Copies the keys from this PTTStringsMap to the clone one 
	 * 		leaving every value null
	 * 
	 * @param node current node
	 * @param clone PTTStringsMap clone of this PTTStringsMap
	 * @requires node != null && clone != null
	 */
	private void copiesKeys(Node<V> node, PTTStringsMap<V> clone) {
		if(node != null){
			for(String key : (ArrayDeque<String>) keys()){
				clone.put(key, null);
			}
		}
	}


	/**
	 * Returns all keys started with a given string as an Iterable
	 * 
	 * @param inicio the beggining of the keys
	 * @return all keys started with a given string as an Iterable
	 */
	public Iterable<String> keysStartingWith(String inicio){
		if (inicio == null) {
			throw new IllegalArgumentException("inicio nao pode ser null");
		}
		ArrayDeque<String> ad = new ArrayDeque<String>();
		Node<V> noh = get(root, inicio, 0);
		if (noh == null)
			return ad;

		if (noh.mid == null)
			ad.add(inicio);

		collectsKeys(noh.mid, new StringBuilder(inicio), ad);

		return ad;
	}

	/**
	 * Node private class
	 * 
	 * @author Guilherme Bernardo Nº 49504
	 * @author Bruno Teixeira Nº 49498
	 *
	 * @param <V>
	 */
	@SuppressWarnings("hiding")
	private class Node<V>{


		/* ************ FIELDS ************ */

		/*
		 * A nodes character
		 */
		private char character;

		/*
		 * A nodes data
		 */
		private V value;

		/*
		 * Assignements of nodes to another node
		 */
		private Node<V> left, right, mid;


		/* ************ CONSTRUCTORS  ************ */

		/**
		 * Node constructor
		 * 
		 * @param data its data
		 * @param character its character
		 * @param left node in the left
		 * @param right node in the right
		 * @param mid node in the middle
		 */
		private Node(V data, char character, Node<V> left, Node<V> right, Node<V> mid){
			this.value = data;
			this.character = character;
			this.left = left;
			this.right = right;
			this.mid = mid;
		}

		/**
		 * Empty node constructor
		 */
		private Node(){
			this.value = null;
			this.left = null;
			this.right = null;
			this.mid = null;
		}
	}
}
