// Name: Mia Zhang (Andrew ID: jingqiz)
// Course: 95771 A
// Assignment number: Project 1 Part 3

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MerkleTree {

    private SinglyLinkedList tree; // This is a 2-dimensional list
    private SinglyLinkedList currentLevel; // Used for recursion
    public SinglyLinkedList root;

    public MerkleTree(String fileName) throws NoSuchAlgorithmException {
        tree = new SinglyLinkedList();
        readFileAndAddData(fileName);
        generateUpperLevel();
    }

    private void readFileAndAddData(String fileName) {
        try {
            // Read lines from file
            File file = new File(fileName);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = null;

                // Put initial data lines into the bottom level list
                SinglyLinkedList initialDataList = new SinglyLinkedList();
                while ((lineText = bufferedReader.readLine()) != null) {
                    initialDataList.addAtEndNode(lineText);
                }
                // If initial data list has odd number nodes, duplicate the last node to make it even
                if (initialDataList.countNodes() % 2 != 0) {
                    initialDataList.addAtEndNode(initialDataList.getLast());
                }
                // Add the initial data list into tree
                tree.addAtFrontNode(initialDataList);

                // Construct bottom hash level
                SinglyLinkedList bottomLevelList = new SinglyLinkedList();
                initialDataList.reset();
                while (initialDataList.hasNext()) {
                    bottomLevelList.addAtEndNode(h((String) initialDataList.next()));
                }
                tree.addAtFrontNode(bottomLevelList);
                currentLevel = bottomLevelList;
            } else {
                System.out.println("File doesn't exist.");
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void generateUpperLevel() throws NoSuchAlgorithmException {
        while (currentLevel.countNodes() > 1) { // Haven't reached the tree top
            if (currentLevel.countNodes() % 2 != 0) {
                currentLevel.addAtEndNode(currentLevel.getLast());
            }
            SinglyLinkedList upperLevel = new SinglyLinkedList();
            currentLevel.reset();
            while (currentLevel.hasNext()) {
                // Concatenate next two hashes and hash the concatenation to compute a new hash, add to upper level
                upperLevel.addAtEndNode(h((String) currentLevel.next() + (String) currentLevel.next()));
            }
            tree.addAtFrontNode(upperLevel);
            currentLevel = upperLevel;
        }
        root = currentLevel; // When out of the loop, current level has reached the tree top
    }

    // SHA-256 Hash function
    public static String h(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String filePath1 = "src/CrimeLatLonXY.csv"; // This one is the file with required root Hash
        String filePath2 = "src/CrimeLatLonXY1990_Size2.csv";
        String filePath3 = "src/CrimeLatLonXY1990_Size3.csv";
        String filePath4 = "src/smallFile.txt";
        MerkleTree myTree1 = new MerkleTree(filePath1);
        MerkleTree myTree2 = new MerkleTree(filePath2);
        MerkleTree myTree3 = new MerkleTree(filePath3);
        MerkleTree myTree4 = new MerkleTree(filePath4);

        System.out.println("Root Hash for file \"" + filePath1 + "\" is:\n" + myTree1.root);
        System.out.println("Root Hash for file \"" + filePath2 + "\" is:\n" + myTree2.root);
        System.out.println("Root Hash for file \"" + filePath3 + "\" is:\n" + myTree3.root);
        System.out.println("Root Hash for file \"" + filePath4 + "\" is:\n" + myTree4.root);
        System.out.println("\nExpected root Hash:\nA5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58");
    }
}
