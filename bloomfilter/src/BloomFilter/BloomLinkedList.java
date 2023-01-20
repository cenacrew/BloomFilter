package BloomFilter;

import java.util.LinkedList;

/**
 * 
 * A Bloom filter implementation using an LinkedList.
 * 
 * @author Valentin Sourdois Pajot
 */
public class BloomLinkedList {
    private int numHashFunctions;
    private LinkedList<Boolean> tab;

    /**
     * Constructs a new Bloom filter with the given size and number of hash
     * functions.
     * 
     * @param size             The size of the filter.
     * @param numHashFunctions The number of hash functions to use.
     */
    public BloomLinkedList(int size, int numHashFunctions) {
        this.numHashFunctions = numHashFunctions;
        this.tab = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            tab.add(false);
        }
    }

    /**
     * Adds the given element to the filter.
     * 
     * @param element The element to add.
     */
    public void add(String element) {
        for (int i = 0; i < numHashFunctions; i++) {
            int hash = hash(element, i);
            int index = hash % tab.size();
            tab.set(index, true);
        }
    }

    /**
     * Tests whether the given element is likely to be in the filter.
     * 
     * @param element The element to test.
     * @return true if the element is likely to be in the filter, false otherwise.
     */
    public boolean contains(String element) {
        for (int i = 0; i < numHashFunctions; i++) {
            int hash = hash(element, i);
            int index = hash % tab.size();
            if (!tab.get(index)) {
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
        return Math.abs(element.hashCode() + i) % tab.size();
    }
}
