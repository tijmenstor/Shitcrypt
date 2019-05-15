package sample;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Encryption {

    private final static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Calculates the prime numbers from a given number.
     *
     * @param valueN a given number
     * @return list of prime integers.
     */
    public static List<Integer> calculatePrimeNumbers(int valueN) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 2; i <= valueN / i; i++) {
            while (valueN % i == 0) {
                factors.add(i);
                valueN /= i;
            }
        }
        if (valueN > 1) {
            factors.add(valueN);
        }
        return factors;
    }

    /**
     * Checks if a given number is a semiprime.
     *
     * @param valueN given number.
     * @return boolean to indicate if its a semiprime.
     */
    public static boolean isSemiPrime(int valueN) {
        int cnt = 0;

        for (int i = 2; cnt < 2 && i * i <= valueN; ++i) {
            while (valueN % i == 0) {
                valueN /= i;
                ++cnt;
            }
        }
        if (valueN > 1) {
            ++cnt;
        }
        return cnt == 2;
    }

    public static List<List<Integer>> calculateCipher(String plainMessage, int pkCompositeFirst, int pkCompositeSecond, int pkSecond) {
        List<Integer> cipherList = new ArrayList<>();
        String[] wordArray = plainMessage.split(" ");
        List<List<Integer>> encryptedWordList = new ArrayList<>();
        for (String word : wordArray) {
            List<Integer> tempWordArray = new ArrayList<>();
            for (int i = 0; i < word.length(); i++) {
                char currentBlock = word.charAt(i);
                tempWordArray.add((int) currentBlock);
            }
            encryptedWordList.add(tempWordArray);
        }

        encryptedWordList.forEach(encryptedWord -> {
            for (int i = 0; i < encryptedWord.size(); i++) {
                BigInteger currentBlock = BigInteger.valueOf(encryptedWord.get(i)).pow(pkSecond);
                Integer newBlock = currentBlock.mod(BigInteger.valueOf(pkCompositeFirst * pkCompositeSecond)).intValue();
                encryptedWord.set(i, newBlock);
            }
        });
        return encryptedWordList;
    }

    /**
     * Calculates the second part of the public key based on the first value.
     * The second value of the public key is a coprime of the first value.
     *
     * @return second part of the public key
     */
    public static int calculatePublicKeyPart2(int publicKeyPart) {
        int newEValue;
        do {
            newEValue = new Random().nextInt(publicKeyPart - 2) + 1;
        } while (!isRelativelyPrime(publicKeyPart, newEValue));
        return newEValue;
    }


    /**
     * Check whether two numbers are co-primes/relatively prime.
     * This method is also called Euclidean Algorithm.
     *
     * @param a first prime number
     * @param b second prime number
     * @return biggest common divider
     */
    private static int euclidsMethod(int a, int b) {
        int t;
        while (b != 0) {
            t = a;
            a = b;
            b = t % b;
        }
        return a;
    }

    /**
     * Uses euclidean algorithm to determine whether two numbers are relative primes.
     *
     * @param a first prime number
     * @param b second prime number
     * @return if the biggest divider is 1, they are relatively prime == true
     */
    private static boolean isRelativelyPrime(int a, int b) {
        return euclidsMethod(a, b) == 1;
    }
}
