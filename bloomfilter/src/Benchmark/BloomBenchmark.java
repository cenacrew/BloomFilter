package Benchmark;

import java.util.Random;
import java.util.UUID;

import BloomFilter.BloomArrayList;
import BloomFilter.BloomLinkedList;
import BloomFilter.BloomTable;

/**
 * 
 * A benchmarking class for testing the performance of Bloom filters.
 * 
 * @author Sourdois Pajot Valentin
 */
public class BloomBenchmark {

  /**
   * The main method for the benchmark.
   * 
   * @param args command line arguments (not used)
   */
  public static void main(String[] args) {
    // Set the parameters for the benchmark
    int k = 3; // number of hash
    int m = 100000; // size of tab
    int n = 100000; // number of element to add

    int[] kValues = { 1, 3, 5 }; // The values of k to test
    double[] mValues = { 1.01, 1.05, 1.10, }; // The values of m as a percentage of n to test

    // Create the Bloom filters
    BloomArrayList arrayListFilter = new BloomArrayList(m, k);
    BloomLinkedList linkedListFilter = new BloomLinkedList(m, k);
    BloomTable arrayFilter = new BloomTable(m, k);

    String[] values = new String[n];
    Random rand = new Random();

    // create random values
    for (int i = 0; i < n; i++) {
      values[i] = UUID.randomUUID().toString();
    }

    // add the value in the differents filters
    for (String value : values) {
      arrayListFilter.add(value);
      linkedListFilter.add(value);
      arrayFilter.add(value);
    }

    // Measure the execution time for searching an element in each filter
    long startTime = System.currentTimeMillis();
    for (String value : values) {
      arrayListFilter.contains(value);
    }
    long endTime = System.currentTimeMillis();
    long arrayListTime = endTime - startTime;

    startTime = System.currentTimeMillis();
    for (String value : values) {
      linkedListFilter.contains(value);
    }
    endTime = System.currentTimeMillis();
    long linkedListTime = endTime - startTime;

    startTime = System.currentTimeMillis();
    for (String value : values) {
      arrayFilter.contains(value);
    }
    endTime = System.currentTimeMillis();
    long arrayTime = endTime - startTime;

    // Print the execution times
    System.out.println("ArrayList filter execution time: " + arrayListTime + " milliseconds");
    System.out.println("LinkedList filter execution time: " + linkedListTime + " milliseconds");
    System.out.println("Array filter execution time: " + arrayTime + " milliseconds");

    for (int k2 : kValues) {
      for (double m2 : mValues) {
        int mInt = (int) (n * m2);

        int numFalsePositives = 0;
        int numIterations = 1000;

        // Create the Bloom filter
        BloomArrayList arrayListFilter2 = new BloomArrayList(mInt, k2);

        String[] values2 = new String[n];
        Random rand2 = new Random();

        // create random values
        for (int i = 0; i < n; i++) {
          values2[i] = UUID.randomUUID().toString();
        }

        // add the value in the filter
        for (String value2 : values2) {
          arrayListFilter2.add(value2);
        }

        for (int i = 0; i < numIterations; i++) {
          String value2 = UUID.randomUUID().toString();
          if (arrayListFilter2.contains(value2)) {
            numFalsePositives++;
          }
        }

        double falsePositiveRate = (double) numFalsePositives / numIterations;
        System.out.printf("k2=%d, m2=%d%% of n: false positive rate: %.2f%%\n", k2, (int) +((m2 - 1) * 100),
            (1 - falsePositiveRate) * 100);
      }
    }

  }

}
