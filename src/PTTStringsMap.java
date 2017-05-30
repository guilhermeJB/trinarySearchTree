<<<<<<< HEAD
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

	/* (non-Javadoc)
	 * @see StringsMap#containsKey(java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see StringsMap#get(java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see StringsMap#size()
	 */
	@Override
	public int size() {
		return size;
	}

	/* (non-Javadoc)
	 * @see StringsMap#put(java.lang.String, java.lang.Object)
	 */
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
			noh.value = val;

		return noh;
	}

	/* (non-Javadoc)
	 * @see StringsMap#keys()
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}


	//ver isto

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ArrayDeque<String> ad = new ArrayDeque<String>();
		collectsKeys(root, new StringBuilder(), ad);
		return ad.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
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

	public static void main(String[] args) {
		PTTStringsMap<String> v = new PTTStringsMap<>();
		v.put("ol", "7");
		v.put("olga", "2");
		v.put("olaria", "5");
		v.put("otario", "0");
		v.put("oppita", "4");
		v.put("ogf", "1");

		System.out.println("v: ");
		System.out.println(v.toString());
		System.out.println("get: " + v.get("ol"));
		System.out.println("contains: (olga)" + v.containsKey("olga"));
		System.out.println("contains: (pita)" + v.containsKey("pita"));
		System.out.println("size: " + v.size());
		System.out.println("chaves: " + v.keys());
		System.out.println("comecarPor (ol): " + v.keysStartingWith("ol"));

		PTTStringsMap<String> v2 = v.clone();
		System.out.println("v2: ");
		System.out.println(v2.toString());
		System.out.println("get: " + v2.get("ol"));
		System.out.println("contains: (olga)" + v2.containsKey("olga"));
		System.out.println("contains: (pita)" + v2.containsKey("pita"));
		System.out.println("size: " + v2.size());
		System.out.println("chaves: " + v2.keys());
		System.out.println("comecarPor (ol): " + v2.keysStartingWith("ol"));
	}
}
=======
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

	/* (non-Javadoc)
	 * @see StringsMap#containsKey(java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see StringsMap#get(java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see StringsMap#size()
	 */
	@Override
	public int size() {
		return size;
	}

	/* (non-Javadoc)
	 * @see StringsMap#put(java.lang.String, java.lang.Object)
	 */
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
			noh.value = val;

		return noh;
	}

	/* (non-Javadoc)
	 * @see StringsMap#keys()
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}


	//ver isto

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ArrayDeque<String> ad = new ArrayDeque<String>();
		collectsKeys(root, new StringBuilder(), ad);
		return ad.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
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

	public static void main(String[] args) {
		PTTStringsMap<String> v = new PTTStringsMap<>();
		v.put("ol", "7");
		v.put("olga", "2");
		v.put("olaria", "5");
		v.put("otario", "0");
		v.put("oppita", "4");
		v.put("ogf", "1");

		System.out.println("v: ");
		System.out.println(v.toString());
		System.out.println("get: " + v.get("ol"));
		System.out.println("contains: (olga)" + v.containsKey("olga"));
		System.out.println("contains: (pita)" + v.containsKey("pita"));
		System.out.println("size: " + v.size());
		System.out.println("chaves: " + v.keys());
		System.out.println("comecarPor (ol): " + v.keysStartingWith("ol"));

		PTTStringsMap<String> v2 = v.clone();
		System.out.println("v2: ");
		System.out.println(v2.toString());
		System.out.println("get: " + v2.get("ol"));
		System.out.println("contains: (olga)" + v2.containsKey("olga"));
		System.out.println("contains: (pita)" + v2.containsKey("pita"));
		System.out.println("size: " + v2.size());
		System.out.println("chaves: " + v2.keys());
		System.out.println("comecarPor (ol): " + v2.keysStartingWith("ol"));
	}
}
>>>>>>> 434edf7dea965fdd019dce9aeec4bcad76e918bb
