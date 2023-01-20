package BloomFilter;

/**
 * 
 * A Bloom filter implementation using an simple Array.
 * 
 * @author Valentin Sourdois Pajot
 */
public class BloomTable {
	private boolean[] tab;
	private int size;
	private int numHashes;

	/**
	 * Constructs a new Bloom filter with the given size and number of hash
	 * functions.
	 * 
	 * @param size             The size of the filter.
	 * @param numHashFunctions The number of hash functions to use.
	 */
	public BloomTable(int size, int numHashes) {
		this.size = size;
		this.numHashes = numHashes;
		this.tab = new boolean[size];
	}

	/**
	 * Adds the given element to the filter.
	 * 
	 * @param element The element to add.
	 */
	public void add(String input) {
		for (int i = 0; i < numHashes; i++) {
			int index = hash(input, numHashes);
			this.tab[index] = true;
		}
	}

	/**
	 * Tests whether the given element is likely to be in the filter.
	 * 
	 * @param element The element to test.
	 * @return true if the element is likely to be in the filter, false otherwise.
	 */
	public boolean contains(String input) {
		for (int i = 0; i < numHashes; i++) {
			int index = hash(input, numHashes);
			if (!this.tab[index]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Hashes the given element with the given index.
	 * 
	 * @param element The element to hash.
	 * @param i       The index to use.
	 */
	private int hash(String element, int i) {
		return Math.abs(element.hashCode() + i) % tab.length;
	}
}