// Name: Mia Zhang (Andrew ID: jingqiz)
// Course: 95771 A
// Assignment number: Project 1 Part 2

import java.math.BigInteger;
import java.util.Scanner;

public class MerkleHellman {
    // The full private key list (a superincreasing sequence): 7^1, 7^2, 7^3, ..., 7^640
    private static SinglyLinkedList fullKey = new SinglyLinkedList();
    private static BigInteger r;
    private static BigInteger q;
    // Length of clear text
    private static int msgLength;
    // Length of binary message (which is 8 * Length of clear text)
    private static int binaryMsgLength;


    public static void main(String[] args) {
        generateFullKey();
        System.out.println("Enter a string and I will encrypt it as single large integer.");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (input.length() > 80) {   // Check if input exceeds length limit
            System.out.println("The string entered is too long (>80 characters), please try again.");
            input = sc.nextLine();
        }
        System.out.println("Clear text:\n" + input);
        System.out.println("Number of clear text bytes = " + input.length());
        BigInteger ciphertext = encrypt(input);
        System.out.println(input + " is encrypted as\n" + ciphertext);
        System.out.println("Result of decryption: " + decrypt(ciphertext));
    }

    private static void generateFullKey() {
        // To generate the full private key list (a superincreasing sequence): 7^1, 7^2, 7^3, ..., 7^640
        BigInteger key = BigInteger.ONE;
        BigInteger seven = BigInteger.valueOf(7);
        for (int i = 1; i <= 640; i++) {
            key = key.multiply(seven);
            fullKey.addAtEndNode(key);
        }
    }

    private static BigInteger encrypt(String s) {
        // Convert input string to binary list
        SinglyLinkedList binaryMsg = convertStringToBinaryList(s);

        // Choose a q larger than sum of private keys
        q = BigInteger.ZERO;
        for (int i = 0; i < binaryMsgLength; i++) {
            q = q.add((BigInteger) fullKey.getObjectAt(i));
        }
        q = q.add(BigInteger.valueOf(7));

        // Choose a r coprime to q
        r = BigInteger.valueOf(11);

        // Construct the public keys and encrypt binary message
        BigInteger ciphertext = BigInteger.ZERO;
        BigInteger currentPrivateKey, currentPublicKey;
        while (binaryMsg.hasNext()) {
            currentPrivateKey = (BigInteger) fullKey.next();
            // Compute the public key by multiplying each private key by r modulo q
            currentPublicKey = new BigInteger(String.valueOf(currentPrivateKey.multiply(r).mod(q)));
            // Multiply each bit by the corresponding public key and add the results
            ciphertext = ciphertext.add(currentPublicKey.multiply((BigInteger) binaryMsg.next()));
        }

        return ciphertext;
    }

    private static String decrypt(BigInteger ciphertext) {
        // Use the Extended Euclidean Algorithm to find the modular inverse of r mod q
        BigInteger rInverse = r.modInverse(q);

        // Compute inversed ciphertext c'
        BigInteger ciphertextInverse = ciphertext.multiply(rInverse).mod(q);

        // Use the greedy algorithm to decompose the inversed ciphertext
        BigInteger currPrivateKey;
        int[] decryptedBinaryMsg = new int[binaryMsgLength];
        // Try all private keys from big to small
        for (int i = binaryMsgLength - 1; i >= 0; i--) {
            if (ciphertextInverse.equals(BigInteger.ZERO)) break;
            currPrivateKey = (BigInteger) fullKey.getObjectAt(i);
            // If current private key <= c' then subtract from c'
            if (ciphertextInverse.compareTo(currPrivateKey) != -1) {
                ciphertextInverse = ciphertextInverse.subtract(currPrivateKey);
                decryptedBinaryMsg[i] = 1;
            }
        }

        // Convert bytes into letters
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < msgLength; n++) { // Each letter
            int currChar = 0;
            for (int i = n * 8; i < n * 8 + 8; i++) { // Each bit in a byte
                currChar += decryptedBinaryMsg[i] * Math.pow(2, 7 - i + n * 8);
            }
            sb.append((char) currChar);
        }
        return sb.toString();
    }

    private static SinglyLinkedList convertStringToBinaryList(String s) {
        SinglyLinkedList binaryMsg = new SinglyLinkedList();
        char[] ch = s.toCharArray();
        msgLength = ch.length;
        binaryMsgLength = msgLength * 8;
        for (char c : ch) {
            // Complete with 0s on the left to form an 8-bit binary string
            String binStr = String.format("%08d", Integer.valueOf(Integer.toBinaryString(c)));
            for (int i = 0; i < 8; i++) {
                binaryMsg.addAtEndNode(new BigInteger(String.valueOf(binStr.charAt(i))));
            }
        }
        return binaryMsg;
    }
}
