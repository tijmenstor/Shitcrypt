package sample;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Decryption {

    private final static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static int pkDecrypt = 0;
    private static int publicKeyE = 0;
    private static int publicPQKey = 0;


    private static Integer calculatePQValue(int valueN) {
        List<Integer> listOfPrimes = Encryption.calculatePrimeNumbers(valueN);
        return (listOfPrimes.get(0) - 1) * (listOfPrimes.get(1) - 1);
    }

    public static Integer calculateDecriptionKey(int valueN, int pbKeyE) {
        int pbKeyPQ = calculatePQValue(valueN);
        BigInteger inversedModuloNum = BigInteger.valueOf(pbKeyE).modInverse(BigInteger.valueOf(pbKeyPQ));
        pkDecrypt = inversedModuloNum.intValue();
        publicKeyE = pbKeyE;
        publicPQKey = valueN;
        return inversedModuloNum.intValue();
    }

    public static List<Character> calculateDecryptedText(List<Integer> encryptedText) {
        List<Character> decryptedText = new ArrayList<>();
        for (Integer encryptedChar : encryptedText) {
            decryptedText.add(calculateCharacter(encryptedChar));
        }
        return decryptedText;
    }

    private static Character calculateCharacter(int encryptedChar) {
        BigInteger currentChar = BigInteger.valueOf(encryptedChar).pow(pkDecrypt);
        currentChar = currentChar.mod(BigInteger.valueOf(publicPQKey));
        return alphabet.charAt(currentChar.intValue() - 1);
    }
}
