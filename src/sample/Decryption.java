package sample;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Decryption {

    private final static String alphabet = "abcdefghijklmnopqrstuvwxyz";


    private Integer calculatePQValue(int valueN) {
        Encryption encryption = new Encryption();
        List<Integer> listOfPrimes = encryption.calculatePrimeNumbers(valueN);
        return (listOfPrimes.get(0) - 1) * (listOfPrimes.get(1) - 1);
    }

    public Integer calculateDecriptionKey(int valueN, int pbKeyE) {
        int pbKeyPQ = calculatePQValue(valueN);
        BigInteger inversedModuloNum = BigInteger.valueOf(pbKeyE).modInverse(BigInteger.valueOf(pbKeyPQ));
        System.out.println(inversedModuloNum);
        return inversedModuloNum.intValue();
    }

    public List<Character> calculateDecryptedText(int pbKeyPQ, int pbKeyE, List<Integer> encryptedText) {
        List<Character> decryptedText = new ArrayList<>();
        for (Integer encryptedChar : encryptedText) {
            decryptedText.add(calculateCharacter(encryptedChar, pbKeyPQ, pbKeyE));
        }
        return decryptedText;
    }

    private Character calculateCharacter(int encryptedChar, int pbDecKey, int pbKeyE) {
        BigInteger currentChar = BigInteger.valueOf(encryptedChar)
                .pow(pbDecKey)
                .mod(BigInteger.valueOf(pbKeyE));

        return alphabet.charAt(currentChar.intValue() - 1);
    }
}
