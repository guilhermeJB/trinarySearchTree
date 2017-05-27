import java.util.Random;

/**
 * A class to exercise the methods of PTTStringsMap, useful to check the correctness of 
 * your PTTStringsMap implementation with ConGu tool.
 * 
 * It uses instances of Maps to Integers.
 * 
 * @author antonialopes (AED 16/17 @ FCUL-DI)
 */
public class MapStringRandomTest {

	public static void main(String[] args) {
		new MapStringRandomTest(new PTTStringsMap<Integer>(), 100).run();
	}

	/*
	 * Number of operations on maps to test.
	 */
	private static final int MAX_OPERATIONS = 6;

	/*
	 * The larger integer to include in the map.
	 */
	private final static int MAX_NUMBER_OF_VALS = 5;

	/*
	 * The limits for the size of the strings to use as key.
	 */
	private final static int MAX_LEN_OF_STRINGS = 6;
	private static final int MIN_LEN_OF_STRINGS = 2;

	private static final String ALPHABETH = "ACGIMTX";
	
	/*
	 * Our random object to generate operations, keys and values.
	 */
	private final Random rand;

	/*
	 * The map manipulated during the test execution.
	 */
	private PTTStringsMap<Integer> map;

	/*
	 * The number of operation calls to perform during the test execution.
	 */
	private final int howMany;

	public MapStringRandomTest(PTTStringsMap<Integer> map, int howMany) {
		this.map = map;
		rand = new Random();
		this.howMany = howMany;
	}

	/*
	 * Creates an integer to put in the map
	 */
	private Integer aValue() {
		return new Integer(rand.nextInt(MAX_NUMBER_OF_VALS));
	}
	
	/*
	 * Creates a key to use in the map
	 * @ensures \return.length > 0
	 */
	private String aKey() {
		int length = rand.nextInt(MAX_LEN_OF_STRINGS) + MIN_LEN_OF_STRINGS;
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < length; i++){
			result.append(ALPHABETH.charAt(rand.nextInt(ALPHABETH.length())));
		}
		return result.toString();
	}


	public void run() {
		for (int i = 0; i < howMany; i++) {
			int value;
			String key;
			switch (rand.nextInt(MAX_OPERATIONS)) {
			case 0:
				value = aValue();
				key = aKey();
				map.put(key, value);
				System.out.println("put ("+ key +","+ value + ")");
				break;
			case 1:                  
				key= aKey();
				if (map.containsKey(key)){
					System.out.println("get "+ key + ": "+ map.get(key));
				}
				break;
			case 2:
				key = aKey();
				System.out.println("containsKey "+ key +" "+map.containsKey(key));
				break;
			case 3:
				System.out.println("size "+ map.size());
				break;
			case 4:
				System.out.println("collect "+map.keys());
				break;
			case 5:
				if (i%10 == 0){ 
					//to allow the map to grow this 
					//cannot be executed too often 
					System.out.println("make()");
					map = new PTTStringsMap<Integer>();
					break;
				} 				
			}
//			System.out.println(map.toStringForDebugging());
			System.out.println(map);
		}
		System.out.println("Done!");  
	}
}
