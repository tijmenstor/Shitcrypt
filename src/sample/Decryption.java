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

    public static String calculateDecryptedText(List<List<Integer>> encryptedText) {
        StringBuilder stringBuilder = new StringBuilder();

        encryptedText.forEach(encryptedWord -> {
            for (Integer integer : encryptedWord) {
                stringBuilder.append(calculateCharacter(integer));
            }
            stringBuilder.append(" ");
        });
        return stringBuilder.toString();
    }

    private static Character calculateCharacter(int encryptedChar) {
        BigInteger currentChar = BigInteger.valueOf(encryptedChar).pow(pkDecrypt);
        currentChar = currentChar.mod(BigInteger.valueOf(publicPQKey));
        return (char) currentChar.intValue();
    }
}
